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
	
	HashTable hash;
	
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
		//inputReader = new BufferedReader(new InputStreamReader(new FileInputStream("src/proj4/input.txt"), "UTF-8"));
		// DELETE THIS NEPHEW
		//outputWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("src/proj4/output.txt"), "UTF-8"));
		
		hash = new HashTable(dictReader);
		System.out.println("done");
		
		//String line = inputReader.readLine();
		//Scanner scan = new Scanner(line);	
	}
}
