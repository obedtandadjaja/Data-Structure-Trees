/*
 * Author: Obed Josiah Tandadjaja
 * COS230 - Assingment 4: Splay Tree
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SplayTree implements Tree {

	Node root;
	Stack ll;
	
	/**
	 * Constructor
	 */
	SplayTree()
	{
		root = null;
		ll = new Stack();
	}
	
	/**
	 * adding a word to the dictionary
	 * @param key (String) - the word
	 * @param def (String) - the definition
	 */
	@Override
	public void add(String key, String def) {
		// TODO Auto-generated method stub
		ll.empty();
		if(root == null)
		{
			root = new Node(key, def);
		}
		else
		{
			Node current = root;
			while(true)
			{
				if(key.toLowerCase().compareTo(current.key.toLowerCase()) > 0)
				{
					ll.add(false);
					if(current.left == null)
					{
						current.left = new Node(key, def, current);
						// splay the tree
						rotate(current.left);
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
						current.right = new Node(key, def, current);
						// splay the tree
						rotate(current.right);
						break;
					}
					else
					{
						current = current.right;
					}
				}
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
	 * Splay method
	 * @param n - node
	 */
	public void rotate(Node n)
	{
		if(n != null)
		{
			Node temp = n;
			int size = ll.size();
			for(int i = 0; i < size; i++)
			{
				if(n.key.equalsIgnoreCase(root.key))
				{
					break;
				}
				// rotate left if it is a right child
				if(ll.pop())
				{
					if(temp.parent.key.equalsIgnoreCase(root.key))
					{
						rotateLeft(root);
					}
					else
					{
						rotateLeft(temp.parent);
					}
				}
				// rotate right if it is a left child
				else
				{
					if(temp.parent.key.equalsIgnoreCase(root.key))
					{
						rotateRight(root);
					}
					else
					{
						rotateRight(temp.parent);
					}
				}
				temp = temp.parent;
			}
		}
	}
	
	/**
	 * rotate a node to the right
	 * @param parent
	 */
	public void rotateRight(Node parent)
	{
		parent.right = new Node(null, parent.key, parent.down, parent.def, parent.right);
		parent.right.parent = parent;
		if(parent.right.right != null)
		{
			parent.right.right.parent = parent.right;
		}
		if(parent.left != null)
		{
			if(parent.left.right != null)
			{
				parent.right.left = parent.left.right;
				parent.right.left.parent = parent.right;
			}
			if(parent.left.left != null)
			{
				parent.key = parent.left.key;
				parent.down = parent.left.down;
				parent.def = parent.left.def;
				parent.left = parent.left.left;
				parent.left.parent = parent;
			}
			else
			{
				parent.key = parent.left.key;
				parent.down = parent.left.down;
				parent.def = parent.left.def;
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
		parent.left = new Node(parent.left, parent.key, parent.down, parent.def, null);
		parent.left.parent = parent;
		if(parent.left.left != null)
		{
			parent.left.left.parent = parent.left;
		}
		if(parent.right != null)
		{
			if(parent.right.left != null)
			{
				parent.left.right = parent.right.left;
				parent.left.right.parent = parent.left;
			}
			if(parent.right.right != null)
			{
				parent.key = parent.right.key;
				parent.down = parent.right.down;
				parent.def = parent.right.def;
				parent.right = parent.right.right;
				parent.right.parent = parent;
			}
			else
			{
				parent.key = parent.right.key;
				parent.down = parent.right.down;
				parent.def = parent.right.def;
				parent.right = null;
			}
		}
	}

	/**
	 * find a word in the dictionary
	 * @param key (String) - the word
	 */
	@Override
	public Node find(String key) {
		// TODO Auto-generated method stub
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
				rotate(current);
				tree();
				return current;
			}
			else if(key.toLowerCase().compareTo(current.key.toLowerCase()) < 0)
			{
				ll.add(true);
				current = current.right;
			}
			else if(key.toLowerCase().compareTo(current.key.toLowerCase()) > 0)
			{
				ll.add(false);
				current = current.left;
			}
		}
		System.out.println("Find failed!");
		return null;
	}
	
	/**
	 * preorder traversal 
	 * @param c - root
	 * @param indent - indentation
	 */
	void preorder(Node c, String indent){
		if(c != null) 
		{
			System.out.println(indent + c.key);
			preorder(c.right, indent + "    ");
			preorder(c.left, indent + "    ");
		}
	}
	
	/**
	 * inorder traversal
	 * @param c - root
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
	 * inorder traversal - prints particular words from the tree
	 * @param c - root
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
	 * prints all the words in the tree in ascending order
	 */
	@Override
	public void list() {
		// TODO Auto-generated method stub
		inorder(root);
	}

	/**
	 * prints particular words in the tree in ascending order
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
	 * load the commands from the command file
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
	 * delete a word from the dictionary
	 * @param key (String) - the word
	 */
	@Override
	public void delete(String key) {
		// TODO Auto-generated method stub
		find(key);
		if(root.left == null && root.right == null) // if no children
		{
			deleteNoChild(root);
		}
		else if(root.left != null && root.right != null) // if two children
		{
			deleteTwoChildren(root);
		}
		else // if one child
		{
			deleteOneChild(root);
		}
		tree();
	}
	
	/**
	 * delete a node with no child
	 * @param tbd
	 */
	public void deleteNoChild(Node tbd)
	{
		root = null;
	}
	
	/**
	 * delete a node with one child
	 * @param tbd
	 */
	public void deleteOneChild(Node tbd)
	{
		if(tbd.left != null)
		{
			root = tbd.left;
			root.parent = null;
		}
		else
		{
			root = tbd.right;
			root.parent = null;
		}
	}
	
	/**
	 * delete a node with two child
	 * @param tbd
	 */
	public void deleteTwoChildren(Node tbd)
	{
		Node parent = tbd;
		Node temp = tbd.right;
		while(temp.left != null)
		{
			parent = temp;
			temp = temp.left;
		}
		tbd.key = temp.key;
		tbd.def = temp.def;
		tbd.down = temp.down;
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
