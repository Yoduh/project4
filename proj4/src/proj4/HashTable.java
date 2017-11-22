package proj4;

import java.io.BufferedReader;
import java.io.IOException;

public class HashTable {
	
	static final int m = 32768;
	static final double phi = 1.61803398875;
	public static int collisions;
	public static int dictCount;
	public static int probeCount;
	LinkedList[] table;

	public HashTable(BufferedReader dictReader) throws IOException {
		table = new LinkedList[m];
		collisions = 0;
		dictCount = 0;
		probeCount = 0;
		while(dictReader.ready()) {
			String word = dictReader.readLine();
			// hash
			int hash = hash(word);
			// compress
			int hashIndex = compress(hash);
			// insert in table
			insert(hashIndex, word);
			dictCount++;
		}
	}

	private int hash(String word) {
		//System.out.print("word: " + word);
		int hash = 0;
		for(int i = 0; i < word.length(); i++) {
			//hash += ((int) word.charAt(i)) * Math.pow(r, i);
			hash = (hash << 7) | (hash >>> 25);
			hash += (int) word.charAt(i);
		}
		return hash;
	}
	
	private int compress(int hash) {
		double c = Math.floor(m*(hash*(1/phi) - Math.floor(hash*(1/phi))));
		//System.out.println(", index: " + c);
		return (int) c;
	}

	public void insert(int idx, String word) {
		if(table[idx] == null) {
			table[idx] = new LinkedList();
		} else {
			collisions++;
		}
		table[idx].add(word);
	}
	
	public boolean lookup(String word) {
		int idx = compress(hash(word));
		if(table[idx] != null) {	// null index = no linked list = no probes required, return false
			int probes = table[idx].contains(word);
			if(probes > 0) {	// positive # of probes = found the word in a hash table linked list
				probeCount += probes;
				return true;
			} else {
				probes = probes * -1;	// negative # of probes = did not find the word in a hash table linked list
				probeCount += probes;
			}
		}
		return false;
	}

}
