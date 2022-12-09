package CorseProject.service;

import CorseProject.dao.EmployeeRep;
import CorseProject.dao.OrderRep;
import CorseProject.dao.TasksRep;
import CorseProject.dao.impl.EmployeeRepImpl;
import CorseProject.dao.impl.OrderRepImpl;
import CorseProject.dao.impl.TasksRepImpl;
import CorseProject.models.Basket;
import CorseProject.models.Tasks;
import CorseProject.models.enums.BdProcess;
import CorseProject.utils.PrettyTable;

import java.util.List;
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
                case 3 -> completeTask(idEmployee);
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
    //ToDo не трогать!
    private void completeTask(long idEmployee) {
        loop:
        while (true){
            System.out.println(super.showTaskForEmployee());
            System.out.print("Введите номер задания: ");
            int id = scanner.nextInt();

            if(taskCheck(id, idEmployee)) {
                tasksRep.updateTask(id, String.valueOf(BdProcess.FINISHED));
                break;
            } else {
                System.out.println("Данного задания не существует или это задание не ваше");
                System.out.println("""
                        
                        1 - Поторить попытку
                        2 - Вернуться в меню
                        """);
                switch (scanner.nextInt()){
                    case 1 -> System.out.println("Повтор");
                    case 2 -> {
                        break loop;
                    }
                }
            }
        }
    }
    private boolean taskCheck(int id, long idEmployee){
        boolean flag = false;

        for (Tasks task : tasksRep.getAllTasks()) {
            flag = task.getId() == id && task.getIdEmployee() == idEmployee;
        }

        return flag;
    }




    // 5 Меню
    //ToDo не трогать!
    private void completeOrder() {
        loop:
        while (true) {

            super.showUnprocessedOrders();

            System.out.print("Введите номер заказа: ");
            int orderNumber = scanner.nextInt();

            if (orderCheck(orderNumber)) {
                orderRep.updateOrder(orderNumber, String.valueOf(BdProcess.FINISHED));
                System.out.println("Заказ выполнен");
                break;

            } else {
                System.out.println("Вы ввели неверный номер заказа");
                System.out.println("""
                                                
                        1 - Поторить попытку
                        2 - Вернуться в меню
                        """);
                switch (scanner.nextInt()) {
                    case 1 -> System.out.println("Повтор");
                    case 2 -> {
                        break loop;
                    }
                }
            }
        }
    }
    private boolean orderCheck(int orderNumber){
        boolean flag = false;

        for (Basket order : orderRep.getAllOrders()) {
            flag = order.getOrderNumber() == orderNumber && String.valueOf(order.getBdProcess()).equals("RUNNING");
        }

        return flag;
    }



    //ToDO не трогать!
    // 6 Меню
    private PrettyTable showSalary(long idEmployee) {
        PrettyTable prettyTable = new PrettyTable("ФИО","Зарплата");

        prettyTable.addRow(employeeRep.getById(idEmployee).getFullName(),
                String.valueOf(employeeRep.getById(idEmployee).getSalary()));

        return  prettyTable;
    }
}