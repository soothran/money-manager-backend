package com.moneymanager.moneymanager.model;

import java.sql.Timestamp;

public class Income {
	private String incomeId;
	private String userId;
	private Timestamp incomeTime;
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
	public Timestamp getIncomeTime() {
		return incomeTime;
	}
	public void setIncomeTime(Timestamp incomeTime) {
		this.incomeTime = incomeTime;
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
	public String getIncomeId() {
		return incomeId;
	}
	public void setIncomeId(String incomeId) {
		this.incomeId = incomeId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	


}
