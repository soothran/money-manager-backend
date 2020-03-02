package com.moneymanager.moneymanager.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.moneymanager.moneymanager.model.User;

@Service
public interface RegistrationService {
	void insertUser(User user);

}
