package vn.com.javaapi.service;

import vn.com.javaapi.bean.ProductsResponse;
import vn.com.javaapi.bean.ResponseData;
import vn.com.javaapi.dto.ProductDTO;
import vn.com.javaapi.entity.Products;

public interface ProductService {
    void addPro(ProductDTO products);

    ProductsResponse listProduct();

    ProductsResponse getProductById(Long id);
}
