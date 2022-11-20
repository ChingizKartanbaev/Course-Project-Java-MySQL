package CorseProject.service;

import CorseProject.dao.ClientRep;
import CorseProject.dao.EmployeeRep;
import CorseProject.dao.impl.ClientRepImpl;
import CorseProject.dao.impl.EmployeeRepImpl;
import CorseProject.models.Client;

import java.util.Scanner;

public class Authorization {
    public static EmployeeRep employeeRep = new EmployeeRepImpl();
    public static ClientRep clientRep = new ClientRepImpl();
    public static Scanner scanner = new Scanner(System.in);

    public void authotization (){

        loop:
        while (true){

            System.out.print("Введити тип аккаунта\n 1 - Работник ресторана \n 2 - Клиент: ");

            switch (scanner.nextInt()){

                case 1 -> {
                    // авторизация для сотрудника
                    // вводится пароль и логин сотрудника
                    Scanner scanner = new Scanner(System.in);
                    System.out.print("Введите логин: ");
                    String login = scanner.nextLine();
                    System.out.print("Введите пароль: ");
                    String password = scanner.nextLine();

                    // получаем список всех работников
                    for (int i = 0; i < employeeRep.getAllEmployee().size(); i++) {
                        // проверяем введеные данные с данными которые хранятся в бд
                        if(employeeRep.getAllEmployee().get(i).getLogin().equals(login) &&
                                employeeRep.getAllEmployee().get(i).getPassword().equals(password)){
                            //берется тип аккаунта и через switch проверяет совпадения
                            switch (employeeRep.getAllEmployee().get(i).getTypeOfAccount()){
                                case "Direktor" -> {System.out.println("\nДобро пожаловать: " +
                                        employeeRep.getAllEmployee().get(i).getFullName());
                                    DirektorService.showMenu();
                                    break loop;
                                }
                                case "Manager" -> {System.out.println("\nДобро пожаловать: " +
                                        employeeRep.getAllEmployee().get(i).getFullName());
                                    ManagerService.managerMenu();
                                    break loop;
                                }
                                case "Cashier" -> {System.out.println("\nДобро пожаловать: " +
                                        employeeRep.getAllEmployee().get(i).getFullName());
                                    CashierService.cashierMenu();
                                    break loop;
                                }
                            }
                        }
                    }
                    System.out.println("Данный сотрудник не был найден");
                }

                case 2 -> {
                    System.out.println("1 - Войти \n2 - Регистрация");

                    switch (scanner.nextInt()){
                        case 1 -> {
                            // авторизация для клиента
                            // ввод данных
                            System.out.print("Введите логин: ");
                            String login = scanner.next();
                            System.out.print("Введите пароль: ");
                            String password = scanner.next();

                            // получаем список всех работников
                            for (int i = 0; i < clientRep.getAllClient().size(); i++) {
                                // проверяем введеные данные с данными которые хранятся в бд
                                if(clientRep.getAllClient().get(i).getLogin().equals(login) &&
                                        clientRep.getAllClient().get(i).getPassword().equals(password)){
                                    System.out.println("Добро пожаловать " + clientRep.getAllClient().get(i).getFullName());
                                    ClientService.clientMenu();
                                }
                            }
                        }
                        case 2 -> {
                            //регистрация клиента
                            Scanner scanner = new Scanner(System.in);
                            String fullName = scanner.nextLine();
                            String login = scanner.nextLine();
                            String password = scanner.nextLine();
                            CorseProject.models.Client clientAdd = new Client(fullName, login, password);
                            // запись данных в бд
                            clientRep.createClient(clientAdd);
                        }
                    }

                }
            }
        }
    }
}