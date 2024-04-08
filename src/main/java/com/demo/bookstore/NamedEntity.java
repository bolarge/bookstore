package com.demo.bookstore;

import jakarta.persistence.MappedSuperclass;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@MappedSuperclass
public class NamedEntity extends BaseEntity {
    protected String itemName;
    protected String itemDescription;
    protected boolean status = false;

    public NamedEntity(String name){
        this.itemName = name;
    }

    public NamedEntity(String name, String description) {
        this(name);
        this.itemDescription = description;
    }

    public String getItemName() {
        return this.itemName;
    }

    public void setItemName(String name) {
        this.itemName = name;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String description) {
        this.itemDescription = description;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

}
