package com.example.wecommerce_api.config;

import com.example.wecommerce_api.util.AuthHelper;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        if (AuthHelper.getUser()==null) {
            return Optional.of("system");
        }
        return Optional.ofNullable(AuthHelper.getUser().getUsername());
    }
}