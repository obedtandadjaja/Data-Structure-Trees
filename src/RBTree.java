/*
 * Author: Obed Josiah Tandadjaja
 * Assignment 4: Dictionary - Red Black Tree
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class RBTree extends BSTree implements Tree{

	// root and stack
	Node root;
	Stack ll;
	
	/**
	 * Constructor
	 */
	RBTree()
	{
		root = null;
		ll = new Stack();
	}
	
	/**
	 * Adding words to the dictionary
	 * @param key (String) - the word
	 * @param def (String) - the definition of the word
	 */
	@Override
	public void add(String key, String def) {
		// TODO Auto-generated method stub
		// empty the stack first
		ll.empty();
		// if root is null then put it at root
		if(root == null)
		{
			root = new Node(key, def);
			root.rb = false;
		}
		else
		{
			// loop through the tree to find the favorable position for the word
			Node current = root;
			while(true)
			{
				// if encounter a black node with two red children then change their color
				if(current.left != null && current.right != null)
				{
					if(current.left.rb && current.right.rb)
					{
						changeColor(current);
						changeColor(current.left);
						changeColor(current.right);
						checkRootColor();
						if(current.parent != null)
						{
							if(current.parent.rb && current.rb)
							{
								RedRed(current);
							}
						}
					}
				}
				if(key.toLowerCase().compareTo(current.key.toLowerCase()) > 0)
				{
					ll.add(false);
					if(current.left == null)
					{
						// adding the new node
						current.left = new Node(key, def, current);
						current.left.rb = true;
						
						// if parent and child are red then redred
						if(current.rb)
						{
							RedRed(current.left);
						}
						break;
					}
					else
					{
						current = current.left;
					}
				}
				else if(key.toLowerCase().compareTo(current.key.toLowerCase()) < 0)
				{
					ll.add(true);
					if(current.right == null)
					{
						// adding the new node
						current.right = new Node(key, def, current);
						current.right.rb = true;
						
						// if parent and child are red then redred
						if(current.rb)
						{
							RedRed(current.right);
						}
						break;
					}
					else
					{
						current = current.right;
					}
				}
				// if the word exists then current.down = word
				else
				{
					while(current.down != null)
					{
						current = current.down;
					}
					current.down = new Node(key, def);
					break;
				}
			}
		}
	}
	
	/**
	 * Checks the root color and keep it black
	 */
	public void checkRootColor()
	{
		if(root.rb)
		{
			root.rb = false;
		}
	}
	
	/**
	 * RedRed violation method
	 * change color and rotate to balance the tree
	 * @param n - child node
	 */
	public void RedRed(Node n)
	{
		if(n.key.compareToIgnoreCase(n.parent.key) > 0) // n = parent.right
		{
			if(n.key.compareToIgnoreCase(root.key) > 0) // root.right
			{
				// outsideGrandChild
				changeColor(n.parent.parent);
				changeColor(n.parent);
				rotateRight(n.parent.parent);
			}
			else
			{
				// insideGrandChild
				changeColor(n.parent.parent);
				changeColor(n);
				rotateRight(n.parent);
				rotateLeft(n.parent.parent);
			}
		}
		else // n = parent.left
		{
			if(n.key.compareToIgnoreCase(root.key) > 0) // root.right
			{
				// insideGrandChild
				changeColor(n.parent.parent);
				changeColor(n);
				rotateLeft(n.parent);
				rotateRight(n.parent.parent);
			}
			else
			{
				// outsideGrandChild
				changeColor(n.parent.parent);
				changeColor(n.parent);
				rotateLeft(n.parent.parent);
			}
		}
	}
	
	/**
	 * changing a color of a node
	 * @param n
	 */
	public void changeColor(Node n)
	{
		if(n.rb)
		{
			n.rb = false;
		}
		else
		{
			n.rb = true;
		}
	}
	
	/**
	 * Rotate a node to the right
	 * @param parent
	 */
	public void rotateRight(Node parent)
	{
		// move parent to the right
		parent.right = new Node(null, parent.key, parent.down, parent.def, parent.right);
		parent.right.rb = parent.rb;
		parent.right.parent = parent;
		
		// set the parent of parent.right to parent
		if(parent.right.right != null)
		{
			parent.right.right.parent = parent.right;
		}
		
		// if there is parent.left is not null
		if(parent.left != null)
		{
			// remove parent and replace it with parent.left values
			parent.key = parent.left.key;
			parent.down = parent.left.down;
			parent.def = parent.left.def;
			parent.rb = parent.left.rb;
			
			// change the parent of the remaining nodes
			if(parent.left.right != null)
			{
				parent.right.left = parent.left.right;
				parent.right.left.parent = parent.right;
			}
			if(parent.left.left != null)
			{
				parent.left = parent.left.left;
				parent.left.parent = parent;
			}
			else
			{
				parent.left = null;
			}
		}
	}
	
	/**
	 * rotate a node to the left
	 * @param parent
	 */
	public void rotateLeft(Node parent)
	{
		// move parent to the left
		parent.left = new Node(parent.left, parent.key, parent.down, parent.def, null);
		parent.left.rb = parent.rb;
		parent.left.parent = parent;
		
		// assign parent of parent.left to parent
		if(parent.left.left != null)
		{
			parent.left.left.parent = parent.left;
		}
		
		// if parent.right is not null
		if(parent.right != null)
		{
			// remove parent and replace it with the values of parent.right
			parent.key = parent.right.key;
			parent.down = parent.right.down;
			parent.def = parent.right.def;
			parent.rb = parent.right.rb;
			
			// change the parent of the affected nodes
			if(parent.right.left != null)
			{
				parent.left.right = parent.right.left;
				parent.left.right.parent = parent.left;
			}
			if(parent.right.right != null)
			{
				parent.right = parent.right.right;
				parent.right.parent = parent;
			}
			else
			{
				parent.right = null;
			}
		}
	}
	
	/**
	 * finding a word from the dictionary
	 * @param key (String) - the word
	 */
	@Override
	public Node find(String key) {
		// TODO Auto-generated method stub
		// loop through the tree to find the word
		Node current = root;
		while(current != null)
		{
			if(current.key.equalsIgnoreCase(key))
			{
				System.out.println(current.key+": "+current.def);
				Node temp = current;
				while(temp.down != null)
				{
					temp = temp.down;
					System.out.println(temp.key+": "+temp.def);
				}
				return current;
			}
			else if(key.toLowerCase().compareTo(current.key.toLowerCase()) < 0)
			{
				current = current.right;
			}
			else if(key.toLowerCase().compareTo(current.key.toLowerCase()) > 0)
			{
				current = current.left;
			}
		}
		// if not found, print...
		System.out.println("Find failed!");
		return null;
	}

	/**
	 * list the words from the dictionary in ascending order
	 */
	@Override
	public void list() {
		// TODO Auto-generated method stub
		inorder(root);
	}

	/**
	 * list particular words from the dictionary in ascending order
	 * @param s1 - starting index
	 * @param s2 - ending index
	 */
	@Override
	public void list(String s1, String s2) {
		// TODO Auto-generated method stub
		if(s1.equalsIgnoreCase(s2))
		{
			inorder(root, s1, s2);
		}
		else
		{
			inorder(root, s1, s2);
		}
	}

	/**
	 * loading the commands from the command file
	 * @param filename
	 */
	@Override
	public void load(String filename) {
		// TODO Auto-generated method stub
		try 
		{
			File f = new File(filename);
			Scanner sc = new Scanner(f);
			
			while(sc.hasNext())
			{
				String s = sc.nextLine();
				String[] split = s.split(" ");
				if(split[0].equalsIgnoreCase("add"))
				{
					String t = "";
					for(int i = 2; i < split.length; i++)
					{
						t += " "+split[i];
					}
					add(split[1], t);
				}
				else if(split[0].equalsIgnoreCase("Find"))
				{
					System.out.println(s);
					find(split[1]);
					System.out.println(" ");
				}
				else if(split[0].equalsIgnoreCase("list"))
				{
					if(split.length == 1)
					{
						System.out.println(s);
						list();
						System.out.println(" ");
					}
					else if(split.length == 3)
					{
						System.out.println(s);
						list(split[1], split[2]);
						System.out.println(" ");
					}
					else
					{
						System.out.println("Incorrect command!");
						System.out.println(s);
						quit();
					}
				}
				else if(split[0].equalsIgnoreCase("delete"))
				{
					System.out.println(s);
					delete(split[1]);
					System.out.println(" ");
				}
				else if(split[0].equalsIgnoreCase("tree"))
				{
					System.out.println(s);
					tree();
					System.out.println(" ");
				}
				else
				{
					System.out.println("Incorrect command!");
					System.out.println(s);
				}
			}
			tree();
		}
		catch (FileNotFoundException e) 
		{
			// TODO Auto-generated catch block
			System.out.println("Command File Not Found!");
			System.out.println(filename);
			quit();
		}
	}

	/**
	 * quitting the program
	 */
	@Override
	public void quit() {
		// TODO Auto-generated method stub
		System.exit(0);
	}

	/**
	 * drawing the tree
	 */
	@Override
	public void tree() {
		// TODO Auto-generated method stub
		preorder(root, "");
	}
	
	/**
	 * preorder traversal used to draw the tree
	 */
	void preorder(Node c, String indent){
		if(c != null) 
		{
			System.out.print(indent + c.key);
			if(c.parent != null)
			{
				System.out.print(" "+c.parent.key);
			}
			if(c.rb)
			{
				System.out.println(" (red)");
			}
			else
			{
				System.out.println(" (black)");
			}
			preorder(c.right, indent + "    ");
			preorder(c.left, indent + "    ");
		}
	}
	
	/**
	 * inorder traversal used to print the words in the tree in ascending order
	 */
	public void inorder(Node c)
	{
		Node temp = c;
		if(c != null)
		{
			inorder(c.right);
			System.out.println(c.key+": "+c.def);
			while(temp.down != null)
			{
				temp = temp.down;
				System.out.println(temp.key+": "+temp.def);
			}
			inorder(c.left);
		}
	}
	
	/**
	 * inorder traversal used to print particular words in the tree in ascending order
	 * @param s1 - starting index
	 * @param s2 - ending index
	 */
	public void inorder(Node c, String s1, String s2)
	{
		Node temp = c;
		if(c != null)
		{
			inorder(c.right, s1, s2);
			if((s1.toLowerCase().compareTo(c.key.toLowerCase()) <= 0) && (s2.toLowerCase().compareTo(c.key.toLowerCase()) >= 0))
			{
				System.out.println(c.key+": "+c.def);
				while(temp.down != null)
				{
					temp = temp.down;
					System.out.println(temp.key+": "+temp.def);
				}
			}
			inorder(c.left, s1, s2);
		}
	}

	/**
	 * deleting a word from the dictionary
	 * @param key (String) - the word
	 */
	@Override
	public void delete(String key) {
		// TODO Auto-generated method stub
		Node tbd = find(key); // tbd = to be deleted
		if(tbd.left == null && tbd.right == null) // if no children
		{
			deleteNoChild(tbd);
		}
		else if(tbd.left != null && tbd.right != null) // if two children
		{
			deleteTwoChildren(tbd);
		}
		else // if one child
		{
			deleteOneChild(tbd);
		}
		tree();
	}
	
	/**
	 * finds the parent of a node
	 * @param key (String) - the word
	 */
	public Node findParent(String key)
	{
		// loop through the tree to get the parent
		Node current = root;
		Node parent = null;
		while(current != null)
		{
			if(current.key.equalsIgnoreCase(key) || current.key.equalsIgnoreCase(key))
			{
				return parent;
			}
			else if(key.toLowerCase().compareTo(current.key.toLowerCase()) < 0)
			{
				parent = current;
				current = current.right;
			}
			else
			{
				parent = current;
				current = current.left;
			}
		}
		System.out.println("Find failed!");
		return null;
	}
	
	/**
	 * delete a node with no children
	 * @param tbd - node to be deleted
	 */
	public void deleteNoChild(Node tbd)
	{
		Node temp = findParent(tbd.key);
		if(temp != null)
		{
			if(temp.left != null && temp.right != null)
			{
				if(temp.left.key.equalsIgnoreCase(tbd.key))
				{
					temp.left = null;
				}
				else
				{
					temp.right = null;
				}
			}
			else if(temp.left != null)
			{
				temp.left = null;
			}
			else
			{
				temp.right = null;
			}
		}
		else
		{
			root = null;
		}
	}
	
	/**
	 * deleting a node with one child
	 * @param tbd - node to be deleted
	 */
	public void deleteOneChild(Node tbd)
	{
		// if it has a left child
		if(tbd.left != null)
		{
			tbd.key = tbd.left.key;
			tbd.def = tbd.left.def;
			tbd.down = tbd.left.down;
			if(tbd.left.left != null && tbd.left.right != null)
			{
				Node temp = tbd.left;
				tbd.left = tbd.left.left;
				tbd.right = temp.right;
			}
			else if(tbd.left.left == null)
			{
				tbd.left = null;
			}
			else if(tbd.left.left != null)
			{
				tbd.left = tbd.left.left;
			}
			else if(tbd.left.right != null)
			{
				tbd.right = tbd.left.right;
			}
			else if(tbd.left.right == null)
			{
				tbd.right = null;
			}
			else
			{
				tbd.right = null;
				tbd.left = null;
			}
		}
		// if it has a right child
		else
		{
			tbd.key = tbd.right.key;
			tbd.def = tbd.right.def;
			tbd.down = tbd.right.down;
			if(tbd.right.right != null && tbd.right.left != null)
			{
				Node temp = tbd.right;
				tbd.right = tbd.right.right;
				tbd.left = temp.left;
			}
			else if(tbd.right.right == null)
			{
				tbd.right = null;
			}
			else if(tbd.right.right != null)
			{
				tbd.right = tbd.right.right;
			}
			else if(tbd.right.left != null)
			{
				tbd.left = tbd.right.left;
			}
			else if(tbd.right.left == null)
			{
				tbd.left = null;
			}
			else
			{
				tbd.left = null;
				tbd.right = null;
			}
		}
	}
	
	/**
	 * deleting a node with two children
	 * @param tbd - node to be deleted
	 */
	public void deleteTwoChildren(Node tbd)
	{
		// finding the successor
		Node parent = tbd;
		Node temp = tbd.right;
		while(temp.left != null)
		{
			parent = temp;
			temp = temp.left;
		}
		
		// removing the tbd values with the successor's values
		tbd.key = temp.key;
		tbd.def = temp.def;
		tbd.down = temp.down;
		
		// assign proper parenting to the affected nodes
		if(parent != tbd)
		{
			if(temp.right != null)
			{
				parent.left = temp.right;
				parent.left.parent = parent;
			}
			else
			{
				parent.left = null;
			}
		}
		else
		{
			if(temp.right != null)
			{
				tbd.right = temp.right;
				tbd.right.parent = parent;
			}
			else
			{
				tbd.right = null;
			}
		}
		tbd.left.parent = tbd;
	}

}
