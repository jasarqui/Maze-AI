import javax.swing.JPanel;
import javax.swing.JOptionPane;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.File;
import java.util.Scanner;
import java.util.Random;

public class Maze extends JPanel {
	// Keeper Graphic Details
	private int xPos;
	private int yPos;
	private int size;
	private int length;

	// Images
	private Image goal;
	private Image visitedTile;
	private Image unvisitedTile;
	private Image player;
	private Image wall;

	// Game
	private Random random = new Random();
	private String[][] gamePuzzle;
	private final static boolean WIN = true;
	private final static boolean ONGOING = false;
	public boolean state;

	// Letter Constants
	private final static String GOAL_TILE = "G";
	private final static String PLAYER = "P";
	private final static String VISITED_TILE = "V";
	private final static String UNVISITED_TILE = "t";
	private final static String WALL = "W";

	// Class Specification
	public Maze() {
		// set defaults
		this.xPos = xPos;
		this.yPos = yPos;
		this.size = 50;
		this.state = ONGOING;

		// load puzzle
		Scanner scan = new Scanner(System.in);
		System.out.println("N: ");
		this.length = scan.nextInt();
		
		String[][] generatedPuzzle = new String[this.length][this.length];

        // ------------- put puzzle to array -------------
		// initialize puzzle as unvisited tiles
    	for (int height = 0; height < this.length; height++){
			for (int width = 0; width < this.length; width++){
				generatedPuzzle[height][width] = UNVISITED_TILE;
			}
		}

		// compute number of walls
		// number of walls = (2 * (length^2)) / 5
		int wallNumber = ((2 * (this.length * this.length)) / 5);

		// place walls
		int currentWalls = 0;
		while (currentWalls < wallNumber){
			// randomize position
			this.xPos = random.nextInt(this.length);
			this.yPos = random.nextInt(this.length);

			// if already a wall, continue
			if (generatedPuzzle[this.yPos][this.xPos].equals(WALL)) {
				continue;
			} else {
				// else change to wall
				generatedPuzzle[this.yPos][this.xPos] = WALL;
				currentWalls++;
			}
		}

		// place goal
		while (true) { // only ends with break
			// random position
			this.xPos = random.nextInt(this.length);
			this.yPos = random.nextInt(this.length);

			// if a wall, loop again for a new position
			if (generatedPuzzle[this.yPos][this.xPos].equals(WALL)) {
				continue;
			} else {
				// else change to goal
				generatedPuzzle[this.yPos][this.xPos] = GOAL_TILE;
				break;
			}
		}

		// place player
		while (true) { // only ends with break
			// random position
			this.xPos = random.nextInt(this.length);
			this.yPos = random.nextInt(this.length);

			// if a wall or a goal tile, loop again for a new position
			if (
				generatedPuzzle[this.yPos][this.xPos].equals(WALL) ||
				generatedPuzzle[this.yPos][this.xPos].equals(GOAL_TILE)
			) {
				continue;
			} else {
				// else change to goal
				generatedPuzzle[this.yPos][this.xPos] = PLAYER;
				break;
			}
		}

		// put puzzle to class
		this.gamePuzzle = generatedPuzzle;
	}

	// Methods
	public void checkWinCondition() {
		// true until proven false
		boolean reachedGoal = true;
		
		for(int y = 0; y < this.length; y++){
			for(int x = 0; x < this.length; x++){
				if (this.gamePuzzle[y][x].equals(GOAL_TILE)
				) {
					// finds the goal tile
					reachedGoal = false;
					break;
				}
			}
		}

		// if no more storage space exists without a box, end game
		if (reachedGoal){
			this.state = WIN;
			JOptionPane.showMessageDialog(null, "You Won!", "Maze", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	// just returns map length
	public int mapLength(){
		return this.length;
	}

	public void moveLeft() {
		// set current puzzle states
		String currentPosition = this.gamePuzzle[this.yPos][this.xPos];
		String nextPosition;
		
		// having an out of bound position in the next tile
		// means it has reached the end of map
		// this prevents errors
		if ((this.xPos - 1) < 0){
			return;
		} else {
			nextPosition = this.gamePuzzle[this.yPos][this.xPos-1];
		}
		
		// if next position is unvisited
		if(nextPosition.equals(UNVISITED_TILE) || nextPosition.equals(GOAL_TILE)){
			// change current position to a visited tile
			this.gamePuzzle[this.yPos][this.xPos] = VISITED_TILE;

			// change next position to a player tile
			this.gamePuzzle[this.yPos][this.xPos-1] = PLAYER;
	
			// update x-position of keeper		
			this.xPos--;
		} 
		// else if wall or visited tile, do nothing

		// reload GUI
		this.repaint();
		// check win condition
		this.checkWinCondition();
	}
	
	public void moveRight() {
		// set current puzzle states
		String currentPosition = this.gamePuzzle[this.yPos][this.xPos];
		String nextPosition;
		
		// having an out of bound position in the next tile
		// means it has reached the end of map
		// this prevents errors
		if ((this.xPos + 1) == this.length){
			return;
		} else {
			nextPosition = this.gamePuzzle[this.yPos][this.xPos+1];
		}
		
		// if next position is unvisited
		if(nextPosition.equals(UNVISITED_TILE) || nextPosition.equals(GOAL_TILE)){
			// change current position to a visited tile
			this.gamePuzzle[this.yPos][this.xPos] = VISITED_TILE;

			// change next position to a player tile
			this.gamePuzzle[this.yPos][this.xPos+1] = PLAYER;
	
			// update x-position of keeper		
			this.xPos++;
		} 
		// else if wall or visited tile, do nothing

		// reload GUI
		this.repaint();
		// check win condition
		this.checkWinCondition();
	}
	
	public void moveUp() {
		// set current puzzle states
		String currentPosition = this.gamePuzzle[this.yPos][this.xPos];
		String nextPosition;
		
		// having an out of bound position in the next tile
		// means it has reached the end of map
		// this prevents errors
		if ((this.yPos - 1) < 0){
			return;
		} else {
			nextPosition = this.gamePuzzle[this.yPos-1][this.xPos];
		}
		
		// if next position is unvisited
		if(nextPosition.equals(UNVISITED_TILE) || nextPosition.equals(GOAL_TILE)){
			// change current position to a visited tile
			this.gamePuzzle[this.yPos][this.xPos] = VISITED_TILE;

			// change next position to a player tile
			this.gamePuzzle[this.yPos-1][this.xPos] = PLAYER;
	
			// update x-position of keeper		
			this.yPos--;
		} 
		// else if wall or visited tile, do nothing

		// reload GUI
		this.repaint();
		// check win condition
		this.checkWinCondition();
	}
	
	public void moveDown() {
		// set current puzzle states
		String currentPosition = this.gamePuzzle[this.yPos][this.xPos];
		String nextPosition;
		
		// having an out of bound position in the next tile
		// means it has reached the end of map
		// this prevents errors
		if ((this.yPos + 1) == this.length){
			return;
		} else {
			nextPosition = this.gamePuzzle[this.yPos+1][this.xPos];
		}
		
		// if next position is unvisited
		if(nextPosition.equals(UNVISITED_TILE) || nextPosition.equals(GOAL_TILE)){
			// change current position to a visited tile
			this.gamePuzzle[this.yPos][this.xPos] = VISITED_TILE;

			// change next position to a player tile
			this.gamePuzzle[this.yPos+1][this.xPos] = PLAYER;
	
			// update x-position of keeper		
			this.yPos++;
		} 
		// else if wall or visited tile, do nothing

		// reload GUI
		this.repaint();
		// check win condition
		this.checkWinCondition();
	}

	// paint GUI
	public void paintComponent(Graphics g) {
		super.paintComponent(g); 
		try {
			// loads all images needed
			goal = ImageIO.read(new File("images/floor_dot.png"));
			unvisitedTile = ImageIO.read(new File("images/floor.png"));
			visitedTile = ImageIO.read(new File("images/leaf.gif"));
			wall = ImageIO.read(new File("images/wall.jpg"));
			player = ImageIO.read(new File("images/kara.png"));
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		Graphics2D g2d = (Graphics2D)g;

		// put images per grid
		for(int y = 0; y < this.length; y++){
			for(int x = 0; x < this.length; x++){
				switch(this.gamePuzzle[y][x]){
					case UNVISITED_TILE:
						g.drawImage(unvisitedTile, (x * 50), (y * 50), 50, 50, null);
						break;
					case VISITED_TILE:
						g.drawImage(unvisitedTile, (x * 50), (y * 50), 50, 50, null);
						g.drawImage(visitedTile, (x * 50), (y * 50), 50, 50, null);
						break;
					case PLAYER:
						g.drawImage(unvisitedTile, (x * 50), (y * 50), 50, 50, null);
						g.drawImage(player, (x * 50), (y * 50), 50, 50, null);
						break;
					case GOAL_TILE:
						g.drawImage(unvisitedTile, (x * 50), (y * 50), 50, 50, null);
						g.drawImage(goal, (x * 50), (y * 50), 50, 50, null);
						break;
					case WALL:
						g.drawImage(wall, (x * 50), (y * 50), 50, 50, null);
						break;
					default:
						break;
				}
			}
		}
	}
}

