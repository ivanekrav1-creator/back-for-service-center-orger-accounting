package course.project.backforservicecenterorderaccountung.service;

import course.project.backforservicecenterorderaccountung.exeption.EntityNotFoundException;
import course.project.backforservicecenterorderaccountung.order.entity.Order;
import course.project.backforservicecenterorderaccountung.order.repository.OrderRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.center.model.OrderCreateDto;
import service.center.model.OrderStatus;

@Service
public class OrderService {
  private final OrderRepository orderRepository;

  public OrderService(OrderRepository orderRepository) {
    this.orderRepository = orderRepository;
  }

  @Transactional
  public Order createOrder(OrderCreateDto dto) {
    Order order =
        new Order(
            dto.getProductName(),
            dto.getBrand(),
            dto.getOwnerFio(),
            dto.getOwnerPhone(),
            dto.getRepairCost(),
            dto.getPlannedIssueDate());
    return orderRepository.save(order);
  }

  @Transactional
  public List<Order> getOrders(LocalDate from, LocalDate to, OrderStatus status) {
    return orderRepository.findOrders(status, from, to);
  }

  @Transactional
  public Order getOrderById(UUID orderId) {
    return orderRepository
        .findById(orderId)
            .orElseThrow(() ->
                    new EntityNotFoundException(
                            "Заказ не найден: " + orderId
                    ));
  }
}
