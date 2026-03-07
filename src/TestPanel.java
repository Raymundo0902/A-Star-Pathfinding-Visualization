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
      - You can manually click it yourself, or can press enter to let the algorithm find the path itself.

*/

public class TestPanel extends JPanel {


    // JPanel settings
    final int maxCol = 26;
    final int maxRow = 20;
    final int screenWidth = 1300;
    final int screenHeight = 800;


    // 2D array that holds references to Node objects
    Node[][] node = new Node[maxCol][maxRow];
    Node startNode, goalNode, currentNode;

    // Stores nodes depending on their status.
    ArrayList<Node> openNodes = new ArrayList<>();
    ArrayList<Node> checkedNodes = new ArrayList<>();

    // Others
    boolean goalReached = false;
    int step = 0;

    public TestPanel() {


        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        // responsible to automatic sizing and positioning the components.
        // GridLayout divides the panel by screenWidth / maxCol and screenHeight / maxRow and uses the results to know how wide/tall each cell should be.
        this.setLayout(new GridLayout(maxRow, maxCol));
        this.addKeyListener(new KeyDetector(this));
        this.setFocusable(true);

        // Place nodes
        int col = 0;
        int row = 0;


        while(col < maxCol && row < maxRow) {

            // Create a node object
            node[col][row] = new Node(col, row);
            // Add the current node object to the JPanel. GridLayout automatically positions it
            // Swing automatically renders it.
            // adds a button that's a node to the gridlayout cell
            this.add(node[col][row]);

            col++;
            if(col == maxCol) {
                col = 0;
                row++;
            }
        }

        // Set start, goal and solid nodes here. (modifies already placed nodes to visually represent start and goal nodes):
        setStartNode(1, 3);
        setGoalNode(25,2);

        setAllSolidNodes();

        // Display all costs on screen
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
//            node.setText("<html>F:" + node.fCost + "<br>G:"+ node.gCost+"</html>"); // used html tags because JButton doesn't really support line breaks
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
            checkedNodes.add(currentNode);
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
            checkedNodes.add(currentNode);
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
            System.out.println(current);
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

    private void setAllSolidNodes() {


// TOP SECTION
        setSolidNode(1,2); setSolidNode(2,2);
        setSolidNode(5,1); setSolidNode(5,2);
        setSolidNode(7,1); setSolidNode(8,1); setSolidNode(9,1);
        setSolidNode(11,1); setSolidNode(11,2);
        setSolidNode(14,1); setSolidNode(14,2);
        setSolidNode(17,1); setSolidNode(18,1); setSolidNode(19,1); setSolidNode(20,1);

// UPPER LEFT / CENTER
        setSolidNode(2,3); setSolidNode(2,4);
        setSolidNode(4,2); setSolidNode(4,3);
        setSolidNode(4,4); setSolidNode(5,4);
        setSolidNode(7,2); setSolidNode(7,3);
        setSolidNode(9,4);  setSolidNode(11,4);
        setSolidNode(12,3); setSolidNode(12,4); setSolidNode(12,5);
        setSolidNode(13,3); setSolidNode(14,3); setSolidNode(15,3); setSolidNode(16,3);
        setSolidNode(16,4); setSolidNode(17,4); setSolidNode(18,4); setSolidNode(19,4);
        setSolidNode(20,3); setSolidNode(21,3); setSolidNode(21,4);

// MID UPPER
        setSolidNode(3,5); setSolidNode(3,6);
        setSolidNode(3,7); setSolidNode(4,7);
        setSolidNode(5,7); setSolidNode(6,7);
        setSolidNode(7,5); setSolidNode(8,5);
        setSolidNode(9,5);
        setSolidNode(13,5); setSolidNode(13,6);
        setSolidNode(15,5); setSolidNode(15,6);
        setSolidNode(15,7); setSolidNode(15,8);
        setSolidNode(18,5); setSolidNode(18,6);
        setSolidNode(18,7); setSolidNode(19,7); setSolidNode(20,7);
        setSolidNode(22,5); setSolidNode(22,6); setSolidNode(22,7);

// CENTER
        setSolidNode(4,9); setSolidNode(5,9);
        setSolidNode(6,9); setSolidNode(7,9);
        setSolidNode(7,8); setSolidNode(7,10);
        setSolidNode(9,8); setSolidNode(9,9); setSolidNode(9,10);
        setSolidNode(10,10); setSolidNode(11,10);
        setSolidNode(12,8); setSolidNode(12,9);
        setSolidNode(13,9);
        setSolidNode(16,9); setSolidNode(16,10); setSolidNode(16,11);
        setSolidNode(18,10); setSolidNode(18,11); setSolidNode(18,12);
        setSolidNode(20,9); setSolidNode(20,10);
        setSolidNode(22,9); setSolidNode(22,10);

// MID LOWER
        setSolidNode(1,12); setSolidNode(2,12);
        setSolidNode(3,12); setSolidNode(4,12);
        setSolidNode(6,12);
        setSolidNode(8,12); setSolidNode(9,12);
        setSolidNode(9,13); setSolidNode(9,14);
        setSolidNode(9,15);
        setSolidNode(10,12); setSolidNode(11,12);
        setSolidNode(11,13);
        setSolidNode(13,12); setSolidNode(13,13);
        setSolidNode(15,12); setSolidNode(16,12);
        setSolidNode(19,12); setSolidNode(20,12);
        setSolidNode(21,12); setSolidNode(22,12);
        setSolidNode(23,12);

// LOWER SECTION
        setSolidNode(2,14); setSolidNode(2,15); setSolidNode(2,16);
        setSolidNode(2,16); setSolidNode(3,16); setSolidNode(4,16); setSolidNode(5,16); setSolidNode(6,16);
        setSolidNode(12,16); setSolidNode(13,16); setSolidNode(13,17);
        setSolidNode(16,14); setSolidNode(16,15);
        setSolidNode(16,16); setSolidNode(18,16);

        setSolidNode(18,16);
        setSolidNode(20,15); setSolidNode(20,16);
        setSolidNode(22,17);

        setSolidNode(0,8);
        setSolidNode(0,9);
        setSolidNode(1,9);
        setSolidNode(1,10);
        setSolidNode(1,11);

        setSolidNode(9,0);
        setSolidNode(10,0);
        setSolidNode(11,0);

        setSolidNode(3,17);
        setSolidNode(3,18);
        setSolidNode(21,13);
        setSolidNode(21,14);
        setSolidNode(21,15);

        setSolidNode(21,17);
        setSolidNode(20,17);
        setSolidNode(18,17);
        setSolidNode(18,18);
        setSolidNode(19,18);
        setSolidNode(19,19);

        setSolidNode(9,19);
        setSolidNode(9,18);
        setSolidNode(10,18);
        setSolidNode(11,18);
        setSolidNode(12,18);



        setSolidNode(13,18);















    }


}
