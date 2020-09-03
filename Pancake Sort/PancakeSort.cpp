
/************************Preprocessor Directives**********************/
#include <vector>

/************************Functions********************/
namespace PancakeSorting
{
	template<typename T>
	void flip(std::vector<T>& arr, int i)
	{
		/* Flips the elements in the range [0,i]
		arr: the array to be flipped
		i: last index of the range to be flipped
		*/

		//Flipping the elements in the given range
		for (int a = 0; a < floor((i + 1) / 2); ++a)
		{
			T temp = arr[a];
			arr[a] = arr[i - a];
			arr[i - a] = temp;
		}

	}

	template<typename T>
	void pancakeSort(std::vector<T>& arr, int (*compare)(T&, T&))
	{
		/* Sorts the given array using pancake sort
		arr: The array to be sorted
		  compare: The function to be used for comparing elements. Return-value < 0 if arg1 < arg2,
		  return-value = 0 if arg1 = arg2, return-value > 0 if arg1 > arg2
		*/

		auto lambda_max_in_range = [&arr, compare](int i) -> int
		{
			int maxIndex = 0; //Pointer to the element having the max value
			for (int a = 1; a <= i; ++a)
			{
				if (compare(arr[maxIndex], arr[a]) < 0) maxIndex = a;
			}
			return maxIndex;
		}; //Lambda for getting the max-valued element in the given range

		for (int a = arr.size() - 1; a >= 0; --a)
		{
			int maxIndex = lambda_max_in_range(a); //Getting the max element in the range [0,a]

			/*Flipping the elements in the range [0,maxIndex]. This transfers arr[maxIndex] to
			the beginning of the array. Hence, the next flip will transfer it to the ath position.*/
			flip<T>(arr, maxIndex);

			flip<T>(arr, a); //The ath position will be occupied by the ath largest element
		}

	}
}
