package com.demo.bookstore.ordering.controller;

import com.demo.bookstore.ordering.ItemRequest;
import com.demo.bookstore.ordering.SalesOrder;
import com.demo.bookstore.ordering.service.ShoppingCartService;
import com.demo.bookstore.ordering.service.ShoppingCartServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@Tag(name = "ShoppingCart", description = "ShoppingCart Resource")
@RequestMapping("api/v1")
@RestController
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @GetMapping("/shopping-cart")
    public ResponseEntity<?> getShoppingCartItems(){
        return new ResponseEntity<>(shoppingCartService.showCartItems(), HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @PostMapping("/shopping-cart/add")
    public ResponseEntity<?> addToShoppingCart(@Valid @RequestBody ItemRequest itemRequest){
        return new ResponseEntity<>(shoppingCartService.addItemsToCart(itemRequest), HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @PostMapping("/shopping-cart/remove")
    public ResponseEntity<?> removeFromShoppingCart(@Valid @RequestBody ItemRequest itemRequest){
        return new ResponseEntity<>(shoppingCartService.removeFromCart(itemRequest), HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @GetMapping("/shopping-cart/check-out")
    public ResponseEntity<?> checkOutCart(){
        SalesOrder salesOrder = shoppingCartService.checkOutShoppingCart();
        return new ResponseEntity<>(salesOrder, HttpStatus.OK);
    }

}
