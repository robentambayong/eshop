package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.repository.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarServiceImplTest {

    @InjectMocks
    private CarServiceImpl carService;

    @Mock
    private CarRepository carRepository;

    private Car car;

    @BeforeEach
    void setUp() {
        car = new Car();
        car.setCarId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        car.setCarName("Toyota Supra");
        car.setCarColor("Red");
        car.setCarQuantity(1);
    }

    @Test
    void testCreate() {
        when(carRepository.create(car)).thenReturn(car);
        Car createdCar = carService.create(car);
        assertEquals(car.getCarId(), createdCar.getCarId());
        verify(carRepository, times(1)).create(car);
    }

    @Test
    void testFindAll() {
        List<Car> carList = new ArrayList<>();
        carList.add(car);
        Iterator<Car> carIterator = carList.iterator();

        when(carRepository.findAll()).thenReturn(carIterator);

        List<Car> allCars = carService.findAll();

        assertNotNull(allCars);
        assertEquals(1, allCars.size());
        assertEquals(car.getCarId(), allCars.get(0).getCarId());
        verify(carRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        when(carRepository.findById(car.getCarId())).thenReturn(car);
        Car foundCar = carService.findById(car.getCarId());
        assertEquals(car.getCarId(), foundCar.getCarId());
        verify(carRepository, times(1)).findById(car.getCarId());
    }

    @Test
    void testUpdate() {
        carService.update(car.getCarId(), car);
        verify(carRepository, times(1)).update(car.getCarId(), car);
    }

    @Test
    void testDelete() {
        carService.deleteCarById(car.getCarId());
        verify(carRepository, times(1)).delete(car.getCarId());
    }
}