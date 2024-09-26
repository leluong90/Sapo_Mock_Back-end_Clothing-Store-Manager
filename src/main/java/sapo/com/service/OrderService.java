package sapo.com.service;

import sapo.com.model.dto.request.order.CreateOrderRequest;
import sapo.com.model.dto.response.order.AllOrderResponse;
import sapo.com.model.dto.response.order.OrderDetailResponse;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface OrderService {
    String createOrder(CreateOrderRequest createOrderRequest);
    List<AllOrderResponse> getAllOrder(int page, int limit, String query, LocalDate startDate, LocalDate endDate);
    OrderDetailResponse getOrderDetail(Long orderId);
    int getNumberOfOrders(String query, LocalDate startDate, LocalDate endDate);
}
