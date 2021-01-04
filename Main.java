import java.awt.*;
import java.awt.geom.*;
import java.util.*;
import java.awt.image.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.imageio.*;
import javax.swing.*;
import java.io.*;
public class Main extends JFrame
{
    static KeyboardInput keyboard = new KeyboardInput();
    static MouseInput1 mouse; 
    
    static Canvas canvas;
    static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    
    static int HEIGHT = screenSize.height;
    static int WIDTH = screenSize.width;
    static int popup = 0;
    static boolean answer = false;
    public static void main(String[] args) throws InterruptedException, IOException
    {  
        openSave();
        Main program = new Main();
        program.setTitle( "Main" );
        program.setVisible( true );
        program.run();
        System.exit( 0 );
    } 
    
    public Main() 
    {
        setIgnoreRepaint( true );
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        canvas = new Canvas();
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
        while(answer == false) 
        {
            try { 
                keyboard.poll();
                mouse.poll();
                draw = bi.createGraphics();
                draw.setColor( background );
                draw.fillRect( 0, 0, WIDTH, HEIGHT );
                
                
                if (popup == 1 && popUp.input == false)
                {
                    popUp.main();
                    answer = popUp.answer;
                    popup = 0;
                    popUp.input = false;
                }
                if (popup == 0)
                {
                    processInput();
                }
            
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
        save();
    }
    public static void openSave() throws IOException
    {
        try(BufferedReader br = new BufferedReader(new FileReader("Save.txt"))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
        
            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            String everything = sb.toString();
        } catch (IOException e) {
            System.out.println("File does not exist");
            e.printStackTrace();
        }
    }
    public void save()
    {
        try {
            FileWriter myWriter = new FileWriter("Save.txt");
            myWriter.write("");
            myWriter.close();
        } catch (IOException e) {
            System.out.println("Saving progress failed");
            e.printStackTrace();
        }
    }
    public void processInput() throws InterruptedException
    {
        Point p = MouseInfo.getPointerInfo().getLocation();
        int x = p.x;
        int y = p.y;
        
            
        if (keyboard.keyDownOnce( KeyEvent.VK_ESCAPE ) && popup != 1)//mouse.buttonDownOnce( 1 )
        {
            popup = 1;
        }
        else 
        {
            popup = 0;
        }
    } 
}