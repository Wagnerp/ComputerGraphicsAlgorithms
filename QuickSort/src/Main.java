
import java.util.ArrayList;
import java.util.Random;

public class Main
{

	/**
	 * Main method
	 * @param args
	 */
	public static void main(String[] args)
	{	
		int arraySize = 100000;
		Random randomGenerator = new Random();
		
		ArrayList<Integer> arrayToSort = new ArrayList<Integer>(); 
		for (int i = 0; i < arraySize; i++) arrayToSort.add((randomGenerator.nextInt(arraySize)+1));
		
		arrayToSort = quicksort(arrayToSort);
	}

	private static ArrayList<Integer> quicksort(ArrayList<Integer> arrayToSort)
	{
		if(arrayToSort.size() < 2) return arrayToSort;
		
		int pivotValue = arrayToSort.get(arrayToSort.size()/2);
		ArrayList<Integer> smaller = new ArrayList<Integer>();
		ArrayList<Integer> bigger = new ArrayList<Integer>();
		ArrayList<Integer> toReturn = new ArrayList<Integer>();
		
		arrayToSort.remove((Object)pivotValue);
		bigger.add(pivotValue);
		
		for (int i = 0; i < arrayToSort.size(); i++)
		{
			int element = arrayToSort.get(i);
			if(element <= pivotValue) smaller.add(element);
			else bigger.add(element);
		}
		
		toReturn.addAll(bigger);
		toReturn.addAll(smaller);
		
		return concatenate(quicksort(smaller),quicksort(bigger));
	}	
	
	private static ArrayList<Integer> concatenate(ArrayList<Integer> al1, ArrayList<Integer> al2)
	{
		ArrayList<Integer> toReturn = new ArrayList<Integer>();
		toReturn.addAll(al1);
		toReturn.addAll(al2);
		return toReturn;
	}
}
