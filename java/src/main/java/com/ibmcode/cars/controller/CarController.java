package com.ibmcode.cars.controller;

import java.util.Optional;

import com.ibmcode.cars.entity.Car;
import com.ibmcode.cars.service.CarRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping(path="/api/car")
public class CarController {
    
    @Autowired
    private CarRepository carRepository;

    @GetMapping(path="/list")
    public @ResponseBody Iterable<Car> getCarList() {
        return carRepository.findAll();
    }

    @GetMapping(path="/{id}")
    public @ResponseBody Car getCar(@PathVariable Integer id) {
        Optional<Car> carResponse = carRepository.findById(id);

        if (carResponse.isPresent()) {
            return carResponse.get();
        } else {
            // if no car for given ID return an HTTP 404 status
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Car not found");
        }
    }

    @PostMapping(path="/add")
    public @ResponseBody Car addNewCar(
        @RequestBody Car carInput
    ) {

        // create a new car, input won't have ID so leave empty as we want
        // auto generated ID
        Car newCar = new Car();
        newCar.setMake(carInput.getMake());
        newCar.setModel(carInput.getModel());
        newCar.setYear(carInput.getYear());
        newCar.setColor(carInput.getColor());

        carRepository.save(newCar);

        return newCar;
    }
}
