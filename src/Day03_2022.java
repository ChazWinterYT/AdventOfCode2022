import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;

public class Day03_2022 {
    //part one
    public int totalPriorities(String filePath)  throws FileNotFoundException {
        File file = new File(filePath);
        Scanner scanner = new Scanner(file);
        int commonChar = 0;
        int total = 0;
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();
            HashSet<Character> firstHalf = new HashSet<>();
            //assemble the first list of chars
            for (int i = 0; i < input.length() / 2; i++) {
                firstHalf.add(input.charAt(i));
            }
            //check for the matching char in the second list
            for (int i = input.length() / 2; i < input.length(); i++) {
                if (firstHalf.contains(input.charAt(i))) {
                    commonChar = input.charAt(i) - 'a' + 1;
                    break;
                }
            }
            //capital letters are assigned negative values. let's fix that.
            if (commonChar < 0) commonChar += 58;
            total += commonChar;
        }
        scanner.close();
        return total;
    }
    //part two
    public int totalBadges(String filePath)  throws FileNotFoundException {
        File file = new File(filePath);
        Scanner scanner = new Scanner(file);
        int elfGroupSize = 3;
        int total = 0;
        while (scanner.hasNextLine()) {
            HashSet<Character> elf1 = new HashSet<>();
            HashSet<Character> elf2 = new HashSet<>();
            HashSet<Character> elf3 = new HashSet<>();
            //assemble the three lists of chars
            for (int i = 0; i < elfGroupSize; i++) {
                String input = scanner.nextLine();
                switch (i % elfGroupSize) {
                    case 0 :
                        for (int j = 0; j < input.length(); j++) elf1.add(input.charAt(j));
                        break;
                    case 1 :
                        for (int j = 0; j < input.length(); j++) elf2.add(input.charAt(j));
                        break;
                    case 2 :
                        for (int j = 0; j < input.length(); j++) elf3.add(input.charAt(j));
                        break;
                }
            }

            //get the common char and convert it to an int
            int commonChar = 0;
            for (char c : elf1) {
                if (elf2.contains(c) && elf3.contains(c)) {
                    commonChar = c - 'a' + 1;
                }
            }
            //capital letters are assigned negative values. let's fix that.
            if (commonChar < 0) commonChar += 58;
            total += commonChar;
        }
        scanner.close();
        return total;
    }
}
