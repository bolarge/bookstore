package com.demo.bookstore.crm;

import com.demo.bookstore.utils.BaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name="identities", uniqueConstraints = {
        @UniqueConstraint(columnNames = "username"),
        @UniqueConstraint(columnNames = "email")
})
public class Identity extends BaseEntity {
    private String username;
    private String password;
    private String email;
    private Date dob;
    @JsonBackReference
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "identity")
    private User user;

    public Identity(String username, String password, String email, Date dob) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.dob = dob;
    }

    public Identity(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public Identity(Long id, String username, String password, String email) {
        super(id);
        this.username = username;
        this.password = password;
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Identity identity = (Identity) o;
        return Objects.equals(getUsername(), identity.getUsername()) && Objects.equals(getEmail(), identity.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getUsername(), getEmail());
    }
}
