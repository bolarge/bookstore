package com.demo.bookstore.inventory;

import com.demo.bookstore.utils.NamedEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "inventories")
public class Inventory extends NamedEntity {
    private String purchaseOrderId;
    private int itemQty;
    @OneToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @ToString.Exclude
    private Book book;
    @Enumerated(EnumType.STRING)
    private InventoryStatus inventoryStatus;

    public Inventory(String itemName, String itemDescription, String purchaseOrderId, int quantity, Book newBook, InventoryStatus available) {
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.purchaseOrderId = purchaseOrderId;
        this.itemQty = quantity;
        this.book = newBook;
        this.inventoryStatus = available;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Inventory inventory)) return false;
        if (!super.equals(o)) return false;
        return getPurchaseOrderId().equals(inventory.getPurchaseOrderId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getPurchaseOrderId());
    }
}
