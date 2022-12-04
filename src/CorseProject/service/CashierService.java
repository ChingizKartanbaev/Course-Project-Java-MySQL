package CorseProject.service;

import CorseProject.dao.EmployeeRep;
import CorseProject.dao.OrderRep;
import CorseProject.dao.TasksRep;
import CorseProject.dao.impl.EmployeeRepImpl;
import CorseProject.dao.impl.OrderRepImpl;
import CorseProject.dao.impl.TasksRepImpl;
import CorseProject.models.Basket;
import CorseProject.models.enums.BdProcess;
import CorseProject.utils.PrettyTable;

import java.util.Scanner;

public class CashierService {

    private static final OrderRep orderRep = new OrderRepImpl();
    private static final TasksRep tasksRep = new TasksRepImpl();
    private static final EmployeeRep employeeRep = new EmployeeRepImpl();
    private static final Scanner scanner = new Scanner(System.in);
    public static void cashierMenu(long idEmployee) {

        //TODO меню 4, 5
        loop:
        while (true){

            System.out.println("""

                    Выберете меню\s
                    1 - Показать список порученных дел
                    2 - Показать список завершенных указаний
                    3 - Выполнить порученое дело
                    4 - Показать заказы, которые требуют обработки
                    5 - Обработать заказ
                    6 - Показать зарплату
                    7 - Выход
                    """);

            switch (scanner.nextInt()){
                case 1 -> System.out.println(showTaskForEmployee());
                case 2 -> showFinishedTask();
                case 3 -> completeTask();
                case 4 -> showUnprocessedOrders();
                case 5 -> completeOrder();
                case 6 -> System.out.println(showSalary(idEmployee));
                case 7 -> {
                    System.out.println("Программа завершена, мы будем рады вашему возвращению!");
                    break loop;
                }
                default -> System.out.println("Данные введены не котректно");
            }
        }
    }



    public static PrettyTable showTaskForEmployee() {

        PrettyTable prettyTable = new PrettyTable("Заднание", "Айди сотрудника", "Имя сотрудника");

        for (int i = 0; i < tasksRep.getAllTasks().size(); i++) {

            if(tasksRep.getAllTasks().get(i).getProcess().equals("RUNNING")){
                prettyTable.addRow(tasksRep.getAllTasks().get(i).getTask(),
                        String.valueOf(tasksRep.getAllTasks().get(i).getIdEmployee()),
                        employeeRep.getById(tasksRep.getAllTasks().get(i).getIdEmployee()).getFullName());
            }
        }

        return prettyTable;
    }



    public static void showFinishedTask() {

        PrettyTable prettyTable = new PrettyTable("Заднание", "Айди сотрудника", "Имя сотрудника");

        for (int i = 0; i < tasksRep.getAllTasks().size(); i++) {
            if(tasksRep.getAllTasks().get(i).getProcess().equals("FINISHED")){
                prettyTable.addRow(tasksRep.getAllTasks().get(i).getTask(),
                        String.valueOf(tasksRep.getAllTasks().get(i).getIdEmployee()),
                        employeeRep.getById(tasksRep.getAllTasks().get(i).getIdEmployee()).getFullName());
                System.out.println(prettyTable);
            }else {
                System.out.println("Задания ещё не выполнены");
            }
        }
    }



    public static void completeTask() {

        System.out.print("Введите номер задания: ");
        int id = scanner.nextInt();
        System.out.print("Выполнили задание? (1 - да,2 - нет) ");

        switch (scanner.nextInt()){
            case 1 -> tasksRep.updateTask(id, String.valueOf(BdProcess.FINISHED));
            case 2 -> System.out.println("Выполните задание");
            default -> System.out.println("Ошибка");
        }
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



    public static void completeOrder() {
        System.out.print("Введите номер заказа: ");
        int orderNumber = scanner.nextInt();
        System.out.print("Заказ готов? (1 - да,2 - нет) ");

        switch (scanner.nextInt()){
            case 1 -> orderRep.updateOrder(orderNumber, String.valueOf(BdProcess.FINISHED));
            case 2 -> System.out.println("Выполните задание");
            default -> System.out.println("Ошибка");
        }
    }


    public static PrettyTable showSalary(long idEmployee) {
        PrettyTable prettyTable = new PrettyTable("ФИО","Зарплата");

        prettyTable.addRow(employeeRep.getById(idEmployee).getFullName(),
                String.valueOf(employeeRep.getById(idEmployee).getSalary()));

        return  prettyTable;
    }
}
