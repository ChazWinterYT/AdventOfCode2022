import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Day04_2022 {
    //part one
    public int total(String filePath, int part)  throws FileNotFoundException {
        File file = new File(filePath);
        Scanner scanner = new Scanner(file);
        List<String> allValues = new ArrayList<>();
        int total = 0;
        while (scanner.hasNextLine()) {
            allValues.clear();
            String input = scanner.nextLine();
            //Split the input with the comma in the middle
            String[] firstSplit = input.split(",");
            //then split into a 4 value array using the dashes
            for (String x : firstSplit) {
                String[] nums = x.split("-");
                allValues.addAll(Arrays.asList(nums));
            }
            if (part == 1) {
                if (isWithin(allValues)) total++;
            } else if (part == 2) {
                if (overlap(allValues)) total++;
            }
        }
        scanner.close();
        return total;
    }
    private boolean isWithin(List<String> nums) {
        int x1 = Integer.parseInt(nums.get(0));
        int x2 = Integer.parseInt(nums.get(1));
        int y1 = Integer.parseInt(nums.get(2));
        int y2 = Integer.parseInt(nums.get(3));
        return (x1 <= y1 && x2 >= y2) || (y1 <= x1 && y2 >= x2);
    }
    private boolean overlap(List<String> nums) {
        int x1 = Integer.parseInt(nums.get(0));
        int x2 = Integer.parseInt(nums.get(1));
        int y1 = Integer.parseInt(nums.get(2));
        int y2 = Integer.parseInt(nums.get(3));
        return !(x2 < y1 || y2 < x1);
    }
}
