import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Gates here.
 * 
 * @author (Gufran Ahmed) 
 * @version (a version number or a date)
 */

public class Gate extends AbstractDoor
{
    static GreenfootImage openImg = new GreenfootImage("gateopen.png");
    static GreenfootImage closeImg = new GreenfootImage("gateclosed.png");
    protected int cycle_duration; 
    protected long lastTime;

    /**
     * Gate Constructor
     *
     * @param cycle_duration A parameter
     */
    public Gate(int cycle_duration){
        this(false,closeImg,openImg);
        this.cycle_duration = cycle_duration;
        this.lastTime = System.currentTimeMillis();

    }

    /**
     * Gate Constructor used by subclasses to set the open and closed images
     *
     * @param open A parameter is the door open on start
     */
    public Gate(boolean open,GreenfootImage cl, GreenfootImage op){
        super(open, cl, op);

    }
    
    /**
     * Method whileClosed, opens gate when condition is met
     *
     */
    public void whileClosed(){
        //if player is on the gate move the player by 30 left or right 
        if(this.isTouching(player.class)){
            player p= ((player) getOneIntersectingObject(player.class));
            if (p.getX() > this.getX()){
                p.setLocation(this.getX()+30, this.getY());
            }
            if(p.getX() < this.getX()){
                p.setLocation(this.getX()-30, this.getY());
            }
        }
        if(System.currentTimeMillis() - lastTime >= cycle_duration){

            this.lastTime = System.currentTimeMillis();
            this.open();
        }

    }

    /**
     * Method whileOpen closes gate when condition is met
     *
     */
    public void whileOpen(){
        if(System.currentTimeMillis() - lastTime >= cycle_duration){

            this.lastTime = System.currentTimeMillis();
            this.close();
        }
    }
}
