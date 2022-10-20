int removeDuplicates(int* array, int size)
{
    int i;
    int last = 0;
    assert(size >= 0);
    if (size <= 0)
        return size;
    for (i = 1; i < size; i++)
    {
        if (array[i] != array[last])
            array[++last] = array[i];
    }
    return(last + 1);
}
/*
int removeDuplicates(int* nums, int numsSize){
    if(!numsSize) return 0;
    int count = 0;
    int *temp = nums;
    for(int i = 0 ; i < numsSize ; ++i)
    {
        if(*temp != *(nums+i)){
            *(++temp)= *(nums+i);
            ++count;
            printf("%d",*temp);
        }
    }
    return count+1;
    
}
*/

#include<assert.h>

int removeDuplicates(int* array, int size)
{
    int i;
    int last = 0;
    assert(size >= 0);
    if (size <= 0)
        return size;
    for (i = 1; i < size; i++)
    {
        if (array[i] != array[last])
            array[++last] = array[i];
    }
    return(last + 1);
}