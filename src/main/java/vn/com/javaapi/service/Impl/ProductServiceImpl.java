package vn.com.javaapi.service.Impl;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.com.javaapi.constant.ProductContant;
import vn.com.javaapi.dto.ProductDTO;
import vn.com.javaapi.entity.Products;
import vn.com.javaapi.repository.ProductRepository;
//import vn.com.javaapi.service.ProductMapper;
import vn.com.javaapi.service.ProductService;

import java.sql.Timestamp;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final Gson gson;
    //private final ProductMapper productMapper;
    private final ProductRepository productRepository;

    @Override
    public void addPro(Products request) {
        var startTime = System.currentTimeMillis();
        log.info("Begin create product with request: {}.", gson.toJson(request));
        request.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        request.setStatus(ProductContant.ProductStatus.ACTIVE.getCode());

        log.info("Data products mapping: {}", request);
        productRepository.save(request);
        log.info("Created product successfully Time handler: {} ms.", (System.currentTimeMillis() - startTime));
    }
}
