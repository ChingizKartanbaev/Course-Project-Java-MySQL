package CorseProject.models;

public class Budget {

    private long idBudget;
    private String budgetAllocation;
    private double expenses;

    public Budget() {
    }

    public Budget(String budgetAllocation, double expenses) {
        this.budgetAllocation = budgetAllocation;
        this.expenses = expenses;
    }

    public long getIdBudget() {
        return idBudget;
    }

    public void setIdBudget(long idBudget) {
        this.idBudget = idBudget;
    }

    public String getBudgetAllocation() {
        return budgetAllocation;
    }

    public void setBudgetAllocation(String budgetAllocation) {
        this.budgetAllocation = budgetAllocation;
    }

    public double getExpenses() {
        return expenses;
    }

    public void setExpenses(double expenses) {
        this.expenses = expenses;
    }

    @Override
    public String toString() {
        return idBudget + " " + budgetAllocation + " " + expenses;
    }
}
