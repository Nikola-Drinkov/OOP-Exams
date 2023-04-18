package robotService.entities.supplements;

public class MetalArmor extends BaseSupplement{
    private static final int METAL_HARDNESS = 5;
    private static final double METAL_PRICE = 15.00;
    public MetalArmor() {
        super(METAL_HARDNESS, METAL_PRICE);
    }
}
