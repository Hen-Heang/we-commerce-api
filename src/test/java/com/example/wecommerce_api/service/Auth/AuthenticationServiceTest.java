package com.example.wecommerce_api.service.Auth;

import com.example.wecommerce_api.config.JwtService;
import com.example.wecommerce_api.entity.UserEntity;
import com.example.wecommerce_api.entity.token.TokenRepository;
import com.example.wecommerce_api.enums.Role;
import com.example.wecommerce_api.payload.BaseResponse;
import com.example.wecommerce_api.payload.auth.AuthenticationResponse;
import com.example.wecommerce_api.payload.auth.RegisterRequest;
import com.example.wecommerce_api.repository.User.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.naming.AuthenticationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Covers the two auth-model gaps fixed alongside this test:
 *   1. Phone login used to issue tokens with no credential check at all.
 *   2. Registration used to trust a client-supplied `role` field.
 */
@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private TokenRepository tokenRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtService jwtService;
    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private AuthenticationService authenticationService;

    private UserEntity existingUser;

    @BeforeEach
    void setUp() {
        existingUser = UserEntity.builder()
                .id(1)
                .phoneNumber("85512345678")
                .email("existing@example.com")
                .password("hashed-password")
                .role(Role.USER)
                .build();
    }

    @Test
    void loginByPhoneNumber_withCorrectPassword_issuesTokens() throws Exception {
        when(userRepository.findByPhoneNumber("85512345678")).thenReturn(existingUser);
        when(passwordEncoder.matches("correct-password", "hashed-password")).thenReturn(true);
        when(jwtService.generateToken(existingUser)).thenReturn("access-token");
        when(jwtService.generateRefreshToken(existingUser)).thenReturn("refresh-token");
        when(tokenRepository.findAllValidTokenByUser(1)).thenReturn(java.util.List.of());

        BaseResponse response = authenticationService.LoginByPhoneNumber("85512345678", "correct-password");

        AuthenticationResponse payload = (AuthenticationResponse) response.getPayload();
        assertThat(payload.getAccessToken()).isEqualTo("access-token");
        assertThat(payload.getRefreshToken()).isEqualTo("refresh-token");
    }

    @Test
    void loginByIdentifier_withEmailAndCorrectPassword_issuesTokens() throws Exception {
        // No user at this phone number — falls back to email lookup.
        when(userRepository.findByPhoneNumber("existing@example.com")).thenReturn(null);
        when(userRepository.findByEmail("existing@example.com")).thenReturn(java.util.Optional.of(existingUser));
        when(passwordEncoder.matches("correct-password", "hashed-password")).thenReturn(true);
        when(jwtService.generateToken(existingUser)).thenReturn("access-token");
        when(jwtService.generateRefreshToken(existingUser)).thenReturn("refresh-token");
        when(tokenRepository.findAllValidTokenByUser(1)).thenReturn(java.util.List.of());

        BaseResponse response = authenticationService.LoginByPhoneNumber("existing@example.com", "correct-password");

        AuthenticationResponse payload = (AuthenticationResponse) response.getPayload();
        assertThat(payload.getAccessToken()).isEqualTo("access-token");
    }

    @Test
    void loginByPhoneNumber_withWrongPassword_isRejected() {
        when(userRepository.findByPhoneNumber("85512345678")).thenReturn(existingUser);
        when(passwordEncoder.matches("wrong-password", "hashed-password")).thenReturn(false);

        assertThatThrownBy(() ->
                authenticationService.LoginByPhoneNumber("85512345678", "wrong-password")
        ).isInstanceOf(AuthenticationException.class);

        verify(jwtService, never()).generateToken(any(UserEntity.class));
    }

    @Test
    void loginByPhoneNumber_withUnknownPhoneNumber_isRejectedWithSameGenericMessage() {
        when(userRepository.findByPhoneNumber("00000000")).thenReturn(null);

        AuthenticationException unknownPhoneEx = catchAuthenticationException(() ->
                authenticationService.LoginByPhoneNumber("00000000", "irrelevant"));

        when(userRepository.findByPhoneNumber("85512345678")).thenReturn(existingUser);
        when(passwordEncoder.matches("wrong-password", "hashed-password")).thenReturn(false);
        AuthenticationException wrongPasswordEx = catchAuthenticationException(() ->
                authenticationService.LoginByPhoneNumber("85512345678", "wrong-password"));

        // Same message for "no such user" and "wrong password" — the response
        // must not let a caller enumerate which phone numbers are registered.
        assertThat(unknownPhoneEx.getMessage()).isEqualTo(wrongPasswordEx.getMessage());
    }

    @Test
    void register_ignoresAnyClientSuppliedRole_alwaysAssignsUser() throws Exception {
        RegisterRequest request = RegisterRequest.builder()
                .userName("New Seller")
                .email("seller@example.com")
                .password("plain-password")
                .phoneNumber("85599999999")
                .build();

        when(userRepository.findByPhoneNumber("85599999999")).thenReturn(null);
        when(userRepository.findByEmail("seller@example.com")).thenReturn(java.util.Optional.empty());
        when(passwordEncoder.encode("plain-password")).thenReturn("hashed-password");
        when(userRepository.save(any(UserEntity.class))).thenAnswer(inv -> inv.getArgument(0));
        when(jwtService.generateToken(any(UserEntity.class))).thenReturn("access-token");
        when(jwtService.generateRefreshToken(any(UserEntity.class))).thenReturn("refresh-token");

        authenticationService.register(request);

        var savedUserCaptor = org.mockito.ArgumentCaptor.forClass(UserEntity.class);
        verify(userRepository).save(savedUserCaptor.capture());
        assertThat(savedUserCaptor.getValue().getRole()).isEqualTo(Role.USER);
    }

    private AuthenticationException catchAuthenticationException(ThrowingCallable callable) {
        try {
            callable.call();
        } catch (AuthenticationException e) {
            return e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        throw new AssertionError("Expected AuthenticationException to be thrown");
    }

    private interface ThrowingCallable {
        void call() throws Exception;
    }
}
