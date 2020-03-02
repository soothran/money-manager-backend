package com.moneymanager.moneymanager.model;

import java.sql.Timestamp;
import java.util.Date;

public class Budget {

	private String budgetId;
	private String userId;
	private Timestamp createdOn;
	private Date period;
	private String amount;
	private String status;

	public String getBudgetId() {
		return budgetId;
	}

	public void setBudgetId(String budgetId) {
		this.budgetId = budgetId;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public Date getPeriod() {
		return period;
	}

	public void setPeriod(Timestamp period) {
		this.period = period;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
