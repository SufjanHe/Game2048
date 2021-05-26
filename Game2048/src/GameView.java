/**
 * Author: Zirui He, hez66
 * Revised: April 12, 2021
 * 
 * Description: a module associated with the game view
 */
package src;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;


/**
 * @brief This class represents HOW the Game is viewed
 * @details inherited from JPanel
 */
public class GameView extends JPanel {

	static Game game;
	static int width = 500;
	static int height = 500;
	static int tileSize;
	static int tileSpace;

	/**
     * @brief This constructor instantiates the GameView of a game
     * @param game A game the GUI shows
     */
	public GameView(Game game) {
		GameView.game = game;
		tileSpace = 15;
		tileSize = (500 - (tileSpace * (1 + game.board.tiles.length))) / game.board.tiles.length;
	}

	/**
     * @brief Paint & style components in the GUI
     * @param graphics the graphics going to be styled in the GUI 
     */
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);

		Graphics2D g = ((Graphics2D) graphics);

		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

		setBackground(new Color(0xfaf8ef));

		g.setFont(new Font("SansSerif", Font.BOLD, 40));
		g.setColor(new Color(0x776e65));
		g.drawString("2048", 350, 40);

		g.setFont(new Font("SansSerif", Font.BOLD, 40));
		g.drawString("Score: " + game.board.getScore(), 35, 40);
		g.drawString("Best: " + game.board.getBest(), 555, 40);

		drawBoard(g);

		g.setColor(new Color(0x8f7a66));
		g.fillRoundRect(675, 150, 100, 100, 20, 20);

		g.setColor(new Color(0xffffff));

		g.drawString("New", 677, 193);
		g.drawString("Game", 677, 228);
	}

	/**
     * @brief Draw the board in the GUI
     * @param g the graphics going to be drawn in the GUI 
     */
	private static void drawBoard(Graphics g) {
		g.setColor(new Color(0xbbada0));
		g.fillRoundRect(150, 50, width, height, 10, 10);

		for (int row = 0; row < game.board.tiles.length; row++) {
			for (int col = 0; col < game.board.tiles.length; col++) {
				drawTile(g, game.board.tiles[row][col], col, row);
			}
		}
	}

	/**
     * @brief Draw tiles in the GUI
     * @param g the graphics going to be drawn in the GUI 
	 * @param tile the tile going to be drawn in the GUI 
	 * @param x the tile is in the (x+1)th row 
	 * @param y the tile is in the (y+1)th row
     */
	private static void drawTile(Graphics g, Tile tile, int x, int y) {
		if(tile == null) tile = new Tile(0);
		int value = tile.value;
		g.setColor(tile.getColor());

		g.fillRoundRect(150 + tileSpace + (x * (tileSpace + tileSize)), 50 + tileSpace + (y * (tileSpace + tileSize)), tileSize, tileSize, 10, 10);

		g.setColor(tile.getTextColor());

		Font font = new Font("SansSerif", Font.BOLD, 33);
		g.setFont(font);

		final FontMetrics fm = g.getFontMetrics(font);

		final int w = fm.stringWidth(value + "");
		final int h = -(int) fm.getLineMetrics(value + "", g).getBaselineOffsets()[2];

		if (value != 0) {
			g.drawString(value + "", 150 + tileSpace + (x * (tileSpace + tileSize)) + (tileSize - w) / 2, 50 + tileSpace + (y * (tileSpace + tileSize)) + tileSize - (tileSize - h) / 2 - 2);
		}

		if (game.board.gameOver) {
			g.setColor(new Color(241, 233, 224, 50));
			g.fillRect(150, 50, width, height);
			g.setColor(new Color(0x0));
			g.setFont(new Font("SansSerif", Font.BOLD, 30));
			g.drawString("You " + (game.board.win ? "Win! :)" : "Lose! :("), 400, 150);
		}
	}
}
