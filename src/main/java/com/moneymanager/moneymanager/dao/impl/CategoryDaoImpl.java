package com.moneymanager.moneymanager.dao.impl;

import java.sql.Blob;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.moneymanager.moneymanager.dao.CategoryDao;
import com.moneymanager.moneymanager.model.Category;

@Repository
public class CategoryDaoImpl  extends JdbcDaoSupport implements CategoryDao{
	
	@Autowired
	DataSource dataSource;

	@PostConstruct
	private void initialize() {
		setDataSource(dataSource);
	}

	@Override
	public Boolean addCategory(Category newCategory) {
		
		String sql = "INSERT INTO category (User_id, Category_name, Category_type,Status) "
				+ "VALUES ( ?, ?, ?, ?)";
		int rowsCreated = getJdbcTemplate().update(sql, new Object[] { newCategory.getUserId(), newCategory.getCategoryName(),
				newCategory.getCategoryType(), newCategory.getStatus() });
		if(rowsCreated > 0) {
			return true;
		}
		return false;
		
	}
	
	@Override
	public List<Category> getCategories(Category getCat){
		String sql = "SELECT * FROM category where (User_id = ? OR User_id = '0') and Category_type = ? and status = 'active'";
		List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql, new Object[] {getCat.getUserId(), getCat.getCategoryType()});
		
		List<Category> categories = new ArrayList<Category>();
		for(Map<String, Object> row:rows){
			Category cat = new Category();
			cat.setCategoryId(String.valueOf(row.get("Category")));
			cat.setUserId((String)row.get("User_id"));
			cat.setCategoryName((String)row.get("Category_name"));
			cat.setCategoryType((String)row.get("Category_type"));
			
			cat.setStatus((String)row.get("status"));
			categories.add(cat);
		}
		
		return categories;
	}
}
