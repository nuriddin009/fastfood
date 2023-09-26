package uz.fastfood.dashboard.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.fastfood.dashboard.dto.response.ApiResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/user")
public class UserController {

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SUPER_ADMIN')")
    @GetMapping
    public ResponseEntity<ApiResponse> findAll(
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "asc", required = false) String nameSort,
            @RequestParam(defaultValue = "asc", required = false) String orderSort,
            @RequestParam(defaultValue = "", required = false) Boolean activeSort
    ) {
        return ResponseEntity.ok(new ApiResponse());
    }


}
