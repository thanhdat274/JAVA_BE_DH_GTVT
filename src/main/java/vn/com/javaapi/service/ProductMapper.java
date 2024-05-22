//package vn.com.javaapi.service;
//
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//import org.mapstruct.Mappings;
//import org.mapstruct.NullValuePropertyMappingStrategy;
//import org.springframework.stereotype.Component;
//import vn.com.javaapi.dto.ProductDTO;
//import vn.com.javaapi.entity.Products;
//
//
//@Component
//@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
//public interface ProductMapper {
//
//    @Mappings({
//        @Mapping(target = "id", ignore = true), // Bỏ qua id vì nó sẽ được tạo tự động
//        @Mapping(target = "name", source = "name"),
//        @Mapping(target = "type", source = "type"),
//        @Mapping(target = "brand", source = "brand"),
//        @Mapping(target = "description", source = "description"),
//        @Mapping(target = "battery", source = "battery"),
//        @Mapping(target = "cpu", source = "cpu"),
//        @Mapping(target = "createdAt", source = "createdAt"),
//        @Mapping(target = "updateAt", ignore = true), // Bỏ qua updateAt vì nó sẽ được cập nhật tự động
//        @Mapping(target = "operatingSystem", source = "operatingSystem"),
//        @Mapping(target = "ram", source = "ram"),
//        @Mapping(target = "screenReslution", source = "screenReslution"),
//        @Mapping(target = "screenSize", source = "screenSize"),
//        @Mapping(target = "status", source = "status"),
//        @Mapping(target = "storage", source = "storage"),
//        @Mapping(target = "thumbnail", source = "thumbnail"),
//        @Mapping(target = "image", source = "image"),
//        @Mapping(target = "weight", source = "weight"),
//        @Mapping(target = "price", source = "price"),
//        @Mapping(target = "salePrice", source = "salePrice"),
//        @Mapping(target = "quantity", source = "quantity"),
//        @Mapping(target = "productView", source = "productView")
//    })
//    Products toEntityProduct(ProductDTO productDTO);
//}
