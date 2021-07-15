package com.ibmcode.cars.service;

import com.ibmcode.cars.entity.Car;
import org.springframework.data.repository.CrudRepository;

public interface CarRepository extends CrudRepository<Car, Integer> {
    /* NOTE:
    * This interface does not require any code as the SpringData Crud extension
    * will take care of creating a proper bean.
    * Most of the time I would not use this convenience method in favor of a
    * more tailored data service, only using this to quickly scaffold this project.
    */
}
