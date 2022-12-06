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

public class CashierService extends Accounts {

    private static final OrderRep orderRep = new OrderRepImpl();
    private static final TasksRep tasksRep = new TasksRepImpl();
    private static final EmployeeRep employeeRep = new EmployeeRepImpl();
    private static final Scanner scanner = new Scanner(System.in);

    public void cashierMenu(long idEmployee) {
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
                case 1 -> System.out.println(super.showTaskForEmployee());
                case 2 -> super.showFinishedTask();
                case 3 -> completeTask();
                case 4 -> super.showUnprocessedOrders();
                case 5 -> completeOrder();
                case 6 -> System.out.println(showSalary(idEmployee));
                case 7 -> {
                    end();
                    break loop;
                }
                default -> System.out.println("Данные введены не котректно");
            }
        }
    }




    // 3 Меню
    //TODO сделать проверку есть ли номер данного задания
    private void completeTask() {
        System.out.print("Введите номер задания: ");
        int id = scanner.nextInt();
        System.out.print("Выполнили задание? (1 - да,2 - нет) ");

        switch (scanner.nextInt()){
            case 1 -> tasksRep.updateTask(id, String.valueOf(BdProcess.FINISHED));
            case 2 -> System.out.println("Выполните задание");
            default -> System.out.println("Ошибка");
        }
    }




    // 5 Меню
    //TODO сделать проверку есть ли номер данного заказа
    private void completeOrder() {
        System.out.print("Введите номер заказа: ");
        int orderNumber = scanner.nextInt();
        System.out.print("Заказ готов? (1 - да,2 - нет) ");

        switch (scanner.nextInt()){
            case 1 -> orderRep.updateOrder(orderNumber, String.valueOf(BdProcess.FINISHED));
            case 2 -> System.out.println("Выполните задание");
            default -> System.out.println("Ошибка");
        }
    }




    // 6 Меню
    private PrettyTable showSalary(long idEmployee) {
        PrettyTable prettyTable = new PrettyTable("ФИО","Зарплата");

        prettyTable.addRow(employeeRep.getById(idEmployee).getFullName(),
                String.valueOf(employeeRep.getById(idEmployee).getSalary()));

        return  prettyTable;
    }
}