package com.inventory.controller;
import com.inventory.model.StockTransaction;
import com.inventory.repository.StockTransactionRepository;
import com.inventory.service.InventoryService;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
@RestController @Validated @RequestMapping("/api/stock")
public class StockController {
 private final InventoryService service; private final StockTransactionRepository repo;
 public StockController(InventoryService service,StockTransactionRepository repo){this.service=service;this.repo=repo;}
 public record StockRequest(@NotNull StockTransaction.TransactionType type,@Min(1) int quantity,String note){}
 @GetMapping("/transactions") public List<StockTransaction> transactions(){return repo.findAll();}
 @PostMapping("/{productId}/adjust") public StockTransaction adjust(@PathVariable Long productId,@RequestBody StockRequest request){return service.adjustStock(productId,request.type(),request.quantity(),request.note());}
 @GetMapping("/report") public Map<String,Object> report(){return service.report();}
 @GetMapping("/low-stock") public Object lowStock(){return service.report().get("lowStockProducts");}
}
