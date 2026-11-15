package course.project.backforservicecenterorderaccountung.order.repository;

import course.project.backforservicecenterorderaccountung.order.entity.Order;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import service.center.model.OrderStatus;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
  @Query(
      """
    SELECT o
    FROM Order o
    WHERE (:status IS NULL OR o.status = :status)
      AND (:from IS NULL OR o.acceptanceDate >= :from)
      AND (:to IS NULL OR o.acceptanceDate <= :to)
    ORDER BY o.acceptanceDate DESC
    """)
  List<Order> findOrders(
      @Param("status") OrderStatus status,
      @Param("from") LocalDate from,
      @Param("to") LocalDate to);
}
