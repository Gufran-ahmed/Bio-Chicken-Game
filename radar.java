import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class radar here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class radar extends enemy
{


static GreenfootImage framea = new GreenfootImage("radarframea.gif");
static GreenfootImage frameb = new GreenfootImage("radarframeb.gif");
static GreenfootImage framec = new GreenfootImage("radarframec.gif");

int chngframe = 0;

    public radar()
    {
        bCanJump = true; //yea its on the ground
        bMeleeAttack = false; //nah dont hurten poor neb
        bKillOnFall = true;   //delete when its fallen down
        stunned = 99; //so its auto kicked
        
        //only kick pls
         bCanBeAttackedFromAbove = false;
         bCanBeAttackedFromBelow = false;
    }//constr

    public void act() 
    {
       if (bRemoved) {return;}
        
          if (!knockedout())
        {
            chngframe++;
            if (chngframe == 10) {setImage(framea);}
            if (chngframe > 20) {
                chngframe = 0;
                setImage(frameb);
            }//endif
            
        }
        else
        {
            setImage(framec);
            setRotation(getRotation() +(int) xs);
            movement();
            gravity();
        }//endif
          
        limits();
       
        setLocation((int) x, (int) y);
        
        if (health < 0) { removeme();} 
    }    //act
    
    
    
    
    
    
    
    
    
    
    
    
    
    ////////////////////////
}//classend





//// OH NO ITS THE END OF THE CLASS :O
