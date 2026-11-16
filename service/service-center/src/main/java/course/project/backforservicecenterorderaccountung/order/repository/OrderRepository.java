package course.project.backforservicecenterorderaccountung.order.repository;

import course.project.backforservicecenterorderaccountung.order.entity.Order;
import java.math.BigDecimal;
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
      AND (cast(:dateFrom as LocalDate) IS NULL OR o.acceptanceDate >= :dateFrom)
      AND (cast(:dateTo as LocalDate) IS NULL OR o.acceptanceDate <= :dateTo)
    ORDER BY o.acceptanceDate DESC
    """)
  List<Order> findOrders(
      @Param("status") OrderStatus status,
      @Param("dateFrom") LocalDate dateFrom,
      @Param("dateTo") LocalDate dateTo);

  @Query(
      """
        SELECT COALESCE(SUM(o.repairCost), 0)
        FROM Order o
        WHERE o.status = :status
          AND o.issueDate BETWEEN :from AND :to
        """)
  BigDecimal calculateIncome(
      @Param("status") OrderStatus status,
      @Param("from") LocalDate from,
      @Param("to") LocalDate to);
}
