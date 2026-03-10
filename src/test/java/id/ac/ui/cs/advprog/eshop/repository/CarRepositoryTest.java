package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class CarRepositoryTest {

    private CarRepository carRepository;

    @BeforeEach
    void setUp() {
        carRepository = new CarRepository();
    }

    @Test
    void testCreateWithId() {
        Car car = new Car();
        car.setCarId("1");
        Car result = carRepository.create(car);
        assertEquals("1", result.getCarId());
    }

    @Test
    void testCreateWithNullId() {
        Car car = new Car();
        car.setCarId(null);
        Car result = carRepository.create(car);
        assertNotNull(result.getCarId());
    }

    @Test
    void testFindAll() {
        Car car = new Car();
        carRepository.create(car);
        Iterator<Car> carIterator = carRepository.findAll();
        assertTrue(carIterator.hasNext());
    }

    @Test
    void testFindByIdSuccess() {
        Car car = new Car();
        car.setCarId("1");
        carRepository.create(car);
        Car foundCar = carRepository.findById("1");
        assertEquals("1", foundCar.getCarId());
    }

    @Test
    void testUpdateSuccess() {
        Car car = new Car();
        car.setCarId("1");
        car.setCarName("Old Name");
        carRepository.create(car);

        Car updatedCar = new Car();
        updatedCar.setCarName("New Name");
        updatedCar.setCarColor("Blue");
        updatedCar.setCarQuantity(5);

        Car result = carRepository.update("1", updatedCar);
        assertNotNull(result);
        assertEquals("New Name", result.getCarName());
    }

    @Test
    void testUpdateNotFound() {
        Car result = carRepository.update("non-existent", new Car());
        assertNull(result);
    }

    @Test
    void testDelete() {
        Car car = new Car();
        car.setCarId("1");
        carRepository.create(car);

        carRepository.delete("1");
        assertNull(carRepository.findById("1"));
    }

    @Test
    void testFindByIdIfEmpty() {
        assertNull(carRepository.findById("any-id"));
    }

    @Test
    void testUpdateIfEmpty() {
        assertNull(carRepository.update("any-id", new Car()));
    }

    @Test
    void testFindByIdNotFound() {
        Car car = new Car();
        car.setCarId("1");
        carRepository.create(car);
        assertNull(carRepository.findById("2"));
    }

    @Test
    void testFindByIdWhenIdIsDifferent() {
        Car car = new Car();
        car.setCarId("car-1");
        carRepository.create(car);
        Car result = carRepository.findById("car-2");
        assertNull(result);
    }

    @Test
    void testUpdateWhenIdIsDifferent() {
        Car car = new Car();
        car.setCarId("car-1");
        carRepository.create(car);
        Car newCarData = new Car();
        newCarData.setCarName("New Name");
        Car result = carRepository.update("car-2", newCarData);
        assertNull(result);
    }
}