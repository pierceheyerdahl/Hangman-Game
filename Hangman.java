import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Hangman {
	public static final int NUM_OF_WORDS = 25; // number of words in the hangman.txt file
	public static final int NUM_OF_MISSES = 5; // amount of misses before game ends
	
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		int gameCount = 0;
		String[] words = new String[NUM_OF_WORDS];
		boolean playAgain = true;
		
		System.out.println("Let's Play Hangman!\n");
		System.out.print("Please enter a filename to import words: ");
		String fileName = input.nextLine();
		File file = new File(fileName);
		
		while (!file.exists()) {
			System.out.print("Error: Enter file name: ");
			fileName = input.nextLine();
			file = new File(fileName);
		}
		try {
			words = importer(fileName);
		} catch (FileNotFoundException e) {
			System.out.print("Error: no file found");
			System.exit(0);
		}
		
		// loop to keep running hangman games
		while (playAgain != false) {
			String answer = words[(int)(Math.random() * words.length)].toUpperCase();
			runGame(answer, input);
			gameCount++;
			System.out.println("Do you want to play again? ");
			if (input.nextLine().toUpperCase().charAt(0) == 'N') {
				playAgain = false;
			}
		}
		System.out.print("You played " + gameCount + " game(s). Goodbye!");
		input.close();
	}

	// Method to run a single instance of a hangman game
	public static void runGame(String answer, Scanner input) {

		String guesses = "";
		String misses = "";
		Boolean solved = false;
		char[] display = new char[answer.length()];
		for (int i = 0; i < display.length; i++) {
			display[i] = '_';
		}
		
		while (solved != true) {
			System.out.print("Word: ");
			displayWord(display);
			System.out.println("");
			System.out.println("Misses: " + misses);
			System.out.print("Guess a letter: ");
			char userGuess = input.nextLine().toUpperCase().charAt(0);

			while (guesses.indexOf(userGuess) > -1 || !Character.isLetter(userGuess)) {
				System.out.print("Invalid! Guess a letter: ");
				userGuess = input.nextLine().toUpperCase().charAt(0);
			}
			System.out.println("");
			guesses += userGuess;
			if (!(answer.indexOf(userGuess) > -1)) {
				misses += userGuess;
				if (misses.length() >= NUM_OF_MISSES) {
					System.out.println("You LOSE! The answer was " + answer);
					break;
				}
			}
			for (int i = 0; i < answer.length(); i++) {
				if (userGuess == answer.charAt(i)) {
					display[i] = userGuess;
				}
			}
			if (!(new String(display).contains("_"))) {
				solved = true;
				System.out.println("The answer was: " + answer);
				System.out.println("You WIN!\n");
			}
		}
	}
	
	// Construct the word
	public static void displayWord(char[] display) {
		for (int i  = 0; i < display.length; i++) {
			System.out.print(display[i] + " ");
		}
	}
	
	// Imports words from hangman.txt
	public static String[] importer(String fileName) throws FileNotFoundException {
		Scanner input = new Scanner(new File(fileName));
		String[] words = new String[NUM_OF_WORDS];
		int i = 0;
		while (input.hasNext()) {
			boolean validWord = true;
			words[i] = input.nextLine();
			for (int k = 0; k < words[i].length(); k++) {
				if (!Character.isLetter(words[i].charAt(k))) {
					validWord = false;
					break;
				}
			}
			if (validWord == true) {
				i++;
			}
		}
		input.close();
		return words;
	}
}