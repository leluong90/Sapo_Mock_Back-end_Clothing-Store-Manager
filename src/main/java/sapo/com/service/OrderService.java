package sapo.com.service;

import sapo.com.model.dto.request.order.CreateOrderRequest;

public interface OrderService {
    String createOrder(CreateOrderRequest createOrderRequest);
}
