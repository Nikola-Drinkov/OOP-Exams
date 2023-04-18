package christmasPastryShop.core;

import christmasPastryShop.core.interfaces.Controller;
import christmasPastryShop.entities.booths.interfaces.OpenBooth;
import christmasPastryShop.entities.booths.interfaces.PrivateBooth;
import christmasPastryShop.entities.cocktails.interfaces.Hibernation;
import christmasPastryShop.entities.cocktails.interfaces.MulledWine;
import christmasPastryShop.entities.delicacies.interfaces.Delicacy;
import christmasPastryShop.entities.cocktails.interfaces.Cocktail;
import christmasPastryShop.entities.booths.interfaces.Booth;
import christmasPastryShop.entities.delicacies.interfaces.Gingerbread;
import christmasPastryShop.entities.delicacies.interfaces.Stolen;
import christmasPastryShop.repositories.interfaces.*;

import java.util.Arrays;
import java.util.Collections;

import static christmasPastryShop.common.ExceptionMessages.BOOTH_EXIST;
import static christmasPastryShop.common.ExceptionMessages.FOOD_OR_DRINK_EXIST;
import static christmasPastryShop.common.OutputMessages.*;

public class ControllerImpl implements Controller {
    private DelicacyRepository<Delicacy> delicacyRepository;
    private CocktailRepository<Cocktail> cocktailRepository;
    private BoothRepository<Booth> boothRepository;
    private double totalIncome;
    public ControllerImpl(DelicacyRepository<Delicacy> delicacyRepository, CocktailRepository<Cocktail> cocktailRepository, BoothRepository<Booth> boothRepository) {
        this.delicacyRepository = delicacyRepository;
        this.cocktailRepository = cocktailRepository;
        this.boothRepository = boothRepository;
    }


    @Override
    public String addDelicacy(String type, String name, double price) {
        Delicacy checkDelicacy = delicacyRepository.getByName(name);
        if(checkDelicacy!=null){
            throw new IllegalArgumentException(String.format(FOOD_OR_DRINK_EXIST,checkDelicacy.getClass().getSimpleName(),checkDelicacy.getName()));
        }
        Delicacy toAdd;
        switch (type){
            case "Gingerbread":
                 toAdd = new Gingerbread(name,price);
                delicacyRepository.add(toAdd);
                break;
            case"Stolen":
                toAdd = new Stolen(name,price);
                delicacyRepository.add(toAdd);
                break;
        }
        return String.format(DELICACY_ADDED,name,type);
    }

    @Override
    public String addCocktail(String type, String name, int size, String brand) {
        Cocktail checkCocktail = cocktailRepository.getByName(name);
        if(checkCocktail!=null){
            throw new IllegalArgumentException(String.format(FOOD_OR_DRINK_EXIST,checkCocktail.getClass().getSimpleName(),checkCocktail.getName()));
        }
        Cocktail toAdd;
        switch (type){
            case "Hibernation":
                toAdd = new Hibernation(name,size,brand);
                cocktailRepository.add(toAdd);
                break;
            case"MulledWine":
                toAdd = new MulledWine(name,size,brand);
                cocktailRepository.add(toAdd);
                break;
        }
        return String.format(COCKTAIL_ADDED,name,brand);
    }

    @Override
    public String addBooth(String type, int boothNumber, int capacity) {
        Booth checkBooth = boothRepository.getByNumber(boothNumber);
        if(checkBooth!=null){
            throw new IllegalArgumentException(String.format(BOOTH_EXIST,boothNumber));
        }
        Booth toAdd;
        switch (type){
            case "OpenBooth":
                toAdd = new OpenBooth(boothNumber,capacity);
                boothRepository.add(toAdd);
                break;
            case"PrivateBooth":
                toAdd = new PrivateBooth(boothNumber,capacity);
                boothRepository.add(toAdd);
                break;
        }
        return String.format(BOOTH_ADDED, boothNumber);
    }

    @Override
    public String reserveBooth(int numberOfPeople) {
        Booth suitableBooth = boothRepository.getAll().stream()
                .filter(x->!x.isReserved() && x.getCapacity()>=numberOfPeople)
                .findFirst()
                .orElse(null);
        if(suitableBooth!=null){
            suitableBooth.reserve(numberOfPeople);
            return String.format(BOOTH_RESERVED,suitableBooth.getBoothNumber(),numberOfPeople);
        }
        else {
            return String.format(RESERVATION_NOT_POSSIBLE,numberOfPeople);
        }
    }

    @Override
    public String leaveBooth(int boothNumber) {
       Booth currentBooth = boothRepository.getAll().stream()
               .filter(x->x.getBoothNumber()==boothNumber)
               .findFirst()
               .orElse(null);

       double currentBoothBill = 0;
       if (currentBooth!=null){
           currentBoothBill = currentBooth.getBill()+currentBooth.getPrice();
           currentBooth.clear();
           totalIncome+=currentBoothBill;
           return String.format(BILL,currentBooth.getBoothNumber(),currentBoothBill);
       }
      return "";
    }

    @Override
    public String getIncome() {
        return String.format(TOTAL_INCOME,totalIncome);
    }
}
