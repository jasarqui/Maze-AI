import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.File;
import java.util.Scanner;
import java.util.Random;

public class Sokoban extends JPanel {
	// Keeper Graphic Details
	private int xPos;
	private int yPos;
	private int size;

	// Images
	private Image box;
	private Image boxOn;
	private Image floor;
	private Image floorOn;
	private Image wall;
	private Image keeper;

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
		
		String[] generatedPuzzle = new String[10];
		int line = 0;

        // ------------- put puzzle to array -------------
    	this.gamePuzzle = new String[10][10];
        // split per space
        for(int i = 0; i < 10; i++){
        	this.gamePuzzle[i] = loadedPuzzle[i].split(" ");
        }

		// initialize game
		boolean foundPos = false;

		// position of warehouse keeper
		for(int i = 0; i < 10; i++){
			for(int j = 0; j < 10; j++){
				if (this.gamePuzzle[i][j].equals(KEEPER_OFF_STORAGE)){
					this.xPos = j;
					this.yPos = i;
					foundPos = true;
					break;
				} else if (this.gamePuzzle[i][j].equals(KEEPER_ON_STORAGE)){
					this.xPos = j;
					this.yPos = i;
					foundPos = true;
					break;
				}
			}
			if (foundPos) break;
		}
	}

	// Methods
	public void checkWinCondition() {
		int storageCount = 0;
		
		for(int y = 0; y < 9; y++){
			for(int x = 0; x < 9; x++){
				if (this.gamePuzzle[y][x].equals(FLOOR_WITH_STORAGE) ||
					this.gamePuzzle[y][x].equals(KEEPER_ON_STORAGE)
				) {
					// counts the number of storages without a box on it
					storageCount++;
				}
			}
		}

		// if no more storage space exists without a box, end game
		if (storageCount == 0){
			this.state = WIN;
			System.out.println("You Win!");
		}
	}

	public void moveLeft() {
		// set current puzzle states
		String currentPosition = this.gamePuzzle[this.yPos][this.xPos];
		String nextPosition = this.gamePuzzle[this.yPos][this.xPos-1];
		String afterNextPosition;
		
		// having an out of bound position after the next tile
		// means it has reached an outer wall
		// this prevents errors
		if (this.xPos-2 < 0){
			return;
		} else {
			afterNextPosition = this.gamePuzzle[this.yPos][this.xPos-2];
		}
		
		// if next position is a floor
		if(nextPosition.equals(FLOOR_NO_STORAGE) || nextPosition.equals(FLOOR_WITH_STORAGE)){
			// change current position to a floor tile
			if (currentPosition.equals(KEEPER_OFF_STORAGE)){
				this.gamePuzzle[this.yPos][this.xPos] = FLOOR_NO_STORAGE;
			} else if (currentPosition.equals(KEEPER_ON_STORAGE)){
				this.gamePuzzle[this.yPos][this.xPos] = FLOOR_WITH_STORAGE;
			}

			// change next position to a keeper tile
			if (nextPosition.equals(FLOOR_NO_STORAGE)){
				this.gamePuzzle[this.yPos][this.xPos-1] = KEEPER_OFF_STORAGE;
			} else if (nextPosition.equals(FLOOR_WITH_STORAGE)){
				this.gamePuzzle[this.yPos][this.xPos-1] = KEEPER_ON_STORAGE;
			}
	
			// update x-position of keeper		
			this.xPos--;
		
		// if next position is a box
		} else if (nextPosition.equals(BOX_OFF_STORAGE) || nextPosition.equals(BOX_ON_STORAGE)){
			// if after the next position is a floor
			if (afterNextPosition.equals(FLOOR_NO_STORAGE) || afterNextPosition.equals(FLOOR_WITH_STORAGE)){
				// change current position to a floor tile
				if (currentPosition.equals(KEEPER_OFF_STORAGE)){
					this.gamePuzzle[this.yPos][this.xPos] = FLOOR_NO_STORAGE;
				} else if (currentPosition.equals(KEEPER_ON_STORAGE)){
					this.gamePuzzle[this.yPos][this.xPos] = FLOOR_WITH_STORAGE;
				}

				// change next position to a keeper tile
				if (nextPosition.equals(BOX_OFF_STORAGE)){
					this.gamePuzzle[this.yPos][this.xPos-1] = KEEPER_OFF_STORAGE;
				} else if (nextPosition.equals(BOX_ON_STORAGE)){
					this.gamePuzzle[this.yPos][this.xPos-1] = KEEPER_ON_STORAGE;
				}

				// change after the next position to a box tile
				if (afterNextPosition.equals(FLOOR_NO_STORAGE)){
					this.gamePuzzle[this.yPos][this.xPos-2] = BOX_OFF_STORAGE;
				} else if (afterNextPosition.equals(FLOOR_WITH_STORAGE)){
					this.gamePuzzle[this.yPos][this.xPos-2] = BOX_ON_STORAGE;
				}

				// update x-position of keeper
				this.xPos--;	
			}

			// else if after next position is a wall or a box, do nothing
		}
		// else if wall or null tile, do nothing

		// reload GUI
		this.repaint();
		// check win condition
		this.checkWinCondition();
	}
	
	public void moveRight() {
		// set current puzzle states
		String currentPosition = this.gamePuzzle[this.yPos][this.xPos];
		String nextPosition = this.gamePuzzle[this.yPos][this.xPos+1];
		String afterNextPosition;
		
		// having an out of bound position after the next tile
		// means it has reached an outer wall
		// this prevents errors
		if (this.xPos+2 > 9){
			return;
		} else {
			afterNextPosition = this.gamePuzzle[this.yPos][this.xPos+2];
		}

		// if next position is a floor
		if(nextPosition.equals(FLOOR_NO_STORAGE) || nextPosition.equals(FLOOR_WITH_STORAGE)){
			// change current position to a floor tile
			if (currentPosition.equals(KEEPER_OFF_STORAGE)){
				this.gamePuzzle[this.yPos][this.xPos] = FLOOR_NO_STORAGE;
			} else if (currentPosition.equals(KEEPER_ON_STORAGE)){
				this.gamePuzzle[this.yPos][this.xPos] = FLOOR_WITH_STORAGE;
			}

			// change next position to a keeper tile
			if (nextPosition.equals(FLOOR_NO_STORAGE)){
				this.gamePuzzle[this.yPos][this.xPos+1] = KEEPER_OFF_STORAGE;
			} else if (nextPosition.equals(FLOOR_WITH_STORAGE)){
				this.gamePuzzle[this.yPos][this.xPos+1] = KEEPER_ON_STORAGE;
			}
	
			// update x-position of keeper		
			this.xPos++;
		
		// if next position is a box
		} else if (nextPosition.equals(BOX_OFF_STORAGE) || nextPosition.equals(BOX_ON_STORAGE)){
			// if after the next position is a floor
			if (afterNextPosition.equals(FLOOR_NO_STORAGE) || afterNextPosition.equals(FLOOR_WITH_STORAGE)){
				// change current position to a floor tile
				if (currentPosition.equals(KEEPER_OFF_STORAGE)){
					this.gamePuzzle[this.yPos][this.xPos] = FLOOR_NO_STORAGE;
				} else if (currentPosition.equals(KEEPER_ON_STORAGE)){
					this.gamePuzzle[this.yPos][this.xPos] = FLOOR_WITH_STORAGE;
				}

				// change next position to a keeper tile
				if (nextPosition.equals(BOX_OFF_STORAGE)){
					this.gamePuzzle[this.yPos][this.xPos+1] = KEEPER_OFF_STORAGE;
				} else if (nextPosition.equals(BOX_ON_STORAGE)){
					this.gamePuzzle[this.yPos][this.xPos+1] = KEEPER_ON_STORAGE;
				}

				// change after the next position to a box tile
				if (afterNextPosition.equals(FLOOR_NO_STORAGE)){
					this.gamePuzzle[this.yPos][this.xPos+2] = BOX_OFF_STORAGE;
				} else if (afterNextPosition.equals(FLOOR_WITH_STORAGE)){
					this.gamePuzzle[this.yPos][this.xPos+2] = BOX_ON_STORAGE;
				}

				// update x-position of keeper
				this.xPos++;	
			}

			// else if after next position is a wall or a box, do nothing
		}
		// else if wall or null tile, do nothing

		// reload GUI
		this.repaint();
		// check win condition
		this.checkWinCondition();
	}
	
	public void moveUp() {
		// set current puzzle states
		String currentPosition = this.gamePuzzle[this.yPos][this.xPos];
		String nextPosition = this.gamePuzzle[this.yPos-1][this.xPos];
		String afterNextPosition;
		
		// having an out of bound position after the next tile
		// means it has reached an outer wall
		// this prevents errors
		if (this.yPos-2 < 0){
			return;
		} else {
			afterNextPosition = this.gamePuzzle[this.yPos-2][this.xPos];
		}

		// if next position is a floor
		if(nextPosition.equals(FLOOR_NO_STORAGE) || nextPosition.equals(FLOOR_WITH_STORAGE)){
			// change current position to a floor tile
			if (currentPosition.equals(KEEPER_OFF_STORAGE)){
				this.gamePuzzle[this.yPos][this.xPos] = FLOOR_NO_STORAGE;
			} else if (currentPosition.equals(KEEPER_ON_STORAGE)){
				this.gamePuzzle[this.yPos][this.xPos] = FLOOR_WITH_STORAGE;
			}

			// change next position to a keeper tile
			if (nextPosition.equals(FLOOR_NO_STORAGE)){
				this.gamePuzzle[this.yPos-1][this.xPos] = KEEPER_OFF_STORAGE;
			} else if (nextPosition.equals(FLOOR_WITH_STORAGE)){
				this.gamePuzzle[this.yPos-1][this.xPos] = KEEPER_ON_STORAGE;
			}
	
			// update y-position of keeper		
			this.yPos--;
		
		// if next position is a box
		} else if (nextPosition.equals(BOX_OFF_STORAGE) || nextPosition.equals(BOX_ON_STORAGE)){
			// if after the next position is a floor
			if (afterNextPosition.equals(FLOOR_NO_STORAGE) || afterNextPosition.equals(FLOOR_WITH_STORAGE)){
				// change current position to a floor tile
				if (currentPosition.equals(KEEPER_OFF_STORAGE)){
					this.gamePuzzle[this.yPos][this.xPos] = FLOOR_NO_STORAGE;
				} else if (currentPosition.equals(KEEPER_ON_STORAGE)){
					this.gamePuzzle[this.yPos][this.xPos] = FLOOR_WITH_STORAGE;
				}

				// change next position to a keeper tile
				if (nextPosition.equals(BOX_OFF_STORAGE)){
					this.gamePuzzle[this.yPos-1][this.xPos] = KEEPER_OFF_STORAGE;
				} else if (nextPosition.equals(BOX_ON_STORAGE)){
					this.gamePuzzle[this.yPos-1][this.xPos] = KEEPER_ON_STORAGE;
				}

				// change after the next position to a box tile
				if (afterNextPosition.equals(FLOOR_NO_STORAGE)){
					this.gamePuzzle[this.yPos-2][this.xPos] = BOX_OFF_STORAGE;
				} else if (afterNextPosition.equals(FLOOR_WITH_STORAGE)){
					this.gamePuzzle[this.yPos-2][this.xPos] = BOX_ON_STORAGE;
				}

				// update y-position of keeper
				this.yPos--;	
			}

			// else if after next position is a wall or a box, do nothing
		}
		// else if wall or null tile, do nothing

		// reload GUI
		this.repaint();
		// check win condition
		this.checkWinCondition();
	}
	
	public void moveDown() {
		// set current puzzle states
		String currentPosition = this.gamePuzzle[this.yPos][this.xPos];
		String nextPosition = this.gamePuzzle[this.yPos+1][this.xPos];
		String afterNextPosition;
		
		// having an out of bound position after the next tile
		// means it has reached an outer wall
		// this prevents errors
		if (this.yPos+2 > 9){
			return;
		} else {
			afterNextPosition = this.gamePuzzle[this.yPos+2][this.xPos];
		}

		// if next position is a floor
		if(nextPosition.equals(FLOOR_NO_STORAGE) || nextPosition.equals(FLOOR_WITH_STORAGE)){
			// change current position to a floor tile
			if (currentPosition.equals(KEEPER_OFF_STORAGE)){
				this.gamePuzzle[this.yPos][this.xPos] = FLOOR_NO_STORAGE;
			} else if (currentPosition.equals(KEEPER_ON_STORAGE)){
				this.gamePuzzle[this.yPos][this.xPos] = FLOOR_WITH_STORAGE;
			}

			// change next position to a keeper tile
			if (nextPosition.equals(FLOOR_NO_STORAGE)){
				this.gamePuzzle[this.yPos+1][this.xPos] = KEEPER_OFF_STORAGE;
			} else if (nextPosition.equals(FLOOR_WITH_STORAGE)){
				this.gamePuzzle[this.yPos+1][this.xPos] = KEEPER_ON_STORAGE;
			}
	
			// update y-position of keeper		
			this.yPos++;
		
		// if next position is a box
		} else if (nextPosition.equals(BOX_OFF_STORAGE) || nextPosition.equals(BOX_ON_STORAGE)){
			// if after the next position is a floor
			if (afterNextPosition.equals(FLOOR_NO_STORAGE) || afterNextPosition.equals(FLOOR_WITH_STORAGE)){
				// change current position to a floor tile
				if (currentPosition.equals(KEEPER_OFF_STORAGE)){
					this.gamePuzzle[this.yPos][this.xPos] = FLOOR_NO_STORAGE;
				} else if (currentPosition.equals(KEEPER_ON_STORAGE)){
					this.gamePuzzle[this.yPos][this.xPos] = FLOOR_WITH_STORAGE;
				}

				// change next position to a keeper tile
				if (nextPosition.equals(BOX_OFF_STORAGE)){
					this.gamePuzzle[this.yPos+1][this.xPos] = KEEPER_OFF_STORAGE;
				} else if (nextPosition.equals(BOX_ON_STORAGE)){
					this.gamePuzzle[this.yPos+1][this.xPos] = KEEPER_ON_STORAGE;
				}

				// change after the next position to a box tile
				if (afterNextPosition.equals(FLOOR_NO_STORAGE)){
					this.gamePuzzle[this.yPos+2][this.xPos] = BOX_OFF_STORAGE;
				} else if (afterNextPosition.equals(FLOOR_WITH_STORAGE)){
					this.gamePuzzle[this.yPos+2][this.xPos] = BOX_ON_STORAGE;
				}

				// update y-position of keeper
				this.yPos++;	
			}

			// else if after next position is a wall or a box, do nothing
		}
		// else if wall or null tile, do nothing

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
			box = ImageIO.read(new File("images/box.png"));
			boxOn = ImageIO.read(new File("images/box_in.png"));
			floor = ImageIO.read(new File("images/floor.png"));
			floorOn = ImageIO.read(new File("images/floor_dot.png"));
			wall = ImageIO.read(new File("images/wall.png"));
			keeper = ImageIO.read(new File("images/warehouseKeeper.png"));
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		Graphics2D g2d = (Graphics2D)g;

		// put images per grid
		for(int y = 0; y < 10; y++){
			for(int x = 0; x < 10; x++){
				switch(this.gamePuzzle[y][x]){
					case BOX_OFF_STORAGE:
						g.drawImage(box, (x * 50), (y * 50), 50, 50, null);
						break;
					case BOX_ON_STORAGE:
						g.drawImage(boxOn, (x * 50), (y * 50), 50, 50, null);
						break;
					case KEEPER_OFF_STORAGE:
						g.drawImage(keeper, (x * 50), (y * 50), 50, 50, null);
						break;
					case KEEPER_ON_STORAGE:
						g.drawImage(keeper, (x * 50), (y * 50), 50, 50, null);
						break;
					case FLOOR_NO_STORAGE:
						g.drawImage(floor, (x * 50), (y * 50), 50, 50, null);
						break;
					case FLOOR_WITH_STORAGE:
						g.drawImage(floorOn, (x * 50), (y * 50), 50, 50, null);
						break;
					case WALL:
						g.drawImage(wall, (x * 50), (y * 50), 50, 50, null);
						break;
					case NULL_FLOOR:
						break;
					default:
						break;
				}
			}
		}
	}
}

