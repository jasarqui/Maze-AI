import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Dimension;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Main {
	static JFrame frame;
	static Maze gamePanel;
	static JButton resetBtn;
	static JButton solveBtn;

	public static void main(String[] args) {
		// initialize game frame
		frame = new JFrame("Maze-UI");
		frame.setPreferredSize(new Dimension(500,500));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 		
		// set Sokoban as gamePanel
 		gamePanel = new Maze();
 		frame.setContentPane(gamePanel);
		frame.setPreferredSize(new Dimension(gamePanel.mapLength() * 50, gamePanel.mapLength() * 50 + 35));
		frame.setLayout(new FlowLayout());

		// create reset button
		resetBtn = new JButton("Reset");
		resetBtn.setFocusable(false);
		frame.add(resetBtn);

		// create solver button
		solveBtn = new JButton("Solve");
		solveBtn.setFocusable(false);
		frame.add(solveBtn);

		// Game constants
		final boolean ONGOING = false;

		// set keylisteners
		// button reset
		resetBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gamePanel = new Maze();
				frame.setContentPane(gamePanel);
				frame.setPreferredSize(new Dimension(gamePanel.mapLength() * 50, (gamePanel.mapLength() * 50) + 35));
				frame.add(resetBtn);
				frame.add(solveBtn);
				frame.setResizable(false);
				frame.setFocusable(true);
				frame.pack();
				frame.setVisible(true);
			}
		});

		// button AI
		solveBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
				SolverPanel solver = new SolverPanel(gamePanel.getMap());
            }
        });

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
