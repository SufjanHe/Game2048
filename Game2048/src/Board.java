/**
 * Author: Zirui He, hez66
 * Revised: April 12, 2021
 * 
 * Description: a module representing boards
 */
package src;

import java.util.ArrayList;


/**
 * @brief This class represents the board type.
 */
public class Board {
    private int score;
    private int best;
    boolean gameOver;
    boolean win = false;
    Tile[][] tiles;

    /**
     * @brief This constructor instantiates the board type with an int
     * @details Start a new 2048 game
     * @param size A int which shows the board is of size*size 
     */
    public Board(int size) {
        super();
        this.tiles = new Tile[size][size];
        startNewGame();
    }

    /**
     * @brief A method that start a new 2048 game in the board
     * @details 2 random tiles is added
     */
    public void startNewGame() {
        score = 0;
        tiles = new Tile[tiles.length][tiles.length];
        gameOver = false;
        win = false;
        addRandomTile();
        addRandomTile();
    }

    /**
     * @brief A method that add a tile randomly
     * @return true if it's added successfully, false otherwise
     */
    public boolean addRandomTile() {
        double numZeroes = 0;

        int lastZeroRow = -1;
        int lastZeroCol = -1;

        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                if (tiles[i][j] == null) {
                    numZeroes++;
                    lastZeroRow = i;
                    lastZeroCol = j;
                }
            }
        }

        // Board is full
        if (lastZeroRow == -1) return false;

        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                // In theory this should always place a tile
                boolean putTileAtLoc = Math.random() < 1.0 / numZeroes;
                if (tiles[i][j] == null && putTileAtLoc) {
                    tiles[i][j] = new Tile();
                    return true;
                }
            }
        }

        tiles[lastZeroRow][lastZeroCol] = new Tile();
        return true;
    }

    /**
     * @brief A getter that gets the current score of the game
     * @return The score of the game
     */
    public int getScore() {
        return score;
    }

    /**
     * @brief A getter that gets the best score of all games
     * @return The best score of all games
     */
    public int getBest() {
        return best;
    }

    /**
     * @brief A method that merge tiles
     * @details if 2 unmerged tiles which are going to be merged are same,
     * merge them into a new tile whose value is twice the original value
     */
    public void mergeTiles(ArrayList<Tile> arr) {
        for(int i = 0; i < arr.size() - 1; i ++) {
            Tile t1 = arr.remove(i);
            Tile t2 = arr.remove(i);
            if(t1.value == t2.value && !t1.hasMerged && !t2.hasMerged) {
                Tile t = new Tile(t1.value + t2.value);
                score += t.value;
                best = Math.max(score, best);
                if(t.value == 2048) {
                    gameOver = true;
                    win = true;
                }
                t.hasMerged = true;
                arr.add(i, t);
                i --;
            } else {
                arr.add(i, t1);
                arr.add(i + 1, t2);
            }
        }

        for(Tile t : arr) {
            t.hasMerged = false;
        }
    }

    /**
     * @brief A method that move all tiles left
     * @details move all tiles left and merge tiles if possible,
     * then add a random tile
     */
    public void moveLeft() {
        if(!possibleLeft()) return;

        for (int row = 0; row < tiles.length; row++) {
            ArrayList<Tile> rowTiles = new ArrayList<>();

            for (int col = 0; col < tiles.length; col++) {
                if (tiles[row][col] != null) rowTiles.add(tiles[row][col]);
                tiles[row][col] = null;
            }

            mergeTiles(rowTiles);

            for (int col = 0; col < tiles.length && rowTiles.size() > 0; col++) {
                tiles[row][col] = rowTiles.remove(0);
            }
        }

        addRandomTile();
    }

    /**
     * @brief A method that move all tiles right
     * @details move all tiles right and merge tiles if possible,
     * then add a random tile
     */
    public void moveRight() {
        if(!possibleRight()) return;

        for (int row = 0; row < tiles.length; row++) {
            ArrayList<Tile> rowTiles = new ArrayList<>();

            for (int col = 0; col < tiles.length; col++) {
                if (tiles[row][col] != null) rowTiles.add(tiles[row][col]);
                tiles[row][col] = null;
            }

            mergeTiles(rowTiles);

            for (int col = tiles.length - 1; col >= 0 && rowTiles.size() > 0; col--) {
                tiles[row][col] = rowTiles.remove(rowTiles.size() - 1);
            }
        }

        addRandomTile();
    }

    /**
     * @brief A method that move all tiles up
     * @details move all tiles up and merge tiles if possible,
     * then add a random tile
     */
    public void moveUp() {
        if(!possibleUp()) return;

        for (int col = 0; col < tiles.length; col++) {
            ArrayList<Tile> colTiles = new ArrayList<>();

            for (int row = 0; row < tiles.length; row++) {
                if (tiles[row][col] != null) colTiles.add(tiles[row][col]);
                tiles[row][col] = null;
            }

            mergeTiles(colTiles);

            for (int row = 0; row < tiles.length && colTiles.size() > 0; row++) {
                tiles[row][col] = colTiles.remove(0);
            }
        }

        addRandomTile();
    }

    /**
     * @brief A method that move all tiles down
     * @details move all tiles down and merge tiles if possible,
     * then add a random tile
     */
    public void moveDown() {
        if(!possibleDown()) return;

        for (int col = 0; col < tiles.length; col++) {
            ArrayList<Tile> colTiles = new ArrayList<>();

            for (int row = 0; row < tiles.length; row++) {
                if (tiles[row][col] != null) colTiles.add(tiles[row][col]);
                tiles[row][col] = null;
            }

            mergeTiles(colTiles);

            for (int row = tiles.length - 1; row >= 0 && colTiles.size() > 0; row--) {
                tiles[row][col] = colTiles.remove(colTiles.size() - 1);
            }
        }

        addRandomTile();
    }

    /**
     * @brief check if it is possible to move all tiles left
     * @return true if it is possible, false otherwise
     */
    public boolean possibleLeft() {
        for (Tile[] row : tiles) {
            for (int i = 1; i < row.length; i++) {
                if (row[i - 1] == null && row[i] != null) return true;
                if (row[i - 1] != null && row[i] != null) {
                    if (row[i - 1].value == row[i].value) return true;
                }
            }
        }
        return false;
    }

    /**
     * @brief check if it is possible to move all tiles right
     * @return true if it is possible, false otherwise
     */
    public boolean possibleRight() {
        for (Tile[] row : tiles) {
            for (int i = row.length - 1 - 1; i >= 0; i--) {
                if (row[i + 1] == null && row[i] != null) return true;
                if (row[i + 1] != null && row[i] != null) {
                    if (row[i + 1].value == row[i].value) return true;
                }
            }
        }
        return false;
    }

    /**
     * @brief check if it is possible to move all tiles up
     * @return true if it is possible, false otherwise
     */
    public boolean possibleUp() {
        for (int col = 0; col < tiles.length; col++) {
            for (int row = 1; row < tiles.length; row++) {
                if (tiles[row - 1][col] == null && tiles[row][col] != null) return true;
                if (tiles[row - 1][col] != null && tiles[row][col] != null) {
                    if (tiles[row - 1][col].value == tiles[row][col].value) return true;
                }
            }
        }
        return false;
    }

    /**
     * @brief check if it is possible to move all tiles down
     * @return true if it is possible, false otherwise
     */
    public boolean possibleDown() {
        for (int col = 0; col < tiles.length; col++) {
            for (int row = tiles.length - 1 - 1; row >= 0; row--) {
                if (tiles[row + 1][col] == null && tiles[row][col] != null) return true;
                if (tiles[row + 1][col] != null && tiles[row][col] != null) {
                    if (tiles[row + 1][col].value == tiles[row][col].value) return true;
                }
            }
        }
        return false;
    }

    /**
     * @brief A method mark game over if tiles cannot move in any position
     * @details change gameOver to true, win to false
     */
    public void isGameOver() {
        if(!possibleDown() && !possibleUp() && !possibleLeft() && !possibleRight() ) {
            gameOver = true;
            win = false;
        }
    }

}