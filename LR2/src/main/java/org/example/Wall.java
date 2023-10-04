package org.example;

public class Wall implements Obstacle{
    int maxHeight;

    Wall(int maxHeight){
        this.maxHeight = maxHeight;
    }

    @Override
    public boolean isCan(Movement movement){
        if (movement.jump(maxHeight)){
            System.out.println(movement.getName() + " перепрыгнул");
            return true;
        }else{
            System.out.println(movement.getName() + " не перепрыгнул");
            return false;
        }
    }
}