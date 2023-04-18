package zoo.core;

import zoo.entities.animals.Animal;
import zoo.entities.animals.AquaticAnimal;
import zoo.entities.animals.TerrestrialAnimal;
import zoo.entities.areas.Area;
import zoo.entities.areas.BaseArea;
import zoo.entities.areas.LandArea;
import zoo.entities.areas.WaterArea;
import zoo.entities.foods.Food;
import zoo.entities.foods.Meat;
import zoo.entities.foods.Vegetable;
import zoo.repositories.FoodRepository;
import zoo.repositories.FoodRepositoryImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Stream;

import static zoo.common.ConstantMessages.*;
import static zoo.common.ExceptionMessages.*;

public class ControllerImpl implements Controller {
    private FoodRepository foodRepository;
    private Collection<Area> areas;

    public ControllerImpl() {
        foodRepository = new FoodRepositoryImpl();
        areas = new ArrayList<>();
    }

    @Override
    public String addArea(String areaType, String areaName) {
        Area area;
        switch (areaType){
            case"WaterArea":
                area = new WaterArea(areaName);
                areas.add(area);
                return String.format(SUCCESSFULLY_ADDED_AREA_TYPE,"WaterArea");
            case"LandArea":
                area = new LandArea(areaName);
                areas.add(area);
                return String.format(SUCCESSFULLY_ADDED_AREA_TYPE,"LandArea");
            default:
                throw new NullPointerException(INVALID_AREA_TYPE);
        }
    }

    @Override
    public String buyFood(String foodType) {
        Food food;
        switch (foodType){
            case"Vegetable":
                food = new Vegetable();
                foodRepository.add(food);
                return String.format(SUCCESSFULLY_ADDED_FOOD_TYPE,"Vegetable");
            case"Meat":
                food = new Meat();
                foodRepository.add(food);
                return String.format(SUCCESSFULLY_ADDED_FOOD_TYPE,"Meat");
            default:
                throw new IllegalArgumentException(INVALID_FOOD_TYPE);
        }
    }

    @Override
    public String foodForArea(String areaName, String foodType) {
        Area currentArea = areas.stream().filter(x->x.getName().equals(areaName)).findFirst().orElse(null);
        Food currentFood = foodRepository.findByType(foodType);
        if (currentFood==null) throw new IllegalArgumentException(String.format(NO_FOOD_FOUND,foodType));
       currentArea.addFood(currentFood);
       foodRepository.remove(currentFood);
       return String.format(SUCCESSFULLY_ADDED_FOOD_IN_AREA,foodType,areaName);
    }

    @Override
    public String addAnimal(String areaName, String animalType, String animalName, String kind, double price) {
        Area currentArea = areas.stream().filter(x->x.getName().equals(areaName)).findFirst().orElse(null);
        Animal animal;
        switch (animalType){
            case"AquaticAnimal":
                animal = new AquaticAnimal(animalName,kind,price);
                if(currentArea.getClass().getSimpleName().equals("LandArea"))
                    return AREA_NOT_SUITABLE;
                break;
            case"TerrestrialAnimal":
                animal = new TerrestrialAnimal(animalName,kind,price);
                if(currentArea.getClass().getSimpleName().equals("WaterArea"))
                    return AREA_NOT_SUITABLE;
                break;
            default:
                throw new IllegalArgumentException(INVALID_ANIMAL_TYPE);
        }
        currentArea.addAnimal(animal);
        return String.format(SUCCESSFULLY_ADDED_ANIMAL_IN_AREA,animalType,areaName);
    }

    @Override
    public String feedAnimal(String areaName) {
        Area currentArea = areas.stream().filter(x->x.getName().equals(areaName)).findFirst().orElse(null);
        currentArea.getAnimals().forEach(Animal::eat);
        return String.format(ANIMALS_FED,currentArea.getAnimals().size());
    }

    @Override
    public String calculateKg(String areaName) {
        Area currentArea = areas.stream().filter(x->x.getName().equals(areaName)).findFirst().orElse(null);
        double sumKg = currentArea.getAnimals().stream().mapToDouble(Animal::getKg).sum();
        return String.format(KILOGRAMS_AREA,areaName,sumKg);
    }

    @Override
    public String getStatistics() {
        StringBuilder sb = new StringBuilder();
        for (Area area : areas){
            sb.append(area.getInfo());
            sb.append(System.lineSeparator());
        }
        return sb.toString().trim();
    }
}
