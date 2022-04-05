package ru.itis.javalab.services;

import ru.itis.javalab.models.Equipment;
import ru.itis.javalab.models.Well;

import java.util.List;
import java.util.Map;

public interface EquipmentService {
    void createEquipments(int count, String wellName);
    Map<Well, List<Equipment>> findAllByWells(List<Well> wells);
}
