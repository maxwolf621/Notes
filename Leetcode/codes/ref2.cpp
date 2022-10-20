#include <iostream>

// Cpp sytle
int& fcpp(int a[], int i)
{
    return a[i];
}

// C sytle
int* fc(int a[], int i)
{
    return &a[i];
}
int main()
{
    int a[] = {1,2,3,4,5};
    //left_handside
    int n = fcpp(a,3);
    int* i = fc(a,3)  ;
    printf("fcpp %d \n",n);
    printf("fc %d \n" , *i);
    //Cpp sytle righthandside
    fcpp(a,3) = 6 ; // now a[3] is 6
    printf("fcpp right handside %d\n",a[3]);
    
    // C style righthandside
    *fc(a,3)   = 7 ; // now a[3] is 7
    printf("fc right handside %d\n",a[3]);
    return 1;
}
