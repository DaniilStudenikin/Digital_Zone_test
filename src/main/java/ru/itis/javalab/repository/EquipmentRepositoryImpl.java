package ru.itis.javalab.repository;


import ru.itis.javalab.models.Equipment;
import ru.itis.javalab.models.Well;
import ru.itis.javalab.sqlite.Database;

import java.sql.*;
import java.util.*;


public class EquipmentRepositoryImpl implements EquipmentRepository {
    //language=SQL
    private static final String INSERT_EQUIPMENT = "INSERT INTO Equipment(NAME, WELL_ID) VALUES (?,?)";
    //language=SQL
    private static final String FIND_ALL_BY_WELL_ID ="SELECT * FROM Equipment WHERE well_id = ?";
    //language=SQL


    private final WellRepository wellRepository;

    public EquipmentRepositoryImpl(WellRepository wellRepository) {
        this.wellRepository = wellRepository;
    }

    @Override
    public void create(int count, String wellName) {
        if (count >= 0) {
            for (int i = 0; i < count; i++) {
                try (Connection connection = DriverManager.getConnection(Database.getUrlToDatabaseFile());
                     PreparedStatement statement = connection.prepareStatement(INSERT_EQUIPMENT)) {
                    statement.setString(1, generateName(new Random()));
                    statement.setInt(2, wellRepository.findByName(wellName).getId());
                    statement.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } else
            System.out.println("Введите побольше количество оборудования");
    }

    @Override
    public List<Equipment> findAllByWellName(String name) {
        Well well = wellRepository.findByName(name);
        List<Equipment> equipments = new ArrayList<>();
        try(Connection connection = DriverManager.getConnection(Database.getUrlToDatabaseFile());
            PreparedStatement statement = connection.prepareStatement(FIND_ALL_BY_WELL_ID)) {
            statement.setInt(1,well.getId());
            try(ResultSet resultSet = statement.executeQuery()){
                while (resultSet.next()) {
                    Equipment equipment = Equipment.builder()
                            .id(resultSet.getInt("id"))
                            .name(resultSet.getString("name"))
                            .well(well)
                            .build();
                    equipments.add(equipment);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return equipments;
    }

    @Override
    public Map<Well, List<Equipment>> findAllByWells(List<Well> wells) {
        Map<Well,List<Equipment>> map = new HashMap<>();
        for(Well well: wells) {
            map.put(well,findAllByWellName(well.getName()));
        }
        return map;
    }


    private String generateName(Random random) {
        final String SOURCES =
                "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
        int length = random.nextInt(32 - 1) + 1;
        char[] text = new char[length];
        for (int i = 0; i < length; i++) {
            text[i] = SOURCES.charAt(random.nextInt(SOURCES.length()));
        }
        return new String(text);
    }
}
