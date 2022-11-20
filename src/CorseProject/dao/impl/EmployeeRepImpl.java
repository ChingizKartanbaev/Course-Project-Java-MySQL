package CorseProject.dao.impl;

import CorseProject.dao.Const;
import CorseProject.dao.DbHelper;
import CorseProject.dao.EmployeeRep;
import CorseProject.models.Employee;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepImpl implements EmployeeRep {
    DbHelper dbHelper = DbHelper.INSTANCE;

    @Override
    public void createEmployee(Employee employee) {

        String insert = "INSERT INTO " + Const.EMPLOYEE_TABEL + "(" + Const.EMPLOYEE_FULLNAME + "," + Const.EMPLOYEE_LOGIN + "," +
                Const.EMPLOYEE_PASSWORD + "," + Const.EMPLOYEE_TYPEOFACCOUNT + "," + Const.EMPLOYEE_SALATY + ")" + "VALUES(?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = dbHelper.dbGetConnection().prepareStatement(insert);
            preparedStatement.setString(1, employee.getFullName());
            preparedStatement.setString(2, employee.getLogin());
            preparedStatement.setString(3, employee.getPassword());
            preparedStatement.setString(4, employee.getTypeOfAccount());
            preparedStatement.setDouble(5, employee.getSalary());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Employee> getAllEmployee(){

        List<Employee> employeeList = new ArrayList<>();
        String select = "SELECT * FROM " + Const.EMPLOYEE_TABEL;

        try (PreparedStatement preparedStatement = dbHelper.dbGetConnection().prepareStatement(select)){
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                Employee employee = new Employee();
                employee.setId(resultSet.getLong("idEmployee"));
                employee.setFullName(resultSet.getNString("fullName"));
                employee.setLogin(resultSet.getString("login"));
                employee.setPassword(resultSet.getString("passwords"));
                employee.setTypeOfAccount(resultSet.getString("typeOfAccount"));
                employee.setSalary(resultSet.getDouble("salary"));
                employeeList.add(employee);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return employeeList;
    }

    @Override
    public Employee getById (long id){

        String select = "SELECT * FROM " + Const.EMPLOYEE_TABEL + " WHERE " + Const.EMPLOYEE_ID + "=?";

        try {
            PreparedStatement preparedStatement = dbHelper.dbGetConnection().prepareStatement(select);
            preparedStatement.setInt(1, Math.toIntExact(id));

            ResultSet resultSet = preparedStatement.executeQuery();
            Employee employee = new Employee();

            while (resultSet.next()){
                employee.setId(resultSet.getLong("idEmployee"));
                employee.setFullName(resultSet.getNString("fullName"));
                employee.setLogin(resultSet.getString("login"));
                employee.setPassword(resultSet.getString("passwords"));
                employee.setTypeOfAccount(resultSet.getString("typeOfAccount"));
                employee.setSalary(resultSet.getDouble("salary"));
            }
            return employee;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateEmployeeSalary (int id, double money) {

        String update = "UPDATE " + Const.EMPLOYEE_TABEL + " set " + Const.EMPLOYEE_SALATY + "=? WHERE " + Const.EMPLOYEE_ID + "=?";

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
    public void deleteEmployee(long id) {

        String delete = "delete from " + Const.EMPLOYEE_TABEL + " where " + Const.EMPLOYEE_ID + "=?";

        try {
            PreparedStatement preparedStatement = dbHelper.dbGetConnection().prepareStatement(delete);
            preparedStatement.setInt(1, Math.toIntExact(id));
            int result = preparedStatement.executeUpdate();

            if(result == 1){
                System.out.println("Работник удален");
            }else if(result == 0){
                System.out.println("Данный работник не был найден");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}