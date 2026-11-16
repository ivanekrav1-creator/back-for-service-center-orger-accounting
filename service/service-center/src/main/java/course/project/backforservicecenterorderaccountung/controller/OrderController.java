package course.project.backforservicecenterorderaccountung.controller;

import course.project.backforservicecenterorderaccountung.order.entity.Order;
import course.project.backforservicecenterorderaccountung.service.OrderService;
import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.jspecify.annotations.Nullable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import service.center.api.OrdersApi;
import service.center.model.IncomeDto;
import service.center.model.OrderCreateDto;
import service.center.model.OrderDto;
import service.center.model.OrderStatus;
import service.center.model.OrderUpdateDto;

@RestController
public class OrderController implements OrdersApi {
  private final OrderService orderService;

  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  @Override
  public ResponseEntity<OrderDto> completeOrder(UUID orderId) {
    Order order = orderService.completeOrder(orderId);
    return ResponseEntity.ok(mapToDto(order));
  }

  @Override
  public ResponseEntity<OrderDto> createOrder(OrderCreateDto orderCreateDto) {
    Order order = orderService.createOrder(orderCreateDto);
    OrderDto response = mapToDto(order);
    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(order.getId())
            .toUri();
    return ResponseEntity.created(location).body(response);
  }

  @Override
  public ResponseEntity<Void> deleteOrder(UUID orderId) {
    orderService.deleteOrder(orderId);
    return ResponseEntity.noContent().build();
  }

  @Override
  public ResponseEntity<IncomeDto> getIncome(LocalDate from, LocalDate to) {
    BigDecimal total = orderService.getIncome(from, to);
    IncomeDto response = new IncomeDto().from(from).to(to).total(total);
    return ResponseEntity.ok(response);
  }

  @Override
  public ResponseEntity<OrderDto> getOrderById(UUID orderId) {
    Order order = orderService.getOrderById(orderId);
    return ResponseEntity.ok(mapToDto(order));
  }

  @Override
  public ResponseEntity<List<OrderDto>> getOrders(
      @Nullable OrderStatus status, @Nullable LocalDate from, @Nullable LocalDate to) {
    List<OrderDto> orders =
        orderService.getOrders(from, to, status).stream().map(this::mapToDto).toList();
    return ResponseEntity.ok(orders);
  }

  @Override
  public ResponseEntity<List<OrderDto>> getUncompletedOrders() {
    return null;
  }

  @Override
  public ResponseEntity<Void> updateOrder(UUID orderId, OrderUpdateDto orderUpdateDto) {
    orderService.updateOrder(orderId, orderUpdateDto);
    return ResponseEntity.noContent().build();
  }

  private OrderDto mapToDto(Order order) {
    OrderDto dto =
        new OrderDto(
            order.getId(),
            order.getProductName(),
            order.getBrand(),
            order.getOwnerFio(),
            order.getOwnerPhone(),
            order.getRepairCost(),
            order.getAcceptanceDate(),
            order.getPlannedIssueDate(),
            order.getStatus());
    dto.setIssueDate(order.getIssueDate());
    return dto;
  }
}
