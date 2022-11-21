package CorseProject.dao;

import CorseProject.models.Budget;

import java.util.List;

public interface BudgetRep {

    void createReport (Budget budget);
    List<Budget> getAllBudget();

    Budget getByBudgetAllocation (String budgetAllocation);

    void updateExpenses (int id, double money);

    void deleteBudget (long id);
}
