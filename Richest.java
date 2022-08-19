// Michael Haynes

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class Richest {
	public static int heapSize;
	
	public static void main(String[] args) throws Exception {
		File fn = new File(args[0]);
		Scanner fin = new Scanner(fn);
		int[] minHeap = new int[10001]; // 10001 length so 1 based indexing can be used
		
		// create the array to be used as a heap
		for(int i = 1; i < minHeap.length; i++) {
			minHeap[i] = Integer.parseInt(fin.nextLine());
		}
		
		minHeap = buildMinHeap(minHeap);
		
		// traverse the file for integers that are greater than the smallest integer in the top 10000
		while(fin.hasNext()) {
			int i = Integer.parseInt(fin.nextLine());
			if(i > minHeap[1]) {
				minHeap[1] = i;
				minHeap = minHeapify(minHeap, 1);
			}
		}
		
		minHeap = heapSort(minHeap);
		FileWriter fr = new FileWriter("richest.output.txt");
		for(int i = 1; i < minHeap.length; i++) 
			fr.write(minHeap[i] + "\n");
		fin.close();
		fr.close();

	}
	
	// sorts the integers in the array to be a min heap and initializes heapSize
	public static int[] buildMinHeap(int[] arr) {
		heapSize = arr.length;
		for(int i = arr.length/2; i >= 1; i--)
			arr = minHeapify(arr, i);	
		return arr;
	}
	
	// sorts i so that the given array is a minHeap, pseudocode from Intro to Algorithms 3rd edition by CLRS page 154 for maxHeapify reworked for minHeapify
	public static int[] minHeapify(int[] arr, int i) {
		int smallest;
		int left = 2 * i; 
		int right = 2 * i + 1;
		
		if(left < heapSize && arr[left] < arr[i])
			smallest = left;
		else
			smallest = i;
		if(right < heapSize && arr[right] < arr[smallest])
			smallest = right;
		if(smallest != i) {
			int tmp = arr[i];
			arr[i] = arr[smallest];
			arr[smallest] = tmp;
			arr = minHeapify(arr, smallest);
		}
		return arr;
	}
	
	// sorts the heap in descending order so that it can be written to richest.output.txt
	public static int[] heapSort(int[] arr) {
		arr = buildMinHeap(arr);
		for(int i = arr.length - 1; i >= 2; i--) {
			int tmp = arr[1];
			arr[1] = arr[i];
			arr[i] = tmp;
			heapSize--;
			arr = minHeapify(arr, 1);
		}
		return arr;
	}
	
	

}
