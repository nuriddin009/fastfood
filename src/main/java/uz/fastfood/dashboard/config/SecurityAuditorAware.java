package uz.fastfood.dashboard.config;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import uz.fastfood.dashboard.entity.User;

import java.util.Optional;

@Component("securityAuditorAware")
public class SecurityAuditorAware implements AuditorAware<String> {


    @NotNull
    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication == null || !authentication.isAuthenticated()) && authentication.getPrincipal() != null) {
            return Optional.ofNullable((String) authentication.getPrincipal());
        }
        return Optional.of("system");
    }
}