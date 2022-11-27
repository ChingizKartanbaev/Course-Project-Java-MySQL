package CorseProject.service;

import CorseProject.dao.EmployeeRep;
import CorseProject.dao.TasksRep;
import CorseProject.dao.impl.EmployeeRepImpl;
import CorseProject.dao.impl.TasksRepImpl;
import CorseProject.models.enums.BdProcess;
import CorseProject.utils.PrettyTable;

import java.util.Scanner;

public class CashierService {

    private static final TasksRep tasksRep = new TasksRepImpl();
    private static final EmployeeRep employeeRep = new EmployeeRepImpl();
    private static final Scanner scanner = new Scanner(System.in);
    public static void cashierMenu() {

        //TODO меню 4, 5
        loop:
        while (true){

            System.out.println("""

                    Выберете меню\s
                    1 - Показать список порученных мне дел
                    2 - Показать список завершенных указаний
                    3 - Выполнить порученое мне дело
                    4 - Показать заказы, которые требуют обработки
                    5 - Обработать заказ
                    6 - Показать зарплату
                    7 - Выход
                    """);

            switch (scanner.nextInt()){
                case 1 -> System.out.println(showTaskForEmployee());
                case 2 -> showFinishedTask();
                case 3 -> completeTask();
                case 4 -> System.out.println("wd");
                case 5 -> System.out.println("g");
                case 6 -> System.out.println(showSalary());
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
                break;
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

    public static PrettyTable showSalary() {
        PrettyTable prettyTable = new PrettyTable("ФИО","Зарплата");
        System.out.print("Введите свой айди: ");
        long idEmployee = scanner.nextLong();

        prettyTable.addRow(employeeRep.getById(idEmployee).getFullName(),
                String.valueOf(employeeRep.getById(idEmployee).getSalary()));

        return  prettyTable;
    }
}
