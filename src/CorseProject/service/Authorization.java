package CorseProject.service;

import CorseProject.dao.ClientRep;
import CorseProject.dao.EmployeeRep;
import CorseProject.dao.impl.ClientRepImpl;
import CorseProject.dao.impl.EmployeeRepImpl;
import CorseProject.models.Client;

import java.util.Scanner;

public class Authorization {
    private static final EmployeeRep employeeRep = new EmployeeRepImpl();
    private static final ClientRep clientRep = new ClientRepImpl();
    private static final Scanner scanner = new Scanner(System.in);

    public void authotization (){

        loop:
        while (true){

            System.out.print("Введити тип аккаунта\n 1 - Работник ресторана \n 2 - Клиент: ");

            switch (scanner.nextInt()){

                case 1 -> {
                    // authorization for an employee
                    // the employee's password and login are entered
                    Scanner scanner = new Scanner(System.in);
                    System.out.print("Введите логин: ");
                    String login = scanner.nextLine();
                    System.out.print("Введите пароль: ");
                    String password = scanner.nextLine();

                    // we get a list of all employees
                    for (int i = 0; i < employeeRep.getAllEmployee().size(); i++) {
                        // we check the entered data with the data stored in the database
                        if(employeeRep.getAllEmployee().get(i).getLogin().equals(login) &&
                                employeeRep.getAllEmployee().get(i).getPassword().equals(password)){
                            // the account type is taken and checks for matches via switch
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
                                    CashierService.cashierMenu(employeeRep.getAllEmployee().get(i).getId());
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
                            // authorization for the client
                            // data entry
                            System.out.print("Введите логин: ");
                            String login = scanner.next();
                            System.out.print("Введите пароль: ");
                            String password = scanner.next();

                            // we get a list of all employees
                            for (int i = 0; i < clientRep.getAllClient().size(); i++) {
                                // we check the entered data with the data stored in the database
                                if(clientRep.getAllClient().get(i).getLogin().equals(login) &&
                                        clientRep.getAllClient().get(i).getPassword().equals(password)){
                                    System.out.println("Добро пожаловать " + clientRep.getAllClient().get(i).getFullName());
                                    ClientService.clientMenu(clientRep.getAllClient().get(i).getId());
                                    break loop;
                                }
                            }
                        }
                        case 2 -> {
                            // client registration
                            Scanner scanner = new Scanner(System.in);
                            System.out.println("ФИО");
                            String fullName = scanner.nextLine();
                            System.out.println("Логин");
                            String login = scanner.nextLine();
                            System.out.println("Пароль");
                            String password = scanner.nextLine();
                            CorseProject.models.Client clientAdd = new Client(fullName, login, password);
                            // writing data to the database
                            clientRep.createClient(clientAdd);
                            authotization();
                        }
                    }
                }
            }
        }
    }
}