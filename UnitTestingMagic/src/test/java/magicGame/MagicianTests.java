package magicGame;

import org.junit.Assert;
import org.junit.Test;

public class MagicianTests {
    private Magician magician;
    private static final String DEFAULT_MAGICIAN_NAME = "Petar";
    private static final int DEFAULT_MAGICIAN_HEALTH = 150;
    private static final String DEFAULT_MAGIC_NAME = "Cherna magiq";
    private static final int DEFAULT_MAGIC_BULLETS = 100;
    @Test(expected = NullPointerException.class)
    public void testCreateMagicianWithNullUsername(){
        magician = new Magician(null,DEFAULT_MAGICIAN_HEALTH);
    }
    @Test(expected = NullPointerException.class)
    public void testCreateMagicianWithEmptyUsername(){
        magician = new Magician("    ",DEFAULT_MAGICIAN_HEALTH);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testCreateMagicianWithNegativeHealth(){
        magician = new Magician(DEFAULT_MAGICIAN_NAME,-150);
    }
    @Test
    public void testGetUsernameShouldReturnUsername(){
        magician = new Magician(DEFAULT_MAGICIAN_NAME,DEFAULT_MAGICIAN_HEALTH);
        Assert.assertEquals(DEFAULT_MAGICIAN_NAME,magician.getUsername());
    }
    @Test(expected = IllegalStateException.class)
    public void testTakeDamageWithDeadMagician(){
        magician = new Magician(DEFAULT_MAGICIAN_NAME,0);
        magician.takeDamage(50);
    }
    @Test
    public void testTakeDamageResultingInNegativeShouldSetHealthTo0(){
        magician = new Magician(DEFAULT_MAGICIAN_NAME, 20);
        magician.takeDamage(40);
        Assert.assertEquals(0,magician.getHealth());
    }
    @Test
    public void testTakeDamageStandard(){
        int startHealth = 40;
        int damage = 20;
        magician = new Magician(DEFAULT_MAGICIAN_NAME,startHealth);
        magician.takeDamage(damage);
        Assert.assertEquals(startHealth-damage,magician.getHealth());
    }
    @Test(expected = NullPointerException.class)
    public void testAddNullMagicToMagician(){
        Magician magician = new Magician(DEFAULT_MAGICIAN_NAME,DEFAULT_MAGICIAN_HEALTH);
        magician.addMagic(null);
    }
    @Test
    public void testAddMagicToMagician(){
        Magician magician = new Magician(DEFAULT_MAGICIAN_NAME,DEFAULT_MAGICIAN_HEALTH);
        Magic magic = new Magic(DEFAULT_MAGIC_NAME,DEFAULT_MAGIC_BULLETS);
        Assert.assertEquals(0,magician.getMagics().size());
        magician.addMagic(magic);
        Assert.assertEquals(1,magician.getMagics().size());
    }
    @Test
    public void testUnsuccessfulMagicRemovalShouldReturnFalse(){
        Magician magician = new Magician(DEFAULT_MAGICIAN_NAME,DEFAULT_MAGICIAN_HEALTH);
        Magic magic = new Magic(DEFAULT_MAGIC_NAME,DEFAULT_MAGIC_BULLETS);
        magician.addMagic(magic);
        Magic magicToRemove = new Magic("Ivanka",100);
        Assert.assertFalse(magician.removeMagic(magicToRemove));
    }
    @Test
    public void testSuccessfulMagicRemovalShouldReturnTrue(){
        Magician magician = new Magician(DEFAULT_MAGICIAN_NAME,DEFAULT_MAGICIAN_HEALTH);
        Magic magic = new Magic(DEFAULT_MAGIC_NAME,DEFAULT_MAGIC_BULLETS);
        magician.addMagic(magic);
        Assert.assertTrue(magician.removeMagic(magic));
    }
    @Test
    public void testNonExistentGetMagicShouldReturnNull(){
        Magic magic = new Magic(DEFAULT_MAGIC_NAME,DEFAULT_MAGIC_BULLETS);
        Magic magic1 = new Magic("Petko",150);
        Magician magician = new Magician(DEFAULT_MAGICIAN_NAME,DEFAULT_MAGICIAN_HEALTH);
        magician.addMagic(magic1);
        Magic result= magician.getMagic(magic.getName());
        Assert.assertNull(result);
    }
    @Test
    public void testGetMagicShouldReturnThisMagic(){
        Magic magic = new Magic(DEFAULT_MAGIC_NAME,DEFAULT_MAGIC_BULLETS);
        Magician magician = new Magician(DEFAULT_MAGICIAN_NAME,DEFAULT_MAGICIAN_HEALTH);
        magician.addMagic(magic);
        Magic result= magician.getMagic(magic.getName());
        Assert.assertEquals(magic,result);
    }
}
