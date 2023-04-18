package christmasRaces.repositories.interfaces;

import christmasRaces.entities.cars.Car;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class CarRepository implements Repository<Car>{
    private Collection<Car> cars;

    public CarRepository() {
        cars = new ArrayList<>();
    }

    @Override
    public Car getByName(String model) {
        return cars.stream().filter(x->x.getModel().equals(model)).findFirst().orElse(null);
    }

    @Override
    public Collection<Car> getAll() {
        return Collections.unmodifiableCollection(cars);
    }

    @Override
    public void add(Car car) {
        cars.add(car);
    }

    @Override
    public boolean remove(Car car) {
        return cars.remove(car);
    }
}
