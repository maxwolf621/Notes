#include <stdio.h>
#include <iostream>
#include <math.h>
#include <vector>

using namespace std;
vector<int> myprime;

int prime[1001];
int main(){
    for(int i=2; i<=sqrt(1000); i++){
        for(int j=i*i; j<=1000; j=j+i){
            prime[j] = 1;
        }
    }
    for(int i=2; i<=1000; i++){
        if(prime[i]==0){
            myprime.push_back(i);
            printf("%d ", i);
        }
    }
    return 0;
}

