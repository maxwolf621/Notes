# Tree
- [Tree](#tree)
  - [Balanced Binary Tree](#balanced-binary-tree)
  - [Validate Binary Search Tree](#validate-binary-search-tree)
    - [Maximum Depth of Binary Tree](#maximum-depth-of-binary-tree)
  - [Minimum Depth of Binary Tree](#minimum-depth-of-binary-tree)
  - [Diameter of Binary Tree](#diameter-of-binary-tree)
    - [Binary Tree Upside Down](#binary-tree-upside-down)
    - [Invert Binary Tree](#invert-binary-tree)
    - [Merge Two Binary Trees](#merge-two-binary-trees)
## Balanced Binary Tree

Given a binary tree, determine if it is height-balanced.
- a height-balanced binary tree is defined which the left and right subtrees of every node differ in height by no more than 1.

```java
              3
             / \
            9  20
              /  \
             15   7
        Input: root = [3,9,20,null,null,15,7]
        Output: true
```

```java
// Compare each node's height starting 
public boolean isBalanced(TreeNode root) {
    if (root == null) return true;

    return (Math.abs(height(root.left)- height(root.right)) <= 1) && 
            isBalanced(root.left) && 
            isBalanced(root.right);
}


public int height(TreeNode root){        

    if(root == null) return 0;

    return Math.max(height(root.left), height(root.right)) + 1;
}
```

## Validate Binary Search Tree

- [Validate Binary Search Tree](https://leetcode.com/problems/validate-binary-search-tree/discuss/32112/Learn-one-iterative-inorder-traversal-apply-it-to-multiple-tree-questions-(Java-Solution))

Given a binary tree, determine if it is a valid Binary Search Tree (BST).

Properties Of BST
- The left subtree of a node contains only nodes with keys **less** than the node's key.
- The right subtree of a node contains only nodes with keys **greater** than the node's key.
- Both the left and right subtrees must also be binary search trees.


```java 
invalidate BST
    10
   /  \
  1   18
      / \
    "5"  30  
     
Validate BST
    10
   /  \
  5   18
      / \
    15   30

public boolean isValidBST(TreeNode root) {
   if (root == null) return true;
   
   Stack<TreeNode> stack = new Stack<>();
   
   TreeNode pre = null;
   
   while (root != null || !stack.isEmpty()) {
      while (root != null) {
         stack.push(root);
         
         root = root.left;
      }
      
      root = stack.pop();
      if(pre != null && root.val <= pre.val) return false;
      
      pre = root;
      root = root.right;
   }
   
   return true;
}
```


### Maximum Depth of Binary Tree
- [Maximum Depth of Binary Tree](https://leetcode.com/problems/maximum-depth-of-binary-tree/)
```java
/**
   Maximum Depth of Binary Tree (Easy)
              3
             / \
            9  20
              /  \
             15   7
 */
public int maxDepth(TreeNode root) {
    if(root == null) return 0; // leaf 
    return Math.max(maxDepth(root.left) , maxDepth(root.right)) + 1; 
}
```

## Minimum Depth of Binary Tree

```java
// Depth-First traversal
public int minDepth(TreeNode root) {
    if(root == null) return 0;

    if(root.left == null)  return minDepth(root.right) + 1; // +1 parent 
    if(root.right== null)  return minDepth(root.left)  + 1; // +1 parent 

    return Math.min(minDepth(root.right), minDepth(root.left)) + 1;
}
```

## Diameter of Binary Tree

The diameter of a binary tree is the length of the longest path between any two nodes in a tree. 

```java
Diameter = MAX(Diameter, leftDepth + rightDepth)
```

```java 
Input:
         1
        / \
       2   3
      / \
     4   5

Return 3, which is the length of the path [4,2,1,3] or [5,2,1,3].

public class Solution {
    int max = 0;
    
    public int diameterOfBinaryTree(TreeNode root) {
        maxDepth(root);
        return max;
    }
    
    private int maxDepth(TreeNode root) {
        if (root == null) return 0;
        
        int left = maxDepth(root.left);
        int right = maxDepth(root.right);
        
        max = Math.max(max, left + right);
        
        return Math.max(left, right) + 1;
    }
}
```

### Binary Tree Upside Down

Given a binary tree where all the right nodes are either leaf nodes with a sibling (a left node that shares the same parent node) or empty, flip it upside down and turn it into a tree where the original right nodes turned into left leaf nodes. 

Return the new root.


### Invert Binary Tree

- [Invert Binary Tree](https://leetcode.com/problems/invert-binary-tree/https://leetcode.com/problems/invert-binary-tree/)
- [via Stack/Queue](https://leetcode.com/problems/invert-binary-tree/discuss/62707/Straightforward-DFS-recursive-iterative-BFS-solutions)

```java
    4           4
   / \         / \
  2   7   =>  7   2
 / \ / \     / \ / \
 1 3 6  9   9  6 3  1
Input: root = [4,2,7,1,3,6,9]
Output: [4,7,2,9,6,3,1]


public TreeNode invertTree(TreeNode root) {
    if(root != null){
        swap(root);
        invertTree(root.left);
        invertTree(root.right);
    } 

    return root;
}


public void swap(TreeNode root){
    if(root == null) return;

    TreeNode tmp = new TreeNode();
    tmp = root.right ;
    root.right = root.left;
    root.left = tmp;
    tmp = null;
}
```

### Merge Two Binary Trees 

```java
Input:
       Tree 1                     Tree 2
          1                         2
         / \                       / \
        3   2                     1   3
       /                           \   \
      5                             4   7

Output:
         3
        / \
       4   5
      / \   \
     5   4   7
     
public TreeNode mergeTrees(TreeNode t1, TreeNode t2) {
    if (t1 == null) return t2;
    if (t2 == null) return t1;
    
    TreeNode result = new TreeNode(t1.val + t2.val);
    result.left = mergeTrees(t1.left, t2.left);
    result.right = mergeTrees(t1.right, t2.right);
    return result;
}
```
