#include<stdbool.h>
#include<stdlib.h>
#include<stdio.h>
#define MAXSIZE 5

struct TreeNode {
     int val;
     struct TreeNode *left;
     struct TreeNode *right;
};

struct TreeNode* newNode(int item) {
    struct TreeNode* temp  = (struct TreeNode*)malloc(sizeof(struct TreeNode)); 
    temp->val = item; 
    temp->left = temp->right = NULL; 
    return temp; 
}


struct dyStack{
    int top;   //  for Tree node
    int topV; // for val
    int capacity;
    // Dynamical Array
    int* arr;
    struct TreeNode* *nodes;
};
struct dyStack *CreateStack(){
    struct dyStack* ptr = (struct dyStack*)malloc(sizeof(struct dyStack));
    if(!ptr)
        return NULL;
    ptr->capacity = MAXSIZE;
    ptr->top = -1 ;
    ptr->topV= -1 ;
    ptr->arr = (int*)malloc(ptr->capacity*sizeof(int));
    if(!ptr->arr)
        return NULL;
    ptr->nodes =(struct TreeNode**)malloc(ptr->capacity*sizeof(struct TreeNode*));
    if(!ptr->nodes)
        return NULL;
    return ptr;
}
bool empty(struct dyStack* s)
{
    return (s->top == -1);
}
bool full(struct dyStack* s)
{
    return (s->top == s->capacity- 1);
}
void resize(struct dyStack* s)
{
    int *temp = s->arr;
    struct TreeNode* *tempnode = s->nodes;
    s->arr = (int*)malloc(sizeof(s->capacity*2*sizeof(int)));
    s->nodes = (struct TreeNode**)malloc(sizeof(s->capacity*2*sizeof(struct TreeNode*)));
    for(int i = 0 ; i < s->capacity ; i++){
        s->arr[i] = *(temp+i);
        s->nodes[i] = *(tempnode+i);
    }
    free(temp);
    free(tempnode);
    s->capacity *=2;
}
void pushNode(struct dyStack* s,struct TreeNode* node)
{
    if(full(s)) resize(s);
    s->nodes[++(s->top)] = node;
}
void pushValue(struct dyStack* s, int val){
    s->arr[++(s->topV)] = val;
}

struct TreeNode* pop(struct dyStack* s) { 
    if (empty(s)){ 
        printf("stack is empty \n");
        return NULL;
    }
    else {return (s->nodes[s->top--]);}
}

int* preorderTraversal(struct TreeNode* root, int* returnsize){
    if(!root)
    {
        *returnsize = 0;
        return NULL;
    }
    struct dyStack* s = NULL;
    s = CreateStack();
    pushNode(s,root);
    while(!empty(s)){
        struct TreeNode* node = pop(s);
        pushValue(s,node->val);
        if(node->right) pushNode(s,node->right);
        if(node->left) pushNode(s,node->left);
    };
    *returnsize = s->topV;
    return s->arr;
}

bool Traversal(struct TreeNode*,int,int);
bool isPathsum(struct TreeNode*, int);

bool isPathSum(struct TreeNode* node, int Target)
{  
    if(!node) return false;
    bool hasPath = Traversal(node,0,Target);
    return hasPath;
}

bool Traversal(struct TreeNode* node, int Count, int Target){
    Count += node->val;
    printf("Count %d, Node Value %d \n ",Count,node->val);
    bool left;
    bool right;
    // if Node is leaf
    if((!node->left) && (!node->right)){
        printf("node value: %d \n", node->val);
        if(Count == Target) return true;
        else return false;          
    }
    // keep doing the traversal
    if(node->left) {
        left = Traversal(node->left, Count, Target);
    }
    if(node->right) {
        right =Traversal(node->right, Count, Target);     
    }
    return (left||right);
}



int main()
{
    struct TreeNode* root = NULL;
    root = newNode(1);
    root->left = newNode(2);

    if(isPathSum(root,0))
    {
        printf("yes");
    }
    return 0;
}





