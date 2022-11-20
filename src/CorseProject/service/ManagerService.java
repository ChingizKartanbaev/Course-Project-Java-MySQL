package CorseProject.service;

import CorseProject.dao.EmployeeRep;
import CorseProject.dao.impl.EmployeeRepImpl;

import java.util.Scanner;

public class ManagerService {

    public static Scanner scanner = new Scanner(System.in);
    public static EmployeeRep employeeRep = new EmployeeRepImpl();

    public static void managerMenu (){

        System.out.println("""

                Выберете меню\s
                1 - Показать список сотрудников
                2 - Посмотреть отзывы
                3 - Показать список заказов
                4 - Ввести задания для сотрудников
                5 - Показать список всех зон покрытия
                6 - Выход
                """);

        switch (scanner.nextInt()){

            case 1 -> {
                for (int i = 0; i < employeeRep.getAllEmployee().size(); i++) {
                System.out.printf(employeeRep.getAllEmployee().get(i).getFullName() + "%20s",
                        employeeRep.getAllEmployee().get(i).getTypeOfAccount() + "\n");}
            }
            case 2 -> {}
            case 3 -> {}
            case 4 -> {}
            case 5 -> {}
            case 6 -> {break;}
        }
    }


}
