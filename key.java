import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class key here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class key extends Actor
{

boolean bpickedup = false;
boolean bremoved = false;
private myWorld mworld;

    public void addedToWorld(World world) {
        mworld = (myWorld) world; 
    }

    public void act() 
    {
        if (bremoved) {return;}
        findplayer();
        picked();
    }   //act 
    
    private void findplayer()
    {
        if (bpickedup) {return;} //if its already picked up then dont bother
        player neb = (player) getOneIntersectingObject(player.class);
        if (neb != null)
        {
            Greenfoot.playSound("keycollect.wav");
            bpickedup = true;
        }//endif
    }//findplayer
    
    
    private void finddoor()
    {
        NormalDoor mydoor = (NormalDoor) getOneIntersectingObject(NormalDoor.class);
        if (mydoor != null) 
        {
            mydoor.open();
            destroy();
        }//endif
    }//finddoor
    
    //key destroys any enemies when its following you
    private void findenemy()
    {
        //enemy mutantleg = (enemy) getOneObjectAtOffset(0,8,enemy.class);
       enemy mutantleg = (enemy) getOneIntersectingObject(enemy.class);
         if (mutantleg != null 
         && mutantleg.health > 1 
         && !mutantleg.bKnockedOut
         && mutantleg.canbeattacked()
         )
        {
            mutantleg.health = 1;
            mutantleg.ys = -3;
            mutantleg.knockmeout();
            Greenfoot.playSound("kick.wav");
            getWorld().addObject(new effect(), mutantleg.getX(),mutantleg.getY()-8);
        }
    }//findenemy
    
    private void destroy()
    {
        if (bremoved) {return;}
        bremoved = true;
        getWorld().removeObject(this);
    }//destroy
    
    private void picked()
    {
        if (!bpickedup) {return;}
         int px =(int) ( getX()+ ((mworld.playerx - getX()) *0.05f));
          int py = (int)( getY()+ ((mworld.playery - getY()) *0.05f));
          
          setLocation(px, py);
          
          findenemy();
          finddoor();
        
    }//picked
    
}//class
