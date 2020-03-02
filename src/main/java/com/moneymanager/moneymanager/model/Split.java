package com.moneymanager.moneymanager.model;

import java.io.File;
import java.sql.Timestamp;

public class Split {

	private int splitId;
	private String expenseId;
	private boolean isGroupExpense;
	private String groupId;
	private String image;
	private String comment;
	private Timestamp createdOn;
	private String status;
	private SplitDetails[] splitDetails;
	private String total;
	
	public int getSplitId() {
		return splitId;
	}
	public void setSplitId(int splitId) {
		this.splitId = splitId;
	}
	public String getExpenseId() {
		return expenseId;
	}
	public void setExpenseId(String expenseId) {
		this.expenseId = expenseId;
	}
	public boolean getIsGroupExpense() {
		return isGroupExpense;
	}
	public void setGroupExpense(boolean isGroupExpense) {
		this.isGroupExpense = isGroupExpense;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Timestamp getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public SplitDetails[] getSplitDetails() {
		return splitDetails;
	}
	public void setSplitDetails(SplitDetails[] splitDetails) {
		this.splitDetails = splitDetails;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	
	
}
