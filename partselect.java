import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

///not an actual enemy
//this is the episode selector
public class partselect extends enemy
{
    
int gotolevel = 0;
int wait = 0;

static GreenfootImage partaimage= new GreenfootImage("part1.png");
static GreenfootImage partbimage= new GreenfootImage("part2.png");   
static GreenfootImage partcimage= new GreenfootImage("part3.png");
   
    public partselect(int lev, int img)
    {
        gotolevel = lev;
       bMeleeAttack = false;
        
        if (img == 0)
        {  setImage(partaimage); }
        
        if (img == 1)
        {  setImage(partbimage); }
        
        if (img == 2)
        {  setImage(partcimage); }
        
    }//partselect


    public partselect()
    {
        bMeleeAttack = false;
    }//partselect

    public void act() 
    {
         if (bRemoved) {return;}   
        if (wait > 0) { wait++;}
        if (wait > 8) {
          myWorld mg = (myWorld) getWorld();
        bRemoved = true;
        mg.currentLevel = gotolevel;
        mg.setLevel(gotolevel);
        }
       
    }   //act 
    
     public void damageStun(int dmg)
     {
        if (bRemoved) {return;}   
        myWorld mg = (myWorld) getWorld();
        wait = 1;
   //     mg.setLevel(gotolevel);
         
     }//damgstun
     
      public boolean canattack()
    {
        return false;
    }
    
    
}//class
