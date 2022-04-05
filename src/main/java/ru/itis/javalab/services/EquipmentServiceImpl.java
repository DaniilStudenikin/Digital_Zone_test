package ru.itis.javalab.services;

import ru.itis.javalab.models.Equipment;
import ru.itis.javalab.models.Well;
import ru.itis.javalab.repository.EquipmentRepository;
import ru.itis.javalab.repository.EquipmentRepositoryImpl;
import ru.itis.javalab.repository.WellRepositoryImpl;

import java.util.List;
import java.util.Map;

public class EquipmentServiceImpl implements EquipmentService {
    private final EquipmentRepository repository;

    public EquipmentServiceImpl(EquipmentRepository repository) {
        this.repository = repository;
    }

    @Override
    public void createEquipments(int count, String wellName) {
        repository.create(count, wellName);
    }

    @Override
    public Map<Well, List<Equipment>> findAllByWells(List<Well> wells) {
        return repository.findAllByWells(wells);
    }

    public static EquipmentService getInstance() {
        return new EquipmentServiceImpl(new EquipmentRepositoryImpl(new WellRepositoryImpl()));

    }
}
