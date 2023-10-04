package org.example;
class MyArrayDataException extends RuntimeException
{
    private int row;
    private int column;

    public MyArrayDataException(int row, int column)
    {
        super("Неверные данные в ячейке [" + row + "][" + column + "]");
        this.row = row;
        this.column = column;

    }
    public int getRow()
    {
        return row;
    }
    public int getColumn()
    {
        return column;
    }
}