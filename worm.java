import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.lang.Math;
/**
 * BOSS - CAVE WORM
 */
public class worm extends enemy
{
////////////////////////////////////
boolean bDefeated = false;

static int totalparts = 30;
wormpart[] parts = new wormpart[totalparts];

boolean bPartsbeenset = false;

int goalx;
int goaly;
boolean bGoalset = false;

int bosshealth = 6;
float parthurt = -1;

//accelration
float xa=0;
float ya=0;

//////////

static GreenfootImage iWormHeadNormal = new GreenfootImage("wormhead.gif");
static GreenfootImage iWormHeadHurt = new GreenfootImage("wormheadhurt.gif");

static GreenfootImage iWormPartNormal = new GreenfootImage("wormpart.gif");
static GreenfootImage iWormPartHurt = new GreenfootImage("wormparthurt.gif");



//////////////////////////////////
    public worm()
    {
     bCanBeAttackedFromBelow = false;
      bKillOnFall = false;
     usualstun = 40;
     health = 400;
      maxrun = 3;
      
      if (mworld.bHARDMODE) {
        bosshealth = 8;
        totalparts = 15;
        parts = new wormpart[totalparts];

        }//hrd
     
    }//constructor

     protected void addedToWorld(World world)
    {
        x = getX();
        y = getY();
    }//added

  ////////////////////////////////////
    public void act() 
    {
         if (bRemoved) {return;}
       
          setparts();
          partsanim();
         if (!bDefeated) {
             setgoal();
                if (stunned <= 0) {followgoal();}
                if (dmgdelay <= 0) {setImage(iWormHeadNormal);}
                move();
             partsfollow();
             hasreachedgoal();
            }
             else
            {
               gravity();
                movement();
              
            }//endif
            
            
        delays();
        limits();
        setLocation((int) x, (int) y);
         
            
         if (bosshealth <= 0 && !bDefeated)
         {
            bDefeated = true;
            bMeleeAttack = false;
            bCanBeAttackedAtAll = false;
            bKillOnFall = true;
              partsfall();
            getWorld().addObject(new key(), 320, 240);   
        }//defeat
        
          if (health < 0) { removeme();}
    }  //act  
 /////////////////
      public void removeme()
    {
        bRemoved = true;
        
        for (int i = 0; i < totalparts;i++)
        {
            
           getWorld().addObject(new effect(), parts[i].getX(),parts[i].getY());
            getWorld().removeObject(parts[i]);
        }//next i
        
    getWorld().addObject(new effect(),getX(),getY());
        getWorld().removeObject(this);
    }//killthis
    
    //////////
      public void damageStun(int dmg)
     {
       if (dmgdelay <= 0) {
    //  ys = 3;   
     // dmgdelay = 10;
      stunned = usualstun;
        }
     }//damstunoverload
    
   /////////////////////////////////////////////
    private void move()
    {
      
        if (stunned <= 0) {
        xs += xa;
        if (xs> maxrun) {xs = maxrun;}
        if (xs<-maxrun) {xs = -maxrun;}
        
        ys += ya;
        if (ys > maxrun) {ys = maxrun;}
        if (ys < -maxrun) {ys = -maxrun;}
    } else {
    if (ys < 6) {ys+= 0.4;}
         }
        
         if (y > 440 && ys > 0 && dmgdelay <=0) 
         {
             ys = -16; 
            stunned = 40;
            dmgdelay = 50;
            Greenfoot.playSound("kick.wav");
            setImage(iWormHeadHurt);
            health = 100;
            bosshealth--;
            parthurt = 0;
        }
         
        x += xs;
        y += ys;
    }//move

    private void followgoal()
    {
        int x = goalx - getX();
        int y = goaly - getY();
        float length =(float) Math.sqrt((x*x)+(y*y));

        xa = x / length;
        ya = y / length;
    }//

    private void hasreachedgoal()
    {
        if (getX() > goalx+12 || getX() < goalx-12) {return;}
        
        if (getY() > goaly+12 || getY() < goaly-12) {return;}
        
        bGoalset = false;
    }//reachedgoal
    
    private void setgoal()
    {
        if (bGoalset) {return;}
        bGoalset = true;
        goalx = goalx + Greenfoot.getRandomNumber(380)+130;
        if (goalx > 600) {goalx = 70;}
        goaly = Greenfoot.getRandomNumber(200)+110;
    }//setgoal
    
    private void partsanim()
    {
        if (parthurt < 0) {return;}
        parthurt++;
        if (parthurt > 20) {
             for (int i = 0; i < totalparts;i++)
        {
            parts[i].setImage(iWormPartNormal);
        }//nexti
            parthurt = -1;}
            else{
      
            for (int i = 0; i < totalparts;i++)
        {
             if (Greenfoot.getRandomNumber(8) == 1) {
                 parts[i].setImage(iWormPartNormal);
                }else
                {
                 parts[i].setImage(iWormPartHurt);}//endif
        }//nexti
    }//else
    }//partsanim
    
    private void partsfall()
    {
        for (int i = 0; i < totalparts;i++)
        {
           // parts[i].setLocation(parts[i].getX()+parts[i].cX,getY());
            parts[i].bActive = false; //minor bugfix  -- turn off being elevator after worm defeated
            parts[i].bUnconnected = true;
            parts[i].ys = (Greenfoot.getRandomNumber(10)+4)*-1;
            parts[i].xs = Greenfoot.getRandomNumber(10)-Greenfoot.getRandomNumber(10);
        }//next i
    }//void

    private void partsfollow()
    {
        int x = 10;
        int y = 10;
        x =(int) (parts[0].getX()+ (getX() - parts[0].getX())*0.2f);
        y =(int) (parts[0].getY()+ (getY() - parts[0].getY())*0.2f);
       
        y++;
//         parts[0].setpx();
//         parts[0].setLocation(x,y); 
//         parts[0].setcx();
    parts[0].place(x,y);

       for (int i = 1; i < totalparts;i++)
       {
         x =(int) (parts[i].getX()+ (parts[i-1].getX() - parts[i].getX())*0.2f);
         y =(int) (parts[i].getY()+ (parts[i-1].getY() - parts[i].getY())*0.2f);
      
            y++;    
//         parts[i].setLocation(x,y); 
            parts[i].place(x,y);
       }//next i
       
    }//endofvoid

    private void setparts()
    {
        if (bPartsbeenset) {return;}
        bPartsbeenset = true;
        
      myWorld mg = (myWorld) getWorld();
      

       //int tive = 0;
       
            for (int i = 0; i<totalparts;i++)
            {
                 parts[i] = new wormpart();
                 mg.addObject(parts[i],getX(),getY());
//                  parts[i].bActive = false;
//                  tive++;
//                  if (tive >2) {tive =0; parts[i].bActive = true;}
                 // parts[i].setImage(wormpart_image);
            }//next i
        
        
   }//setparts 
 ////////////////////////////////
}//class
