package sandBox;

import graphicsLib.Window;
import graphicsLib.G;
//all the imported class can be replaced by "awt.*"
//Graphics,Color,FontMetrics,event.MouseEvent,Point
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Paint extends Window{
    //there is no default constructor for Window
    //call (using super) the non-default constructor for Window.
    // The three arguments are the Name of the app,
    // the default width, Height of the single Window.
    public Paint(){super("Paint",1000,800);}

    public static int clicks = 0; // we will total the mouse clicks
    //modify Paint to hold a single path
    public static Path daPath=new Path();
    public static Pic daPic=new Pic();

    public void mousePressed(MouseEvent me){
        clicks++; // bump up the click counter.
        daPath=new Path();
        daPic.add(daPath);
        //daPath.clear();
        //if we use the same daPath and clear it when we have a
        //new line. window'll only show one line bc. daPic store the
        //reference of daPath.
        daPath.add(me.getPoint());
        repaint();
    }

    public void mouseDragged(MouseEvent me){
        daPath.add(me.getPoint());
        repaint();
    }

    //All painting in Swing is eventually done in a procedure: paintComponent
    //a Paint object is a Window and Windows are JPanels,
    //they have a paintComponent method
    protected void paintComponent(Graphics g){
        //g.setColor(Color.WHITE); g.fillRect(0,0,9000,9000);
        //g.setColor(Color.BLUE);
        Color c =G.rndColor();
        g.setColor(c);
        //Grab the lower corner of the window and resize it.
        //Notice that the Color of that Oval rapidly changes
        //Operating System notices that you are resizing the window
        //and calls you paintComponent routine.
        //OS decided something important has changed (the size of your window) and
        //your app may need to change the way things were laid out on the screen
        //so it calls your paintComponent to let your app fix things up.

        //telling that g object that was handed to you by the operating system
        //the "local coordinates" that you want to use for your drawing

        //It's Graphics object's job to figure out what the "global coordinates",
        // the actual place on the screen, that your local coordinates represent.

        //(x,y) coordinate - (100,100) in our case, was saying that
        //you want to move one hundred pixels from the left hand side of the window
        //and 100 pixels DOWN from the top of your window.
        g.fillOval(100,100,200,300);
        int x =400, y=200;
        String msg="Clicks="+clicks;

        // local variable fm is information about the current font.
        FontMetrics fm = g.getFontMetrics();
        // the ascent is how far above the baseline the font extends, descent is how far below for letters like
        // gqy
        // so the entire height of the font will be a+d
        int a =fm.getAscent(),d=fm.getDescent();
        // note: since fonts can have variable character width, iii taking less space than mmm, we must
        // tell fm, what string we are interested in measuring and fm will perform the calculation for us
        // and tell us how many pixels wide that string will be.
        // so now we know enough to draw the box.
        int w =fm.stringWidth(msg);
        //draw a bounding box for the text string
        g.drawRect(x,y-a,w,a+d);

        g.drawString(msg,x,y);
        g.drawOval(x,y,3,3);
        //g.drawString("Ma",400,200);
        g.setColor(Color.BLACK);
        g.drawLine(100,600,600,100);
        daPic.draw(g);
    }
    public static class Path extends ArrayList<Point> {
        //Point is a java.awt class that holds coordinate pairs
        public void draw(Graphics g){
            for(int i=1;i<size();i++){
                Point p=get(i-1),n=get(i);
                g.drawLine(p.x,p.y,n.x,n.y);
            }
        }
    }
    public static class Pic extends ArrayList<Path>{
        public void draw(Graphics g){
            for(Path p: this){
                p.draw(g);
            }
        }
    }
}
