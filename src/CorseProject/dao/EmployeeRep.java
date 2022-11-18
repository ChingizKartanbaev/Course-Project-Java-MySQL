package CorseProject.dao;

import CorseProject.models.Employee;

import java.util.List;

public interface EmployeeRep {
    void createEmployee(Employee employee);
    List<Employee> getAllEmployee();

    Employee getById (long id);

    void updateEmployeeSalary (int id, double money);

    void deleteEmployee (long id);
}
