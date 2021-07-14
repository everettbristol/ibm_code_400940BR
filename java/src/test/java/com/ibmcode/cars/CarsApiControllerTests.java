package com.ibmcode.cars;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Optional;

import com.ibmcode.cars.controller.CarController;
import com.ibmcode.cars.entity.Car;
import com.ibmcode.cars.service.CarRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CarController.class)
public class CarsApiControllerTests {
    
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarRepository carRepository;

    @Test
    public void getAllReturnsJsonArray() throws Exception {

        Car car1 = new Car();
        car1.setId(1);
        car1.setMake("alpha");
        car1.setModel("b");

        Car car2 = new Car();
        car2.setId(2);
        car2.setMake("y");
        car2.setModel("zed");

        ArrayList<Car> carList = new ArrayList<Car>();
        carList.add(car1);
        carList.add(car2);

        // mocks up the database call
        when(carRepository.findAll()).thenReturn(carList);

        this.mockMvc.perform(get("/api/car/list"))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("zed")))
            .andExpect(content().string(containsString("alpha")));
    }

    @Test
    public void verifyEmptyQueryResult404() throws Exception {

        // mock up the db call so it returns empty option
        when(carRepository.findById(1)).thenReturn(Optional.empty());

        // this should return statis of 404
        this.mockMvc.perform(get("/api/car/1"))
            .andExpect(status().isNotFound());
    }

    @Test
    public void verifyQueryResultReturned() throws Exception {

        // mock up db call so single car returned
        Car car1 = new Car();
        car1.setId(1);
        car1.setMake("alpha");
        when(carRepository.findById(1)).thenReturn(Optional.of(car1));

        // should return one car with 200 status
        this.mockMvc.perform(get("/api/car/1"))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("alpha")));
    }
}
