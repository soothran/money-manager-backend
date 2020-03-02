package com.moneymanager.moneymanager.model;

import java.io.File;
import java.sql.Timestamp;

public class SplitDetails {

	private String detailId;
	private String splitId;
	private String userId;
	private String spent;
	private String amount;
	private String owe;
	private String oweTo;
	private String status;
	
	public String getDetailId() {
		return detailId;
	}
	public void setDetailId(String detailId) {
		this.detailId = detailId;
	}
	public String getSplitId() {
		return splitId;
	}
	public void setSplitId(String splitId) {
		this.splitId = splitId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getSpent() {
		return spent;
	}
	public void setSpent(String spent) {
		this.spent = spent;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getOwe() {
		return owe;
	}
	public void setOwe(String owe) {
		this.owe = owe;
	}
	public String getOweTo() {
		return oweTo;
	}
	public void setOweTo(String oweTo) {
		this.oweTo = oweTo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
