package christmasRaces.entities.cars;

import static christmasRaces.common.ExceptionMessages.INVALID_HORSE_POWER;

public class SportsCar extends BaseCar{
    private static final double SPORTS_CUBIC = 3000.00;
    public SportsCar(String model, int horsePower) {
        super(model, horsePower, SPORTS_CUBIC);
    }

    @Override
    protected void setHorsePower(int horsePower){
        if(horsePower<250 || horsePower>450) throw new IllegalArgumentException(String.format(INVALID_HORSE_POWER,horsePower));
        super.setHorsePower(horsePower);
    }
}
