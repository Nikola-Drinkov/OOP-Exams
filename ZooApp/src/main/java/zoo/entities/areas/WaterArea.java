package zoo.entities.areas;

public class WaterArea extends BaseArea{
    private static final int WATER_CAPACITY = 10;
    public WaterArea(String name) {
        super(name, WATER_CAPACITY);
    }
}
