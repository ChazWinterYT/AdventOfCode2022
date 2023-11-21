import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

public class Day06_2022 {
    public int getPosition(String filePath, int targetCharCount)  throws IOException {
        Deque<Integer> deque = new ArrayDeque<>();
        //Use a HashSet to track unique characters as they are read
        Set<Integer> set = new HashSet<>();
        //we will track the current position of the char that we are reading
        int position = 0;

        //use a FileReader to take in the text file,
        //and a buffered reader to read it one character at a time
        try (FileReader fileReader = new FileReader(filePath);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            //Use a Deque to track the last four characters that are read

            int currentChar = bufferedReader.read();
            //currentChar will be -1 when we reach the end of file
            while (currentChar != -1) {
                position++;
                //add the current char to the deque and the set
                deque.offerLast(currentChar);
                set.add(currentChar);
                //when the deque size passes targetCharCount, remove the oldest char
                if (deque.size() > targetCharCount) {
                    int firstChar = deque.pollFirst();
                    //if that char is still in the deque (in a different position),
                    //then you should not remove it from the set
                    if (!deque.contains(firstChar)) {
                        set.remove(firstChar);
                    }
                }
                //when the set size reaches targetCharCount, you have your unique chars!
                //if not, then keep reading the file
                if (set.size() == targetCharCount) {
                    return position;
                } else {
                    currentChar = bufferedReader.read();
                }
            }
            //if the loop completes, there were no unique sets
            return -1;
        }
    }
}
