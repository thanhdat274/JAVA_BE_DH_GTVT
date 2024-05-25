package vn.com.javaapi.service;

import vn.com.javaapi.bean.ResponseData;
import vn.com.javaapi.dto.CartsDTO;

public interface CartService {
    void addCart(CartsDTO cartsDTO);
}
