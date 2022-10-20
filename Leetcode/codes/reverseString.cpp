class Solution {
public:
    void reverseString(vector<char>& s) {
    int i = 0;
    int j = s.size() - 1; 
    int middle = s.size()/2;
    while(i <= middle && j >= middle)
    {
        
        std::swap(s[j],s[i]);
        --j;
        ++i;
    }
    }
};

