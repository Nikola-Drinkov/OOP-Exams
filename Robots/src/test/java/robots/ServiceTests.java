package robots;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ServiceTests {
    private static final String ROBOT_NAME = "Ivan";
    private Robot default_robot;
    private static final String SERVICE_NAME = "Firma";
    private static final int SERVICE_CAPACITY = 10;
    private Service default_service;
    @Before
    public void setup(){
        default_robot = new Robot(ROBOT_NAME);
        default_service = new Service(SERVICE_NAME,SERVICE_CAPACITY);
    }
    @Test
    public void testCreateRobot(){
        Robot robot = new Robot("Joro");
        Assert.assertEquals("Joro", robot.getName());
        Assert.assertTrue(robot.isReadyForSale());
    }
    @Test(expected = NullPointerException.class)
    public void testCreateServiceWithNullName(){
        Service service = new Service(null, SERVICE_CAPACITY);
    }
    @Test(expected = NullPointerException.class)
    public void testCreateServiceWithEmptyName(){
        Service service = new Service("    ",SERVICE_CAPACITY);
    }
    @Test
    public void testGetServiceCapacity(){
        Assert.assertEquals(SERVICE_CAPACITY, default_service.getCapacity());
    }
    @Test(expected = IllegalArgumentException.class)
    public void testCreateServiceWithNegativeCapacity(){
        Service service = new Service(SERVICE_NAME,-1);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testAddRobotToFullService(){
        Service service = new Service(SERVICE_NAME, 1);
        service.add(default_robot);
        service.add(default_robot);
    }
    @Test
    public void testAddRobotToService(){
        default_service.add(default_robot);
        Assert.assertEquals(1, default_service.getCount());
    }
    @Test(expected = IllegalArgumentException.class)
    public void testRemoveNonExistingRobot(){
        Robot toRemove = new Robot("Jordan");
        default_service.add(default_robot);
        default_service.remove(toRemove.getName());
    }
    @Test
    public void testRemoveExistingRobot(){
        default_service.add(default_robot);
        Assert.assertEquals(1, default_service.getCount());
        default_service.remove(default_robot.getName());
        Assert.assertEquals(0, default_service.getCount());
    }
    @Test(expected = IllegalArgumentException.class)
    public void testSetNonExistingRobotForSale(){
        Robot toRemove = new Robot("Jordan");
        default_service.add(default_robot);
        default_service.forSale(toRemove.getName());
    }
    @Test
    public void testSetExistingRobotForSale(){
        default_service.add(default_robot);
        default_service.forSale(default_robot.getName());
        Assert.assertFalse(default_robot.isReadyForSale());
    }
    @Test
    public void testServiceReport(){
        default_service.add(default_robot);
        default_service.add(new Robot("Koko"));
        String expected = "The robot Ivan, Koko is in the service Firma!";
        Assert.assertEquals(expected, default_service.report());
    }
}
