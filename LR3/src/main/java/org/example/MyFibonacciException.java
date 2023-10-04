package org.example;
class MyFibonacciException extends RuntimeException {
    private int row;
    private int column;

    public MyFibonacciException(int row, int column) {
        super("Число в ячейке [" + row +"]" + "["+ column+"]  является числом Фибоначчи в пределах тысячи.");
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }
}