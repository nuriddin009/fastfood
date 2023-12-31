package uz.fastfood.dashboard.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.fastfood.dashboard.dto.request.AuthenticationRequest;
import uz.fastfood.dashboard.dto.request.RegisterRequest;
import uz.fastfood.dashboard.dto.response.AuthenticationResponse;
import uz.fastfood.dashboard.entity.Role;
import uz.fastfood.dashboard.entity.Token;
import uz.fastfood.dashboard.entity.User;
import uz.fastfood.dashboard.entity.enums.RoleName;
import uz.fastfood.dashboard.entity.enums.TokenType;
import uz.fastfood.dashboard.repository.RoleRepository;
import uz.fastfood.dashboard.repository.TokenRepository;
import uz.fastfood.dashboard.repository.UserRepository;
import uz.fastfood.dashboard.security.JwtService;


import java.io.IOException;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;
    private final UserSession userSession;


    public AuthenticationResponse register(RegisterRequest request) {
        Role role = roleRepository.save(Role.builder().name(RoleName.ROLE_USER).build());
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .username(request.getUsername())
                .phoneNumber("+998991234567")
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(Set.of(role))
                .build();
        var savedUser = repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(savedUser, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }





    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user = repository.findByUsername(request.getUsername())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = this.repository.findByUsername(userEmail)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

    public String isValidAdminToken(String token) {
        Optional<User> byEmail = repository.findByUsername(jwtService.extractUsername(token));
        if (byEmail.isPresent()) {
            boolean tokenValid = jwtService.isTokenValid(token, byEmail.get());
            return tokenValid ? "ADMIN" : "UnAuthorized";
        } else {
            return "UnAuthorized";
        }
    }

//    public BaseResponse<Certificate> changePassword(ChangePasswordRequest request, BaseResponse<Certificate> response, HttpServletRequest servletRequest) {
//
//        try {
//
//
//
//            String email = jwtService.extractUsername(servletRequest.getHeader("Authorization"));
//
//            User user = userRepository.findByEmail(email).get();
//
//            if (passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
//                user.setEmail(request.getEmail());
//                user.setPassword(passwordEncoder.encode(request.getNewPassword()));
//                repository.save(user);
//                response.setMessage("Credential changed");
//            } else {
//                response.setMessage("Password not matched");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            response.setError(true);
//            response.setMessage(e.getMessage());
//        }
//
//        return response;
//    }

    public String extractToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }
}
