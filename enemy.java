import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

  //yea i guess i should have made a class to be
    //the superclass for both enemies and player
    // -- but the thing is i wasnt expecting to get that far
    // to have enemies at all, so whatever
    //anyway what i mean is that most of these were just copied over from player class
    
    //and then it got rewritten like twice
public class enemy extends Actor
{
////////////////////////////
public myWorld mworld;

boolean bRemoved = false;
boolean bKnockedOut = false;

int health = 2;
int dmgdelay = 0;
int kickdelay = 0;
int usualkick = 1000;

int stunned = 0;
int usualstun = 250;

float x,y, xs,ys;
boolean bCanJump = false;

static float minx = 16;
static float miny = 16;
static float maxx = 640-16;
static float maxy = 480-16;

int xoffset = 14;
int yoffset = 14;
int extrayoffset = 8;

int dir = 0;
float maxrun = 3;
float maxaccel = 0.15f;

float cur_frame=0.0f;
float anim_speed= 0.0f;
float start_frame=0.0f;
float end_frame=0.0f;
int animno = 0;
int animdelay = 0;

boolean bKillOnFall = true;

// boolean bCanBeKicked = false;
 boolean bMeleeAttack = true;
 int attackdelay = 0;
 
boolean bCanBeAttackedFromAbove = true;
boolean bCanBeAttackedFromBelow = true;
boolean bCanBeAttackedAtAll = true;
  
private static GreenfootImage frames[] = new GreenfootImage[32];

    public enemy()
    {
        setCrabframes();     
     //   setanim_default();
    }//constructor

    public void act()
    {
        if (bRemoved) {return;}
        
        movement();
        gravity();
       
        
        if (!knockedout())
        {
            
         if (stunned <= 0) {
             setanim_default();
             ai_patrol();
         }
         else
         {
             setanim_stunned();  
          }//endif stunted
          
        deccelrate(); 
        checkbackground();
        checkelevator();
    }
        else     
    {
        setRotation(getRotation()+8);
        setanim_knockedout();
    }//endif knocked
         
        limits();
        
        animate(frames);
        delays();
        
        setLocation((int) x, (int) y);
        
        if (health < 1) { knockmeout();}
        if (health < 0) { removeme();}
    }//actend
 ///////////////////////////////////////////////////////////////////////////////////////////   
       protected void addedToWorld(World world)
    {
        mworld = (myWorld) world;
         x = getX();
         y = getY();
    } //addedtoworldend
//////////////////////////////////////////////////////////////////////////////
//animation
  
   public void animate(GreenfootImage[] myframes){
    if (animdelay > 0) {return;}
    
  if (anim_speed > 0) {  
    cur_frame += anim_speed;
    if (cur_frame > end_frame) { cur_frame = start_frame;}
}

    int frame = (int) cur_frame + dir;
    
    if (myframes[frame] != null) 
        {  
            this.setImage(myframes[frame]);
        }//endif
    }//animate
    
    ///animations
    private void setanim_default()
    {
        if (knockedout()) {return;}
        if (animno == 1 ) {return;}
        animno = 1;
     anim_speed = 0.2f; cur_frame = 0;  start_frame = 0;  end_frame = 4;
    }//anim default
    
    private void setanim_stunned()
    {
        if (knockedout()) {return;}
     if (animno == 2) {return;}
     animno = 2; 
     anim_speed = 0.1f; cur_frame = 4;  start_frame = 4;  end_frame = 6;   
    }//anim stunned
    //ok i just realized if i wouldn't change cur_frame, i woudln't need animno, ohwell 
     private void setanim_knockedout()
    {
     if (animno == 3) {return;}
     animno = 3; 
     anim_speed = 0.05f; cur_frame = 6;  start_frame = 6;  end_frame = 8;   
    }//anim stunned
    
////////////////////////////////////////////////////
    public void ai_patrol()
    {
        if (dir == 0 && xs < maxrun)
        { xs += maxaccel;  }
        if (dir == 16 && xs > -maxrun)
        { xs -= maxaccel;  }
    
       if (getX() > maxx-8 ||  mworld.isGround( getX()+xoffset+2, getY() )  || mworld.isPatrol( getX()+xoffset+2, getY() ) )
        { dir = 16;}
       if (getX() < minx+8  || mworld.isGround( getX()-xoffset-2, getY() ) || mworld.isPatrol( getX()-xoffset-2, getY() ) )
        { dir = 0;}     
    }//ai patrol end
    
    public void delays()
    {
        if (dmgdelay > 0) {dmgdelay--;}
        if (stunned > 0) {stunned--;}
        if (animdelay > 0) {animdelay--;}
        if (kickdelay > 0) {kickdelay--;}
         if (attackdelay > 0) {attackdelay--;}
    }//delaysend
    
    public boolean canattack()
    {
       return (dmgdelay <= 0 && stunned <= 0 && bMeleeAttack && !knockedout() && !bRemoved );
    }//canattackend
    
    public boolean canbekicked()
    {
        return (dmgdelay <= 0  && kickdelay <= 0 && stunned > 0 && bCanJump && !knockedout() && !bRemoved);
    }//canbekickedend
    
     public boolean canbeattacked()
    {
        return (bCanBeAttackedAtAll  && dmgdelay <= 0 && !knockedout() && !bRemoved);
    }//canbeattackend
    
    
     public void damage(int dmg)
     {
           if (knockedout() || bRemoved) {return;}
           health -= dmg;
             dmgdelay = 10;
     }//damage
     
      public void damageStun(int dmg)
     {
           if (knockedout() || bRemoved) {return;}
           health -= dmg;
             dmgdelay = 10;
           stunned = usualstun;
     }//damage
    
      public void removeme()
    {
        bRemoved = true;
        getWorld().removeObject(this);
    }//killthis
    
    public void kickme()
    {
        knockmeout();
        kickdelay = usualkick;
        ys = -6;
    }//kicked
 
    public boolean attackbelow()
    {
        return bCanBeAttackedFromBelow;
    }//attackbelow
    
    public boolean attackabove()
    {
        return bCanBeAttackedFromAbove;
    }//attackabove

    
    public boolean knockedout()
    {
        return bKnockedOut;
    }//knockedoutend
    
    public void knockmeout()
    {
        if (bKnockedOut) {return;}
        bKnockedOut = true;
    }//knockmeoutend
    
    
    public void gravity()
    {
        if (ys < 5 ) {ys += 0.3;}
    }//gravityend
    
    public void deccelrate()
    {
         xs *=  0.91;
    }//deccelrate end
    
    public void movement()
    {
         x += xs;
         y += ys; 
    }//movementend
    
    public void limits()
    {
        
        if (y < miny) {y = miny; }
        if (y > maxy) {
            y = maxy; 
            if (bKillOnFall) {health = -1;}
        }//endif    
        if (x > maxx) { x = maxx; }
        if (x < minx) {x = minx;}
    }//limitsend
    
   
//     public void checkbackground()
//     {
//         checkbackground(xoffset, yoffset);
//     }
    
  public void checkbackground()
    {
         if (mworld.isJumpPad(getX(), getY()+this.yoffset) )
        { ys = -13;} //endif 
        
          if (mworld.isGround(getX(), getY()+this.yoffset+2) )
          {
              if (ys > 0){ys = 0;}
              bCanJump = true;
          }
          else
          {
               bCanJump = false;
          }//endif
        
        if (mworld.isGround(getX(), getY()+this.yoffset) )
        { y -= 1; 
          //  if (ys > 0){ys = 0;}
            
        }///endif
        
         if (mworld.isGround(getX(), getY()-this.yoffset) )
        { y += 1; 
            if (ys < 0){ys = 0;}
        }///endif
        
        if (mworld.isGround(getX()+this.xoffset, getY()) )
        { x -= 1;
          if (xs > 0) {xs = 0;}
        }////endif
        
         if (mworld.isGround(getX()-this.xoffset, getY()) )
        { x += 1;
          if (xs < 0) {xs = 0;}
        }///endif   
    }//checkbgend
    
    public void checkelevator()
    {
      elevator mg = (elevator) getOneIntersectingObject(elevator.class);
      
      if (mg!= null && !mworld.isGround(getX(), getY()-yoffset-2)) 
      {
              
        if (ys > 0 && getY()  < (mg.getY()- mg.yOffset) )
        {
            y = mg.getY()- mg.yOffset - extrayoffset;
            x = x + mg.cX;
            y = y + mg.cY;
            ys = 0;
            bCanJump = true;
        }//ifys
        
        }//ifmgnull

    }//checkelevatorend
    
    private void setCrabframes()
    {
         if (frames[0] == null) { //so maybe we dont reload images every time we make an enemy
          //crab - abcd:walking ef:stunned gh:knockedout
          frames[0] = new GreenfootImage("crabframea.gif");
          frames[1] = new GreenfootImage("crabframeb.gif");
          frames[2] = new GreenfootImage("crabframec.gif");
          frames[3] = new GreenfootImage("crabframed.gif");
          frames[4] = new GreenfootImage("crabframee.gif");
          frames[5] = new GreenfootImage("crabframef.gif");
          frames[6] = new GreenfootImage("crabframeg.gif");
          frames[7] = new GreenfootImage("crabframeh.gif");
          
          frames[0+16] = new GreenfootImage("crabframea.gif");
          frames[1+16] = new GreenfootImage("crabframeb.gif");
          frames[2+16] = new GreenfootImage("crabframec.gif");
          frames[3+16] = new GreenfootImage("crabframed.gif");
          frames[4+16] = new GreenfootImage("crabframee.gif");
          frames[5+16] = new GreenfootImage("crabframef.gif");
          frames[6+16] = new GreenfootImage("crabframeg.gif");
          frames[7+16] = new GreenfootImage("crabframeh.gif");
          
          for (int i=(0+16); i<(5+17);i++)
          {
            frames[i].mirrorHorizontally();
            }//next i
            }//endif
    }////Crabframes
    
    
//      public void checkbackground(int yoff, int xoff)
//     {
//          if (mworld.isJumpPad(getX(), getY()+yoff) )
//         { ys = -13;} //endif 
//         
//           if (mworld.isGround(getX(), getY()+yoff+2) )
//           {
//               if (ys > 0){ys = 0;}
//               bCanJump = true;
//             }//endif
//         
//         if (mworld.isGround(getX(), getY()+yoff) )
//         { y -= 1; 
//           //  if (ys > 0){ys = 0;}
//             
//         }///endif
//         
//          if (mworld.isGround(getX(), getY()-yoff) )
//         { y += 1; 
//             if (ys < 0){ys = 0;}
//         }///endif
//         
//         if (mworld.isGround(getX()+xoff, getY()) )
//         { x -= 1;
//           if (xs > 0) {xs = 0;}
//         }////endif
//         
//          if (mworld.isGround(getX()-xoff, getY()) )
//         { x += 1;
//           if (xs < 0) {xs = 0;}
//         }///endif   
//     }//checkbgend
    
}//classend
//////////////////////////////////////////////////////////////////////EEEEND
