import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

public class Day07_2022 {
    //part one
    public int getSize(String filePath)  throws FileNotFoundException {
        File file = new File(filePath);
        Scanner scanner = new Scanner(file);
        int totalSize = 0;
        //Stack to keep track of directories
        Stack<String> currentPath = new Stack<>();
        //HashMap to store directory sizes
        HashMap<String, Integer> directorySize = new HashMap<>();
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();
            // deal with $ cd commands
            if (input.startsWith("$ cd")) {
                //name of directory is after the 2nd space
                String directory = input.split(" ")[2];
                if (directory.equals("..")) {
                    currentPath.pop();
                } else if (directory.equals("/")) {
                    currentPath.clear();
                } else {
                    currentPath.push(directory);
                }
            }  //deal with file sizes within a directory
            else if (Character.isDigit(input.charAt(0))) {
                //the file size is the number before the space
                int size = Integer.parseInt(input.split(" ")[0]);
                //the file size should be applied to the current directory,
                //AND every directory above it (ie, below it on the stack)
                Iterator<String> iterator = currentPath.iterator();
                String pathSoFar = "";
                //iterate through directories on the stack
                while (iterator.hasNext()) {
                    String directory = iterator.next();
                    //adding a slash between directory names for aesthetics
                    pathSoFar += (pathSoFar.isEmpty() ? "" : "/") + directory;
                    //add the size of this file to every directory on the stack.
                    //start at zero if it's the first file in this directory
                    directorySize.put(pathSoFar, directorySize.getOrDefault(pathSoFar, 0) + size);
                }
            }
        }
        scanner.close();
        //add up all the directories that total less than 100000 in size
        for (int size : directorySize.values()) {
            if (size < 100000) totalSize += size;
        }
        return totalSize;
    }

    //part two
    public int getSize2(String filePath)  throws FileNotFoundException {
        File file = new File(filePath);
        Scanner scanner = new Scanner(file);
        //Stack to keep track of directories
        Stack<String> currentPath = new Stack<>();
        //HashMap to store directory sizes
        HashMap<String, Integer> directorySize = new HashMap<>();
        int outermostDirectorySize = 0;
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();
            // deal with $ cd commands
            if (input.startsWith("$ cd")) {
                //name of directory is after the 2nd space
                String directory = input.split(" ")[2];
                if (directory.equals("..")) {
                    currentPath.pop();
                } else if (directory.equals("/")) {
                    currentPath.clear();
                } else {
                    currentPath.push(directory);
                }
            }  //deal with file sizes within a directory
            else if (Character.isDigit(input.charAt(0))) {
                //the file size is the number before the space
                int size = Integer.parseInt(input.split(" ")[0]);
                //if the file is in the outermost directory, we won't see it later
                //let's account for it now
                if (currentPath.isEmpty()) {
                    outermostDirectorySize += size;
                }
                //the file size should be applied to the current directory,
                //AND every directory above it (ie, below it on the stack)
                Iterator<String> iterator = currentPath.iterator();
                String pathSoFar = "";
                //iterate through directories on the stack
                while (iterator.hasNext()) {
                    String directory = iterator.next();
                    //adding a slash between directory names for aesthetics
                    pathSoFar += (pathSoFar.isEmpty() ? "" : "/") + directory;
                    //add the size of this file to every directory on the stack.
                    //start at zero if it's the first file in this directory
                    directorySize.put(pathSoFar, directorySize.getOrDefault(pathSoFar, 0) + size);
                }
            }
        }
        scanner.close();
        //get the size of the outermost directory
        //I never recorded the outermost directory,
        //so I need the total sizes of the directories without a "/"
        int totalSize = 0;
        for (Map.Entry<String, Integer> entry : directorySize.entrySet()) {
            if (!entry.getKey().contains("/")) {
                totalSize += entry.getValue();
            }
        }
        //don't forget to account for the files in the outermost directory!
        totalSize += outermostDirectorySize;
        //unused space is 70,000,000 minus the used space (totalSize)
        int unusedSpace = 70000000 - totalSize;
        //Create an ArrayList of the values in the HashMap so we can sort them
        List<Integer> sizeList = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : directorySize.entrySet()) {
            sizeList.add(entry.getValue());
        }
        Collections.sort(sizeList);
        //we need to delete the smallest directory that increases unused space to 30,000,000
        for (int i : sizeList) {
            if (i + unusedSpace >= 30000000) {
                return i;
            }
        }
        //if the loop survives, then no directory will free up enough space
        //(impossible, but we need to account for it)
        return -1;
    }
}
