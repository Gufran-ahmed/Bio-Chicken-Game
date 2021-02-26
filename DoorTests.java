import greenfoot.*;
/**
 * Instead of updating myWorld directly (it's a long class with many interdependent variables
 * and methods), use the methods in this class to add elements to levels.
 * 
 * @author Derek Reilly
 * @version 1.0
 */
public class DoorTests  
{

    private static final int TEST_LEVEL = -3;
    private static final int PORTAL_LEVEL = -4;
    private static final int LEVEL_ONE = 1;
    
    /**
     * This method gets called when setting up the "Test Area", a plain level for debugging
     * new game elements
     * @param world the game world, used to add game elements via addObject
     */
    public static void setUpTestLevel (World world) {
        final int TEST_RIGHT_X = 540;
        final int TEST_FLOOR_Y = 400;
        world.addObject (new NormalDoor (false,PORTAL_LEVEL), TEST_RIGHT_X, TEST_FLOOR_Y); 
        // add your own game elements here for testing and debugging
    }
    
    /**
     * This is the first level of the first game episode.
     */
    public static void setUpLevelOne (World world) {

        
         
        // uncomment this test after you have implemented and tested the TimedGate class
        Gate g1 = new Gate (1000); // 1 second cycle
        Gate g2 = new Gate (500); // 1/2 second cycle
          
        world.addObject (g1, 400, 210);
        world.addObject (g2, 500, 210);
         
    }

    /**
     * This is a portal level for testing. Right now it's connected to the test level (see above).
     */
    public static void setUpPortalLevel (World world) {
        
        // uncomment this test when you have implemented and tested the Portal class

        Portal a1 = new Portal ();
        Portal a2 = new Portal ();
        a1.connect (a2);
        world.addObject(a1, 492,317);
        world.addObject (a2, 457, 214);
        
        Portal b1 = new Portal ();
        Portal b2 = new Portal ();
        b1.connect (b2);
        world.addObject (b1, 197,422); 
        world.addObject (b2, 400,422);
        
    }
}
