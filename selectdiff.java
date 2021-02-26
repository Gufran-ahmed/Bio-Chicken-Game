import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Difficulty selector
 */
public class selectdiff extends enemy
{
 
static GreenfootImage easyimage= new GreenfootImage("selecteasy.gif");
static GreenfootImage hardimage= new GreenfootImage("selecthard.gif");  
  
    public selectdiff()
    {
        bMeleeAttack = false;
    }//const

    public void act() 
    {
        // Add your action code here.
    }    //act
    
    public void damageStun(int dmg)
     {
          myWorld mg = (myWorld) getWorld();
    
          if (mg.toggleDifficulty() ) 
          {
            setImage(hardimage);
            }
          else
          {
            setImage(easyimage);
            }//endif
          
       }//change diff
         
    
}//class
