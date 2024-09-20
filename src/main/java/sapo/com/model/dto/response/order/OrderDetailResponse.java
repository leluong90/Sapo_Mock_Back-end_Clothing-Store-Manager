package sapo.com.model.dto.response.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import sapo.com.model.entity.Order;
import sapo.com.model.entity.OrderDetail;
import sapo.com.repository.OrderDetailRepository;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderDetailResponse {

    private Long customerId;
    private List<OrderDetail> orderDetails;
    private String note;
    private String paymentType;
    private BigDecimal cashReceive;
    private BigDecimal cashRepay;
    private BigDecimal totalPayment;

    public OrderDetailResponse(Order order) {
        this.customerId = order.getCustomer().getId();
        this.note = order.getNote();
        this.paymentType = order.getPaymentType();
        this.cashReceive = order.getCashReceive();
        this.cashRepay = order.getCashRepay();
        this.totalPayment = order.getTotalPayment();
    }
}
