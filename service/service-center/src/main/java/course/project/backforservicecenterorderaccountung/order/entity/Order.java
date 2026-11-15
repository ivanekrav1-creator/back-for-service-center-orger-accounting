package course.project.backforservicecenterorderaccountung.order.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
import service.center.model.OrderStatus;

@Entity
@Table(name = "orders")
public class Order {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = "product_name", nullable = false)
  private String productName;

  @Column(nullable = false)
  private String brand;

  @Column(name = "owner_fio", nullable = false)
  private String ownerFio;

  @Column(name = "owner_phone", nullable = false)
  private String ownerPhone;

  @Column(name = "repair_cost", nullable = false)
  private BigDecimal repairCost;

  @Column(name = "acceptance_date", nullable = false)
  private LocalDate acceptanceDate;

  @Column(name = "planned_issue_date", nullable = false)
  private LocalDate plannedIssueDate;

  @Column(name = "issue_date")
  private LocalDate issueDate;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private OrderStatus status;

  public Order() {}

  public Order(
      String productName,
      String brand,
      String ownerFio,
      String ownerPhone,
      BigDecimal repairCost,
      LocalDate plannedIssueDate) {
    this.productName = productName;
    this.brand = brand;
    this.ownerFio = ownerFio;
    this.ownerPhone = ownerPhone;
    this.repairCost = repairCost;
    this.acceptanceDate = LocalDate.now();
    this.plannedIssueDate = plannedIssueDate;
    this.status = OrderStatus.IN_PROGRESS;
    this.issueDate = null;
  }

  public UUID getId() {
    return id;
  }

  public String getProductName() {
    return productName;
  }

  public String getBrand() {
    return brand;
  }

  public String getOwnerFio() {
    return ownerFio;
  }

  public String getOwnerPhone() {
    return ownerPhone;
  }

  public BigDecimal getRepairCost() {
    return repairCost;
  }

  public LocalDate getAcceptanceDate() {
    return acceptanceDate;
  }

  public LocalDate getPlannedIssueDate() {
    return plannedIssueDate;
  }

  public LocalDate getIssueDate() {
    return issueDate;
  }

  public OrderStatus getStatus() {
    return status;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public void setBrand(String brand) {
    this.brand = brand;
  }

  public void setOwnerFio(String ownerFio) {
    this.ownerFio = ownerFio;
  }

  public void setOwnerPhone(String ownerPhone) {
    this.ownerPhone = ownerPhone;
  }

  public void setRepairCost(BigDecimal repairCost) {
    this.repairCost = repairCost;
  }

  public void setAcceptanceDate(LocalDate acceptanceDate) {
    this.acceptanceDate = acceptanceDate;
  }

  public void setPlannedIssueDate(LocalDate plannedIssueDate) {
    this.plannedIssueDate = plannedIssueDate;
  }

  public void setIssueDate(LocalDate issueDate) {
    this.issueDate = issueDate;
  }

  public void complete(OrderStatus status) {
    this.status = OrderStatus.COMPLETED;
    this.issueDate = LocalDate.now();
  }
}
