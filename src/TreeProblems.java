import java.util.HashMap;
import java.util.Map;

public class TreeProblems {

	public TreeNode createBSTFromSortedArray(int low, int high, int[] a) {

		if (low <= high) {
			int mid = (low + high) / 2;
			TreeNode root = new TreeNode(a[mid]);
			root.left = createBSTFromSortedArray(low, mid - 1, a);
			root.right = createBSTFromSortedArray(mid + 1, high, a);
			return root;
		}
		return null;
	}

	// left, root, right
	public void inOrder(TreeNode root) {
		if (root != null) {
			inOrder(root.left);
			System.out.print(root.data + "--");
			inOrder(root.right);
		}
	}

	// root, left, right
	public void preOrder(TreeNode root) {
		if (root != null) {
			System.out.print(root.data + "--");
			inOrder(root.left);
			inOrder(root.right);
		}
	}

	// left, right, root
	public void postOrder(TreeNode root) {
		if (root != null) {
			inOrder(root.left);
			inOrder(root.right);
			System.out.print(root.data + "--");
		}
	}
	
	// Subtree of Another Tree: Consider t2 to be the smaller tree(subtree)
	public boolean SubtreeOfAnotherTree(TreeNode t1, TreeNode t2) {
		if(t1 == null && t2 == null)
			return true;
		if(t1 == null || t2 == null)
			return false;
		
		StringBuilder sb1 = new StringBuilder();
		StringBuilder sb2 = new StringBuilder();
		
		preOrderhelper(t1, sb1);
		preOrderhelper(t2, sb2);
		
		if(sb1.toString().contains(sb2.toString()))
			return true;
		return false;
	}
	
	private void preOrderhelper(TreeNode t1, StringBuilder s) {
		if(t1 != null) {
			s.append("#"+t1.data);
			preOrderhelper(t1.left, s);
			preOrderhelper(t1.right, s);
		}
		s.append("#null");
	}
	
	// Subtree of Another Tree : recursion
	public boolean SubtreeOfAnotherTreeRecursion(TreeNode t1, TreeNode t2) {
		if(t1 == null && t2 == null)
			return true;
		if(t2 == null)
			return false;
		return traverse(t1,t2);
	}
	
	
	private boolean traverse(TreeNode t1, TreeNode t2) {
		return t1 != null &&
				(equals(t1,t2) || traverse(t1.left,t2) || traverse(t1.right,t2));
	}


	private boolean equals(TreeNode t1, TreeNode t2) {
		if(t1 == null && t2 == null)
			return true;
	
		if(t1 == null || t2 == null)
			return false;
		
		return (t1.data == t2.data) && equals(t1.left, t2.left) && equals(t1.right, t2.right);
	}


	//Construct Binary Tree from Inorder and Postorder Traversal
	private int[] postOrder;
	private int postOrderLastIndexpos;
	Map<Integer,Integer> map;
	public TreeNode constructTreefromInorderAndPostorderTraversal(int[] inOrder, int[] postOrder) {
		this.postOrder = postOrder;
		// inOrder and postOrder lastIndex would be same
		postOrderLastIndexpos = postOrder.length-1;
		
		int low = 0;
		int high = postOrderLastIndexpos; // You can also set it to inOrderLastIndex
		
		map = new HashMap<Integer, Integer>();
		for(int i=0;i<inOrder.length;i++) {
			map.put(inOrder[i], i);
		}
		
		return helperConstructTreefromInorderAndPostorderTraversal(low,high);
		
	}

	private TreeNode helperConstructTreefromInorderAndPostorderTraversal(int low, int high) {
		if(low > high)
			return null;
		
		int rootVal = postOrder[postOrderLastIndexpos];
		
		TreeNode root = new TreeNode(rootVal);
		
		int rootPosInOrderArray = map.get(rootVal);
		
		postOrderLastIndexpos--;
		root.right = helperConstructTreefromInorderAndPostorderTraversal(rootPosInOrderArray+1, high);
		root.left = helperConstructTreefromInorderAndPostorderTraversal(low, rootPosInOrderArray-1);
		return root;
	}
	
	//Construct Binary Tree from Inorder and Preorder Traversal
	private int[] preOrder;
	private int preOrderLastIndexpos;
	private TreeNode constructTreefromInorderAndPreOrderTraversal(int[] inOrder, int[] preOrder) {
		if(inOrder.length == 0)
			return null;
		
		map = new HashMap<Integer, Integer>();
		this.preOrder = preOrder;
		preOrderLastIndexpos = 0;
		
		for(int i=0;i<inOrder.length;i++) {
			map.put(inOrder[i], i);
		}
		
		return helperConstructTreefromInorderAndPreOrderTraversal(0,inOrder.length-1);
	}

	private TreeNode helperConstructTreefromInorderAndPreOrderTraversal(int low, int high) {
		
		if(low > high)
			return null;
		
		//first element of the preOrder is the root
		int rootVal = preOrder[preOrderLastIndexpos];
		TreeNode root = new TreeNode(rootVal);
		
		int rootPosInorderArray = map.get(rootVal);
		
		preOrderLastIndexpos++;
		root.left = helperConstructTreefromInorderAndPreOrderTraversal(low, rootPosInorderArray-1);
		root.right = helperConstructTreefromInorderAndPreOrderTraversal(rootPosInorderArray+1, high);
		return root;
	}

	public static void main(String[] args) {
		TreeProblems solutions = new TreeProblems();
		int[] a = new int[] { 1, 2, 3, 4, 5 };
		TreeNode root = solutions.createBSTFromSortedArray(0, a.length - 1, a);
		solutions.inOrder(root);
		System.out.println();
		solutions.preOrder(root);
		System.out.println();
		solutions.postOrder(root);
		
		// SubtreeOfAnotherTree
		TreeNode t1 = new TreeNode(3);
		t1.left = new TreeNode(4);
		t1.right = new TreeNode(5);
		t1.left.left = new TreeNode(1);
		t1.left.right = new TreeNode(2);
//		t1.left.right.left = new TreeNode(0);
		TreeNode t2 = new TreeNode(4);
		t2.left = new TreeNode(1);
		t2.right = new TreeNode(2);
//		boolean result = solutions.SubtreeOfAnotherTree(t1, t2);
		boolean result = solutions.SubtreeOfAnotherTreeRecursion(t1, t2);
		System.out.println();
		if(result) {
			System.out.println("It's a subtree");
		}
		else {
			System.out.println("It's not a subtree");
		}
		//Construct Binary Tree from Inorder and Postorder Traversal
		int[] inOrder = new int[] {9,3,15,20,7};
		int[] postOrder = new int[] {9,15,7,20,3};
		
		root = solutions.constructTreefromInorderAndPostorderTraversal(inOrder, postOrder);
		System.out.println("Construct Binary Tree from Inorder and Postorder Traversal");
		solutions.inOrder(root);
		System.out.println();
		
		//Construct Binary Tree from Inorder and Postorder Traversal
		int[] preOrder = new int[] {3,9,20,15,7};
		root = solutions.constructTreefromInorderAndPreOrderTraversal(inOrder, preOrder);
		System.out.println("Construct Binary Tree from Inorder and Postorder Traversal");
		solutions.inOrder(root);
		
	}

}
