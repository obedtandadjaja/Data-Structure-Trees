/*
 * Author: Obed Josiah Tandadajaja
 * COS230 - Assignment 4: Stack
 */

public class Stack {

	Node head;
	
	/**
	 * Constructor
	 */
	Stack()
	{
		head = null;
	}
	
	/**
	 * push a node to the stack
	 * @param bool
	 */
	public void add(boolean bool)
	{
		if(head == null)
		{
			head = new Node(bool, null);
		}
		else
		{
			head = new Node(bool, head);
		}
	}
	
	/**
	 * pop a node out of the stack
	 * @return
	 */
	public boolean pop()
	{
		Node temp = head;
		if(head.right != null)
		{
			head = head.right;
		}
		else
		{
			head = null;
		}
		return temp.lr;
	}
	
	/**
	 * peek to the first value of the stack
	 * @return bool
	 */
	public boolean peek()
	{
		return head.lr;
	}
	
	/**
	 * check the size of the stack
	 * @return int
	 */
	public int size()
	{
		if(head != null)
		{
			Node temp = head;
			int sum = 1;
			while(temp.right != null)
			{
				sum++;
				temp = temp.right;
			}
			return sum;
		}
		else
		{
			return 0;
		}
	}
	
	/**
	 * empty the stack
	 */
	public void empty()
	{
		while(head != null)
		{
			pop();
		}
	}
}
