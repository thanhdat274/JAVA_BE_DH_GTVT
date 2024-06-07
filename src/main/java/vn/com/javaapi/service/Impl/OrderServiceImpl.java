package vn.com.javaapi.service.Impl;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.com.javaapi.bean.OrderDetailReq;
import vn.com.javaapi.bean.OrderRequest;
import vn.com.javaapi.constant.OrderContact;
import vn.com.javaapi.entity.OrderDetails;
import vn.com.javaapi.entity.Orders;
import vn.com.javaapi.repository.OrderDetailRepository;
import vn.com.javaapi.repository.OrderRepository;
import vn.com.javaapi.service.OrderService;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final Gson gson;
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;

    @Override
    public void addOrder(OrderRequest request) {
        var startTime = System.currentTimeMillis();
        log.info("Begin create order with request: {}.", gson.toJson(request));
        Orders orders = Orders.builder()
            .userId(request.getUserId())
            .orderDate(new Timestamp(System.currentTimeMillis()))
            .phone(request.getPhone())
            .address(request.getAddress())
            .totalAmount(request.getTotalAmount())
            .orderStatus(OrderContact.OrderStatus.ORDER_PLACED.getMessage())
            .notes(request.getNotes())
            .build();
        orderRepository.save(orders);
        Long id = orders.getId();
        request.getOrderDetail().forEach(orderDetailReq -> {
            OrderDetails orderDetails = OrderDetails.builder()
                .orderId(id)
                .productId(orderDetailReq.getProductId())
                .quantity(orderDetailReq.getQuantity())
                .unitPrice(orderDetailReq.getUnitPrice())
                .build();
            orderDetailRepository.save(orderDetails);
        });
        log.info("Created order successfully Time handler: {} ms.",
            (System.currentTimeMillis() - startTime));
    }
}
