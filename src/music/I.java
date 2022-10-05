package music;

import java.awt.*;
//keep all the interfaces inside I.java
public interface I {
    public interface Draw{public void draw(Graphics g);}
    public interface Hit{public boolean hit(int x,int y);}
    public interface React{
        //public int bid(Gesture g);
        //public void doit(Gesture g);
    }
    public interface Area{
        public boolean hit(int x, int y);
        public void pressed(int x,int y);
        public void dragged(int x,int y);
        public void released(int x,int y);
    }
}
