import java.io.IOException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        Day11_2022 day11 = new Day11_2022();
        long monkey = day11.monkeyBusiness("src/resources/input2022-11", 1);
        System.out.println(monkey);
        long monkey2 = day11.monkeyBusiness("src/resources/input2022-11", 2);
        System.out.println(monkey2);

        /*


        Day10_2022 day10 = new Day10_2022();
        int signal = day10.signalStrength("src/resources/input2022-10");
        System.out.println(signal);

        Day09_2022 day09 = new Day09_2022();
        int total = day09.visitedPoints("src/resources/input2022-09");
        System.out.println(total);
        int total2 = day09.visitedPoints2("src/resources/input2022-09");
        System.out.println(total2);

        Day08_2022 day08 = new Day08_2022();
        int position = day08.visibleTrees("src/resources/input2022-08", 1);
        System.out.println(position);
        int score = day08.visibleTrees("src/resources/input2022-08", 2);
        System.out.println(score);

        Day07_2022 day07 = new Day07_2022();
        int position = day07.getSize("src/resources/input2022-07");
        System.out.println(position);
        int position2 = day07.getSize2("src/resources/input2022-07");
        System.out.println(position2);

        Day06_2022 day06 = new Day06_2022();
        int position = day06.getPosition("src/resources/input2022-06", 4);
        System.out.println(position);
        int position2 = day06.getPosition("src/resources/input2022-06", 14);
        System.out.println(position2);

        Day05_2022 day05 = new Day05_2022();
        String top = day05.topStack("src/resources/input2022-05", 1);
        System.out.println(top);
        String top2 = day05.topStack("src/resources/input2022-05", 2);
        System.out.println(top2);

        Day04_2022 day04 = new Day04_2022();
        int part1 = day04.total("src/resources/input2022-04", 1);
        System.out.println(part1);
        int part2 = day04.total("src/resources/input2022-04", 2);
        System.out.println(part2);

        Day03_2022 day03 = new Day03_2022();
        int totalPriorities = day03.totalPriorities("src/resources/input2022-03");
        System.out.println(totalPriorities);
        int totalBadges = day03.totalBadges("src/resources/input2022-03");
        System.out.println(totalBadges);

        Day02_2022 day02 = new Day02_2022();
        int score1 = day02.getScore("src/resources/input2022-02", 1);
        System.out.println(score1);
        int score2 = day02.getScore("src/resources/input2022-02", 2);
        System.out.println(score2);

        Day01_2022 day01 = new Day01_2022();
        int maxSum = day01.getMaxSum("src/resources/input2022-01");
        System.out.println(maxSum);
        int topThree = day01.getTopThree("src/resources/input2022-01");
        System.out.println(topThree);
         */
    }
}
