import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class sidelift here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class sidelift extends elevator
{

private myWorld mworld;
int dir = 0;    


    public sidelift(int di)
    {
        dir = di;
        yOffset= 14;
    }

    public sidelift()
    {
        yOffset= 14;
    }
    
 public void addedToWorld(World world) {
        mworld = (myWorld) world; 
     }//added

    public void act() 
    {
        pX = getX();// x and y before moving
        pY = getY();
        
      if (dir == 0) { setLocation(getX()+1,getY());
          if (mworld.isGround(getX()+31, getY()) || mworld.isPatrol(getX()+31, getY()) ){ dir = 16; }
        }//endif
        
      if (dir == 16) { setLocation(getX()-1,getY());
             if (mworld.isGround(getX()-31, getY()) || mworld.isPatrol(getX()-31, getY())  ){ dir = 0; }
        }//endif
      
        cX = getX()-pX;
        cY = getY()-pY;
    }   //act 
}//class
