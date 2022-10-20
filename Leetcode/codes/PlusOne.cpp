/*
Given a non-empty array of decimal digits representing a non-negative integer, increment one to the integer.
The digits are stored such that the most significant digit is at the head of the list, and each element in the array contains a single digit.
You may assume the integer does not contain any leading zero, except the number 0 itself.
*/


class Solution {
public:
    vector<int> plusOne(vector<int>& digits) {
        for(auto i = digits.rbegin() ; i != digits.rend() ; ++i)
        {
            *i+=1;
            if(*i == 10){
                *i=0;
            }
            else break;
        }
        if(digits.front() == 0)
        {
            digits.front() = 1;
            digits.push_back(0);
        }
        return digits;    
    }
};



vector<int> plusOne(vector<int>& digits) {
    for(int i = digits.size() - 1; i >= 0; i--) {
    if (++digits[i] %= 10)
        return digits;
    }

    digits[0] = 1;
    digits.push_back(0);
    return digits;
}