import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class HangmanGame {
    public void play() {
        String word = randomWord();
        HashSet<Character> guessedLetters = new HashSet<>();
        int lives = 8;

        while (lives > 0 && !isWordGuessed(word, guessedLetters)) {
            char letter = requestLetter();
            guessedLetters.add(letter);

            if (!lettersIn(word).contains(letter)) {
                lives--;
            }

            show(word, guessedLetters, lives);
        }

        if (lives == 0) {
            System.out.println("You lost!");
        } else {
            System.out.println("You won!");
        }
    }

    private void show(String word, Set<Character> guessedLetters, int lifes) {
        ArrayList<Character> sortedLetters = new ArrayList<>(guessedLetters);
        Collections.sort(sortedLetters);

        System.out.println(hiddenWord(word, guessedLetters));

        System.out.println("You have guessed the following letters: ");
        for (Character letter : sortedLetters) {
            System.out.print(letter);
        }
        System.out.println();

        System.out.println("Lifes left: " + lifes);

        System.out.println();
    }

    private String hiddenWord(String word, Set<Character> guessedLetters) {
        String hidden = "";

        for (char letter : word.toCharArray()) {
            hidden += guessedLetters.contains(letter) ? letter : '-';
        }

        return hidden;
    }

    private boolean isWordGuessed(String word, Set<Character> guessedLetters) {
        HashSet<Character> intersection = new HashSet<>(guessedLetters);
        HashSet<Character> letters = lettersIn(word);
        intersection.retainAll(lettersIn(word));
        return intersection.size() == letters.size();
    }

    private HashSet<Character> lettersIn(String text) {
        HashSet<Character> letters = new HashSet<>();

        for (char c : text.toCharArray()) {
            letters.add(c);
        }

        return letters;
    }

    private char requestLetter() {
        Scanner reader = new Scanner(System.in);
        char letter = ' ';

        while (!Character.isLetter(letter)) {
            System.out.println("Enter a letter: ");
            String userInput = reader.next();

            if (userInput.length() == 1) {
                letter = userInput.charAt(0);
            }
        }

        return letter;
    }

    private String randomWord() {
        ArrayList<String> words = englishDictionary();
        return words.get(new Random().nextInt(words.size()));
    }

    private ArrayList<String> englishDictionary() {
        String fileLocation = "./resources/wordsEn.txt";
        ArrayList<String> words = new ArrayList<>();

        try {

            BufferedReader reader = new BufferedReader(new FileReader(fileLocation));
            String word = reader.readLine();
            while (word != null) {
                words.add(word);
                word = reader.readLine();
            }

            return words;
        }
        catch (IOException e) {
            System.out.println("Cannot find file: " + fileLocation);
            System.exit(1);
        }

        return words;
    }

}
