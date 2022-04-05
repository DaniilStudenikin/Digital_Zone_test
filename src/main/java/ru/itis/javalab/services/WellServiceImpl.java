package ru.itis.javalab.services;

import ru.itis.javalab.models.Equipment;
import ru.itis.javalab.models.Well;
import ru.itis.javalab.repository.EquipmentRepository;
import ru.itis.javalab.repository.EquipmentRepositoryImpl;
import ru.itis.javalab.repository.WellRepository;
import ru.itis.javalab.repository.WellRepositoryImpl;
import ru.itis.javalab.sqlite.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WellServiceImpl implements WellService {
    private final WellRepository wellRepository;
    private final EquipmentRepository equipmentRepository;

    public WellServiceImpl(WellRepository wellRepository, EquipmentRepository equipmentRepository) {
        this.wellRepository = wellRepository;
        this.equipmentRepository = equipmentRepository;
    }

    @Override
    public Well findByName(String name) {
        return wellRepository.findByName(name);
    }

    @Override
    public void createWell(String name) {
        wellRepository.create(name);
    }

    @Override
    public void findAllInfoAboutEquipments(String... names) {
        Map<String, Integer> info = new HashMap<>();
        for (String s: names){
            List<Equipment> equipments = equipmentRepository.findAllByWellName(s);
            info.put(s,equipments.size());
        }
        info.forEach((x,y)-> System.out.printf("%s - %d\n",x,y));
    }

    @Override
    public List<Well> findAll() {
        return wellRepository.findAll();
    }


    public static WellService getInstance() {
        return new WellServiceImpl(new WellRepositoryImpl(),new EquipmentRepositoryImpl(new WellRepositoryImpl()));
    }
}
