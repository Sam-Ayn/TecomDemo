package ru.tecom.test.demo.service;

import ru.tecom.test.demo.entity.Car;

import java.util.List;
import java.util.Map;

public interface CarService {
    List<Car> findAll();

    Car findById(long id);

    void save(Car employee);

    void deleteById(long id);

    List<Car> findAllByParameters(Map<String, String> parameters);
}

