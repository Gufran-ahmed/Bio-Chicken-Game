import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Portal here.
 * 
 * @author (Gufran Ahmed) 
 * @version (a version number or a date)
 */
public class Portal extends AbstractDoor
{
    protected Portal connected;
    static GreenfootImage openImg = new GreenfootImage("portalopen.png");
    static GreenfootImage closeImg = new GreenfootImage("portalclosed.png");

    /**
     * no parameter Portal Constructor
     *
     */

    public Portal(){
        this(false,openImg ,closeImg);
    }

    /**
     * Portal Constructor used by subclasses to set the open and closed images
     * @param closed A parameter is the door closed on start
     * @param cl A parameter
     * @param op A parameter
     */
    public Portal(boolean closed,GreenfootImage cl,GreenfootImage op){
        super(closed,cl,op);

    }
    
    /**
     * Method connect connects the two portals for teleporting
     *
     * @param p A parameter
     */
    public void connect(Portal p){
        this.connected = p;
        p.connected = this;
    }

    /**
     * Method whileOpen open when player touches portal and gives the directionality of the player after teleporting
     *
     */
    public void whileOpen(){
        //player touching object
        if (isTouching(player.class)){
            //if player is touching object do a function
            player bob = ((player)getOneIntersectingObject(player.class));
            //teleports and moves player to the right by 40
            if(bob.getX() <= this.getX()){
                bob.setLocation(connected.getX() + 40, connected.getY());
            }
            //teleports and moves player to the left by 40
            else if(bob.getX() >= this.getX()){
                bob.setLocation(connected.getX() - 40, connected.getY());
            }
        }
    }
}  