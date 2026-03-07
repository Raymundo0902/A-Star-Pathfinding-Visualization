

# A Star Pathfinding Visualizer
![Java](https://img.shields.io/badge/Language-Java-orange)
![Algorithm](https://img.shields.io/badge/Algorithm-A*-blue)
![Type](https://img.shields.io/badge/Project-2d%20indie%20horror%20game-green)

A Java implementation of the A* (A-Star) pathfinding algorithm designed for tile-based 2D games.
This system calculates the most efficient path from a start node to a goal node while avoiding obstacles.

The project demonstrates how A* can be integrated into a real-time game loop, allowing entities such as enemies to dynamically follow a player.



## Demo
Example of the algorithm generating a path through a grid with solid tiles acting as obstacles.

![ezgif com-video-to-gif-converter](https://github.com/user-attachments/assets/e9051a1d-a72e-46d9-b8ed-1b16b0c769c3)



## How A* Works

The A* algorithm finds the shortest path using three cost values:

G Cost

Distance from the start node to the current node

H Cost

Estimated distance from the current node to the goal node

Usually calculated with Manhattan Distance:

H = |x1 - x2| + |y1 - y2|

F Cost:
F = G + H

The algorithm always selects the node with the lowest F cost to explore next.




## Algorithm Steps


    1. Add the start node to the open list
    2. Loop until the goal node is found
    3. Select node with the lowest F cost
    4. Move it to the closed list
    5. Check all neighbor nodes
    6. Calculate new G/H/F costs
    7. Update parent nodes
    8. Repeat until goal is reached
    9. Reconstruct the path by following parent nodes backwards
## Grid System

Grid System

The algorithm operates on a 2D grid of nodes.

Each node contains:

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

Solid nodes represent obstacles.


## Project structure

    TestPanel.java:
    The panel featuring gridLayout, has several methods
    to make the algorithm work.

    Node.java:
    Represents each tile in the grid.

    KeyDetector.java:
    Listens for an enter key to begin the search.



## Why this project

This project was built to explore:

Game AI

Graph search algorithms

Efficient pathfinding in games

It serves as a foundation for enemy AI in a 2D Java indie horror game project i'm currently working on.

Here is how I used this algorithm to apply it in my game:

![2026-03-0707-49-47-ezgif com-video-to-gif-converter](https://github.com/user-attachments/assets/23e691d0-dc83-430b-b019-6fe4c6c37567)





## Author


Raymundo Portillo

Computer Science student focused on game development, simulations, algorithms, and backend systems.

Connect with me on LinkedIn! 
www.linkedin.com/in/raymundo-portillo-3370a3247

