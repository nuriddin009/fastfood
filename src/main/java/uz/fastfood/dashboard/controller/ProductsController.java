package uz.fastfood.dashboard.controller;

import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.fastfood.dashboard.dto.ProductCreateDTO;
import uz.fastfood.dashboard.dto.ProductDTO;
import uz.fastfood.dashboard.dto.ProductUpdateDTO;
import uz.fastfood.dashboard.filter.ProductFilter;
import uz.fastfood.dashboard.service.ProductsService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/products")
public class ProductsController {
    private final ProductsService productService;
//    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN')")
    @PostMapping
    public ResponseEntity<ProductCreateDTO> createProduct(@RequestBody ProductCreateDTO createDTO) {
        return ResponseEntity.ok(productService.create(createDTO));
    }

//    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN')")
    @PutMapping
    public ResponseEntity<ProductUpdateDTO> updateProduct(@RequestBody ProductUpdateDTO updateDTO) {
        return ResponseEntity.ok(productService.update(updateDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable UUID id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @GetMapping
    public ResponseEntity<Page<ProductDTO>> getAllProducts(@ParameterObject ProductFilter filter) {
        return ResponseEntity.ok(productService.getAllProducts(filter));
    }

//    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN')")
    @DeleteMapping("delete")
    public ResponseEntity<?> deleteProduct(@RequestParam UUID id){
        return ResponseEntity.ok(productService.deleteProduct(id));
    }
}
