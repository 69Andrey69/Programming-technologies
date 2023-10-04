package org.example;

public class Main {
    public static void main(String[] args) {
        Movement[] movements = {
                new Cat("Умка", 50, 3),
                new Human("Роман", 500, 1),
                new Robot("Бамблби", 100, 0),
                new Robot("Оптимус Прайм", 10, 0)};

        Obstacle[] obstacle =
                {
                        new Treadmill(Distance.SHORT.getDist()),
                        new Treadmill(Distance.MIDDLE.getDist()),
                        new Treadmill(Distance.LONG.getDist())
                };

        for (Movement move: movements) {
            for (Obstacle obs : obstacle) {
                if (!obs.isCan(move)) {
                    break;
                }
            }
        }
    }
}