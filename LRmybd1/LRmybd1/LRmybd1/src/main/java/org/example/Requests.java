package org.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
//import jakarta.persistence.*;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.time.Instant;
import java.util.*;

public class Requests {
    private final SessionFactory sessionFactory;

    public Requests(SessionFactory sessionFactory) {

        this.sessionFactory = sessionFactory;
    }

    public void addDishes(Dishes dishes) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(dishes);
            session.getTransaction().commit();
            session.close();
        }
    }

    public void addClient(Client client) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(client);
            session.getTransaction().commit();
            session.close();
        }
    }

    public void addOrders(Orders orders) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(orders);
            session.getTransaction().commit();
            session.close();
        }
    }


    public void printLastDishByClientName(String client_name) {
        try (Session session = sessionFactory.openSession()) {
            List<Client> clients = session.createQuery("FROM Client WHERE client_name = :name", Client.class).setParameter("name", client_name).list();
            if (!clients.isEmpty()) {
                for (Client client : clients) {
                    System.out.println(client.getLastDishTitle());
                }
            }
        }
    }


    public void printClientByDishesTitle(String dishes_title) {
        try (Session session = sessionFactory.openSession()) {
            List<Dishes> dishes = session.createQuery("FROM Dishes d WHERE d.dishes_title = :dishes_title").setParameter("dishes_title", dishes_title).list();
            for (Dishes dish : dishes) {
                if (dish.getOrders() != null) {
                    System.out.println(dish.getOrders().getclient());
                }
            }
        }
    }


    public void deleteOrderAndDishByClientId(Long client_id) {
        System.out.println("Удалено:");
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Client client = session.get(Client.class, client_id);
            if (client != null) {
                for (Orders orders : client.getOrders()) {
                    for (Dishes dishes : orders.getDishes()) {
                        System.out.println(dishes);
                        session.delete(dishes);
                    }
                }
                for (Orders orders : client.getOrders()) {
                    System.out.println(orders);
                    session.delete(orders);
                }
                System.out.println(client);
                session.delete(client);
                transaction.commit();
            } else {
                System.out.println("Не существует клиента с ID: " + client_id);
            }
        }
    }


    public void updateDishTitleById(Long Dishes_id, String dish_title) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            Dishes dishes = session.get(Dishes.class, Dishes_id);

            if (dishes != null) {
                System.out.println("Изменено " + dishes);

                dishes.setDishesTitle(dish_title);

                session.update(dishes);

                transaction.commit();
            } else {
                System.out.println("Не существует блюда с ID: " + Dishes_id);
            }
        }
    }


    public void addClient(String client_name, String client_fam) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            Client client = new Client(client_name, client_fam);

            System.out.println("Добавлено: " + client);

            session.persist(client);

            transaction.commit();
        }
    }

}