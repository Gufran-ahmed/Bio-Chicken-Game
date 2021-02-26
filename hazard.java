import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.lang.Math;

/**
 * Hazards are just things you cannot destroy
 * but they move around and trying to hurt you
 * but just patterns, no intelligence involved
 */
public class hazard extends Actor
{

public myWorld mworld;

private int dir = 0;
private int basey = 0;
private float angle = 0;
private int yy = 0;

private int dist = 64;
private int speed = 3;
private float freq = 0.1f;

int miny = 0+8;
int maxy = 480-8;
int minx = 0+8;
int maxx = 640-8;

 public hazard()
 {
    
  }//altconstructor

    public hazard(int dis, int direction, int spe, float fre)
    {
        dist = dis;
        dir = direction;
        speed = spe;
        freq = fre;
    }//constructor

    protected void addedToWorld(World world)
    {
        mworld = (myWorld) world;
         basey = getY();
         yy = getY();
    } //addedtoworldend

    public void act() 
    {
        huntplayer();
        
        angle +=freq;
        if (angle > 359) {angle = 0;}
        yy = (int) (basey + Math.sin(Math.toRadians(angle))*dist);
      //(180/3.1415f)
        
        if (dir == 0) {
            setLocation(getX()+speed,yy);
            if (getX() > maxx) {dir = 16;}
        }//dir0
        
        if (dir == 16) {
            setLocation(getX()-speed,yy);
            if (getX() < minx) {dir = 0;}
        }//dir0
    }    //act
      
      public void huntplayer()
    {

        player neb = (player) getOneIntersectingObject(player.class);
        if (neb != null)
        {
            neb.hurtplayer(); 
        }//endif
    }//huntplayer end
    
}//class
