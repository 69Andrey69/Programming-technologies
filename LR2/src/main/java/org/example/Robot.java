package org.example;

public class Robot implements Movement{

    int maxDist;
    int maxHeight;
    String name;

    private static int superRun = 3;


    public boolean run(int dist) {
        if(dist <= maxDist) return true;
        else if(isSuperRun()) return true;
        else return false;
    }

    @Override
    public boolean jump(int heigth) {
        if(heigth <= maxHeight) return true;
        else return false;
    }

    Robot(String name, int maxDist, int maxHeight){
        this.maxDist = maxDist;
        this.maxHeight = maxHeight;
        this.name = name;
    }

    public String getName() {
        return name;
    }
    @Override
    public int getSuperRun() {
        return superRun;
    }

    @Override
    public void setSuperRun(int superRun) {
        this.superRun = superRun;
    }

}