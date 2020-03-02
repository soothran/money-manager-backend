package com.moneymanager.moneymanager.dao;

import java.util.List;

import com.moneymanager.moneymanager.model.Income;

public interface IncomeDao {
	
Income addIncome(Income newIncome);

Income updateIncome(Income editIncome);

Boolean deleteIncome(Income deleteIncome);

List<Income> getAllIncomes(Income allInc);
}
