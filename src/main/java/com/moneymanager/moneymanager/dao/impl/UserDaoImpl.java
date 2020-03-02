package com.moneymanager.moneymanager.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.moneymanager.moneymanager.dao.UserDao;
import com.moneymanager.moneymanager.model.Budget;
import com.moneymanager.moneymanager.model.Category;
import com.moneymanager.moneymanager.model.Income;
import com.moneymanager.moneymanager.model.User;

@Repository
public class UserDaoImpl extends JdbcDaoSupport implements UserDao {

	@Autowired
	DataSource dataSource;

	@PostConstruct
	private void initialize() {
		setDataSource(dataSource);
	}

	@Override
	public void insertUser(User user) {
		String sql = "INSERT INTO user_details "
				+ "(user_id, username, email_id, contact_number, password, reg_date, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
		getJdbcTemplate().update(sql, new Object[] { user.getUserId(), user.getUserName(), user.getEmailId(),
				user.getContactNumber(), user.getPassword(), user.getRegDate(), user.getStatus() });
	}

	@Override
	public User authenticateUser(User user) {
		User userDetails = new User();
		String sql = "SELECT * FROM user_details WHERE (username = ? OR email_id = ?) AND password = ? AND status = 'ACTIVE'";
		try {
			return (User) getJdbcTemplate().queryForObject(sql, new Object[] { user.getUserName(), user.getUserName(), user.getPassword() },
					new RowMapper<User>() {
						@Override
						public User mapRow(ResultSet rs, int rwNumber) throws SQLException {

							userDetails.setUserId(rs.getInt("user_id"));
							userDetails.setUserName(rs.getString("username"));
							userDetails.setEmailId(rs.getString("email_id"));
							userDetails.setContactNumber(rs.getString("contact_number"));
							userDetails.setRegDate(rs.getTimestamp("reg_date"));
							userDetails.setStatus(rs.getString("status"));
							return userDetails;
						}
					});
		} catch (EmptyResultDataAccessException e) {
			return userDetails;
		}
	}

	@Override
	public Boolean addBudget(Budget budget) {

		String sql = "INSERT INTO budget_details (User_id, Created_on, Amount, Period, Status) "
				+ "VALUES ( ?, ?, ?, ?, 'ACTIVE')";
		boolean deletePrevBudget = deleteBudget(budget);
		int rowsCreated = getJdbcTemplate().update(sql, new Object[] { budget.getUserId(), budget.getCreatedOn(),
				budget.getAmount(), budget.getPeriod() });
		if (rowsCreated > 0) {
			return true;
		}
		return false;
	}
	
	@Override
	public Budget getBudget(Budget budget) {
		Budget userDetails = new Budget();
		String sql = "SELECT * FROM budget_details WHERE User_id = ? and Period >= CURDATE() AND status = 'ACTIVE'";
		try {
			return (Budget) getJdbcTemplate().queryForObject(sql, new Object[] { budget.getUserId() },
					new RowMapper<Budget>() {
						@Override
						public Budget mapRow(ResultSet rs, int rwNumber) throws SQLException {							
							userDetails.setBudgetId(String.valueOf(rs.getInt("Budget_id")));
							userDetails.setUserId(rs.getString("User_id"));
							userDetails.setCreatedOn(rs.getTimestamp("Created_on"));
							userDetails.setAmount(rs.getString("Amount"));
							userDetails.setPeriod(rs.getTimestamp("Period"));
							userDetails.setStatus(rs.getString("Status"));
							return userDetails;
						}
					});
		} catch (EmptyResultDataAccessException e) {
			return userDetails;
		}
	}
	
public Boolean deleteBudget(Budget budget) {
		
		String sql = "UPDATE budget_details  set status = 'deleted' where User_id = ?";
		int rowsCreated = getJdbcTemplate().update(sql, new Object[] {Integer.parseInt(budget.getUserId()) });
		if(rowsCreated != 0) {
			return true;
		}
		return false;
		
	}
}