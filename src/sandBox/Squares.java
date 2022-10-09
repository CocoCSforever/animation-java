package sandBox;

import graphicsLib.Window;
import graphicsLib.G;
import music.I;
import music.UC;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.*;//for Timer

public class Squares extends Window implements ActionListener{
    static G.VS daVS=new G.VS(100,100,200,300);
    static Color daColor=G.rndColor();
    public static Square.List daList =new Square.List();
    public static Square daSquare;

    //public boolean dragging =false;
    public static G.V mouseDelta=new G.V(0,0);
    public static G.V pressedLoc=new G.V(0,0);

    public static I.Area curArea;// this will be the area that we found on the LIST
    //public static Square.List daList=new Square.List(); //we create LIST as an empty list
    public static Square BACKGROUND =new Square(0,0){// this will be our back square BUT FIRST some overrides
        public void pressed(int x,int y){daSquare=new Square(x,y);daList.add(daSquare);}
        public void dragged(int x,int y){daSquare.resize(x,y);}
    };// this BACKGROUND is an anonymous subclass of Square with different pressed and dragged behavior
    // note the syntax. I created a new Square BUT just before the semi-colon I put in a block to
    // override some functions in this ONE SINGLE OBJECT. So it behaves like any Square BUT this
    // one object has a different pressed and a different dragged routine.
    // Now, we finish initializing that special first BACKGROUND Square and put it FIRST on LIST!
    static{BACKGROUND.c=Color.WHITE;BACKGROUND.size.set(3000,3000);daList.add(BACKGROUND);}
    // next we fix the mousePressed and mouseDragged to reflect the Area style.
    public static Timer timer;

    public Squares(){
        super("Squares",UC.mainWinodwWidth,UC.mainWindowHeight);
        timer=new Timer(30, (ActionListener)this);
        timer.setInitialDelay(2000);
        timer.start();
    }
    /*protected void paintComponent(Graphics g){
        //G.fillBackground(g,Color.WHITE);
        //daVS.fill(g,daColor);
        daList.draw(g);
    }*/

    public static class Square extends G.VS implements I.Area{
        public Color c=G.rndColor();
        //random velocity between -10 and 10 in both x and y
        //public G.V dv=new G.V(G.rnd(20)-10,G.rnd(20)-10);
        public G.V dv=new G.V(0,0);//stop the motion

        public Square(int x,int y){super(x,y,100,100);}

        public void resize(int x,int y){if(x>loc.x&&y>loc.y){size.set(x-loc.x,y-loc.y);}}
        public void move(int x,int y){loc.set(x,y);}
        public void moveAndBounce(){
            loc.add(dv);
            //which simply test when upper-left corner hits the wall instead of any side of rect
            if(lox()<0 && dv.x<0){dv.x=-dv.x;}
            if(hix()>1000 && dv.x>0){dv.x=-dv.x;}
            if(loy()<0 && dv.y<0){dv.y=-dv.y;}
            if(hiy()>800 && dv.y>0){dv.y=-dv.y;}
        }
        //fill first bc. the first time you draw a square,shouldn't have dv
        public void draw(Graphics g){fill(g,c);moveAndBounce();}

        // Squares already implement hit so we need 3 more routines.
        public void pressed(int x, int y){mouseDelta.set(loc.x - x, loc.y - y);} // calculate drag offset
        public void dragged(int x, int y){loc.set(mouseDelta.x + x, mouseDelta.y + y);}
        public void released(int x, int y){}

        public static class List extends ArrayList<Square>{
            public void draw(Graphics g){for(Square s: this){s.draw(g);}}
            public void addNew(int x,int y){add(new Square(x,y));}
            public Square hit(int x,int y){
                Square res=null;
                for(Square s: this){if(s.hit(x,y)){res=s;}}
                return res;
            }
        }
    }
    public void mousePressed(MouseEvent me){
        int x = me.getX(); int y = me.getY();
        curArea = daList.hit(x,y); // should always succeed because of BACKGROUND
        curArea.pressed(x,y);
        repaint(); // notice: I repaint here in Squares NOT in each little Area
    }
    public void mouseDragged(MouseEvent me){
        int x = me.getX(); int y = me.getY();
        curArea.dragged(x, y);
        repaint(); // notice: I repaint here in Squares NOT in each little Area
    }
    public void actionPerformed(ActionEvent ae){repaint();}
    // and paint component is now trivial
    public void paintComponent(Graphics g){
        daList.draw(g);
        if(daList.size()>=4) {// we will treat the 3 squares as handles for the spline
            G.poly.reset();
            int xa = daList.get(1).loc.x, ya = daList.get(1).loc.y;
            int xb = daList.get(2).loc.x, yb = daList.get(2).loc.y;
            int xc = daList.get(3).loc.x, yc = daList.get(3).loc.y;
            G.pSpline(xa, ya, xb, yb, xc, yc, 4);
            g.setColor(Color.BLACK);
            g.fillPolygon(G.poly);
        }
    }
    /*@Override
    public void mousePressed(MouseEvent me){
        //if(daVS.hit(me.getX(),me.getY())){daColor=G.rndColor();}
        //daList.addNew(me.getX(),me.getY());
        int x=me.getX(),y=me.getY();
        daSquare=daList.hit(x,y);
        if(daSquare!=null){
            dragging=true;
            daSquare.dv.set(0,0);
            pressedLoc.set(x,y);
            mouseDelta.set(daSquare.loc.x-x,daSquare.loc.y-y);
        }else{
            dragging=false;
            daSquare=new Square(x,y);
            daList.add(daSquare);
        }
        repaint();
    }
    public void mouseDragged(MouseEvent me){
        int x=me.getX(),y=me.getY();
        if(dragging){
            daSquare.move(mouseDelta.x+x,mouseDelta.y+y);
        }else{
            daSquare.resize(x,y);
        }
        repaint();
    }
    public void mouseReleased(MouseEvent me){
        if(dragging){
            daSquare.dv.set(me.getX()-pressedLoc.x,me.getY()-pressedLoc.y);
        }
    }*/
}
