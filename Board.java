//first create the Board w/ will be the jpanel
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.geom.Line2D.Double;

import javax.swing.JPanel;

import java.util.Timer;
import java.util.TimerTask;
import java.lang.Math.*;

public class Board extends JPanel
{
   //this JPanel is added to the JFrame. 
   //The JFrame takes on the size of this JPanel b/c of the preferredSize() and b/c of the JFrame's pack() method. True
   private final int B_WIDTH = 400;
   private final int B_HEIGHT = 400;
   private final int INITIAL_X1 = 4;
   private final int INITIAL_Y1 = 8;
   private final int INITIAL_X2 = 20;
   private final int INITIAL_Y2 = 2;
   
   private final long DELAY = 3000;
   private final long PER = 25;
   
   int x1, x2, y1, y2;
   Timer timer;
   
   
   //constructor
   public Board()
   {
      //calls on the initializing method
      initAll();
   }
   
   private void initAll()
   {
      //set the background color
      setBackground(Color.BLACK);      
      //set the preferred size
      setPreferredSize(new Dimension(B_WIDTH,B_HEIGHT));      
      //set the doubleBuffer: basically the image is created on a offscreen buffer and copied to the main window (?) 
      setDoubleBuffered(true);
      
      
      //initialize the starting points
      x1 = INITIAL_X1;
      y1 = INITIAL_Y1;
      x2 = INITIAL_X2;
      y2 = INITIAL_Y2;
      
      //set the timer and start it.
      timer = new Timer();
      timer.scheduleAtFixedRate(new ScheduledTask(), DELAY, PER);
   
   }
   private class ScheduledTask extends TimerTask
   {
      //the run() method is called
      //inside the run method, all the points are incremented so the object moves DR diagonaly.
      //Then the repaint method is called. 
      
      @Override
      public void run()
      {
         //recalculate (move)
         //these will have to move , easier prob to draw w/ points though, b/c +-. 
         //will have to do w/ half the equation of the line. 
         x1+=1;
         y1+=2;
         x2+=2;
         y2+=1;
         if(x2 > B_WIDTH || y2 > B_HEIGHT)
         {
            //reset
            x1 = INITIAL_X1;
            y1 = INITIAL_Y1;
            x2 = INITIAL_X2;
            y2 = INITIAL_Y2;
         }
         
         //repaint it. 
         //calling the Board's repaint method. can access outside members such as x1 etc. 
         repaint();  
      }
   }
   /*
   Some good things to know. 
   calling repaint() requests that this component ((JPanel)) be repainted.
   This is also called by the system for example when a window needs to be repainted such as when it becomes uncovered.
   The way the component is painted is that the update() method is called, which basically clears the component
   ((paints it using the default background color)), so if one wants to avoid flickering, one must override this method.
   The update() method then calls the paint() method, which paints the entire component. The paint method paints the border,
   children etc. and calls the paintComponent method. So this is why we just override the paintComponent method. 
   
   */
   @Override
   public void paintComponent(Graphics g)
   {
      //paints the background. if this is not done, then the object will leave a trail of itself/ afterimages. 
      //(?) how to paint the background of the panel w/o super.paintComponent(g) && the object on top. 
      if(x1 == INITIAL_X1)
      super.paintComponent(g);
      
      //call method to paint the line
      paintFrame(g);
      
      
   }
   
   public void paintFrame(Graphics g)
   {
      Graphics2D g2 = (Graphics2D) g;
      
      //draw a line
      g2.setColor(Color.YELLOW);
      g2.drawLine(x1, y1, x2, y2);
      //vv allows it to work smoothly on Unix-based OS's (?)
      Toolkit.getDefaultToolkit().sync();
   }   
}