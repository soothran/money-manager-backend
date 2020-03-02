package com.moneymanager.moneymanager.service.impl;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moneymanager.moneymanager.dao.UserDao;
import com.moneymanager.moneymanager.model.Budget;
import com.moneymanager.moneymanager.model.Income;
import com.moneymanager.moneymanager.model.User;
import com.moneymanager.moneymanager.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {
	@Autowired
	UserDao userDao;

	@Override
	public User authenticateUser(User user) {
		User loginUser = userDao.authenticateUser(user);
		if (loginUser.getStatus() == null || loginUser.getStatus() == "INVALID") {
			loginUser.setStatus("INVALID");
		}
		return loginUser;
	}

	@Override
	public Boolean addBudget(Budget budget) {
		Date dt = new Date();
		budget.setCreatedOn(new Timestamp(dt.getTime()));
		return userDao.addBudget(budget);
	}
	
	@Override
	public Budget getBudget(Budget budget) {
		return userDao.getBudget(budget);
	}
}
