package com.demo.bookstore.crm;

import com.demo.bookstore.utils.BaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
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
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "identity", fetch = FetchType.LAZY)
    private User user;
    @OneToMany
    private Set<Role> roles = new HashSet<>();

    public Identity(String username, String password, String email, Date dob) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.dob = dob;
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
