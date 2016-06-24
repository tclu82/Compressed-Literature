import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/*
 * Compressed Literature
 */

/**
 * Coding Tree class.
 *
 * @author Tzu-Chien Lu tclu82@uw.edu
 * @version Winter 2016
 */
public class CodingTree {
	
	/** A Huffman Tree node. */
	public HuffmanTreeNode myNode;
	
	/** A map of characters and their binary code. */
	public Map<Character, String> myCodes;
	
	/**A string is used for compressed text. */
	public String myBits;
	
	/**
	 * Constructor
	 * 
	 * @param theMessage the string to encode 
	 */
	public CodingTree(String theMessage) {
		myNode = null;
		myCodes = new HashMap<Character, String>();
		Map<Character, Integer> count = countFrequency(theMessage);
		MyPriorityQueue<HuffmanTreeNode> pq = processPQ(count);
		buildTree(pq);
		buildCodes(myNode, "");
		convertToBinary(theMessage);
	}
	
	/**
	 * Count character frequency
	 * 
	 * @param theMessage
	 * @return Map<Character, Integer>
	 */
	private Map<Character, Integer> countFrequency(String theMessage) {
		HashMap<Character, Integer> count = new HashMap<>();
		
		for (char key : theMessage.toCharArray()) {
			
			if(!count.containsKey(key)) count.put(key, 1);
				
			else count.put(key, count.get(key) + 1);	
		}
		return count;
	}
	
	/**
	 * A PriorityQueeu prioritize Huffman tree node.
	 * 
	 * @param theMap
	 * @return PriorityQueue<HuffmanTreeNode>
	 */
	private MyPriorityQueue<HuffmanTreeNode> processPQ(Map<Character, Integer> theMap) {
		//Comparator<HuffmanTreeNode> compare = new HuffmanTreeNode();
		MyPriorityQueue<HuffmanTreeNode> pq = new MyPriorityQueue<HuffmanTreeNode>();
		
		for (char key : theMap.keySet()) {
//			HuffmanTreeNode node = new HuffmanTreeNode(key, theMap.get(key));
//			pq.offer(node);
			pq.offer(new HuffmanTreeNode(key, theMap.get(key)));;
		}
		return pq;
	}
	
	/**
	 * Builds Huffman tree by merging the first 2 (smallest) and add back to PriorityQueue.
	 * 
	 * @param pq
	 */
	private void buildTree(MyPriorityQueue<HuffmanTreeNode> pq) {
		
		while (pq.size() > 1) {
//			HuffmanTreeNode left = pq.poll();
//			HuffmanTreeNode right = pq.poll();
//			pq.offer(new HuffmanTreeNode(left, right));
			pq.offer(new HuffmanTreeNode(pq.poll(), pq.poll()));;
		}
		myNode = pq.poll();
	}
	
	/**
	 * Builds Huffman Tree Node codes.
	 *
	 * @param theRoot root node.
	 * @param theCode binary code
	 */
	private void buildCodes(HuffmanTreeNode theRoot, String theCode) {
		
		if (theRoot.myLeft != null) buildCodes(theRoot.myLeft, theCode + '0');
		
		if (theRoot.myRight != null) buildCodes(theRoot.myRight, theCode + '1');
		
		if (theRoot.isLeaf()) myCodes.put(theRoot.myKey, theCode);
	}
	
	/**
	 * Convert character to binary using myCodes then add to myBits
	 * 
	 * @param theBook
	 */
	private void convertToBinary(String theBook) {
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < theBook.length(); i++) {
//			char c = theBook.charAt(i);
//			sb.append(myCodes.get(c));
			sb.append(myCodes.get(theBook.charAt(i)));
		}
		myBits = sb.toString();
	}
	
	/**
	 * Extra credits for decode back to character
	 * 
	 * @param theBit
	 * @param theCode
	 * @return A string represents decode
	 */
	private String decode(String theBit, Map<Character,String> theCode) {
		StringBuilder sb = new StringBuilder();
		StringBuilder temp = new StringBuilder();
		Map<String, Character> decode = new HashMap<String, Character>();
		
		for (char c : theCode.keySet()) decode.put(theCode.get(c), c);
		
		for (int i = 0; i < theBit.length() ; i++) {
			temp.append(theBit.charAt(i));
			
			if (decode.containsKey(temp)) {
				sb.append(decode.get(temp));
				temp = new StringBuilder();
			}
		}
		return sb.toString();
	}
	
	/**
	 * Inner class for Huffman Tree node.
	 */
	private class HuffmanTreeNode implements Comparable<HuffmanTreeNode> {
		
		/** A key for Huffman Tree node. */
		public char myKey;
		
		/** A frequency for Huffman Tree node. */
		public int myFreq;
		
		/** The left child. */
		public HuffmanTreeNode myLeft;
		
		/** The right child. */
		public HuffmanTreeNode myRight;

		/**
		 * Constructor for root node.
		 *
		 * @param theChar the the char
		 * @param theFreq the the freq
		 */
		public HuffmanTreeNode(char theChar, int theFreq) {
			myKey = theChar;
			myFreq = theFreq;
			myLeft = null;
            myRight = null;
		}
		
		/**
		 * Constructor for merged node.
		 *
		 * @param theHTN1 
		 * @param theHTN2 
		 */
		public HuffmanTreeNode(HuffmanTreeNode theHTN1, HuffmanTreeNode theHTN2) {
			myLeft = theHTN1;
			myRight = theHTN2;
			myFreq = theHTN1.myFreq + theHTN2.myFreq;
		}
		
		/**
		 * Checks if it is a leaf node.
		 *
		 * @return true if leaf node.
		 */
		private boolean isLeaf() {
			return (myLeft == null && myRight == null);
		}
		
		/**
		 * toString method for output printing.
		 */
		public String toString() {
			return myKey + ": " + myFreq;
		}

		/**
		 * Compares 2 nodes for PriorityQueue.
		 * 
		 * @param theOther
		 */
		@Override
		public int compareTo(HuffmanTreeNode theOther) {
			return myFreq - theOther.myFreq;
		}
	}
}
