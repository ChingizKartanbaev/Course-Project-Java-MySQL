package CorseProject.dao.impl;

import CorseProject.dao.BudgetRep;
import CorseProject.dao.Const;
import CorseProject.dao.DbHelper;
import CorseProject.models.Budget;
import CorseProject.models.Client;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BudgetRepImpl implements BudgetRep {

    DbHelper dbHelper = DbHelper.INSTANCE;

    @Override
    public void createReport(Budget budget) {

        String insert = "INSERT INTO " + Const.BUDGET_TABLE + "(" + Const.BUDGET_BUDGETALLOCATION
                + "," + Const.BUDGET_EXPENSES + ")" + "VALUES(?,?)";

        try {
            PreparedStatement preparedStatement = dbHelper.dbGetConnection().prepareStatement(insert);
            preparedStatement.setString(1, budget.getBudgetAllocation());
            preparedStatement.setDouble(2, budget.getExpenses());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Budget> getAllBudget() {

        List<Budget> budgetList = new ArrayList<>();
        String select = "SELECT * FROM " + Const.BUDGET_TABLE;

        try (PreparedStatement preparedStatement = dbHelper.dbGetConnection().prepareStatement(select)){
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                Budget budget = new Budget();
                budget.setIdBudget(resultSet.getLong("idbudget"));
                budget.setBudgetAllocation(resultSet.getNString("budgetAllocation"));
                budget.setExpenses(resultSet.getDouble("expenses"));
                budgetList.add(budget);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return budgetList;
    }

    @Override
    public Budget getByBudgetAllocation(String budgetAllocation) {

        String select = "SELECT * FROM " + Const.BUDGET_TABLE + " WHERE " + Const.BUDGET_BUDGETALLOCATION + "=?";

        try {
            PreparedStatement preparedStatement = dbHelper.dbGetConnection().prepareStatement(select);
            preparedStatement.setString(1, budgetAllocation);

            ResultSet resultSet = preparedStatement.executeQuery();
            Budget budget = new Budget();

            while (resultSet.next()){
                budget.setIdBudget(resultSet.getLong("idbudget"));
                budget.setBudgetAllocation(resultSet.getNString("budgetAllocation"));
                budget.setExpenses(resultSet.getDouble("expenses"));
            }
            return budget;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateExpenses(int id, double money) {
        String update = "UPDATE " + Const.BUDGET_TABLE + " set " + Const.BUDGET_EXPENSES
                + "=? WHERE " + Const.BUDGET_ID + "=?";

        try {
            PreparedStatement preparedStatement = dbHelper.dbGetConnection().prepareStatement(update);
            preparedStatement.setDouble(1, money);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteBudget(long id) {

    }
}
