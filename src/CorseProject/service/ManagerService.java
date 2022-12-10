package CorseProject.service;

import CorseProject.models.Basket;
import CorseProject.models.enums.BdProcess;
import CorseProject.dao.*;
import CorseProject.dao.impl.*;
import CorseProject.models.Tasks;
import CorseProject.utils.PrettyTable;

import java.util.Scanner;

public class ManagerService extends Accounts {

    public static OrderRep orderRep = new OrderRepImpl();
    public static EmployeeRep employeeRep = new EmployeeRepImpl();
    public static TasksRep tasksRep = new TasksRepImpl();
    public static Scanner scanner = new Scanner(System.in);


    public void managerMenu() {

        loop:
        while (true){
            System.out.println("""

                Выберете меню\s
                1 - Посмотреть отзывы
                2 - Показать список заказов
                3 - Показать список завершеных закаов
                4 - Ввести задания для сотрудников
                5 - Показать список заданий для сотрудников
                6 - Показать список выполненых заданий для сотрудников
                7 - Показать список всех зон покрытия
                8 - Выход
                """);

            switch (scanner.nextInt()) {

                case 1 -> System.out.println(super.showReview());
                case 2 -> super.showUnprocessedOrders();
                case 3 -> showFinishedOrders();
                case 4 -> writeTaskForEmployee();
                case 5 -> System.out.println(super.showTaskForEmployee());
                case 6 -> super.showFinishedTask();
                case 7 -> super.showAListOfAllCoverageAreas();
                case 8 -> {
                    end();
                    break loop;
                }
                default -> System.out.println("Данные введены не котректно");
            }
        }
    }



    // 4 Меню
    private void showFinishedOrders() {
        PrettyTable prettyTable = new PrettyTable("Клиент", "Название", "Кол", "Стоимость");

        for (Basket basket : orderRep.getAllOrders()) {
            if(String.valueOf(basket.getBdProcess()).equals("FINISHED")){
                prettyTable.addRow(String.valueOf(basket.getIdClient()), basket.getOrder(), String.valueOf(basket.getAmount()),
                        String.valueOf(basket.getCost()));
            }
        }

        System.out.println(prettyTable);
    }



    // 5 Меню
    private void writeTaskForEmployee() {
        // plug
        String s = scanner.nextLine();

        // the task for the employee is entered
        System.out.println("Введите задание для работника: ");
        String task = scanner.nextLine();

        // the employee's ID is entered
        System.out.println("Введите айди работника: ");
        long idEmployee = scanner.nextLong();

        if(checkTask(task) && checkEmployee(idEmployee)) {
            Tasks tasks = new Tasks(task, idEmployee, BdProcess.RUNNING);
            // database entry
            tasksRep.createTasks(tasks);
        } else {
            System.out.println("Вы не ввели задание или вы не можете назачить задание данному сотруднику");
            writeTaskForEmployee();
        }
    }

    private boolean checkTask(String task) {
        return !task.isEmpty();
    }

    private boolean checkEmployee(long idEmployee){
        return employeeRep.getByTypeOfAccount("Direktor").getId() != idEmployee;
    }
}