/*
 * Author: Obed Josiah Tandadjaja
 * Assignment 4: Dictionary - Binary Search Tree
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class BSTree implements Tree
{
	Node root;
	
	/**
	 * Constructor
	 */
	public BSTree()
	{
		root = null;
	}
	
	/**
	 * Add items to the tree
	 * @param key (String) - name of the word to be added
	 * @param def (String) - definition of the word to be added
	 */
	@Override
	public void add(String key, String def)
	{
		// root = word if root = null
		if(root == null)
		{
			root = new Node(key, def);
		}
		else
		{
			// loop through the tree to find the position
			Node current = root;
			while(true)
			{
				if(key.toLowerCase().compareTo(current.key.toLowerCase()) > 0)
				{
					if(current.left == null)
					{
						current.left = new Node(key, def);
						break;
					}
					else
					{
						current = current.left;
					}
				}
				else if(key.toLowerCase().compareTo(current.key.toLowerCase()) < 0)
				{
					if(current.right == null)
					{
						current.right = new Node(key, def);
						break;
					}
					else
					{
						current = current.right;
					}
				}
				// if the word exists in the table then current.down = new word
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
	 * preorder traversal to show the tree
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
	 * inorder traversal to sort and print the whole tree
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
	 * inorder traversal to sort and print the a portion of the tree 
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
				System.out.println(c.key);
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
	 * finding a word in the tree and printing its definition
	 * if not found it will print out "Find Failed!"
	 * @param key (String) - the word itself
	 */
	@Override
	public Node find(String key) {
		// TODO Auto-generated method stub
		
		// loop through the tree to get the position of the word
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
		// if not found print...
		System.out.println("Find failed!");
		return null;
	}

	/**
	 * List all the items in the tree in order
	 */
	@Override
	public void list() {
		// TODO Auto-generated method stub
		inorder(root);
	}

	/**
	 * List a portion of the tree in order
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
	 * Loading the commands from the command files
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
	 * draws the tree
	 */
	@Override
	public void tree() {
		// TODO Auto-generated method stub
		preorder(root, "");
	}

	/**
	 * deleting an item from the tree
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
	}
	
	/**
	 * finds the parent of a particular node
	 * @param key
	 * @return parent
	 */
	public Node findParent(String key)
	{
		// loop through the tree to get to the position of the word
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
		// if no parent found then print...
		System.out.println("Find failed!");
		return null;
	}
	
	/**
	 * deleting a node with no child
	 * @param tbd
	 */
	public void deleteNoChild(Node tbd)
	{
		// get the parent
		Node temp = findParent(tbd.key);
		if(temp != null)
		{
			// if parent has two children
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
			// if parent has one child
			// if parent has left child
			else if(temp.left != null)
			{
				temp.left = null;
			}
			// if parent has right child
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
	 * deleting node with one child
	 * @param tbd
	 */
	public void deleteOneChild(Node tbd)
	{
		// if the child is a left child
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
		// if the child is a right child
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
	 * @param tbd
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
		// removing the word with the values of the successor
		tbd.key = temp.key;
		tbd.def = temp.def;
		tbd.down = temp.down;
		if(temp.right != null)
		{
			parent.left = temp.right;
		}
		else
		{
			parent.left = null;
		}
	}

}