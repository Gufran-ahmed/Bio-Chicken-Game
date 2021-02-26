import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)


public class wormpart extends elevator
{

public boolean bUnconnected = false;

float ys = 0;
float xs = 0;

    public wormpart()
    {
        yOffset= 20;
    }
   
    public void act() 
    {
        if (bUnconnected) {fall();}
        playerslope();
        //naahhh
    }    //act
    
    public void fall()
    {
        setLocation(getX()+(int) xs, getY()+(int) ys);
        
        ys += 0.3;
        
      //  xs *= 0.99;
        
        if (getX() < 16  && xs < 0) {xs *= -0.7 ;}
      
      if (getX() > 640-17 && xs > 0) {xs *= -0.7;}
        
        
        if (ys > 5 ) {ys = 5;}
        if (getY() > 450) {ys = ys * -0.7f;}
    }//fall
    
    public void place(int xx, int yy)
    {
         pX = getX();// x and y before moving
        pY = getY();
        
         setLocation(xx,yy); 
         
          cX = getX()-pX;
        cY = getY()-pY; 
        
        //bugfix
        myWorld mworld = (myWorld) getWorld();
         if (mworld.isGround(getX(), getY())) //if overlaps background dont move player
         { bActive = false; 
            cX = 0;
            cY = 0;
            }
         else
         { bActive = true; }
    }//place
    
    public void playerslope()
    {
        if (!bActive) {return;} //bugfix
     player neb = (player) getOneIntersectingObject(player.class);
        if (
        neb != null 
        && neb.ys >= 0
         && neb.getY()  < (getY()- yOffset) 
        
        )
        {
            neb.canjump = true;
            //neb.y--;
            neb.y = getY() - yOffset;
                        //y = mg.getY()- mg.yOffset;
        }//if
    }//pslope
    
} //class
