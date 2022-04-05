package ru.itis.javalab.sqlite;

import java.sql.*;


public class Database {
    private static final String PATH_TO_FILE = System.getProperty("user.dir") + System.getProperty("file.separator") + "test.db";
    private static final String URL_TO_DATABASE_FILE = "jdbc:sqlite:" + PATH_TO_FILE;

    public static String getUrlToDatabaseFile() {
        return URL_TO_DATABASE_FILE;
    }

    public static void createDB() {
        try (Connection connection = DriverManager.getConnection(URL_TO_DATABASE_FILE);) {
            if (connection != null) {
                createWellTable();
                createEquipmentTable();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createWellTable() {
        //language=SQL
        String sql = "create table if not exists Well\n" +
                "(\n" +
                "    id   integer primary key autoincrement,\n" +
                "    name text not null unique\n" +
                ")";
        try (Connection connection = DriverManager.getConnection(URL_TO_DATABASE_FILE);
             Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createEquipmentTable() {
        //language=SQL
        String sql = "create table if not exists Equipment\n" +
                "(\n" +
                "    id   integer primary key autoincrement,\n" +
                "    name varchar(32) unique not null,\n" +
                "    well_id integer,\n" +
                "    constraint fk_well\n" +
                "        foreign key (well_id)\n" +
                "        references well(id)\n" +
                ")";
        try (Connection connection = DriverManager.getConnection(URL_TO_DATABASE_FILE);
             Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
