package org.example;

import lombok.*;
import org.example.model.*;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;

import javax.persistence.Entity;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceException;
import java.util.Random;

public class TestDAO {
    private static SessionFactory sessionFactory;
    private final Configuration configuration = new Configuration();
    private static final Random random = new Random();
    private static final int countOfEntries = 40;   // Количество записей
    private static final int countOfThreads = 8;    // Количество потоков
    private static final int countOfEntriesForEachThread = 20000;   // Количество операций для каждого потока
    private static volatile int rollbacksCount = 0;

    public void createTables(Class<?>... entities) {
        for (Class<?> entity : entities) {
            if (entity.isAnnotationPresent(Entity.class)) {
                configuration.addAnnotatedClass(entity);
            }
        }
        configuration.configure();
        configuration.setProperty("hibernate.connection.pool_size", String.valueOf(countOfThreads));
        sessionFactory = configuration.buildSessionFactory();
    }

    public void fillItems() {
        @Cleanup Session session = sessionFactory.openSession();
        for (int i = 0; i < countOfEntries; i++) {
            Transaction transaction = session.beginTransaction();
            session.save(new Item());
            transaction.commit();
        }
    }

    private static void incrementValue(SessionFactory sessionFactory, boolean pessimisticLock) {
        long itemId = 0;
        boolean success = true;
        for (int i = 0; i < countOfEntriesForEachThread; i++) {
            if (success) {
                itemId = (long) random.nextInt(countOfEntries) + 1;
            }
            @Cleanup Session session = sessionFactory.getCurrentSession();
            Transaction transaction = session.beginTransaction();
            try {
                Item item;
                if (pessimisticLock) {
                    item = session.find(Item.class, itemId, LockModeType.PESSIMISTIC_WRITE);
                } else {
                    item = session.find(Item.class, itemId);
                }
                item.setVal(item.getVal() + 1);
                Thread.sleep(5);
                session.save(item);
                success = true;
                transaction.commit();
            } catch (PersistenceException | InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + " rollback");
                rollbacksCount++;
                success = false;
                i--;
                transaction.rollback();
            } finally {
                session.close();
            }
        }
    }

    @SneakyThrows
    public void testForItems() {
        System.out.printf("\nПотоков: %d\nЗаписей: %d\nОпераций для каждого потока: %d\n\n", countOfThreads, countOfEntries, countOfEntriesForEachThread);
        long optimisticStart = System.currentTimeMillis();
        Thread[] optimisticThreads = new Thread[countOfThreads];
        for (int i = 0; i < countOfThreads; i++) {
            optimisticThreads[i] = new Thread(() -> incrementValue(sessionFactory, false));
            optimisticThreads[i].start();
            System.out.printf("Optimistic Thread #%d started...\n", i + 1);
        }
        for (Thread thread : optimisticThreads) {
            thread.join();
        }
        long optimisticEnd = System.currentTimeMillis();
        long pessimisticStart = System.currentTimeMillis();
        Thread[] pessimisticThreads = new Thread[countOfThreads];
        for (int i = 0; i < countOfThreads; i++) {
            pessimisticThreads[i] = new Thread(() -> incrementValue(sessionFactory, true));
            pessimisticThreads[i].start();
            System.out.printf("Pessimistic Thread #%d started...\n", i + 1);
        }
        for (Thread thread : pessimisticThreads) {
            thread.join();
        }
        long pessimisticEnd = System.currentTimeMillis();
        @Cleanup Session session = sessionFactory.openSession();
        Object sum = session.createQuery("SELECT SUM(val) FROM Item").getSingleResult();
        System.out.println("End.\n");
        System.out.printf("Сумма всех val = %d\n", ((Number) sum).intValue());
        System.out.printf("Количество откатов = %d\n", rollbacksCount);
        System.out.printf("Optimistic Time = %.2f min\n", ((double) optimisticEnd - optimisticStart) / 60000);
        System.out.printf("Pessimistic Time = %.2f min\n", ((double) pessimisticEnd - pessimisticStart) / 60000);
        sessionFactory.close();
    }
}