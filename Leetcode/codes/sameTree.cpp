/**
 * Definition for a binary tree node.
 * struct TreeNode {
 *     int val;
 *     TreeNode *left;
 *     TreeNode *right;
 *     TreeNode() : val(0), left(nullptr), right(nullptr) {}
 *     TreeNode(int x) : val(x), left(nullptr), right(nullptr) {}
 *     TreeNode(int x, TreeNode *left, TreeNode *right) : val(x), left(left), right(right) {}
 * };
 */
class Solution {
public:
    using Tptr = TreeNode*;
    bool isSameTree(TreeNode* p, TreeNode* q) {
        if((!p) && (!q)) return true;
        if(!p || !p)    return false;
        queue<Tptr> treeR;
        queue<Tptr> treeL;
        treeR.push(p);
        treeL.push(q);
        Tptr l,r;
        while(!treeR.empty() || !treeL.empty()){
            l =treeL.front();
            r =treeR.front();
            treeR.pop();
            treeL.pop();
            if(l && r){
                if(r->val != l->val) return false;
                
                if(!(isLeaf(l) && isLeaf(r))){
                    // just push all even it's nullptr
                    if(r->right || l->right){
                        cout<<l->val<<r->val;
                        treeR.push(r->right);
                        treeL.push(l->right);
                    }
                    if(r->left || l->left){
                        cout<<l->val<<r->val;
                        treeR.push(r->left);
                        treeL.push(l->left);
                    }
                }
            }
            // for such [1,2] [1,null,2]
            else return false ;
        }
        return true;
    }
    bool isLeaf(Tptr node){
        return !(node->left || node->right);
    }
};