package org.example;import java.util.*;
import java.util.function.Function;import java.util.stream.Collectors;
public class Main {    public static void main(String[] args)
{        System.out.println("Первое задание");
    String[] words = {"милана", "рот", "милана", "закрыла", "милана", "закрыла", "рот", "рот","a","a","a"};        System.out.println( Arrays.stream(words)
        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))                .entrySet()
        .stream()                .max(Comparator.comparingLong(entry -> entry.getValue()))
        .map(entry -> entry.getValue())                .map(maxFrequency -> Arrays.stream(words)
                .filter(word -> Arrays.stream(words).filter(w -> w.equalsIgnoreCase(word)).count() == maxFrequency)                        .distinct()
                .sorted()                        .collect(Collectors.joining(", ")))
        .orElse(""));


    System.out.println("Второе задание");        int n=3;
    List<Employee> employees = new ArrayList<>(Arrays.asList(                new Employee("Иван", 30, "Мужчина", 500000000),
            new Employee("Оптимус", 36, "Мужчина", 8000),                new Employee("Артем", 45, "Мужчина", 10000),
            new Employee("Бамблби", 20, "Мужчина", 10000000),                new Employee("Алексей", 25, "Мужчина", 6000),
            new Employee("Мария", 35, "Женщина", 5500),                new Employee("Ольга", 28, "Женщина", 7000),
            new Employee("Андрей1", 40, "Мужчина", 9000),                new Employee("Андрей", 12, "Мужчина", 7500)));
    System.out.println(employees.stream()                .filter(employee -> employee.getGender().equals("Мужчина"))
            .sorted(Comparator.comparing(Employee::getSalary))                .skip(employees.stream().filter(employee -> employee.getGender().equals("Мужчина")).count() - n)
            .sorted(Comparator.comparing(Employee::getAge))                .map(Employee::getName)
            .collect(Collectors.joining(", ", n + " самых высокооплачиваемых сотрудников зовут: ", ".")));
}}