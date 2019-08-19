package com.ghifar.demoku;

import com.ghifar.demoku.domain.CarRepository;
import com.ghifar.demoku.entity.Car;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

//focus of this class is to only test JPA components
@RunWith(SpringRunner.class)
@DataJpaTest
public class CarRepositoryTest {

    @Autowired
    TestEntityManager testEntityManager; //use to handle persist entities for testing purpose

    @Autowired
    CarRepository carRepository;

    @Test
    public void saveCar(){
//        carRepository.save(new Car("Ford","SUV","Red","DD799PP",2017,100000));
//        carRepository.save(new Car("Ford2","SUV2","Red2","DD799PP",2018,120000));
//        carRepository.save(new Car("Ford3","SUV3","Red3","DD799PP",2019,130000));

        Car car= new Car("Ford3","SUV3","Red3","DD799PP",2019,130000);

        testEntityManager.persistAndFlush(car);

        Assertions.assertThat(car.getId()).isNotNull();

    }

    @Test
    public void deleteCar(){
        testEntityManager.persistAndFlush(new Car("Ford","SUV","Red","DD799PP",2017,100000));
        testEntityManager.persistAndFlush(new Car("Ford2","SUV2","Red","DD799PP",2017,100000));
        testEntityManager.persistAndFlush(new Car("Ford3","SUV3","Red","DD799PP",2017,100000));

        System.out.println("OI >>> "+carRepository.findAll());

        carRepository.deleteAll();

        System.out.println(carRepository.findAll());

        Assertions.assertThat(carRepository.findAll()).isEmpty();

    }
}
