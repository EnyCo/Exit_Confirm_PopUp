import java.awt.*;
import java.awt.geom.*;
import java.util.*;
import java.awt.image.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.imageio.*;
import javax.swing.*;
import java.io.*;
public class popUp extends JFrame
{
    static KeyboardInput keyboard = new KeyboardInput();
    static MouseInput1 mouse; 
    
    static Canvas canvas;
    static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    
    static int HEIGHT = screenSize.height/4;
    static int WIDTH = screenSize.width/4;
    public static boolean input = false;
    public static boolean answer = false;
    static int canvasX = 0;
    static int canvasY = 0;
    static String message = "Do you want to exit the program?";
    Point p;
    Point c;
    static boolean view1 = false;
    static boolean view2 = false;
    public static void main() throws InterruptedException 
    {  
        popUp program = new popUp();
        program.setTitle( "popUp" );
        program.setVisible( true );
        program.run();
        program.setVisible( false );
    } 
    
    public popUp() 
    {
        canvas = new Canvas();
        setIgnoreRepaint( true );
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        canvas.setIgnoreRepaint( true );
        canvas.setSize( WIDTH, HEIGHT );
        add( canvas );
        pack();
        
        // for keyboard using
        addKeyListener( keyboard );
        canvas.addKeyListener( keyboard );
        
        // for mouse using
        mouse = new MouseInput1();
        addMouseListener( mouse );
        addMouseMotionListener( mouse );
        canvas.addMouseListener( mouse );
        canvas.addMouseMotionListener( mouse );
    }
    public void run() throws InterruptedException
    { 
        canvas.createBufferStrategy( 2 );
        BufferStrategy buffer = canvas.getBufferStrategy();
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        GraphicsConfiguration gc = gd.getDefaultConfiguration();
        BufferedImage bi = gc.createCompatibleImage( WIDTH, HEIGHT );
        
        Graphics graphics = null;
        Graphics2D draw = null;
        Color background = Color.WHITE;
        while(input == false) 
        {
            p = MouseInfo.getPointerInfo().getLocation();
            c = canvas.getLocationOnScreen();
            try { 
                
                keyboard.poll();
                mouse.poll();
                draw = bi.createGraphics();
                draw.setColor( background );
                draw.fillRect( 0, 0, WIDTH, HEIGHT );
                
                processInput();
                if (view1 == true)
                {
                    draw.setColor(Color.GREEN);
                    draw.fillRoundRect(WIDTH/5*3,HEIGHT/3*2,WIDTH/5,HEIGHT/6,10,10); 
                }
                if (view2 == true)
                {
                    draw.setColor(Color.RED);
                    draw.fillRoundRect(WIDTH/5,HEIGHT/3*2,WIDTH/5,HEIGHT/6,10,10); 
                }
                if (view1 == false)
                {
                    draw.setColor(Color.GREEN);
                    draw.drawRoundRect(WIDTH/5*3,HEIGHT/3*2,WIDTH/5,HEIGHT/6,10,10); 
                }
                if (view2 == false)
                {
                    draw.setColor(Color.RED);
                    draw.drawRoundRect(WIDTH/5,HEIGHT/3*2,WIDTH/5,HEIGHT/6,10,10); 
                }
                draw.setColor(Color.BLACK);
                draw.drawString("No", WIDTH/5*3+WIDTH/12,HEIGHT/3*2+HEIGHT/11);
                draw.drawString("Yes", WIDTH/5+WIDTH/12,HEIGHT/3*2+HEIGHT/11);
                draw.drawString(message, WIDTH/10*3,HEIGHT/3);
                
                graphics = buffer.getDrawGraphics();
                graphics.drawImage( bi, 0, 0, null );
                if( !buffer.contentsLost() )
                buffer.show();
            
              
                Thread.sleep(10);
            
            }
                finally 
            {
            
                if( graphics != null ) 
                  graphics.dispose();
                if( draw != null ) 
                  draw.dispose();
              
            }
          
          
        }
    }
    
    public void processInput() throws InterruptedException
    {
        int x = p.x-c.x;
        int y = p.y-c.y;
        //System.out.println(x + ":" +y);
        
            
        if (mouse.buttonDown( 1 ) && x > WIDTH/5 && x < 2*WIDTH/5 && y > HEIGHT/3*2 && y < HEIGHT/3*2+HEIGHT/6)//green
        {
            input = true;
            answer = true;
        }
        else if (mouse.buttonDown( 1 ) && x > 3*WIDTH/5 && x < 4*WIDTH/5 && y > HEIGHT/3*2 && y < HEIGHT/3*2+HEIGHT/6)//red
        {
            input = true;
            answer = false;
        }
        
        if (x > WIDTH/5 && x < 2*WIDTH/5 && y > HEIGHT/3*2 && y < HEIGHT/3*2+HEIGHT/6)//green
        {
            view2 = true;
        }
        else if (x > 3*WIDTH/5 && x < 4*WIDTH/5 && y > HEIGHT/3*2 && y < HEIGHT/3*2+HEIGHT/6)//red
        {
            view1 = true;
        }
        else
        {
            view1 = false;
            view2 = false;
        }
    } 
}