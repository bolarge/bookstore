package com.demo.bookstore.crm.controller;

import com.demo.bookstore.crm.datatransfer.UserRecord;
import com.demo.bookstore.crm.service.IdentityService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Tag(name = "Users", description = "User Resource")
@RequestMapping("/api/v1")
@RestController
public class UserController {
    private final IdentityService identityService;
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/users/{userId}")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserRecord userRecord, @PathVariable Long userId){
        var userResponse = identityService.createUser(userRecord, userId);
        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers(){
        return new ResponseEntity<>(identityService.fetchAllUsers(), HttpStatus.OK);
    }


/*
    @PutMapping("/rest/widget/{id}")
    public ResponseEntity<Widget> updateWidget(@RequestBody Widget widget, @PathVariable Long id, @RequestHeader(HttpHeaders.IF_MATCH) String ifMatch) {
        // Get the widget with the specified id
        Optional<Widget> existingWidget = widgetService.findById(id);
        if (!existingWidget.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        // Validate that the if-match header matches the widget's version
        if (!ifMatch.equalsIgnoreCase(Integer.toString(existingWidget.get().getVersion()))) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        // Update the widget
        widget.setId(id);
        widget = widgetService.save(widget);

        try {
            // Return a 200 response with the updated widget
            return ResponseEntity
                    .ok()
                    .eTag(Integer.toString(widget.getVersion()))
                    .location(new URI("/rest/widget/" + widget.getId()))
                    .body(widget);
        } catch (URISyntaxException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/rest/proper/widget/{id}")
    public ResponseEntity<Widget> updateWidgetProper(@RequestBody Widget widget, @PathVariable Long id, @RequestHeader("If-Match") Integer ifMatch) {
        Optional<Widget> existingWidget = widgetService.findById(id);
        if (existingWidget.isPresent()) {
            if (ifMatch.equals(existingWidget.get().getVersion())) {
                widget.setId(id);
                return ResponseEntity.ok().body(widgetService.save(widget));
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/rest/widget/{id}")
    public ResponseEntity deleteWidget(@PathVariable Long id) {
        widgetService.deleteById(id);
        return ResponseEntity.ok().build();
    }
*/
}
