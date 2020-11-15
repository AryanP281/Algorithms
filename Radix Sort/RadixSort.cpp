
#include<iostream>
#include<queue>
#include<random>
#include<time.h>
#include<chrono>

typedef long long ll;

bool isSorted(ll* arr, int n)
{
    for(int a = 1; a < n; ++a)
    {
        if(arr[a] < arr[a-1]) return false;
    }
    
    return true;
}

void radixSort(ll* arr, int n);

int main()
{
    const int ARR_SIZE = 100000000;
    ll* arr = new ll[ARR_SIZE];
    srand(time(0));
    for(int a = 0; a < ARR_SIZE; ++a)
    {
        arr[a] = rand() % (ARR_SIZE * 5);
    }

    auto start = std::chrono::high_resolution_clock::now();
    radixSort(arr, ARR_SIZE);
    auto end = std::chrono::high_resolution_clock::now();
    std::cout << '\n' << "IS SORTED: " << (isSorted(arr, ARR_SIZE) ? "True" : "False") << '\n'; 
    std::cout << "Execution time : " << std::chrono::duration_cast<std::chrono::milliseconds>(end - start).count();

    delete[] arr;
    std::getchar();
    return 0;
}

void radixSort(ll* arr, int n)
{
    int bitCount = sizeof(ll) * 8; //The number of bits used to represent long long values

    //Sorting the elements by their bits, starting from the lsb
    for(int a = 0; a < bitCount; ++a)
    {
        ll* bitZero = new ll[n]; //The elements having 0 as their a'th element
        int zeroIndex = 0; //The index of the next element in the bitZero array
        ll* bitOne = new ll[n]; //The elements having 1 as their a'th element
        int oneIndex = 0; //The index of the next element in the bitOne array

        //Classifing the elements according to their a'th bit
        for(int a = 0; a < n; ++a)
        {
            if((arr[a] & (1 << a)) == 0) bitZero[zeroIndex++] = arr[a];
            else bitOne[oneIndex++] = arr[a];
        }

        //Placing the elements with the a'th bit as zero
        for(int a = 0; a < zeroIndex; ++a)
        {
            arr[a] = bitZero[zeroIndex];
        }
        //Placing the elements with the a'th bit as one
        for(int a = 0; a < oneIndex; ++a)
        {
            arr[zeroIndex+a] = bitOne[oneIndex]; 
        }

        //Deallocating memory after use
        delete[] bitZero;
        delete[] bitOne;
    }
}