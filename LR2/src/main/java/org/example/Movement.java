package org.example;

public interface Movement {
    default int getSuperRun(){
        throw new UnsupportedOperationException("");
    }
    default void setSuperRun(int superRun){
        throw new UnsupportedOperationException("");
    }

    default boolean isSuperRun() {
        int superRun = getSuperRun();
        if (superRun > 0) {
            setSuperRun(superRun - 1);
            return true;
        }
        return false;
    }

    boolean run(int dist);
    boolean jump(int height);
    String getName();
}