import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.io.File;
import java.util.Scanner;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.awt.Dimension;

public class SolverPanel {
    private Solver solver;

    public SolverPanel(String[][] map) {
		// load puzzle
		String[][] gamePuzzle;

        // ------------- put puzzle to array -------------
    	gamePuzzle = new String[map.length][map.length];
        // split per space
        for(int i = 0; i < map[0].length; i++){
            for(int j = 0; j < map[i].length; j++) {
        	    gamePuzzle[i][j] = map[i][j];
            }
        }

        JFrame aiFrame = new JFrame("Maze-AI");
        // set ai frame default attributes
        aiFrame.setPreferredSize(new Dimension(250,125));
        aiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
        aiFrame.setLayout(null);
        aiFrame.setResizable(false);
        aiFrame.setFocusable(true);

        // create the next and prev buttons
        JButton prev = new JButton();
        prev.setBounds(5,5,80,25);
        prev.setVisible(true);
        prev.setText("Prev");
        prev.setFocusable(false);
        aiFrame.add(prev);

        JButton next = new JButton();
        next.setBounds(165,5,80,25);
        next.setVisible(true);
        next.setText("Next");
        next.setFocusable(false);
        aiFrame.add(next);
        
        // solve the map
        AStar aStarSolver = new AStar(gamePuzzle);

        // create the solverPanel
        try {
            long startTime = System.currentTimeMillis();
            this.solver = new Solver(aStarSolver.solve().getPath()); 
            aiFrame.add(this.solver);
            long endTime = System.currentTimeMillis();
            System.out.println("Maze solved in " + (endTime - startTime));

            prev.addActionListener (new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    solver.prevIndex();
                }
            });

            next.addActionListener (new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    solver.nextIndex();
                }
            });

            // remove focus
            aiFrame.pack();
            aiFrame.setVisible(true);
            aiFrame.setFocusable(false);
        } catch (Exception e) {
            System.out.println("No possible ways to solve problem.");
        }
    }
}