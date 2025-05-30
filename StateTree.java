import java.util.Scanner;

/**
 *	The driver program for creating and manipulating
 *	a binary tree of state information.
 *
 *	@author	Ani Kumar
 *	@since	May 21, 2025
 */
public class StateTree {
	// Fields
	private BinaryTree<State> bTree;
	private final String IN_FIlE = "states2.txt";	// input file

	public StateTree() {
		bTree = new BinaryTree<>();
	}

	public static void main (String[] args) {
		StateTree treeOrder = new StateTree();
		treeOrder.mainMenu();
	}

	public void mainMenu () {
		String choice;
		do {
			System.out.println("Binary Tree algorithm menu\n");
			System.out.println("(1) Read Data from a file");
			System.out.println("(2) Print the list");
			System.out.println("(3) Search the list");
			System.out.println("(4) Delete node");
			System.out.println("(5) Count nodes");
			System.out.println("(6) Clear the list");
			System.out.println("(7) Print the level");
			System.out.println("(8) Print depth of tree");
			System.out.println("(Q) Quit\n");
			choice = Prompt.getString("Choice");
			System.out.println();
			if ('1' <= choice.charAt(0) && choice.charAt(0) <= '8') {
				switch (choice.charAt(0)) {
					case '1' :
						loadData();
						break;
					case '2' :
						System.out.println();
						System.out.println("The tree printed inorder\n");
						bTree.printInorder();
						System.out.println();
						break;
					case '3' :
						find();
						break;
					case '4' :
						delete();
						break;
					case '5' :
						System.out.println("Number of nodes = " + size(bTree.getRoot()));
						System.out.println();
						break;
					case '6' :
						clear();
						break;
					case '7' :
						printLevel();
						break;
					case '8' :
						if (depth(bTree.getRoot(), -1) > -1)
							System.out.println("Depth of tree = " + depth(bTree.getRoot(), -1));
						else
							System.out.println("Tree empty");
						System.out.println();
						break;
				}
			}
		}
		while (choice.charAt(0) != 'Q' && choice.charAt(0) != 'q');
	}

	/**	Load the data into the binary tree */
	public void loadData() {
		Scanner input = FileUtils.openToRead(IN_FIlE);
		while (input.hasNext()) {
			String name = input.next();
			String abbreviation = input.next();
			int population = input.nextInt();
			int area = input.nextInt();
			int reps = input.nextInt();
			String capital = input.next();
			int month = input.nextInt();
			int day = input.nextInt();
			int year = input.nextInt();
			State state = new State(name, abbreviation, population, area, reps, capital, month, day, year);
			bTree.add(state);
		}
		input.close();
		System.out.println("Database " + IN_FIlE + " is loaded!!\n");
	}

	/**	Find the node in the tree */
	public void find() {
		String name = Prompt.getString("Enter state name to search (Q to quit)").trim();
		while (!name.equals("Q")) {
			TreeNode<State> treeNode = bTree.getRoot();
			State found = find(treeNode, name);
			if (found == null)
				System.out.println("\n" + name + " not found\n");
			else
				System.out.println("\n" + found);
			name = Prompt.getString("Enter state name to search (Q to quit)").trim();
		}
	}

	/**	Returns the number of nodes in the subtree - recursive
	 *	@param node		Root of subtree
	 *  @param name		Target name to search for
	 *  @return			State if found, null otherwise
	 */
	private State find(TreeNode<State> node, String name) {
		if (node == null)
			return null;
		int compare = name.compareToIgnoreCase(node.getValue().getName());
		if (compare == 0)
			return node.getValue();
		else if (compare < 0)
			return find(node.getLeft(), name);
		else
			return find(node.getRight(), name);
	}

	/** Delete a node */
	public void delete() {
		String name = Prompt.getString("Enter state name to delete (Q to quit)").trim();
		while (!name.equals("Q")) {
			State defaultState = new State(name, "", 0, 0, 0, "", 0, 0, 0);
			if (bTree.search(defaultState) != null) {
				bTree.remove(defaultState);
				System.out.println("\n" + name + " has been deleted!!\n");
			} else
				System.out.println("\n" + name + " not found\n");
			name = Prompt.getString("Enter state name to search (Q to quit)").trim();
		}
	}

	/**	Returns the number of nodes in the subtree - recursive
	 *	@param node		Root of subtree
	 *	@return			Number of nodes in subtree
	 */
	public int size(TreeNode<State> node) {
		if (node == null)
			return 0;
		return size(node.getLeft()) + size(node.getRight()) + 1;
	}

	/**	Clear out the binary tree */
	public void clear() {
		bTree = new BinaryTree<>();
	}

	/** Print the level requested */
	public void printLevel() {
		int level;
		while ((level = Prompt.getInt("Enter level value to print (-1 to quit)")) != -1) {
			System.out.println("\nLevel " + level);
			printLevel(bTree.getRoot(), 0, level);
			System.out.println("\n");
		}
		System.out.println("\n");
	}

	/** Print the level requested */
	private void printLevel(TreeNode<State> node, int current, int target) {
		if (node == null)
			return;
		if (current == target)
			System.out.print(node.getValue().getName() + "  ");
		else {
			printLevel(node.getLeft(), current + 1, target);
			printLevel(node.getRight(), current + 1, target);
		}
	}

	/**
	 * Returns the depth of the subtree - recursive
	 *
	 * @param node		Root of subtree
	 * @return			Depth of subtree
	 */
	public int depth(TreeNode<State> node, int depth) {
		if (node == null)
			return depth;
		return Math.max(depth(node.getLeft(), depth + 1), depth(node.getRight(), depth + 1));
	}
}