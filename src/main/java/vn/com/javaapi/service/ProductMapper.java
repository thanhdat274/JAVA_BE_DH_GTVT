package vn.com.javaapi.service;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import vn.com.javaapi.dto.ProductDTO;
import vn.com.javaapi.entity.Products;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProductMapper {

     Products toEntityProduct(ProductDTO productDTO);
}
