package gifts;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GiftFactoryTests {
    private static final String GIFT_TYPE = "cheren";
    private static final double GIFT_MAGIC = 75;
    private static Gift default_gift;
    private static GiftFactory default_factory;

    @Before
    public void setup(){
        default_gift = new Gift(GIFT_TYPE,GIFT_MAGIC);
        default_factory = new GiftFactory();
    }
    @Test
    public void testCreateGift(){
        Assert.assertEquals(String.format("Successfully added gift %s with magic %.2f.",GIFT_TYPE,GIFT_MAGIC),default_factory.createGift(default_gift));
        Assert.assertEquals(1, default_factory.getCount());
    }
    @Test(expected = IllegalArgumentException.class)
    public void testCreateExistingGift(){
        default_factory.createGift(default_gift);
        default_factory.createGift(default_gift);
    }
    @Test(expected = NullPointerException.class)
    public void testRemoveNullGift(){
        default_factory.removeGift(null);
    }
    @Test(expected = NullPointerException.class)
    public void testRemoveWhitespaceGift(){
        default_factory.removeGift("        ");
    }
    @Test
    public void testRemoveNonExistingGift(){
        default_factory.createGift(default_gift);
        Assert.assertFalse(default_factory.removeGift("Ivan"));
    }
    @Test
    public void testRemoveGift(){
        default_factory.createGift(default_gift);
        Assert.assertTrue(default_factory.removeGift(GIFT_TYPE));
    }
    @Test
    public void testGetGiftWithLeastMagic(){
        Gift gift1 = new Gift("Zelen",50);
        Gift gift2 = new Gift("Pesho", 43);
        Gift gift3 = new Gift("Oranjav", 632);
        default_factory.createGift(gift1);
        default_factory.createGift(gift2);
        default_factory.createGift(gift3);
        Assert.assertEquals(gift2,default_factory.getPresentWithLeastMagic());
    }
    @Test
    public void testUnsuccessfulGetPresent(){
        Gift gift = default_factory.getPresent("Tosho");
        Assert.assertNull(gift);
    }
    @Test
    public void testSuccessfulGetPresent(){
        Gift gift = new Gift("Petko",87);
        default_factory.createGift(default_gift);
        default_factory.createGift(gift);
        Assert.assertEquals(gift, default_factory.getPresent("Petko"));
    }
}
