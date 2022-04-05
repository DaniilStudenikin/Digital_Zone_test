package ru.itis.javalab.services;

import ru.itis.javalab.models.Well;

import java.util.List;


public interface WellService {
    Well findByName(String name);
    void createWell(String name);
    void findAllInfoAboutEquipments(String ... names);
    List<Well> findAll();
}
