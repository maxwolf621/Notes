# Linked List

- [Linked List](#linked-list)
  - [Sort List](#sort-list)
  - [Swap](#swap)
    - [Odd Even Linked List](#odd-even-linked-list)
    - [Swap Nodes in Pairs](#swap-nodes-in-pairs)
  - [Merge](#merge)
    - [Merge Two Sorted Lists](#merge-two-sorted-lists)
  - [Operation Add](#operation-add)
    - [Add Two Numbers](#add-two-numbers)
    - [Add Two Numbers II](#add-two-numbers-ii)
  - [Split](#split)
    - [Split Linked List in Parts](#split-linked-list-in-parts)
  - [Remove](#remove)
    - [Remove Duplicates from Sorted List](#remove-duplicates-from-sorted-list)
    - [Remove Duplicates From Sorted List II](#remove-duplicates-from-sorted-list-ii)
  - [Rotate](#rotate)
    - [Rotate List](#rotate-list)
    - [Intersection of Two Linked Lists](#intersection-of-two-linked-lists)


1. Consider Linked List is odd or even 

2. Consider null linked list `[]` or one node length single list
`if(list.next == null || list == null) return list;`

3. Consider merging two list  
`if(list1 == null) return` and `if(list12== null) return list1`


## Sort List

- [Sort List](https://leetcode.com/problems/sort-list/)

Given the head of a linked list, return the list after sorting it in ascending order.
![image](https://user-images.githubusercontent.com/68631186/154366818-80934954-3717-470d-821c-d24cafe48caf.png)  


## Swap
###  Odd Even Linked List

Given the head of a singly linked list, group all the nodes with odd indices together followed by the nodes with even indices, and return the reordered list.

![image](https://user-images.githubusercontent.com/68631186/154723484-cb1dd58e-a48f-4a23-89b6-a035602c25fd.png)

```java
Input: head = [1,2,3,4,5]
Output: [1,3,5,2,4]
```
- Solve the problem in `O(1)` extra space complexity and `O(n)` time complexity.	

```java
public ListNode oddEvenList(ListNode head) {
    if (head == null) {
        return head;
    }
    ListNode odd = head, even = head.next, evenHead = even;

    while (even != null && even.next != null) {
        odd.next = odd.next.next;
        odd = odd.next;
        even.next = even.next.next;
        even = even.next;
    }
    odd.next = evenHead;
    return head;
}
```

```java
class Solution {
    public ListNode oddEvenList(ListNode head) {
        int ith = 1;
        int len = 1;
 
        ListNode tail = head;
        ListNode cur = head;
        ListNode pre = head; // combine odd
        
        if(head == null) {
            return null;
        }
            
        while(tail.next != null){
            tail = tail.next;
            len++;
        }

        if(len <= 2){
            return head;
        }
        
        while(len > 0){
            
            // even
            if(ith%2 ==0){
                pre.next = cur.next ;
                pre = pre.next;
                tail.next = cur;
                tail = tail.next;               
            }
            
            cur = cur.next;
            
            ++ith;
            --len;
        }
        
        tail.next = null;
        return head;
    }
}
```

### Swap Nodes in Pairs 

```java
Input : List = [1,2,3,4]
OutPut : List = [2,1,4,3]
```
- The number of nodes in the list is in the range `[0, 100]`.

```java
// 3 pointers prePre , pre , cur
class Solution {
    public ListNode swapPairs(ListNode head) {
        
        if(head == null || head.next == null) return head;
        
        ListNode pre = head, cur = head.next, newHead = head.next, tmp;
        ListNode prePre = new ListNode(0);
        
        while(cur != null){

            tmp = cur.next;
            
            cur.next = pre;
             
            // prePre -> pre -> cur -> ...
            // prePre -> cur -> pre 
            prePre.next = cur;
            prePre = pre;
            
            pre.next = tmp;
            pre = tmp;
            
            if(tmp == null) break;
            
            cur = tmp.next;
        }
        return newHead;
    }
}

// recursion
public class Solution {
    public ListNode swapPairs(ListNode head) {
        if ((head == null)||(head.next == null)) return head;
        
        ListNode n = head.next;
        
        head.next = swapPairs(head.next.next);
        
        n.next = head;
        
        return n;
    }
}
```

## Merge

###  Merge Two Sorted Lists

Merge the two lists in a one SORTED list.    
```java
Input: l1 = [1,2,4], l2 = [1,3,4]
Output: [1,1,2,3,4,4]
```
- The number of nodes in both lists is in the range `[0, 50]`.
- `-100 <= Node.val <= 100`
- Both list1 and list2 are sorted in non-decreasing order.

```java
class Solution {
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        
        if(list1 == null){
            return list2;
        }
        if(list2 == null){
            return list1;
        }
        
        ListNode dummy = new ListNode(0);
        ListNode preHead = dummy;
        
        while( list1 != null && list2 != null ){
                if(list2.val <= list1.val){
                    dummy.next = list2;
                    list2 = list2.next;
                }else{
                    dummy.next = list1;
                    list1 = list1.next;
                }
                dummy = dummy.next;
        }
        if(list1 == null) dummy.next = list2;
        else dummy.next = list1;
        
        return preHead.next;
    }
}

public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
    if(l1 == null) return l2;
    if(l2 == null) return l1;

    if(l1.val > l2.val){
        l1.next = mergeTwoLists(l1.next , l2);
        return l1;
    }else{
        l2.next = mergeTwoLists(l2.next, l1);
        return l2; 
    }
}
```


## Operation Add
### Add Two Numbers

- [solution](https://leetcode.com/problems/add-two-numbers/discuss/1059/My-accepted-Java-solution)
```html
Input: l1 = [2,4,3], l2 = [5,6,4]
Output: [7,0,8]
Explanation: 342 + 465 = 807.
```

```java
class Solution {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(0);
        ListNode tmp = dummy;
        
        // References
        ListNode l1_t = l1;
        ListNode l2_t = l2;
    
        int carry = 0;
        
        while(l1_t != null || l2_t != null || carry != 0){
            
            if(l1_t != null){
                carry += l1_t.val;
                l1_t = l1_t.next;
            }
            
            if(l2_t != null){
                carry += l2_t.val;
                l2_t = l2_t.next;
            }
            
            
            ListNode newNode = new ListNode( carry % 10);           
            carry /= 10;
            
            tmp.next = newNode;
            tmp = tmp.next;
        }
        
        
        return dummy.next;
    }
}
```

### Add Two Numbers II

```html
Input: (7 -> 2 -> 4 -> 3) + (5 -> 6 -> 4)
Output: 7 -> 8 -> 0 -> 7
```


## Split
### Split Linked List in Parts

Given the head of a singly linked list and an integer k, split the linked list into k consecutive linked list parts.

- The length of each part should be as equal as possible: no two parts should have a size differing by more than one. This may lead to some parts being null.
- The parts should be in the order of occurrence in the input list, and parts occurring earlier should always have a size greater than or equal to parts occurring later.

Return an array of the k parts.

```html
Input: root = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10], k = 3
Output: [[1, 2, 3, 4], [5, 6, 7], [8, 9, 10]]
```
- The input has been split into consecutive parts with size difference at most 1, and earlier parts are a larger size than the later parts.

```html
Input: head = [1,2,3], k = 5
Output: [[1],[2],[3],[],[]]
```
- The first element `output[0]` has `output[0].val` is `1`, `output[0].next` is `null`.
- The last element `output[4]` is null, but its string representation as a ListNode is `[]`.

```java
class Solution {
    public ListNode[] splitListToParts(ListNode root, int k) {
        ListNode[] parts = new ListNode[k];
        
        // len of the likedlist 
        int len = 0;
        for (ListNode node = root; node != null; node = node.next)
            len++;
            
        // n : minimum guaranteed each part size; 
        // r : extra nodes spread to the first r parts;
        int n = len / k, r = len % k;
        
        ListNode node = root, prev = null;
        
        for (int i = 0; node != null && i < k; i++, r--) {
        
            parts[i] = node;
            
            for (int j = 0; j < n + (r > 0 ? 1 : 0); j++) {
                prev = node;
                node = node.next;
            }
            prev.next = null;
        }
        
        return parts;        
    }
}
```

## Remove

### Remove Duplicates from Sorted List

```diff
1->1->2, return 1->2.

1->1->2->3->3, return 1->2->3.
```

```java
class Solution {
    public ListNode deleteDuplicates(ListNode head) {
        
        
        if(head == null || head.next == null){
            return head;
        }
        
        
        ListNode tail = head;
        ListNode cur = head.next;
                
        /**
            1-1-2-3
            t c
            
            1-1-2-3
            t   c
            
            1 1 2-3
            '---' c
                t 
            
            1 1 2 3 null 
            '---'-'-'
        **/
        while(cur != null){
            
            if(cur.val != tail.val){
                tail.next = cur;
                tail = cur     ;
                
            }
            cur = cur.next;            
        }
        
        tail.next = null;
        
        return head;
    }
}
```

### Remove Duplicates From Sorted List II

- [Remove Duplicates from Sorted List II](https://leetcode.com/problems/remove-duplicates-from-sorted-list-ii/)


## Rotate
### Rotate List

```java
Input: head = [1,2,3,4,5], k = 2
head will be rotated to right by 2 places
Output: [4,5,1,2,3]
```
- The number of nodes in the list is in the range `[0, 500]`.
- `0 <= k <= 2 * 109`

```java
/**
  * 1. find linkedLen and LinkedList's tail
  * 2. find new Head's tail and new head 
  */
class Solution {
    public ListNode rotateRight(ListNode head, int k) {
        
        if( head == null || 
            head.next == null || 
            k == 0) return head;
        
        ListNode tail = head;
        
        
        int linkedLen = 1;
        // 1. find len of Linked List 
        // 2. find the tail of linked list => stop iterating if tail.next == null
        while( tail.next != null){
            tail = tail.next;
            linkedLen++;
        }
        
        // Link New Head's tail to Original Head       
        tail.next = head;    

        // k%linkedLen : `0 <= k <= 2 * 109`
        int tailLen = linkedLen - k%linkedLen;
        
        // Get new head
        while(tailLen > 0){
            tail = tail.next;   
            tailLen--;
        }

        head = tail.next;

        tail.next = null;
        
        return head;
    }
}
```


### Intersection of Two Linked Lists

Given the heads of two singly linked-lists headA and headB, return the node at which the two lists intersect.      
- If the two linked lists have no intersection at all, `return null`.

![圖 1](images/ecfc055c76800ebba3bc136fa2ed88cdae71d31a9b871b0f4010581eff693d89.png)
```java
Input: intersectVal = 8, 
listA = [4,1,8,4,5]
listB = [5,6,1,8,4,5]
skipA = 2
skipB = 3
```
- The number of nodes of listA is in the m.  
The number of nodes of listB is in the n.  
`1 <= m, n <= 3 * 104`   
`0 <= skipA < m`    
`0 <= skipB < n`   
- `intersectVal` is `0` if listA and listB do not intersect.
- `intersectVal == listA[skipA] == listB[skipB]` if listA and listB intersect.

```java
public class Solution {
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        
    }
}
```