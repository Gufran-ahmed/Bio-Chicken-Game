import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class fallift here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class fallift extends elevator
{


float basey;
float ys = 0;
float yy = 0;
boolean bFallDown = true;

    public fallift()
    {
    yOffset= 14;
    }
    
    public fallift(boolean falld)
    {
    yOffset= 14;
        bFallDown = falld;
    }//fallift

     public void addedToWorld(World world) {
        basey = getY();
        yy = basey;
     }//added
    
    public void act() 
    {
      //  pX = getX();
        pY = getY();
        
        if (bFallDown) {overplayer();} else {overplb();}//endif
        setLocation(getX(),(int) yy);
        
        cY = getY()-pY;
    }  //act  
//     
//    ///fortestingonly
//     public boolean isoverplayer()
//     {
//         player neb = (player)  getOneIntersectingObject(player.class);
//     if ( neb!= null) {return true;}
//     return false;
//     }    //////////////
    
    private void overplb()
    {
          player neb = (player)  getOneIntersectingObject(player.class);
           if ( neb!= null && ys > -5)
           {
             ys -= 0.08f; 
            }
             else
             {
                  if (yy < basey ) { ys+=0.1f;}
           }//endif
           
           yy += ys;
           
            if (yy > basey) {
                ys = 0; 
                yy = basey;      
            }//endif
            
            if (yy < 10) { yy = 10; ys = 0;} //endif 
    }//overplb

    private void overplayer()
    {
        player neb = (player)  getOneIntersectingObject(player.class);
           if ( neb!= null && ys < 5)
           {
             ys += 0.08f; 
              if (ys < -2) { neb.ys = -5;}
           }
           else
           {
                if (yy > basey ) { ys-=0.3f;}
            }//endif
            
            yy += ys;
            
            if (yy < basey) {
                ys = 0; 
                yy = basey;      
            }//endif
           
           if (yy > 470) { yy = 470; ys = 0;} 
           
    }//overplayer
    
}//fallift
