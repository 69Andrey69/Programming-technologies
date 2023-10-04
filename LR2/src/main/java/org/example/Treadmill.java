package org.example;

public class Treadmill implements Obstacle{
    int maxDist;
    Treadmill(int maxDist){
        this.maxDist = maxDist;
    }

    public  boolean isCan(Movement movement){
        if (movement.run(maxDist))
        {
            System.out.println(movement.getName() + " пробежал");
            return true;
        }
        else
        {
            System.out.println(movement.getName() + " не пробежал");
            return false;
        }
    }
}