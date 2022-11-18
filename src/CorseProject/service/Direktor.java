package CorseProject.service;

import CorseProject.dao.ClientRep;
import CorseProject.dao.EmployeeRep;
import CorseProject.dao.impl.ClientRepImpl;
import CorseProject.dao.impl.EmployeeRepImpl;
import CorseProject.models.Client;
import CorseProject.models.Employee;
import java.util.Scanner;

public class Direktor {

    public static EmployeeRep employeeRep = new EmployeeRepImpl();
    public static ClientRep clientRep = new ClientRepImpl();
    public static Scanner scanner = new Scanner(System.in);

    public static void showMenu (){

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
            case 1 -> System.out.println("list");

            case 2 -> System.out.println("Бюджет каоторый испольщуется: " + budget());

            case 3 -> System.out.println("redact");

            case 4 -> System.out.println("reviews");

            case 5 -> System.out.println(raiseSalary());

            case 6 -> System.out.println(lowerSalary());

            case 7 -> register();

            case 8 -> delete();

            case 9 -> System.out.println("До свидания!");
        }
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

    public static void register (){

        System.out.print("Введите кого вы хотие зарегистрировать" +
                "1 - Работник " +
                "2 - Клиент: ");
        switch (scanner.nextInt()){

            //регистрация сотриднука
            case 1 -> {
                String fullName = scanner.nextLine();
                String login = scanner.nextLine();
                String password = scanner.nextLine();
                String typeOfAccount = scanner.nextLine();
                double salary = scanner.nextDouble();
                Employee employeeAdd = new Employee(fullName, login, password, typeOfAccount, salary);

                employeeRep.createEmployee(employeeAdd);
            }

            //регистрация клиента
            case 2 -> {
                Scanner scanner = new Scanner(System.in);
                String fullName = scanner.nextLine();
                String login = scanner.nextLine();
                String password = scanner.nextLine();
                CorseProject.models.Client clientAdd = new Client(fullName, login, password);

                clientRep.createClient(clientAdd);
            }
        }
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

        //запись в бд
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

        //запись в бд
        employeeRep.updateEmployeeSalary((int) employee.getId(), refreshSalary);
        return "Зарплата была понижена!";
    }
}