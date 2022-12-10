package CorseProject.service;

import CorseProject.dao.*;
import CorseProject.dao.impl.*;
import CorseProject.models.Basket;
import CorseProject.models.Employee;
import CorseProject.models.enums.BdProcess;
import CorseProject.utils.PrettyTable;

import java.util.Scanner;


public abstract class Accounts {

    private static final ReportManagerRep reportManagerRep = new ReportManagerRepImpl();
    private static final ReviewsRep reviewsRep = new ReviewsRepImpl();
    private static final ClientRep clientRep = new ClientRepImpl();
    private static final ProductRep productRep = new ProductRepImp();
    private static final OrderRep orderRep = new OrderRepImpl();
    private static final TasksRep tasksRep = new TasksRepImpl();
    private static final EmployeeRep employeeRep = new EmployeeRepImpl();


    //TODO продумать!
    protected PrettyTable showAListOfAllCoverageAreas (){
        Scanner scanner = new Scanner(System.in);
        System.out.println("""
                1 - Список сотрудников
                2 - Список клиентов
                3 - Меню
                """);

        switch (scanner.nextInt()){
            case 1 -> System.out.println(showListOfEmployee());
            case 2 -> System.out.println(showListOfClient());
            case 3 -> System.out.println(showMenu());
        }

        // displays a list of coverage areas
        PrettyTable prettyTable = new PrettyTable("Количество сотрудников", "Количество клиентвов",
                "Количество позиций в меню");

        // through forEach, the name of the city and the profit from the city are added to the pretty table
        prettyTable.addRow(String.valueOf(employeeRep.getAllEmployee().size()), String.valueOf(clientRep.getAllClient().size()),
                String.valueOf(productRep.getAllProduct().size()));
        return prettyTable;
    }


    //ToDo не трогать
    protected PrettyTable showListOfEmployee() {
        PrettyTable prettyTable = new PrettyTable("Id сотрудние", "Фамилия и Имя сотрудника", "Должность", "Зарплата");
        employeeRep.getAllEmployee().forEach(x -> prettyTable.addRow(String.valueOf(x.getId()), x.getFullName(),
                x.getTypeOfAccount(), String.valueOf(x.getSalary())));
        return prettyTable;
    }

    //ToDo не трогать
    protected PrettyTable showListOfClient() {
        PrettyTable prettyTable = new PrettyTable("Id клиента", "Фамилия и Имя клиента");
        clientRep.getAllClient().forEach(x -> prettyTable.addRow(String.valueOf(x.getId()), x.getFullName()));
        return prettyTable;
    }

    //ToDo не трогать
    protected PrettyTable showMenu() {
        PrettyTable prettyTable = new PrettyTable("Id", "Название пробукта", "Стоимость");
        productRep.getAllProduct().forEach(x -> prettyTable.addRow(String.valueOf(x.getIdProduct()),
                x.getName(), String.valueOf(x.getCost())));

        return prettyTable;
    }


    //TODO не трогать!
    protected PrettyTable showReview() {
        // Creating a come table and adding a header
        PrettyTable prettyTable = new PrettyTable("Отзыв", "Имя клиента");

        // Through the people of the function, we add a review and a client's ID to the pritti table
        reviewsRep.getAllReviews().forEach(x -> prettyTable.addRow(x.getReview(), String.valueOf(x.getIdClient())));

        return prettyTable;
    }


    //TODO не трогать!
    protected PrettyTable showTaskForEmployee() {
        PrettyTable prettyTable = new PrettyTable( "Id", "Заднание", "Айди сотрудника", "Имя сотрудника");

        // through the streams, we filter by "RUNNING" if the received data meets the condition, they are added to the pretty table
        tasksRep.getAllTasks().stream().filter(x -> x.getProcess().equals("RUNNING")).forEach(x ->
                prettyTable.addRow(String.valueOf(x.getId()), x.getTask(), String.valueOf(x.getIdEmployee()),
                        employeeRep.getById(x.getIdEmployee()).getFullName()));

        return prettyTable;
    }


    //TODO не трогать!
    protected void showFinishedTask() {
        PrettyTable prettyTable = new PrettyTable("Заднание", "Айди сотрудника", "Имя сотрудника");

        // through the streams, we filter by "FINISHED" if the received data meets the condition, they are added to the pretty table
        tasksRep.getAllTasks().stream().filter(x -> x.getProcess().equals("FINISHED")).forEach(x ->
                prettyTable.addRow(x.getTask(), String.valueOf(x.getIdEmployee()),
                        employeeRep.getById(x.getIdEmployee()).getFullName()));

        System.out.println(prettyTable);
    }


    //TODO не трогать!
    protected void showUnprocessedOrders() {
        PrettyTable prettyTable = new PrettyTable("Клиент", "Название", "Кол", "Стоимость", "Номер заказа");

        for (Basket basket : orderRep.getAllOrders()) {
            if(String.valueOf(basket.getBdProcess()).equals("RUNNING")){
                prettyTable.addRow(String.valueOf(basket.getIdClient()), basket.getOrder(), String.valueOf(basket.getAmount()),
                        String.valueOf(basket.getCost()), String.valueOf(basket.getOrderNumber()));
            }
        }

        System.out.println(prettyTable);
    }


    //TODO не трогать!
    protected void end() {
        System.out.println("Программа завершена, мы будем рады вашему возвращению!");
    }
}
