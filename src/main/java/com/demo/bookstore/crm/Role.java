package com.demo.bookstore.crm;

import com.demo.bookstore.utils.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;
import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "roles")
public class Role extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private UserRole userRole;

    @ManyToMany(mappedBy = "roles")
    @ToString.Exclude
    Set<User> users;

    public Role(UserRole roleUser) {
        this.userRole = roleUser;
    }

    public Role(Long id, UserRole userRole) {
        super(id);
        this.userRole = userRole;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Role role)) return false;
        if (!super.equals(o)) return false;
        return getUserRole() == role.getUserRole();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getUserRole());
    }
}
