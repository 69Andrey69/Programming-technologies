package org.example;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.time.Instant;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Client.class)
                .addAnnotatedClass(Orders.class)
                .addAnnotatedClass(Dishes.class)
                .buildSessionFactory();

        Requests requests = new Requests(factory);
        //Вывести последний заказ клиента вывод названия цены блюда
        Client client1 = new Client("Андрей", "Белянин");
        requests.addClient(client1);
        Client client2 = new Client("Артем", "Михеев");
        requests.addClient(client2);
        Client client3 = new Client("Оптимус", "Прайм");
        requests.addClient(client3);
        Client client4 = new Client("Степич", "Испанский");
        requests.addClient(client4);

        Orders order1 = new Orders(Instant.parse("2023-02-01T17:00:00Z"), 500.0, client1);
        requests.addOrders(order1);
        Orders order2 = new Orders(Instant.parse("2023-02-01T18:00:00Z"), 1000.0, client2);
        requests.addOrders(order2);
        Orders order3 = new Orders(Instant.parse("2023-02-01T16:00:00Z"), 1500.0, client3);
        requests.addOrders(order3);
        Orders order4 = new Orders(Instant.parse("2023-02-01T15:00:00Z"), 2000.0, client4);
        requests.addOrders(order4);
        Orders order5 = new Orders(Instant.parse("2023-02-01T20:00:00Z"), 2500.0, client2);
        requests.addOrders(order5);

        Dishes dish1 = new Dishes("Сяки", "Прикольно", 50.0, order4);
        requests.addDishes(dish1);
        Dishes dish2 = new Dishes("Филадельфия", "Балдеж", 100.0, order3);
        requests.addDishes(dish2);
        Dishes dish3 = new Dishes("Суши из лосося", "Дар Господа", 150.0, order2);
        requests.addDishes(dish3);
        Dishes dish4 = new Dishes("Мидии", "Нормс", 200.0, order1);
        requests.addDishes(dish4);
        Dishes dish5 = new Dishes("Суши из тунца", "Вкуснятина", 200.0, order5);
        requests.addDishes(dish5);





        requests.printLastDishByClientName("Артем");
        System.out.println("----");
        requests.printClientByDishesTitle("Мидии");
        System.out.println("----");
        requests.deleteOrderAndDishByClientId(1L);
        System.out.println("----");
        requests.updateDishTitleById(2L, "Осьминог");
        System.out.println("----");
        requests.addClient("Шершень", "Бамблби");
    }
}