package hw4.puzzle;

import java.util.ArrayList;
import java.util.List;

public class Board implements WorldState {

    private final int[][] tiles;
    private int[] zeroPos = new int[2];
    private static int[][] off = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    private List<WorldState> neighbours;

    public Board(int[][] tiles) {
        this.tiles = new int[tiles.length][tiles.length];
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                if (tiles[i][j] == 0) {
                    zeroPos[0] = i;
                    zeroPos[1] = j;
                }
                this.tiles[i][j] = tiles[i][j];
            }
        }
    }

    private boolean checkValid(int i, int j) {
        if (i < 0 || j < 0 || i >= tiles.length || j >= tiles.length)
            return false;
        return true;
    }

    public int tileAt(int i, int j) {
        if (!checkValid(i, j))
            throw new IndexOutOfBoundsException();
        return tiles[i][j];
    }

    public int size() {
        return tiles.length;
    }

    public Iterable<WorldState> neighbors() {
        neighbours = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            int zx = zeroPos[0] + off[i][0];
            int zy = zeroPos[1] + off[i][1];
            if (!checkValid(zx, zy)) continue;
            int[][] newTiles = copyTile();
            newTiles[zeroPos[0]][zeroPos[1]] = newTiles[zx][zy];
            newTiles[zx][zy] = 0;
            neighbours.add(new Board(newTiles));
        }
        return neighbours;
    }

    private int[][] copyTile() {
        int[][] newTiles = new int[tiles.length][tiles.length];
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                newTiles[i][j] = tiles[i][j];
            }
        }
        return newTiles;
    }

    public int hamming() {
        int ham = 0;
        int len = tiles.length;
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                if (tiles[i][j] == 0) continue;
                if (tiles[i][j] != i*tiles.length+j+1) {
                    ham++;
                }
            }
        }
        return ham;
    }

    public int manhattan() {
        int manh = 0;
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                if (tiles[i][j] == 0) continue;
                int sr = (tiles[i][j] - 1) / tiles.length;
                int sc = (tiles[i][j] - 1) % tiles.length;
                manh += Math.abs(i - sr) + Math.abs(j - sc);
            }
        }
        return manh;
    }

    public int estimatedDistanceToGoal() {
        return manhattan();
    }

    public boolean equals(Object y) {
        if (y == null) return false;
        if (this == y) return true;
        if (this.getClass() != y.getClass()) return false;
        Board b = (Board) y;
        if (b.size() != size()) return false;
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                if (tiles[i][j] != b.tiles[i][j])
                    return false;
            }
        }
        return true;
    }

    /** Returns the string representation of the board. 
      * Uncomment this method. */
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i,j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

}
