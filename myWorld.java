import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 *ok, so this codes a mess,
 *sorry bout that
 */
public class myWorld extends World
{
    //////////////////////////// /////////////////////////////

    static boolean bHARDMODE = false; 

    static  GreenfootImage background; //= new GreenfootImage("biologo.gif");
    static GreenfootImage bgmask; //= new GreenfootImage("levelbmask.gif"); 

    static int swidth = 640;
    static int sheight = 480;

    static GreenfootImage codes = new GreenfootImage("codes.gif");

    static Color grndcolor;
    static Color aicolor;
    static Color acidcolor;
    static Color hurtcolor; static Color althurtcolor;
    static Color jumpcolor;

    static int currentLevel = 0;
    static int maxLevel = 99; //be sure to change maxlevel if you made too much

    //a little trick, since i not figured out how lists work yet
    //so the player object just sends it coordinates to world
    //and then the enemies read it out from here
    //definetly not elegant but dead simple
    static public int playerx, playery; 

    static public int startx, starty;
    
    //////////////////////////////   //////////

   
    ///////////////////////////////////////////////////////////////////   
    ///stuff happening at start of game
    public myWorld()
    {    
        // Create a new world with swidthxsheight cells with a cell size of 1x1 pixels.
        super(swidth, sheight, 1);
        setvals();

        //drawing order of stuff
        setPaintOrder(
            effect.class,firedemon.class,
            edemon.class,hazard.class, 
            elevator.class, player.class,
            rock.class,
            worm.class,wormpart.class,
            key.class,enemy.class, 
            barrel.class,AbstractDoor.class);

        //             
        restartGame();//uncomment before export

        //  
        //  currentLevel = 26;
        //  setLevel(currentLevel);

        prepare();
    }//constructor
    ///////////////////////////////////////

    ////////////////////////////////////////act
    public void act()
    {
        pigbossfix();
        lastlevel();
        //  endboss();

    }///act
    ////////////////////////////////////////////   

    //  //this code is now in the endboss class itself
    //     private void endboss()
    //     {
    //         if (currentLevel != 32) {return;}
    //         if (
    //         getObjects(rock.class).isEmpty()      
    //         &&  !getObjects(endboss.class).isEmpty()
    //         && !bHARDMODE
    //         )
    //         {
    //         addObject(new rock(), Greenfoot.getRandomNumber(500)+80,300);
    //         }//endif
    //         
    //         if (
    //         getObjects(edemon.class).isEmpty()
    //         &&  !getObjects(endboss.class).isEmpty()
    //         && bHARDMODE
    //         )
    //         {
    //             addObject(new edemon(), Greenfoot.getRandomNumber(500)+80,50);
    //             addObject(new edemon(), Greenfoot.getRandomNumber(500)+80,50);
    //         //    addObject(new edemon(), Greenfoot.getRandomNumber(500)+80,50);
    //         }//endif
    //     
    //     }//endb

    //pigboss safety catch
    //in case the key doesnt appears
    private void pigbossfix()
    {

        if (currentLevel == 9
        && getObjects(kingpig.class).isEmpty()
        && getObjects(key.class).isEmpty()
        )
        {
            // addObject(new key(), 320,320);
            nextLevel();
        }//eendif

    }//voidend

    ///ending events
    private void lastlevel()
    {
        if (currentLevel == 33
        && playerx > 300
        && playery < 100 
        )
        {
            nextLevel();
        }//endif
    }//void

    ////////////////////////////////////////////
    //////////////////////////////////////////

    private void setvals()
    { //set which colors of the mask should use
        //i could just set them by rgb values  but paint is kind of unpredictable,
        //for some reason it saves yellow as 252 254 4 instead of 255 255 0 for example
        grndcolor = codes.getColorAt(1,1); //black, ground, cant pass it
        aicolor = codes.getColorAt(22,1); //red, if patrolling enemies hit it they turn
        acidcolor = codes.getColorAt(40,1); //green, instant death hazards
        hurtcolor = codes.getColorAt(57,1); //yellow - 1 hp down hazards
        althurtcolor = new Color(255,255,0);

        jumpcolor = codes.getColorAt(68,1); //cyan - jumppad
    }//setvals

    /////////////////////////////////
    //////////////////////////////////

    public boolean toggleDifficulty()
    {
        bHARDMODE = !bHARDMODE;
        return bHARDMODE;
    }//togglediff

    
    ///////////////////////////////////////////////////////
    //the players coordinates are sent to world
    //so enemies etc can read it from here
    //its just seemed easier to do this way

    public void setPlayerCoords(int px, int py)
    {
        playerx = px;
        playery = py;
        //    background.fillRect(px,py, 3,3);
    }

    /////////////////////////////////////radars    
    public boolean radarsDestroyed()
    {

        if (getObjects(radar.class).isEmpty()) {return true; } 
        return false;
    }//raddest

    ///////////////////////////////mask codes    
    public boolean isGround(Color mg)
    {
        if (grndcolor.equals(mg)) 
        {return true;}
        return false;
    }

    public boolean isGround(int x, int y)
    {
        if (x > swidth-1 || x < 0 || y > sheight-1 || y < 0) {return false;}

        Color ez = bgmask.getColorAt(x,y);
        if (grndcolor.equals(ez)) 
        {return true;}
        return false;
    }//ground color

    public boolean isPatrol(int x, int y)
    {
        if (x > swidth-1 || x < 0 || y > sheight-1 || y < 0) {return false;}

        Color ez = bgmask.getColorAt(x,y);
        if (aicolor.equals(ez)) 
        {return true;}
        return false;
    }//ground color

    public boolean isAcid(int x, int y)
    {
        if (x > swidth-1 || x < 0 || y > sheight-1 || y < 0) {return false;}

        Color ez = bgmask.getColorAt(x,y);
        if (acidcolor.equals(ez)) 
        {return true;}
        return false;
    }//hurt color

    public boolean isHurting(int x, int y)
    {
        if (x > swidth-1 || x < 0 || y > sheight-1 || y < 0) {return false;}

        Color ez = bgmask.getColorAt(x,y);
        if (hurtcolor.equals(ez) || althurtcolor.equals(ez)) 
        {return true;}
        return false;
    }//hurt color

    public boolean isJumpPad(int x, int y)
    {
        if (x > swidth-1 || x < 0 || y > sheight-1 || y < 0) {return false;}

        Color ez = bgmask.getColorAt(x,y);
        if (jumpcolor.equals(ez)) 
        {return true;}
        return false;
    }//hurt color

    ///////////////////////////////////////
    static  public void paintBg(int x, int y)
    {
        background.setColorAt(x,y, Color.RED);
        background.setColor(Color.RED);
        background.fillRect(x,y, 10,10);
    }//paintbg

    
    //this one was made only for testing purposes
    /// update - but then i ended up using it to clean up the levels
    public void testLevelLoad()
    {
        this.removeObjects( this.getObjects(elevator.class) );
        this.removeObjects( this.getObjects(enemy.class) );
        this.removeObjects( this.getObjects(key.class) );
        this.removeObjects( this.getObjects(AbstractDoor.class) );
        this.removeObjects( this.getObjects(player.class) );
        this.removeObjects( this.getObjects(defeat.class) );
        this.removeObjects( this.getObjects(hazard.class) );
        this.removeObjects( this.getObjects(sparkplug.class) );
    }//testlevload

    public void nextLevel()
    {
        currentLevel++;
        if ( currentLevel > maxLevel) {currentLevel = 0;}  
        setLevel(currentLevel);
        //setLevel(0);
    }//nextlevel

    public void restartLevel()
    {
        setLevel(currentLevel);
    }//restartlev

    public void restartGame()
    {
        bHARDMODE = false;
        currentLevel = -1;
        setLevel(currentLevel);
    }//restartgame

    public void setLevel(int i)
    {
        if (i <-5 || i > maxLevel) {return;}
        testLevelLoad();
        currentLevel = i;
        switch(i) {
            case -4: setLevel_portallevel(); break;
            case -3: setLevel_testlevel(); break;
            case -2: setLevel_logo(); break;
            case -1: setLevel_selectorlevel(); break;

            //WORLD 1
            case 0:  setLevel_a();  break; //tutorial level
            case 1:  setLevel_b();  break; 
            case 2:  setLevel_c();  break;
            case 3:  setLevel_hazard();  break;
            case 4:  setLevel_jumplevel();  break;
            case 5:  setLevel_zaplevel(); break;
            case 6: setLevel_frustratinglevel(); break;
            case 7: setLevel_ulevel();  break;
            case 8: setLevel_prebosslevel(); break;
            case 9: setLevel_piglevel(); break;

            case 10:  nextLevel(); break; ///insert cutscene here //world 1 to 2
            
            //WORLD 2
            case 11: setLevel_azteclevel(); break;
            case 12:  setLevel_cairolevel(); break;
            case 13: setLevel_egyptlevel(); break;

            case 14:  setLevel_snaillevel(); break;
            case 15: setLevel_dangerlevel(); break;
            case 16: setLevel_pitlevel(); break;
            case 17: setLevel_merrygolevel(); break; 

            case 18: nextLevel(); break; //no level yet

            case 19: setLevel_holelevel(); break;
            case 20: setLevel_wormlevel(); break;          

            case 21: setLevel_world2to3(); break;// nextLevel(); break; //insert cutscene here //world2 to 3

            case 22:  nextLevel(); break; // setLevel_demoendlevel(); break; // restartGame(); break;// setLevel_demoendlevel(); break;

            //WORLD 3
            case 23: nextLevel(); break; //setLevel_eleclevel(); break;   

            case 24: setLevel_plasmlevel(); break;
            case 25: setLevel_radarlevel(); break;
            case 26: setLevel_domelevel(); break;

            case 27: setLevel_nutslevel();break;
            case 28: setLevel_pipelevel();break;
            case 29: setLevel_blueprintlevel(); break;
            case 30: nextLevel(); break; 
            case 31: nextLevel(); break;
            case 32: setLevel_endbosslevel(); break; //boss

            case 33: setLevel_endinga(); break; //ending
            case 34:
            if (!bHARDMODE)
            {
                setLevel_endingb(); 
                break;
            }
            else
            {
                setLevel_endinghard();
            }//endif
            break;
            //  case 35: setLevel_endinghard(); break;

            case 99: setLevel_testlevel(); break;
        }//switch
    }//setlevel

    
    /////////////////////////////////
    public void setLevel_testlevel()
    {
        background = new GreenfootImage("testinglevelmask.gif");
        setBackground(background); 
        bgmask = new GreenfootImage("testinglevelmask.gif");
        startx = 160;
        starty = 400;
        addObject(new player(), startx,starty);
        DoorTests.setUpTestLevel (this);
    }//testlevel

    public void setLevel_portallevel()
    {
        background = new GreenfootImage("portallevelframe.png");
        setBackground(background); 
        bgmask = new GreenfootImage("portallevelmask.png");
        startx =429;
        starty =72;
        addObject(new player(), startx,starty);

        addObject(new fallift(false),314,433);
        addObject(new fallift(false),90,433);
        addObject(new fallift(false),548,433);
        addObject(new fallift(false),314,225);
        
        //addObject(new edemon(),320,240);
        addObject(new edemon(),320,240);

        //midtop
        addObject(new radar(), 143,214);
        addObject(new radar(), 232,214);
        // PORTAL A addObject(new radar(), 457,214);
        //mid
        addObject(new radar(), 163,317);
        addObject(new radar(), 408,317);
        // PORTAL A addObject(new radar(), 492,317);
        //down
        addObject(new radar(), 510,422);
        // PORTAL B addObject(new radar(), 197,422);
        // PORTAL B addObject(new radar(), 428,422);
        //top
        addObject(new radar(), 206,101);

        addObject(new MetalDoor(true,-2), 374,71);
        DoorTests.setUpPortalLevel (this);
    }//radarlevel
    
    public void setLevel_demoendlevel()
    {
        //           background = new GreenfootImage("demoendlevel.gif");
        //         setBackground(background); 
        //         bgmask = new GreenfootImage("prebosslevelmask.gif");
        background = new GreenfootImage("ending2mask.png");
        setBackground(background); 
        bgmask = new GreenfootImage("ending2mask.png");

        startx = 110;
        starty = 367;
        addObject(new player(), startx,starty);
        //   addObject(new NormalDoor(false), 595,365);
    }//level

    public void setLevel_endingb()
    {
        background = new GreenfootImage("bioendingb.png");
        setBackground(background); 
    }//ending

    public void  setLevel_endinghard()
    {
        background = new GreenfootImage("bioendinghard.png");
        setBackground(background); 
    }//ending for hardmode

    ///  where objects are in levels are 'stored' in methods for now
    // so if youre curious they all made by me creating objects around
    // and then inspecting their coordinates
    // lots of work but kind of fun actually

    
    //////////////////////////////////////selection etc
    public void setLevel_logo()
    {
        background = new GreenfootImage("biologo2b.png");
        setBackground(background);
        startx = 0;
        starty = 0;
        addObject(new player(), startx,starty);
        addObject(new NormalDoor(false), 0,0);
    }//logo
    /////////////////////

    public void setLevel_selectorlevel()
    {
        background = new GreenfootImage("episodeselectframe_withtestarea.png");
        setBackground(background); 
        bgmask = new GreenfootImage("prebosslevelmask.gif");

        startx = 110;
        starty = 367;
        addObject(new player(), startx,starty);

        addObject(new partselect(0,0), 180,222);

        addObject(new partselect(11,1), 350,222);

        addObject(new partselect(23,2), 520,222);

        addObject(new selectdiff(), 53, 272);
        
        addObject(new NormalDoor(false,-3), 600,367);

    }//level
    ////////////////////////////////ending/cutscenes
    public void setLevel_endinga()
    { 
        background = new GreenfootImage("endingaframe.png");
        setBackground(background); 
        bgmask = new GreenfootImage("endingamask.png");   
        startx = 46;
        starty =330;
        addObject(new player(), startx,starty); 
    } //enda

    public void setLevel_world2to3()
    {
        background = new GreenfootImage("world2to3frame.png");
        setBackground(background); 
        bgmask = new GreenfootImage("world2to3mask.png");   
        startx = 147;
        starty =86;
        addObject(new player(), startx,starty); 

        addObject(new fallift(false), 441,335);
        addObject(new MetalDoor(false), 587,139);

    }//w2to3

    /////////////////////////////////////// WORLD 3

    public void setLevel_endbosslevel()
    {
        background = new GreenfootImage("endbossframe.png");
        setBackground(background); 
        bgmask = new GreenfootImage("endbossmask.png");  
        startx = 118;
        starty =405;
        addObject(new player(), startx,starty); 

        addObject(new endboss(), 480,180);

        addObject(new NormalDoor(true), 502,404);  

        addObject(new fallift(false), 580,380);
        addObject(new fallift(false), 60,380);

    }//endbosslevel
    ///////// /// //////////////////////

 
    public void setLevel_nutslevel()
    {
        background = new GreenfootImage("nutslevelframe.png");
        setBackground(background); 
        bgmask = new GreenfootImage("nutslevelmask.png");   
        startx = 303;
        starty =345;
        addObject(new player(), startx,starty); 
        addObject(new MetalDoor(true), 312,40);

        //left
        addObject(new radar(),117,94);
        addObject(new radar(),64,295); 
        //mid
        addObject(new radar(),304,207); 
        //right
        addObject(new radar(),527,88); 
        addObject(new radar(),561,285);

        ////
        addObject(new fallift(),156,367); 
        addObject(new fallift(),455,372); 

        addObject(new fallift(),456,249); 
        addObject(new fallift(false),184,281); 

        //addObject(new fallift(),205,180); 

        addObject(new fallift(),383,195); 
        addObject(new fallift(false),448,152);

        addObject(new fallift(),260,95); 
        addObject(new fallift(),568,213); 
        addObject(new fallift(),63,229); 

        addObject(new fallift(false),25,416);
        addObject(new fallift(false),619,425);
        ///////////

        //sparkplug(int total, float maxdis, float ang, float andelt, float ddelt)
        addObject(new sparkplug(5,32,180,0.0f,0f), 27,452);
        addObject(new sparkplug(5,32,0,0.0f,0f), 614,457);

        addObject(new sparkplug(3,64,225,3.0f,1.4f), 25,23);
        addObject(new sparkplug(3,64,315,3.0f,1.4f), 614,23);


        if (bHARDMODE) 
        {
            addObject(new hazard(80,16,3,6f), 20, 220);
            addObject(new hazard(80,16,3,5f), 40, 220);
            addObject(new hazard(80,16,3,4f), 60, 220);

            addObject(new sparkplug(4,32,180,2.0f,2f), 306,248);
        }
        else
        {
            addObject(new sparkplug(3,32,180,2.0f,2f), 306,248);
        }//hardmo

    }//nutslevel
    ////////////////

    public void setLevel_pipelevel()
    {
        background = new GreenfootImage("pipelevelframe.png");
        setBackground(background); 
        bgmask = new GreenfootImage("pipelevelmask.png");   
        startx  = 44;
        starty =57;
        addObject(new player(), startx,starty);    
        addObject(new MetalDoor(true), 209,57);   

        for (int i= 187; i < 530 ;i+=32)
        { addObject(new barrel(), i,370); 
            //addObject(new barrel(), i,250);
        }//next i

        for (int i= 603; i > 237 ;i-=32)
        { //addObject(new barrel(), i,315);
            addObject(new barrel(), i,190);
        }//next i

        addObject(new barrel(), 409,277);       
        addObject(new fallift(true),285,280);  
        //addObject(new fallift(true),270,280)
        // addObject(new fallift(true),202,255); 

        addObject(new radar(),408,245);

        addObject(new radar(),58,423);

        addObject(new radar(),196,338);
        // addObject(new radar(),362,338);
        addObject(new radar(),488,338);

        addObject(new radar(),573,158);
        addObject(new radar(),420,158);
        addObject(new radar(),280,158);

        addObject(new radar(),516,52);
        addObject(new radar(),570,52);
        addObject(new radar(),618,52);

        for (int i= 260; i < 500 ;i+=48)
        {
            addObject(new enemy(),i,150); 
            addObject(new enemy(),i,330); 
        }//next u 

        addObject(new edemon(),612,41); 
        addObject(new edemon(),612,41); 
        //addObject(new edemon(),612,41); 

        if (bHARDMODE) 
        {
            addObject(new edemon(),612,41); 
            addObject(new edemon(),612,41); 
            addObject(new fire(180, 1.5f, 30,100, true), 86,209);
        }//hrdmod

        addObject(new sidelift(),455,130); 
        //sparkplug(int total, float maxdis, float ang, float andelt, float ddelt)
        addObject(new sparkplug(2,48,90,0.0f,5f), 294,440);
        addObject(new sparkplug(2,48,90,0.0f,5f), 440,440);

    }//pipelevel

    ///////////////////////////////
    public void setLevel_blueprintlevel()
    {
        background = new GreenfootImage("blueprintlevelframe.png");
        setBackground(background); 
        bgmask = new GreenfootImage("blueprintlevelmask.png");  
        startx =336;
        starty =103;
        addObject(new player(), startx,starty);
        addObject(new MetalDoor(true), 354,102);

        addObject(new fallift(false),418,279); 
        addObject(new fallift(false),564,400); 
        addObject(new fallift(false),239,450); 
        addObject(new fallift(false),173,295);  

        addObject(new fallift(false),319,335); 

        addObject(new radar(), 364,404);
        addObject(new radar(), 95,426);
        addObject(new radar(),59,235);
        addObject(new radar(),42,126);

        //sparkplug(int total, float maxdis, float ang, float andelt, float ddelt)
        addObject(new sparkplug(6,14,0,1.5f,0), 423,325);
        addObject(new sparkplug(5,14,180,1.5f,0), 177,334);
        addObject(new sparkplug(5,16,180,-1.5f,0), 121,171);

        addObject(new edemon(),549,123);
        addObject(new edemon(),549,123);

        if (bHARDMODE) 
        {
            //   addObject(new hazard(44,16,2,3f), 320, 250);
            addObject(new fire(100, 0.5f, 300,10, true), 320,220);
            addObject(new fire(110, 0.5f, 300,10, true), 320,220);
            addObject(new fire(120, 0.5f, 300,10, true), 320,220);
            addObject(new fire(130, 0.5f, 300,10, true), 320,220);
        }//hardmode

    }//blueprintlevel

    public void setLevel_domelevel()
    {
        background = new GreenfootImage("domelevelframe.png");
        setBackground(background); 
        bgmask = new GreenfootImage("domelevelmask.png");
        startx =357;
        starty =30;
        addObject(new player(), startx,starty);

        addObject(new rotalift(0, 1.2f, 200,50), 320,360);
        addObject(new rotalift(180, 1.2f, 200,50), 320,360);
        addObject(new rotalift(45, 1.2f, 200,50), 320,360);
        addObject(new rotalift(225, 1.2f, 200,50), 320,360);

        addObject(new fallift(false),65,394);
        addObject(new fallift(false),576,387);

        addObject(new radar(), 182,49);
        addObject(new radar(), 455,48);

        addObject(new MetalDoor(true), 373,164);

        //sparkplug(int total, float maxdis, float ang, float andelt, float ddelt)
        addObject(new sparkplug(5,48,200,0,0), 66,429);
        addObject(new sparkplug(5,48,340,0,0), 563,420);  

        //hazard(int dis, int direction, int spe, float fre)
        addObject(new hazard(44,0,2,3f), 320, 250);

        if (bHARDMODE) 
        {
            addObject(new hazard(44,16,2,3f), 320, 250);
            addObject(new fire(0, 1.0f, 100,100, true), 320,350);
            addObject(new fire(180, 1.0f, 100,100, true), 320,350);
            //  addObject(new fire(240, 1.0f, 100,100, true), 320,350);
            //   addObject(new fire(270, 1.0f, 100,100, true), 320,350);

            addObject(new fallift(true),322,200);
        }//endif

        addObject(new fire(90, -1.5f, 50,70, true), 127,77);
        addObject(new fire(180, 1.5f, 50,70, true), 521,80);

        addObject(new barrel(), 8,100);
        addObject(new barrel(), 624,100);
    }
    //domelevel

    public void setLevel_radarlevel()
    {
        background = new GreenfootImage("radarlevelframe.png");
        setBackground(background); 
        bgmask = new GreenfootImage("radarlevelmask.png");
        startx =429;
        starty =72;
        addObject(new player(), startx,starty);

        addObject(new fallift(false),314,436);
        addObject(new fallift(false),90,433);
        addObject(new fallift(false),559,433);

        addObject(new edemon(),320,240);
        addObject(new edemon(),320,240);

        if (bHARDMODE) 
        {
            addObject(new edemon(),320,240);
            addObject(new edemon(),320,240);
            addObject(new edemon(),320,240);
            addObject(new edemon(),320,240);
        }//endif

        //midtop
        addObject(new radar(), 143,214);
        addObject(new radar(), 232,214);
        addObject(new radar(), 457,214);
        //mid
        addObject(new radar(), 163,317);
        addObject(new radar(), 408,317);
        addObject(new radar(), 492,317);
        //down
        addObject(new radar(), 197,422);
        addObject(new radar(), 428,422);
        //top
        addObject(new radar(), 206,101);

        addObject(new MetalDoor(true), 374,71);
    }//radarlevel
    /////

    public void setLevel_plasmlevel()
    {
        background = new GreenfootImage("plasmalevelframe.png");
        setBackground(background); 
        bgmask = new GreenfootImage("plasmalevelmask.png");
        startx =70;
        starty =359;

        addObject(new player(), startx,starty);
        addObject(new key(), 63,102);
        addObject(new NormalDoor(true),100,357);

        addObject(new fallift(),293,352);
        addObject(new fallift(false),474,317);
        addObject(new fallift(),393,151);

        addObject(new fallift(false),127,142);
        addObject(new fallift(false),241,133);

        addObject(new sparkplug(7,32,180,0,0), 18,49);
        addObject(new sparkplug(7,32,0,0,0), 397,455);

        addObject(new edemon(),606,72);

        if (bHARDMODE)
        {
            //sparkplug(int total, float maxdis, float ang, float andelt, float ddelt)
            addObject(new sparkplug(4,24,90,0,1.5f), 200,375);
            addObject(new sparkplug(2,64,180, 2f,0), 542,255);

            addObject(new sparkplug(2,40,270,0,1.5f), 181,14);
        }//bhardmode

    }//eleclevel
 
    public void setLevel_eleclevel()
    {
        background = new GreenfootImage("triclevelmask.png");
        setBackground(background); 
        bgmask = new GreenfootImage("triclevelmask.png");
        startx =320;
        starty =50;

        addObject(new player(), startx,starty);
    }//eleclevel

    
    
    ////////////////////////////////////////   WORLD2

    public void setLevel_merrygolevel()
    {
        background = new GreenfootImage("merrygoroundframe.png");
        setBackground(background); 
        bgmask = new GreenfootImage("merrygoroundmask.png");
        startx = 120;
        starty = 400;
        addObject(new player(), startx,starty);
        addObject(new key(), 68,69);
        addObject(new NormalDoor(true),526,73);

        int ang = 0;
        for (int i = 0; i<8;i++)
        { 
            addObject(new rotalift(ang, 0.7f, 180,120), 320,240);
            addObject(new fire(ang, -0.5f, 50,20), 320,240);
            ang += 360/8;
        }//nexti
        addObject(new fire(0, -2f, 180,120), 320,240);

        if (bHARDMODE)
        {
            addObject(new fire(0, 2f, 180,120), 320,240);
        }//endif

        //          addObject(new rotalift(0, 0.7f, 180,140), 320,240);
        //          addObject(new rotalift(90, 0.7f, 180,140), 320,240);
        //          addObject(new rotalift(180, 0.7f, 180,140), 320,240);
        //          addObject(new rotalift(270, 0.7f, 180,140), 320,240);

    }//merrygolevel
    ///////////

    public void setLevel_holelevel()
    {
        background = new GreenfootImage("holelevelframe.png");
        setBackground(background); 
        bgmask = new GreenfootImage("holelevelmask.png");   
        startx = 320;
        starty = 50;
        addObject(new player(), startx,starty);
        addObject(new NormalDoor(true),313,403);

        addObject(new barrel(), 320+32,100);
        addObject(new barrel(), 320,100);
        addObject(new barrel(), 320-32,100);
    }//holelevel

    public void setLevel_pitlevel()
    {
        background = new GreenfootImage("pitlevelframe.png");
        setBackground(background); 
        bgmask = new GreenfootImage("pitlevelmask.png");   
        startx = 319;
        starty = 441;
        addObject(new player(), startx,starty);

        addObject(new key(), 60,193);
        addObject(new NormalDoor(true),573,208);

        addObject(new sidelift(), 240,410);
        addObject(new sidelift(), 390,370);
        addObject(new sidelift(), 220,320);
        addObject(new sidelift(), 300,265);

        if (!bHARDMODE) {
            addObject(new sidelift(), 210,120);
            addObject(new sidelift(), 380,120);
            addObject(new sidelift(), 300,140);
        }//endif

        if (bHARDMODE)
        {
            addObject(new firedemon(3,0),320,190);
        }//endif

        //left side
        for (int i = 77; i<258;i+=32) {
            addObject(new enemy(), i,36);
        }//next i

        //right side
        for (int i = 343; i<522;i+=32) {
            addObject(new enemy(), i,36);
        }//next i
    }//pitlevel

    public void setLevel_dangerlevel()
    {
        background = new GreenfootImage("dangerlevelframe.png");
        setBackground(background); 
        bgmask = new GreenfootImage("dangerlevelmask.png"); 

        startx = 115;
        starty = 389;
        addObject(new player(), startx,starty);
        addObject(new key(), 102,76);
        addObject(new NormalDoor(true),527,68);

        addObject(new rotalift(0, 1.5f, 10,120), 200,275); 
        addObject(new rotalift(90, 1.0f, 10,88), 36,176);
        addObject(new rotalift(180, 1.0f, 20,200), 406,244);
        addObject(new rotalift(90, 0.75f, 3,88), 607,205);

        addObject(new firedemon(6,0),419,47);
        addObject(new firedemon(1,0),125,183);

        addObject(new hazard(120,0,3,3f), 27, 189);

        if (bHARDMODE)
        {
            addObject(new fire(0, 1.5f, 80,80), 500,260);
            addObject(new fire(180, 1.5f, 80,80), 500,260);

            addObject(new fire(90, 1.5f, 80,80), 110,85);
        }//endif

    }//dangerlevel

    public void setLevel_snaillevel()
    {
        background = new GreenfootImage("snailblevelframe.png");
        setBackground(background); 
        bgmask = new GreenfootImage("snailblevelmask.png");
        startx = 533;
        starty = 110;
        addObject(new player(), startx,starty);
        addObject(new key(), 79,67);
        addObject(new NormalDoor(true),302,300);

        addObject(new elevator(),500,201); 
        addObject(new elevator(),604,391); 

        addObject(new fire(0, 1.5f, 80,0), 229,425);
        addObject(new fire(180, 1.5f, 80,0), 229,425);
        addObject(new barrel(), 380,425);
        addObject(new fire(220, 1.5f, 80,0), 550,425);
        addObject(new fire(40, 1.5f, 80,0), 550,425);

        if (bHARDMODE) {
            firedemon one = new firedemon(4,0);
            addObject(one,85,177);

            fire mg = new fire(90, 1.8f, 20,100);
            fire mgb =new fire(270, 1.8f, 20,100);

            //  fire mgc =new fire(270, 1.6f, 120,0);
            fire mgd =new fire(90, 2.6f,150,20);

            one.childs[0] = mgb;
            one.childs[1] = mg;
            //one.childs[2] = mgc;
            one.childs[2] = mgd;

            addObject(new firedemon(2,0),370,380);
        }//endif

        if (!bHARDMODE)
        {
            addObject(new firedemon(2,0),85,177);
        }//endif

        addObject(new firedemon(1,0),550,225);

    }//snaillevel
    //////

    public void setLevel_cairolevel()
    {
        background = new GreenfootImage("cairolevelframe.png");
        setBackground(background); 
        bgmask = new GreenfootImage("cairolevelmask.png");
        startx = 320;
        starty = 385;
        addObject(new player(), startx,starty);
        addObject(new key(), 61,69);
        addObject(new NormalDoor(true),582,73);

        //  addObject(new firedemon(), 423,247);
        addObject(new firedemon(3,16), 320,237);

         
        //right
        addObject(new sidelift(), 415,130);
        addObject(new sidelift(), 455,180);

        addObject(new sidelift(), 535,234);
        addObject(new sidelift(), 370,295);
        addObject(new sidelift(), 530,360);

        //left
        addObject(new sidelift(), 162,130);
        addObject(new sidelift(), 192,180);

        addObject(new sidelift(), 60,234);
        addObject(new sidelift(), 240,295);
        addObject(new sidelift(), 60,360);

        if (bHARDMODE)
        {
            addObject(new firedemon(2,0),411,62);
            addObject(new firedemon(2,0),212,56);

            addObject(new firedemon(2,0),160,300);
            addObject(new firedemon(2,16),480,300);

            addObject(new fire(0, 0.8f, 300,58),320,200);        
            addObject(new fire(0, -0.8f, 300,58),320,300);

            addObject(new fire(180, 0.8f, 300,58),320,200);        
            addObject(new fire(180, -0.8f, 300,58),320,300);
        }
        else
        {
            addObject(new firedemon(1,0),212,56);

            addObject(new enemy(), 546,71);
            addObject(new enemy(), 46,71);
        }//endif

    }//cairolevel
    /////

    public void setLevel_egyptlevel()
    {
        background = new GreenfootImage("egyptlevelframe.png");
        setBackground(background); 
        bgmask = new GreenfootImage("egyptlevelmask.png");

        startx = 285;
        starty = 389;
        addObject(new player(), startx,starty);

        addObject(new sidelift(), 238,145);
        addObject(new sidelift(), 387,210);
        addObject(new sidelift(), 287,260);

        addObject(new elevator(), 67, 470);
        addObject(new elevator(), 67, 210);

        addObject(new elevator(), 534, 316);
        addObject(new elevator(), 534, 116);

        addObject(new hazard(20,16,2,3f), 400, 50);
        addObject(new hazard(20,0,2,3f), 240, 50);

        addObject(new fire(0, 0.8f, 70),300,240);
        addObject(new fire(330, 0.8f, 70),300,240);
        addObject(new fire(30, 0.8f, 70),300,240);

        addObject(new fire(160, 0.8f, 70),300,230);
        addObject(new fire(190, 0.8f, 70),300,240);
        addObject(new fire(220, 0.8f, 70),300,240);

        addObject(new key(), 300,240);
        addObject(new NormalDoor(true),375,387);

        if (bHARDMODE)
        {
            addObject(new fire(0, 1.8f, 200,100),300,240);
            //addObject(new fire(180, 1.8f, 200,100),300,240);
        }//endif

    }//egypt

    //world2 level1
    public void setLevel_azteclevel()
    {
        background = new GreenfootImage("azteclevelframe.png");
        setBackground(background); 
        bgmask = new GreenfootImage("azteclevelmask.png");

        startx = 35;
        starty = 64;
        addObject(new player(), startx,starty);

        float fspeed = 0.5f;

        addObject(new fire(0, fspeed, 200),320,240);
        addObject(new fire(120, fspeed, 200),320,240);

        addObject(new fire(240, fspeed, 200),320,240);
        //    addObject(new fire(270, fspeed, 200),320,240);

        //  addObject(new fire(33, -2f, 60),320,400);

        addObject(new NormalDoor(true),596,57);

        if (bHARDMODE) 
        {
            //ang speed xd yd
            addObject(new rotalift(180, 2.5f, 50,0), 170,400); 
            addObject(new rotalift(0, 2.5f, 50,0), 440,400); 

            addObject(new rotalift(90, 2.5f, 40,60), 470,300);
            addObject(new rotalift(270, 2.5f, 30,60), 475,170);

            addObject(new firedemon(5,0),300,300);

            addObject(new fire(0, fspeed, 170),320,240);
            addObject(new fire(120, fspeed, 170),320,240);

            addObject(new fire(240, fspeed, 170),320,240);
            //   addObject(new fire(270, fspeed, 150),320,240);

            addObject(new fallift(), 95,100);

        }
        else
        {
            addObject(new sidelift(), 100,400);
            addObject(new sidelift(), 500,400);

            addObject(new elevator(), 440,450);
            addObject(new elevator(), 519,441);
        }//endif          
    }//azteclevel
    /////////   ////    

    //BOSS level worm
    public void setLevel_wormlevel()
    {
        background = new GreenfootImage("wormlevframe.png");
        setBackground(background); 
        bgmask = new GreenfootImage("wormlevmask.png");

        startx = 257;
        starty = 26;
        addObject(new player(), startx,starty);

        addObject(new NormalDoor(true),312,353);
        addObject(new worm(), 320,240);
    } //wormlevel

    
    
    ////////////////////world1 /////////////////////////////////  
    public void setLevel_frustratinglevel()
    {
        background = new GreenfootImage("frustlevelframe.gif");
        setBackground(background); 
        bgmask = new GreenfootImage("frustlevelmask.gif");
        startx = 336;
        starty = 269;
        addObject(new player(), startx,starty);

        addObject(new NormalDoor(true),478,163);
        addObject(new key(), 185,285);

        addObject(new enemy(),414,165);
        addObject(new enemy(),176,86);
        addObject(new enemy(),137,329);

        if (!bHARDMODE) {addObject(new sidelift(16),415, 362);}
        else {addObject(new rotalift(0,1.5f,160,5 ),418,358 ); }

        addObject(new hazard(90,16,4,3f), 600, 100);

        if (bHARDMODE) {    
            addObject(new enemy(),166,86);
            addObject(new enemy(),156,86);
            addObject(new enemy(),146,86);
            addObject(new enemy(),107,329);
            addObject(new enemy(),127,329);
            addObject(new enemy(),117,329);
            addObject(new hazard(100,0,4,3.1f), 600, 100);
        }//endif

    }//frustlevel

    

    
    public void setLevel_prebosslevel()
    {
        background = new GreenfootImage("prebosslevelframe.gif");
        setBackground(background); 
        bgmask = new GreenfootImage("prebosslevelmask.gif");

        startx = 110;
        starty = 367;
        addObject(new player(), startx,starty);
        addObject(new NormalDoor(false), 595,365);
    }//level

    
    public void setLevel_zaplevel()
    {
        background = new GreenfootImage("zaplevelframe.gif");
        setBackground(background); 
        bgmask = new GreenfootImage("zaplevelmask.gif");
        startx = 552;
        starty = 356;
        addObject(new player(), startx,starty);
        addObject(new NormalDoor(true), 496,395);
        addObject(new key(), 67, 78);

        addObject(new hazard(48,0,3,3f), 27, 189);
        addObject(new hazard(32,16,4,1f), 578, 87);

        addObject(new hazard(22,0,2,2f), 87, 410);

        if (bHARDMODE) { addObject(new hazard(160,0,1,4f), 100, 220);}
    }//zaplevel

    
    public void setLevel_piglevel()
    {
        background = new GreenfootImage("piglevelframe.gif");
        setBackground(background); 
        bgmask = new GreenfootImage("piglevelmask.gif");

        startx = 160;
        starty = 353;
        addObject(new player(), startx,starty);

        addObject(new kingpig(), 450,333);

        addObject(new NormalDoor(true), 52,384);
    }//piglevel

    public void setLevel_a()
    { //first level
        background = new GreenfootImage("leveloneframe.gif");
        setBackground(background); 
        bgmask = new GreenfootImage("levelonemask.gif");

        startx = 66;
        starty = 227;
        addObject(new player(), startx,starty);
        addObject(new key(), 583,202);
        addObject(new NormalDoor(true), 141,432);

        //addObject(new elevator(), 480, 254);
        //addObject(new elevator(), 444, 320);
        //addObject(new elevator(), 408, 400);
        
        DoorTests.setUpLevelOne (this);
    }//setlevel a

    public void setLevel_b()
    { //cave level with balloons
        background = new GreenfootImage("levelbframe.gif");
        setBackground(background); 
        bgmask = new GreenfootImage("levelbmask.gif");

        startx = 424;
        starty = 390;
        addObject(new player(), 424,390);
        addObject(new NormalDoor(true), 570,396);
        addObject(new key(), 62,40);

        addObject(new elevator(), 281, 420);
        addObject(new elevator(), 217, 324);
        addObject(new elevator(), 152, 236);
        addObject(new elevator(), 257, 143);
        addObject(new elevator(), 186, 56);

        addObject(new elevator(), 275, 263);
        addObject(new elevator(), 161, 354);
        addObject(new elevator(), 194, 425);
        addObject(new elevator(), 174, 133);

        if (bHARDMODE) { addObject(new edemon(), 494,24); }

    }//levelb

    public void setLevel_c()
    {
        background = new GreenfootImage("levelcframe.gif");
        setBackground(background); 
        bgmask = new GreenfootImage("levelcmask.gif");

        startx = 307;
        starty = 372;
        addObject(new player(), startx,starty);

        addObject(new NormalDoor(false), 304,39);

        addObject(new enemy(),555 ,91);
        addObject(new enemy(), 60 ,91);
        addObject(new enemy(), 303 ,114);

        addObject(new enemy(), 152 ,250);
        addObject(new enemy(), 489 ,250);

        addObject(new barrel(), 58,372);
        addObject(new barrel(), 558,372);

        addObject(new barrel(), 558,302);
        addObject(new barrel(), 62,302);

        for (int i= 276; i <351;i+=32)
        { addObject(new barrel(), i,150); }

        for (int i= 226; i <391;i+=32)
        { addObject(new barrel(), i,220); }

        addObject(new barrel(), 306,271);

        if (bHARDMODE) {addObject(new hazard(40,0,5,3f), 52, 190);}
    }//levelc
    
    public void setLevel_hazard()
    {
        background = new GreenfootImage("hzrdlevelframe.gif");
        setBackground(background); 
        bgmask = new GreenfootImage("hzrdlevelmask.gif");

        startx = 377;
        starty = 281;
        addObject(new player(), startx,starty);

        addObject(new NormalDoor(true), 313,129);
        addObject(new key(), 50,238);

        addObject(new elevator(), 474, 336);
        addObject(new elevator(), 528, 173);

        if (!bHARDMODE) { addObject(new sidelift(), 120, 307);}
    }//levelhazard

    public void setLevel_jumplevel()
    {
        background = new GreenfootImage("jumplevelframe.gif");
        setBackground(background); 
        bgmask = new GreenfootImage("jumplevelmask.gif");

        startx = 102;
        starty = 72;
        addObject(new player(), startx,starty);

        addObject(new NormalDoor(true), 62,70);
        addObject(new key(), 575,226);
        for (int i= 467; i <580;i+=32)
        { addObject(new enemy(), i,41);
            addObject(new enemy(), i,110);}//nexti

        if (bHARDMODE) {addObject(new hazard(100,0,3,3f), 320, 240);}
    }//leveljump

    public void setLevel_ulevel()
    {
        background = new GreenfootImage("ulevelframe.gif");
        setBackground(background); 
        bgmask = new GreenfootImage("ulevelmask.gif");

        startx = 316;
        starty = 61;
        addObject(new player(), startx,starty);

        addObject(new sidelift(), 128, 375);
        addObject(new sidelift(), 371, 375);

        addObject(new key(), 274,354);
        addObject(new NormalDoor(true),500,357);

        if (bHARDMODE) { 
            addObject(new hazard(120,0,4,1f), 320, 220);
            addObject(new hazard(3,0,2,0.1f), 320, 350);
        }//endif

        for (int i= 275; i > 105 ;i-=32)
        { addObject(new enemy(), i,120-32); }

        for (int i= 361; i < (361+170) ;i+=32)
        { addObject(new enemy(), i,120-32); }

        for (int i= 361; i < (361+170) ;i+=32)
        { addObject(new enemy(), i,120-32+128); }

        //leftside
        for (int i= 275; i > 105 ;i-=32)
        { addObject(new barrel(), i,120); }

        for (int i= 37; i < (37+128+64) ;i+=32)
        { addObject(new barrel(), i,120+96); }

        for (int i= 275; i > 105 ;i-=32)
        { addObject(new barrel(), i,120+96+96); }

        //rightside
        for (int i= 361; i < (361+170) ;i+=32)
        { addObject(new barrel(), i,120); }

        for (int i= 594; i > (594-128-96) ;i-=32)
        { addObject(new barrel(), i,120+64); }

        for (int i= 361; i < (361+170) ;i+=32)
        { addObject(new barrel(), i,120+128); }

        for (int i= 594; i > (594-128-96) ;i-=32)
        { addObject(new barrel(), i,120+64+128); }

    }//ulevel

    ///////////////////////    
    /**
     * Prepare the world for the start of the program.
     * That is: create the initial objects and add them to the world.
     */
    private void prepare()
    {
    }
}//class
