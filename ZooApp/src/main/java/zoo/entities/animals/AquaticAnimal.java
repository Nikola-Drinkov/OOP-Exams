package zoo.entities.animals;

public class AquaticAnimal extends BaseAnimal{
    private static final double AQUATIC_KG = 2.50;
    public AquaticAnimal(String name, String kind, double price) {
        super(name, kind, AQUATIC_KG, price);
    }

    @Override
    public void eat() {
        setKg(getKg()+7.50);
    }
}
