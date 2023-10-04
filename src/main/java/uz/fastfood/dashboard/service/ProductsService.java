package uz.fastfood.dashboard.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import uz.fastfood.dashboard.dto.ProductCreateDTO;
import uz.fastfood.dashboard.dto.ProductDTO;
import uz.fastfood.dashboard.dto.ProductUpdateDTO;
import uz.fastfood.dashboard.dto.response.ApiResponse;
import uz.fastfood.dashboard.entity.Product;
import uz.fastfood.dashboard.mapper.ProductMapper;
import uz.fastfood.dashboard.filter.ProductFilter;
import uz.fastfood.dashboard.repository.ProductRepository;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ProductsService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductCreateDTO create(ProductCreateDTO createDTO) {
        Product product = productMapper.toEntity(createDTO);
        productRepository.save(product);
        return createDTO;
    }

    public ProductUpdateDTO update(ProductUpdateDTO updateDTO) {
        Product product = productRepository.findById(updateDTO.getId()).orElseThrow(() -> new EntityNotFoundException("Product not found"));
        productMapper.updateEntity(product, updateDTO);
        productRepository.save(product);
        return updateDTO;
    }

    public ProductDTO getProductById(UUID id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Product not found"));
        return productMapper.getProductDTO(product);
    }

    public Page<ProductDTO> getAllProducts(ProductFilter filter) {
        return productRepository.findALlByFilter(filter).map(productMapper::getProductDTO);
    }

    public Object deleteProduct(UUID productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new EntityNotFoundException("Product not found"));
        product.setDeleted(true);
        productRepository.save(product);
        return new ApiResponse(true, "Product deleted");
    }
}
