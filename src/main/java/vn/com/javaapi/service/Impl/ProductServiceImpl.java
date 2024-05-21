package vn.com.javaapi.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.com.javaapi.bean.ResponseData;
import vn.com.javaapi.entity.Products;
import vn.com.javaapi.service.ProductService;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    public ResponseData addPro(Products products) {
        return null;
    }
}
