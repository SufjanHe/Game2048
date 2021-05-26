/**
 * Author: Zirui He, hez66
 * Revised: April 12, 2021
 * 
 * Description: a module representing a tile
 */
package src;

import java.awt.*;


/**
 * @brief This class represents the tile type.
 */
public class Tile {

	int value;
	boolean hasMerged;

	/**
     * @brief This constructor instantiates the tile type with an int
     * @param value A int which is the value of the tile
     */
	public Tile(int value) {
		this.value = value;
	}

	/**
     * @brief This constructor instantiates a random tile
	 * @details 90% the tile is of value 2, 10% the tile is of value 4
     */
	public Tile() {
		this.value = Math.random() < 0.9 ? 2 : 4;
	}

	/**
     * @brief Get the color of tiles of different values
	 * @return the color of the tile
     */
	public Color getColor() {
		switch(value) {
			case 0: return new Color(0xcdc1b4);
			case 2: return new Color(0xeee4da);
			case 4: return new Color(0xeee1c9);
			case 8: return new Color(0xf3b297);
			case 16: return new Color(0xf69664);
			case 32: return new Color(0xf77c5f);
			case 64: return new Color(0xf75f3b);
			case 128: return new Color(0xedd073);
			case 256: return new Color(0xecca66);
			case 512: return new Color(0xedc651);
			case 1024: return new Color(0xeec745);
			case 2048: return new Color(0x66ce8a);
			default: return new Color(0xffffff);
		}
	}

	/**
     * @brief Get the color of the text in the tile
	 * @return the color of the text in tile
     */
	public Color getTextColor() {
		switch(value) {
			default: return new Color(0x776e65);
		}
	}
}
