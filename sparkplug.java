import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class sparkplug here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class sparkplug extends Actor
{
    
int totalspark = 7;
fire[] sparks;

boolean bSparksSet = false;

float angle = 90;
//float dist = 16;
float maxdist = 24;

float dangle = 180; //distance angle
//boolean bPulse = true;

float dangledelta = 1;
float angledelta = 0;

    public sparkplug()
    {  
    }//sparkplug
    
     public sparkplug(float ang)
    {  
        angle = ang;
    }//sparkplug
    
    public sparkplug(int total, float maxdis, float ang, float andelt, float ddelt)
    {
    totalspark = total;
    maxdist = maxdis;
    angle = ang;
    //bPulse = puls;
    angledelta = andelt;
    dangledelta = ddelt;
    }//const2

    ///////////////////////////////
    public void act() 
    {
         setSparks();
         updateSparks();
         
        angle+= angledelta;
         if (angle > 360) {angle = 0;}
         if (angle < 0) {angle = 360;}
         
     
           dangle+= dangledelta;
         if (dangle > 270) {dangle = 90;}
         if (dangle < 90) {dangle = 270;}
       
    }//act   
    
    //////////////////////////////
    
    private void updateSparks()
    {
           if (!bSparksSet) {return;}
        ///ok actually it makes a sort of line between start and destpos
        float sxpos = getX(); //startxpos
        float sypos = getY(); //startypos
        
        float dist = (float)  (Math.cos( Math.toRadians(dangle) )*maxdist ); 
        
        float goalx =(float) (Math.cos( Math.toRadians(angle) )*(dist*totalspark) +sxpos);
        float goaly =(float) (Math.sin( Math.toRadians(angle) )*(dist*totalspark)+sypos);
   
        float stepx = (goalx-sxpos)*(1f/(float) totalspark);
        float stepy = (goaly-sypos) *(1f/(float) totalspark);
        
         for (int i = 0; i<totalspark; i++)
        {
            sxpos += stepx;
            sypos += stepy;
            
            sparks[i].basex =(int) sxpos;
            sparks[i].basey =(int) sypos;
            
           // sparks[i].dist = 40;
           // sparks[i].angle = angle;
            
            sparks[i].setRotation(sparks[i].getRotation()-(Greenfoot.getRandomNumber(6)));
            
        }//next i
    }//upsparks
    
    
    private void setSparks()
    {
        if (bSparksSet) {return;}
        bSparksSet = true;
        
        sparks = new fire[totalspark];
        
          for (int i = 0; i<totalspark;i++)
            {
             //  if (sparks[i] == null) {
                   sparks[i] = new fire(true);
                   getWorld().addObject(sparks[i],getX(),getY());
               // } //endif
               
            }//next i
    }//sparksset
    
/////////////////////////////////
}//class
