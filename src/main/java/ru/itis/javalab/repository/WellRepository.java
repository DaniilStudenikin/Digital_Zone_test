package ru.itis.javalab.repository;

import ru.itis.javalab.models.Well;

import java.util.List;
import java.util.Optional;

public interface WellRepository {
    Well findByName(String name);
    void create(String name);
    List<Well> findAll();
}
