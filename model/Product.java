package com.inventory.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Entity
@Table(name = "products", uniqueConstraints = @UniqueConstraint(columnNames = "sku"))
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @NotBlank @Column(nullable = false, unique = true, length = 50) private String sku;
    @NotBlank @Column(nullable = false, length = 150) private String name;
    @Column(length = 500) private String description;
    @NotNull @DecimalMin("0.00") @Column(nullable = false, precision = 12, scale = 2) private BigDecimal unitPrice;
    @Min(0) @Column(nullable = false) private int quantity;
    @Min(0) @Column(nullable = false) private int reorderLevel = 5;
    @ManyToOne(fetch = FetchType.EAGER) @JoinColumn(name = "category_id") private Category category;
    @ManyToOne(fetch = FetchType.EAGER) @JoinColumn(name = "supplier_id") private Supplier supplier;

    public Product() {}
    public Long getId(){return id;} public void setId(Long id){this.id=id;}
    public String getSku(){return sku;} public void setSku(String sku){this.sku=sku;}
    public String getName(){return name;} public void setName(String name){this.name=name;}
    public String getDescription(){return description;} public void setDescription(String description){this.description=description;}
    public BigDecimal getUnitPrice(){return unitPrice;} public void setUnitPrice(BigDecimal unitPrice){this.unitPrice=unitPrice;}
    public int getQuantity(){return quantity;} public void setQuantity(int quantity){this.quantity=quantity;}
    public int getReorderLevel(){return reorderLevel;} public void setReorderLevel(int reorderLevel){this.reorderLevel=reorderLevel;}
    public Category getCategory(){return category;} public void setCategory(Category category){this.category=category;}
    public Supplier getSupplier(){return supplier;} public void setSupplier(Supplier supplier){this.supplier=supplier;}
}
