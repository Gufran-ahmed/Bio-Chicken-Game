import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class elevator here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class elevator extends Actor
{
    /**
     * Act - do whatever the elevator wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    
public int yOffset;
    

int pX, pY;
int cX, cY;

boolean bActive = true; //if true it holds players

    public elevator()
    {
        yOffset = 25; //(int) (0.5 * getHeight() -1);//* 0.5f)-1;
        // setLocation(Greenfoot.getRandomNumber(640), Greenfoot.getRandomNumber(480));
    }
    
    public boolean isActive()
    {
        return bActive;
    }//isactiv
    
    public void act() 
    {
        pX = getX();// x and y before moving
        pY = getY();
        
      setLocation(getX(),getY()-1);
        
        //the difference of previous and current coordinates
        //useful so players and enemies can ride the elevator
        cX = getX()-pX;
        cY = getY()-pY;
        
        if (getX() > 630) {setLocation(10, getY());}
        if (getY() < 10) {setLocation(getX(), 470);}
        
        if (getY() > 475)  {setLocation(getX(), 10);}
        
    }//act    
}
