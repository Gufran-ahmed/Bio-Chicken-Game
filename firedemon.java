import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**

 */
public class firedemon extends enemy
{
 

int totalfire = 1;
fire[] childs;
boolean bFiresBeenSet = false;
boolean bFiresRemoved = false;

static final GreenfootImage hurtFrame = new GreenfootImage("firedemonhurt.gif");

    public firedemon(int total, int di)
    {
        if (total <= 0 || total > 15) {total = 2;}
        totalfire = total;
        childs = new fire[totalfire];
        health = 2;
        dir = di;
        bMeleeAttack = false;
       xoffset = 24;
       yoffset = 24;
       maxrun = 2;
    }//constructoralt

    public firedemon()
    {
        totalfire = 2;
        childs = new fire[totalfire];
        health = 2;
        bMeleeAttack = false;
       xoffset = 24;
       yoffset = 24;
       maxrun = 2;
    }//constructor

    public void act() 
    {
         if (bRemoved) {return;}
         
          setfires();
        
         
    
         
       if (health > 1) 
       { 
          ai_patrol();
       }//endif
         
         movement();
       //  deccelrate();
       
            limits();  
            delays();
        setLocation((int) x, (int) y);
           upfires();
           
        if (health == 1) 
            {
             bCanBeAttackedAtAll = false;
             
                if (!bKnockedOut) {
                    ys = -7;
                    setImage(hurtFrame);
                  }
             bKnockedOut = true;
             removefires();
             gravity(); 
            }//endif
 
        
         if (health < 0) { removefires(); removeme();}
    }//act
  //////////////////////
  
   public void knockmeout()
   {return;  } //just needed to override so key wont affect animation things
  
  private void removefires()
  {
    if (bFiresRemoved) {return;}
    bFiresRemoved = true;
    
     for (int i = 0; i<totalfire;i++)
            {
                getWorld().removeObject(childs[i]);
            }
  }//removefires end
  
  private void upfires()
  {
          for (int i = 0; i<totalfire;i++)
            {
                childs[i].basex = getX();   
                childs[i].basey = getY()-5;   
            }//next i
  }//upfiresend
  
  private void setfires()
  {
    if (bFiresBeenSet) {return;}
    bFiresBeenSet = true;
    
    //   myWorld mg = (myWorld) getWorld();
  
    int ang = 0;
       
       for (int i = 0; i<totalfire;i++)
            {
                if (childs[i] == null) {childs[i] = new fire(ang,1f,40);}
                ang += 360/totalfire;
                getWorld().addObject(childs[i],getX(),getY());
            }//next i
    }//setfires
    
    
    //////////////////
}//class
