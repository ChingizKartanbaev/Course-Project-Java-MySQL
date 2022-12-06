package CorseProject.service;

import CorseProject.dao.OrderRep;
import CorseProject.dao.impl.OrderRepImpl;
import CorseProject.dao.ProductRep;
import CorseProject.dao.impl.ProductRepImp;
import CorseProject.dao.ReviewsRep;
import CorseProject.dao.impl.ReviewsRepImpl;
import CorseProject.models.Basket;
import CorseProject.models.Reviews;
import CorseProject.models.enums.BdProcess;
import CorseProject.models.enums.Categories;
import CorseProject.utils.*;

import java.util.*;

public class ClientService extends Accounts {

    private static final ProductRepImp productRep = new ProductRepImp();
    private static final OrderRep orderRep = new OrderRepImpl();
    private static final ReviewsRep reviewsRep = new ReviewsRepImpl();
    private static final Scanner scanner = new Scanner(System.in);

    public void clientMenu(long idClient) {
        loop:
        while (true) {

            System.out.println("""

                    Выберете меню\s
                    1 - Сделать заказ
                    2 - Проверить заказ
                    3 - Оставить Отзыв
                    4 - Выход
                    """);

            switch (scanner.nextInt()){
                case 1 -> makeOrder(idClient);
                case 2 -> checkOrder();
                case 3 -> writeReview();
                case 4 -> {
                    end();
                    break loop;
                }
                default -> System.out.println("Данные введены не котректно");
            }
        }
    }


    // 1 Меню
    //TODO не трогать пусть так будет!
    private void makeOrder(long idClient) {
        ArrayList<Basket> baskets = new ArrayList<>();
        int numberOfOrder = 1 + (int) (Math.random() * 100);

        loop:
        while (true) {
            System.out.println("""
                    1 - посмотреть все меню
                    2 - посмотреть по категории
                    3 - Добавить в карзину
                    4 - Удалить из казрины
                    5 - Завершить""");

            switch (scanner.nextInt()) {

                case 1 -> {
                    PrettyTable prettyTable = new PrettyTable("Айди", "Название пробукта", "Стоимость");
                    productRep.getAllProduct().forEach(x -> prettyTable.addRow(String.valueOf(x.getIdProduct()),
                            x.getName(), String.valueOf(x.getCost())));
                    System.out.println(prettyTable);
                }

                case 2 -> {
                    System.out.println("""
                            Выберите категорию
                            1 - Напитки
                            2 - Бургеры
                            3 - Твистер
                            4 - Курица
                            5 - Баскет""");

                    switch (scanner.nextInt()) {
                        case 1 -> System.out.println(choseByCategory(Categories.DRINKS));
                        case 2 -> System.out.println(choseByCategory(Categories.BURGERS));
                        case 3 -> System.out.println(choseByCategory(Categories.TWISTER));
                        case 4 -> System.out.println(choseByCategory(Categories.CHICKEN));
                        case 5 -> System.out.println(choseByCategory(Categories.BASKET));
                        default -> System.out.println("Ошибка");
                    }
                }

                case 3 -> {
                    System.out.print("Введите id продукта: ");
                    long idProduct = scanner.nextLong();

                    System.out.print("Введите количество");
                    int amountOfProduct = scanner.nextInt();

                    double totalCost = amountOfProduct * productRep.getProductById(idProduct).getCost();

                    Basket basket = new Basket((int) idClient, productRep.getProductById(idProduct).getName(), amountOfProduct,
                            totalCost, totalCost, numberOfOrder, BdProcess.RUNNING);
                    basket.setIdOrders(idProduct);

                    baskets.add(basket);

                    System.out.println(printTable(baskets));
                }

                case 4 -> {
                    int idProduct = scanner.nextInt();
                    for (Basket b : baskets) {
                        if(b.getIdOrders() == idProduct){
                            baskets.remove(b);
                            System.out.println(printTable(baskets));
                            break;
                        }
                    }
                }

                case 5 -> {
                    break loop;
                }
                default -> System.out.println("Ошибка");
            }
        }
        orderRep.createOrder(baskets);
        System.out.println("---------");
        System.out.println("Ваш номер заказа: " + numberOfOrder);
        System.out.println("---------" + "\n");
    }

    private PrettyTable printTable(ArrayList<Basket> baskets){
        PrettyTable tableOfBasket = new PrettyTable("Id", "Наименование продукта", "Количество", "Стоимость");

        for (Basket basket : baskets) {
            tableOfBasket.addRow(String.valueOf(basket.getIdOrders()), basket.getOrder(), String.valueOf(basket.getAmount()),
                    String.valueOf(basket.getTotalCost()));
        }

        return tableOfBasket;
    }




    private PrettyTable choseByCategory(Categories categories) {
        // Create a pretty table and add a header
        PrettyTable prettyTable = new PrettyTable( "Айди", "Название пробукта", "Стоимость");
        ProductRep productRep = new ProductRepImp();

            // Through the loop we run through the array
        for (int i = 0; i < productRep.getAllProduct().size(); i++) {
            //
            if(productRep.getAllProduct().get(i).getCategories().equals(categories)){
                prettyTable.addRow(String.valueOf(productRep.getAllProduct().get(i).getIdProduct()),
                        productRep.getAllProduct().get(i).getName(),
                        String.valueOf(productRep.getAllProduct().get(i).getCost()));
            }
        }
        return prettyTable;
    }



    // 2 Меню
    //TODO может быть сделать короче
    private void checkOrder(){
        boolean flag = false;

        System.out.print("Введите номер заказа: ");
        int num = scanner.nextInt();

        for (int i = 0; i < orderRep.getAllOrders().size(); i++) {
            if(orderRep.getAllOrders().get(i).getOrderNumber() == num){
                flag = orderRep.getAllOrders().get(i).getOrderNumber() == num &&
                        String.valueOf(orderRep.getAllOrders().get(i).getBdProcess()).equals("FINISHED");
            }
            else {
                System.out.println("Данного заказа нету");
            }
        }

        if(flag){
            System.out.println("Ваш заказ готов");
        }else {
            System.out.println("Ваш заказ еще не готов");
        }
    }



    // 3 Меню
    //TODO сделать проверки
    private void writeReview() {
        System.out.println("Напишите отзыв: ");
        String reviewThatWriteClient = scanner.nextLine();

        System.out.println("Введите свой айди");
        int clientId = scanner.nextInt();

        Reviews reviews = new Reviews(reviewThatWriteClient, clientId);

        reviewsRep.createReview(reviews);
    }
}