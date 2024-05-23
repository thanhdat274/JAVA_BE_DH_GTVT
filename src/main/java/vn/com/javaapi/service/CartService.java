package vn.com.javaapi.service;

import vn.com.javaapi.entity.Cart;

import java.util.List;

public interface CartService {
    List<Cart> getCartsByUserId();

    List<Cart> getCartsByUserId(Long userId);

    Cart getCartById(Long id);
    Cart saveCart(Cart cart);
    void deleteCart(Long id);
}
