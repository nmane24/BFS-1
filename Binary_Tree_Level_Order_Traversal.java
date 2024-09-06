
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.swing.tree.TreeNode;



/*
 * 
 * Diff between BFS and DFS 
 *  - DFS we try to go till the leaf ; traverse towards the depth ; any nodes
 *          In DFS, when we take a node ; any node can be taken either left one or right one
 * 
 *  - BFS we try to go left to right ; traverse on the breadth; all nodes ; 
 *      In BFS when we take a node, what happens is all the children are taken 
 *      IN BFS we mainatain a queue
 * 
 * LeetCode : https://leetcode.com/problems/binary-tree-level-order-traversal/description/
 * 
 * 
 * Class Example
 * 
 *                          1
 *                  2               3
 * 
 *          4           5        6       7
 *        8    9           10  12           14
 *                      11                 15
 * 
 * Output : 
 * [
 *  [1],
 * [2,3],
 * [4,5,6,7],
 * [8,9,10,12,14]
 * [11,15]
 * ]
 * 
 * 
 * Approach 1: Using BFS ( queue data strcuture)
 * Queue os FIFO : add elements towards the end and pop out the elements from the beginning of the queue.
 * IN BFS we take a queue, so we start at root node. 
 * Now we want to return the output as list of list where each list is created level wise. For that we maintain a size 
 * variable
 * 
 * We want to Push 1 to queue, 
 * |1|  ; when 1 will be popped out we will push both ( all ) the babies of the that node
 * Now my size variable becomes 1 , which is size of the queue.
 * On the size vaiable a loop will be running. So whatever will be the size of the queue, those many elements we will have
 * at that particular level. 
 * So as my size =1 ,only one time the loop will be running , we will pop out 1, 1 will get added to our list after that both
 * the babies will get added to the list
 * 
 * So when 1 will be popped out both 2 and 3 will be added.
 * |2|3|
 * Now the size variable will get updated to 2. So as soon as one level finishes the size variable gets updated.
 * 
 * When 2 will be popped out , and 2 will be added to the next list, as this is new level, new list will be created
 * and in that list we will be adding the popped out element. For that popped out element we
 * have to add both(all) the babies of 2 i.e  4 and 5 will be added to the queue.
 * |3|4|5|
 * Now the size vairable will not get updated, till the time that level is not getting over, the size will not get updated.
 * Now 3 will gets popped out, 3 will be added to the same list where 2 was added as the level is not yet finished 
 * and then both (all) the babies of 3 : 6 and 7 will be added to the queue.
 * |4|5|6|7|
 * And now our for loop for size will be finished.
 * Now again the size variable will be updated which will be size of the queue = 4
 * And this will continue
 * 
 * The Type of Queue we take while coding is TreeNode as we are popping out the TreeNodes, we should be able to access its babies
 * If we only add integers, then we will not be able to add the child nodes.  
 * 
 * 
 * TC : O(n) .. we are traversing all the node, n is number of all the nodes in the tree
 * SC : O(n/2).. space for leaf nodes, .. max space we will have is width of the tree, that will be 
 * the max space that will be taken by the queue.;  as 2 is constant it will be O(n)
 */
public class Binary_Tree_Level_Order_Traversal {

    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if(root == null) return result;
        Queue<TreeNode> q = new LinkedList<>();
        q.add(root); // add the 1st node to the queue

        while(!q.isEmpty()){
            int size = q.size();
            List<Integer> li = new ArrayList<>();
            for(int i = 0; i<size;i++){
                TreeNode curr = q.poll(); // pop the element
                li.add(curr.val);
                // add both the babies to the queue
                if(curr.left != null){
                    q.add(curr.left);
                }
                if(curr.right != null){
                    q.add(curr.right);
                }
            }
            result.add(li); // add the levels list into result
        }
     return result;
    }
}

/*
 * 
 *  Approach 2A: Using DFS ( stack data strcuture with recursion)
 * 
 * We can use recusion , maintain the levels and add to the list.
 * 
 * We make use of recursion and pass the level variable as local parameter to helper function.
 * To know the elements at each level need to be added to same list we will make use of map
 *          levels: list
 * 
 * We start at level 0 for root node. Create an entry for 0 and add 1 to list
 * When we traverse left to 2, our level becomes 1 and we add entry for 1 and add node 2 to list
 * Recursion will go towards left, our level becomes 2, we do not have any entry for 2 so we into the map that entry and we add the node
 * 4 to it
 * map : { 0 : [1], 1: [2,] , 2: [4]}
 * 
 * Recursion goes towards left and we dont have any entry for level, so we add the entry for 3 and 8 against it in the list
 *   map : { 0 : [1], 1: [2,] , 2: [4], 3: [8]}
 * 
 * Now next of 8 node ( recursive call to left is null) so we go back to node 4, where the level is 2. Now we take recursion to 
 * right and the level becomes whatever level we have + 1 ;( so 2 + 1 = 3). As we haev an entry for 3 so we get the list and the
 * node 9 to the list.
 * map : { 0 : [1], 1: [2,] , 2: [4], 3: [8,9]}
 * Then for 9 the recursion will be done, both left and right are null. nothing will get added to the list, go back to parent 
 * 4 and come back to node 2 .
 * From 2 we take recursion call towards right(5 node). The level here will be : level of the parent (1)  + 1 to it = 2. As we have 
 * entry for level 2 in map, we get the list and add 5.
 * map : { 0 : [1], 1: [2,] , 2: [4,5], 3: [8,9]}
 * 
 * Left on recursion of 5, null so we come back. go to recuriosn right on 5 ; level 2+1 = 3. map becomes
 *  map : { 0 : [1], 1: [2,] , 2: [4,5], 3: [8,9,10]}
 * 
 * From 10 we do recursive call to left and arrive at node 11. The level here becomes 4 . Do we have an entry for 4 in map
 * No. So we create entry for 4 and add to map with list against it
 *   map : { 0 : [1], 1: [2,] , 2: [4,5], 3: [8,9,10], 4:[11]}
 * 
 * Left and right of 11 are null, return to parent 10, try to take recursion on 10.right it is null, will come back on 10
 * then come back on parent 5, come back to 2 and then to 1
 * 
 * 
 * Now all the recursive calls to right of root node (1) will happen
 * 
 * At the end, we have to give out all the lists in order of levels 0 , 1 , 2, 3 and 4. So we will be iterating on the map.
 * For skipping iterating, we can sort the keys of the map but then it will be nlogn operation
 * 
 * We can keep a min and max variable to track the min level and max level of map and then iterate map 
 * betwen that min and max, get the list belonging to that particular level and addd it to our result.
 * 
 * Do we require the map ? as it is adding time and space complexity ?
 * 
 * So instead of using hashmap we can use the result arraylist itself. 
 * We check the level with the size of the result
 * When the size is equal it means there is no entry happened for the level, create an entry for it i.e create a list and add the node
 * to it
 * if(result.size() == level) {
 * 
 * }
 * 
 * At 0, now when we check for level 1 the size of result =1 and level is 1 , which is equal add node 2 to it 
 * [
 * [1]
 * ]
 *  
 * Add node 2, level 1
 * [ [1],[2 ]
 * 
 * [ [1],[2 , [4, ]
 *  [ [1],[2 , [4, [8,]
 * 
 * When the size of the result is not equal to level, it tells there already exists a list. So access the list of that level 
 * and add the element to the list in the result arraylist. Index of the result and level of the tree will help to retrieve
 * the list from the result and add the element to the list
 * 
 * Approach 2B :DFS Without extra space of hashmap
 * TC : O(n)
 * SC : O(h) .. height of the tree ( recurison stack will store elements with the height of the tree)
 * Here it does not matter if we process our root in preorder, postorder or inorder as we have made sure we have added our list to
 * the result arraylist
 */
List<List<Integer>> result;  // declare within a class globally
 public List<List<Integer>> levelOrder(TreeNode root) {
    result = new ArrayList<>();

   if(root == null) return result;
   helper(root, 0);
   return result;
 }

 private void helper(TreeNode root, int level){
    // base
    if(root == null) return;

    //process root
    if(result.size() == level){ // this means we need to add empty list to it
        result.add(new ArrayList<>());
    }
    // get the list and update the value at the level in the list
    result.get(level).add(root.val);

    helper(root.left, level+1);
    helper(root.right, level+1);

 }
