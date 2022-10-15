package com.shubnikofff.eshop.customer.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Table(name = "customers")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CustomerView {

	@NotNull
	@Id
	@Column(unique = true, nullable = false)
	private UUID id;

	@Column(nullable = false)
	private String name;

	@Setter
	@Column(nullable = false)
	private boolean isActive;
}
