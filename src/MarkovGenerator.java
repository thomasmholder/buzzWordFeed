package buzzWordFeedTEST;

import java.util.ArrayList;

public class MarkovGenerator {
	
	// The size of each gram, which is the slice of characters
	// used to train the markov generator
	private final int GRAMSIZE = 5;
	
	// List that stores each gram at index 0
	private ArrayList<ArrayList<String>> gramLists;
	
	public MarkovGenerator(String input) {
		gramLists = new ArrayList<ArrayList<String>>();
		
		for(int i = 0; i < input.length()-GRAMSIZE; i++) {
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
	public void generateText(int length) {
		String text = chooseStartingGram();
		for(int i = 0; i < length; i++) {
			String gram = text.substring(text.length()-GRAMSIZE,text.length());
			text += generateNextChar(gram);
		}
		System.out.println(text);
		
	}
	
	/**
	 * chooses a gram from a given gram's list of possible next grams. Returns the
	 * last character of that gram
	 */
	public char generateNextChar(String gram) {
		ArrayList<String> list = gramLists.get(search(gram));
		return list.get(1 + ((int)((list.size()-1)*Math.random()))).charAt(GRAMSIZE-1);
	}
	
	/**
	 * Returns a starting gram. Since the starting gram needs to be the beg
	 */
	public String chooseStartingGram() {
		return gramLists.get(((int)(gramLists.size()*Math.random()))).get(0);
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

