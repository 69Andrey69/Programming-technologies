package org.example;
public class Main {
    public static void main(String[] args)
    {    String[][] array =
            {
                    {"=", "7", "7", "7"},
                    {"7", "7", "7", "7"},
                    {"7", "7", "7", "7"},
                    {"7", "2", "=", "="}
            };
    int sum = 0;
    boolean hasException = false;
    for (int i = 0; i < array.length; i++)
    {
        for (int j = 0; j < array[i].length; j++) {
        try
        {
            int value = Integer.valueOf(array[i][j]);
            sum += value;
            if (!isFibonacci(value)) {
                throw new MyFibonacciException(i, j);
            }
        }
        catch (NumberFormatException e)
        {
            hasException = true;
            e.printStackTrace();
        }
        catch (MyFibonacciException e)
        {
            e.printStackTrace();
        }
        catch (MyArrayDataException e)
        {
            e.printStackTrace();
            hasException = true;
        }
        }
    }
    if(array.length != 4 || array[0].length != 4)    {
        throw new MyArraySizeException();    }
    if (!hasException)    {
        System.out.println("Сумма чисел в массиве: " + sum);    }
}    public static boolean isFibonacci(int number) {
    if (number == 0 || number == 1) {
        return false;
    }

    int fib1 = 0;
    int fib2 = 1;

    while (fib2 <= number && fib2 <= 1000) {
        int temp = fib1 + fib2;
        fib1 = fib2;
        fib2 = temp;

        if (fib2 == number) {
            return false;
        }
    }

    return true;
}
}