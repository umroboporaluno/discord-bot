package br.natalnet.ura.bot.entity.car.controller;

import br.natalnet.ura.bot.entity.car.Car;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
public class CarController {

    private final List<Car> cars;

    public CarController() {
        this.cars = new ArrayList<>();
    }

    public void addCar(Car car) {
        cars.add(car);
    }

    public void removeCar(Car car) {
        cars.remove(car);
    }

    public Collection<Car> getCars() {
        return cars;
    }
}
