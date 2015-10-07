/*
 * Author: Obed Josiah Tandadjaja
 * Assignment 4: Dictionary
 */

public class Dictionary {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// error handling if no arguments received
		if(args.length == 0)
		{
			System.out.println("No Command Line Arguments received!");
			System.out.println("Input format: Dictionary <tree type> <command file name(s)>");
			System.out.println("Please try again!");
		}
		else
		{
			// initializing the trees
			String treeType = args[0].toString();
			if("bst".equalsIgnoreCase(treeType))
			{
				Tree t = new BSTree();
				getAllCommandFiles(t, args);
			}
			else if("rb".equalsIgnoreCase(treeType))
			{
				Tree t = new RBTree();
				getAllCommandFiles(t, args);
			}
			else if("avl".equalsIgnoreCase(treeType))
			{
				Tree t = new AVLTree();
				getAllCommandFiles(t, args);
			}
			else if("splay".equalsIgnoreCase(treeType))
			{
				Tree t = new SplayTree();
				getAllCommandFiles(t, args);
			}
			// another error handling if the tree type input is wrong
			else
			{
				System.out.println("Wrong Input! Please input bst, rb, avl, or splay");
				System.exit(0);
			}
		}
	}
	
	/**
	 * Get all the command files and load them up to the tree
	 * @param t - initialized tree
	 * @param args - command line arguments
	 */
	public static void getAllCommandFiles(Tree t, String[] args)
	{
		for(int i = 1; i < args.length; i++)
		{
			t.load(args[i]);
		}
	}
	
}
