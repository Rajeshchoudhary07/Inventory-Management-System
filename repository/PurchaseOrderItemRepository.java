package com.inventory.repository;
import com.inventory.model.PurchaseOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
public interface PurchaseOrderItemRepository extends JpaRepository<PurchaseOrderItem, Long> {}
