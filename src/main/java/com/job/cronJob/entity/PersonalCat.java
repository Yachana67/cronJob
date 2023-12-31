package com.job.cronJob.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class PersonalCat {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private int policy;
	@Enumerated(EnumType.STRING)
	private InsuranceCategory category;
	public InsuranceCategory getCategory() {
		return category;
	}
	public void setCategory(InsuranceCategory category) {
		this.category = category;
	}
	private String name;
	private String email;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPolicy() {
		return policy;
	}
	public void setPolicy(int policy) {
		this.policy = policy;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
