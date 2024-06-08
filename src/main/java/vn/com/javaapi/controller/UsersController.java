package vn.com.javaapi.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.com.javaapi.bean.ResponseData;
import vn.com.javaapi.bean.UserResponse;
import vn.com.javaapi.service.UserService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UsersController {
    private final UserService userService;

    @ExceptionHandler  // đây là hàm xử lý khi trong postman ko có dữ liệu vẫn bấm gửi
    public ResponseEntity<ResponseData> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        log.info("Request data not found: ", ex);
        ResponseData responseData = ResponseData.builder().code("99")
            .message("Data not found!")
            .build();
        return ResponseEntity.badRequest().body(responseData);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<UserResponse> addProduct(@PathVariable("id") Long id) {
        return new ResponseEntity<>(userService.getOneUser(id), HttpStatus.OK);
    }
}
