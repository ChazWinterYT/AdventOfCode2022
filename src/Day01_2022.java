import java.io.File;
import java.io.FileNotFoundException;
import java.util.PriorityQueue;
import java.util.Scanner;


public class Day01_2022 {
    //part 1 solution
    public int getMaxSum(String filePath) throws FileNotFoundException {
        int elfSum = 0;
        int maxSum = 0;

        File inputFile = new File(filePath);
        Scanner scanner = new Scanner(inputFile);
        while (scanner.hasNextLine()) {
            String currentNum = scanner.nextLine();
            try {
                elfSum += Integer.parseInt(currentNum);
            } catch (NumberFormatException e) {

                maxSum = Math.max(elfSum, maxSum);
                elfSum = 0;
            }
        }
        //loop ends before checking the last sum. check it here
        maxSum = Math.max(maxSum, elfSum);
        scanner.close();
        return maxSum;
    }
    //part 2 solution
    public int getTopThree(String filePath) throws FileNotFoundException {
        int elfSum = 0;
        int numTopElves = 3;
        PriorityQueue<Integer> topThree = new PriorityQueue<>(numTopElves);
        File inputFile = new File(filePath);
        Scanner scanner = new Scanner(inputFile);
        while (scanner.hasNextLine()) {
            String currentNum = scanner.nextLine();
            try {
                elfSum += Integer.parseInt(currentNum);
            } catch (NumberFormatException e) {
                //if the Elf sum beats the top scores, slide the top scores down the rankings
                if (topThree.size() < numTopElves) {
                    topThree.add(elfSum);
                } else if (elfSum > topThree.peek()) {
                    topThree.poll();
                    topThree.add(elfSum);
                }
                elfSum = 0;
                //System.out.printf("The top three scores are %d, %d, and %d. elfSum is %d%n", topElf, elfTwo, elfThree, elfSum);
            }
        }
        //loop ends before checking the last sum. check it here
        if (elfSum > topThree.peek()) {
            topThree.poll();
            topThree.add(elfSum);
        }
        scanner.close();
        //add the top three scores and return the answer
        int totalScore = 0;
        while (!topThree.isEmpty()) {
            totalScore += topThree.poll();
        }
        return totalScore;
    }
}
