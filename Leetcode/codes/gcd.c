#include<stdio.h>

int gcd(int ,int);

int main(){
    int a=2 , b=10;
    if(gcd(a,b)==1){
        printf("prime\n");
    }
    else{
        printf("No\n");
    }
    return 0;
}




int gcd(int a, int b){
    return a%b==0 ? b : gcd(b,a%b);
}


