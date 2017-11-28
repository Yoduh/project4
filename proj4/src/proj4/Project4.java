package proj4;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Scanner;

/**
 * Spellchecker that reads in a dictionary into a hash table and reads
 * in an input file of text and spell checks the text against the dictionary.
 * Output includes misspelled words and statistics of hash table queries.
 * @author aehandlo
 *
 */
public class Project4 {
	/** Hash table object. An array of linked lists */
	private HashTable hash;
	/** Input text file word counter */
	private static int wordCount = 0;
	/** Misspelling counter */
	private static int misspells = 0;
	/** Hash table lookup counter */
	private static int lookups = 0;
	
	/**
	 * Main method starts execution of the probram
	 * @param args Command line arguments (not used)
	 * @throws FileNotFoundException if input/output files not found in system
	 * @throws IOException if reading/writing error occurs
	 */
	public static void main(String[] args) throws FileNotFoundException, IOException {
		Project4 obj = new Project4();
		obj.execute();
	}

	/**
	 * Reads in the files, constructs the hash table, finds the misspellings
	 * and writes the statistics to output
	 * @throws FileNotFoundException if input/output files not found in system
	 * @throws IOException if reading/writing error occurs
	 */
	private void execute() throws FileNotFoundException, IOException {
		// prepare input and output streams
		
		// for redirection
		BufferedReader dictReader = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
		BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
		BufferedWriter outputWriter = new BufferedWriter(new OutputStreamWriter(System.out, "UTF-8"));
		
		// for files
		if(!dictReader.ready()) {
			System.out.println("Enter a dictionary file (e.g. \"dictionary.txt\"): ");
			File dictFileName = new File(dictReader.readLine());
			System.out.println("Enter an input filename to be spell checked (e.g. \"input.txt\"): ");
			File inputFileName = new File(inputReader.readLine());
			System.out.println("Enter an output filename (e.g. \"output.txt\"): ");
			File outputFileName = new File(inputReader.readLine());
			dictReader = new BufferedReader(new InputStreamReader(new FileInputStream(dictFileName), "UTF-8"));
			inputReader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFileName), "UTF-8"));
			outputWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFileName), "UTF-8"));
		}
		
		// create and populate hash table with dictionary entries
		hash = new HashTable(dictReader);		
		
		// read input file and count misspellings using dictionary lookups
		Scanner in;
		boolean correct;
		while(inputReader.ready()) {
			in = new Scanner(inputReader.readLine());
			in.useDelimiter("([^A-Za-z0-9']+)");
			while(in.hasNext()) {
				String word = in.next();
				if(word.length() == 0) continue;
				correct = spellcheck(word);
				if(!correct) {
					outputWriter.write("Misspelling detected: " + word + "\n");
					misspells++;
				}
				wordCount++;
			}
		}
		
		// write out final statistics
		outputWriter.write("Number of words in the dictionary: " + HashTable.dictCount + "\n");
		outputWriter.write("Number of words in the text to be spell-checked: " + wordCount + "\n");
		outputWriter.write("Number of misspelled words in the text: " + misspells + "\n");
		outputWriter.write("Total number of probes during the checking phase: " + HashTable.probeCount + "\n");
		outputWriter.write(String.format("Average number of probes per word: %.2f\n", ((double) HashTable.probeCount / wordCount)));
		outputWriter.write(String.format("Average number of probes per lookup operation: %.2f\n", ((double) HashTable.probeCount / lookups)));
		
		dictReader.close();
		inputReader.close();
		outputWriter.close();
	}

	/**
	 * Spellcheck rules as defined by project guidelines and written report.
	 * @param word Text from input file being spellchecked
	 * @return True if word is spelled correctly, false if not
	 */
	private boolean spellcheck(String word) {
		// remove non-apostrophe punctuation from beginning of string
		while(!Character.isLetterOrDigit(word.charAt(0)) && word.charAt(0) != '\'') {
			if(word.length() == 1) { // return if the string was all punctuation but don't count it as a misspell since it wasn't a word
				misspells--;
				return false;
			}
			word = word.substring(1, word.length());
		}
		// remove non-apostrophe punctuation from end of string
		while(!Character.isLetterOrDigit(word.charAt(word.length() - 1)) && word.charAt(word.length() - 1) != '\'') {
			word = word.substring(0, word.length() - 1);
		}
		// check if exact word is in dictionary
		lookups++;
		if(hash.lookup(word))
			return true;
		// check with lowercase first letter
		if(Character.isUpperCase(word.charAt(0))) {
			char x = Character.toLowerCase(word.charAt(0));
			word = x + word.substring(1, word.length());
			lookups++;
			if(hash.lookup(word))
				return true;
		}
		// check without "'s" ending
		if(word.length() >=2 && word.substring(word.length() - 2, word.length()).equals("'s")) {
			word = word.substring(0, word.length() - 2); // leave off the "'s"
			lookups++;
			if(hash.lookup(word))
				return true;
		}
		// check without "s" suffix
		if(word.length() >=2 && word.charAt(word.length() - 1) == 's') {
			String modWord = word.substring(0, word.length() - 1);
			lookups++;
			if(hash.lookup(modWord))
				return true;
			// check without "es" suffix
			if(word.substring(word.length() - 2, word.length()).equals("es")) {
				word = word.substring(0, word.length() - 2); // leave off the "es"
				lookups++;
				if(hash.lookup(word))
					return true;
			} else {
				word = word.substring(0, word.length() - 1); // leave off only the "s"
			}
		}
		// check without "ed" suffix
		if(word.length() >=3 && word.substring(word.length() - 2, word.length()).equals("ed")) {
			word = word.substring(0, word.length() - 2); // leave off the "ed"
			lookups++;
			if(hash.lookup(word))
				return true;
			else {
				word = word + "e"; // leave off only the "d"
				lookups++;
				if(hash.lookup(word))
					return true;
			}
		}
		// check without "er" suffix
		if(word.length() >=3 && word.substring(word.length() - 2, word.length()).equals("er")) {
			word = word.substring(0, word.length() - 2); // leave off the "er"
			if(hash.lookup(word))
				return true;
			else {
				word = word + "e"; // leave off only the "r"
				if(hash.lookup(word))
					return true;
			}
		}
		// check without "ing" suffix
		if(word.length() >=4 && word.substring(word.length() - 3, word.length()).equals("ing")) {
			word = word.substring(0, word.length() - 3); // leave off the "ing"
			if(hash.lookup(word))
				return true;
			else {
				word = word + "e"; // replace "ing" with "e"
				if(hash.lookup(word))
					return true;
			}
		}
		// check without "ly" suffix
		if(word.length() >=2 && word.substring(word.length() - 2, word.length()).equals("ly")) {
			word = word.substring(0, word.length() - 2); // leave off the "ly"
			if(hash.lookup(word))
				return true;
		}
		return false;
	}
}
