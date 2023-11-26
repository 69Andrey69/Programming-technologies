package org.example;

import java.sql.*;

public class Main {
    static Connection connection;
    static Statement statement;
    public static void main(String[] args) throws SQLException {
        connect();

        statement.execute("drop table if exists результат;");
        statement.execute("drop table if exists студенты;");
        statement.execute("drop table if exists предметы;");
        statement.execute("CREATE TABLE IF NOT EXISTS студенты" +
                "(id serial not null PRIMARY KEY," +
                " имя varchar(30) not null," +
                " СерияПаспорта varchar(4) not null," +
                " НомерПаспорта varchar(6) not null," +
                " UNIQUE (СерияПаспорта, НомерПаспорта)" +
                ");");
        statement.execute("CREATE TABLE IF NOT EXISTS предметы" +
                "(id serial not null PRIMARY KEY, название varchar(50) not null);");

        statement.execute("CREATE TABLE IF NOT EXISTS результат" +
                "(id serial not null PRIMARY KEY," +
                " студент int not null REFERENCES студенты(id) ON DELETE CASCADE," +
                " предмет int not null REFERENCES предметы(id)," +
                " оценка smallint CHECK(оценка BETWEEN 2 and 5));");
        statement.execute("INSERT into студенты (id, имя, СерияПаспорта, НомерПаспорта) values" +
                "    (1, 'Степан', 1111, 111111)," +
                "    (2, 'Андрей', 2222, 222222)," +
                "    (3, 'Никита', 3333, 333333)," +
                "    (4, 'Анна', 4444, 444444)," +
                "    (5, 'Артем', 5555, 555555)," +
                "    (6, 'Лера', 6666, 666666);");

        statement.execute("INSERT into предметы (id, название) values" +
                "    (1, 'Астрономия')," +
                "    (2, 'География')," +
                "    (3, 'ОБЖ')," +
                "    (4, 'Биология')," +
                "    (5, 'МХК')," +
                "    (6, 'Краеведение');");
        statement.execute("INSERT into результат (id, студент, предмет, оценка) values" +
                "    (1, 1, 1, 5)," +
                "    (2, 2, 5, 4)," +
                "    (3, 3, 5, 5)," +
                "    (4, 4, 2, 3)," +
                "    (5, 5, 2, 5)," +
                "    (6, 6, 5, 2);");
        System.out.println("Вывести список студентов, сдавших определенный предмет, на оценку выше 3");
        var s1 = statement.executeQuery("Select с.имя, р.оценка, п.название from студенты с " +
                "join результат р on с.id = р.студент " +
                "join предметы п on п.id = р.предмет " +
                "WHERE р.оценка > 3 and п.название = 'МХК';");
        while (s1.next()) {
            String subjName = s1.getString(1);
            int mark = s1.getInt(2);
            String studName = s1.getString(3);
            System.out.println(subjName + " " + mark + " " + studName);
        }
        System.out.println("Посчитать средний балл по определенному предмету");
        var s2 = statement.executeQuery("select avg(р.оценка) from результат р " +
                "join предметы п on р.предмет = п.id " +
                "where п.название = 'МХК';");
        while (s2.next()) {
            double avg = s2.getDouble(1);
            System.out.println(avg);
        }
        System.out.println("Посчитать средний балл по определенному студенту");
        var s3 = statement.executeQuery("select avg(р.оценка) from студенты с " +
                "join результат р on с.id = р.студент " +
                "where с.имя = 'Андрей';");
        while (s3.next()) {
            double avg = s3.getDouble(1);
            System.out.println(avg);
        }
        System.out.println("Найти три предмета, которые сдали наибольшее количество студентов");
        var s4 = statement.executeQuery("SELECT count(*), п.название from результат р " +
                "join предметы п on п.id = р.предмет " +
                "where р.оценка > 2 " +
                "group by п.название " +
                "order by count(*) desc limit 3;");
        while (s4.next()) {
            int cnt = s4.getInt(1);
            String name = s4.getString(2);
            System.out.println(cnt + " " + name);
        }
        System.out.println("Вывести список студентов, сдавших предмет, на оценку выше 3");
        var s5 = statement.executeQuery("Select с.имя from студенты с " +
                "join результат р on с.id = р.студент " +
                "join предметы п on п.id = р.предмет " +
                "WHERE р.оценка > 3 and р.оценка is not null;");
        while (s5.next()) {
            String studName = s5.getString(1);
            System.out.println(studName);
        }
        System.out.println();
        disconnect();


    }
    public static void connect()
    {
        try
        {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:LR101.db");
            statement = connection.createStatement();
        }
        catch (ClassNotFoundException| SQLException e)
        {
            e.printStackTrace();
        }
    }
    public static void disconnect()
    {
        try
        {
            statement.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        try
        {
            connection.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}