/**
 * Author: Zirui He, hez66
 * Revised: April 12, 2021
 * 
 * Description: a module representing the GUI
 */
package src;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;

/**
 * @brief This class represents the GUI
 * @details inherited from JPanel
 */
public class GUI extends JFrame {

	private final int width = 800;
	private final int height = 600;
	private final Game game;

	/**
     * @brief This constructor instantiates the GUI of the game
     * @param game the game the GUI shows
     */
	public GUI(Game game) {
		super();
		this.game = game;
		setTitle("2048 Game");
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(width, height));
		setLocationRelativeTo(null);
		pack();
		setFocusable(true);
		setVisible(true);
		getContentPane().add(new GameView(game));

		addMouseListener(new MouseAdapter() {
			/**
    		 * @brief mouse clicked on New Game button then start a new game
    		 * @param e mouse event that users do
    		 */
			@Override
			public void mousePressed(MouseEvent e) {
				super.mousePressed(e);
				if(e.getX() >= 675 && e.getX() <= 775 && e.getY() >= 150 && e.getY() <= 250) {
					game.board.startNewGame();
					repaint();
				}
			}
		});

		addKeyListener(new KeyAdapter() {
			/**
    		 * @brief key pressed then play the game with corresponding instruction
			 * @details the board moves tiles up if pressed key "UP", 
			 * so are "DOWN", "LEFT" and "RIGHT". Then repaint.
    		 * @param e key event that users do
    		 */
			@Override
			public void keyPressed(KeyEvent e) {

				int keyCode = e.getKeyCode();

				if(!game.board.gameOver) {
					switch (keyCode) {
						case KeyEvent.VK_UP:
							game.board.moveUp();
							break;
						case KeyEvent.VK_DOWN:
							game.board.moveDown();
							break;
						case KeyEvent.VK_LEFT:
							game.board.moveLeft();
							break;
						case KeyEvent.VK_RIGHT:
							game.board.moveRight();
							break;
						default:
							break;
					}
				}

				game.board.isGameOver();
				repaint();
			}
		});
	}
}