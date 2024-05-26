package vn.com.javaapi.service;

import vn.com.javaapi.bean.CartResponse;
import vn.com.javaapi.bean.ResponseData;
import vn.com.javaapi.dto.CartsDTO;

import java.util.List;

public interface CartService {
    void addCart(CartsDTO cartsDTO);

    CartResponse listCarts(Long userId);

    void updateCart(CartsDTO cartsDTO);

    void deleteCart(Long id);

}
