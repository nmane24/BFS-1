
import java.util.HashMap;

/*
 * 
 * LeetCode Explanation : https://leetcode.com/problems/course-schedule/description/
 * 
 * There are a total of numCourses courses you have to take, labeled from 0 to numCourses - 1. You are given an array prerequisites where prerequisites[i] = [ai, bi] indicates that you must take course bi first if you want to take course ai.

For example, the pair [0, 1], indicates that to take course 0 you have to first take course 1.
Return true if you can finish all courses. Otherwise, return false.

 

Example 1:

Input: numCourses = 2, prerequisites = [[1,0]]
Output: true
Explanation: There are a total of 2 courses to take. 
To take course 1 you should have finished course 0. So it is possible.
Example 2:

Input: numCourses = 2, prerequisites = [[1,0],[0,1]]
Output: false
Explanation: There are a total of 2 courses to take. 
To take course 1 you should have finished course 0, and to take course 0 you should also have finished course 1. So it is impossible.
 * 
 * 
 * 
 * Class Example
 * 
 * Approach : BFS using hashmap and adjacency list
 * 
 * arr  = [[1,0],[2,0],[3,1],[5,2],[3,2],[4,1],[5,4]]
 * 
 * The course 1 is dependent on course 0
 * the course 2 is dependent on course 1.
 * 
 * 
 *          0
 *      2       1
 *          3
 *      5       4
 * 
 * We start from the course  which has 0 dependencies i.e course 0;as we will be able to complete that course.
 * 
 * Maintain an indegree array and keep tracking the number of incoming edges to that particular node. The size of indegree array 
 * is equal to the number of courses.
 * 
 * We iterate over the arr and update the index of in array  
 * 
 *  indegree
 * 
 *      | 0  |  1 |  1 |  2  |  1 |  2 |
 *        0    1     2   3     4    5
 * 
 * whichever course has indegreee 0 we start from that ccourse.
 * 
 * Make use of BFS and take a queue and first add the zero dependent course.
 * 
 *  |0| 
 * 
 * If course zero is done, pop out zero all the courses dependent on zero their indegree should be reduced by 1. REason
 * one of the prerequisite for their course is done.
 * We will check the dependent courses by iterating upon the arr array and find out 1 and 2 are so push into the queue
 * 
 * | 1 | 2 |
 * Then 1 will be popped out and  check which all courses are dependant upon 1. 
 * as we haev to iterate again and again over array to check which all courses will be dependent on 1, we make a use of map
 * i.e optimize the search
 * 
 * We have created hashmap as below . This map is called adjentcy list
 *   independent     dependeant 
 * {     0         :   [1,2]   }
 *       1         :   [3,4]
 *       2         :   [3,5]
 *       3         :   [   ]
 *       4         :   [5]
 *       5         :   [ ] 
 * 
 * 
 * Add course 0 to  queue
 * queue : |0| 
 * 
 * If course zero is done, pop out zero all the courses dependent on zero their indegree should be reduced by 1. 
 * REason for reducing indegree : one of the prerequisite for their course is done.
 * 
 * We get the dependant lits from map. So we check from map the dependent course for 0,we get the list [1,2]. Go to indegree array
 * for 1 reduce the indegree by 1 , it will be 0. When the indegree for node becomes 0 add it to the queue. 
 * 
 *  *  indegree
 * 
 *      | 0  |  0 |  1 |  2  |  1 |  2 |
 *        0     1     2   3     4    5
 * 
 *  queue : |1|    {Add only if indegree for that partuclar node becomes 0}
 * Come to next dependent node for 0 which is 2, go to indegree array and reduce by 1. Now it has become 0 so add 2 also to the queue.
 * 
 *  *  *  indegree
 * 
 *      | 0  |  0 |  0  |  2  |  1 |  2 |
 *        0     1     2    3     4    5
 * 
 *  *  queue : |1|2| 
 * 
 * Do we neeed to maitain size variable here ? -- No . As here we are just asked to check if we will be able to do the courses
 * or not. If the question was in how many semester you would have taken to complete the courses ? then we would required
 * size variable.
 * 
 * Then 1 will be popped out, get all the dependant of 1 from the map. 
 *  *  queue : |2| 
 * 
 *  *  *  indegree
 * 
 *      | 0  |  0 |  0  |  1  |  1 |  2 |
 *        0     1     2    3     4    5
 * 
 * As indegree of 3 is not yet 0, we do not pus to queue. WE check for next dependant of 1 which is 4. 
 *   *  *  indegree
 * 
 *      | 0  |  0 |  0  |  1  |  0 |  2 |
 *        0     1     2    3     4    5
 * 
 * Indegree of node 4 is now 0, add it to the queue
 *   *  queue : |2|4|
 * 
 * Now, pop out 2 from the queue, get all its dependants
 *  *  *  indegree
 * 
 *      | 0  |  0 |  0  |  0  |  0 |  2 |
 *        0     1     2    3     4    5
 * 
 *  *  queue : |4|3|
 * 
 * *  *  *  indegree
 * 
 *      | 0  |  0 |  0  |  0  |  0 |  1 |
 *        0     1     2    3     4    5
 * 
 * 
 * Pop next one which is 4 , reduce the indegree of 5 by 1
 * 
 * *  *  *  indegree
 * 
 *      | 0  |  0 |  0  |  0  |  0 |  0 |
 *        0     1     2    3     4    5
 * 
 *  *  queue : |3|5|
 * 
 * 3 popped out, no dependant
 * 5 , popped out , no depedant
 * 
 * 
 * 
 *  Question : If we have a cycle in our course schedule then we will not be able to complete the course because of a cycle
 * Question 2: If there is one more node like 0 which has 0 dependancy we need to add both nodes at the same time to queue at start
 * 'As basic of BFS - is all ,, all the nodes will be going into the queue'
 * 
 * 
 * TC : O(V + E)
 * v: number of courses
 * e :number of adjusts
 * 
 * * SC : O(V + E)
 * v: number of courses ( indegree array )
 * e :number of adjusts ( hashmap)
 * 
 */
public class Scheduling_Courses {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        HashMap<Integer, List<Integer>> map = new HashMap<>();

    }
}
