package football;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FootballTeamTests {
    private static final String FOOTBALLER_NAME = "Malkoch";
    private static final String TEAM_NAME = "Malkochi";
    private static final int VACANT_POSITIONS = 12;
    private Footballer footballer;
    private  FootballTeam footballTeam;

   @Before
    public void setUp(){
       this.footballer = new Footballer(FOOTBALLER_NAME);
       this.footballTeam = new FootballTeam(TEAM_NAME, VACANT_POSITIONS);
   }

   @Test(expected =  IllegalArgumentException.class)
    public void testCreatingNewFootballTeamWithNoVacancyShouldThrowEx(){
       footballTeam = new FootballTeam(TEAM_NAME,-1);
   }
   @Test(expected = NullPointerException.class)
    public void testCreatingNewFootballTeamWithEmptyNameShouldThrowEx(){
       footballTeam = new FootballTeam("         ",VACANT_POSITIONS);
   }

   @Test
    public void testAddFootballerToTeamWithAvailablePositionsShouldIncreaseSize(){
       footballTeam.addFootballer(footballer);
       Assert.assertEquals(1, footballTeam.getCount());
   }
   @Test(expected = IllegalArgumentException.class)
    public void testAddFootballerToFullTeamShouldThrowEx(){
       footballTeam = new FootballTeam(TEAM_NAME,0);
       footballTeam.addFootballer(footballer);
   }
   @Test
    public void testRemoveFootballerShouldDecreaseSize(){
       footballTeam.addFootballer(footballer);
       Assert.assertEquals(1, footballTeam.getCount());
       footballTeam.removeFootballer(footballer.getName());
       Assert.assertEquals(0, footballTeam.getCount());
   }
   @Test(expected = IllegalArgumentException.class)
    public void testRemoveNonExistentPlayerShouldThrowEx(){
       footballTeam.addFootballer(footballer);
       footballTeam.removeFootballer("Peev");
   }
   @Test
    public void testFootballerForSaleShouldBeInactive(){
       footballTeam.addFootballer(footballer);
       footballTeam.footballerForSale(footballer.getName());
       Assert.assertFalse(footballer.isActive());
   }
   @Test(expected = IllegalArgumentException.class)
    public void testIfFootballerForSaleIsNonExistentShouldThrowEx(){
       footballTeam.addFootballer(footballer);
       footballTeam.footballerForSale("Peev");
   }
}
