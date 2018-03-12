import java.util.ArrayList;
import java.lang.Math;

// state class to easily store position of player and path
public class State {
    private int xPos;
    private int yPos;
    private int xGoal;
    private int yGoal;
    private double currPathcost = 0;
    private double overallCost = 0;
    private String[][] map;
    private ArrayList<String> path;

    public State(
            int xPos, 
            int yPos, 
            int xGoal,
            int yGoal,
            String[][] map, 
            String action, 
            double pathcost, 
            double overallCost
        ) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.xGoal = xGoal;
        this.yGoal = yGoal;
        this.currPathcost = pathcost;
        this.map = new String[map[0].length][map[0].length];

        // map
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map.length; x++) {
                this.map[y][x] = map[y][x];
            }
        }

        this.path = new ArrayList<String>();
        this.path.add(action);
    }

    public State(State newState) {
        this.xPos = newState.getxPos();
        this.yPos = newState.getyPos();
        this.currPathcost = newState.getCurrPathCost();
        this.overallCost = newState.getOverallCost();
        this.map = new String[newState.getMap()[0].length][newState.getMap()[0].length];

        // get the map
        for (int y = 0; y < newState.getMap()[0].length; y++) {
            for (int x = 0; x < newState.getMap()[y].length; x++) {
                this.map[y][x] = newState.getMap()[y][x];
            }
        }

        this.path = new ArrayList<String>(newState.getPath());
    }

    // Setters
    // sets the direction that lead to here
    public void setAction (String action) {
        this.path.add(action);
    }

    // sets the x position of the player
    public void setxPos (int xPos) {
        this.xPos = this.xPos + (xPos);
    }

    // sets the y position of the player
    public void setyPos (int yPos) {
        this.yPos = this.yPos + (yPos);
    }

    // sets the current state of the map
    public void setMap (int xPos, int yPos, String update) {
        this.map[yPos][xPos] = update;
    }

    // initializes the map
    public void initMap (String[][] map) {
        this.map = map;
    }

    // Getters
    // get the xPos
    public int getxPos () {
        return this.xPos;
    }

    // get the yPos
    public int getyPos () {
        return this.yPos;
    }

    // get the current map
    public String[][] getMap () {
        return this.map;
    }

    // get the previous path
    public String getPrevAction () {
        return this.path.get(this.path.size()-1);
    }

    // get the path
    public ArrayList<String> getPath () {
        return this.path;
    }

    // get the current path cost
    public double getCurrPathCost() {
        return this.currPathcost;
    }

    // get the overall cost
    public double getOverallCost() {
        return this.overallCost;
    }

    // add to path cost
    public void addPathCost() {
        this.currPathcost = this.currPathcost + 1;
    }

    // set the overall cost
    public void setOverallCost(double cost) {
        this.overallCost = cost;
    }
}