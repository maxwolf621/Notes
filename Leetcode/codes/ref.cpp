#include <iostream>
using namespace std;

int main()
{
    int &r = n;
    
    // r is constant pointer to an integer
    int *const r = &n; // value in r cant not be change
    int *q = 12; 
    r = q ; // Error , r is const
    *r = 1 ;  // it works
    // It is important to note the difference btw the type int *const and the type const int *
    const int *s = &m // pointer to a constant integer
    s =  &m;          // admissible
    *s = 2 ;         // Error , where s point to "is constant"
}
