package com.moneymanager.moneymanager.model;

import java.io.File;
import java.sql.Timestamp;

public class BalanceDetails {

	private String detailId;
	private String splitId;
	private String expenseId;
	private String totalAmt;
	private boolean isGrpExpense;
	private String groupId;
	private String debtorId;
	private Timestamp createdOn;
	private String groupTitle;
	private String userName;
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

	public String getDebterId() {
		return debtorId;
	}

	public void setDebterId(String debtorId) {
		this.debtorId = debtorId;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getExpenseId() {
		return expenseId;
	}

	public void setExpenseId(String expenseId) {
		this.expenseId = expenseId;
	}

	public String getTotalAmt() {
		return totalAmt;
	}

	public void setTotalAmt(String totalAmt) {
		this.totalAmt = totalAmt;
	}

	public boolean getIsGrpExpense() {
		return isGrpExpense;
	}

	public void setGrpExpense(boolean isGrpExpense) {
		this.isGrpExpense = isGrpExpense;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public String getGroupTitle() {
		return groupTitle;
	}

	public void setGroupTitle(String groupTitle) {
		this.groupTitle = groupTitle;
	}
}
