package ru.itis.javalab.repository;

import ru.itis.javalab.models.Equipment;
import ru.itis.javalab.models.Well;

import java.util.List;
import java.util.Map;

public interface EquipmentRepository {
    void create(int count, String wellName);
    List<Equipment> findAllByWellName(String name);
    Map<Well,List<Equipment>> findAllByWells(List<Well> wells);
}
