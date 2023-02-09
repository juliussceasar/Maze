package maze;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Menu {
    public void getMenu() {
        print("=== Menu ===\n" +
                "1. Generate a new maze\n" +
                "2. Load a maze\n" +
                "0. Exit\n");
        chooseFromMenu();
    }

    public void getExtendedMenu(Board maze) {
        print("=== Menu ===\n" +
                "1. Generate a new maze\n" +
                "2. Load a maze\n" +
                "3. Save the maze\n" +
                "4. Display the maze\n" +
                "0. Exit\n");
        chooseFromExtMenu(maze);
    }

    public void chooseFromMenu() {
        Scanner scanner = new Scanner(System.in);
        int number = scanner.nextInt();
        Board maze;
        switch (number) {
            case 1:
                System.out.println("Enter the size of a new maze");
                String size = new Scanner(System.in).next();
                maze = generateMaze(size);
                getExtendedMenu(maze);
                break;
            case 2:
                maze = loadMaze();
                getExtendedMenu(maze);
                break;
            case 0:
                System.out.println("Bye!");
                System.exit(1);
        }
    }

    public void chooseFromExtMenu(Board maze) {
        Scanner scanner = new Scanner(System.in);
        int number = scanner.nextInt();

        switch (number) {
            case 1:
                System.out.println("Enter the size of a new maze");
                String size = new Scanner(System.in).next();
                maze = generateMaze(size);
                getExtendedMenu(maze);
            case 2:
                maze = loadMaze();
                getExtendedMenu(maze);
                break;
            case 3:
                saveMaze(maze);
                getExtendedMenu(maze);
                break;
            case 4:
                displayMaze(maze);
                getExtendedMenu(maze);
                break;
            case 0:
                System.exit(1);
                break;
        }
    }
    private void print(String strng) {
        System.out.println(strng);
    }

    private Board generateMaze(String size) {
        Board maze = new Board(Integer.parseInt(size));
        maze.setNode(1, 1, new ArrayList<>(List.of(0, 1, 2, 3)));
        maze.setExits();
        maze.printBoard();
        return maze;
    }

    private Board loadMaze() {
        Scanner scanner = new Scanner(System.in);
        String pathStr = scanner.next();
        Path path = Paths.get("/home/yulia/IdeaProjects/Maze Runner/Maze Runner/task/src/maze/" + pathStr);
        try {
            List<String> file = Files.readAllLines(path);
            file = file.stream().map(line -> line + "\n").collect(Collectors.toList());

            int[][] ints = new int[file.size()][file.size()];
            for (int line = 0; line < file.size(); line++) {
                for (int row = 0; row < file.size(); row++) {
                    String[] str = file.get(line).split(",");
                    if (Objects.equals(str[row], "██")) {
                        ints[line][row] = 1;
                    }
                }
             }
            Board maze = new Board(ints);

            return maze;
        } catch (FileNotFoundException p) {
            System.out.println("The file " + pathStr + "does not exist");
            getMenu();
            return null;
        } catch (IOException e) {
            System.out.println("Cannot load the maze. It has invalid format");
            getMenu();
            return null;
        }
    }

    private void saveMaze(Board maze) {
        Scanner scanner = new Scanner(System.in);
        String path = scanner.next();
        File file = new File("/home/yulia/IdeaProjects/Maze Runner/Maze Runner/task/src/maze/" + path);
        try (PrintWriter writer = new PrintWriter(file)) {
            maze.saveBoard(writer);
        } catch (FileNotFoundException e) {}
    }
    private void displayMaze(Board maze) {
        maze.printBoard();
    }


}

