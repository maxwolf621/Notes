class Solution {
//suck
public:
    int singleNumber(vector<int>& nums) {
        //using hashTable
        unordered_map<int,int> table;
        for(auto num : nums){
            table[num]+=1;
            if(table[num]>1){
                table.erase(num);
            }
        }

        return table.begin()->first;
    }
};

// only allow two duplicate number
int singleNumber(int* nums, int numsSize){
    int num = 0;
    for(int i = 0 ; i < numsSize ; ++i)
    {
        num ^=nums[i];
    }
    return num;
}

02/19/2021 22:43	Accepted	16 ms	7.5 MB	c
02/19/2021 22:40	Accepted	28 ms	19.8 MB	cpp



/* Find Single Number, 3 duplicate numbers */
class Solution {
public:
    // Each element in nums appears exactly three times except for one element which appears once.

    int singleNumber(vector<int>& nums) {
        unordered_map<int,int> table;
        for(auto num : nums){
            table[num]+=1;
            if(table[num] ==3) table.erase(num);
        }
    
        return table.begin()->first;
    }

    int singleNumber(vector<int>& nums) {
    int ones = 0, twos = 0;
    for(int i = 0; i < nums.size(); i++){
        ones = (ones ^ nums[i]) & ~twos;
        twos = (twos ^ nums[i]) & ~ones;
    }
    return ones;
    
};


