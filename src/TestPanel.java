import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/*
                                            A Star Pathfinding Algorithm:

     To evaluate the best/shortest path to the goal, A star uses three numbers: G Cost, H Cost and F Cost.
     G cost: the distance between the starting position and the current position (player,etc).
     H cost: The distance between the current position(node) and the goal node.
     F cost: The sum of G cost and H cost. (G + H)
     Every node has the three costs. The algorithm evaluates the costs and finds out which node is the most promising node to
     reach the goal effectively.

*/

public class TestPanel extends JPanel {



    final int maxCol = 16;
    final int maxRow = 10;
    final int nodeSize = 70;
    final int screenWidth = nodeSize * maxCol;
    final int screenHeight = nodeSize * maxRow;

    // 2D array that holds references to Node objects
    Node[][] node = new Node[maxCol][maxRow];
    Node startNode, goalNode, currentNode;
    ArrayList<Node> openList = new ArrayList<>();
    ArrayList<Node> checkedList = new ArrayList<>();

    // Others
    boolean goalReached = false;

    public TestPanel() {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        // responsible to automatic sizing and positioning the components.
        this.setLayout(new GridLayout(maxRow, maxCol));

        // Place nodes
        int col = 0;
        int row = 0;

        while(col < maxCol && row < maxRow) {

            // Create a node object
            node[col][row] = new Node(col, row);
            // Add the current node object to the JPanel. GridLayout automatically positions it
            // Swing automatically renders it.
            this.add(node[col][row]);

            col++;
            if(col == maxCol) {
                col = 0;
                row++;
            }
        }

        // Set start, goal and solid nodes here. (modifies already placed nodes to visually represent start and goal nodes):

        setStartNode(2, 7);
        setGoalNode(11,2);

        setSolidNode(6,1);
        setSolidNode(6,2);
        setSolidNode(7,2);
        setSolidNode(8,2);
        setSolidNode(9,2);
        setSolidNode(9,3);
        setSolidNode(9,4);
        setSolidNode(9,5);
        setSolidNode(9,6);
        setSolidNode(9,7);
        setSolidNode(10,7);
        setSolidNode(11,7);

        // Display cost on screen
        setCostOnNodes();

    }

    private void setStartNode(int col, int row) {
        node[col][row].setAsStart();
        startNode = node[col][row];
        currentNode = startNode;
    }

    private void setGoalNode(int col, int row) {
        node[col][row].setAsGoal();
        goalNode = node[col][row];
    }
    private void setSolidNode(int col, int row) {
        node[col][row].setAsSolid();

    }
    private void setCostOnNodes() {

        int col = 0;
        int row = 0;

        while(col < maxCol && row < maxRow) {

            getCost(node[col][row]);
            col++;
            if(col == maxCol) {
                col = 0;
                row++;
            }
        }
    }

    private void getCost(Node node) {

        // Get G cost (distance from start node)
        int xDistance = Math.abs(node.col - startNode.col); // returns abs value of x distance from current node and start node.
        int yDistance = Math.abs(node.row - startNode.row);
        node.gCost = xDistance + yDistance;

        // Get H Cost (distance from the goal node)
        xDistance = Math.abs(node.col - goalNode.col);
        yDistance = Math.abs(node.row - goalNode.row);
        node.hCost = xDistance + yDistance;

        // Get F Cost (total cost)
        node.fCost = node.gCost + node.hCost;

        if(node != startNode && node != goalNode) {
            node.setText("<html>F:" + node.fCost + "<br>G:"+ node.gCost+"</html>"); // used html tags because JButton doesn't really support line breaks
        }
    }
    // Call this method everytime we evaluate and check a new node
    public void search() {
        // first check if goal reached
        if(goalReached == false) {

            int row = currentNode.row;
            int col = currentNode.col;

            currentNode.setAsChecked();
            checkedList.add(currentNode);
            openList.remove(currentNode);

            // Only open nodes if there's a node around the current node to avoid NullPointer errors

            // Open the "up" node
            openNode(node[col][row-1]);
            // Open left node
            openNode(node[col-1][row]);
            // Open the down node
            openNode(node[col][row+1]);
            // Open the right node
            openNode(node[col+1][row]);

        }

    }

    private void openNode(Node node) {

        if(node.open == false && node.checked == false && node.solid == false) {

            // If the node is not opened yet, add it to the open list
            node.setAsOpen();
            node.parent = currentNode; // need this to track back the path when we reach the goal.
            openList.add(node);

        }
    }

}
