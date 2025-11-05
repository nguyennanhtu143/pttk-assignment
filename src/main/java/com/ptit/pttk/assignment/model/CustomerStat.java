package com.ptit.pttk.assignment.model;

public class CustomerStat extends Customer {
	private Integer customerId;
	private String customerName;
	private String customerCode;
	private Integer purchasedCount;
	private Double totalSpent;

	public CustomerStat(Integer customerId, String customerName, String customerCode, Integer purchasedCount, Double totalSpent) {
		super();
		this.customerId = customerId;
		this.customerName = customerName;
		this.customerCode = customerCode;
		this.purchasedCount = purchasedCount;
		this.totalSpent = totalSpent;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public Integer getPurchasedCount() {
		return purchasedCount;
	}

	public void setPurchasedCount(Integer purchasedCount) {
		this.purchasedCount = purchasedCount;
	}

	public Double getTotalSpent() {
		return totalSpent;
	}

	public void setTotalSpent(Double totalSpent) {
		this.totalSpent = totalSpent;
	}
}
