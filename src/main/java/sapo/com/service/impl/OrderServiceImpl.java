package sapo.com.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sapo.com.model.dto.request.order.CreateOrderRequest;
import sapo.com.model.dto.request.order.CreateOrderDetailRequest;
import sapo.com.model.dto.response.order.AllOrderResponse;
import sapo.com.model.dto.response.order.OrderDetailResponse;
import sapo.com.model.entity.*;
import sapo.com.repository.*;
import sapo.com.service.OrderService;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VariantRepository variantRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    @Transactional
    public String createOrder(CreateOrderRequest createOrderRequest) {
        // Kiểm tra thông tin đầu vào
        Customer customer = customerRepository.findById(createOrderRequest.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Khách hàng không tồn tại"));
        User user = userRepository.findById(createOrderRequest.getCreatorId())
                .orElseThrow(() -> new RuntimeException("Người tạo đơn hàng không tồn tại"));
        if (createOrderRequest.getOrderLineItems().isEmpty()) {
            throw new RuntimeException("Đơn hàng không có sản phẩm");
        }

        // Tạo đơn hàng
        Order order = new Order();
        order.setCustomer(customer);
        order.setCreator(user);
        order.setTotalQuantity(createOrderRequest.getTotalQuantity());
        order.setTotalPayment(createOrderRequest.getTotalPayment());
        order.setCashReceive(createOrderRequest.getCashReceive());
        order.setCashRepay(createOrderRequest.getCashRepay());
        order.setPaymentType(createOrderRequest.getPaymentType());
        order.setNote(createOrderRequest.getNote());
        Order newOrder = orderRepository.save(order);

        // Tạo chi tiết đơn hàng
        final Set<CreateOrderDetailRequest> orderDetails = createOrderRequest.getOrderLineItems();
        orderDetails.forEach(createOrderDetailRequest -> {
            Variant variant = variantRepository.findById(createOrderDetailRequest.getVariantId())
                    .orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại"));
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(newOrder);
            orderDetail.setVariant(variant);
            orderDetail.setQuantity(createOrderDetailRequest.getQuantity());
            orderDetail.setSubTotal(createOrderDetailRequest.getSubTotal());
            orderDetailRepository.save(orderDetail);
        });

        return "Tạo đơn hàng thành công";
    }

    @Override
    public List<AllOrderResponse> getAllOrder(int page, int limit, String query, LocalDate startDate, LocalDate endDate) {
        List<Order> orders = orderRepository.findOrdersByDateAndCode(startDate, endDate, query);
        // Chuyển đổi danh sách đơn hàng sang danh sách response
        List<AllOrderResponse> allOrderResponseList = orders.stream().map(AllOrderResponse::new).toList();
        // Phân trang
        return allOrderResponseList.subList(Math.max((page - 1) * limit, 0), Math.min(page * limit, allOrderResponseList.size()));
    }

    @Override
    public OrderDetailResponse getOrderDetail(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Đơn hàng không tồn tại"));
        OrderDetailResponse orderDetailResponse = new OrderDetailResponse(order);
        // Lấy chi tiết đơn hàng
        orderDetailResponse.setOrderDetails(orderDetailRepository.findAllByOrderId(orderId));
        return orderDetailResponse;
    }
}
