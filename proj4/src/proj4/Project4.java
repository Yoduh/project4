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


public class Project4 {
	
	private HashTable hash;
	private static int wordCount = 0;
	private static int misspells = 0;
	private static int lookups = 0;
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		Project4 obj = new Project4();
		obj.execute();
	}

	private void execute() throws FileNotFoundException, IOException {
		// prepare input and output streams
		
		// for redirection
		BufferedReader dictReader = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
		BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
		BufferedWriter outputWriter = new BufferedWriter(new OutputStreamWriter(System.out, "UTF-8"));
		
		// for files
		/*
		if(!dictReader.ready()) {
			System.out.println("Enter a dictionary file (e.g. \"dict.txt\"): ");
			File dictFileName = new File(dictReader.readLine());
			System.out.println("Enter an input filename to be spell checked (e.g. \"input.txt\"): ");
			File inputFileName = new File(inputReader.readLine());
			System.out.println("Enter an output filename (e.g. \"output.txt\"): ");
			File outputFileName = new File(inputReader.readLine());
			dictReader = new BufferedReader(new InputStreamReader(new FileInputStream(dictFileName), "UTF-8"));
			inputReader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFileName), "UTF-8"));
			outputWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFileName), "UTF-8"));
		}*/
		// DELETE THIS NEPHEW
		dictReader = new BufferedReader(new InputStreamReader(new FileInputStream("src/proj4/dict.txt"), "UTF-8"));
		// DELETE THIS NEPHEW
		inputReader = new BufferedReader(new InputStreamReader(new FileInputStream("src/proj4/input.txt"), "UTF-8"));
		// DELETE THIS NEPHEW
		//outputWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("src/proj4/output.txt"), "UTF-8"));
		
		hash = new HashTable(dictReader);
		System.out.println("collisions = " + HashTable.collisions);
		
		
		
		Scanner in;
		boolean correct;
		while(inputReader.ready()) {
			in = new Scanner(inputReader.readLine());
			in.useDelimiter("\\s|\\,|\\!|\\.|\\\"|\\--+|\\;");
			while(in.hasNext()) {
				String word = in.next();
				if(word.length() == 0) continue;
				correct = spellcheck(word);
				if(!correct) {
					misspells++;
				}
				System.out.println(word + ", T/F? : " + correct);
				wordCount++;
			}
		}
		
		System.out.println("Number of words in the dictionary: " + HashTable.dictCount);
		System.out.println("Number of words in the text to be spell-checked: " + wordCount);
		System.out.println("Number of misspelled words in the text: " + misspells);
		System.out.println("Total number of probes during the checking phase: " + HashTable.probeCount);
		System.out.printf("Average number of probes per word: %.2f\n", ((double) HashTable.probeCount / wordCount));
		System.out.printf("Average number of probes per lookup operation: %.2f\n", ((double) HashTable.probeCount / lookups));
		
		dictReader.close();
		inputReader.close();
	}

	private boolean spellcheck(String word) {
		// remove non-apostrophe punctuation from beginning of string
		System.out.println("WORD: " + word);
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
		System.out.println("WORRRRD: " + word);
		// check if exact word is in dictionary
		lookups++;
		if(hash.lookup(word))
			return true;
		// check with lowercase first letter
		if(Character.isUpperCase(word.charAt(0))) {
			char x = Character.toLowerCase(word.charAt(0));
			word = x + word.substring(1, word.length());
			System.out.println("lowercase: " + word);
			lookups++;
			if(hash.lookup(word))
				return true;
		}
		// check without "'s" ending
		if(word.length() >=2 && word.substring(word.length() - 2, word.length()).equals("'s")) {
			word = word.substring(0, word.length() - 2); // leave off the "'s"
			System.out.println("no 's: " + word);
			lookups++;
			if(hash.lookup(word))
				return true;
		}
		// check without "s" suffix
		if(word.length() >=2 && word.charAt(word.length() - 1) == 's') {
			String modWord = word.substring(0, word.length() - 1);
			System.out.println("no s: " + modWord);
			lookups++;
			if(hash.lookup(modWord))
				return true;
			// check without "es" suffix
			if(word.substring(word.length() - 2, word.length()).equals("es")) {
				word = word.substring(0, word.length() - 2); // leave off the "es"
				System.out.println("no es: " + word);
				lookups++;
				if(hash.lookup(word))
					return true;
			} else {
				word = word.substring(0, word.length() - 1); // leave off only the "s"
				System.out.println("no s AGAIN: " + word);
			}
		}
		// check without "ed" suffix
		if(word.length() >=3 && word.substring(word.length() - 2, word.length()).equals("ed")) {
			word = word.substring(0, word.length() - 2); // leave off the "ed"
			System.out.println("no ed: " + word);
			lookups++;
			if(hash.lookup(word))
				return true;
			else {
				word = word + "e"; // leave off only the "d"
				System.out.println("no d: " + word);
				lookups++;
				if(hash.lookup(word))
					return true;
			}
		}
		// check without "er" suffix
		if(word.length() >=3 && word.substring(word.length() - 2, word.length()).equals("er")) {
			word = word.substring(0, word.length() - 2); // leave off the "er"
			System.out.println("no er: " + word);
			if(hash.lookup(word))
				return true;
			else {
				word = word + "e"; // leave off only the "r"
				System.out.println("no r: " + word);
				if(hash.lookup(word))
					return true;
			}
		}
		// check without "ing" suffix
		if(word.length() >=4 && word.substring(word.length() - 3, word.length()).equals("ing")) {
			word = word.substring(0, word.length() - 3); // leave off the "ing"
			System.out.println("no ing: " + word);
			if(hash.lookup(word))
				return true;
			else {
				word = word + "e"; // replace "ing" with "e"
				System.out.println("replace with e: " + word);
				if(hash.lookup(word))
					return true;
			}
		}
		// check without "ly" suffix
		if(word.length() >=2 && word.substring(word.length() - 2, word.length()).equals("ly")) {
			word = word.substring(0, word.length() - 2); // leave off the "ly"
			System.out.println("no ly: " + word);
			if(hash.lookup(word))
				return true;
		}
		return false;
	}
}
