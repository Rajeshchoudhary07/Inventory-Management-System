package com.inventory.repository;
import com.inventory.model.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {}
