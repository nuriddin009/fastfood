package uz.fastfood.dashboard.service;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import uz.fastfood.dashboard.dto.ProductCreateDTO;
import uz.fastfood.dashboard.dto.ProductDTO;
import uz.fastfood.dashboard.dto.ProductUpdateDTO;
import uz.fastfood.dashboard.entity.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product toEntity(ProductCreateDTO productDTO);

    void updateEntity(@MappingTarget Product product, ProductUpdateDTO updateDTO);

    ProductDTO getProductDTO(Product product);
}
