import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Day10_2022 {
    //part one
    public int signalStrength(String filePath) throws FileNotFoundException {
        File file = new File(filePath);
        Scanner scanner = new Scanner(file);
        //get the operations and signal values from the text file
        ArrayList<Integer> operations = new ArrayList<>();
        ArrayList<Integer> signals = new ArrayList<>();
        //inputs are 1-indexed. Add the initial case of (0, 1) so the indexes make sense
        operations.add(0);
        signals.add(1);
        int currentStrength = 1;
        int currentTime = 0;
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();
            String[] parts = input.split(" ");
            if (parts.length < 2) {
                parts = Arrays.copyOf(parts, 2);
                parts[1] = "0";
            }
            //we need to delay the addition of currentStrength by 1 cycle,
            //we'll do it by subtracting it off for 1 cycle, and then putting it back
            int delayStrength = Integer.parseInt(parts[1]);
            currentStrength += Integer.parseInt(parts[1]);
            switch (parts[0]) {
                case "noop" :
                    currentTime += 1;
                    operations.add(currentTime);
                    signals.add(currentStrength);
                    break;
                case "addx" :
                    currentTime += 1;
                    operations.add(currentTime);
                    signals.add(signals.get(signals.size()-1));
                    currentTime += 1;
                    operations.add(currentTime);
                    signals.add(currentStrength);
                    break;
            }
            //System.out.printf("%s %s -> (%d , %d)%n", parts[0], parts[1], currentTime, currentStrength);
        }
        scanner.close();
        //doing part 2 here because it just prints to the console
        //part 1 will be below it
        for (int rows = 0; rows < 6; rows++) {
            for (int cols = 0; cols < 40; cols++) {
                if (Math.abs((operations.get(40 * rows + cols) % 40) - signals.get(40 * rows + cols)) <= 1) {
                    System.out.print("#");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println("");
        }

        //now here's part one
        int totalStrength = 0;
        int[] evalTimes = {20, 60, 100, 140, 180, 220};
        for (int x : evalTimes) {
            //we want the signal "during" that cycle,
            //so it's actually the signal "before" the current signal finishes
            totalStrength += operations.get(x) * signals.get(x-1);
        }
        return totalStrength;
    }
}
