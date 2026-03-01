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
     reach the goal efficiently.

     There's a manual/auto search method. Uncomment in the KeyHandler class to experiment with both.

*/

public class TestPanel extends JPanel {


    // JPanel settings
    final int maxCol = 16;
    final int maxRow = 10;
    final int nodeSize = 70;
    final int screenWidth = nodeSize * maxCol;
    final int screenHeight = nodeSize * maxRow;

    // 2D array that holds references to Node objects
    Node[][] node = new Node[maxCol][maxRow];
    Node startNode, goalNode, currentNode;
    ArrayList<Node> openNodes = new ArrayList<>();
    ArrayList<Node> checkedList = new ArrayList<>();

    // Others
    boolean goalReached = false;
    int step = 0;

    public TestPanel() {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        // responsible to automatic sizing and positioning the components.
        this.setLayout(new GridLayout(maxRow, maxCol));
        this.addKeyListener(new KeyHandler(this));
        this.setFocusable(true);

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

    // !Press enter for every step for this method!
    // Call this method everytime we evaluate and check a new node
    public void manualSearch() {
        // first check if goal reached
        if(goalReached == false) {

            int row = currentNode.row;
            int col = currentNode.col;

            currentNode.setAsChecked();
            checkedList.add(currentNode);
            openNodes.remove(currentNode);

            // Only open nodes if there's a node around the current node to avoid NullPointer errors

            // Open the "up" node
            if(row - 1 >= 0) {
                openNode(node[col][row - 1]);
            }
            if(col - 1 >= 0) {
                // Open left node
                openNode(node[col - 1][row]);
            }
            if(row + 1 < maxRow) {
                // Open the down node
                openNode(node[col][row + 1]);
            }
            if(col + 1 < maxCol) {
                // Open the right node
                openNode(node[col + 1][row]);
            }

            // Find the best node
            int bestNodeIndex = 0;
            int bestNodefCost = 999;

            for(int i = 0; i < openNodes.size(); i++){

                // Check if this node's F cost is better than other
                if(openNodes.get(i).fCost < bestNodefCost) {
                    bestNodeIndex = i;
                    bestNodefCost = openNodes.get(i).fCost;
                }
                // If F cost is equal, check the G cost
                else if(openNodes.get(i).fCost == bestNodefCost) {
                    if(openNodes.get(i).gCost < openNodes.get(bestNodeIndex).gCost) {
                        bestNodeIndex = i;
                    }
                }
            }
            // After loop, get the best node -- where we'll step next since it's the best option
            currentNode = openNodes.get(bestNodeIndex);

            if(currentNode == goalNode) {
                goalReached = true;
                trackPath();
            }
        }
    }


    // Auto search
    // Call this method everytime we evaluate and check a new node
    public void autoSearch() {
        // first check if goal reached
        while(goalReached == false && step < 300) {

            int row = currentNode.row;
            int col = currentNode.col;

            currentNode.setAsChecked();
            checkedList.add(currentNode);
            openNodes.remove(currentNode);

            // Open the "up" and "down" nodes if available on grid.
            if(row - 1 >= 0) {
                openNode(node[col][row - 1]);
            }
            if(row + 1 < maxRow) {
                openNode(node[col][row + 1]);
            }
            // Open left and right nodes if available on grid.
            if(col - 1 >= 0) {
                openNode(node[col - 1][row]);
            }
            if(col + 1 < maxCol) {
                openNode(node[col + 1][row]);
            }

            // Find the best node
            int bestNodeIndex = 0;
            int lowestNodeFcost = 999;

            for(int i = 0; i < openNodes.size(); i++){

                // Check if this node's F cost is better than other
                if(openNodes.get(i).fCost < lowestNodeFcost) {
                    bestNodeIndex = i;
                    lowestNodeFcost = openNodes.get(i).fCost;
                }
                // If F cost is equal, then we must check the G cost
                else if(openNodes.get(i).fCost == lowestNodeFcost) {
                    if(openNodes.get(i).gCost < openNodes.get(bestNodeIndex).gCost) {
                        bestNodeIndex = i;
                    }
                }
            }
            // After loop, get the best node -- where we'll step next since it's the best option
            currentNode = openNodes.get(bestNodeIndex);

            if(currentNode == goalNode) {
                goalReached = true;
                trackPath();
            }
        }
    }

    private void trackPath() {

        // Backtrack and draw the decided path took
        Node current = goalNode;

        while(current != startNode) {
            current = current.parent; // gets updated each iteration

            if(current != startNode) {
                current.setAsPath();
            }
        }
    }

    private void openNode(Node node) {

        if(node.open == false && node.checked == false && node.solid == false) {
            // If the node is not opened yet, add it to the open list
            node.setAsOpen();
            node.parent = currentNode; // need this to track back the path when we reach the goal.
            openNodes.add(node);
        }
    }


}
