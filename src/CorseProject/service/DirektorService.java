package CorseProject.service;

import CorseProject.utils.PrettyTable;
import CorseProject.dao.*;
import CorseProject.dao.impl.*;
import CorseProject.models.Employee;

import java.util.Scanner;

public class DirektorService extends Accounts {

    private static final EmployeeRep employeeRep = new EmployeeRepImpl();
    private static final ClientRep clientRep = new ClientRepImpl();
    private static final BudgetRep budgetRep = new BudgetRepImpl();
    private static final Scanner scanner = new Scanner(System.in);


    public void showDirekorMenu (){
        loop:
        while (true){

            System.out.println("""

                Выберете меню\s
                1 - Показать список всех зон покрытия
                2 - Показать общий бюджет необходимый для зарплаты
                3 - Редактировать общий бюджет для зарплат
                4 - Посмотреть отзывы
                5 - Повысить зарплату сотруднику
                6 - Понизить зарплату сотруднику
                7 - Добавить сотрудника
                8 - Удалить
                9 - Выход
                """);

            switch (scanner.nextInt()){

                case 1 -> super.showAListOfAllCoverageAreas();

                case 2 -> budget();

                case 3 -> redactBudget();

                case 4 -> System.out.println(super.showReview());

                case 5 -> raiseSalary();

                case 6 -> lowerSalary();

                case 7 -> register();

                case 8 -> delete();

                case 9 -> {
                    end();
                    break loop;
                }
                default -> System.out.println("Данные введены не котректно");
            }
        }
    }



    // 2 Меню
    private void budget() {
        // variable for storing the total budget
        double budgetThatUsed = 0;
        double budgetThatIsNotUsed = 0;

        PrettyTable prettyTable = new PrettyTable("Общий бюджет необходимый для зарплаты", "Используемы бюджет",
                "Не использованый бюджет");

        // gets the entire list of employees and through employee.getSalary() sums up the total amount
        for (Employee employee : employeeRep.getAllEmployee()) {
            budgetThatUsed += employee.getSalary();
        }

        // finds the budget used
        budgetThatUsed = budgetRep.getByBudgetAllocation("Salary budget").getExpenses() -
                (budgetRep.getByBudgetAllocation("Salary budget").getExpenses() - budgetThatUsed);

        // finds a free budget
        budgetThatIsNotUsed += budgetRep.getByBudgetAllocation("Salary budget").getExpenses() - budgetThatUsed;


        // through forEach, the general budget for the salary and the budget used are added to the pritty table
        prettyTable.addRow(String.valueOf(budgetRep.getByBudgetAllocation("Salary budget").getExpenses()),
                String.valueOf(budgetThatUsed), String.valueOf(budgetThatIsNotUsed));

        System.out.println(prettyTable);
    }




    // 3 Меню
    private void redactBudget() {
        while (true){

            double budgetThatUsed = 0;

            // introducing a new budget
            System.out.print("Введите новую бюджет для зароботной платы: ");
            double newBudgtForSalary = scanner.nextDouble();

            // gets the entire list of employees and through employee.getSalary() sums up the total amount
            for (Employee employee : employeeRep.getAllEmployee()) {
                budgetThatUsed += employee.getSalary();
            }

            // finds the sum of all salaries
            budgetThatUsed = budgetRep.getByBudgetAllocation("Salary budget").getExpenses() -
                    (budgetRep.getByBudgetAllocation("Salary budget").getExpenses() - budgetThatUsed);

            // check (total budget > (other expenses + new budget for po)) if true, the new budget is written to the database
            if(budgetRep.getByBudgetAllocation("Total budget").getExpenses() >=
                    (budgetRep.getByBudgetAllocation("Actual expenses").getExpenses() + newBudgtForSalary)){
                if(newBudgtForSalary >= budgetThatUsed){
                    budgetRep.updateExpenses(2, newBudgtForSalary);
                    System.out.println("Новый бюджет успешно сохранен");
                    break;

                }else {
                    System.out.println("Ошибка предлагаемый бюджет ниже используемого бюджета!");
                }

            }else {
                System.out.println("Ошибка бюджет зарплаты превышает общий бюджет. Общтий бюджет = " +
                        budgetRep.getByBudgetAllocation("Total budget").getExpenses());
            }
        }
    }




    // 5 Меню
    private void raiseSalary() {
        double salaryThatUsed = 0;

        // the sum of all salaries
        for (Employee employee1 : employeeRep.getAllEmployee()) {
            salaryThatUsed += employee1.getSalary();
        }

        // output a list of all employees
        for (Employee employee : employeeRep.getAllEmployee()) {
            System.out.println(employee.getId() + " " + employee.getFullName() + " " + employee.getSalary());
        }
        System.out.print("Введите айди: ");
        long idEmployee = scanner.nextLong();

        // id search
        Employee employee = employeeRep.getById(idEmployee);

        while (true){
            // entering amounts and calculating new wages
            System.out.print("Сумму на которуй вы хотите повысить: ");
            double upSalary = scanner.nextDouble();
            double refreshSalary = employee.getSalary() + upSalary;

            // checking for a salary increase, the po should not be more than the allocated budget
            if(budgetRep.getByBudgetAllocation("Salary budget").getExpenses() > refreshSalary + (salaryThatUsed - employee.getSalary())){
                // database entry
                employeeRep.updateEmployeeSalary((int) employee.getId(), refreshSalary);
                System.out.println("Зарплата была повышена");
                break;

            }else {
                System.out.println("Ошибка зарплата превышает выделеный бюджет");
                salaryThatUsed = 0;
            }
        }
    }




    // 6 Меню
    private void lowerSalary() {

        // output a list of all employees
        for (Employee employee : employeeRep.getAllEmployee()) {
            System.out.println(employee.getId() + " " + employee.getFullName() + " " + employee.getSalary());
        }
        System.out.print("Введите айди: ");
        // id search
        Employee employee = employeeRep.getById(scanner.nextInt());

        while (true){

            // entering amounts and calculating new wages;
            System.out.print("Сумму на которуй вы хотите понизить: ");
            double upSalary = scanner.nextDouble();
            double refreshSalary = employee.getSalary() - upSalary;

            if(refreshSalary > 0){
                // database entry
                employeeRep.updateEmployeeSalary((int) employee.getId(), refreshSalary);
                break;
            } else {
                System.out.println("Ошибка зарплат не может быть отрицательной");
            }
        }
    }




    // 7 Меню
    private void register() {

        Scanner reg = new Scanner(System.in);

        System.out.println("Введите имя и фамилию сотрудника");
        String fullName = reg.nextLine();
        System.out.println("Введите логин");
        String login = reg.nextLine();
        System.out.println("Введите пароль");
        String password = reg.nextLine();
        System.out.println("Ветите тип аккаунта (Manager/Cashier)");
        String typeOfAccount = reg.next();
        System.out.println("Введите зарабатную плату");
        double salary = reg.nextDouble();

        if(checkFullName(fullName) && checkLogin(login) && checkTypeOfAccount(typeOfAccount) && checkSalary(salary)){
            Employee employeeAdd = new Employee(fullName, login, password, typeOfAccount, salary);

            // all data is written to the database
            employeeRep.createEmployee(employeeAdd);
        } else {
            System.out.println("Данные были введены некорректно");
            register();
        }
    }

    private boolean checkFullName(String fullName){
        return !fullName.isEmpty();
    }

    private boolean checkLogin(String login) {
        boolean flag = false;

        for (Employee employee : employeeRep.getAllEmployee()) {
            flag = !login.equals(employee.getLogin());
        }

        return flag;
    }

    private boolean checkTypeOfAccount(String typeOfAccount){
        return !typeOfAccount.equals("Direktor");
    }

    private boolean checkSalary(double salary){
        return salary > 0;
    }




    // 8 Меню
    private void delete() {

        System.out.println("Введите кого вы хотие удалить " +
                "1 - Работник " +
                "2 - Клиент: ");

        switch (scanner.nextInt()){
            // delete an employee
            case 1 -> {
                System.out.println(super.showListOfEmployee());
                long deleteEmployee = scanner.nextLong();
                employeeRep.deleteEmployee(deleteEmployee);
            }
            // delete a client
            case 2 -> {
                System.out.println(super.showListOfClient());
                long deleteClinet = scanner.nextLong();
                clientRep.deleteClient(deleteClinet);
            }
            // if incorrect data is entered
            default -> {
                System.out.println("Вы ввели не правильные данные, введите ещё раз");
                delete();
            }
        }
    }
}