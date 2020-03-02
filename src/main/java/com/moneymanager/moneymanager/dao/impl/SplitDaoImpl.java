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
import com.moneymanager.moneymanager.dao.SplitDao;
import com.moneymanager.moneymanager.model.Balance;
import com.moneymanager.moneymanager.model.BalanceDetails;
import com.moneymanager.moneymanager.model.Category;
import com.moneymanager.moneymanager.model.Expense;
import com.moneymanager.moneymanager.model.Group;
import com.moneymanager.moneymanager.model.Split;
import com.moneymanager.moneymanager.model.SplitDetails;
import com.moneymanager.moneymanager.model.User;

@Repository
public class SplitDaoImpl extends JdbcDaoSupport implements SplitDao {

	@Autowired
	DataSource dataSource;

	@PostConstruct
	private void initialize() {
		setDataSource(dataSource);
	}

	@Override
	public List<BalanceDetails> getFriendSplitHistory(String userId, String friendId) {
		String sql = " select m.Master_id, m.Expense_id, e.Amount as total, m.is_Group_expense, m.Group_id, m.Created_on, e.title, d.User_id as debtor, d.Amount, d.spent, d.owe, d.owe_to "
				+ "from split_master m inner join split_details d inner join expense_details e on m.Expense_id=e.Expense_id and m.Master_id = d.Master_id "
				+ "where (d.owe_to = ? AND d.User_id = ? )OR (d.owe_to = ? AND d.User_id = ?) and d.status = 'ACTIVE' and m.status = 'NEW'";
		List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql,
				new Object[] { userId, friendId, friendId, userId });

		List<BalanceDetails> details = new ArrayList<BalanceDetails>();
		for (Map<String, Object> row : rows) {
			BalanceDetails item = new BalanceDetails();
			item.setSplitId(String.valueOf((Long) row.get("Master_id")));
			item.setExpenseId((String) row.get("Expense_id"));
			item.setTotalAmt((String) row.get("total"));
			item.setGrpExpense(Boolean.parseBoolean((String)row.get("is_Group_expense")));
			item.setGroupId((String) row.get("Group_id"));
			item.setCreatedOn((Timestamp) row.get("Created_on"));
			item.setGroupTitle((String) row.get("title"));
			item.setDebterId((String) row.get("debtor"));
			item.setAmount((String) row.get("Amount"));
			item.setOwe((String) row.get("owe"));
			item.setSpent((String) row.get("spent"));
			item.setOweTo((String) row.get("owe_to"));
			details.add(item);
		}

		return details;
	}

	@Override
	public List<Balance> getSplitDetailsFriends(String userId) {

		/*
		 * String sql = "INSERT INTO friends_info (User_id, Friends_id ,Status) " +
		 * "VALUES ( ?, ?, 'ACTIVE')"; int rowsCreated = getJdbcTemplate().update(sql,
		 * new Object[] {}); if (rowsCreated > 0) { return true; }
		 */
		List<Balance> debtors = getDebtors(userId);
		List<Balance> creditors = getCreditors(userId);
		List<Balance> balance = debtors;
		for (Balance item : balance) {
			for (Balance credit : creditors) {
				if (item.getUserId().equals(credit.getUserId())) {
					float debtAmt = Float.parseFloat(item.getDebt());
					float creditAmt = Float.parseFloat(credit.getCredit());
					float greatest = Math.max(debtAmt, creditAmt);
					float finalAmt = Math.max(debtAmt, creditAmt) - Math.min(debtAmt, creditAmt);
					if (greatest == debtAmt) {
						item.setDebt(String.valueOf(finalAmt));
					} else {
						item.setCredit(String.valueOf(finalAmt));
						//;
					}

				}
			}
		}

		return balance;

	}

//	@Override
	public List<Balance> getDebtors(String userId) {
		String sql = "select s.User_id , ROUND(sum(s.owe), 2) as debt, s.status, u.username from split_details s inner join user_details u on s.User_id = u.User_id where s.owe_to = ? and s.User_id != ? and s.status='active' group by s.User_id";
		List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql, new Object[] { userId, userId });

		List<Balance> debtors = new ArrayList<Balance>();
		for (Map<String, Object> row : rows) {
			Balance debtor = new Balance();
			debtor.setUserId((String) row.get("User_id"));
			debtor.setUserName((String) row.get("username"));
			double d = (Double) row.get("debt");
			debtor.setDebt(String.valueOf((float) d));

			debtor.setStatus((String) row.get("status"));
			debtors.add(debtor);
		}

		return debtors;
	}

//	@Override
	public List<Balance> getCreditors(String userId) {
		String sql = "select s.owe_to, ROUND(sum(s.owe), 2) as credit, s.status, u.username from split_details s inner join user_details u on s.owe_to = u.User_id where s.owe_to != ? and s.User_id = ? and s.status='active' group by s.owe_to";
		List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql, new Object[] { userId, userId });

		List<Balance> creditors = new ArrayList<Balance>();
		for (Map<String, Object> row : rows) {
			Balance creditor = new Balance();
			creditor.setUserId((String) row.get("owe_to"));
			creditor.setUserName((String) row.get("username"));
			double d = (Double) row.get("credit");
			creditor.setCredit(String.valueOf((float) d));
			// User friend = searchFriend(new String(), friendId);

			creditor.setStatus((String) row.get("status"));
			creditors.add(creditor);
		}

		return creditors;
	}

	@Override
	public Boolean splitWithFriend(Split newSplit) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		String sql = "INSERT INTO split_master (Expense_id, Is_Group_expense, Group_id, Image, Comment, Created_on, Status) "
				+ "VALUES ( ?, ?, ?, ?, ?, ?, 'NEW')";
		// int rowsCreated = getJdbcTemplate().update(sql, new Object[] { ,
		// newGrp.getTitle(), });
		int rowsCreated = getJdbcTemplate().update(connection -> {
			PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setString(1, newSplit.getExpenseId());
			ps.setString(2, (String.valueOf(newSplit.getIsGroupExpense())));
			ps.setString(3, newSplit.getGroupId());
			ps.setString(4, newSplit.getImage());
			ps.setString(5, newSplit.getComment());
			ps.setTimestamp(6, newSplit.getCreatedOn());
			return ps;
		}, keyHolder);
		String masterId = String.valueOf(keyHolder.getKey());
		if (rowsCreated > 0) {
			boolean result = addSplitDetails(masterId, newSplit);
			Expense upExp = new Expense();
			upExp.setExpenseId(newSplit.getExpenseId());
			updateExpense(upExp);
			return result;
		}
		return false;

	}

	public boolean addSplitDetails(final String masterId, Split newSplit) {
		String sql = "INSERT INTO split_details(Master_id, User_id, Amount, spent, owe, owe_to, Status) VALUES (?, ?, ?, ?, ?, ?, 'ACTIVE')";

		List<Object[]> parameters = new ArrayList<Object[]>();
		for (SplitDetails details : newSplit.getSplitDetails()) {
			parameters.add(new Object[] { masterId, details.getUserId(), details.getAmount(), details.getSpent(),
					details.getOwe(), details.getOweTo() });
		}
		int[] rowsAffected = getJdbcTemplate().batchUpdate(sql, parameters);

		if (rowsAffected.length > 0) {
			return true;
		}
		return false;
	}
	
public Boolean updateExpense(Expense upExpense) {
		
		String sql = "UPDATE expense_details  set status = 'SHARED' where Expense_id = ?";
		int rowsCreated = getJdbcTemplate().update(sql, new Object[] {Integer.parseInt(upExpense.getExpenseId()) });
		if(rowsCreated != 0) {
			return true;
		}
		return false;
		
	}
}
