package com.inventory;

import com.inventory.model.Product;
import com.inventory.model.StockTransaction;
import com.inventory.repository.ProductRepository;
import com.inventory.repository.StockTransactionRepository;
import com.inventory.service.InventoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InventoryManagementApplicationTests {
    @Mock ProductRepository products;
    @Mock StockTransactionRepository transactions;
    @InjectMocks InventoryService service;

    @Test void stockInIncreasesQuantity() {
        Product p = new Product(); p.setId(1L); p.setQuantity(4); p.setUnitPrice(new BigDecimal("10.00"));
        when(products.findById(1L)).thenReturn(Optional.of(p));
        when(products.save(any(Product.class))).thenAnswer(i -> i.getArgument(0));
        when(transactions.save(any(StockTransaction.class))).thenAnswer(i -> i.getArgument(0));
        service.adjustStock(1L, StockTransaction.TransactionType.STOCK_IN, 3, "delivery");
        assertEquals(7, p.getQuantity());
        verify(transactions).save(any(StockTransaction.class));
    }

    @Test void stockOutRejectsInsufficientQuantity() {
        Product p = new Product(); p.setId(1L); p.setQuantity(2);
        when(products.findById(1L)).thenReturn(Optional.of(p));
        assertThrows(IllegalArgumentException.class,
            () -> service.adjustStock(1L, StockTransaction.TransactionType.STOCK_OUT, 3, "sale"));
        verify(products, never()).save(any());
    }
}
