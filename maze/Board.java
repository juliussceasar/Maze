package maze;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class Board {
    final int height;
    final int width;
    final int[][] board;
    final String WALL = "\u2588\u2588";
    final String EMPTY = "  ";
    int nodesCnt;

    public Board(int height, int width) {
        this.height = height;
        this.width = width;
        board = new int[height][width];
        nodesCnt = ((height - 1) / 2) * ((width - 1) / 2);
        for (int[] row : board)
            Arrays.fill(row, 1);
    }

    void printBoard() {
        System.out.println();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                System.out.print(board[i][j] == 1 ? WALL : EMPTY);
            }
            System.out.println();
        }
    }

    public void setNode(int v, int h, ArrayList<Integer> directions) {
        board[v][h] = 0;
        nodesCnt--;

        while (nodesCnt * directions.size() > 0) {
            int dir = directions.remove(new Random().nextInt(directions.size()));
//            debugBoard(v, h, dir, nodesCnt);  // TODO
            if (dir == 0 && h > 2 && board[v][h - 2] != 0) {
                board[v][h - 1] = 0;
                setNode(v, h - 2, new ArrayList<>(List.of(0, 1, 3)));
            } else if (dir == 1 && v > 2 && board[v - 2][h] != 0) {
                board[v - 1][h] = 0;
                setNode(v - 2, h, new ArrayList<>(List.of(0, 1, 2)));
            } else if (dir == 2 && h < width - 3 && board[v][h + 2] != 0) {
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
        if (width % 2 == 1) {
            board[rnd.apply(width - 2)][width - 1] = 0;
        } else {
            int v = rnd.apply(width - 3);
            board[v][width - 2] = 0;
            board[v][width - 1] = 0;
        }
    }

//    private void debugBoard(int v, int h, int dir, int cnt) {
//        printBoard();
//        String showDir = dir == 0 ? "<" : dir == 1 ? "^" : dir == 2 ? ">" : "v";
//        System.out.printf("(%d, %d) %s %d\n", v, h, showDir, cnt);
//
//        try {
//            java.util.concurrent.TimeUnit.MILLISECONDS.sleep(1000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//    }
}