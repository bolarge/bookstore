package com.demo.bookstore.ordering.controller;

import com.demo.bookstore.ordering.ItemRequest;
import com.demo.bookstore.ordering.service.ShoppingCartServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@Tag(name = "ShoppingCart", description = "ShoppingCart Resource")
@RequestMapping("api/v1/shopping-cart")
@RestController
public class ShoppingCartController {

    private final ShoppingCartServiceImpl shoppingCartService;

    @PostMapping("/add")
    public ResponseEntity<?> addToShoppingCart(@Valid @RequestBody ItemRequest itemRequest){
        var requestResponse = shoppingCartService.addItemsToCart(itemRequest);
        return new ResponseEntity<>(requestResponse, HttpStatus.OK);
    }

    @PostMapping("/remove")
    public ResponseEntity<?> removeFromShoppingCart(@Valid @RequestBody ItemRequest itemRequest){
        var requestResponse = shoppingCartService.removeFromCart(itemRequest);
        return new ResponseEntity<>(requestResponse, HttpStatus.OK);
    }

    @GetMapping("/total")
    public ResponseEntity<?> calculateTotal(){
        var requestResponse = shoppingCartService.getTotalAmount();
        return new ResponseEntity<>(requestResponse, HttpStatus.OK);
    }

    @GetMapping("/check-out")
    public ResponseEntity<?> checkOutCart(){
        var requestResponse = shoppingCartService.getTotalAmount();
        return new ResponseEntity<>(requestResponse, HttpStatus.OK);
    }

}
