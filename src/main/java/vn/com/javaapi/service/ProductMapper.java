package vn.com.javaapi.service;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import vn.com.javaapi.dto.ProductDTO;
import vn.com.javaapi.entity.Products;


@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProductMapper {

    @Mapping(target = "id", source = "product.id")
    @Mapping(target = "name", source = "product.name")
    @Mapping(target = "type", source = "product.type")
    @Mapping(target = "brand", source = "product.brand")
    @Mapping(target = "description", source = "product.description")
    @Mapping(target = "battery", source = "product.battery")
    @Mapping(target = "cpu", source = "product.cpu")
    @Mapping(target = "createdAt", source = "product.createdAt")
    @Mapping(target = "updateAt", source = "product.updatedAt")
    @Mapping(target = "operatingSystem", source = "product.operatingSystem")
    @Mapping(target = "ram", source = "product.ram")
    @Mapping(target = "screenReslution", source = "product.screenReslution")
    @Mapping(target = "screenSize", source = "product.screenSize")
    @Mapping(target = "status", source = "product.status")
    @Mapping(target = "storage", source = "product.storage")
    @Mapping(target = "thumbnail", source = "product.thumbnail")
    @Mapping(target = "image", source = "product.image")
    @Mapping(target = "weight", source = "product.weight")
    @Mapping(target = "price", source = "product.price")
    @Mapping(target = "salePrice", source = "product.salePrice")
    @Mapping(target = "quantity", source = "product.quantity")
    @Mapping(target = "productView", source = "product.productView")
    ProductDTO toDTO(Products products);

    Products toEntityProduct(ProductDTO productDTO);
}
