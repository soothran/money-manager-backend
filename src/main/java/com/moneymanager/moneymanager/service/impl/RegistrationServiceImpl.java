package com.moneymanager.moneymanager.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moneymanager.moneymanager.dao.UserDao;
import com.moneymanager.moneymanager.model.User;
import com.moneymanager.moneymanager.service.RegistrationService;

@Service
public class RegistrationServiceImpl implements RegistrationService {

	@Autowired
	UserDao userDao;

	@Override
	public void insertUser(User user) {
		userDao.insertUser(user);
	}

//	@Override
//	public void insertEmployees(List<User> employees) {
//		employeeDao.insertEmployees(employees);
//	}

//	public void getAllExpenses() {
//		List<User> employees = employeeDao.getAllEmployees();
//		for (User employee : employees) {
//			System.out.println(employee.toString());
//		}
//	}


}
