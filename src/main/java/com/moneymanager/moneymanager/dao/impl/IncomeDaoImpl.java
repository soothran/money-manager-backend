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

import com.moneymanager.moneymanager.dao.IncomeDao;
import com.moneymanager.moneymanager.dao.UserDao;
import com.moneymanager.moneymanager.model.Income;
import com.moneymanager.moneymanager.model.User;

@Repository
public class IncomeDaoImpl extends JdbcDaoSupport implements IncomeDao {

	@Autowired
	DataSource dataSource;

	@PostConstruct
	private void initialize() {
		setDataSource(dataSource);
	}

	@Override
	public Income addIncome(Income newIncome) {
		String sql = "INSERT INTO income_details (Income_id, User_id, Income_time, Category_id,"
				+ "title, Amount, Image, Notes, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		int rowsCreated = getJdbcTemplate().update(sql, new Object[] { newIncome.getIncomeId(), newIncome.getUserId(), newIncome.getIncomeTime(), 
				newIncome.getCategoryId(), newIncome.getTitle(), newIncome.getAmount(),
			newIncome.getImage(),newIncome.getNotes(),newIncome.getStatus() });
		if(rowsCreated > 0) {
//			newIncome.setIncomeId(Integer.toString(rowsCreated));
			return newIncome;
		}
		return new Income();
		
	}

	@Override
	public Income updateIncome(Income editIncome) {
		
		String sql = "UPDATE income_details  set Category_id = ?,"
				+ "title = ?, Amount = ?, Image = ?, Notes = ?, status = ? where Income_id = ?";
		int rowsCreated = getJdbcTemplate().update(sql, new Object[] {  
				editIncome.getCategoryId(), editIncome.getTitle(), editIncome.getAmount(),
				editIncome.getImage(),editIncome.getNotes(),editIncome.getStatus(), Integer.parseInt(editIncome.getIncomeId()) });
		if(rowsCreated != 0) {
//			newIncome.setIncomeId(Integer.toString(rowsCreated));
			return editIncome;
		}
		return new Income();
		
	}
	
	@Override
	public Boolean deleteIncome(Income delIncome) {
		
		String sql = "UPDATE income_details  set status = 'deleted' where Income_id = ?";
		int rowsCreated = getJdbcTemplate().update(sql, new Object[] {Integer.parseInt(delIncome.getIncomeId()) });
		if(rowsCreated != 0) {
			return true;
		}
		return false;
		
	}
	
	@Override
	public List<Income> getAllIncomes(Income allInc){
		String sql = " SELECT ex.*, c.Category_name FROM income_details ex inner join category c on c.Category = ex.Category_id where ex.User_id = ? and ex.status != 'deleted'";
		List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql, new Object[] {allInc.getUserId()});
		
		List<Income> incomes = new ArrayList<Income>();
		for(Map<String, Object> row:rows){
			Income inc = new Income();
			inc.setIncomeId(String.valueOf(row.get("Income_id")));
			inc.setIncomeTime((Timestamp)row.get("Income_time"));
			inc.setCategoryId((String)row.get("Category_id"));
			inc.setTitle((String)row.get("title"));
			inc.setAmount((String)row.get("Amount"));
			inc.setCategoryName((String)row.get("Category_name"));
			inc.setUserId((String)row.get("User_id"));
			//Blob imgBlob = (Blob)row.get("Image");
			/*
			 * byte[] b=imgBlob.getBytes(1,(int)imgBlob.length()); FileOutputStream
			 * objFileOutputStream = new FileOutputStream("income_image" +
			 * inc.getIncomeId() + ".jpg"); objFileOutputStream.write(b);
			 */
			inc.setImage((String)row.get("Image"));
			inc.setNotes((String)row.get("Notes"));
			inc.setStatus((String)row.get("status"));
			incomes.add(inc);
		}
		
		return incomes;
	}
}