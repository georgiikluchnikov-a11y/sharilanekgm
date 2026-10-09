package ru.klyuchnikov.sharilane;

/**
 * Точка входа проекта. Используется обычная сигнатура main и System.out,
 * чтобы класс запускался на любом JDK 17+ и в любой конфигурации IntelliJ IDEA.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("Hello and welcome!");

        for (int i = 1; i <= 5; i++) {
            System.out.println("i = " + i);
        }
    }
}
