package com.inventory.repository;
import com.inventory.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsBySku(String sku);
    List<Product> findByQuantityLessThanEqual(int quantity);
    List<Product> findByNameContainingIgnoreCase(String name);
}
