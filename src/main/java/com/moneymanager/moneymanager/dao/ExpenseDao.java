package com.moneymanager.moneymanager.dao;

import java.util.List;

import com.moneymanager.moneymanager.model.Expense;

public interface ExpenseDao {
	
Expense addExpense(Expense newExpense);

Expense updateExpense(Expense editExpense);

Boolean deleteExpense(Expense deleteExpense);

List<Expense> getAllExpenses(Expense allExp);
}
