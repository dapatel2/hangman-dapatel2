import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;


public class Hangman {

	public static void main(String [] args) {

        	Hangman hangman = new Hangman();

        	List<String> phraseList=null;
        	// Get the phrase from a file of phrases
        	try {
            		phraseList = Files.readAllLines(Paths.get("phrases.txt"));
        	} catch (IOException e) {
        	    	System.out.println(e);
        	}
        	System.out.println(phraseList);
    	}

}
