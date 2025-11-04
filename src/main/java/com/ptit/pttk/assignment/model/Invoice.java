package com.ptit.pttk.assignment.model;

import java.util.Date;

public class Invoice {
	private Integer id;
	private Integer customerId;
	private Date invoiceDate;
	private Double totalAmount;

	public Invoice(Integer id, Integer customerId, Date invoiceDate, Double totalAmount) {
		this.id = id;
		this.customerId = customerId;
		this.invoiceDate = invoiceDate;
		this.totalAmount = totalAmount;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}
}



