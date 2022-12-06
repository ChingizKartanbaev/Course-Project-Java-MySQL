package CorseProject.service;

import CorseProject.dao.*;
import CorseProject.dao.impl.*;
import CorseProject.models.Basket;
import CorseProject.utils.PrettyTable;

public abstract class Accounts {

    private static final ReportManagerRep reportManagerRep = new ReportManagerRepImpl();
    private static final ReviewsRep reviewsRep = new ReviewsRepImpl();
    private static final OrderRep orderRep = new OrderRepImpl();
    private static final TasksRep tasksRep = new TasksRepImpl();
    private static final EmployeeRep employeeRep = new EmployeeRepImpl();

    //TODO продумать!
    protected PrettyTable showAListOfAllCoverageAreas (){
        // displays a list of coverage areas
        PrettyTable prettyTable = new PrettyTable("City Name", "Coverage area");

        // through forEach, the name of the city and the profit from the city are added to the pretty table
        reportManagerRep.getAllReports().forEach(x -> prettyTable.addRow(x.getCityName(),x.getCustomerCoverageArea()));
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
        PrettyTable prettyTable = new PrettyTable("Заднание", "Айди сотрудника", "Имя сотрудника");

        // through the streams, we filter by "RUNNING" if the received data meets the condition, they are added to the pretty table
        tasksRep.getAllTasks().stream().filter(x -> x.getProcess().equals("RUNNING")).forEach(x ->
                prettyTable.addRow(x.getTask(), String.valueOf(x.getIdEmployee()),
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
        PrettyTable prettyTable = new PrettyTable("Клиент", "Название", "Кол", "Стоимость");

        for (Basket basket : orderRep.getAllOrders()) {
            if(String.valueOf(basket.getBdProcess()).equals("RUNNING")){
                prettyTable.addRow(String.valueOf(basket.getIdClient()), basket.getOrder(), String.valueOf(basket.getAmount()),
                        String.valueOf(basket.getCost()));
            }
        }

        System.out.println(prettyTable);
    }


    //TODO не трогать!
    protected PrettyTable showEmployee() {
        // Creating a come table and adding a header
        PrettyTable prettyTable = new PrettyTable("Айди","ФИО","Тип аккаунта");

        // adding an employee, full name, account type to the pretty table
        employeeRep.getAllEmployee().forEach(x -> prettyTable.addRow(String.valueOf(x.getId()),x.getFullName(),
                x.getTypeOfAccount()));

        return prettyTable;
    }


    //TODO не трогать!
    protected void end() {
        System.out.println("Программа завершена, мы будем рады вашему возвращению!");
    }
}
