package zoo.entities.areas;

import zoo.entities.animals.Animal;
import zoo.entities.animals.BaseAnimal;
import zoo.entities.foods.Food;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import static zoo.common.ExceptionMessages.AREA_NAME_NULL_OR_EMPTY;
import static zoo.common.ExceptionMessages.NOT_ENOUGH_CAPACITY;

public abstract class BaseArea implements Area {
    private String name;
    private int capacity;
    private Collection<Food> foods;
    private Collection<Animal> animals;

    public BaseArea(String name, int capacity) {
        setName(name);
        setCapacity(capacity);
        foods = new ArrayList<>();
        animals = new ArrayList<>();
    }

    private void setName(String name) {
        if(name==null || name.trim().isEmpty()) throw new NullPointerException(AREA_NAME_NULL_OR_EMPTY);
        this.name = name;
    }

    private void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Collection<Animal> getAnimals() {
        return animals;
    }

    @Override
    public Collection<Food> getFoods() {
        return foods;
    }

    @Override
    public int sumCalories() {
        return foods.stream().mapToInt(Food::getCalories).sum();
    }

    @Override
    public void addAnimal(Animal animal) {
        if(animals.size()<capacity){
            animals.add(animal);
        }
        else throw new IllegalStateException(NOT_ENOUGH_CAPACITY);
    }

    @Override
    public void removeAnimal(Animal animal) {
        animals.remove(animal);
    }

    @Override
    public void addFood(Food food) {
        foods.add(food);
    }

    @Override
    public void feed() {
        animals.forEach(Animal::eat);
    }

    @Override
    public String getInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%s (%s):",name, this.getClass().getSimpleName())).append(System.lineSeparator());
        sb.append("Animals: ");
        if(!animals.isEmpty()) sb.append(animals.stream().map(Animal::getName).collect(Collectors.joining(" ")));
        else sb.append("none");
        sb.append(System.lineSeparator());
        sb.append("Foods: ").append(foods.size()).append(System.lineSeparator());
        sb.append("Calories: ").append(sumCalories());

        return sb.toString();
    }
}
