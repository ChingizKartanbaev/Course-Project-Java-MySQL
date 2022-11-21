package CorseProject.service;

import CorseProject.dao.ClientRep;
import CorseProject.dao.EmployeeRep;
import CorseProject.dao.ReportManagerRep;
import CorseProject.dao.ReviewsRep;
import CorseProject.dao.impl.ClientRepImpl;
import CorseProject.dao.impl.EmployeeRepImpl;
import CorseProject.dao.impl.ReportManagerRepImpl;
import CorseProject.dao.impl.ReviewsRepImpl;
import CorseProject.utils.PrettyTable;

import java.util.Scanner;

public class ManagerService {

    public static Scanner scanner = new Scanner(System.in);
    public static EmployeeRep employeeRep = new EmployeeRepImpl();
    public static ClientRep clientRep = new ClientRepImpl();
    public static ReviewsRep reviewsRep = new ReviewsRepImpl();
    public static ReportManagerRep reportManagerRep = new ReportManagerRepImpl();

    public static void managerMenu (){

        loop:
        while (true){
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

                case 1 -> System.out.println(showEmployee());
                case 2 -> System.out.println(showReview());
                case 3 -> System.out.println("a");
                case 4 -> System.out.println("b");
                case 5 -> System.out.println(showAListOfAllCoverageAreas());
                case 6 -> {break loop;}
            }
        }
    }

    public static PrettyTable showEmployee (){
        PrettyTable prettyTable = new PrettyTable("Айди","ФИО","Тип аккаунта");

        employeeRep.getAllEmployee().forEach(x -> prettyTable.addRow(String.valueOf(x.getId()),x.getFullName(),
                x.getTypeOfAccount()));

        return prettyTable;
    }

    public static PrettyTable showReview (){

        PrettyTable prettyTable = new PrettyTable("Отзыв", "Имя клиента");
        for (int i = 0; i < reviewsRep.getAllReviews().size(); i++) {
            prettyTable.addRow(reviewsRep.getAllReviews().get(i).getReview(),
                    String.valueOf(clientRep.getAllClient().get(i).getFullName()));
        }

        return prettyTable;
    }

    public static PrettyTable showAListOfAllCoverageAreas (){

        // выводит список зон покрытия
        PrettyTable prettyTable = new PrettyTable("City Name", "Coverage area");

        reportManagerRep.getAllReports().forEach(x -> prettyTable.addRow(x.getCityName(),
                x.getCustomerCoverageArea()));

        return prettyTable;
    }

}
