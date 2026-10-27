package com.inventory.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import java.math.BigDecimal;

@Entity
@Table(name = "purchase_order_items")
public class PurchaseOrderItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(optional = false, fetch = FetchType.EAGER) @JoinColumn(name = "purchase_order_id") private PurchaseOrder purchaseOrder;
    @ManyToOne(optional = false, fetch = FetchType.EAGER) @JoinColumn(name = "product_id") private Product product;
    @Min(1) @Column(nullable = false) private int quantity;
    @Column(nullable = false, precision = 12, scale = 2) private BigDecimal unitCost;

    public PurchaseOrderItem() {}
    public Long getId(){return id;} public void setId(Long id){this.id=id;}
    public PurchaseOrder getPurchaseOrder(){return purchaseOrder;} public void setPurchaseOrder(PurchaseOrder purchaseOrder){this.purchaseOrder=purchaseOrder;}
    public Product getProduct(){return product;} public void setProduct(Product product){this.product=product;}
    public int getQuantity(){return quantity;} public void setQuantity(int quantity){this.quantity=quantity;}
    public BigDecimal getUnitCost(){return unitCost;} public void setUnitCost(BigDecimal unitCost){this.unitCost=unitCost;}
}
