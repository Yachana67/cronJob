package com.job.cronJob.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Entity
public class Insurance {

	@Id
	private int policy;
	private String name;
	private String email;
	@Enumerated(EnumType.STRING)
	private InsuranceCategory category;
	public InsuranceCategory getCategory() {
		return category;
	}
	public void setCategory(InsuranceCategory category) {
		this.category = category;
	}
	@Override
	public String toString() {
		return "Insurance [policy=" + policy + ", name=" + name + ", email=" + email + ", category=" + category + "]";
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
