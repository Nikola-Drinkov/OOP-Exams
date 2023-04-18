package robotService.entities.robot;

public class MaleRobot extends BaseRobot{
    private static final int MALE_KG = 9;
    public MaleRobot(String name, String kind, double price) {
        super(name, kind, MALE_KG, price);
    }

    @Override
    public void eating() {
        setKilograms(getKilograms()+3);
    }
}
