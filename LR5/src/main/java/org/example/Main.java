package org.example;
import java.util.*;
public class Main {    public static void main(String[] args) {
    String[] wordsArray = {"милана", "я", "абсолют", "милана", "рот", "я", "рот", "абсолют", "я", "милана", "абсолют"};
    Map<String, Integer> wordCount = new HashMap<>();        for (var word : wordsArray) {
        wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);        }
    System.out.println("Количество повторений: " + wordCount.entrySet());

           Dictionary dictionary = new Dictionary();
    dictionary.add("Веселый", "Радостный");        dictionary.add("Грустный", "Несчастный");
    dictionary.add("Большой", "Огромный");        dictionary.add("Большой", "Немаленький");
    dictionary.add("Маленький", "Небольшой");
    dictionary.add("Маленький", "маленький");
    dictionary.add("Маленький", "маленький");        dictionary.add("Маленький", "маленький1");
    dictionary.add("Маленький", "маленький0");
    System.out.println("\nСловарь:");
    dictionary.printAll();
    System.out.println("Синонимы слова 'Веселый': " + dictionary.get("Веселый"));
    System.out.println("Синонимы слова 'Маленький': " + dictionary.get("Маленький"));
    System.out.println("Синонимы слова 'Зеленый': " + dictionary.get("Зеленый"));
}}

