package com.demo.bookstore.ordering.repository;

import com.demo.bookstore.ordering.SalesOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalesOrderRepository extends JpaRepository<SalesOrder, Long> {
}
