import java.awt.EventQueue;
import java.awt.Color;
import javax.swing.JFrame;

class MyWin extends JFrame
{  //JFrame basically window
   //this JFrame is created inside the run() method. 
   //so it constructs itself. and in turn it creates a new Board ((JPanel)) w/ is called...
   //the constructor can add the jpanel here
   public MyWin()
   {
      //add is from the component class (?)
      add(new ThreadAnim()); //new Board()
      add(new ThreadAnim(Color.YELLOW));
      
      //setResizeable to false
      setResizable(false);
      pack();
      
      //center it.
      setLocationRelativeTo(null);
      //
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      //make it visible
      setVisible(true);
   }
   
   public static void main(String[] args)
   {/*we make an anonymous class to run our program,
      instead of making a Thread object and doing threadObject.start() (?)
      and the thread object being our board b/c.
      
      
      So Runnable.run() is a single thread that runs synchronously meaning that it is run in the same executing thread.
      For programs like these, we can use it. 
      When we do:
      //create an object that implements the runnable interface & override its run method
      Class Runny implements Runnable
      {
         @Override
         public void run()
         {
            //do something
         }
      }
      //and then we pass it as an argument to a thread.
      Thread myThread = new Thread(new Runny());
      
      //and we just call the thread's run method
      
      myThread.run();
      
      it is the same as calling the Runnable object's run method, meaning it runs the thread synchronously
      
      new Runny().run(); //== myThread.run();
      
      //but if we do 
      
      myThread.start();
      
      //we run the Runnable object's run() method in a new concurrent thread, so we are multithreading. 
      
      //When we use
      
      EventQueue.invokeLater(new Runny());
      
      //We are sending a runnable to be put on the System's EventQueue which controls the threads that will be executed,
      //_ and it is executed on a thread called the event dispatch thread. The Runny object's run method will be executed
      //_asynchronously. 
      
      //So now the question is: (!)(?) If we want to create another thread, do we simply invoke the EventQueue.invokeLater(thread2)?.
         
      */
      EventQueue.invokeLater(new Runnable()
      {
      //we pass a anonymous runnable object with its run method overriden. 
      //this Runnable object's run method is called.
      //This implementaion of the run method will create a new JFrame.
         public void run()
         {
            MyWin prog = new MyWin();
         }
      });    
   
   }
}