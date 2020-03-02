package com.moneymanager.moneymanager.dao.impl;

import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.moneymanager.moneymanager.dao.CategoryDao;
import com.moneymanager.moneymanager.dao.CommunityDao;
import com.moneymanager.moneymanager.model.Category;
import com.moneymanager.moneymanager.model.Group;
import com.moneymanager.moneymanager.model.User;

@Repository
public class CommunityDaoImpl extends JdbcDaoSupport implements CommunityDao {

	@Autowired
	DataSource dataSource;

	@PostConstruct
	private void initialize() {
		setDataSource(dataSource);
	}

	@Override
	public Boolean addFriend(String userId, String friendId) {

		String sql = "INSERT INTO friends_info (User_id, Friends_id ,Status) " + "VALUES ( ?, ?, 'ACTIVE')";
		int rowsCreated = getJdbcTemplate().update(sql, new Object[] { userId, friendId });
		if (rowsCreated > 0) {
			return true;
		}
		return false;

	}

	@Override
	public User searchFriend(String email, String userId) {
		String sql = "SELECT * FROM user_details where (email_id = ? OR User_id = ?) and status = 'ACTIVE'";
		return (User) getJdbcTemplate().queryForObject(sql, new Object[] { email, userId }, new RowMapper<User>() {
			@Override
			public User mapRow(ResultSet rs, int rwNumber) throws SQLException {

				User friend = new User();
				friend.setUserId(rs.getInt("user_id"));
				friend.setUserName(rs.getString("username"));
				friend.setEmailId(rs.getString("email_id"));
				friend.setContactNumber(rs.getString("contact_number"));

				return friend;
			}
		});
	}

	@Override
	public List<User> getFriends(String userId) {
		String sql = "SELECT * FROM friends_info where (User_id = ? OR Friends_id = ?)and status = 'ACTIVE'";
		List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql, new Object[] { userId, userId });

		List<User> friends = new ArrayList<User>();
		for (Map<String, Object> row : rows) {
			String friendId = (String) row.get("User_id");
			if (friendId.equalsIgnoreCase(userId)) {
				friendId = (String) row.get("Friends_id");
			}
			User friend = searchFriend(new String(), friendId);

			friend.setStatus((String) row.get("status"));
			friends.add(friend);
		}

		return friends;
	}

	@Override
	public List<Group> getGroups(String userId) {
		String sql = "SELECT * FROM group_master where (Master_id IN (select Master_id from group_details where Member_id = ?) )and status = 'ACTIVE'";
		List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql, new Object[] { userId });

		List<Group> groups = new ArrayList<Group>();
		for (Map<String, Object> row : rows) {
			Group group = new Group();
			group.setGroupId(String.valueOf(row.get("Master_id")));
			group.setUserId((String) row.get("User_id"));
			group.setCreatedOn((Timestamp) row.get("Created_on"));
			group.setTitle((String) row.get("Group_name"));
			group.setStatus((String) row.get("Status"));
			groups.add(group);
		}

		return groups;
	}

	@Override
	public Group getGroupDetails(String grpId) {
		String sql = "SELECT us.User_id, us.username, us.email_id from user_details  us inner join group_details gr on "
				+ "us.User_id = gr.Member_id where Master_id = ? and gr.Status = 'ACTIVE'";
		List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql, new Object[] { grpId });

		Group group = new Group();
		group.setGroupId(grpId);
		List<User> members = new ArrayList<User>();
		for (Map<String, Object> row : rows) {
			User member = new User();
			member.setUserId(Integer.parseInt(String.valueOf(row.get("User_id"))));
			member.setUserName((String) row.get("username"));
			member.setEmailId((String) row.get("email_id"));
			members.add(member);
		}
		group.setUser((User[])members.toArray(new User[members.size()]));
		return group;
	}

	@Override
	public Boolean addGrp(Group newGrp) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		String sql = "INSERT INTO group_master (User_id, Group_name, Created_on ,Status) "
				+ "VALUES ( ?, ?, ?, 'ACTIVE')";
		// int rowsCreated = getJdbcTemplate().update(sql, new Object[] { ,
		// newGrp.getTitle(), });
		int rowsCreated = getJdbcTemplate().update(connection -> {
			PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setString(1, newGrp.getUserId());
			ps.setString(2, newGrp.getTitle());
			ps.setTimestamp(3, newGrp.getCreatedOn());
			return ps;
		}, keyHolder);
		String masterId = String.valueOf(keyHolder.getKey());
		if (rowsCreated > 0) {
			boolean result = addGrpDetails(masterId, newGrp);
			return result;
		}
		return false;

	}

	public boolean addGrpDetails(final String masterId, Group newGrp) {
		String sql = "INSERT INTO group_details(Master_id, Member_id, Status) VALUES (?, ?, 'ACTIVE')";

		List<Object[]> parameters = new ArrayList<Object[]>();
		parameters.add(new Object[] { masterId, newGrp.getUserId() });
		for (User member : newGrp.getUser()) {
			parameters.add(new Object[] { masterId, member.getUserId() });
		}
		int[] rowsAffected = getJdbcTemplate().batchUpdate(sql, parameters);

		if (rowsAffected.length > 0) {
			return true;
		}
		return false;
	}
}
