package CorseProject.service;

import CorseProject.dao.ClientRep;
import CorseProject.dao.EmployeeRep;
import CorseProject.dao.ReportManagerRep;
import CorseProject.dao.ReviewsRep;
import CorseProject.dao.impl.ClientRepImpl;
import CorseProject.dao.impl.EmployeeRepImpl;
import CorseProject.dao.impl.ReportManagerRepImpl;
import CorseProject.dao.impl.ReviewsRepImpl;
import CorseProject.models.Employee;

import java.util.Scanner;

public class DirektorService {

    public static EmployeeRep employeeRep = new EmployeeRepImpl();
    public static ClientRep clientRep = new ClientRepImpl();
    public static ReportManagerRep reportManagerRep = new ReportManagerRepImpl();
    public static ReviewsRep reviewsRep = new ReviewsRepImpl();
    public static Scanner scanner = new Scanner(System.in);

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

                case 2 -> System.out.println("Бюджет каоторый испольщуется: " + budget());

                case 3 -> System.out.println("redact");

                case 4 -> showReview();

                case 5 -> System.out.println(raiseSalary());

                case 6 -> System.out.println(lowerSalary());

                case 7 -> register();

                case 8 -> delete();

                case 9 -> {
                    System.out.println("До свидания!");
                    break loop;
                }

            }
        }
    }

    public static void showAListOfAllCoverageAreas (){
        // выводит список зон покрытия
        for (int i = 0; i < reportManagerRep.getAllReports().size(); i++) {
            System.out.printf(reportManagerRep.getAllReports().get(i).getCityName() + "%15s",
                    reportManagerRep.getAllReports().get(i).getCustomerCoverageArea() + "\n");}
    }

    public static double budget() {

        double totalBuget = 1000000;
        double budgetThatUsed = 0;

        // получает весь список сотрудников и через employee.getSalary() суммирует общюю сумму
        for (Employee employee : employeeRep.getAllEmployee()) {
            budgetThatUsed += employee.getSalary();
        }
        // принтует общий бюджет
        System.out.println("Общий бюджет: " + totalBuget + "\n");
        // возращает сумму ислоьзованую для заработной платы
        return totalBuget - (totalBuget - budgetThatUsed);
    }

    public static void showReview (){
        for (int i = 0; i < reviewsRep.getAllReviews().size(); i++) {
            System.out.printf(reviewsRep.getAllReviews().get(i).getReview() + "%15s",
                    reviewsRep.getAllReviews().get(i).getIdClient() + "\n");}
    }

    public static void register (){

        System.out.print("Введите данные работника" );
        // регистрация сотриднука
        System.out.println("Имя");
        String fullName = scanner.nextLine();
        System.out.println("Логин");
        String login = scanner.nextLine();
        System.out.println("Пароль");
        String password = scanner.nextLine();
        System.out.println("Тип аккаунта");
        String typeOfAccount = scanner.nextLine();
        System.out.println("Зарабатную плату");
        double salary = scanner.nextDouble();
        Employee employeeAdd = new Employee(fullName, login, password, typeOfAccount, salary);

        // записываются все данные в бд
        employeeRep.createEmployee(employeeAdd);
    }

    public static void delete () {

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

    public static String raiseSalary (){

        // выводим список всех работников
        for (Employee employee : employeeRep.getAllEmployee()) {
            System.out.println(employee.getId() + " " + employee.getFullName() + " " + employee.getSalary());
        }
        System.out.print("Введите айди: ");

        // поиск по айди
        Employee employee = employeeRep.getById(scanner.nextInt());

        // ввод сумм и подсчет новой заработной платы;
        System.out.print("Сумму на которуй вы хотите повысить: ");
        double upSalary = scanner.nextDouble();
        double refreshSalary = employee.getSalary() + upSalary;

        // запись в бд
        employeeRep.updateEmployeeSalary((int) employee.getId(), refreshSalary);
        return "Зарплата была поднята!";
    }

    public static String lowerSalary (){
        // выводим список всех работников
        for (Employee employee : employeeRep.getAllEmployee()) {
            System.out.println(employee.getId() + " " + employee.getFullName() + " " + employee.getSalary());
        }
        System.out.print("Введите айди: ");

        // поиск по айди
        Employee employee = employeeRep.getById(scanner.nextInt());

        // ввод сумм и подсчет новой заработной платы;
        System.out.print("Сумму на которуй вы хотите понизить: ");
        double upSalary = scanner.nextDouble();
        double refreshSalary = employee.getSalary() - upSalary;

        // запись в бд
        employeeRep.updateEmployeeSalary((int) employee.getId(), refreshSalary);
        return "Зарплата была понижена!";
    }
}