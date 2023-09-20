package uz.fastfood.dashboard.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import uz.fastfood.dashboard.entity.User;
import uz.fastfood.dashboard.repository.UserRepository;

import java.util.Optional;


public class AuditorAwareImpl implements AuditorAware<User> {


    @Autowired
    private UserRepository userRepository;



    @Override
    public Optional<User> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication == null || !authentication.isAuthenticated())) {
            return userRepository.findByUsername((String) authentication.getPrincipal());
        }
        return Optional.empty();
    }
}