import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class Main {
	public static void main(String[] args) {
		// initialize game frame
		JFrame frame = new JFrame("Maze-UI");
		frame.setPreferredSize(new Dimension(500,500));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 		
		// set Sokoban as gamePanel
 		final Maze gamePanel = new Maze();
 		frame.setContentPane(gamePanel);

		// Game constants
		final boolean ONGOING = false;

		// set keylisteners
 		frame.addKeyListener(new KeyListener(){
             public void keyPressed(KeyEvent ke){
				if(ke.getKeyCode()==KeyEvent.VK_UP){
					if (gamePanel.state == ONGOING){
						gamePanel.moveUp();
					}
				} else if(ke.getKeyCode()==KeyEvent.VK_LEFT){
					if (gamePanel.state == ONGOING){
						gamePanel.moveLeft();
					}
				} else if(ke.getKeyCode()==KeyEvent.VK_DOWN){
					if (gamePanel.state == ONGOING){
						gamePanel.moveDown();
					}
				} else if(ke.getKeyCode()==KeyEvent.VK_RIGHT){
					if (gamePanel.state == ONGOING) {
						gamePanel.moveRight();
					}
				}
			}

			public void keyTyped(KeyEvent ke){}
			public void keyReleased(KeyEvent ke){}
        });

		// set game frame default attributes
		frame.setResizable(false);
        frame.setFocusable(true);
		frame.pack();
		frame.setVisible(true);
	}
}
