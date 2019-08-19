package com.ghifar.demoku.controller;

import com.ghifar.demoku.domain.CarRepository;
import com.ghifar.demoku.entity.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CarController {

    @Autowired
    private CarRepository carRepository;


    @RequestMapping("/cars")
    public Iterable<Car> getCars(){
        return carRepository.findAll();

    }
}
