package uz.fastfood.dashboard.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.fastfood.dashboard.dto.request.CategoryRequest;
import uz.fastfood.dashboard.dto.response.ApiResponse;
import uz.fastfood.dashboard.dto.response.BaseResponse;
import uz.fastfood.dashboard.service.CategoryService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/category")
public class CategoryController {


    private final CategoryService service;


    @GetMapping
    public ResponseEntity<?> findAll() {
        return null;
    }


    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @PostMapping("insert")
    public ResponseEntity<BaseResponse<?>> insert(
            @RequestBody @Valid CategoryRequest request
    ) {
        BaseResponse<?> response = new BaseResponse<>();
        response = service.createCategory(request, response);
        HttpStatus status = response.getError() ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.CREATED;
        return new ResponseEntity<>(response, status);
    }

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @PutMapping("update")
    public ResponseEntity<ApiResponse> update(@RequestBody @Valid CategoryRequest request) {
        return new ResponseEntity<>(service.update(request), HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @DeleteMapping
    public ResponseEntity<ApiResponse> delete(
            @RequestParam UUID id
    ) {
        return ResponseEntity.ok(service.delete(id));
    }

}
