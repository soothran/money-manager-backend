package com.moneymanager.moneymanager.dao.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.moneymanager.moneymanager.dao.ExpenseDao;
import com.moneymanager.moneymanager.dao.UserDao;
import com.moneymanager.moneymanager.model.Expense;
import com.moneymanager.moneymanager.model.User;

@Repository
public class ExpenseDaoImpl extends JdbcDaoSupport implements ExpenseDao {

	@Autowired
	DataSource dataSource;

	@PostConstruct
	private void initialize() {
		setDataSource(dataSource);
	}

	@Override
	public Expense addExpense(Expense newExpense) {
		String sql = "INSERT INTO expense_details (Expense_id, User_id, Expense_time, Category_id,"
				+ "title, Amount, Image, Notes, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		int rowsCreated = getJdbcTemplate().update(sql, new Object[] { newExpense.getExpenseId(), newExpense.getUserId(), newExpense.getExpenseTime(), 
				newExpense.getCategoryId(), newExpense.getTitle(), newExpense.getAmount(),
			newExpense.getImage(),newExpense.getNotes(),newExpense.getStatus() });
		if(rowsCreated > 0) {
//			newExpense.setExpenseId(Integer.toString(rowsCreated));
			return newExpense;
		}
		return new Expense();
		
	}

	@Override
	public Expense updateExpense(Expense editExpense) {
		
		String sql = "UPDATE expense_details  set Category_id = ?,"
				+ "title = ?, Amount = ?, Image = ?, Notes = ?, status = ? where Expense_id = ?";
		int rowsCreated = getJdbcTemplate().update(sql, new Object[] {  
				editExpense.getCategoryId(), editExpense.getTitle(), editExpense.getAmount(),
				editExpense.getImage(),editExpense.getNotes(),editExpense.getStatus(), Integer.parseInt(editExpense.getExpenseId()) });
		if(rowsCreated != 0) {
//			newExpense.setExpenseId(Integer.toString(rowsCreated));
			return editExpense;
		}
		return new Expense();
		
	}
	
	@Override
	public Boolean deleteExpense(Expense delExpense) {
		
		String sql = "UPDATE expense_details  set status = 'deleted' where Expense_id = ?";
		int rowsCreated = getJdbcTemplate().update(sql, new Object[] {Integer.parseInt(delExpense.getExpenseId()) });
		if(rowsCreated != 0) {
			return true;
		}
		return false;
		
	}
	
	@Override
	public List<Expense> getAllExpenses(Expense allExp){
		String sql = " SELECT ex.*, c.Category_name FROM expense_details ex inner join category c on c.Category = ex.Category_id where ex.User_id = ? and ex.status != 'deleted'";
		List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql, new Object[] {allExp.getUserId()});
		
		List<Expense> expenses = new ArrayList<Expense>();
		for(Map<String, Object> row:rows){
			Expense exp = new Expense();
			exp.setExpenseId(String.valueOf(row.get("Expense_id")));
			exp.setExpenseTime((Timestamp)row.get("Expense_time"));
			exp.setCategoryId((String)row.get("Category_id"));
			exp.setTitle((String)row.get("title"));
			exp.setAmount((String)row.get("Amount"));
			exp.setCategoryName((String)row.get("Category_name"));
			exp.setUserId((String)row.get("User_id"));
			//Blob imgBlob = (Blob)row.get("Image");
			/*
			 * byte[] b=imgBlob.getBytes(1,(int)imgBlob.length()); FileOutputStream
			 * objFileOutputStream = new FileOutputStream("expense_image" +
			 * exp.getExpenseId() + ".jpg"); objFileOutputStream.write(b);
			 */
			exp.setImage((String)row.get("Image"));
			exp.setNotes((String)row.get("Notes"));
			exp.setStatus((String)row.get("status"));
			expenses.add(exp);
		}
		
		return expenses;
	}
}