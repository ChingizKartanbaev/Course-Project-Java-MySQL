package CorseProject;

import CorseProject.dao.EmployeeRep;
import CorseProject.dao.impl.EmployeeRepImpl;

public class logic {

    EmployeeRep employeeRep = new EmployeeRepImpl();

    //Сотрудник по индексу
//        System.out.printf("Name and surname %22s", "Account type \n");
//        System.out.printf(employeeRep.getById(3).getFullName() + "%15s" , employeeRep.getById(3).getTypeOfAccount() + " " +
//                employeeRep.getById(3).getSalary() + "\n");

    //Обновить зарплату
//        employeeRep.updateEmployee(3);


    //список всех сотрудников
//        for (Employee employee : employeeRep.getAllEmployee()) {
//            System.out.println(employee.getFullName() + " " + employee.getTypeOfAccount() + " " +employee.getSalary());
//        }
}
