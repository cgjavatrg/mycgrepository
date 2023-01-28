package com.cg.model;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Product {
	
	@Id
	private int productId;
	@NotEmpty(message = "Product Name Can not be empty")
	private String productName;
	@Min(value = 1,message = "Quantity should be minimum 1")
	private int quantity;
	@DecimalMin(value="100.00",message="Price should be min 100.00")
	private double price;
	
	@JsonFormat(pattern="dd-MMM-yyyy")
	private Date expiryDate;
	
	public Product() {
		
	}
	
	public Product(int productId, String productName, int quantity, double price, Date expiryDate) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.quantity = quantity;
		this.price = price;
		this.expiryDate = expiryDate;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public Date getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}
	@Override
	public String toString() {
		return "Product [productId=" + productId + ", productName=" + productName + ", quantity=" + quantity
				+ ", price=" + price + ", expiryDate=" + expiryDate + "]";
	}
	
	
	

}
