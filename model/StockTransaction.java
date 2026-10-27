package com.inventory.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "stock_transactions")
public class StockTransaction {
    public enum TransactionType { STOCK_IN, STOCK_OUT }
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(optional = false, fetch = FetchType.EAGER) @JoinColumn(name = "product_id") private Product product;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 20) private TransactionType type;
    @Min(1) @Column(nullable = false) private int quantity;
    @Column(length = 255) private String note;
    @Column(nullable = false) private LocalDateTime createdAt = LocalDateTime.now();

    public StockTransaction() {}
    public Long getId(){return id;} public void setId(Long id){this.id=id;}
    public Product getProduct(){return product;} public void setProduct(Product product){this.product=product;}
    public TransactionType getType(){return type;} public void setType(TransactionType type){this.type=type;}
    public int getQuantity(){return quantity;} public void setQuantity(int quantity){this.quantity=quantity;}
    public String getNote(){return note;} public void setNote(String note){this.note=note;}
    public LocalDateTime getCreatedAt(){return createdAt;} public void setCreatedAt(LocalDateTime createdAt){this.createdAt=createdAt;}
}
