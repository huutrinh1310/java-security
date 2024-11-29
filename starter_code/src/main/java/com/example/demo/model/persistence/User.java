package com.example.demo.model.persistence;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty
	@Column
	private Long id;
	
	@Column(nullable = false, unique = true)
	@JsonProperty
	private String username;

	@Column(nullable = false, length = 1000)
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id", referencedColumnName = "id")
	@JsonIgnore
    private Cart cart;

	public User (String username, String password) {
		this.username = username;
		this.password = password;
	}
}
