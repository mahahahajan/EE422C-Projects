package assignment3;

public class Node {

	public String word; 
	public Node parent;
	public int length;
	
	/**
	 * 
	 */
	public Node() {
		word = null;
		parent = null;
		length = 0;
	}
	
	/**
	 * @param word String containing word to be saved
	 */
	public Node(String word) {
		this.word = word;
		this.parent = null; 
	}
	
	/**
	 * @param word String containing word to be saved
	 * @param parent Node containing previous word
	 */
	public Node(String word, Node parent) {
		this.parent = parent;
		this.word = word;
	}
	
	/**
	 * @param word String containing word to be saved
	 * @param parent Node containing previous word
	 * @param length int representing how far the current node is from the start
	 */
	public Node(String word, Node parent, int length) {
		this.word = word;
		this.parent = parent;
		this.length = length;
	}
	
}
