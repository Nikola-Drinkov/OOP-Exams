package christmasPastryShop.entities.booths.interfaces;

import christmasPastryShop.entities.cocktails.interfaces.Cocktail;
import christmasPastryShop.entities.delicacies.interfaces.Delicacy;

import java.util.ArrayList;
import java.util.Collection;

import static christmasPastryShop.common.ExceptionMessages.INVALID_NUMBER_OF_PEOPLE;
import static christmasPastryShop.common.ExceptionMessages.INVALID_TABLE_CAPACITY;

public abstract class BaseBooth implements Booth{
    private Collection<Delicacy> delicacyOrders;
    private Collection<Cocktail> cocktailOrders;
    private int boothNumber;
    private int capacity;
    private int numberOfPeople;
    private double pricePerPerson;
    private boolean isReserved;
    private double price;

    public BaseBooth(int boothNumber, int capacity, double pricePerPerson) {
        this.boothNumber = boothNumber;
        setCapacity(capacity);
        this.pricePerPerson = pricePerPerson;
        delicacyOrders = new ArrayList<>();
        cocktailOrders = new ArrayList<>();
    }

    private void setCapacity(int capacity) {
        if(capacity<0) throw new IllegalArgumentException(INVALID_TABLE_CAPACITY);
        this.capacity = capacity;
    }
    @Override
    public int getBoothNumber() {
        return boothNumber;
    }

    @Override
    public int getCapacity() {
        return capacity;
    }

    @Override
    public boolean isReserved() {
        return isReserved;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public void reserve(int numberOfPeople) {
        if(numberOfPeople<=0) throw new IllegalArgumentException(INVALID_NUMBER_OF_PEOPLE);
        this.numberOfPeople = numberOfPeople;
        price = pricePerPerson*numberOfPeople;
        isReserved = true;
    }

    @Override
    public double getBill() {
        double sumCocktails = cocktailOrders.stream().mapToDouble(Cocktail::getPrice).sum();
        double sumDelicacies = delicacyOrders.stream().mapToDouble(Delicacy::getPrice).sum();
        return sumDelicacies+sumCocktails;
    }

    @Override
    public void clear() {
        cocktailOrders.clear();
        delicacyOrders.clear();
        isReserved = false;
        price = 0;
        numberOfPeople = 0;
    }
}
