package vn.com.javaapi.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.com.javaapi.bean.CartResponse;
import vn.com.javaapi.bean.ResponseData;
import vn.com.javaapi.constant.BaseResponse;
import vn.com.javaapi.dto.CartsDTO;
import vn.com.javaapi.dto.ProductDTO;
import vn.com.javaapi.service.CartService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/carts")
public class CartsController {
    private final CartService cartService;
    @ExceptionHandler  // đây là hàm xử lý khi trong postman ko có dữ liệu vẫn bấm gửi
    public ResponseEntity<ResponseData> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        log.info("Request data not found: ", ex);
        ResponseData responseData = ResponseData.builder().code("99")
            .message("Data not found!")
            .build();
        return ResponseEntity.badRequest().body(responseData);
    }

    @PostMapping("/add")
    public BaseResponse addCart(CartsDTO request) {
        cartService.addCart(request);
        return BaseResponse.success();
    }
}
