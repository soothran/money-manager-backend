package com.moneymanager.moneymanager.dao;

import java.util.List;

import com.moneymanager.moneymanager.model.Balance;
import com.moneymanager.moneymanager.model.BalanceDetails;
import com.moneymanager.moneymanager.model.Category;
import com.moneymanager.moneymanager.model.Group;
import com.moneymanager.moneymanager.model.Split;
import com.moneymanager.moneymanager.model.User;

public interface SplitDao {

	Boolean splitWithFriend(Split newSplit);

	List<Balance> getSplitDetailsFriends(String userId);
	
	List<BalanceDetails> getFriendSplitHistory(String userId, String friendId);

	// Boolean splitWithGrp(Split newSplit);

	/*
	 * User searchFriend(String email, String friendId);
	 * 
	 * List<User> getFriends(String userId);
	 * 
	 * Boolean addGrp(Group newGrp);
	 */
}
