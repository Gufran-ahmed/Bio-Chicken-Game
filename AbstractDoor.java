import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Base class for all types of door in the game. 
 * 
 * @author
 * @version 1.0
 */
public abstract class AbstractDoor extends Actor
{
  

    private boolean closed = false; // is the door in closed state

    private GreenfootImage imgClosed; // image to display when closed
    private GreenfootImage imgOpened; // image to display when open

    protected myWorld mworld; // reference to the world the door is a part of
 
    /**
     * Creates a door object
     * @param clo whether the door is in closed state to start
     * @param closedImg the image shown when the door is closed
     * @param openImg the image shown when the door is open
     */
    public AbstractDoor(boolean clo, GreenfootImage closedImg, GreenfootImage openImg)
    {
        imgClosed = closedImg;
        imgOpened = openImg;
        if (clo) {
            close();
        } else {
            open();
        }
    }
    
    /**
     * This implementation of Actor.act is intended for all subclasses of AbstractDoor. 
     * Subclasses override onOpen and/or onClose as needed to add specific functionality.
     * This method is declared final to prevent overriding.
     */
    final public void act () {
        if (closed) whileClosed ();
        else whileOpen ();
    }
    
    /**
     * Door behaviour when in "open" state is defined in this method. The default implmentation
     * does nothing.
     */
    protected void whileOpen () { }
    
    /**
     * Door behaviour when in "closed" state is defined in this method. The default implmentation
     * does nothing.
     */    
    protected void whileClosed () { }
    
    /**
     * "Opens" this door.
     */
    public void open  () {
        closed = false;
        setImage(imgOpened);
    }
    
    /**
     * "Closes" this door.
     */
    public void close() {
        closed = true;
        setImage(imgClosed);
    }
    
    /**
     * Called when the door is added to the  active world.
     * @param world the active world
     */
    public void addedToWorld(World world) {
        mworld = (myWorld) world; 
    }
    
    /**
     * Indicates whether or not the door is closed
     * @return true if closed, false if open
     */
    public boolean isClosed () {
        return closed;
    }
}
