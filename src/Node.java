import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// responds to mouse click
public class Node extends JButton implements ActionListener {

    Node parent;
    int col;
    int row;
    int gCost;
    int hCost;
    int fCost;
    boolean start;
    boolean goal;
    boolean solid;
    boolean open;
    boolean checked;

    public Node(int col, int row) {

        this.col = col;
        this.row = row;

        // set background and foreground colors for node
        setBackground(Color.white);
        setForeground(Color.black);
        addActionListener(this);
    }

    public void setAsStart() {
        setBackground(Color.red);
        setForeground(Color.black);
        setText("Start");
        start = true;
    }

    public void setAsGoal() {
        setBackground(Color.green);
        setForeground(Color.black);
        setText("Goal");
        goal = true;

    }

    public void setAsSolid() {
        setBackground(Color.black);
        setForeground(Color.black);
        solid = true;
    }

    public void setAsOpen() {
        open = true;
    }
    public void setAsChecked() {
        if(start == false && goal == false) {
            setBackground(Color.orange);
            setForeground(Color.black);
        }
        checked = true;
    }
    public void setAsPath() {
        setBackground(Color.magenta);
        setForeground(Color.black);
    }


    // Method runs when button detects an action event such as a click.
    @Override
    public void actionPerformed(ActionEvent e) {

        setBackground(Color.orange);
    }
}
