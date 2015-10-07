/*
 * Author: Obed Josiah Tandadjaja
 * COS230 - Assingment 4: AVL Tree
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class AVLTree implements Tree {

	Node root;
	Stack ll;
	
	/**
	 * Constructor
	 */
	AVLTree()
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
		// empty stack
		ll.empty();
		// if root is null then put it in the root
		if(root == null)
		{
			root = new Node(key, def);
			root.AVLvalue = 0;
		}
		else
		{
			// loop through the tree to get to the proper position for the word
			Node current = root;
			while(true)
			{
				if(key.toLowerCase().compareTo(current.key.toLowerCase()) > 0)
				{
					ll.add(false);
					if(current.left == null)
					{
						current.left = new Node(key, def, current);
						// update the AVL value
						updateAVLvalue(current.left);
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
						// update the AVL value
						updateAVLvalue(current.right);
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
	 * Update the AVL value of the tree starting with node n
	 * @param n - inserted node
	 */
	public void updateAVLvalue(Node n)
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
				
				// going right
				if(ll.pop())
				{
					System.out.println(temp.parent.key+" "+temp.parent.AVLvalue);
					if(temp.parent.key.equalsIgnoreCase(root.key))
					{
						// right = +1
						root.AVLvalue += 1;
						// when AVL value is 2 then rotate the node
						if(root.AVLvalue == 2)
						{
							root.AVLvalue = 0;
							rotateLeft(root);
							break;
						}
					}
					else
					{
						temp.parent.AVLvalue += 1;
						if(temp.parent.AVLvalue == 2)
						{
							temp.parent.AVLvalue = 0;
							rotateLeft(temp.parent);
							break;
						}
					}
				}
				
				// going left
				else
				{
					if(temp.parent.key.equalsIgnoreCase(root.key))
					{
						// left = -1
						root.AVLvalue -= 1;
						// when the AVL value is -2 then rotate the node
						if(root.AVLvalue == -2)
						{
							root.AVLvalue = 0;
							rotateRight(root);
							break;
						}
					}
					else
					{
						temp.parent.AVLvalue -= 1;
						if(temp.parent.AVLvalue == -2)
						{
							temp.parent.AVLvalue = 0;
							rotateRight(temp.parent);
							break;
						}
					}
				}
				temp = temp.parent;
			}
		}
	}
	
	/**
	 * check AVL value if it is correct
	 * @param n - node
	 */
	public void checkAVLvalue(Node n)
	{
		if(n != null)
		{
			if(n.left != null && n.right != null)
			{
				n.AVLvalue = 0;
			}
			else if(n.left != null)
			{
				n.AVLvalue = -1;
			}
			else if(n.right != null)
			{
				n.AVLvalue = 1;
			}
			else
			{
				n.AVLvalue = 0;
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
			parent.right.AVLvalue += 1;
		}
		if(parent.left != null)
		{
			parent.key = parent.left.key;
			parent.down = parent.left.down;
			parent.def = parent.left.def;
			
			if(parent.left.right != null)
			{
				parent.right.left = parent.left.right;
				parent.right.left.parent = parent.right;
				parent.right.AVLvalue -= 1;
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
		checkAVLvalue(parent);
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
			parent.left.AVLvalue -= 1;
		}
		if(parent.right != null)
		{
			parent.key = parent.right.key;
			parent.down = parent.right.down;
			parent.def = parent.right.def;
			
			if(parent.right.left != null)
			{
				parent.left.right = parent.right.left;
				parent.left.right.parent = parent.left;
				parent.left.AVLvalue += 1;
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
		checkAVLvalue(parent);
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
	 * print all the words in the dictionary in ascending order
	 */
	@Override
	public void list() {
		// TODO Auto-generated method stub
		inorder(root);
	}

	/**
	 * print particular words in the dictionary in ascending order
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
	 * preorder traversal
	 * @param c - root
	 * @param indent - indentation
	 */
	void preorder(Node c, String indent){
		if(c != null) 
		{
			System.out.println(indent + c.key +" "+ c.AVLvalue);
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
	 * inorder traversal for particular words
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
	}

	/**
	 * finding the parent of a node
	 * @param key
	 * @return node
	 */
	public Node findParent(String key)
	{
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
	 * deleting a node with no child
	 * @param tbd
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
					temp.AVLvalue += 1;
				}
				else
				{
					temp.right = null;
					temp.AVLvalue -= 1;
				}
			}
			else if(temp.left != null)
			{
				temp.left = null;
				temp.AVLvalue += 1;
			}
			else
			{
				temp.right = null;
				temp.AVLvalue -= 1;
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
		if(tbd.left != null)
		{
			tbd.key = tbd.left.key;
			tbd.def = tbd.left.def;
			tbd.down = tbd.left.down;
			tbd.AVLvalue = tbd.left.AVLvalue;
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
		else
		{
			tbd.key = tbd.right.key;
			tbd.def = tbd.right.def;
			tbd.down = tbd.right.down;
			tbd.AVLvalue = tbd.right.AVLvalue;
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
