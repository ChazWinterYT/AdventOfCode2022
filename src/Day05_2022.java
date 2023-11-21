import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;
import java.util.Stack;

public class Day05_2022 {
    public String topStack(String filePath, int part)  throws FileNotFoundException {
        final int NUM_STACKS = 9;
        File file = new File(filePath);
        Scanner scanner = new Scanner(file);
        StringBuilder sb = new StringBuilder();
        //make an array of 9 deques
        Deque<Character>[] stacks = new Deque[NUM_STACKS];
        for (int i = 0; i < NUM_STACKS; i++) {
            stacks[i] = new ArrayDeque<>();
        }
        //get the stacks from the file and add them to the Deque array
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.contains(" 1   2   3")) break;
            //start from charAt 1, and count every 4th char until the end of the line
            for (int i = 1; i < NUM_STACKS * 4; i += 4) {
                if (line.charAt(i) != ' ') {
                    stacks[i / 4].addLast(line.charAt(i));
                    //divide by 4 to convert charAt positions back to indexes
                }
            }
        }
        //there's a blank line before the next set of instructions
        scanner.nextLine();
        //make an array with the 3 values representing the number of moves, and the from/to stack locations
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();
            String[] parts = input.split(" ");
            int numBoxes = Integer.parseInt(parts[1]);
            //the stacks in the input are 1-indexed. Subtract 1 to make them 0-indexed!
            int fromStack = Integer.parseInt(parts[3]) - 1;
            int toStack = Integer.parseInt(parts[5]) - 1;

            //if the number of moves is greater than the number of actual boxes,
            //then only move the actual number of boxes on the stack
            numBoxes = Math.min(numBoxes, stacks[fromStack].size());

            if (part == 1) {
                for (int i = 0; i < numBoxes; i++) {
                    stacks[toStack].push(stacks[fromStack].pop());
                }
            } else if (part == 2) {
                Stack<Character> tempStack = new Stack<>();
                for (int i = 0; i < numBoxes; i++) {
                    tempStack.push(stacks[fromStack].pop());
                }
                for (int i = 0; i < numBoxes; i++) {
                    stacks[toStack].push(tempStack.pop());
                }
            }
        }
        scanner.close();
        //build the string of boxes at the top of each stack
        for (int i = 0; i < 9; i++) {
            if (!stacks[i].isEmpty()) {
                sb.append(stacks[i].peek());
            } else { //if the stack is empty
                sb.append(" ");
            }
        }
        return sb.toString();
    }
}