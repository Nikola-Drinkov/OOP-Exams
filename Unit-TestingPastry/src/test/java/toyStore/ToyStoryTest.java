package toyStore;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.naming.OperationNotSupportedException;
import java.util.LinkedHashMap;
import java.util.Map;

public class ToyStoryTest {
    private Toy defaultToy;
    private ToyStore store;
    private static final String DEFAULT_TOY_MANUFACTURER = "Petur4o";
    private static final String DEFAULT_TOY_ID = "4z16";

    @Before
    public void setup(){
        defaultToy = new Toy(DEFAULT_TOY_MANUFACTURER,DEFAULT_TOY_ID);
        store = new ToyStore();
    }

    @Test
    public void testCreateToy(){
        Toy toy = new Toy("Ivan","Goro");
        Assert.assertEquals("Ivan",toy.getManufacturer());
        Assert.assertEquals("Goro",toy.getToyId());
    }
   @Test(expected = IllegalArgumentException.class)
    public void testAddToOnNonExistingShelf() throws OperationNotSupportedException {
        store.addToy("H", defaultToy);
   }
   @Test(expected = IllegalArgumentException.class)
    public void testAddToyToNotEmptyShelf() throws OperationNotSupportedException {
       store.addToy("A",defaultToy);
       store.addToy("A",new Toy("Jorko","1231"));
   }
   @Test(expected = OperationNotSupportedException.class)
    public void testAddExistingToy() throws OperationNotSupportedException {
        store.addToy("A",defaultToy);
        store.addToy("C",defaultToy);
   }
   @Test
    public void testAddToy() throws OperationNotSupportedException {
       Assert.assertEquals("Toy:"+DEFAULT_TOY_ID+" placed successfully!" ,store.addToy("A",defaultToy));
       Assert.assertSame(store.getToyShelf().get("A"), defaultToy);
   }
   @Test(expected = IllegalArgumentException.class)
    public void testRemoveNonExistingToy() throws OperationNotSupportedException {
        store.addToy("A",defaultToy);
        store.removeToy("B", new Toy( "Joro", "3123"));
   }

   @Test(expected = IllegalArgumentException.class)
    public void testRemoveNonExistingToyOnThisShelf() throws OperationNotSupportedException {
        store.addToy("A",defaultToy);
        store.removeToy("A",new Toy( "Joro", "3123"));
   }
   @Test
    public void testRemoveToy() throws OperationNotSupportedException {
        store.addToy("A",defaultToy);
        Assert.assertEquals("Remove toy:"+DEFAULT_TOY_ID+" successfully!",store.removeToy("A",defaultToy));
       Assert.assertNull(store.getToyShelf().get("A"));
   }
   @Test
    public void testGetMap(){
       Map<String, Toy> map = new LinkedHashMap<>();
       map.put("A", null);
       map.put("B", null);
       map.put("C", null);
       map.put("D", null);
       map.put("E", null);
       map.put("F", null);
       map.put("G", null);
       Assert.assertEquals(map, store.getToyShelf());
   }
}