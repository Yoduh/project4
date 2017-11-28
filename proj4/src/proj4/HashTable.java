package proj4;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Implementation of hash table using array of linked lists.
 * Hashing uses 7-bit cyclic shift, and compression uses the
 * "golden ratio" method. Collision resolution is done through
 * separate chaining.
 * @author aehandlo
 *
 */
public class HashTable {
	/** size of hash table. chosen as a power of 2, 30% larger than input size */
	static final int m = 32768;
	/** phi (golden ratio) constant */
	static final double phi = 1.61803398875;
	/** number of collisions used for internal debugging */
	public static int collisions;
	/** dictionary word counter */
	public static int dictCount;
	/** probe counter */
	public static int probeCount;
	/** array of linked lists */
	LinkedList[] table;

	/**
	 * Constructor method that reads in dictionary and populates the hash table
	 * @param dictReader Dictionary
	 * @throws IOException if reading/writing error occurs
	 */
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
			// insert word into in table
			insert(hashIndex, word);
			dictCount++;
		}
	}

	/**
	 * Hash the given String using 7-bit cyclic shift
	 * @param word Word to be hashed
	 * @return the newly hashed word as an int
	 */
	private int hash(String word) {
		int hash = 0;
		for(int i = 0; i < word.length(); i++) {
			hash = (hash << 7) | (hash >>> 25);
			hash += (int) word.charAt(i);
		}
		return hash;
	}
	
	/**
	 * Maps a given hash code to an integer index that is uniformly
	 * distributed within the hash table using the golden ratio method
	 * @param hash Hash to be compressed
	 * @return Mapped integer index
	 */
	private int compress(int hash) {
		double c = Math.floor(m*(hash*(1/phi) - Math.floor(hash*(1/phi))));
		return (int) c;
	}

	/**
	 * Insert given string into given index of the hash table
	 * @param idx Index of the hash table
	 * @param word String to be inserted
	 */
	public void insert(int idx, String word) {
		if(table[idx] == null) {
			table[idx] = new LinkedList();
		} else {
			collisions++;
		}
		table[idx].add(word);
	}
	
	/**
	 * Determine if given string is in the hash table or not.
	 * Also counts number of probes returned from the search operation.
	 * @param word String being searched in the hash table
	 * @return True if the string is found in the hash table, flase if not
	 */
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
