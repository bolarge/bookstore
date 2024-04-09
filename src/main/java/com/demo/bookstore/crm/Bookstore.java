package com.demo.bookstore.crm;

import com.demo.bookstore.utils.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "bookstore")
public class Bookstore extends BaseEntity {
    private String bookStoreName;
    private String website;
    private String email;

    //Architectural Relationships
    //Departments
    //Staff Users
    //Inventory
    //Purchase Orders
    //Sales Orders
}
