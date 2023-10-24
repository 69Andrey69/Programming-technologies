package org.example;
import java.util.ArrayList;
public class Box<T extends Fruit> {
    private ArrayList <T> fruits;

    private boolean ban;
    public Box(Class fruit){
        if(Banana.class.equals(fruit))
            ban = true;
        this.fruits = new ArrayList<>();
    }

    public void addFruit(T fruit) {
        fruits.add(fruit);
    }

    public int getWeight() {
        int ves = 0;
        for (T fruit : fruits) {
            ves += fruit.getWeight();
        }
        return ves;
    }
    public void printBox () {
        for (T item : fruits) {
            System.out.println(item);
        }
    }
    public boolean sravnenie(Box<?> otherBox) {
        return Math.abs(this.getWeight() - otherBox.getWeight()) < 0.0001;
    }
    public void peresipka(Box<T> box)
    {
        if (!box.ban && fruits.equals(box.getClass()))
        {
            box.fruits.addAll(fruits);
            fruits.clear();
        }
        else {
            for (int i = 0; i <= fruits.size() - 1; i++)
                if (Banana.class.equals(fruits.get(i).getClass())) {
                    box.fruits.add(fruits.get(i));
                    fruits.remove(i);
                }
        }
    }
}