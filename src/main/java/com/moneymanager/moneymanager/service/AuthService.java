package com.moneymanager.moneymanager.service;

import org.springframework.stereotype.Service;

import com.moneymanager.moneymanager.model.Budget;
import com.moneymanager.moneymanager.model.User;

@Service
public interface AuthService {
User authenticateUser(User user);

Boolean addBudget(Budget budget);

Budget getBudget(Budget budget);
}
