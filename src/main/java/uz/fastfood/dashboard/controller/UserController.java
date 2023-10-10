package uz.fastfood.dashboard.controller;

import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.fastfood.dashboard.dto.response.ApiResponse;
import uz.fastfood.dashboard.entity.User;
import uz.fastfood.dashboard.entity.enums.Status;
import uz.fastfood.dashboard.filter.UserFilter;
import uz.fastfood.dashboard.service.UserService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/user")
public class UserController {
    private final UserService service;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SUPER_ADMIN')")
    @GetMapping
    public ResponseEntity<ApiResponse> findAll(
            @ParameterObject UserFilter filter
    ) {
        return ResponseEntity.ok(service.getCustomers(filter));
    }


    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SUPER_ADMIN')")
    @PatchMapping
    public ResponseEntity<ApiResponse> changeUserStatus(
            @RequestParam UUID userId,
            @RequestParam Status status
    ) {
        return ResponseEntity.ok(service.changeUserStatus(userId, status));
    }


    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN')")
    @PatchMapping("change_role")
    public ResponseEntity<?> changeUserRole() {

        service.changeUserRole();


        return ResponseEntity.ok(new User());
    }


}
