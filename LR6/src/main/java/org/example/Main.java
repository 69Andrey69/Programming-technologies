package org.example;
import java.util.Arrays;
public class Main {    static final int SIZE =50_000_000 ;
    static final int HALF = SIZE / 2;    static final int THREADS_COUNT = 5;
    public static void main(String[] args) {
        float[] arr = new float[SIZE];
        Arrays.fill(arr,1);
        System.out.println("Работа первого метода:");        long startTime = System.currentTimeMillis();
        Formula(arr);        long endTime = System.currentTimeMillis();
        Print(arr);        System.out.println("\nВремя выполнения первого метода: " + (endTime - startTime) + " мс");
        arr = new float[SIZE];
        Arrays.fill(arr,1);
        System.out.println("\nРабота второго метода:");        startTime = System.currentTimeMillis();
        Vich(arr);        endTime = System.currentTimeMillis();
        Print(arr);        System.out.println("\nВремя выполнения второго метода: " + (endTime - startTime) + " мс");
        int threads = THREADS_COUNT;
        arr = new float[SIZE];            Arrays.fill(arr,1.0f);
        System.out.println("\nРабота метода с " + threads + " потоками:");
        startTime = System.currentTimeMillis();            Vich2(arr, threads);
        endTime = System.currentTimeMillis();            Print(arr);
        System.out.println("\nВремя выполнения метода с " + threads + " потоками: " + (endTime - startTime) + " мс");        }

    private static void Formula(float[] arr) {        for (int i = 0; i < arr.length; i++) {
        arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));        }
    }
    public static void Vich(float[] array) {        float[] a1 = new float[HALF];
        float[] a2 = new float[HALF];        System.arraycopy(array, 0, a1, 0, HALF);
        System.arraycopy(array, HALF, a2, 0, HALF);
        Thread threadOne = new Thread(() -> {            for (int i = 0; i < a1.length; i++) {
            a1[i] = (float) (a1[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));            }
            System.arraycopy(a1, 0, array, 0, a1.length);
        });        threadOne.start();
        Thread threadTwo = new Thread(() -> {
            for (int i = 0; i < a2.length; i++) {
                a2[i] = (float) (a2[i] * Math.sin(0.2f + (HALF + i) / 5) * Math.cos(0.2f + (HALF + i) / 5) * Math.cos(0.4f + (HALF + i) / 2));            }
            System.arraycopy(a2, 0, array, HALF, a2.length);
        });        threadTwo.start();
        try {
            threadOne.join();            threadTwo.join();
        } catch (InterruptedException e) {            e.printStackTrace();
        }}
    private static void Vich2(float[] arr, int Potok_Kolvo) {
        int PotokSize = arr.length / Potok_Kolvo;        int ost = arr.length % Potok_Kolvo;
        Thread[] threads = new Thread[Potok_Kolvo];        int startIndex = 0;
        for (int i = 0; i < Potok_Kolvo; i++) {
            final int start = startIndex;            final int threadIndex = i;
            int endIndex = start + PotokSize + (i == 0 ? ost : 0);            final int finalStartIndex = start;
            final int finalEndIndex = endIndex;            threads[i] = new Thread(() -> {
                Formula_V_Potoke(arr, finalStartIndex, finalEndIndex);                float[] result = Arrays.copyOfRange(arr, finalStartIndex, finalEndIndex);
                System.out.println("Результат потока " + (threadIndex + 1) + ": " + result.length);            });
            threads[i].start();            startIndex = endIndex;
        }
        for (int i = 0; i < Potok_Kolvo; i++) {            try {
            threads[i].join();
        }
        catch (InterruptedException e) {                e.printStackTrace();
        }        }
    }
    private static void Formula_V_Potoke(float[] arr, int startIndex, int endIndex) {        for (int i = startIndex; i < endIndex; i++) {
        arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));        }
    }
    private static void Print(float[] arr) {        System.out.println("Первая ячейка: " + arr[0]);
        System.out.println("Последняя ячейка: " + arr[arr.length - 1]+"\n");        System.out.println(Arrays.toString(arr));
    }}