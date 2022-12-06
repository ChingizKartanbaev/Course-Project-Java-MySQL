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

                case 1 -> System.out.println(super.showEmployee());
                case 2 -> System.out.println(super.showReview());
                case 3 -> super.showUnprocessedOrders();
                case 4 -> showFinishedOrders();
                case 5 -> writeTaskForEmployee();
                case 6 -> System.out.println(super.showTaskForEmployee());
                case 7 -> super.showFinishedTask();
                case 8 -> System.out.println(super.showAListOfAllCoverageAreas());
                case 9 -> {
                    end();
                    break loop;
                }
                default -> System.out.println("Данные введены не котректно");
            }
        }
    }


    // 4 Меню
    //TODO не трогать!
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
    //TODO сделать проверки
    private void writeTaskForEmployee() {
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
}