/*
 * Author: Obed Josiah Tandadjaja
 * Assignment 4: Dictionary
 */

public interface Tree {
	/**
	 * adding a word to the Dictionary
	 * @param key
	 * @param def
	 */
	void add(String key, String def);
	
	/**
	 * finding a word in the Dictionary
	 * @param key
	 * @return node
	 */
	Node find(String key);
	
	/**
	 * Prints all the words in the Dictionary in order
	 */
	void list();
	
	/**
	 * prints some particular words in the Dictionary in order
	 * @param s1 - starting index
	 * @param s2 - ending index
	 */
	void list(String s1, String s2);
	
	/**
	 * Loading and running the commands from the command files
	 * @param filename
	 */
	void load(String filename);
	
	/**
	 * quitting the program
	 */
	void quit();
	
	/**
	 * Drawing the tree
	 */
	void tree();
	
	/**
	 * Deleting a particular word in the Dictionary
	 * @param key
	 */
	void delete(String key);
}