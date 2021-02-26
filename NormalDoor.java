import greenfoot.*;
/**
 * Door that takes the player to the next level
 * This is an updated version of door, adapted for AbstractDoor
 * @author 
 * @version 2.0
 */
public class NormalDoor extends AbstractDoor 
{
    static GreenfootImage openImg = new GreenfootImage("dooropen.gif");
    static GreenfootImage closedImg = new GreenfootImage("doorclosed.gif");

    private int destination; // -100 means next level in game sequence
    
    /**
     * Constructor for objects of class NormalDoor
     * @param closed is the door closed on start
     * @param destination the level the door leads to, or -100 if next level in game sequence
     */
    public NormalDoor (boolean closed, int destination) {
        this(closed, destination, closedImg, openImg);
    }

    /**
     * Constructor for objects of class NormalDoor
     * The door will lead to the next level in default game sequence
     * @param closed is the door closed on start
     */
    public NormalDoor (boolean closed) {
        this(closed, -100); 
    }
    
    
    /**
     * Constructor used by subclasses to set the open and closed images
     */
    protected NormalDoor (boolean closed, int destination, GreenfootImage cl, GreenfootImage op) {
        super (closed, cl, op);
        this.destination = destination;
    }
    
    /**
     * Takes the player to the linked game level if they are at the door. 
     */
    protected void whileOpen () 
    {
        player neb = (player) getOneObjectAtOffset(0,0,player.class);
        if (neb != null) {
            if (destination == -100) {
                mworld.nextLevel(); 
            } else {
                mworld.setLevel (destination);
            }
        }
    }   
}
