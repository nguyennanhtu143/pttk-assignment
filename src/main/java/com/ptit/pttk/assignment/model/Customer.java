package com.ptit.pttk.assignment.model;

public class Customer {
	private Integer memberId;
	private String customerCode;

	public Customer() {
	}

	public Customer(Integer memberId, String customerCode) {
		this.memberId = memberId;
		this.customerCode = customerCode;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
}
