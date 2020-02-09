package buzzWordFeedTEST;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class MarkovGenerator {
	
	/* The size of each gram, which is the slice of characters
	used to train the markov generator*/
	private int GRAMSIZE;
	
	// List that stores each gram at index 0
	private ArrayList<ArrayList<String>> gramLists;
	
	public MarkovGenerator(String input, int GRAMSIZE) {
		gramLists = new ArrayList<ArrayList<String>>();
		
		for(int i = 0; i < input.length()-GRAMSIZE; i++) {
			this.GRAMSIZE = GRAMSIZE;
			
			// gram set to string slice of size GRAMSIZE
			String gram = input.substring(i, i+GRAMSIZE);
			
			// if there is no list starting with gram, create list with gram at index 0
			// and append list to gramLists
			int index = search(gram);
			if(index == -1) {
				ArrayList<String> list = new ArrayList<String>();
				list.add(gram);
				gramLists.add(list);
				index = gramLists.indexOf(list);
			}
			// append the next gram in the list
			gramLists.get(index).add(input.substring(i+1,i+GRAMSIZE+1));
		}
	}
	
	/**
	 * 	Generates text using a randomly chosen first gram and probability lists of following grams
	 */
	public void generateText(int length, String fileName, boolean printToFile) {
		String text = chooseStartingGram();
		
		boolean whiteSpace = false;
		for(int i = 0; i + GRAMSIZE < length-1 || !whiteSpace; i++) {
			String gram = text.substring(text.length()-GRAMSIZE,text.length());
			String nextChar = generateNextChar(gram);
			text += nextChar;
			
			if(i + GRAMSIZE > length-1 && nextChar.equals(" ")) {
				whiteSpace = true;
			}
		}
		
		if(printToFile) {
			printToFiles(text, fileName);
		}
		else {
			System.out.println(text);
		}
		
	}
	
	public void printToFiles(String text, String FileName) {
		String str = text;
	    BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter(FileName, false));
			writer.flush();
			writer.write(str);
			writer.close();
		} catch (IOException e) {
			// TODO Auto-
			e.printStackTrace();
		}
	}
	
	/**
	 * chooses a gram from a given gram's list of possible next grams. Returns the
	 * last character of that gram
	 */
	public String generateNextChar(String gram) {
		if(search(gram)==-1) {
			return chooseStartingGram();
		}
		ArrayList<String> list = gramLists.get(search(gram));
		return ""+list.get(1 + ((int)((list.size()-1)*Math.random()))).charAt(GRAMSIZE-1);
	}
	
	/**
	 * Returns a starting gram. Since the starting gram needs to be the beginning of
	 * a word, it chooses a gram that follows a space
	 */
	public String chooseStartingGram() {
		String gram = gramLists.get(((int)(gramLists.size()*Math.random()))).get(0);  
		while(gram.charAt(0)!=' ') {
			gram = gramLists.get(((int)(gramLists.size()*Math.random()))).get(0);
		}
		return gram.substring(1) + generateNextChar(gram);
	}
	
	public void printGrams() {
		for(ArrayList<String> g : gramLists) {
			System.out.println(g);
		}
	}
	
	// searches if first index of each gram array matches given gram
	private int search(String gram) {
		for(int i = 0; i < gramLists.size(); i++) {
			if(gramLists.get(i).get(0).equals(gram)) {
				return i;
			}
		}
		return -1;
	}
}

