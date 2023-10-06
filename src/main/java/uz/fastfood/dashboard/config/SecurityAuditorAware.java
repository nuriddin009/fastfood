package uz.fastfood.dashboard.config;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import uz.fastfood.dashboard.entity.User;
import uz.fastfood.dashboard.repository.UserRepository;

import java.util.Optional;

@Component("securityAuditorAware")
@RequiredArgsConstructor
public class SecurityAuditorAware implements AuditorAware<String> {


    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication == null || !authentication.isAuthenticated()) && authentication.getPrincipal() != null) {
            return Optional.ofNullable((String) authentication.getPrincipal());
//            if (!StringUtils.isBlank(login) && userRepository.existsByUsername(login)) {
//                return userRepository.findByUsername((String) authentication.getPrincipal());
//            }
//        }
        }
        return Optional.of("system");
    }
}