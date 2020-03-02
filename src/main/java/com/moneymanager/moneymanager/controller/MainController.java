package com.moneymanager.moneymanager.controller;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.*;
import com.cloudinary.utils.ObjectUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moneymanager.moneymanager.model.Balance;
import com.moneymanager.moneymanager.model.BalanceDetails;
import com.moneymanager.moneymanager.model.Budget;
import com.moneymanager.moneymanager.model.Category;
import com.moneymanager.moneymanager.model.Expense;
import com.moneymanager.moneymanager.model.Group;
import com.moneymanager.moneymanager.model.Income;
import com.moneymanager.moneymanager.model.Split;
import com.moneymanager.moneymanager.model.User;
import com.moneymanager.moneymanager.service.AuthService;
import com.moneymanager.moneymanager.service.MainService;
import com.moneymanager.moneymanager.service.RegistrationService;

@Controller
public class MainController {

	@Autowired
	RegistrationService registrationService;

	@Autowired
	AuthService authService;

	@Autowired
	MainService mainService;

	@RequestMapping(method = RequestMethod.POST, value = "/moneymanager/register/user")
	@ResponseBody
	User regUser(@RequestBody User newUser) {
		User user = new User();
		user.setUserName(newUser.getUserName());
		user.setEmailId(newUser.getEmailId());
		user.setContactNumber(newUser.getContactNumber());
		user.setPassword(newUser.getPassword());
		Date date = new Date();
		user.setRegDate(new Timestamp(date.getTime()));
		user.setStatus("ACTIVE");
		registrationService.insertUser(user);
		return user;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/moneymanager/auth/user")
	@ResponseBody
	User authenticate(@RequestBody User authData) {
		User user = new User();
		user.setUserName(authData.getUserName());
		user.setPassword(authData.getPassword());
		User loginResponse = authService.authenticateUser(user);
		return loginResponse;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/moneymanager/expense/add")
	@ResponseBody
	ResponseEntity addExpense(@RequestParam("file") MultipartFile file, @RequestParam String data) {
		ObjectMapper mapper = new ObjectMapper();
		Expense newExpense = new Expense();
		String imgUrl = getUploadURL(file);
		try {
			Expense getExpense = mapper.readValue(data, Expense.class);
			newExpense.setUserId(getExpense.getUserId());
			Date expDate = new Date();
			newExpense.setExpenseTime(new Timestamp(expDate.getTime()));
			newExpense.setCategoryId(getExpense.getCategoryId());
			newExpense.setTitle(getExpense.getTitle());
			newExpense.setAmount(getExpense.getAmount());
			newExpense.setImage(imgUrl);
			newExpense.setNotes(getExpense.getNotes());
			newExpense.setStatus("new");

		} catch (IOException e) {

			e.printStackTrace();
		}

		Expense addedExpense = mainService.addExpense(newExpense);
		// Expense addedExpense = new Expense();
		return new ResponseEntity(addedExpense, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/moneymanager/income/add")
	@ResponseBody
	ResponseEntity addIncome(@RequestParam("file") MultipartFile file, @RequestParam String data) {
		ObjectMapper mapper = new ObjectMapper();
		Income newIncome = new Income();
		String imgUrl = getUploadURL(file);
		try {
			Income getIncome = mapper.readValue(data, Income.class);
			newIncome.setUserId(getIncome.getUserId());
			Date incDate = new Date();
			newIncome.setIncomeTime(new Timestamp(incDate.getTime()));
			newIncome.setCategoryId(getIncome.getCategoryId());
			newIncome.setTitle(getIncome.getTitle());
			newIncome.setAmount(getIncome.getAmount());
			newIncome.setImage(imgUrl);
			newIncome.setNotes(getIncome.getNotes());
			newIncome.setStatus("new");

		} catch (IOException e) {

			e.printStackTrace();
		}

		Income addedIncome = mainService.addIncome(newIncome);
		// Expense addedExpense = new Expense();
		return new ResponseEntity(addedIncome, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/moneymanager/expense/update")
	@ResponseBody
	ResponseEntity updateExpense(@RequestParam("file") MultipartFile file, @RequestParam String data) {
		ObjectMapper mapper = new ObjectMapper();
		String imgUrl = getUploadURL(file);
		Expense getExpense = new Expense();
		try {
			getExpense = mapper.readValue(data, Expense.class);
			Date expDate = new Date();
			getExpense.setExpenseTime(new Timestamp(expDate.getTime()));
			getExpense.setImage(imgUrl);

		} catch (IOException e) {

			e.printStackTrace();
		}
		Expense updatedExpense = mainService.updateExpense(getExpense);

		return new ResponseEntity(updatedExpense, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/moneymanager/income/update")
	@ResponseBody
	ResponseEntity updateIncome(@RequestParam("file") MultipartFile file, @RequestParam String data) {
		ObjectMapper mapper = new ObjectMapper();
		String imgUrl = getUploadURL(file);
		Income getIncome = new Income();
		try {
			getIncome = mapper.readValue(data, Income.class);
			Date incDate = new Date();
			getIncome.setIncomeTime(new Timestamp(incDate.getTime()));
			getIncome.setImage(imgUrl);

		} catch (IOException e) {

			e.printStackTrace();
		}

		Income updatedIncome = mainService.updateIncome(getIncome);

		return new ResponseEntity(updatedIncome, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/moneymanager/expense/delete")
	@ResponseBody
	ResponseEntity deleteExpense(@RequestBody Expense getExpense) {
		Expense delExp = new Expense();
		delExp.setExpenseId(getExpense.getExpenseId());
		delExp.setStatus("deleted");
		Boolean deleteStatus = mainService.deleteExpense(delExp);

		return new ResponseEntity(deleteStatus, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/moneymanager/income/delete")
	@ResponseBody
	ResponseEntity deleteIncome(@RequestBody Income getIncome) {
		getIncome.setStatus("deleted");
		Boolean deleteStatus = mainService.deleteIncome(getIncome);

		return new ResponseEntity(deleteStatus, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/moneymanager/expense/getallexpenses")
	@ResponseBody
	ResponseEntity getExpensesById(@RequestBody Expense getUID) {
		Expense allExp = new Expense();
		allExp.setUserId(getUID.getUserId());
		List<Expense> allExpenses = mainService.getAllExpenses(allExp);

		return new ResponseEntity(allExpenses, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/moneymanager/income/getallincomes")
	@ResponseBody
	ResponseEntity getIncomesById(@RequestBody Income getUID) {
		List<Income> allIncomes = mainService.getAllIncomes(getUID);

		return new ResponseEntity(allIncomes, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/moneymanager/category/getcategories")
	@ResponseBody
	ResponseEntity getCategories(@RequestBody Category userCat) {

		List<Category> categories = mainService.getCategories(userCat);

		return new ResponseEntity(categories, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/moneymanager/community/searchfriend")
	@ResponseBody
	ResponseEntity searchFriend(@RequestBody User searchData) {
		String email = searchData.getEmailId();
		String userId = String.valueOf(searchData.getUserId());
		User friend = mainService.searchFriend(email, userId);

		return new ResponseEntity(friend, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/moneymanager/category/add")
	@ResponseBody
	ResponseEntity addCategory(@RequestBody Category categoryDetails) {

		Boolean isCategoryAdded = mainService.addCategory(categoryDetails);

		return new ResponseEntity(isCategoryAdded, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/moneymanager/budget/add")
	@ResponseBody
	ResponseEntity addBudget(@RequestBody Budget budget) {

		Boolean isAdded = authService.addBudget(budget);

		return new ResponseEntity(isAdded, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/moneymanager/budget/get")
	@ResponseBody
	ResponseEntity getBudget(@RequestBody Budget budget) {

		Budget fetchData = authService.getBudget(budget);

		return new ResponseEntity(fetchData, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/moneymanager/community/addfriend")
	@ResponseBody
	ResponseEntity addFriend(@RequestParam("userId") String userId, @RequestParam("friendId") String friendId) {

		Boolean isFriendAdded = mainService.addFriend(userId, friendId);

		return new ResponseEntity(isFriendAdded, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/moneymanager/community/getfriends")
	@ResponseBody
	ResponseEntity getFriends(@RequestBody User user) {
		String userId = String.valueOf(user.getUserId());
		List<User> friends = mainService.getFriends(userId);

		return new ResponseEntity(friends, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/moneymanager/community/getgroups")
	@ResponseBody
	ResponseEntity getGroups(@RequestBody User user) {
		String userId = String.valueOf(user.getUserId());
		List<Group> groups = mainService.getGroups(userId);

		return new ResponseEntity(groups, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/moneymanager/community/addgroup")
	@ResponseBody
	ResponseEntity addFriend(@RequestBody Group newGrp) {

		Boolean isGrpAdded = mainService.addGrp(newGrp);

		return new ResponseEntity(isGrpAdded, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/moneymanager/community/getgrpdetails")
	@ResponseBody
	ResponseEntity getGroupDetails(@RequestBody Group grpId) {
		String groupId = String.valueOf(grpId.getGroupId());
		Group groupDetails = mainService.getGroupDetails(groupId);

		return new ResponseEntity(groupDetails, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/moneymanager/split/splitwithfriend")
	@ResponseBody
	ResponseEntity splitWithFriend(@RequestBody Split newSplit) {

		Boolean isGrpAdded = mainService.splitWithFriend(newSplit);

		return new ResponseEntity(isGrpAdded, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/moneymanager/split/splitwithgrp")
	@ResponseBody
	ResponseEntity splitWithGrp(@RequestBody Split newSplit) {

		Boolean isGrpAdded = mainService.splitWithGrp(newSplit);

		return new ResponseEntity(isGrpAdded, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/moneymanager/split/getsplitdetailsoffriends")
	@ResponseBody
	ResponseEntity getSplitDetailsFriends(@RequestBody User user) {
		String userId = String.valueOf(user.getUserId());
		List<Balance> balanceList = mainService.getSplitDetailsFriends(userId);

		return new ResponseEntity(balanceList, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/moneymanager/split/getfriendsplithistory")
	@ResponseBody
	ResponseEntity getFriendSplitHistory(@RequestParam("userId") String userId,
			@RequestParam("friendId") String friendId) {
		// String userId = String.valueOf(user.getUserId());
		List<BalanceDetails> balanceDetails = mainService.getFriendSplitHistory(userId, friendId);

		return new ResponseEntity(balanceDetails, HttpStatus.OK);
	}

	public String getUploadURL(MultipartFile toUpload) {
		String url = "";
		if (!toUpload.isEmpty()) {
			Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap("cloud_name", "moneymanager", "api_key",
					"131883725112657", "api_secret", "jLgIaAQszGN91XCkeK-4hsszR0k"));

			try {
				Map uploadResult = cloudinary.uploader().upload(toUpload.getBytes(), ObjectUtils.emptyMap());
				url = (String) uploadResult.get("url");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return url;
	}
}