package uz.buxorooquv.dashboard.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import uz.buxorooquv.dashboard.auth.AuthenticationRequest;
import uz.buxorooquv.dashboard.auth.AuthenticationResponse;
import uz.buxorooquv.dashboard.auth.AuthenticationService;
import uz.buxorooquv.dashboard.auth.RegisterRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import uz.buxorooquv.dashboard.dto.BaseResponse;
import uz.buxorooquv.dashboard.dto.ChangePasswordRequest;
import uz.buxorooquv.dashboard.entity.Certificate;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @GetMapping
    public ResponseEntity<String> isValidToken(@RequestParam String token) {
        return ResponseEntity.ok(service.isValidAdminToken(token));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        service.refreshToken(request, response);
    }

    @Secured(value = "ROLE_ADMIN")
    @PostMapping("/change_password")
    public ResponseEntity<BaseResponse<?>> changePassword(
            @RequestBody ChangePasswordRequest request,
            HttpServletRequest servletRequest
    ) {
        BaseResponse<Certificate> response = new BaseResponse<>();
        response = service.changePassword(request, response,servletRequest);
        HttpStatus status = response.getError() ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK;
        return new ResponseEntity<>(response, status);
    }

}
