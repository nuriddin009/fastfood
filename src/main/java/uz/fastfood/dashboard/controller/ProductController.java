package uz.fastfood.dashboard.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.fastfood.dashboard.dto.response.ApiResponse;
import uz.fastfood.dashboard.dto.response.BaseResponse;
import uz.fastfood.dashboard.projection.ProductProjection;
import uz.fastfood.dashboard.service.ProductService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/product")
public class ProductController {

    private final ProductService productService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SUPER_ADMIN')")
    @PostMapping
    public HttpEntity<?> createProduct() {


        return ResponseEntity.ok("");
    }


    @GetMapping
    public ResponseEntity<ApiResponse> getProducts(
            @RequestParam(defaultValue = "asc") String priceSort,
            @RequestParam(defaultValue = "asc") String nameSort,
            @RequestParam(defaultValue = "", required = false) UUID categorySort,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "") String search
    ) {
        return ResponseEntity.ok(productService.getProducts(priceSort, nameSort, categorySort, page, search));
    }


}
