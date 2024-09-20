package sapo.com.controller.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sapo.com.model.dto.request.order.CreateOrderRequest;
import sapo.com.model.dto.response.ResponseObject;
import sapo.com.service.OrderService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@RestController
@RequestMapping("/v1/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/")
    public ResponseEntity<ResponseObject> getAllOrder(@RequestParam(value = "page", defaultValue = "0") int page,
                                                      @RequestParam(value = "limit", defaultValue = "10") int limit,
                                                      @RequestParam(value = "query", defaultValue = "") String query,
                                                      @RequestParam(value = "startDate") String startDate,
                                                      @RequestParam(value = "endDate") String endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            return ResponseEntity.ok(ResponseObject.builder().data(orderService.getAllOrder(page, limit, query, LocalDate.parse(startDate, formatter), LocalDate.parse(endDate, formatter).plusDays(1))).build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ResponseObject.builder().message(e.getMessage()).status(null).build());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getOrderDetail(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(ResponseObject.builder().data(orderService.getOrderDetail(id)).build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ResponseObject.builder().message(e.getMessage()).status(null).build());
        }
    }

    @PostMapping("/create")
    public ResponseEntity<String> createOrder(@RequestBody CreateOrderRequest createOrderRequest) {
        try {
            return ResponseEntity.ok(orderService.createOrder(createOrderRequest));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
