package com.demo.bookstore.crm;

import com.demo.bookstore.utils.BaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="users")
public class User extends BaseEntity {
	private String email;
	private String firstName;
	private String lastName;
	private String mobilePhone;
	@Enumerated(EnumType.STRING)
	private UserType userType;
	private boolean isEnabled = false;
	private boolean isAdmin = false;

	@JsonBackReference
	@OneToOne
	private Identity identity;
	@JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(  name = "user_roles",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();

	public User(String email, String firstName, String lastName, String mobilePhone, UserType userType) {
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.mobilePhone = mobilePhone;
		this.userType = userType;
	}

	public User(String firstName, String lastName, boolean isAdmin, Identity identity, Set<Role> roles) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.isAdmin = isAdmin;
		this.identity = identity;
		this.roles = roles;
	}

	public User(String firstName, String lastName, String email) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof User user)) return false;
		if (!super.equals(o)) return false;
		return getEmail().equals(user.getEmail()) && getFirstName().equals(user.getFirstName()) && getLastName().equals(user.getLastName());
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), getEmail(), getFirstName(), getLastName());
	}
}
