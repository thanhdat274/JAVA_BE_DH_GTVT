package vn.com.javaapi.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;
import vn.com.javaapi.bean.ResponseData;
import vn.com.javaapi.entity.Cart;
import vn.com.javaapi.service.CartService;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/carts")
public class CartController {

    @Autowired
    private CartService cartService;

    @ExceptionHandler  // đây là hàm xử lý khi trong postman ko có dữ liệu vẫn bấm gửi
    public ResponseEntity<ResponseData> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        log.info("Request data not found: ", ex);
        ResponseData responseData = ResponseData.builder().code("99")
            .message("Data not found!")
            .build();
        return ResponseEntity.badRequest().body(responseData);
    }

    // Endpoint để lấy danh sách giỏ hàng của một người dùng dựa trên user id
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Cart>> getCartsByUserId(@PathVariable Long userId) {
        List<Cart> carts = cartService.getCartsByUserId(userId);
        return new ResponseEntity<>(carts, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cart> getCartById(@PathVariable("id") Long id) {
        Cart cart = cartService.getCartById(id);
        if (cart != null) {
            return new ResponseEntity<>(cart, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Cart> addCart(@RequestBody Cart cart) {
        Cart newCart = cartService.saveCart(cart);
        return new ResponseEntity<>(newCart, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCart(@PathVariable("id") Long id) {
        cartService.deleteCart(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
