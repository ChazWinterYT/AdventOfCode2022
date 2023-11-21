import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Day09_2022 {
    //declare the 2D array outside all methods to make the code easier to read
    //use the text file and setGrid method to figure out how large to make the array first
    int[][] grid = new int[500][250];
    //test case uses a different grid size
    //int[][] grid = new int[28][36];

    //part one
    public int visitedPoints(String filePath) throws FileNotFoundException {
        File file = new File(filePath);
        Scanner scanner = new Scanner(file);
        //get the directions and number of spaces from the input file
        ArrayList<Character> direction = new ArrayList<>();
        ArrayList<Integer> steps = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();
            String[] parts = input.split(" ");
            direction.add(input.charAt(0));
            String currentSteps = parts[1];
            steps.add(Integer.parseInt(currentSteps));
        }

        /*
        determine how large the 2D array should be
        this method will be commented out once the size of the grid has been determined
        Result was:
        Left: 140   Right: 86
        Up:     7   Down: 477
        Based on these values, a 2D array size [500][250] was created
        To avoid going out of bounds, start the head at position [10][150]
        */
        //setGrid(direction, steps);

        //set the starting point for the head and the tail, and mark it visited
        int headRow = 10, headCol = 150;
        int tailRow = 10, tailCol = 150;

        clearGrid();
        visit(tailRow, tailCol);
        //create the movement logic. I'm doing it in here to avoid scope issues
        //when the tail is within one space of the head, it does nothing
        //when it gets more than 1 space away, it snaps directly behind the head
        for (int i = 0; i < steps.size(); i++) {
            switch (direction.get(i)) {
                case 'U':
                    //head going up: row decreases, col doesn't change
                    for (int numSteps = 0; numSteps < steps.get(i); numSteps++) {
                        headRow--;
                        if (isTooFar(headRow, headCol, tailRow, tailCol)) {
                            tailRow = headRow + 1;
                            tailCol = headCol;
                            visit(tailRow, tailCol);
                        }
                    }
                    break;
                case 'D':
                    //head going down: row increases, col doesn't change
                    for (int numSteps = 0; numSteps < steps.get(i); numSteps++) {
                        headRow++;
                        if (isTooFar(headRow, headCol, tailRow, tailCol)) {
                            tailRow = headRow - 1;
                            tailCol = headCol;
                            visit(tailRow, tailCol);
                        }
                    }
                    break;
                case 'L':
                    //head going left: row doesn't change, col decreases
                    for (int numSteps = 0; numSteps < steps.get(i); numSteps++) {
                        headCol--;
                        if (isTooFar(headRow, headCol, tailRow, tailCol)) {
                            tailCol = headCol + 1;
                            tailRow = headRow;
                            visit(tailRow, tailCol);
                        }
                    }
                    break;
                case 'R':
                    //head going right: row doesn't change, col increases
                    for (int numSteps = 0; numSteps < steps.get(i); numSteps++) {
                        headCol++;
                        if (isTooFar(headRow, headCol, tailRow, tailCol)) {
                            tailCol = headCol - 1;
                            tailRow = headRow;
                            visit(tailRow, tailCol);
                        }
                    }
                    break;
                default:
                    System.out.println("Invalid direction: " + direction.get(i) + " is not a valid direction!");
            }
        }
        //count the number of visited points
        int pointsVisited = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 1) pointsVisited++;
            }
        }
        return pointsVisited;
    }

    //part two
    //make an array of the head and the nine tails. each tail has a row and a col
    int[][] tails = new int[10][2];
    /*
    example: tails[0][0] is the head's row, tails[0][1] is the head's column
             tails[9][0] is the 9th tail's row, tails[9][1] is the 9th tails column
             row 5, col 10 -> tails[9][0] == 5, tails[9][1] == 10
     */
    public int visitedPoints2(String filePath) throws FileNotFoundException {
        File file = new File(filePath);
        Scanner scanner = new Scanner(file);
        //get the directions and number of spaces from the input file
        ArrayList<Character> direction = new ArrayList<>();
        ArrayList<Integer> steps = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();
            String[] parts = input.split(" ");
            direction.add(input.charAt(0));
            String currentSteps = parts[1];
            steps.add(Integer.parseInt(currentSteps));
        }
        scanner.close();
        //set the starting point for the head and the tail, and mark it visited
        int headRow = 10, headCol = 150;
        //test case uses different starting values
        //int headRow = 15, headCol = 11;

        for (int i = 0; i < tails.length; i++) {
            tails[i][0] = headRow;
            tails[i][1] = headCol;
        }
        clearGrid();
        visit9();
        //create the movement logic. start by iterating through each instruction
        for (int i = 0; i < steps.size(); i++) {
            //within each instruction, get the direction and number of steps
            //perform the head movement j times, where j is the number of steps
            for (int j = 0; j < steps.get(i); j++) {
                switch (direction.get(i)) {
                    case 'U':
                        tails[0][0]--;
                        break;
                    case 'D':
                        tails[0][0]++;
                        break;
                    case 'L':
                        tails[0][1]--;
                        break;
                    case 'R':
                        tails[0][1]++;
                        break;
                    default:
                        System.out.println("Invalid direction: " + direction.get(i) + " is not a valid direction!");
                }
                //every time the head moves, check if the next tail isTooFar for every tail
                //if it is, moveTail to its new position
                for (int tail = 1; tail < tails.length; tail++) {
                    if (isTooFar(tails[tail-1][0],tails[tail-1][1],tails[tail][0],tails[tail][1])) {
                        int[] movement = moveTail(tails[tail-1][0],tails[tail-1][1],tails[tail][0],tails[tail][1]);
                        tails[tail][0] = movement[0];
                        tails[tail][1] = movement[1];
                    }
                }
                //finally, let the 9th tail visit its current location on the grid
                visit9();
            }
        }
        //count the number of visited points
        int pointsVisited = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 1) pointsVisited++;
            }
        }
        return pointsVisited;
    }
    //helper method to mark spaces on the 2D array as visited
    private void visit(int row, int col) {
        grid[row][col] = 1;
    }
    //same as above, but specifically for tail 9
    private void visit9() {
        grid[tails[9][0]][tails[9][1]] = 1;
    }

    private void clearGrid() {
        //set the grid to all zeroes
        for (int[] row : grid) Arrays.fill(row, 0);
    }

    //helper method to determine if the tail has been left too far behind the head
    private boolean isTooFar(int headRow, int headCol, int tailRow, int tailCol) {
        return (Math.abs(headRow - tailRow) > 1 || Math.abs(headCol - tailCol) > 1);
    }
    //helper method to move the tails
    private int[] moveTail(int headRow, int headCol, int tailRow, int tailCol) {
        int[]values = new int[2];
        //horizontal movement only
        if (headRow == tailRow) {
            tailCol = (headCol > tailCol) ? ++tailCol : --tailCol;
            //in ternary expressions, you have to increment the value before returning it
        }
        //vertical movement only
        if (headCol == tailCol) {
            tailRow = (headRow > tailRow) ? ++tailRow : --tailRow;
        }
        //diagonal movement
        if (headRow != tailRow && headCol != tailCol) {
            tailRow = (headRow > tailRow) ? ++tailRow : --tailRow;
            tailCol = (headCol > tailCol) ? ++tailCol : --tailCol;
        }
        values[0] = tailRow;
        values[1] = tailCol;
        return values;
    }
    private void setGrid(ArrayList<Character> direction, ArrayList<Integer> steps) {
        //this is a count of the number of steps in each direction,
        //in order to figure out how large the array should be
        int up = 0, down = 0, left = 0, right = 0;
        int maxUp = 0, maxDown = 0, maxLeft = 0, maxRight = 0;
        for (int i = 0; i < direction.size(); i++) {
            switch (direction.get(i)) {
                case 'U' :
                    up += steps.get(i);
                    down -= steps.get(i);
                    break;
                case 'D' :
                    up -= steps.get(i);
                    down += steps.get(i);
                    break;
                case 'L' :
                    left += steps.get(i);
                    right -= steps.get(i);
                    break;
                case 'R' :
                    left -= steps.get(i);
                    right += steps.get(i);
                    break;
            }
            maxUp = Math.max(maxUp, up);
            maxDown = Math.max(maxDown, down);
            maxLeft = Math.max(maxLeft, left);
            maxRight = Math.max(maxRight, right);
        }
        System.out.println("Expected grid size:");
        System.out.println("Left: " + maxLeft);
        System.out.println("Right: " + maxRight);
        System.out.println("Up: " + maxUp);
        System.out.println("Down: " + maxDown);
    }
    //method to print the array for debugging purposes
    private void printArray(int[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            System.out.print("Row " + i + ": ");
            int points = 0;
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 1) points++;
            }
            System.out.print(points + " points visited ");
            System.out.println(Arrays.toString(grid[i]));
        }
        System.out.println("");
    }
}
