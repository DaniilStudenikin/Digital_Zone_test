package ru.itis.javalab.services;

import ru.itis.javalab.models.Equipment;
import ru.itis.javalab.models.Well;

import java.util.List;
import java.util.Map;

public interface UtilService {
    Map<Well, List<Equipment>> findAllDataToXML();
}
