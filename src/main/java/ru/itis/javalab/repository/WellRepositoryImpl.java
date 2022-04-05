package ru.itis.javalab.repository;

import ru.itis.javalab.models.Well;
import ru.itis.javalab.sqlite.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WellRepositoryImpl implements WellRepository {

    //language=SQL
    private static final String FIND_BY_NAME = "SELECT * FROM Well WHERE name = ?";
    //language=SQL
    private static final String CREATE_WELL = "INSERT INTO Well(name) VALUES(?)";
    //language=SQL
    private static final String FIND_ALL = "SELECT * FROM Well";

    @Override
    public Well findByName(String name) {
        Well well = null;
        try (Connection connection = DriverManager.getConnection(Database.getUrlToDatabaseFile());
             PreparedStatement statement = connection.prepareStatement(FIND_BY_NAME)) {
            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                //добавить проверку если элемент есть то получить его,если нет то создать новый
                if (!resultSet.isBeforeFirst()) {
                    create(name);
                    try (ResultSet newResultSet = statement.executeQuery()) {
                        while (newResultSet.next()) {
                            well = Well.builder()
                                    .id(resultSet.getInt("id"))
                                    .name(resultSet.getString("name"))
                                    .build();
                        }
                        return well;
                    }
                } else {
                    while (resultSet.next()) {
                        well = Well.builder()
                                .id(resultSet.getInt("id"))
                                .name(resultSet.getString("name"))
                                .build();
                    }
                    return well;
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return well;
    }

    @Override
    public void create(String name) {
        try (Connection connection = DriverManager.getConnection(Database.getUrlToDatabaseFile());
             PreparedStatement statement = connection.prepareStatement(CREATE_WELL)) {
            statement.setString(1, name);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Well> findAll() {
        List<Well> wells = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(Database.getUrlToDatabaseFile());
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(FIND_ALL)) {
            while (resultSet.next()) {
                wells.add(Well.builder()
                        .id(resultSet.getInt("id"))
                        .name(resultSet.getString("name"))
                        .build());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wells;
    }
}
