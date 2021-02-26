import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class player here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class player extends Actor
{

float x = 160;
float y = 100;
float xs;
float ys;

float maxspeed = 3;

boolean canjump = false;

static GreenfootImage[] frames = new GreenfootImage[32];
//static GreenfootImage[] mframes = new GreenfootImage[9]; //mirrored frames
float cur_frame=0;
float anim_speed= 0.1f; //f needed or java thinks its a double
float start_frame=0;
float end_frame=0;
int animno = 0;
int dir = 0;
int animdelay = 0;
int hurtdelay = 0;

boolean dead = false;
int health = 3;

private myWorld mworld;

    public player(){
     if (frames[0] == null){   //just in case
        //loading up frames
        frames[0] = new GreenfootImage("nebframea.gif");
        frames[1] = new GreenfootImage("nebframea.gif");
        frames[2] = new GreenfootImage("nebframed.gif");
        frames[3] = new GreenfootImage("nebframec.gif");
        frames[4] = new GreenfootImage("nebframeb.gif");
        frames[5] = new GreenfootImage("nebframee.gif");
        frames[6] = new GreenfootImage("nebframef.gif");
        frames[7] = new GreenfootImage("nebframeg.gif");
        frames[8] = new GreenfootImage("nebframeh.gif");
        frames[9] = new GreenfootImage("nebframei.gif");
        
        //mirrored frames //just add 16
        frames[0+16] = new GreenfootImage("nebframea.gif");
        frames[1+16] = new GreenfootImage("nebframea.gif");
        frames[2+16] = new GreenfootImage("nebframed.gif");
        frames[3+16] = new GreenfootImage("nebframec.gif");
        frames[4+16] = new GreenfootImage("nebframeb.gif");
        frames[5+16] = new GreenfootImage("nebframee.gif");
        frames[6+16] = new GreenfootImage("nebframef.gif");
        frames[7+16] = new GreenfootImage("nebframeg.gif");
        frames[8+16] = new GreenfootImage("nebframeh.gif");
        frames[9+16] = new GreenfootImage("nebframei.gif");
        
        for (int i = 16; i<9+17;i++)
        {
        frames[i].mirrorHorizontally();
        } //next i
             
        setanim_stand();
    }//endif
    }//constructor
    
    
    private void animate(){
        if (animdelay > 0) {animdelay--;}
        if (hurtdelay > 0) {hurtdelay--;} //yea, this codes getting messy
        
    cur_frame += anim_speed;
    if (cur_frame > end_frame) { cur_frame = start_frame;}
    
    this.setImage(frames[(int)cur_frame + dir]);
    }//animate

    
    
    //animations
    private void setanim_stand()
    {
        if (animno == 0 || canjump == false || animdelay > 0) {return;}
        animno = 0;
     anim_speed = 0; cur_frame = 0;  start_frame = 0;  end_frame = 0;
    }//stand
    
    private void setanim_run()
    {
        if (animno == 1 || canjump == false || animdelay > 0) {return;}
        animno = 1;
     anim_speed = 0.2f; cur_frame = 1;  start_frame = 1;  end_frame = 5;
    }//run
    
    private void setanim_brake()
    {
     if (animno == 3 || canjump == false || animdelay > 0) {return;}
        animno = 3;
     anim_speed = 0; cur_frame = 5;  start_frame = 5;  end_frame = 5;
    }
    
     private void setanim_jump()
    {
        if (animno == 2 || animdelay > 0) {return;}
        animno = 2;
     anim_speed = 0; cur_frame = 2;  start_frame = 2;  end_frame = 2;
    }//jump
    
    private void setanim_fall()
    {
         if (animno == 5 || canjump == true || animdelay > 0) {return;}
                 animno = 5;
     anim_speed = 0; cur_frame = 5;  start_frame = 5;  end_frame = 5;
    }//fall
    
     private void setanim_kick()
    {
        if (animno == 4) {return;}
        animno = 4;
     anim_speed = 0.5f; cur_frame = 8;  start_frame = 9;  end_frame = 9;
    }//jump
    
       private void setanim_hurt()
    {
         if (animno == 6) {return;}
         animno = 6;
     anim_speed = 0.4f; cur_frame = 6;  start_frame = 6;  end_frame = 8;
    }//fall
    
    
    /**
     * Act - do whatever the player wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        if (health <= 0)
        {
            dead = true;
            Greenfoot.playSound("nebhurt.wav");
            getWorld().addObject(new defeat(), getX(),getY());
              getWorld().removeObject(this);
        }//health ran out
        if (dead) {return;}
        
        //umm .. dont uncomment this unless you want this thing to crash
//         if (Greenfoot.isKeyDown("space"))
//         {  this.setImage((GreenfootImage)null); }
        
        //set animation
        animate();
        
        if (Math.abs(xs) < 0.3) {setanim_stand();}
              
        if ( !Greenfoot.isKeyDown("left") && 
        !Greenfoot.isKeyDown("right") &&  
        Math.abs(xs) > 0.3  )
        {setanim_brake();}
        //controls
        //run
        if (Greenfoot.isKeyDown("left") && xs > -maxspeed )
        {            xs -= 0.75; setanim_run(); 
                if (animdelay <= 0 ) {dir = 16;} 
            }//endif
            
        if (Greenfoot.isKeyDown("right") && xs < maxspeed )
        {            xs += 0.75; setanim_run(); 
            if (animdelay <= 0 ) {dir = 0;}
        }//endif
      //jump
        if (Greenfoot.isKeyDown("up") && canjump)//&& ys > -4 )
        {            ys = -6; canjump = false;  setanim_jump();      }
        
        //movement physics etc
        x += xs;
        y += ys;
        
       // if (x < 16) {x = 16;}
       // if (x > myWorld.swidth) {x =  myWorld.swidth;}
        
        xs *=  0.91;
        
        if (ys > 2 ) {setanim_fall(); canjump = false;}
        if (ys < 5 ) {ys += 0.3;}
        
        if (y > 460)
        {
            x = mworld.startx;
            y = mworld.starty;
            //   y = 460; ys = 0; canjump = true;
        }//endif
        
        if (y < 16) {y = 16;}
        
         if (x > (myWorld.swidth-16)) { x = (myWorld.swidth-16); }
        if (x < 0) {x = 0;}
        
        
        
        
        findElevator();
        
        experiment();
        
        attack();
        
      
        
        super.setLocation((int)x,(int)y-8);
        mworld.setPlayerCoords(getX(),getY() );
       
          gethurt();
       
         //myWorld.xo = (int) x-320;
     //  if (getX() > 400) {myWorld.scrollWorld(2,0);}
        
    }//act
 ///////////////////////////////////////   
    
    public void setLocation (int px, int py) {
        super.setLocation (px, py);
        x = this.getX (); y = this.getY () + 8;
        if (mworld != null) {
            mworld.setPlayerCoords(this.getX(),this.getY() );
        }
    }
    
    
    //need to do this or i got all kind of funny errors
     public void addedToWorld(World world) {
        mworld = (myWorld) world;  
        
         x = getX();
        y = getY();
    }//addedtoworld 
    
  /////////////////////////////////////////////
  
  
    public void hurtplayer()
    {
        if (hurtdelay > 0 && animdelay > 0 ) {return;}
         
             ys = 0;
             setanim_hurt();
             hurtdelay = 60;
             animdelay = 30;
             health--;
               getWorld().addObject(new effect(), getX(),getY());
             Greenfoot.playSound("shock.wav");
    }//hurtplayer
  
  
  
  
  
  
    private void gethurt()
    {
          ///ok so you can only get hurt if
    //you overlap the enemy/hazard , not kicking any stunned foe,
    //and didnt got hurt in the past 60 frames
    //and also if the enemy is not stunned, and can be damaged by you as well
    //so umm its kind of complicated
     //enemy mutantleg = (enemy) getOneObjectAtOffset(0,0,enemy.class);
  
     enemy mutantleg = (enemy) getOneIntersectingObject(enemy.class);
     
     if (mutantleg != null
     //&& mutantleg.stunned <= 0
     //&& !mutantleg.bKnockedOut
     && hurtdelay <= 0
     //&& mutantleg.dmgdelay <= 0
     && animdelay <= 0 
     && mutantleg.canattack()
     && ys > -2
      && getY() >= mutantleg.getY()
     )
     
     { 
            ys = 0;
             setanim_hurt();
             hurtdelay = 60;
             animdelay = 30;
              health--;
              getWorld().addObject(new effect(), getX(),getY());
               Greenfoot.playSound("shock.wav");
        }//endif
        
      if (mutantleg != null  
          && hurtdelay <= 0
            && animdelay <= 0   
            && mutantleg.canattack()
            && !mutantleg.attackbelow()
               && ys < 0
               && getY() >= mutantleg.getY()
          )
          {
             ys = 0;
             setanim_hurt();
             hurtdelay = 60;
             animdelay = 30;
              health--;
              getWorld().addObject(new effect(), getX(),getY());
               Greenfoot.playSound("shock.wav");
          }//hurtb
   
     if (mworld.isHurting(getX(),getY()) && hurtdelay <= 0 && animdelay <= 0)
     {
         setanim_hurt();
         getWorld().addObject(new effect(), getX(),getY());
             hurtdelay = 60;
             animdelay = 30;
              health--;
               Greenfoot.playSound("shock.wav");
        }
    }//gethurt////////
    
    private void attack()
    {
        if (hurtdelay > 5) {return;}
        
        //ok hotshot, you need to be above the enemy and falling down
       // enemy mutantleg = (enemy) getOneObjectAtOffset(0,24,enemy.class);
       enemy mutantleg = (enemy) getOneIntersectingObject(enemy.class);
       
       if (mutantleg != null
       && ys > 0
       && mutantleg.canbeattacked()
        && mutantleg.attackabove()
       && getY() < mutantleg.getY()
       && !mutantleg.knockedout()
        )
        {
            ys = -4;
            mutantleg.ys = 0;
            hurtdelay = 17;
            mutantleg.damageStun(1);
            getWorld().addObject(new effect(), getX(),getY()+16);
            Greenfoot.playSound("knock.wav");
        }
        
        //if you get under an enemy you can also take it out
         //mutantleg = (enemy) getOneObjectAtOffset(0,-24,enemy.class);
         mutantleg = (enemy) getOneIntersectingObject(enemy.class);
        if (mutantleg != null
        && ys < 0 
        && getY() > mutantleg.getY()
       && mutantleg.canbeattacked()
       && mutantleg.attackbelow()
       && !mutantleg.bKnockedOut
        )
        {
            ys = 1;
            hurtdelay = 17;
            mutantleg.ys = -5;
            mutantleg.damageStun(1);
            getWorld().addObject(new effect(), getX(),getY()-16);
             Greenfoot.playSound("knock.wav");
        }
        
        //overlap enemy when its stunned to kick it down
        mutantleg = (enemy) getOneIntersectingObject(enemy.class);
         if (mutantleg != null 
         && canjump
         && mutantleg.canbekicked()
         )

        {
            animdelay = 30;
            setanim_kick();
         //   mutantleg.health = 1;
         hurtdelay = 17;
       
         mutantleg.xs = this.xs *1.3f;
          //  mutantleg.ys = -6;
            mutantleg.kickme(); 
            getWorld().addObject(new effect(), mutantleg.getX(),getY());
             Greenfoot.playSound("kick.wav");
          //  ys = -2;
        }
    }//attack
 ///////////////////////////////////////   
 
   private void experiment()
    {
        
         if (mworld.isJumpPad(getX(), getY()+14) )
        { ys = -13;}///
        //im the most surprised that this one actually worked :O
       // Color ground = mworld.getColorAt(getX(), getY()+16);
        if (mworld.isGround(getX(), getY()+16))//ground.equals(Color.BLACK))
        { ys = 0; canjump = true; }//x =320; y = 240;}
            //ys = 0; y -= 1; }
        //myWorld.paintBg(getX(), getY());
        if (mworld.isGround(getX(), getY()+14) )
        { y -= 1;}///
        
//         //fix slopes/// not works yet
//         if (!mworld.isGround(getX(), getY()+10) && mworld.isGround(getX(), getY()+9))
//         {y += 1;}
        
         if (mworld.isGround(getX(), getY()-14) )
        { y += 1; 
            if (ys < 0){ys = 0;}
        }///
        
        if (mworld.isGround(getX()+14, getY()) )
        { x -= 1;
          if (xs > 0) {xs = 0;}
        }////
        
         if (mworld.isGround(getX()-14, getY()) )
        { x += 1;
          if (xs < 0) {xs = 0;}
        }///
    
    }//experiment
    
    private void findElevator()
    {
      // elevator mg =(elevator) getOneObjectAtOffset(0,1, class.elevator);
      elevator mg = null;
   // mg = (elevator) getOneObjectAtOffset(0,0,elevator.class);
      mg = (elevator) getOneIntersectingObject(elevator.class);
      
      if (mg!= null && !mworld.isGround(getX(), getY()-14) && !mworld.isGround(getX(), getY()+14) ){
           // mworld.paintBg(mg.getX(),mg.getY());
            
        if (
     //   ys > 0  
         mg.isActive()
        && ys > -1
        && (getY())  < (mg.getY()- mg.yOffset) 
        )
        {
            y = mg.getY()- mg.yOffset;
            x = x + mg.cX;
            y = y + mg.cY;
            ys = 0;
            canjump = true;
            
          
        }//ys
        
        }//mgnull
    }//void
   
}     
//class
