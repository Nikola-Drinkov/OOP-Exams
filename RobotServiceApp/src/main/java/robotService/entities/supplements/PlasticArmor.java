package robotService.entities.supplements;

public class PlasticArmor extends BaseSupplement{
    private static final int PLASTIC_HARDNESS = 1;
    private static final double PLASTIC_PRICE = 10.00;
    public PlasticArmor() {
        super(PLASTIC_HARDNESS, PLASTIC_PRICE);
    }
}
