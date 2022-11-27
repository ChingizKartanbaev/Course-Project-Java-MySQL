package CorseProject.service;

import CorseProject.models.enums.BdProcess;
import CorseProject.dao.*;
import CorseProject.dao.impl.*;
import CorseProject.models.Tasks;
import CorseProject.utils.PrettyTable;

import java.util.Scanner;

public class ManagerService {

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
                case 3 -> System.out.println("a");
                case 4 -> System.out.println("b");
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

        // Создание притти тейбл и добавление хедера
        PrettyTable prettyTable = new PrettyTable("Айди","ФИО","Тип аккаунта");

        // добавленние в притти тейбл айди работника, ФИО, тип аккаунта
        employeeRep.getAllEmployee().forEach(x -> prettyTable.addRow(String.valueOf(x.getId()),x.getFullName(),
                x.getTypeOfAccount()));

        return prettyTable;
    }

    public static PrettyTable showReview() {

        // Создание притти тейбл и добавление хедера
        PrettyTable prettyTable = new PrettyTable("Отзыв", "Имя клиента");

        // Через любду функции добовляем в притти тейбл отзыв и айди клиента
        reviewsRep.getAllReviews().forEach(x -> prettyTable.addRow(x.getReview(), String.valueOf(x.getIdClient())));

        return prettyTable;
    }

    private static void writeTaskForEmployee() {
        // заглушка что-бы работал сканннер
        String s = scanner.nextLine();

        // вводятся задание для сотрудника
        System.out.println("Введите задание для работника: ");
        String task = scanner.nextLine();

        // вводится айди сотрудника
        System.out.println("Введите айди работника: ");
        long idEmployee = scanner.nextLong();

        // проверка на пустоту
        if(task.isEmpty()){

            System.out.println("Нужно ввести данные");

        } else {

            // проверка на тип аккаунта, так как мы не можем дать задание директору или же менеджеру
            if(employeeRep.getByTypeOfAccount("Direktor").getId() != idEmployee &&
                    employeeRep.getByTypeOfAccount("Manager").getId() != idEmployee){

                Tasks tasks = new Tasks(task, idEmployee, BdProcess.RUNNING);
                // запись в бд
                tasksRep.createTasks(tasks);

            }else {
                System.out.println("Извините но вы не можете назначить задание данному сотруднику");
            }
        }
    }

    public static PrettyTable showTaskForEmployee() {

        PrettyTable prettyTable = new PrettyTable("Заднание", "Айди сотрудника", "Имя сотрудника");

        // через потоки фильтруем по "RUNNING" если полученные данные соответсвуют условию они добавляются в притти тебйл
        tasksRep.getAllTasks().stream().filter(x -> x.getProcess().equals("RUNNING")).forEach(x ->
                prettyTable.addRow(x.getTask(), String.valueOf(x.getIdEmployee()),
                        employeeRep.getById(x.getIdEmployee()).getFullName()));

        return prettyTable;
    }


    public static void showFinishedTask() {

        PrettyTable prettyTable = new PrettyTable("Заднание", "Айди сотрудника", "Имя сотрудника");

        // через потоки фильтруем по "FINISHED" если полученные данные соответсвуют условию они добавляются в притти тебйл
        tasksRep.getAllTasks().stream().filter(x -> x.getProcess().equals("FINISHED")).forEach(x ->
                prettyTable.addRow(x.getTask(), String.valueOf(x.getIdEmployee()),
                        employeeRep.getById(x.getIdEmployee()).getFullName()));

        System.out.println(prettyTable);
    }

    public static PrettyTable showAListOfAllCoverageAreas() {

        // выводит список зон покрытия
        PrettyTable prettyTable = new PrettyTable("City Name", "Coverage area");

        // Через любду функции добовляем в притти тейбл отзыв название страны в и прибыль с данной странный
        reportManagerRep.getAllReports().forEach(x -> prettyTable.addRow(x.getCityName(), x.getCustomerCoverageArea()));

        return prettyTable;
    }
}
