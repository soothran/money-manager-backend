package com.moneymanager.moneymanager.dao;

import java.util.List;

import com.moneymanager.moneymanager.model.Category;

public interface CategoryDao {
	
	Boolean addCategory(Category newCategory);
	
	List<Category> getCategories(Category getCat);

}
