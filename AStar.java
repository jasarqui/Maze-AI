// Data Structures
import java.util.Scanner;
import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.Queue;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

// I/O
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

// UI
import javax.swing.JPanel;
import javax.swing.JOptionPane;

public class AStar {
	// Keeper Graphic Details
	private int xPos;
	private int yPos;

	// Game
	private String[][] gamePuzzle;
	private final static boolean WIN = true;
	private final static boolean ONGOING = false;
	public boolean state;

	// AI
    private Comparator<State> comparator = new StateComparator();
	private PriorityQueue<State> frontier = new PriorityQueue<State>(100, comparator);
	private State initialState;
	private int xGoal;
	private int yGoal;

	// Letter Constants
	private final static String GOAL_TILE = "G";
	private final static String PLAYER = "P";
	private final static String VISITED_TILE = "V";
	private final static String UNVISITED_TILE = "t";
	private final static String WALL = "W";

	// Class Specification
	public AStar(String[][] map) {

		// set defaults
		this.xPos = 0;
		this.yPos = 0;
		this.xGoal = 0;
		this.yGoal = 0;

		// load puzzle
		this.gamePuzzle = new String[map[0].length][map[0].length];
        for (int y = 0; y < map[0].length; y++) {
            for (int x = 0; x < map[0].length; x++) {
                this.gamePuzzle[y][x] = map[y][x];
            }
        }

		// initialize game
		boolean foundPos = false;

		// position of player
		for(int i = 0; i < this.gamePuzzle[0].length; i++){
			for(int j = 0; j < this.gamePuzzle[0].length; j++){
				if (this.gamePuzzle[i][j].equals(PLAYER)){
					this.xPos = j;
					this.yPos = i;
					foundPos = true;
					break;
                }
			}
			if (foundPos) break;
		}

		// position of goal
		foundPos = false;

		// position of goal
		for(int i = 0; i < this.gamePuzzle[0].length; i++){
			for(int j = 0; j < this.gamePuzzle[0].length; j++){
				if (this.gamePuzzle[i][j].equals(GOAL_TILE)){
					this.xGoal = j;
					this.yGoal = i;
					foundPos = true;
					break;
                }
			}
			if (foundPos) break;
		}

		this.initialState = new State(
			this.xPos, 
			this.yPos,
			this.xGoal,
			this.yGoal,  
			this.gamePuzzle, 
			"INITIAL", 
			0, 
			0
		);
	}

	// Methods
	// Actions(s) function
	public ArrayList<String> findActions(State gameState) {
		ArrayList<String> actions = new ArrayList<String>();

		// checks the next and afternext positions
		// adds to actions list if available
		// cannot go back to previous position
		if (this.checkLeft(gameState)) {
			actions.add("LEFT");
		}
		
		if (this.checkRight(gameState)) {
			actions.add("RIGHT");
		}
		
		if (this.checkUp(gameState)) {
			actions.add("UP");
		}
		
		if (this.checkDown(gameState)) {
			actions.add("DOWN");
		}

		return actions;
	}

	// checks the left side of the player if available
	public boolean checkLeft(State gameState) {
		// set current puzzle states
		String currentPosition = gameState.getMap()[gameState.getyPos()][gameState.getxPos()];
		String nextPosition;
		
		// having an out of bound position in the next tile
		// means it has reached the end of map
		// this prevents errors
		if ((gameState.getxPos()-1) < 0){
			return false;
		} else {
			nextPosition = gameState.getMap()[gameState.getyPos()][gameState.getxPos()-1];
		}

		// if next position is a floor or goal tole
		if(nextPosition.equals(UNVISITED_TILE) || nextPosition.equals(GOAL_TILE)){
			// always return true
			return true;
		} 
		// else if wall or visited tile, return false by default
		return false;
	}

	// checks the right side of the player if available
	public boolean checkRight(State gameState) {
		// set current puzzle states
		String currentPosition = gameState.getMap()[gameState.getyPos()][gameState.getxPos()];
		String nextPosition;
		
		// having an out of bound position in the next tile
		// means it has reached the end of map
		// this prevents errors
		if ((gameState.getxPos()+1) >= gameState.getMap()[0].length){
			return false;
		} else {
			nextPosition = gameState.getMap()[gameState.getyPos()][gameState.getxPos()+1];
		}

		// if next position is a floor or goal tole
		if(nextPosition.equals(UNVISITED_TILE) || nextPosition.equals(GOAL_TILE)){
			// always return true
			return true;
		} 
		// else if wall or visited tile, return false by default
		return false;
	}

	// checks the upper side of the player if available
	public boolean checkUp(State gameState) {
		// set current puzzle states
		String currentPosition = gameState.getMap()[gameState.getyPos()][gameState.getxPos()];
		String nextPosition;
		
		// having an out of bound position in the next tile
		// means it has reached the end of map
		// this prevents errors
		if ((gameState.getyPos()-1) < 0){
			return false;
		} else {
			nextPosition = gameState.getMap()[gameState.getyPos()-1][gameState.getxPos()];
		}

		// if next position is a floor or goal tole
		if(nextPosition.equals(UNVISITED_TILE) || nextPosition.equals(GOAL_TILE)){
			// always return true
			return true;
		} 
		// else if wall or visited tile, return false by default
		return false;
	}

	// checks the lower side of the player if available
	public boolean checkDown(State gameState) {
		// set current puzzle states
		String currentPosition = gameState.getMap()[gameState.getyPos()][gameState.getxPos()];
		String nextPosition;
		
		// having an out of bound position in the next tile
		// means it has reached the end of map
		// this prevents errors
		if ((gameState.getyPos()+1) >= gameState.getMap()[0].length){
			return false;
		} else {
			nextPosition = gameState.getMap()[gameState.getyPos()+1][gameState.getxPos()];
		}

		// if next position is a floor or goal tole
		if(nextPosition.equals(UNVISITED_TILE) || nextPosition.equals(GOAL_TILE)){
			// always return true
			return true;
		} 
		// else if wall or visited tile, return false by default
		return false;
	}

	public State nextState(State currentState, String action) {
		State state = new State(currentState);
		// Note that out of bound state already checked in findActions
		String currentPosition = state.getMap()[state.getyPos()][state.getxPos()];
		String nextPosition;
		// constants for readability
		final int MOVE_LEFT = -1;
		final int MOVE_RIGHT = 1;
		final int MOVE_UP = -1;
		final int MOVE_DOWN = 1;

		// Uses Manhattan Distance Heuristic
        // Default Heuristic for Square Grids
        // that can only move in 4 main directions:
        // up, down, left, and right

        // It measures the distance between two points
        // along the axes at right angles
        // https://heuristicswiki.wikispaces.com/Manhattan+Distance

        // !! does not consider diagonals !!

		switch (action) {
			case "LEFT":
				nextPosition = state.getMap()[state.getyPos()][state.getxPos()-1];

				// if next position is a floor
				if(nextPosition.equals(UNVISITED_TILE) || nextPosition.equals(GOAL_TILE)){
					// change current position to a visited tile
					state.setMap(state.getxPos(), state.getyPos(), VISITED_TILE);
					// change next position to a player tile
					state.setMap(state.getxPos()-1, state.getyPos(), PLAYER);
					
					// update x-position of player		
					state.addPathCost();
					state.setOverallCost(
						state.getCurrPathCost() + ((double) 
							Math.sqrt(
								Math.pow(this.yGoal - state.getyPos(), 2) + 
								Math.pow(this.xGoal - state.getxPos(), 2)
							)
						)
					);
					state.setxPos(MOVE_LEFT);
					state.setAction("LEFT");
				} 

				// else if after next position is a wall, do nothing
				break;
			case "RIGHT":
				nextPosition = state.getMap()[state.getyPos()][state.getxPos()+1];

				// if next position is a floor
				if(nextPosition.equals(UNVISITED_TILE) || nextPosition.equals(GOAL_TILE)){
					// change current position to a floor tile
					state.setMap(state.getxPos(), state.getyPos(), VISITED_TILE);
					// change next position to a player tile
					state.setMap(state.getxPos()+1, state.getyPos(), PLAYER);
			
					// update x-position of player
					state.addPathCost();
					state.setOverallCost(
						state.getCurrPathCost() + ((double) 
							Math.sqrt(
								Math.pow(this.yGoal - state.getyPos(), 2) + 
								Math.pow(this.xGoal - state.getxPos(), 2)
							)
						)
					);		
					state.setxPos(MOVE_RIGHT);
					state.setAction("RIGHT");
				}

				// else if after next position is a wall, do nothing
				break;
			case "UP":
				nextPosition = state.getMap()[state.getyPos()-1][state.getxPos()];

				// if next position is a floor
				if(nextPosition.equals(UNVISITED_TILE) || nextPosition.equals(GOAL_TILE)){
					// change current position to a floor tile
					state.setMap(state.getxPos(), state.getyPos(), VISITED_TILE);
					// change next position to a keeper tile
					state.setMap(state.getxPos(), state.getyPos()-1, PLAYER);

					// update y-position of player
					state.addPathCost();
					state.setOverallCost(
						state.getCurrPathCost() + ((double) 
							Math.sqrt(
								Math.pow(this.yGoal - state.getyPos(), 2) + 
								Math.pow(this.xGoal - state.getxPos(), 2)
							)
						)
					);		
					state.setyPos(MOVE_UP);
					state.setAction("UP");
				}

				// else if after next position is a wall, do nothing
				break;
			case "DOWN":
				nextPosition = state.getMap()[state.getyPos()+1][state.getxPos()];

				// if next position is a floor
				if(nextPosition.equals(UNVISITED_TILE) || nextPosition.equals(GOAL_TILE)){
					// change current position to a floor tile
					state.setMap(state.getxPos(), state.getyPos(), VISITED_TILE);
					// change next position to a keeper tile
					state.setMap(state.getxPos(), state.getyPos()+1, PLAYER);
			
					// update y-position of player
					state.addPathCost();
					state.setOverallCost(
						state.getCurrPathCost() + ((double) 
							Math.sqrt(
								Math.pow(this.yGoal - state.getyPos(), 2) + 
								Math.pow(this.xGoal - state.getxPos(), 2)
							)
						)
					);		
					state.setyPos(MOVE_DOWN);
					state.setAction("DOWN");
				}

				// else if after next position is a wall or a box, do nothing
				break;
		}

		return state;
	}

	public boolean checkWinCondition(State currentState) {
		boolean goalNotReached = true;
		
		for(int y = 0; y < currentState.getMap()[0].length; y++){
			for(int x = 0; x < currentState.getMap()[y].length; x++){
				if (currentState.getMap()[y][x].equals(GOAL_TILE)) {
					goalNotReached = false;
					break;
				}
			}
		}

		// if the goal is reached, end game
		return goalNotReached;
	}

	public State solve() {
		// create the queue and add the initial state
		int iterations = 0;
		State currentState;
		this.frontier.offer(new State(this.initialState));
		// initialize explored state
		HashSet<String> exploredStates = new HashSet<String>();
		
		while (this.frontier.peek() != null) {
			// get the head of the queue
			currentState = new State(this.frontier.poll());

			// check if current state is a win condition
			if (this.checkWinCondition(currentState)) {
				System.out.println("Solution found");

				// write to a file
				try {
					BufferedWriter writer = new BufferedWriter(new FileWriter("maze.out"));
					writer.write(currentState.getPath().get(1) + "\n");
					for (int nextPath = 2; nextPath < currentState.getPath().size(); nextPath++) {
						writer.append(currentState.getPath().get(nextPath) + "\n");
					}
					writer.close();
				} catch (IOException e) {
					System.err.println("Problem writing to maze.out");
				}

				return currentState;
			}
			else {
				// check all possible moves that can be made
				for (String action: findActions(currentState)) {
					// check if state is already explored
					if (!exploredStates.contains(Arrays.deepToString(
						this.nextState(currentState, action).getMap()
					))) {
						// add to explored if not explored yet
						exploredStates.add(Arrays.deepToString(currentState.getMap()));
						// also to frontier
						this.frontier.offer(this.nextState(currentState, action));
					}
					// Maze specifications does not allow one
					// to go back to any previous states with the VISITED_TILE
					// checking for duplicates not implemented for the sake of runtime
					// as it will never go to that part of the code.
				}
			}
			iterations++;
			System.out.println("Number of Iterations: " + iterations);
		}

		JOptionPane.showMessageDialog(null, "Sorry, no ways to win. Kindly reset the game.", "Maze", JOptionPane.INFORMATION_MESSAGE);
		return null;
	}
}