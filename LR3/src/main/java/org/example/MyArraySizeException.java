
package org.example;
class MyArraySizeException extends RuntimeException
{
    public MyArraySizeException()
    {
    super("Неправильный размер массива.");
    }
}