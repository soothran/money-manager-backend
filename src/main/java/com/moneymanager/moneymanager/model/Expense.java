package com.moneymanager.moneymanager.model;

import java.io.File;
import java.sql.Timestamp;

public class Expense {
	
	private String expenseId;
	private String userId;
	private Timestamp expenseTime;
	private String categoryId;
	private String title;
	private String amount;
	private String image;
	private String notes;
	private String status;
	private String categoryName;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Timestamp getExpenseTime() {
		return expenseTime;
	}
	public void setExpenseTime(Timestamp expenseTime) {
		this.expenseTime = expenseTime;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String imgUrl) {
		this.image = imgUrl;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getExpenseId() {
		return expenseId;
	}
	public void setExpenseId(String expenseId) {
		this.expenseId = expenseId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	

}
