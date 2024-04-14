package com.demo.bookstore.ordering.dataaccess;

import com.demo.bookstore.ordering.SalesOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalesOrderRepository extends JpaRepository<SalesOrder, Long> {
}
