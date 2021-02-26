import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 BOSS - KING BOAR FROG
 */
public class kingpig extends enemy
{

//int health = 2;
//int usualstun = 50; 
 
//public int xoffset = 32;
//public int yoffset = 32;
//int extrayoffset = 8;

boolean bDefeated = false;

int bosshealth = 7;

private static GreenfootImage pigframes[] = new GreenfootImage[32];

    public kingpig()
    {
        setPigframes();
        usualstun = 130;
        kickdelay = 30;
        usualkick = 60;
        attackdelay = 100;
        health = 200;
        dir = 16;
        xs = 0;
        ys = 1;
        xoffset = 32;
        yoffset = 32;
        maxaccel = 2;
        bCanBeAttackedFromBelow = false;
        bKillOnFall = true;
        
        if (mworld.bHARDMODE) {
            bosshealth = 10;
            usualstun = 70;
            maxrun = 3.75f;
        }//endif
        
    }//constructor

    public void act() 
    {
        if (bRemoved) {return;}
        
     if (!bDefeated) {  
         if (!knockedout())
        {    
         if (stunned <= 0 && kickdelay <= 0) {
              if (bCanJump && ys >= 0) {setanim_default();}
            if (!bCanJump) { ai_patrol();}
             ai_pigJump();
            }
            else
            {
             if (kickdelay <= 0) {setanim_stunned();}
         }//endifstunned
        
        }//ifknockedout
        else
        {
            setanim_kicked();
            stunned = 0;
            health = 10;
            bosshealth--;
            bKnockedOut = false;
        }//elseknockedout
        
    }//endif
    
      animate(pigframes);
    
        deccelrate();
        movement();
        gravity();
        limits();
        
      if (!bDefeated) { checkbackground(); } 
        
        delays();    
         setLocation((int) x, (int) y);
         
         if (bosshealth <= 0 && !bDefeated)
         {
            bDefeated = true;
            bMeleeAttack = false;
            bCanBeAttackedAtAll = false;
            mworld.addObject(new key(), getX(), getY());   
         }
         
        if (health < 0) { removeme();}
    }    //actend
    //////////////////////////////////////////////
    private void ai_pigJump()
    {
        if (attackdelay <= 0 && bCanJump)
        {
            attackdelay = 100;
            ys = -8;
            setanim_attack();
            bCanJump = false;
        }
    
    }//pigjump
    
    
    
    
    
    ///////////////////////////
    private void setPigframes()
    {
        if (pigframes[0] != null) {return;}
        
          pigframes[0] = new GreenfootImage("kingpigframea.gif");
          pigframes[1] = new GreenfootImage("kingpigframeb.gif");
          pigframes[2] = new GreenfootImage("kingpigframec.gif");
          pigframes[3] = new GreenfootImage("kingpigframed.gif");
          pigframes[4] = new GreenfootImage("kingpigframee.gif");
          
          pigframes[0+16] = new GreenfootImage("kingpigframea.gif");
          pigframes[1+16] = new GreenfootImage("kingpigframeb.gif");
          pigframes[2+16] = new GreenfootImage("kingpigframec.gif");
          pigframes[3+16] = new GreenfootImage("kingpigframed.gif");
          pigframes[4+16] = new GreenfootImage("kingpigframee.gif"); 
          
            for (int i=(0); i<(3+1);i++)
          {
            pigframes[i].mirrorHorizontally();
          }//next i
          
           pigframes[5] = pigframes[2];
           pigframes[5+16] = pigframes[2+16];
    }//pigframes
    
    private void setanim_default()
    {
      if (!bCanJump) {return;}
     if (animno == 0) {return;}
     animno = 0; 
     cur_frame = 0;
     anim_speed= 0.0f;
    }//void
    
    private void setanim_kicked()
    {
     if (animno == 1) {return;}
     animno = 1; 
     cur_frame = 4; 
     start_frame=5;
     end_frame= 5;
     anim_speed= 0.15f;
    }//void
    
    private void setanim_attack()
    {
     if (animno == 2) {return;}
      bMeleeAttack = true;
     animno = 2; 
     cur_frame = 1; 
     anim_speed= 0.0f;
    }//void
    
     private void setanim_stunned()
    {
     if (animno == 3) {return;}
     bMeleeAttack = false;
     animno = 3; 
     cur_frame = 3; 
     anim_speed= 0.0f;
    }//void
////////////////////////////////////////////    
}//classend
