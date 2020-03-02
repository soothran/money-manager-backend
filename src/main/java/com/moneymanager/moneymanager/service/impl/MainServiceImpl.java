package com.moneymanager.moneymanager.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moneymanager.moneymanager.dao.CategoryDao;
import com.moneymanager.moneymanager.dao.CommunityDao;
import com.moneymanager.moneymanager.dao.ExpenseDao;
import com.moneymanager.moneymanager.dao.IncomeDao;
import com.moneymanager.moneymanager.dao.SplitDao;
import com.moneymanager.moneymanager.model.Balance;
import com.moneymanager.moneymanager.model.BalanceDetails;
import com.moneymanager.moneymanager.model.Category;
import com.moneymanager.moneymanager.model.Expense;
import com.moneymanager.moneymanager.model.Group;
import com.moneymanager.moneymanager.model.Income;
import com.moneymanager.moneymanager.model.Split;
import com.moneymanager.moneymanager.model.SplitDetails;
import com.moneymanager.moneymanager.model.User;
import com.moneymanager.moneymanager.service.MainService;

@Service
public class MainServiceImpl implements MainService {

	@Autowired
	ExpenseDao expenseDao;
	
	@Autowired
	IncomeDao incomeDao;

	@Autowired
	CategoryDao categoryDao;

	@Autowired
	CommunityDao communityDao;

	@Autowired
	SplitDao splitDao;

	@Override
	public Expense addExpense(Expense newExpense) {
		return expenseDao.addExpense(newExpense);
	}

	@Override
	public Expense updateExpense(Expense editExpense) {
		return expenseDao.updateExpense(editExpense);
	}

	@Override
	public Boolean deleteExpense(Expense delExpense) {
		return expenseDao.deleteExpense(delExpense);
	}

	@Override
	public List<Expense> getAllExpenses(Expense allExp) {
		return expenseDao.getAllExpenses(allExp);
	}
	
	@Override
	public Income addIncome(Income newIncome) {
		return incomeDao.addIncome(newIncome);
	}

	@Override
	public Income updateIncome(Income editIncome) {
		return incomeDao.updateIncome(editIncome);
	}

	@Override
	public Boolean deleteIncome(Income delIncome) {
		return incomeDao.deleteIncome(delIncome);
	}

	@Override
	public List<Income> getAllIncomes(Income allInc) {
		return incomeDao.getAllIncomes(allInc);
	}

	@Override
	public Boolean addCategory(Category newCategory) {
		return categoryDao.addCategory(newCategory);
	}

	@Override
	public List<Category> getCategories(Category getCat) {
		return categoryDao.getCategories(getCat);
	}

	@Override
	public User searchFriend(String email, String userId) {
		return communityDao.searchFriend(email, userId);
	}

	@Override
	public Boolean addFriend(String userId, String friendId) {
		return communityDao.addFriend(userId, friendId);
	}

	@Override
	public List<User> getFriends(String userId) {
		return communityDao.getFriends(userId);
	}

	@Override
	public Boolean addGrp(Group newGrp) {
		Date expDate = new Date();
		newGrp.setCreatedOn(new Timestamp(expDate.getTime()));
		return communityDao.addGrp(newGrp);
	}

	@Override
	public List<Group> getGroups(String userId) {
		return communityDao.getGroups(userId);
	}

	@Override
	public Group getGroupDetails(String grpId) {
		return communityDao.getGroupDetails(grpId);
	}

	@Override
	public Boolean splitWithFriend(Split newSplit) {
		Date dt = new Date();
		newSplit.setCreatedOn(new Timestamp(dt.getTime()));
		float total = Float.parseFloat(newSplit.getTotal());
		float userShare = Float.parseFloat(newSplit.getSplitDetails()[0].getAmount());
		float friendShare = Float.parseFloat(newSplit.getSplitDetails()[1].getAmount());
		float userSpent = Float.parseFloat(newSplit.getTotal());
		float friendSpent = 0;
		float userOwe = userShare - userSpent;
		float friendOwe = friendShare - friendSpent;
		newSplit.getSplitDetails()[0].setSpent(String.valueOf(userSpent));
		newSplit.getSplitDetails()[0].setOwe(String.valueOf(userOwe));

		newSplit.getSplitDetails()[1].setSpent(String.valueOf(friendSpent));
		newSplit.getSplitDetails()[1].setOwe(String.valueOf(friendOwe));
		newSplit.getSplitDetails()[1].setOweTo(newSplit.getSplitDetails()[0].getUserId());
		newSplit.getSplitDetails()[0].setOweTo(newSplit.getSplitDetails()[1].getUserId());
		if (userOwe < 0) {
			newSplit.getSplitDetails()[0].setOweTo(newSplit.getSplitDetails()[0].getUserId());
		} else {
			newSplit.getSplitDetails()[1].setOweTo(newSplit.getSplitDetails()[1].getUserId());
		}
		return splitDao.splitWithFriend(newSplit);
	}

	@Override
	public Boolean splitWithGrp(Split newSplit) {
		Date dt = new Date();
		newSplit.setCreatedOn(new Timestamp(dt.getTime()));
		float total = Float.parseFloat(newSplit.getTotal());
		float userShare = Float.parseFloat(newSplit.getSplitDetails()[0].getAmount());
		float userSpent = Float.parseFloat(newSplit.getTotal());
		float userOwe = userShare - userSpent;
		int idx = 0;
		for (SplitDetails details : newSplit.getSplitDetails()) {
			if (idx > 0) {
				float friendShare = Float.parseFloat(details.getAmount());
				float friendSpent = 0;
				float friendOwe = friendShare - friendSpent;
				newSplit.getSplitDetails()[idx].setSpent(String.valueOf(friendSpent));
				newSplit.getSplitDetails()[idx].setOwe(String.valueOf(friendOwe));
				newSplit.getSplitDetails()[idx].setOweTo(newSplit.getSplitDetails()[0].getUserId());
			}
			idx++;
		}
		newSplit.getSplitDetails()[0].setSpent(String.valueOf(userSpent));
		newSplit.getSplitDetails()[0].setOwe(String.valueOf(userOwe));
		newSplit.getSplitDetails()[0].setOweTo(newSplit.getSplitDetails()[0].getUserId());
		return splitDao.splitWithFriend(newSplit);
	}

	@Override
	public List<Balance> getSplitDetailsFriends(String userId) {
		return splitDao.getSplitDetailsFriends(userId);
	}

	@Override
	public List<BalanceDetails> getFriendSplitHistory(String userId, String friendId) {
		return splitDao.getFriendSplitHistory(userId, friendId);
	}
}
