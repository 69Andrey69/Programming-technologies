package org.example;
import java.sql.*;
import java.lang.reflect.Field;
import java.util.*;

public class Main {

    public static void createToTable(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        String tableName = clazz.getSimpleName().toLowerCase();
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("CREATE TABLE IF NOT EXISTS ").append(tableName).append(" (");

        Map<Class<?>, String> columnTypeMap = typeColumn();

        List<Field> Priority = new ArrayList<>();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Column.class)) {
                Priority.add(field);
            }
        }

        Priority.sort(Comparator.comparingInt(field -> field.getAnnotation(Column.class).priority()));

        for (Field field : Priority) {
            Class<?> fieldType = field.getType();
            String columnName = field.getName();
            String columnType = columnTypeMap.get(fieldType);
            queryBuilder.append(columnName).append(" ").append(columnType).append(", ");
        }

        queryBuilder.delete(queryBuilder.length() - 2, queryBuilder.length());
        queryBuilder.append(");");

        String createTableQuery = queryBuilder.toString();

        try (Statement statement = connection.createStatement()) {
            statement.execute(createTableQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static Map<Class<?>, String> typeColumn() {
        Map<Class<?>, String> columnTypeMap = new HashMap<>();
        columnTypeMap.put(String.class, "VARCHAR");
        columnTypeMap.put(int.class, "INT");
        columnTypeMap.put(double.class, "DOUBLE");
        columnTypeMap.put(boolean.class, "BOOLEAN");
        columnTypeMap.put(SizeKluv.class, "VARCHAR");
        return columnTypeMap;
    }
    public static void insertIntoTable(Object object) {
        Class<?> clazz = object.getClass();
        if (clazz.isAnnotationPresent(Table.class)) {
            Table tableAnnotation = clazz.getAnnotation(Table.class);
            String tableName = tableAnnotation.Title();
            Field[] fields = clazz.getDeclaredFields();
            StringBuilder query = new StringBuilder("INSERT OR IGNORE INTO " + tableName + " (");
            for (Field field : fields) {
                if (field.isAnnotationPresent(Column.class)) {
                    query.append(field.getName()).append(",");
                }
            }
            query.deleteCharAt(query.length() - 1).append(") VALUES (");
            for (Field field : fields) {
                if (field.isAnnotationPresent(Column.class)) {
                    try {
                        query.append("'").append(field.get(object)).append("',");
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
            query.deleteCharAt(query.length() - 1).append(")");
            String insertToTable = query.toString();
            try {
                statement.execute(insertToTable);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void deleteFromTable(Class<?> clazz) {
        if (clazz.isAnnotationPresent(Table.class)) {
            Table tableAnnotation = clazz.getAnnotation(Table.class);
            String tableName = tableAnnotation.Title();
            String deleteTableQuery = "DELETE FROM " + tableName;
            try {
                statement.executeUpdate(deleteTableQuery);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }}
    }
    public static void dropTable(Class<?> clazz)
    {
        if (clazz.isAnnotationPresent(Table.class)) {
            Table tableAnnotation = clazz.getAnnotation(Table.class);
            String tableName = tableAnnotation.Title();
            String dropTableQuery = "DROP TABLE IF EXISTS " + tableName;
            try {
                statement.executeUpdate(dropTableQuery);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }}
    }

    private static Connection connection;
    private static Statement statement;
    public static void main(String[] args)
    {
        connect();

        //createToTable(Chicken.class);

        Chicken chicken = new Chicken("Артем Баскетболист", 777, SizeKluv.фига);
        Chicken chicken1 = new Chicken("Бамлби(Шершень)", 1, SizeKluv.среднячок);
        Chicken chicken2 = new Chicken("Ряба", 100, SizeKluv.здоровенный);

        Chicken[] chickens = {chicken, chicken1, chicken2};
       //deleteFromTable("Chicken");
        boolean dropTable = false;
        for (Chicken c : chickens) {
            if (!dropTable) {
                dropTable(c.getClass());
                dropTable = true;
            }
            createToTable(c.getClass());
            insertIntoTable(c);
        }

        //dropTable("Chicken");
        disconnect();
    }
    public static void connect()
    {
        try
        {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:LR9Andre.db");
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