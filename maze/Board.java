package maze;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class Board {
    private final int height;
    final String WALL = "\u2588\u2588";
    final String EMPTY = "  ";
    int[][] board;
    int nodesCnt;
    static Board maze;

    Board(int height) {
        this.height = height;
        this.board = new int[height][height];
        this.nodesCnt = ((height - 1) / 2) * ((height - 1) / 2);
        for (int[] row : board)
            Arrays.fill(row, 1);
    }

    Board(int[][] ints) {
        this.height = ints.length;
        this.board = ints;
        this.nodesCnt = ((ints.length - 1) / 2) * ((ints.length - 1) / 2);
    }

    static Board getMaze(int height) {
        if (maze == null) {
            maze = new Board(height);
        }
        return maze;
    }

    void printBoard() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < height; j++) {
                System.out.print(board[i][j] == 1 ? WALL : EMPTY);
            }
            System.out.println();
        }
    }

    void saveBoard(Writer writer) {
        try {
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < height; j++) {
                    writer.write(board[i][j] == 1 ? WALL + "," : EMPTY + ",");
                }
                writer.write("\n");
            }
        } catch (IOException ignored) {}
    }

    public void setNode(int v, int h, ArrayList<Integer> directions) {
        board[v][h] = 0;
        nodesCnt--;

        while (nodesCnt * directions.size() > 0) {
            int dir = directions.remove(new Random().nextInt(directions.size()));
            if (dir == 0 && h > 2 && board[v][h - 2] != 0) {
                board[v][h - 1] = 0;
                setNode(v, h - 2, new ArrayList<>(List.of(0, 1, 3)));
            } else if (dir == 1 && v > 2 && board[v - 2][h] != 0) {
                board[v - 1][h] = 0;
                setNode(v - 2, h, new ArrayList<>(List.of(0, 1, 2)));
            } else if (dir == 2 && h < height - 3 && board[v][h + 2] != 0) {
                board[v][h + 1] = 0;
                setNode(v, h + 2, new ArrayList<>(List.of(1, 2, 3)));
            } else if (dir == 3 && v < height - 3 && board[v + 2][h] != 0) {
                board[v + 1][h] = 0;
                setNode(v + 2, h, new ArrayList<>(List.of(0, 2, 3)));
            }
        }
    }

    public void setExits() {
        Function<Integer, Integer> rnd = x -> {
            int y = new Random().nextInt(height - 2) + 1;
            if (board[y][x] == 1) y = y > 1 ? y - 1 : y + 1;
            return y;
        };

        board[rnd.apply(1)][0] = 0;
        if (height % 2 == 1) {
            board[rnd.apply(height - 2)][height - 1] = 0;
        } else {
            int v = rnd.apply(height - 3);
            board[v][height - 2] = 0;
            board[v][height - 1] = 0;
        }
    }
}