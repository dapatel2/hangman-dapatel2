import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Hangman {

    public static void main(String[] args) {
        Hangman hangman = new Hangman();

        List<String> phraseList = new ArrayList<>();
        // Get the phrase from a file of phrases
        try {
            phraseList = Files.readAllLines(Paths.get("phrases.txt"));
        } catch (IOException e) {
            System.out.println(e);
        }
        System.out.println(phraseList);                    //Fetching strings stored in file, in the form of a list []

        Random num = new Random();                        //utilizing 'import java.util.Random;'
        int rand_phrase = num.nextInt(3);        //generating a random integer between 0-2
        String phrase = phraseList.get(rand_phrase);    //creating a new variable and ASSIGNING the Random phrase to it
        System.out.println(phrase);                        //Printing Random Phrase

        //HIDDEN PHRASE//
        StringBuilder hidden_phrase = new StringBuilder();                     //creating new string builder object
        for (int i = 0; i < phrase.length(); i++) {
            char ch = phrase.charAt(i);
            int ascii = ch;
            if (ascii >= 65 && ascii <= 90) {                                  //conditions for UPPER case letters
                hidden_phrase.append("*");
            } else if (ascii >= 97 && ascii <= 122) {                               //conditions for LOWER case letters
                hidden_phrase.append("*");
            } else {
                hidden_phrase.append(phrase.charAt(i));                         //appending NON Letters
            }
        }

        int n_misses = 0;                                                          //initializing # of MISSES
        System.out.println("WELCOME TO HANGMAN --- Start by taking a guess");
        System.out.println("Number of misses: " + n_misses);
        System.out.println("Phrase to guess: " + hidden_phrase);
        System.out.println(" ");

        //EMPTY List for USER INPUT//
        ArrayList<Integer> user_guess = new ArrayList<>();


        while ((n_misses != 4) && (hidden_phrase.indexOf("*") != -1)) {
            System.out.print("Enter a letter to guess: ");                     //Prompting user for INPUT
            Scanner input = new Scanner(System.in);                        //NEW Scanner object
            char user_input = input.findInLine(".").charAt(0);    //Scanning user input

            int user_ascii = user_input;                                  //Converting USER Input -> ASCII
            int user_ascii32 = user_ascii - 32;                            //Variable to check for Upper Case
            int user_32ascii = user_ascii + 32;                            //Variable to check for Lower Case

            if ((user_ascii < 65 || user_ascii > 122) || (user_ascii > 90 && user_ascii < 97)) {
                //Special Chars Condition
                System.out.println("No special character allowed!");
                System.out.println("Please enter an UPPER/LOWER Case Character!");
                System.out.println(" ");
            } else if (user_guess.contains(user_ascii) || user_guess.contains(user_ascii32) || user_guess.contains(user_32ascii)) {
                //cross reference item in GUESS LIST
                System.out.println("Letter already guessed, please try another letter!");
                System.out.println("Number of misses: " + n_misses);
                System.out.println(" ");
            } else if ((phrase.indexOf(user_ascii) != -1) || (phrase.indexOf(user_32ascii) != -1) || (phrase.indexOf(user_ascii32) != -1)) {    //condition for char PRESENT
                for (int j = 0; j < phrase.length(); j++) {
                    if (user_ascii - phrase.charAt(j) == 32) {                 //Upper-Lower Case condition
                        hidden_phrase.setCharAt(j, phrase.charAt(j));
                        user_guess.add(user_ascii);                            //Adding USER ascii to the list
                    } else if (user_ascii - phrase.charAt(j) == -32) {        //Upper-Lower Case condition
                        hidden_phrase.setCharAt(j, phrase.charAt(j));
                        user_guess.add(user_ascii);                            //Adding USER ascii to the list
                    } else if (user_ascii == phrase.charAt(j)) {                //exact guess
                        hidden_phrase.setCharAt(j, user_input);
                        user_guess.add(user_ascii);                          //Adding USER ascii to the list
                    }
                }
            } else {                                    //condition for match found
                n_misses++;
                user_guess.add(user_ascii);
                System.out.println("INCORRECT LETTER!!!");
                System.out.println(" ");
            }
            System.out.println("Phrase to guess: " + hidden_phrase);
            System.out.println("Number of misses: " + n_misses);
            System.out.println(" ");
        }
        if (hidden_phrase.indexOf("*") == -1) {
            System.out.println("CONGRATULATIONS!!! YOU WIN!!! :D");
            n_misses = 4;
        } else {
            System.out.println("You have reached the maximum number of tries!!!");
            System.out.println("GAME OVER!!!");
        }
    }
}


