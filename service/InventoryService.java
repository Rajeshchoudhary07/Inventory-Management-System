package com.inventory.service;

import com.inventory.exception.ResourceNotFoundException;
import com.inventory.model.*;
import com.inventory.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
public class InventoryService {
    private final ProductRepository products;
    private final StockTransactionRepository transactions;
    public InventoryService(ProductRepository products, StockTransactionRepository transactions) { this.products=products; this.transactions=transactions; }

    @Transactional
    public StockTransaction adjustStock(Long productId, StockTransaction.TransactionType type, int quantity, String note) {
        if (quantity < 1) throw new IllegalArgumentException("Quantity must be at least 1.");
        Product product = products.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found: " + productId));
        if (type == StockTransaction.TransactionType.STOCK_OUT && product.getQuantity() < quantity)
            throw new IllegalArgumentException("Insufficient stock. Available quantity: " + product.getQuantity());
        product.setQuantity(type == StockTransaction.TransactionType.STOCK_IN ? product.getQuantity()+quantity : product.getQuantity()-quantity);
        products.save(product);
        StockTransaction tx = new StockTransaction(); tx.setProduct(product); tx.setType(type); tx.setQuantity(quantity); tx.setNote(note);
        return transactions.save(tx);
    }

    public Map<String,Object> report() {
        List<Product> all = products.findAll();
        long units = all.stream().mapToLong(Product::getQuantity).sum();
        double value = all.stream().mapToDouble(p -> p.getUnitPrice().doubleValue() * p.getQuantity()).sum();
        List<Product> low = all.stream().filter(p -> p.getQuantity() <= p.getReorderLevel()).toList();
        Map<String,Object> result = new LinkedHashMap<>();
        result.put("productCount", all.size()); result.put("totalUnitsInStock", units);
        result.put("inventoryRetailValue", Math.round(value*100.0)/100.0); result.put("lowStockCount", low.size()); result.put("lowStockProducts", low);
        return result;
    }
}
