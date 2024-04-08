package com.demo.bookstore.inventory;

import com.demo.bookstore.NamedEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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
    private String itemDescription;
    private int itemQty;
    @OneToOne
    private Book book;
    private InventoryStatus inventoryStatus = InventoryStatus.UNAVAILABLE;
}
