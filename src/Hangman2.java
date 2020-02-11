import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Hangman2 {

    static private String randomPhrase(String fileName) {
        List<String> phraseList = new ArrayList<>();                        // Get the phrase from a file of phrases
        try {
            phraseList = Files.readAllLines(Paths.get(fileName));
        } catch (IOException e) {
            System.out.println(e);
        }
        System.out.println(phraseList);                                          //Fetching strings stored in file, in the form of a list []

        Random num = new Random();                                              //utilizing 'import java.util.Random;'
        int rand_phrase = num.nextInt(3);                                //generating a random integer between 0-2
        String phrase = phraseList.get(rand_phrase);                            //creating a new variable and ASSIGNING the Random phrase to it
        System.out.println(phrase);                                            //Printing Random Phrase

        return phrase;
    }

    private static StringBuilder generateHiddenPhrase(String phrase) {
        StringBuilder hidden_phrase = new StringBuilder();                      //creating new string builder object
        for (int i = 0; i < phrase.length(); i++) {
            char ch = phrase.charAt(i);
            int ascii = ch;
            if (ascii >= 65 && ascii <= 90) {                                  //conditions for UPPER case letters
                hidden_phrase.append("*");
            } else if (ascii >= 97 && ascii <= 122) {                           //conditions for LOWER case letters
                hidden_phrase.append("*");
            } else {
                hidden_phrase.append(phrase.charAt(i));                         //appending NON Letters
            }
        }
        return hidden_phrase;
    }

    private static int getGuess() {
        System.out.print("Enter a letter to guess: ");                          //Prompting user for INPUT
        Scanner input = new Scanner(System.in);                                 //NEW Scanner object
        char user_input = input.findInLine(".").charAt(0);
        int user_ascii = user_input;
        return user_ascii;
    }

    private static int processGuess(int userInput, String hidden_phrase, String randPhrase) {
        ArrayList<Integer> user_guess = new ArrayList<>();
        int i = 0;
        if ((userInput < 65 || userInput > 122) || (userInput > 90 && userInput < 97)) {
            return 0;
        } else if (user_guess.contains(userInput)) {
            return -2;
        } else if (randPhrase.indexOf(userInput) != -1) {
            return -1;
        } else {
            return 1;
        }
    }


    public static void main(String[] args) {
        Hangman hangman = new Hangman();
        String randPhrase = (randomPhrase("phrases.txt").toString());     //calling randomPhrase method and assigning it to a variable
        String hidden_phrase = (generateHiddenPhrase(randPhrase).toString());     //calling generateHiddenPhrase method and assigning it to a var

        int n_misses = 0;                                                       //initializing # of MISSES
        System.out.println("WELCOME TO HANGMAN --- Start by taking a guess");
        System.out.println("Number of misses: " + n_misses);
        System.out.println("Phrase to guess: " + hidden_phrase);
        System.out.println(" ");


        while ((n_misses != 4) && (hidden_phrase.indexOf("*") != -1)) {
            int userInput = (getGuess());                               //calling getGuess Method
            int processResult = (processGuess(userInput, hidden_phrase, randPhrase));
            if (processResult == 0) {
                System.out.println("No special Characters Allowed!");
            } else if (processResult == -2) {
                System.out.println("Same Guess. Try Again!");
            } else if (processResult == -1) {
                System.out.println("Correct Letter!");
            } else {
                System.out.println("Incorect Letter");
                n_misses += 1;
            }
        }
        if (hidden_phrase.indexOf("*") == -1) {
            System.out.println("CONGRATULATIONS!!! YOU WIN!!! :D");
            n_misses = 4;
        }
    }
}