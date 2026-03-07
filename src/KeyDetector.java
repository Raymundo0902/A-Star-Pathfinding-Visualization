import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyDetector implements KeyListener {

    TestPanel tp;

    public KeyDetector(TestPanel tp){
        this.tp = tp;
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        int code = e.getKeyCode();

        if(code == KeyEvent.VK_ENTER) {
//            tp.manualSearch();
            tp.autoSearch();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
