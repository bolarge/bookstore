package com.demo.bookstore.inventory;

import com.demo.bookstore.utils.NamedEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

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
    @OneToOne
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
}
