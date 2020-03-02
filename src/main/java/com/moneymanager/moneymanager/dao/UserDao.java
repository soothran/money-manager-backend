package com.moneymanager.moneymanager.dao;

import java.util.List;

import com.moneymanager.moneymanager.model.Budget;
import com.moneymanager.moneymanager.model.User;

public interface UserDao {
	void insertUser(User user);

	User authenticateUser(User user);
	
	Boolean addBudget(Budget budget);
	
	Budget getBudget(Budget budget);

	//User getUserById(int userId);
}
