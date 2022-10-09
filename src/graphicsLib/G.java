package graphicsLib;

//import java.awt.*;
import java.awt.*;
import java.util.Random;

public class G {
    //variable RND hold our new Random number generator
    public static Random RND =new Random();
    public static final VS BACKGROUND_RECT = new VS(0,0,3000,3000);

    //gives a random number in an integer range
    public static int rnd(int max){return RND.nextInt(max);}
    //helper function: create color in full range
    public static Color rndColor(){return new Color(rnd(256),rnd(256),rnd(256));}

    public static Polygon poly = new Polygon();

    public static void pSpline(int xa,int ya,int xb,int yb,int xc,int yc,int n){
        if(n==0){poly.addPoint(xa,ya);poly.addPoint(xc,yc);return;}
        int xab = (xa+xb)/2, yab=(ya+yb)/2,xbc=(xb+xc)/2,ybc=(yb+yc)/2;
        int xabc= (xab+xbc)/2,yabc=(yab+ybc)/2;
        pSpline(xa,ya,xab,yab,xabc,yabc,n-1);
        pSpline(xabc,yabc,xbc,ybc,xc,yc,n-1);
    }


    //we can change the following function:
    //public static void fillBackground(Graphics g,Color c){g.setColor(c);g.fillRect(0,0,3000,3000);}
    public static void fillBackground(Graphics g,Color c){BACKGROUND_RECT.fill(g,c);}

    public static class V{
        public int x,y;
        public V(int x,int y){this.set(x,y);}
        public void set(int x,int y){this.x=x;this.y=y;}
        public void add(V v){x+=v.x;y+=v.y;}// velocity vector addition(distance)
    }

    public static class VS{
        public V loc,size;
        public VS(int x,int y,int w,int h){loc=new V(x,y);size=new V(w,h);}
        public void fill(Graphics g,Color c){g.setColor(c);g.fillRect(loc.x,loc.y,size.x,size.y);}
        public boolean hit(int x,int y){return loc.x<=x && loc.y<=y && x<=loc.x+size.x && y<=loc.y+size.y;}
        public int lox(){return loc.x;}
        public int hix(){return loc.x+size.x;}
        public int midx(){return (loc.x+loc.x+size.x)/2;}
        public int loy(){return loc.y;}
        public int hiy(){return loc.y+size.y;}
        public int midy(){return (loc.y+loc.y+size.y)/2;}
    }



    public static class LoHi{}

    public static class BBox{}

    public static class PL{}

}
