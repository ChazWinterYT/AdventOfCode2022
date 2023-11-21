import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;
import java.util.function.Predicate;


public class Day11_2022 {

    public long monkeyBusiness(String filePath, int part) throws IOException {
        File file = new File(filePath);
        Scanner scanner = new Scanner(file);

        //get each line of text from the input
        List<String> lines = new ArrayList<>();
        while (scanner.hasNextLine()) {
            lines.add(scanner.nextLine());
        }
        List<Integer> allMonkeyNames = new ArrayList<>();
        List<List<Long>> allItems = new ArrayList<>();
        List<Function<Long, Long>> allOperations = new ArrayList<>();
        List<String> allOpsText = new ArrayList<>(); //text version of the Function above
        List<Predicate<Long>> allTests = new ArrayList<>();
        List<String> allTestsText = new ArrayList<>(); //text version of the Predicate above
        List<Integer> allTrueMonkeys = new ArrayList<>();
        List<Integer> allFalseMonkeys = new ArrayList<>();
        int allDivisors = 1;
        //the file cycles through 7 types of lines
        for (int i = 0; i < lines.size(); i++) {
            switch (i % 7) {
                //line 0 - sample:  Monkey 0:
                case 0:
                    String[] firstSplitCase0 = lines.get(i).split(":");
                    String[] secondSplitCase0 = firstSplitCase0[0].split(" ");
                    allMonkeyNames.add(Integer.parseInt(secondSplitCase0[1]));
                    break;
                //line 1 - sample:  Starting items: 54, 53
                case 1:
                    String trimmedInputCase1 = lines.get(i).replace(" ", "");
                    String[] firstSplitCase1 = trimmedInputCase1.split(":");
                    String[] secondSplitCase1 = firstSplitCase1[1].split(",");
                    List<Long> numbers = new ArrayList<>();
                    for (String x : secondSplitCase1) {
                        numbers.add(Long.parseLong(x));
                    }
                    allItems.add(numbers);
                    break;
                //line 2 - sample:  Operation: new = old * 3
                case 2:
                    String[] firstSplitCase2 = lines.get(i).split(" ");
                    //special case for the one line that isn't a number
                    if (firstSplitCase2[7].equals("old")) {
                        allOperations.add(x -> x * x);
                    } else {
                        if (firstSplitCase2[6].equals("+")) {
                            allOperations.add(x -> x + Long.parseLong(firstSplitCase2[7]));
                        } else if (firstSplitCase2[6].equals("*")) {
                            allOperations.add(x -> x * Long.parseLong(firstSplitCase2[7]));
                        }
                    }
                    allOpsText.add(lines.get(i));
                    break;
                //line 3 - sample:  Test: divisible by 2
                case 3:
                    String[] firstSplitCase3 = lines.get(i).split(" ");
                    int testValueCase3 = Integer.parseInt(firstSplitCase3[5]);
                    allTests.add(x -> x % testValueCase3 == 0);
                    allTestsText.add(lines.get(i));
                    allDivisors *= Integer.parseInt(firstSplitCase3[5]);
                    break;
                //line 4 - sample:  If true: throw to monkey 2
                case 4:
                    String[] firstSplitCase4 = lines.get(i).split(" ");
                    allTrueMonkeys.add(Integer.parseInt(firstSplitCase4[firstSplitCase4.length - 1]));
                    break;
                //line 5 - sample:  If false: throw to monkey 6
                case 5:
                    String[] firstSplitCase5 = lines.get(i).split(" ");
                    allFalseMonkeys.add(Integer.parseInt(firstSplitCase5[firstSplitCase5.length - 1]));
                    break;
                //line 6 is always blank
                case 6:
                    break;
            }
        }

        //construct the monkeys!
        ArrayList<Monkey> monkeys = new ArrayList<>();
        for (int i = 0; i < allMonkeyNames.size(); i++) {
            Monkey monkey = new Monkey(allMonkeyNames.get(i), allItems.get(i),
                                       allOperations.get(i), allOpsText.get(i),
                                       allTests.get(i), allTestsText.get(i),
                                       allTrueMonkeys.get(i), allFalseMonkeys.get(i));
            monkeys.add(monkey);
        }
        int numTurns;
        if (part == 1) {
            numTurns = 20;
        } else { //part 2
            numTurns = 10000;
        }

        for (int turns = 0; turns < numTurns; turns++) {       //iterate through the rounds
            for (int i = 0; i < allMonkeyNames.size(); i++) {   //iterate through the monkeys
                Monkey currentMonkey = monkeys.get(i);
                Monkey trueMonkey = monkeys.get(currentMonkey.getTrueMonkey());
                Monkey falseMonkey = monkeys.get(currentMonkey.getFalseMonkey());
                while (currentMonkey.itemList().size() != 0) {  //iterate through the items
                    long currentItem = currentMonkey.itemList().get(0);
                    long newWorryLevel = currentMonkey.inspectItem(currentItem);
                    if (part == 1) {
                        newWorryLevel = newWorryLevel / 3;
                    } else { //part 2
                        newWorryLevel = newWorryLevel % allDivisors;
                    }
                    currentMonkey.itemList().set(0, newWorryLevel);
                    if (currentMonkey.testItem(newWorryLevel)) {
                        currentMonkey.throwItemTo(trueMonkey);
                    } else {
                        currentMonkey.throwItemTo(falseMonkey);
                    }
                }
            }
        }
        /*
        for (int i = 0; i < allMonkeyNames.size(); i++) {
            monkeys.get(i).printMonkey();
        }
        */

        List<Long> itemInspections = new ArrayList<>();
        long monkeyBusiness = 1;
        for (Monkey monkey : monkeys) {
            itemInspections.add(monkey.getNumInspections());
        }
        Collections.sort(itemInspections, Collections.reverseOrder());
        System.out.println(itemInspections);
        monkeyBusiness = itemInspections.get(0) * itemInspections.get(1);
        return monkeyBusiness;
    }
}
