package com.example.wecommerce_api.entity;

import com.example.wecommerce_api.entity.token.Token;
import com.example.wecommerce_api.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_tb")
public class UserEntity implements UserDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "email", unique = true)
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "phone_number", unique = true)
    private String phoneNumber;
    @Column(name = "profile_photo")
    private String profilePhoto;
    @Column(name = "status",columnDefinition = "boolean default false")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Boolean status;
    @Column(name = "google_link")
    private String googleLink;
    @Column(name = "map_link")
    private String maplink;
    @Column(name = "created_date",columnDefinition = "timestamp default now()")
    private LocalDateTime createDate;
    @Column(name = "address")
    private String shopAddress;
    @Enumerated(EnumType.STRING)
    @JsonIgnore
    private Role role;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Token> tokens;
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private CodeEntity code;

    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL)
    @JsonIgnore
    private CredentialEntity credential;

    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL)
    @JsonIgnore
    private DeviceTokenEntity deviceToken;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<PurchaseDetailEntity> purchases;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ProductEntity> product;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<BookMarkEntity> bookMark;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<CollectionEntity> collection;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<AddressEntity> address;
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private PasswordEntity passwordEntity;

    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL)
    @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<NotificationEntity> notificationS;
    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL)
    @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<NotificationEntity> notificationR;

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return email != null ? email : phoneNumber;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }

}
