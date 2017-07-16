/*Create a new one for just threads. will call itself in its own constructor.
Reuse MyWin. 
A fun project would be to implement the drag and drop features on Pika.
Then create a variable. As it spins across the screen, one can grab it, and it will probably be mouse clicked,
and if the drag and drop is done manually, then the xy coordinates, are calculated based on the click coordinates. So 
if the click coordinates move while it is clicked, then the object clicked is drawn at the new coordinates. The thing
is that I want it to continue spinning, so there is a boolean that controls the automatic xy movement, but not the spinning. 
So when the mouse is released, then the boolean is set to false, again, but the pikachu keeps transalting where it was dropped.

 
Doesn't reset the coordinates, so prob the cX is advancing farther, but, since the image is scaled down it doesn't look as it cX has passed. 
*/

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D.Double;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.Image;

import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.imageio.ImageIO;

import java.lang.Thread;
import java.lang.Runnable;
import java.lang.InterruptedException;
import java.lang.Math;

import java.io.File;
import java.io.IOException;


class ThreadAnim extends JPanel implements Runnable
{
   private Thread firstThread; 
   private int BRDR_W;// = 800;
   private int BRDR_H;// = 800;
   
   //should just declare the radius as a constant and x & y take from it. but this code is portable in that
   //x and y can be taken just from any point on the plane. 
   private final double STRT_X1 = (double)0;//*(BRDR_W/8);
   private final double STRT_Y1 = (double)(0)+14;//BRDR_H/8
   private final double STRT_X2 = (double)0;//*(BRDR_W/8);
   private final double STRT_Y2 = (double)(0)-14;
   private BufferedImage surprisedPatrick;
   private BufferedImage alpha; 
   
   private ImageIcon background;
   
   
   private File starGif = new File("C:\\Users\\AdminMain.cece-PC\\Desktop\\starrysky.gif");
   private File vortexGif = new File("C:\\Users\\AdminMain.cece-PC\\Desktop\\vortex.gif");
   private File wonderlandGif = new File("C:\\Users\\AdminMain.cece-PC\\Desktop\\wonderland.gif");
   private File galaxyPic = new File("C:\\Users\\AdminMain.cece-PC\\Desktop\\galaxypic.jpg");
   
   private AffineTransform tf = new AffineTransform();
   
   private final double STRT_DEGREES = 90d;
   
   double tempX = STRT_X1-STRT_X2; //because the line starts vertical it won't need this. as the length can just be computed by half of y1 and y2. 
   double tempY = STRT_Y1-STRT_Y2; //radius can just be strty2-strty1/2
   
   private long PER =  24L;
   
   private Color def = null;
     
   double num = 0.5d;
   double cX;
   double cY;
   
   private double radius;
   private double degrees;
   private double x1, y1, x2, y2;
   
   private java.awt.geom.Line2D line;
   
   //fields
   
   //constructor will run itself
   
   /*instead of calling the EventQueue from here and creating a concurrent thread,
   and instead of creating any concurrent thread, and instead of 
   can simply call this method's run method directly: object.run();
   */
   public ThreadAnim()
   {
      initAll();
   }//constructor
   public ThreadAnim(Color m)
   {
      initAll(m);
   }//constructor
   
   
   private void initAll(Color m)
    {
//          try
//          {
//             background = ImageIO.read(vortexGif);      
//          }
//          catch(IOException e)
//          {
//             System.out.println("Error opening file.");;
//          }
         
         background = new ImageIcon(vortexGif.toString());
         background.setImageObserver(this);
         
            try
            {
               surprisedPatrick = ImageIO.read(new File("C:\\Users\\AdminMain.cece-PC\\Desktop\\pikachu_3D.png"));
            }
            catch(IOException e)
            {
               System.out.println("Error: patrick.");
            }
            
            alpha = new BufferedImage(surprisedPatrick.getWidth(), surprisedPatrick.getHeight(), BufferedImage.TYPE_INT_ARGB);
            
         
         BRDR_W = background.getIconWidth();
         BRDR_H = background.getIconHeight();
    
         //set the preferredSize
         setPreferredSize(new Dimension(BRDR_W, BRDR_H));
         //set the background color
         setBackground(Color.BLACK);
         
         setDoubleBuffered(true);        
         PER = 24L;
         def = m;
         

         
         x1 = STRT_X1;
         y1 = STRT_Y1;
         x2 = STRT_X2;
         y2 = STRT_Y2;
         
         cX = 0;//;*(BRDR_W/8);
         cY = 0;//*(BRDR_H/8);
         
         radius = Math.sqrt((Math.pow(tempX, 2d)/4d) + (Math.pow(tempY, 2d)/4d));
         
         degrees = STRT_DEGREES;
         //init'ze all the variables
         
         //initialize the object to draw. 
         line = new java.awt.geom.Line2D.Double(x1,y1, x2, y2);
         
         //finally intialize the thread.
         //will just create a new Thread to run this, b/c all examples so far do. Why not just ThreadAnim.run()?
         firstThread = new Thread(this);
         firstThread.start();
         
         // this.run();
    }

    private void initAll()
    {
         //set the preferredSize
         setPreferredSize(new Dimension(BRDR_W, BRDR_H));
         //set the background color
         setBackground(Color.BLACK);
         
         setDoubleBuffered(true);        
         
         x1 = STRT_X1;
         y1 = STRT_Y1;
         x2 = STRT_X2;
         y2 = STRT_Y2;
         
         cX = 0*(BRDR_W/8);
         cY = 0*(BRDR_H/8);
         
         radius = Math.sqrt((Math.pow(tempX, 2d)/4d) + (Math.pow(tempY, 2d)/4d));
         
         degrees = STRT_DEGREES;
         //init'ze all the variables
         
         //initialize the object to draw. 
         line = new java.awt.geom.Line2D.Double(x1,y1, x2, y2);
         
         //finally intialize the thread.
         //will just create a new Thread to run this, b/c all examples so far do. Why not just ThreadAnim.run()?
         firstThread = new Thread(this);         
         firstThread.start();         
         // this.run();
    }
    
   private void rotate()
   {    
      //first increment the degrees.
      degrees-=12d; //will move clockwise. if counter-clockwise degrees++;
      
      cX+=5;
      cY+=2;  
     
      if(x1 > (BRDR_W/2) || x2 > (BRDR_W/2) || y1 > (BRDR_H/2) || y2 > (BRDR_H/2))
      {
         num+=0.7d;
                  degrees-=10d;
         cX+=num;
         cY+=num;

      }
      else if(x1 > (BRDR_W/4) || x2 > (BRDR_W/4) || y1 > (BRDR_H/4) || y2 > (BRDR_H/4))
      {
         num+=0.4d;
                  degrees-=7d;
         cX+=num;
         cY+=num;

      }
      else if(x1 > (BRDR_W/8) || x2 > (BRDR_W/8) || y1 > (BRDR_H/8) || y2 > (BRDR_H/8))
      {
         num+=0.2d;
         degrees-=4d;
         cX+=num;
         cY+=num;
      }
       else if(x1 > 0 || x2 > 0 || y1 > 0 || y2 > 0)
      {
         num+=0.1d;
         degrees-=2d;
         cX+=num;
         cY+=num;
      }


     
      //if it's completed 360degrees, from 90 degrees, then reset.
      if(degrees < -270)
      {
         degrees = STRT_DEGREES;
      }
            
//       x1 =  cX - (radius * Math.cos(Math.toRadians(degrees))); 
//       y1 =  cY + (radius * Math.sin(Math.toRadians(degrees)));
//       x2 =  cX + (radius * Math.cos(Math.toRadians(degrees)));
//       y2 =  cY - (radius * Math.sin(Math.toRadians(degrees)));
      

      
      if(cX > BRDR_W || cY > BRDR_H)//if(x1 > BRDR_W || x2 > BRDR_W || y1 > BRDR_H || y2 > BRDR_H)
      {
         x1 = STRT_X1;
         y1 = STRT_Y1;
         x2 = STRT_X2;
         y2 = STRT_Y2;
         cX = 0;//*(BRDR_W/8);
         cY = 0;//*(BRDR_H/8);
         num = 0;
      }
      
//       System.out.printf("%n***Aft***%nDegree = %.0f%n(x1,y1)=(%.2f,%.2f)%n(x2,y2)=(%.2f,%.2f)%n(cX,cY)=(%.2f,%.2f)",
//                   degrees, x1,y1, x2,y2, cX, cY);
      line.setLine(x1,y1,x2,y2);
   } 
    
   
   @Override
   public void run()
   {
      //this is where the code will go to animate
      long befTime, timeAfterDelay , sleepTime;
      
      //initial delay
      if(Thread.currentThread() == firstThread)
      {
         try
         {
            Thread.sleep(500L);
         }
         catch(InterruptedException e)
         {
            System.out.println("Error with Thread!");
         }
      }
      
      befTime = System.currentTimeMillis();
      
      while(Thread.currentThread() == firstThread)
      {
         //if we want an initial delay it should go before the while loop.
 
         rotate();
            
         repaint();
         //I believe the calculations should be done after the repaint
         //_so that the repaint is done immediately when the while has cycled,
         //_so the actual sleep time is more accurate. But then a repaint is wasted in the beginning .
         
         
         //now the we calculate the time that has elapsed so that it updates at around the same time.
         //check if it is under
         timeAfterDelay = System.currentTimeMillis() - befTime;
         sleepTime = PER - timeAfterDelay;
         //the time after delay took too long, so no more delay. 
         if(sleepTime < 0)
            sleepTime = 0;            
         
         try
         {
            Thread.sleep(sleepTime);
         }
         catch(InterruptedException e)
         {
            System.out.println("Error with Thread!");
         }        
         
         befTime = System.currentTimeMillis();      
      }   
   }
   
   @Override
   public void paintComponent(Graphics g)
   {
//       if(count < 3)//degrees == 90d
      super.paintComponent(g); //paint the background
//       count++;
      //no need to call this if the entire window is painted as the background won't be seen.
      
      paintMyPanel(g);    
   }
   
   private void paintMyPanel(Graphics g)
   {
      Graphics2D g2D = (Graphics2D) g;
      Graphics2D pat = g2D;
      
//       g2D.drawImage(background.getImage(), 0, 0, this);
      background.paintIcon(this, g2D, 0, 0);
      
//       pat.transform(
      pat.transform(AffineTransform.getScaleInstance(0.4d, 0.4d));
      pat.transform(AffineTransform.getTranslateInstance(1,1));
      pat.transform(AffineTransform.getRotateInstance(Math.toRadians(degrees), (double)cX+(surprisedPatrick.getWidth(null)/2), cY+(surprisedPatrick.getHeight(null)/2)));
      /*
      pat.rotate(Math.toRadians(15d));
      pat.translate(1,1);
      */
      pat.drawImage(surprisedPatrick, (int)cX, (int)cY, this);
// //       alpha.getGraphics().drawImage(surprisedPatrick , (int)cX, (int)cY, this);
      
//       if(def == null)
//          g2D.setColor(Color.RED);
//       else      
//          g2D.setColor(def);
// 
//       
//       g2D.draw(line);

      pat.dispose();
   }
   
}//end ThreadAnim   
      
      
