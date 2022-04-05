package ru.itis.javalab.services;

import ru.itis.javalab.models.Equipment;
import ru.itis.javalab.models.Well;
import ru.itis.javalab.repository.EquipmentRepository;
import ru.itis.javalab.repository.EquipmentRepositoryImpl;
import ru.itis.javalab.repository.WellRepositoryImpl;

import java.util.List;
import java.util.Map;

public class UtilServiceImpl implements UtilService {
    private final EquipmentService equipmentService;
    private final WellService wellService;

    public UtilServiceImpl(EquipmentService equipmentService, WellService wellService) {
        this.equipmentService = equipmentService;
        this.wellService = wellService;
    }

    @Override
    public Map<Well, List<Equipment>> findAllDataToXML() {
        List<Well> wells = wellService.findAll();
        return equipmentService.findAllByWells(wells);
    }
    public static UtilServiceImpl getInstance() {
        WellRepositoryImpl wellRepository = new WellRepositoryImpl();
        EquipmentRepository equipmentRepository = new EquipmentRepositoryImpl(wellRepository);
        WellServiceImpl wellService = new WellServiceImpl(wellRepository,equipmentRepository);
        EquipmentServiceImpl equipmentService =  new EquipmentServiceImpl(equipmentRepository);
        return new UtilServiceImpl(equipmentService,wellService);
    }
}
