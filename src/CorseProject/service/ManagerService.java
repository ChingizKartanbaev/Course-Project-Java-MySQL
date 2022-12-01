package CorseProject.service;

import CorseProject.models.Basket;
import CorseProject.models.enums.BdProcess;
import CorseProject.dao.*;
import CorseProject.dao.impl.*;
import CorseProject.models.Tasks;
import CorseProject.utils.PrettyTable;

import java.util.Scanner;

public class ManagerService {

    public static OrderRep orderRep = new OrderRepImpl();
    public static EmployeeRep employeeRep = new EmployeeRepImpl();
    public static ReviewsRep reviewsRep = new ReviewsRepImpl();
    public static ReportManagerRep reportManagerRep = new ReportManagerRepImpl();
    public static TasksRep tasksRep = new TasksRepImpl();
    public static Scanner scanner = new Scanner(System.in);


    public static void managerMenu() {

        loop:
        while (true){
            System.out.println("""

                Выберете меню\s
                1 - Показать список сотрудников
                2 - Посмотреть отзывы
                3 - Показать список заказов
                4 - Показать список завершеных закаов
                5 - Ввести задания для сотрудников
                6 - Показать список заданий для сотрудников
                7 - Показать список выполненых заданий для сотрудников
                8 - Показать список всех зон покрытия
                9 - Выход
                """);

            switch (scanner.nextInt()) {

                case 1 -> System.out.println(showEmployee());
                case 2 -> System.out.println(showReview());
                case 3 -> showUnprocessedOrders();
                case 4 -> showFinishedOrders();
                case 5 -> writeTaskForEmployee();
                case 6 -> System.out.println(showTaskForEmployee());
                case 7 -> showFinishedTask();
                case 8 -> System.out.println(showAListOfAllCoverageAreas());
                case 9 -> {
                    System.out.print("Программа завершена, мы будем рады вашему возвращению!");
                    break loop;
                }
                default -> System.out.println("Данные введены не котректно");
            }
        }
    }



    public static PrettyTable showEmployee() {

        // Creating a come table and adding a header
        PrettyTable prettyTable = new PrettyTable("Айди","ФИО","Тип аккаунта");

        // adding an employee, full name, account type to the pretty table
        employeeRep.getAllEmployee().forEach(x -> prettyTable.addRow(String.valueOf(x.getId()),x.getFullName(),
                x.getTypeOfAccount()));

        return prettyTable;
    }



    public static PrettyTable showReview() {

        // Creating a come table and adding a header
        PrettyTable prettyTable = new PrettyTable("Отзыв", "Имя клиента");

        // Through the people of the function, we add a review and a client's ID to the pritti table
        reviewsRep.getAllReviews().forEach(x -> prettyTable.addRow(x.getReview(), String.valueOf(x.getIdClient())));

        return prettyTable;
    }



    public static void showUnprocessedOrders() {
        PrettyTable prettyTable = new PrettyTable("Клиент", "Название", "Кол", "Стоимость");

        for (Basket basket : orderRep.getAllOrders()) {
            if(String.valueOf(basket.getBdProcess()).equals("RUNNING")){
                prettyTable.addRow(String.valueOf(basket.getIdClient()), basket.getOrder(), String.valueOf(basket.getAmount()),
                        String.valueOf(basket.getCost()));
            }
        }

        System.out.println(prettyTable);
    }



    public static void showFinishedOrders() {
        PrettyTable prettyTable = new PrettyTable("Клиент", "Название", "Кол", "Стоимость");

        for (Basket basket : orderRep.getAllOrders()) {
            if(String.valueOf(basket.getBdProcess()).equals("FINISHED")){
                prettyTable.addRow(String.valueOf(basket.getIdClient()), basket.getOrder(), String.valueOf(basket.getAmount()),
                        String.valueOf(basket.getCost()));
            }
        }

        System.out.println(prettyTable);
    }



    private static void writeTaskForEmployee() {
        // plug
        String s = scanner.nextLine();

        // the task for the employee is entered
        System.out.println("Введите задание для работника: ");
        String task = scanner.nextLine();

        // the employee's ID is entered
        System.out.println("Введите айди работника: ");
        long idEmployee = scanner.nextLong();

        // checking for emptiness
        if(task.isEmpty()){

            System.out.println("Нужно ввести данные");

        } else {

            // checking for the type of account, since we cannot give a task to the director or manager
            if(employeeRep.getByTypeOfAccount("Direktor").getId() != idEmployee &&
                    employeeRep.getByTypeOfAccount("Manager").getId() != idEmployee){

                Tasks tasks = new Tasks(task, idEmployee, BdProcess.RUNNING);
                // database entry
                tasksRep.createTasks(tasks);

            }else {
                System.out.println("Извините но вы не можете назначить задание данному сотруднику");
            }
        }
    }



    public static PrettyTable showTaskForEmployee() {

        PrettyTable prettyTable = new PrettyTable("Заднание", "Айди сотрудника", "Имя сотрудника");

        // through the streams, we filter by "RUNNING" if the received data meets the condition, they are added to the pretty table
        tasksRep.getAllTasks().stream().filter(x -> x.getProcess().equals("RUNNING")).forEach(x ->
                prettyTable.addRow(x.getTask(), String.valueOf(x.getIdEmployee()),
                        employeeRep.getById(x.getIdEmployee()).getFullName()));

        return prettyTable;
    }



    public static void showFinishedTask() {

        PrettyTable prettyTable = new PrettyTable("Заднание", "Айди сотрудника", "Имя сотрудника");

        // through the streams, we filter by "FINISHED" if the received data meets the condition, they are added to the pretty table
        tasksRep.getAllTasks().stream().filter(x -> x.getProcess().equals("FINISHED")).forEach(x ->
                prettyTable.addRow(x.getTask(), String.valueOf(x.getIdEmployee()),
                        employeeRep.getById(x.getIdEmployee()).getFullName()));

        System.out.println(prettyTable);
    }



    public static PrettyTable showAListOfAllCoverageAreas() {

        // displays a list of coverage areas
        PrettyTable prettyTable = new PrettyTable("City Name", "Coverage area");

        // Through the people of the function, we add to the pretty table a review of the name of the country in and the profit from this strange
        reportManagerRep.getAllReports().forEach(x -> prettyTable.addRow(x.getCityName(), x.getCustomerCoverageArea()));

        return prettyTable;
    }
}
