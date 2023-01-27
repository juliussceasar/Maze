package maze;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Please, enter the size of a maze");
        String[] size = new Scanner(System.in).nextLine().split(" ");
        new Main().run(size);
    }

    private void run(String[] size) {
        Board maze = new Board(Integer.parseInt(size[0]), Integer.parseInt(size[1]));
        maze.setNode(1, 1, new ArrayList<>(List.of(0, 1, 2, 3)));
        maze.setExits();
        maze.printBoard();
    }
}