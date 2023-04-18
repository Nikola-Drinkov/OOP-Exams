package petStore;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class PetStoreTests {
    private static final String DEFAULT_SPECIE = "Camel";
    private static final int DEFAULT_MAX_KGS = 40;
    private static final double DEFAULT_PRICE = 465.23;
    private static Animal defaultAnimal;
    private static PetStore petStore;

    @Before
    public void setup(){
        defaultAnimal = new Animal(DEFAULT_SPECIE,DEFAULT_MAX_KGS,DEFAULT_PRICE);
        petStore = new PetStore();
    }

    @Test
    public void testCreateAnimal(){
        Animal animal = new Animal(DEFAULT_SPECIE,DEFAULT_MAX_KGS,DEFAULT_PRICE);
        Assert.assertEquals(DEFAULT_SPECIE, animal.getSpecie());
        Assert.assertEquals(DEFAULT_MAX_KGS,animal.getMaxKilograms());
        Assert.assertEquals(DEFAULT_PRICE, animal.getPrice(),0);
    }
    @Test
    public void testSetAnimalAge(){
        defaultAnimal.setAge(40);
        Assert.assertEquals(40,defaultAnimal.getAge());
    }

    @Test
    public void testGetCountPetStore(){
        petStore.addAnimal(defaultAnimal);
        Assert.assertEquals(1, petStore.getCount());
    }
    @Test
    public void testFindAllAnimalsWithMaxKilograms(){
        Animal animal1 = new Animal(DEFAULT_SPECIE,DEFAULT_MAX_KGS,DEFAULT_PRICE);
        Animal animal2 = new Animal(DEFAULT_SPECIE,10,DEFAULT_PRICE);
        Animal animal3 = new Animal(DEFAULT_SPECIE,80,DEFAULT_PRICE);
        petStore.addAnimal(animal1);
        petStore.addAnimal(animal2);
        petStore.addAnimal(animal3);
        List<Animal> animals = petStore.findAllAnimalsWithMaxKilograms(15);
        List<Animal> expected = new ArrayList<>();
        expected.add(animal1);
        expected.add(animal3);
        Assert.assertEquals(animals,expected);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testAddNullAnimal(){
        petStore.addAnimal(null);
    }
    @Test
    public void testAddAnimal(){
        petStore.addAnimal(defaultAnimal);
        Assert.assertEquals(1,petStore.getCount());
    }
    @Test
    public void testGetTheMostExpensiveAnimal(){
        Animal animal1 = new Animal(DEFAULT_SPECIE,DEFAULT_MAX_KGS,10);
        Animal animal2 = new Animal(DEFAULT_SPECIE,DEFAULT_MAX_KGS,15);
        Animal animal3 = new Animal(DEFAULT_SPECIE,DEFAULT_MAX_KGS,800);
        petStore.addAnimal(animal1);
        petStore.addAnimal(animal2);
        petStore.addAnimal(animal3);
        Animal mostExpensive = petStore.getTheMostExpensiveAnimal();
        Assert.assertEquals(animal3,mostExpensive);
    }
    @Test
    public void testGetTheMostExpensiveAnimalInEmptyStore(){
        Animal mostExpensive = petStore.getTheMostExpensiveAnimal();
        Assert.assertNull(mostExpensive);
    }
    @Test
    public void testFindAllAnimalBySpecie(){
        Animal animal1 = new Animal(DEFAULT_SPECIE,DEFAULT_MAX_KGS,10);
        Animal animal2 = new Animal(DEFAULT_SPECIE,DEFAULT_MAX_KGS,15);
        Animal animal3 = new Animal("Koza",DEFAULT_MAX_KGS,800);
        petStore.addAnimal(animal1);
        petStore.addAnimal(animal2);
        petStore.addAnimal(animal3);
        List<Animal> expected = new ArrayList<>();
        expected.add(animal1);
        expected.add(animal2);
        List<Animal> allOfSpecies = petStore.findAllAnimalBySpecie(DEFAULT_SPECIE);
        Assert.assertEquals(expected,allOfSpecies);
    }
}

