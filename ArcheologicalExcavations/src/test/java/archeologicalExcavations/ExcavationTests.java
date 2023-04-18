package archeologicalExcavations;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ExcavationTests {
    private static final String  ARCHEOLOGIST_NAME = "PEEV";
    private static final double ARCHEOLOGIST_ENERGY = 25.5;
    private static final String EXCAVATION_NAME = "Peevska";
    private static final int EXCAVATION_CAPACITY = 10;
    private Archaeologist archaeologist;
    private Excavation excavation;

    @Before
    public void setup(){
        this.archaeologist = new Archaeologist(ARCHEOLOGIST_NAME,ARCHEOLOGIST_ENERGY);
        this.excavation = new Excavation(EXCAVATION_NAME,EXCAVATION_CAPACITY);
    }
    @Test(expected = NullPointerException.class)
    public void testCreateExcavationWithoutNameShouldFail(){
        Excavation excavation = new Excavation(null,EXCAVATION_CAPACITY);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testCreateExcavationWithNegativeCapacityShouldFail(){
        Excavation excavation = new Excavation(EXCAVATION_NAME,-1);
    }
    @Test(expected = NullPointerException.class)
    public void testCreateExcavationWithEmptyNameShouldFail(){
        Excavation excavation = new Excavation("     ",EXCAVATION_CAPACITY);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testAddArcheologistToFullExcavationShouldFail(){
        Excavation excavation = new Excavation(EXCAVATION_NAME,0);
        excavation.addArchaeologist(archaeologist);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testAddExistingArcheologistToExcavationShouldFail(){
        excavation.addArchaeologist(archaeologist);
        excavation.addArchaeologist(archaeologist);
    }
    @Test
    public void testAddArcheologistToExcavationShouldIncreaseSize(){
        excavation.addArchaeologist(archaeologist);
        Assert.assertEquals(1,excavation.getCount());
    }
    @Test
    public void testRemoveArcheologistFromExcavationShouldDecreaseSize(){
        excavation.addArchaeologist(archaeologist);
        Assert.assertEquals(1,excavation.getCount());
        Assert.assertTrue(excavation.removeArchaeologist(archaeologist.getName()));
        Assert.assertEquals(0,excavation.getCount());
    }
    @Test
    public void restRemoveNonExistentArcheologistFromExcavationShouldReturnFalse(){
        Archaeologist archaeologist1 = new Archaeologist("Shishman",ARCHEOLOGIST_ENERGY);
        excavation.addArchaeologist(archaeologist);
        Assert.assertFalse(excavation.removeArchaeologist(archaeologist1.getName()));
        Assert.assertEquals(1, excavation.getCount());
    }
}
