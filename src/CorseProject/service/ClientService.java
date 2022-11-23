package CorseProject.service;

import CorseProject.dao.ReviewsRep;
import CorseProject.dao.impl.ReviewsRepImpl;
import CorseProject.models.Reviews;

import java.util.Scanner;

public class ClientService {


    private static final ReviewsRep reviewsRep = new ReviewsRepImpl();
    private static final Scanner scanner = new Scanner(System.in);

    public static void clientMenu (){

        loop:
        while (true) {

            System.out.println("""

                    Выберете меню\s
                    1 - Сделать заказ
                    2 - Проверить заказ
                    3 - Оставить Отзыв
                    5 - Выход
                    """);

            switch (scanner.nextInt()){
                case 1 -> System.out.println("Сделать заказ");
                case 2 -> System.out.println("Проверить заказ");
                case 3 -> writeReview();
                case 4 -> System.out.println("");
                case 5 -> {
                    System.out.println("Выход");
                    break loop;
                }
                default -> System.out.println("Данные введены не котректно");
            }

        }
    }



    public static void writeReview() {

        while (true){

            System.out.println("Напишите отзыв: ");
            String reviewThatWriteClient = scanner.nextLine();

            System.out.println("Введите свой айди");
            int clientId = scanner.nextInt();

            Reviews reviews = new Reviews(reviewThatWriteClient, clientId);

            if (reviewThatWriteClient.isEmpty()) {
                System.out.println("Error");
                // заглушка
                String s = scanner.nextLine();
            }else {

                try {
                    reviewsRep.createReview(reviews);
                } catch (Exception e){

                    System.out.println("Айди клиента не найден");
                    // заглушка
                    String s = scanner.nextLine();
                    writeReview();
                    break;
                }
            }
        }
    }

}