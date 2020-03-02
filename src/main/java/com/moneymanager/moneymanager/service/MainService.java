package com.moneymanager.moneymanager.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.moneymanager.moneymanager.model.Balance;
import com.moneymanager.moneymanager.model.BalanceDetails;
import com.moneymanager.moneymanager.model.Category;
import com.moneymanager.moneymanager.model.Expense;
import com.moneymanager.moneymanager.model.Group;
import com.moneymanager.moneymanager.model.Income;
import com.moneymanager.moneymanager.model.Split;
import com.moneymanager.moneymanager.model.User;

@Service
public interface MainService {
Expense addExpense(Expense newExpense);

Expense updateExpense(Expense editExpense);

Boolean deleteExpense(Expense delExpense);

List<Expense> getAllExpenses(Expense allExp);

Income addIncome(Income newIncome);

Income updateIncome(Income editIncome);

Boolean deleteIncome(Income delIncome);

List<Income> getAllIncomes(Income allInc);

Boolean addCategory(Category newCategory);

List<Category> getCategories(Category getCat);

User searchFriend(String email, String userId);

Boolean addFriend(String userId, String friendId);

List<User> getFriends(String userId);

List<Group> getGroups(String userId);

Group getGroupDetails(String grpId);

Boolean addGrp(Group newGrp);

Boolean splitWithFriend(Split newSplit);

Boolean splitWithGrp(Split newSplit);

List<Balance> getSplitDetailsFriends(String userId);

List<BalanceDetails> getFriendSplitHistory(String userId, String friendId);
}
