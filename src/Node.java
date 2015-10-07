/*
 * Author: Obed Josiah Tandadjaja
 * COS230 - Assignment 4: Node
 */

public class Node 
	{
		String key;		// key word
		Node left; 		// Node left
		Node right;		// Node right
		Node down;		// Node down
		boolean rb;		// red = true, black = false
		String def;		// word definition
		boolean lr; 	// left = false, right = true
		Node parent;	// Node parent
		int AVLvalue;	// AVL value
		
		/**
		 * Constructor1 - initialize key and definition
		 * @param key
		 * @param def
		 */
		Node(String key, String def)
		{
			this.key = key;
			this.def = def;
			left = right = null;
		}
		
		/**
		 * Constructor2 - initialize key, definition and parent
		 * @param key
		 * @param def
		 * @param parent
		 */
		Node(String key, String def, Node parent)
		{
			this.key = key;
			this.def = def;
			left = right = null;
			this.parent = parent;
		}
		
		/**
		 * Constructor 3 - initialize left, key, down, definition, right
		 * @param left
		 * @param key
		 * @param down
		 * @param def
		 * @param right
		 */
		Node(Node left, String key, Node down, String def, Node right)
		{
			this.key = key;
			this.def = def;
			this.left = left;
			this.right = right;
			this.down = down;
		}
		
		/**
		 * Constructor4 - initilialize left, key, down, def, right, AVLvalue
		 * @param left
		 * @param key
		 * @param down
		 * @param def
		 * @param right
		 * @param AVLvalue
		 */
		Node(Node left, String key, Node down, String def, Node right, int AVLvalue)
		{
			this.key = key;
			this.def = def;
			this.left = left;
			this.right = right;
			this.down = down;
			this.AVLvalue = AVLvalue;
		}
		
		/**
		 * Constructor5 - for stack purposes only initialize bool lr
		 * @param bool
		 * @param next
		 */
		Node(boolean bool, Node next)
		{
			this.lr = bool;
			this.right = next;
		}
	}