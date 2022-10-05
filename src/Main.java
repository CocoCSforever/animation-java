import graphicsLib.Window;
import sandBox.Paint;
import sandBox.Squares;

public class Main {
    public static void main(String[] args){
        Window.PANEL = new Squares();
        Window.launch();
    }
}
