package christmasRaces.entities.cars;

import static christmasRaces.common.ExceptionMessages.INVALID_HORSE_POWER;

public class MuscleCar extends BaseCar{
    private static final double MUSCLE_CUBIC = 5000.00;
    public MuscleCar(String model, int horsePower) {
        super(model, horsePower, MUSCLE_CUBIC);
    }

    @Override
    protected void setHorsePower(int horsePower){
        if(horsePower<400 || horsePower>600) throw new IllegalArgumentException(String.format(INVALID_HORSE_POWER,horsePower));
        super.setHorsePower(horsePower);
    }
}
