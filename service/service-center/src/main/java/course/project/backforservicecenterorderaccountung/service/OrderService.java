package course.project.backforservicecenterorderaccountung.service;

import course.project.backforservicecenterorderaccountung.exeption.BadRequestException;
import course.project.backforservicecenterorderaccountung.exeption.EntityNotFoundException;
import course.project.backforservicecenterorderaccountung.exeption.InvalidOrderException;
import course.project.backforservicecenterorderaccountung.order.entity.Order;
import course.project.backforservicecenterorderaccountung.order.repository.OrderRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.center.model.OrderCreateDto;
import service.center.model.OrderStatus;
import service.center.model.OrderUpdateDto;

@Service
public class OrderService {
  private final OrderRepository orderRepository;

  public OrderService(OrderRepository orderRepository) {
    this.orderRepository = orderRepository;
  }

  @Transactional
  public Order createOrder(OrderCreateDto dto) {
    if (dto.getPlannedIssueDate().isBefore(LocalDate.now())) {
      throw new InvalidOrderException("Плановая дата выдачи не может быть раньше даты приёма");
    }
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
        .orElseThrow(() -> new EntityNotFoundException("Заказ не найден: " + orderId));
  }

  @Transactional
  public void deleteOrder(UUID orderId) {
    if (!orderRepository.existsById(orderId)) {
      throw new EntityNotFoundException("Заказ не найден: " + orderId);
    }
    orderRepository.deleteById(orderId);
  }

  @Transactional
  public Order completeOrder(UUID orderId) {
    Order order =
        orderRepository
            .findById(orderId)
            .orElseThrow(() -> new EntityNotFoundException("Заказ не найден: " + orderId));
    if (order.getStatus() == OrderStatus.COMPLETED) {
      throw new BadRequestException("Заказ уже завершён");
    }
    order.complete();
    return orderRepository.save(order);
  }

  @Transactional
  public void updateOrder(UUID orderId, OrderUpdateDto orderUpdateDto) {
    Order order =
        orderRepository
            .findById(orderId)
            .orElseThrow(() -> new EntityNotFoundException("Заказ не найден: " + orderId));
    if (order.getStatus() == OrderStatus.COMPLETED) {
      throw new BadRequestException("Нельзя изменить завершённый заказ");
    }

    if (orderUpdateDto.getPlannedIssueDate() != null
        && orderUpdateDto.getPlannedIssueDate().isBefore(order.getAcceptanceDate())) {
      throw new BadRequestException("Плановая дата выдачи не может быть раньше даты приёмки");
    }
    order.update(orderUpdateDto);
    orderRepository.save(order);
  }

  public BigDecimal getIncome(LocalDate from, LocalDate to) {
    if (from.isAfter(to)) {
      throw new BadRequestException("Начальная дата не может быть позже конечной");
    }
    return orderRepository.calculateIncome(OrderStatus.COMPLETED, from, to);
  }
}
