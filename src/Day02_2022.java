import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Day02_2022 {
    public int getScore(String filePath, int part)  throws FileNotFoundException {
        File file = new File(filePath);
        Scanner scanner = new Scanner(file);
        int totalScore = 0;
        while (scanner.hasNextLine()) {
            String event = scanner.nextLine();
            char opponent = event.charAt(0);
            char you = event.charAt(2);
            if (part == 1) {
                totalScore += gameResult(opponent, you);
            } else if (part == 2) {
                totalScore += fixedResult(opponent, you);
            }
        }
        scanner.close();
        return totalScore;
    }

    // part two
    private int fixedResult (char opponent, char outcome) {
        int winScore = 0;
        int choiceScore = 0;
        switch (outcome) {      //A = Rock, B = Paper, C = Scissors for opponent
                            //Rock = 1, Paper = 2, Scissors = 3 for you
            case 'X' : //Lose
                winScore = 0;
                if (opponent == 'A') choiceScore = 3;
                if (opponent == 'B') choiceScore = 1;
                if (opponent == 'C') choiceScore = 2;
                break;
            case 'Y' : //Draw
                winScore = 3;
                if (opponent == 'A') choiceScore = 1;
                if (opponent == 'B') choiceScore = 2;
                if (opponent == 'C') choiceScore = 3;
                break;
            case 'Z' : //Win
                winScore = 6;
                if (opponent == 'A') choiceScore = 2;
                if (opponent == 'B') choiceScore = 3;
                if (opponent == 'C') choiceScore = 1;
                break;
        }
        return choiceScore + winScore;
    }


    //part one
    private int gameResult(char opponent, char you) {
        int choiceScore = 0;
        int winScore = 0;
        switch (you) {      //A = Rock, B = Paper, C = Scissors for opponent
            case 'X' : //Rock
                choiceScore = 1;
                if (opponent == 'A') winScore = 3;
                if (opponent == 'C') winScore = 6;
                break;
            case 'Y' : //Paper
                choiceScore = 2;
                if (opponent == 'B') winScore = 3;
                if (opponent == 'A') winScore = 6;
                break;
            case 'Z' : //Scissors
                choiceScore = 3;
                if (opponent == 'C') winScore = 3;
                if (opponent == 'B') winScore = 6;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + you);
        }
        return choiceScore + winScore;
    }
}
