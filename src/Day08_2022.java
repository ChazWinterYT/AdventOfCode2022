import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.sun.security.jgss.GSSUtil;

public class Day08_2022  {
    //declaring this 2D array outside the methods to make the rest of the code easier to read
    private int[][] trees;
    //now instead of referencing trees[row][column] over and over,
    //we can just refer to the rows and columns themselves.

    //part one
    public int visibleTrees(String filePath, int part) throws FileNotFoundException {
        File file = new File(filePath);
        Scanner scanner = new Scanner(file);
        //Collect all the rows of numbers into a List of Strings
        List<String> treeRowsList = new ArrayList<>();
        while (scanner.hasNextLine()) {
            treeRowsList.add(scanner.nextLine());
        }
        scanner.close();
        // Convert the List to an array
        String[] treeRows = treeRowsList.toArray(new String[0]);

        //Get the digits from each string and make a 2D array of ints
        trees = new int[treeRows.length][treeRows[0].length()];
        for (int i = 0; i < treeRows.length; i++) {
            for (int j = 0; j < treeRows[i].length(); j++) {
                trees[i][j] = treeRows[i].charAt(j) - '0';
                //subtracting char '0' results in the actual integer value we need
            }
        }

        if (part == 1) {
            int visibleTrees = 0;
            //check if a tree is visible from any of the four directions.
            //it only needs to be visible from one direction to count, so as soon
            //as one method returns true, stop checking that tree, so it only counts once!
            for (int row = 0; row < trees.length; row++) {
                for (int col = 0; col < trees[row].length; col++) {
                    if (isVisible(row,col,"left")) {
                        visibleTrees++;
                        continue;
                    } else if (isVisible(row,col,"right")) {
                        visibleTrees++;
                        continue;
                    } else if (isVisible(row,col,"top")) {
                        visibleTrees++;
                        continue;
                    } else if (isVisible(row,col,"bottom")) {
                        visibleTrees++;
                        continue;
                    }
                }
            }
            return visibleTrees;

        } else if (part == 2) {
            int maxTreeScore = 0;
            for (int row = 0; row < trees.length; row++) {
                for (int col = 0; col < trees[row].length; col++) {
                    int currentTreeScore = treeScore(row,col,"up")
                                          * treeScore(row,col,"down")
                                          * treeScore(row,col,"left")
                                          * treeScore(row,col,"right");
                    maxTreeScore = (Math.max(maxTreeScore, currentTreeScore));
                }
            }
            return maxTreeScore;
        }
        return -1;
    }

    //helper method for isVisible determination, from all four directions
    private boolean isVisible(int row, int col, String fromDirection) {
        int start, end, increment;
        boolean isRow;
        switch (fromDirection) {
            case "left":
                start = 0;
                end = col;
                increment = 1;
                isRow = true;
                break;
            case "right":
                start = col + 1;
                end = trees[row].length;
                increment = 1;
                isRow = true;
                break;
            case "top":
                start = 0;
                end = row;
                increment = 1;
                isRow = false;
                break;
            case "bottom":
                start = row + 1;
                end = trees.length;
                increment = 1;
                isRow = false;
                break;
            default:
                throw new IllegalArgumentException("Invalid direction: " + fromDirection +
                        ". Only left/right/top/bottom allowed!");
        }
        for (int i = start; i < end; i += increment) {
            if (isRow) {
                if (trees[row][col] <= trees[row][i]) return false;
            } else {
                if (trees[row][col] <= trees[i][col]) return false;
            }
        }
        return true;
    }

    //helper method to determine the number of trees visible in each direction
    private int treeScore(int row, int col, String direction) {
        int start, end, increment;
        boolean isRow;
        //determine the conditions of the for loop depending on direction
        switch (direction) {
            case "left":
                if (col == 0) return 0;
                start = col - 1;
                end = -1;
                increment = -1;
                isRow = true;
                break;
            case "right":
                if (col == trees[row].length - 1) return 0;
                start = col + 1;
                end = trees[row].length;
                increment = 1;
                isRow = true;
                break;
            case "up":
                if (row == 0) return 0;
                start = row - 1;
                end = -1;
                increment = -1;
                isRow = false;
                break;
            case "down":
                if (row == trees.length - 1) return 0;
                start = row + 1;
                end = trees.length;
                increment = 1;
                isRow = false;
                break;
            default:
                throw new IllegalArgumentException("Invalid direction: " + direction +
                        ". Only left/right/up/down allowed!");
        }
        int numTrees = 0;
        //start at the current tree, and count forward until you
        //reach a tree that is the same height or taller
        for (int i = start; i != end; i += increment) {
            if (isRow) {
                if (trees[row][col] > trees[row][i]) {
                    numTrees++;
                } else {
                    numTrees++;
                    break;
                }
            } else {
                if (trees[row][col] > trees[i][col]) {
                    numTrees++;
                } else {
                    numTrees++;
                    break;
                }
            }
        }
        return numTrees;
    }
}
