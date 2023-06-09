package christmasRaces.core;

import christmasRaces.core.interfaces.Controller;
import christmasRaces.entities.cars.Car;
import christmasRaces.entities.cars.MuscleCar;
import christmasRaces.entities.cars.SportsCar;
import christmasRaces.entities.drivers.Driver;
import christmasRaces.entities.drivers.DriverImpl;
import christmasRaces.entities.races.Race;
import christmasRaces.entities.races.RaceImpl;
import christmasRaces.repositories.interfaces.Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static christmasRaces.common.ExceptionMessages.*;
import static christmasRaces.common.OutputMessages.*;

public class ControllerImpl implements Controller {
    private Repository<Driver> driverRepository;
    private Repository<Car> carRepository;
    private Repository<Race> raceRepository;
    public ControllerImpl(Repository<Driver> driverRepository, Repository<Car> carRepository, Repository<Race> raceRepository) {
        this.driverRepository = driverRepository;
        this.carRepository = carRepository;
        this.raceRepository = raceRepository;
    }

    @Override
    public String createDriver(String driverName) {
        Driver driver = new DriverImpl(driverName);
        if(driverRepository.getByName(driverName)!=null) throw new IllegalArgumentException(String.format(DRIVER_EXISTS,driverName));

        driverRepository.add(driver);
        return String.format(DRIVER_CREATED,driverName);
    }

    @Override
    public String createCar(String type, String model, int horsePower) {
        Car car = null;
        switch (type){
            case"Muscle":
                car = new MuscleCar(model, horsePower);
                break;
            case"Sports":
                car = new SportsCar(model,horsePower);
                break;
        }
        if (carRepository.getByName(model)!=null) throw new IllegalArgumentException(String.format(CAR_EXISTS,model));

        carRepository.add(car);
        return String.format(CAR_CREATED, car.getClass().getSimpleName(),model);
    }

    @Override
    public String addCarToDriver(String driverName, String carModel) {
        Driver driver = driverRepository.getByName(driverName);
        Car car = carRepository.getByName(carModel);
        if(driver==null) throw new IllegalArgumentException(String.format(DRIVER_NOT_FOUND,driverName));
        if(car==null) throw new IllegalArgumentException(String.format(CAR_NOT_FOUND,carModel));

        driver.addCar(car);
        return String.format(CAR_ADDED,driverName,carModel);
    }

    @Override
    public String addDriverToRace(String raceName, String driverName) {
        Race race = raceRepository.getByName(raceName);
        Driver driver = driverRepository.getByName(driverName);
        if(race==null) throw new IllegalArgumentException(String.format(RACE_NOT_FOUND,raceName));
        if(driver==null) throw new IllegalArgumentException(String.format(DRIVER_NOT_FOUND,driverName));

        race.addDriver(driver);
        return String.format(DRIVER_ADDED,driverName,raceName);
    }

    @Override
    public String startRace(String raceName) {
        Race race = raceRepository.getByName(raceName);
        if(race==null) throw new IllegalArgumentException(String.format(RACE_NOT_FOUND,raceName));
        if (race.getDrivers().size()<3) throw new IllegalArgumentException(String.format(RACE_INVALID,raceName,3));

        List<Driver> suitableDrivers = driverRepository.getAll().stream().filter(Driver::getCanParticipate).collect(Collectors.toList());
        suitableDrivers = suitableDrivers.stream()
                .sorted(Comparator.comparing(x->x.getCar().calculateRacePoints(race.getLaps())))
                .limit(3)
                .collect(Collectors.toList());

        /*List<Driver> finalDrivers = new ArrayList<>();
        for(int i=suitableDrivers.size()-1; i>=0; i--){
            finalDrivers.add(suitableDrivers.get(i));
        }*/

        StringBuilder sb = new StringBuilder();
        sb.append(String.format(DRIVER_FIRST_POSITION,suitableDrivers.get(2).getName(),raceName)).append(System.lineSeparator());
        sb.append(String.format(DRIVER_SECOND_POSITION,suitableDrivers.get(1).getName(),raceName)).append(System.lineSeparator());
        sb.append(String.format(DRIVER_THIRD_POSITION,suitableDrivers.get(0).getName(),raceName)).append(System.lineSeparator());

        return sb.toString().trim();
    }

    @Override
    public String createRace(String name, int laps) {
        Race race = new RaceImpl(name,laps);
        if (raceRepository.getByName(name)!=null) throw new IllegalArgumentException(String.format(RACE_EXISTS,name));

        raceRepository.add(race);
        return String.format(RACE_CREATED,name);
    }
}
