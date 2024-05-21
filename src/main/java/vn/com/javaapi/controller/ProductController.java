package vn.com.javaapi.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import vn.com.javaapi.bean.AuthResponse;
import vn.com.javaapi.bean.ResponseData;
import vn.com.javaapi.entity.Products;
import vn.com.javaapi.entity.Users;
import vn.com.javaapi.service.ProductService;

@Slf4j
@RestController("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @ExceptionHandler  // đây là hàm xử lý khi trong postman ko có dữ liệu vẫn bấm gửi
    public ResponseEntity<ResponseData> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        log.info("Request data not found: ", ex);
        ResponseData responseData = ResponseData.builder().code("99")
            .message("Data not found!")
            .build();
        return ResponseEntity.badRequest().body(responseData);
    }

    @PostMapping("/add")
    public ResponseEntity<ResponseData> signup(@RequestBody Products products) {
        ResponseData responseData = productService.addPro(products);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }
}
