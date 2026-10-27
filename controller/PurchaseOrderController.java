package com.inventory.controller;

import com.inventory.exception.ResourceNotFoundException;
import com.inventory.model.*;
import com.inventory.repository.*;
import com.inventory.service.InventoryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;

@RestController @RequestMapping("/api/purchase-orders")
public class PurchaseOrderController {
 private final PurchaseOrderRepository orders; private final SupplierRepository suppliers; private final ProductRepository products; private final InventoryService inventory;
 public PurchaseOrderController(PurchaseOrderRepository orders,SupplierRepository suppliers,ProductRepository products,InventoryService inventory){this.orders=orders;this.suppliers=suppliers;this.products=products;this.inventory=inventory;}
 public record ItemRequest(@NotNull Long productId,@Min(1) int quantity,@NotNull BigDecimal unitCost){}
 public record CreateOrderRequest(@NotNull Long supplierId,@NotNull List<@Valid ItemRequest> items){}
 @GetMapping public List<PurchaseOrder> all(){return orders.findAll();}
 @GetMapping("/{id}") public PurchaseOrder get(@PathVariable Long id){return orders.findById(id).orElseThrow(()->new ResourceNotFoundException("Purchase order not found: "+id));}
 @PostMapping @Transactional public PurchaseOrder create(@Valid @RequestBody CreateOrderRequest req){
   Supplier supplier=suppliers.findById(req.supplierId()).orElseThrow(()->new ResourceNotFoundException("Supplier not found"));
   if(req.items().isEmpty())throw new IllegalArgumentException("A purchase order must contain at least one item.");
   PurchaseOrder po=new PurchaseOrder();po.setSupplier(supplier);po.setStatus(PurchaseOrder.Status.DRAFT);
   for(ItemRequest item:req.items()){
     if(item.unitCost().signum()<0)throw new IllegalArgumentException("Unit cost cannot be negative.");
     Product p=products.findById(item.productId()).orElseThrow(()->new ResourceNotFoundException("Product not found: "+item.productId()));
     PurchaseOrderItem line=new PurchaseOrderItem();line.setPurchaseOrder(po);line.setProduct(p);line.setQuantity(item.quantity());line.setUnitCost(item.unitCost());po.getItems().add(line);
   }
   return orders.save(po);
 }
 @PatchMapping("/{id}/status") @Transactional public PurchaseOrder changeStatus(@PathVariable Long id,@RequestParam PurchaseOrder.Status status){
   PurchaseOrder po=get(id);
   if(po.getStatus()==PurchaseOrder.Status.RECEIVED && status!=PurchaseOrder.Status.RECEIVED)throw new IllegalArgumentException("A received purchase order cannot be reopened.");
   if(status==PurchaseOrder.Status.RECEIVED && po.getStatus()!=PurchaseOrder.Status.RECEIVED){
     for(PurchaseOrderItem item:po.getItems())inventory.adjustStock(item.getProduct().getId(),StockTransaction.TransactionType.STOCK_IN,item.getQuantity(),"Received purchase order #"+id);
   }
   po.setStatus(status);return orders.save(po);
 }
 @DeleteMapping("/{id}") public void delete(@PathVariable Long id){PurchaseOrder po=get(id);if(po.getStatus()==PurchaseOrder.Status.RECEIVED)throw new IllegalArgumentException("Received purchase orders cannot be deleted.");orders.delete(po);}
}
