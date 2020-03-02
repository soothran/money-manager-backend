package com.moneymanager.moneymanager.dao;

import java.util.List;

import com.moneymanager.moneymanager.model.Category;
import com.moneymanager.moneymanager.model.Group;
import com.moneymanager.moneymanager.model.User;

public interface CommunityDao {
	
	Boolean addFriend(String userId, String friendId);
	
	User searchFriend(String email, String friendId);

	List<User> getFriends(String userId);
	
	Boolean addGrp(Group newGrp);
	
	List<Group> getGroups(String userId);
	
	Group getGroupDetails(String grpId);
}
