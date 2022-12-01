package CorseProject.service;

import CorseProject.utils.PrettyTable;
import CorseProject.dao.*;
import CorseProject.dao.impl.*;
import CorseProject.models.Employee;

import java.util.Scanner;

public class DirektorService {

    private static final EmployeeRep employeeRep = new EmployeeRepImpl();
    private static final ClientRep clientRep = new ClientRepImpl();
    private static final ReportManagerRep reportManagerRep = new ReportManagerRepImpl();
    private static final ReviewsRep reviewsRep = new ReviewsRepImpl();
    private static final BudgetRep budgetRep = new BudgetRepImpl();
    private static final Scanner scanner = new Scanner(System.in);

    public static void showMenu (){

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
                7 - Добавить
                8 - Удалить
                9 - Выход
                """);

            switch (scanner.nextInt()){

                case 1 -> showAListOfAllCoverageAreas();

                case 2 -> budget();

                case 3 -> redactBudget();

                case 4 -> showReview();

                case 5 -> raiseSalary();

                case 6 -> lowerSalary();

                case 7 -> register();

                case 8 -> delete();

                case 9 -> {
                    System.out.println("Программа завершена, мы будем рады вашему возвращению!");
                    break loop;
                }

            }
        }
    }

    public static void showAListOfAllCoverageAreas (){

        // displays a list of coverage areas
        PrettyTable prettyTable = new PrettyTable("City Name", "Coverage area");

        // through forEach, the name of the city and the profit from the city are added to the pretty table
        reportManagerRep.getAllReports().forEach(x -> prettyTable.addRow(x.getCityName(),x.getCustomerCoverageArea()));
        System.out.println(prettyTable);
    }

    private static void budget() {

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

    private static void redactBudget (){

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

    public static void showReview (){

        // adds a header to the table
        PrettyTable prettyTable = new PrettyTable("Отзыв", "Имя клиента");

        // adds a review and the client's name to the table
        reviewsRep.getAllReviews().forEach(x -> prettyTable.addRow(x.getReview(),
                clientRep.getClientById(x.getIdClient()).getFullName()));
        System.out.println(prettyTable);
    }

    private static void raiseSalary (){

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

        salaryThatUsed -= employeeRep.getById(idEmployee).getSalary();
        // id search
        Employee employee = employeeRep.getById(idEmployee);

        while (true){
            // entering amounts and calculating new wages
            System.out.print("Сумму на которуй вы хотите повысить: ");
            double upSalary = scanner.nextDouble();
            double refreshSalary = employee.getSalary() + upSalary;

            // checking for a salary increase, the po should not be more than the allocated budget
            if(budgetRep.getByBudgetAllocation("Salary budget").getExpenses() > (refreshSalary + salaryThatUsed)){
                // database entry
                employeeRep.updateEmployeeSalary((int) employee.getId(), refreshSalary);
                System.out.println("Зарплата была повышена");
                salaryThatUsed = 0;
                break;

            }else {
                System.out.println("Ошибка зарплата превышает выделеный бюджет");
                salaryThatUsed = 0;
            }
        }
    }

    private static void lowerSalary (){

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

    private static void register (){
        // Заглушка
        String n = scanner.nextLine();

        System.out.println("Введите данные работника" );
        // employee registration
        System.out.println("Имя");
        String fullName = scanner.nextLine();
        System.out.println("Логин");
        String login = scanner.nextLine();
        System.out.println("Пароль");
        String password = scanner.nextLine();
        System.out.println("Тип аккаунта");
        String typeOfAccount = scanner.next();
        System.out.println("Зарабатную плату");
        double salary = scanner.nextDouble();
        Employee employeeAdd = new Employee(fullName, login, password, typeOfAccount, salary);

        // all data is written to the database
        employeeRep.createEmployee(employeeAdd);
    }

    private static void delete () {

        System.out.println("Введите кого вы хотие удалить" +
                "1 - Работник " +
                "2 - Клиент: ");
        switch (scanner.nextInt()){
            // delete an employee
            case 1 -> {
                long deleteEmployee = scanner.nextLong();
                employeeRep.deleteEmployee(deleteEmployee);
            }
            // delete a client
            case 2 -> {
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