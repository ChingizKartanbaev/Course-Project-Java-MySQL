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

        // выводит список зон покрытия
        PrettyTable prettyTable = new PrettyTable("City Name", "Coverage area");

        // через forEach в притти тейбл доболяется название города и прибыль с города
        reportManagerRep.getAllReports().forEach(x -> prettyTable.addRow(x.getCityName(),x.getCustomerCoverageArea()));
        System.out.println(prettyTable);
    }

    private static void budget() {

        // переменная для храниения общего бюджета
        double budgetThatUsed = 0;
        double budgetThatIsNotUsed = 0;

        PrettyTable prettyTable = new PrettyTable("Общий бюджет необходимый для зарплаты", "Используемы бюджет",
                "Не использованый бюджет");

        // получает весь список сотрудников и через employee.getSalary() суммирует общюю сумму
        for (Employee employee : employeeRep.getAllEmployee()) {
            budgetThatUsed += employee.getSalary();
        }

        // находит используемый бюджет
        budgetThatUsed = budgetRep.getByBudgetAllocation("Salary budget").getExpenses() -
                (budgetRep.getByBudgetAllocation("Salary budget").getExpenses() - budgetThatUsed);

        // находит свободнный бюджет
        budgetThatIsNotUsed += budgetRep.getByBudgetAllocation("Salary budget").getExpenses() - budgetThatUsed;


        // через forEach в притти тейбл доболяется общтий бюджет для зарплаты и используемый бюджет
        prettyTable.addRow(String.valueOf(budgetRep.getByBudgetAllocation("Salary budget").getExpenses()),
                String.valueOf(budgetThatUsed), String.valueOf(budgetThatIsNotUsed));

        System.out.println(prettyTable);
    }

    private static void redactBudget (){

        while (true){

            double budgetThatUsed = 0;

            // вводим новый бюджет
            System.out.print("Введите новую бюджет для зароботной платы: ");
            double newBudgtForSalary = scanner.nextDouble();

            // получает весь список сотрудников и через employee.getSalary() суммирует общюю сумму
            for (Employee employee : employeeRep.getAllEmployee()) {
                budgetThatUsed += employee.getSalary();
            }

            // находит сумму всех зарплат
            budgetThatUsed = budgetRep.getByBudgetAllocation("Salary budget").getExpenses() -
                    (budgetRep.getByBudgetAllocation("Salary budget").getExpenses() - budgetThatUsed);

            // проверка (общий бюджет > (прочие расходы + новый бюджет для зп)) если true то новый бюджет записывается в бд
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

        // в таблицу добовляет хэдер
        PrettyTable prettyTable = new PrettyTable("Отзыв", "Имя клиента");

        // в таблицу добовляет отзыв и имя клиента
        reviewsRep.getAllReviews().forEach(x -> prettyTable.addRow(x.getReview(),
                clientRep.getClientById(x.getIdClient()).getFullName()));
        System.out.println(prettyTable);
    }

    private static void raiseSalary (){

        double salaryThatUsed = 0;

        // сумма всех зарплат
        for (Employee employee1 : employeeRep.getAllEmployee()) {
            salaryThatUsed += employee1.getSalary();
        }

        // выводим список всех работников
        for (Employee employee : employeeRep.getAllEmployee()) {
            System.out.println(employee.getId() + " " + employee.getFullName() + " " + employee.getSalary());
        }
        System.out.print("Введите айди: ");
        long idEmployee = scanner.nextLong();

        salaryThatUsed -= employeeRep.getById(idEmployee).getSalary();
        // поиск по айди
        Employee employee = employeeRep.getById(idEmployee);

        while (true){
            // ввод сумм и подсчет новой заработной платы;
            System.out.print("Сумму на которуй вы хотите повысить: ");
            double upSalary = scanner.nextDouble();
            double refreshSalary = employee.getSalary() + upSalary;

            // проверка на повышение зарплаты, зп должна быть не больше выделеного бюджета
            if(budgetRep.getByBudgetAllocation("Salary budget").getExpenses() > (refreshSalary + salaryThatUsed)){
                // запись в бд
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

        // выводим список всех работников
        for (Employee employee : employeeRep.getAllEmployee()) {
            System.out.println(employee.getId() + " " + employee.getFullName() + " " + employee.getSalary());
        }
        System.out.print("Введите айди: ");
        // поиск по айди
        Employee employee = employeeRep.getById(scanner.nextInt());

        while (true){

            // ввод сумм и подсчет новой заработной платы;
            System.out.print("Сумму на которуй вы хотите понизить: ");
            double upSalary = scanner.nextDouble();
            double refreshSalary = employee.getSalary() - upSalary;

            if(refreshSalary > 0){
                // запись в бд
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
        // регистрация сотриднука
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

        // записываются все данные в бд
        employeeRep.createEmployee(employeeAdd);
    }

    private static void delete () {

        System.out.println("Введите кого вы хотие удалить" +
                "1 - Работник " +
                "2 - Клиент: ");
        switch (scanner.nextInt()){
            // удалить сотрудника
            case 1 -> {
                long deleteEmployee = scanner.nextLong();
                employeeRep.deleteEmployee(deleteEmployee);
            }
            // удалить клиента
            case 2 -> {
                long deleteClinet = scanner.nextLong();
                clientRep.deleteClient(deleteClinet);
            }
            // если введены не коректные данные
            default -> {
                System.out.println("Вы ввели не правильные данные, введите ещё раз");
                delete();
            }
        }
    }
}