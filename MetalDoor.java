import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A specialized normal door made of metal.  
 * Metal doors open when all "radars" in a level have been destroyed.
 * @author
 * @version 2.0
 */
public class MetalDoor extends NormalDoor {
    static GreenfootImage openImg = new GreenfootImage("doormetalopen.gif");
    static GreenfootImage closedImg = new GreenfootImage("doormetalclosed.gif");
    
    /**
     * Constructor for objects of class MetalDoor
     * @param closed is the door closed on start
     * @param destination the level the door leads to, or -100 if next level in game sequence
     */
    public MetalDoor (boolean closed, int destination) {
        super (closed, destination, closedImg, openImg);
    }

    /**
     * Constructor for objects of class MetalDoor
     * The door will lead to the next level in default game sequence
     * @param closed is the door closed on start
     */
    public MetalDoor (boolean closed) {
        this (closed, -100);
    }

    /**
     * Metal doors open when all the "radars" in the level have been destroyed
     */
    public void whileClosed () {
        if (mworld.radarsDestroyed()) {
             open();
        }
    }
}
