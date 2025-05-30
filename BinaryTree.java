import java.util.ArrayList;

/**
 *	Binary Tree of Comparable values.
 *	The tree only has unique values. It does not add duplicate values.
 *
 *	@author	Ani Kumar
 *	@since	May 14, 2025
 */
public class BinaryTree<E extends Comparable<E>> {

	private TreeNode<E> root;		// the root of the tree

	private final int PRINT_SPACES = 3;	// print spaces between tree levels
										// used by printTree()

	/**	constructor for BinaryTree */
	public BinaryTree() {}

	/**	Field accessors and modifiers */

	/**
	 * Returns root of the tree.
	 * @return 			Root TreeNode
	 */
	public TreeNode<E> getRoot() {
		return root;
	}

	/**	Add a node to the tree.
	 *	@param value		the value to put into the tree
	 */
	public void add(E value) {
		root = add(root, value);
	}

	/**
	 * Recursive add method.
	 * @param node		Current node
	 * @param value		Value to add
	 * @return 			Updated TreeNode to be linked to parent
	 */
	private TreeNode<E> add(TreeNode<E> node, E value) {
		if (node == null)
			return new TreeNode<>(value);
		int compare = value.compareTo(node.getValue());
		if (compare < 0) // go left
			node.setLeft(add(node.getLeft(), value));
		else if (compare > 0) // go right
			node.setRight(add(node.getRight(), value));
		return node;
	}

	/**
	 * Searches for a value in the tree.
	 * @param value 		Value to search for
	 * @return				TreeNode containing value, null if not found
	 */
	public TreeNode<E> search(E value) {
		return search(root, value);
	}

	/**
	 * Recursive search method.
	 * @param node			Current node
	 * @param value			Value to search for
	 * @return				TreeNode containing value, null if not found
	 */
	private TreeNode<E> search(TreeNode<E> node, E value) {
		if (node == null)
			return null;
		int compare = value.compareTo(node.getValue());
		if (compare == 0)
			return node;
		else if (compare < 0)
			return search(node.getLeft(), value);
		else
			return search(node.getRight(), value);
	}

	/**
	 *	Print Binary Tree Inorder
	 */
	public void printInorder() {
		printInorder(root);
	}

	/**
	 * Recursive inorder traversal method.
	 * @param node		Current node
	 */
	private void printInorder(TreeNode<E> node) {
		if (node == null)
			return;
		printInorder(node.getLeft());
		System.out.print(node.getValue() + " ");
		printInorder(node.getRight());
	}

	/**
	 *	Print Binary Tree Preorder
	 */
	public void printPreorder() {
		printPreorder(root);
	}

	/**
	 * Recursive preorder traversal method.
	 * @param node		Current node
	 */
	private void printPreorder(TreeNode<E> node) {
		if (node == null)
			return;
		System.out.print(node.getValue() + " ");
		printPreorder(node.getLeft());
		printPreorder(node.getRight());
	}

	/**
	 *	Print Binary Tree Postorder
	 */
	public void printPostorder() {
		printPostorder(root);
	}

	/**
	 * Recursive postorder traversal method.
	 * @param node		Current node
	 */
	private void printPostorder(TreeNode<E> node) {
		if (node == null)
			return;
		printPostorder(node.getLeft());
		printPostorder(node.getRight());
		System.out.print(node.getValue() + " ");
	}

	/**	Return a balanced version of this binary tree
	 *	@return			Balanced tree
	 */
	public BinaryTree<E> makeBalancedTree() {
		BinaryTree<E> balancedTree = new BinaryTree<>();
		ArrayList<E> sortedValues = new ArrayList<>();
		getInorderTraversal(root, sortedValues);
		balancedTree.root = buildBalancedTree(sortedValues, 0, sortedValues.size() - 1);
		return balancedTree;
	}

	/**
	 * Helper method to perform inorder traversal and collect values into an ArrayList.
	 * @param node		Current node
	 * @param list 		ArrayList to store values in
	 */
	private void getInorderTraversal(TreeNode<E> node, ArrayList<E> list) {
		if (node == null)
			return;
		getInorderTraversal(node.getLeft(), list);
		list.add(node.getValue());
		getInorderTraversal(node.getRight(), list);
	}

	/**
	 * Helper method to build a balanced BST from a sorted ArrayList.
	 * @param sortedValues 	Sorted list of values
	 * @param start 		Starting index of sub array
	 * @param end 			Ending index of sub array
	 * @return 				Root of the balanced subtree
	 */
	private TreeNode<E> buildBalancedTree(ArrayList<E> sortedValues, int start, int end) {
		if (start > end)
			return null;
		int mid = start + (end - start) / 2;
		TreeNode<E> node = new TreeNode<>(sortedValues.get(mid));
		node.setLeft(buildBalancedTree(sortedValues, start, mid - 1));
		node.setRight(buildBalancedTree(sortedValues, mid + 1, end));
		return node;
	}

	/**
	 *	Remove value from Binary Tree
	 *	@param value	Value to remove from tree
	 *	Precondition: value exists in tree
	 */
	public void remove(E value) {
		root = remove(root, value);
	}
	/**
	 *	Remove value from Binary Tree
	 *	@param node		Root of subtree
	 *	@param value	Value to remove from subtree
	 *	@return			TreeNode that connects to parent
	 */
	public TreeNode<E> remove(TreeNode<E> node, E value) {
		if (node == null)
			return null;
		int cmp = value.compareTo(node.getValue());
		if (cmp < 0) // val in left
			node.setLeft(remove(node.getLeft(), value));
		else if (cmp > 0) // val in right
			node.setRight(remove(node.getRight(), value));
		else { // found val
			if (node.getLeft() == null)
				return node.getRight();
			else if (node.getRight() == null)
				return node.getLeft();
			TreeNode<E> successorNode = findMin(node.getRight());
			TreeNode<E> newNode = new TreeNode<>(successorNode.getValue());
			newNode.setLeft(node.getLeft());
			newNode.setRight(remove(node.getRight(), successorNode.getValue()));
			return newNode;
		}
		return node;
	}

	/**
	 * Helper method to find node with minimum value in a subtree.
	 * Used for finding the inorder successor.
	 * @param node 		Root of subtree to search
	 * @return 			TreeNode with minimum value
	 */
	private TreeNode<E> findMin(TreeNode<E> node) {
		while (node.getLeft() != null)
			node = node.getLeft();
		return node;
	}

	/*******************************************************************************/
	/********************************* Utilities ***********************************/
	/*******************************************************************************/
	/**
	 *	Print binary tree
	 *
	 *	Prints in vertical order, top of output is right-side of tree,
	 *			bottom is left side of tree,
	 *			left side of output is root, right side is deepest leaf
	 *	Example Integer tree:
	 *			  11
	 *			/	 \
	 *		  /		   \
	 *		5			20
	 *				  /	  \
	 *				14	   32
	 *
	 *	would be output as:
	 *
	 *				 32
	 *			20
	 *				 14
	 *		11
	 *			5
	 ***********************************************************************/
	public void printTree() {
		printLevel(root, 0);
	}

	/**
	 *	Recursive node printing method
	 *	Prints reverse order: right subtree, node, left subtree
	 *	Prints the node spaced to the right by level number
	 *	@param node		root of subtree
	 *	@param level	level down from root (root level = 0)
	 */
	private void printLevel(TreeNode<E> node, int level) {
		if (node == null) return;
		// print right subtree
		printLevel(node.getRight(), level + 1);
		// print node: print spaces for level, then print value in node
		for (int a = 0; a < PRINT_SPACES * level; a++) System.out.print(" ");
		System.out.println(node.getValue());
		// print left subtree
		printLevel(node.getLeft(), level + 1);
	}
}