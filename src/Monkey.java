import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

//class to be used with Day11_2022 to spawn monkeys
public class Monkey {
    private int name;
    private List<Long> items = new ArrayList<>();
    private Function<Long, Long> operation;
    private String opsText;
    private Predicate<Long> test;
    private String testText;
    private int trueMonkey;
    private int falseMonkey;
    private long numInspections;
    public Monkey(int name, List<Long> startingItems,
                      Function<Long, Long> operation, String opsText,
                      Predicate<Long> test, String testText,
                      int trueMonkey, int falseMonkey) {
        this.name = name;
        this.items.addAll(startingItems);
        this.operation = operation;
        this.opsText = opsText;
        this.test = test;
        this.testText = testText;
        this.trueMonkey = trueMonkey;
        this.falseMonkey = falseMonkey;
        this.numInspections = 0;
    }

    public List<Long> itemList() {
        return this.items;
    }
    public int getTrueMonkey() {
        return this.trueMonkey;
    }
    public int getFalseMonkey() {
        return this.falseMonkey;
    }

    public long getNumInspections() {
        return this.numInspections;
    }

    public long inspectItem(long currentItem) {
        this.numInspections++;
        return operation.apply(currentItem);
    }
    public boolean testItem(long currentItem) {
        return test.test(currentItem);
    }

    public void throwItemTo(Monkey catcherMonkey) {
        long currentItem = 0;
        if (!this.items.isEmpty()) {
            currentItem = items.remove(0);
        } else {
            return;
        }
        catcherMonkey.items.add(currentItem);
    }

    public void printMonkey() {
        System.out.println("Monkey Name: " + this.name);
        System.out.println("Held Items: " + this.items);
        System.out.println(this.opsText);
        System.out.println(this.testText);
        System.out.println("If true, throws to: " + this.trueMonkey);
        System.out.println("If false, throws to: " + this.falseMonkey);
        System.out.println("Inspections: " + this.numInspections);
        System.out.println("====END OF MONKEY====");
        System.out.println();
    }
}
