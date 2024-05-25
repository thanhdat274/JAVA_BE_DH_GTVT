package vn.com.javaapi.service.Impl;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.com.javaapi.constant.ProductContant;
import vn.com.javaapi.dto.CartsDTO;
import vn.com.javaapi.entity.Carts;
import vn.com.javaapi.entity.Products;
import vn.com.javaapi.repository.CartRepository;
import vn.com.javaapi.service.CartService;

import java.sql.Timestamp;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final Gson gson;
    private final CartRepository cartRepository;

    @Override
    public void addCart(CartsDTO cartsDTO) {
        var startTime = System.currentTimeMillis();
        log.info("Begin create cart with request: {}.", gson.toJson(cartsDTO));
        //Carts carts = productMapper.toEntityProduct(request);
        //log.info("Data products mapping: {}", products);
        //productRepository.save(products);
        log.info("Created product successfully Time handler: {} ms.", (System.currentTimeMillis() - startTime));
    }
}
