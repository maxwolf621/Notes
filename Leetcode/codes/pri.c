#include <stdio.h>
#include <malloc.h>
#include <conio.h>

typedef struct node *nodeptr;
struct node
{
	int data;
	int priority;
	struct node *next;
};
nodeptr nodeList = NULL;

nodeptr Enqueue(nodePtr nodeList, int NewQueVal, int NewQuePri)
{
	nodeptr NewQue, curPtr ;
	//initiallize
	ptr = (nodeptr)malloc(sizeof(nodeptr));
	//printf("\n enter the value and its priority : ");
	//scanf(" %d %d", &val, &pri);
	NewQue->data     = NewQueVal;
	NewQue->priority = NewQuePri;

	// FirstNode
	if(!nodeList || NewQue < nodeList->priority)
	{		
		NewQue->next = nodeList ;
		nodeList     = newQueue  ;
	}
	else 
	{
		curPtr = node ;
		// if pri <= curPtr->prioritaete
		while( curPtr->next != NULL && curPtr->next->priority <= NewQuePri)
			curPtr = curPtr->next	;
		NewQue->next = tempPtr->next;
		curPtr->next = NewQue	    ;
	}
	return nodeList;
}
nodeptr Dequeue(nodeptr node)
{
	nodeptr temp;
	if(!node)
	{
		puts("\n UNDERFLOW");
		return ;
	}
	else
	{
		temp = node;
		puts("\n Delted item is : %d", temp->data);
		free(temp);
	}
	return start
}

void display(nodeptr node)
{
	nodeptr temp;
	ptr = node  ;
	if(!ptr)
		puts("\nQueue is Empty");
	else
	{
		puts("\n Priority Queue is : ");
		while(ptr)
		{
			printf("%d[pritority=%d", ptr->data, ptr->priority);
			ptr = ptr->next;
		}
	}
}


typedef struct Graph *Graphptr;
struct Graph{
	int Vertrex, Edges;
	// doppelte Zeiger
	int **adjmat;
	int **weight;
};

Graphptr BuildAdjMat(){
	int e /* Edge */ , x, y /* Reihe und Kol */ ;
	Graphptr G;
	MALLOC(G,sizeof(*G));
	//G = (Graphptr)malloc(sizeof(struct Graph));

	printf("Num of Vertrex:");
	scanf("%d",&G->Vertrex);
	printf("Numb of Edges :");
	scanf("%d",&G->Edges);

	G->adjmat=(int**) malloc(sizeof(int*) * G->Vertrex);
    G->weight=(int**) malloc(sizeof(int*) * G->Vertrex);
    for (int i = 0; i < G->Vertrex; ++i)
		G->adjmat[i] = (int*) malloc (sizeof(int) * G->Vertrex);
        G->weight[i] = (int*) malloc (sizeof(int) * G->Vertrex);
	
	for( x = 0 ; x < G->Vertrex ; x++)
        for(y = 0 ; y < G->Vertrex ; y++)
            G->adjmat[x][x] = 0;
	
    for( e = 0 ; e < G->Edges ; e++){
		printf("Enter Row: ");
		scanf("%d", &x);
		printf("Enter Col: ");
		scanf("%d", &y);
		printf("Processing Graph[%d][%d] and Grapg[%d][%d] \n", x, y, y,x);
		G->adjmat[x][y] = 1;
		G->adjmat[y][x] = 1;
	}

	return G;
}




