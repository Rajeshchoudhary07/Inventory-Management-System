package com.inventory.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "purchase_orders")
public class PurchaseOrder {
    public enum Status { DRAFT, ORDERED, RECEIVED, CANCELLED }
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(optional = false, fetch = FetchType.EAGER) @JoinColumn(name = "supplier_id") private Supplier supplier;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 20) private Status status = Status.DRAFT;
    @Column(nullable = false) private LocalDateTime createdAt = LocalDateTime.now();
    @OneToMany(mappedBy = "purchaseOrder", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<PurchaseOrderItem> items = new ArrayList<>();

    public PurchaseOrder() {}
    public Long getId(){return id;} public void setId(Long id){this.id=id;}
    public Supplier getSupplier(){return supplier;} public void setSupplier(Supplier supplier){this.supplier=supplier;}
    public Status getStatus(){return status;} public void setStatus(Status status){this.status=status;}
    public LocalDateTime getCreatedAt(){return createdAt;} public void setCreatedAt(LocalDateTime createdAt){this.createdAt=createdAt;}
    public List<PurchaseOrderItem> getItems(){return items;} public void setItems(List<PurchaseOrderItem> items){this.items=items;}
}
