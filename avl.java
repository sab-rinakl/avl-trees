import java.util.*;

class Node 
{
	int item, height;
	Node left, right;

	Node(int d) 
	{
		item = d;
		height = 1;
	}
}

class AVLTree 
{
	Node root;

	// returns tree height
	int height(Node N) 
	{
		if (N == null)
			return 0;
		return N.height;
	}

	// returns the larger of the inputed values
	int max(int a, int b) 
	{
		if (a > b)
		{
			return a;
		}
		return b;
	}

	// rotates subtree right
	Node rightRotate(Node y) 
	{
		Node x = y.left;
		Node T2 = x.right;
		x.right = y;
		y.left = T2;
		y.height = max(height(y.left), height(y.right)) + 1;
		x.height = max(height(x.left), height(x.right)) + 1;
		return x;
	}

	// rotates subtree left
	Node leftRotate(Node x) 
	{
		Node y = x.right;
		Node T2 = y.left;
		y.left = x;
		x.right = T2;
		x.height = max(height(x.left), height(x.right)) + 1;
		y.height = max(height(y.left), height(y.right)) + 1;
		return y;
	}

	// get balance factor of a node
	int getBalanceFactor(Node N) 
	{
		if (N == null)
		{
			return 0;
		}
		return height(N.left) - height(N.right);
	}

	// insert a node
	Node insertNode(Node node, int item) 
	{
		// insert
		if (node == null)
		{
			return (new Node(item));
		}
		if (item < node.item)
		{
			node.left = insertNode(node.left, item);
		}
		else if (item > node.item)
		{
			node.right = insertNode(node.right, item);
		}
		else
		{
			return node;
		}

		// update the balance factor of nodes and balance tree
		node.height = 1 + max(height(node.left), height(node.right));
		int balanceFactor = getBalanceFactor(node);
		if (balanceFactor > 1) 
		{
			if (item < node.left.item) 
			{
				return rightRotate(node);
			} 
			else if (item > node.left.item) 
			{
				node.left = leftRotate(node.left);
				return rightRotate(node);
			}
		}
		if (balanceFactor < -1) 
		{
			if (item > node.right.item) 
			{
				return leftRotate(node);
			} 
			else if (item < node.right.item) 
			{
				node.right = rightRotate(node.right);
				return leftRotate(node);
			}
		}
		return node;
	}

	// returns node with smallest value   
	Node nodeWithSmallestValue(Node node) 
	{
		Node curr = node;
		while (curr.left != null)
		{
			curr = curr.left;
		}
		return curr;
	}

	// Delete a node
	Node deleteNode(Node root, int item) 
	{
		// Find the node to be deleted and remove it
		if (root == null)
		{
			return root;
		}
		if (item < root.item)
		{
			root.left = deleteNode(root.left, item);
		}
		else if (item > root.item)
		{
			root.right = deleteNode(root.right, item);
		}
		else 
		{
			if ((root.left == null) || (root.right == null)) 
			{
				Node temp = null;
				if (temp == root.left)
				{
					temp = root.right;
				}	
				else
				{
					temp = root.left;
				}
				if (temp == null) 
				{
					temp = root;
					root = null;
				} 
				else
				{
					root = temp;
				}	
			} 
			else 
			{
				Node temp = nodeWithSmallestValue(root.right);
				root.item = temp.item;
				root.right = deleteNode(root.right, temp.item);
			}
		}
		if (root == null)
		{
			return root;
		}

		// update the balance factor of each node and balance the tree
		root.height = max(height(root.left), height(root.right)) + 1;
		int balanceFactor = getBalanceFactor(root);
		if (balanceFactor > 1) 
		{
			if (getBalanceFactor(root.left) >= 0) 
			{
				return rightRotate(root);
			} 
			else 
			{
				root.left = leftRotate(root.left);
				return rightRotate(root);
			}
		}
		if (balanceFactor < -1) 
		{
			if (getBalanceFactor(root.right) <= 0) 
			{
				return leftRotate(root);
			} 
			else 
			{
				root.right = rightRotate(root.right);
				return leftRotate(root);
			}
		}
		return root;
	}

	void preOrder(Node node) 
	{
		if (node != null) 
		{
			System.out.print(node.item + " ");
			preOrder(node.left);
			preOrder(node.right);
		}
	}

	// print tree
	private void printTree(Node curr, String space, boolean last) 
	{
		if (curr != null) 
		{
			System.out.print(space);
			if (last) 
			{
				System.out.print("R----");
				space += "   ";
			} 
			else 
			{
				System.out.print("L----");
				space += "|  ";
			}
			System.out.println(curr.item);
			printTree(curr.left, space, false);
			printTree(curr.right, space, true);
		}
	}

	// driver code
	public static void main(String[] args) 
	{
		Random rand = new Random();
		AVLTree tree = new AVLTree();
		int r;
		for (int i = 0; i < 7; i++)
		{
			r = rand.nextInt(100);
			tree.root = tree.insertNode(r);
		}
		tree.printTree(tree.root, "", true);
		tree.root = tree.deleteNode(tree.root, r);
		System.out.println("After Deletion of " + r +": ");
		tree.printTree(tree.root, "", true);
	}
}