package vn.com.javaapi.service;

import vn.com.javaapi.bean.ResponseData;
import vn.com.javaapi.entity.Products;

public interface ProductService {
    ResponseData addPro(Products products);
}
