/**
 * 
 */
package com.flatironschool.javacs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Provides sorting algorithms.
 *
 */
public class ListSorter<T> {

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void insertionSort(List<T> list, Comparator<T> comparator) {
	
		for (int i=1; i < list.size(); i++) {
			T elt_i = list.get(i);
			int j = i;
			while (j > 0) {
				T elt_j = list.get(j-1);
				if (comparator.compare(elt_i, elt_j) >= 0) {
					break;
				}
				list.set(j, elt_j);
				j--;
			}
			list.set(j, elt_i);
		}
	}

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void mergeSortInPlace(List<T> list, Comparator<T> comparator) {
		List<T> sorted = mergeSort(list, comparator);
		list.clear();
		list.addAll(sorted);
	}

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * Returns a list that might be new.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public List<T> mergeSort(List<T> list, Comparator<T> comparator) {
        if(list.size() == 1){
            return list;
        }
        
        List<T> first = list.subList(0, list.size()/2);
        List<T> second = list.subList(list.size()/2, list.size());
        
        insertionSort(first, comparator);
        insertionSort(second, comparator);
        
        List<T> mergedList = new ArrayList<T>();
        
        int left = 0, right = 0;
        
        while(left != first.size() && right != second.size()){
            if(comparator.compare(first.get(left), second.get(right)) <= 0){
                mergedList.add(first.get(left));
                left++;
            }else{
                mergedList.add(second.get(right));
                right++;
            }
        }
        
        if(left < first.size()){
            while(left < first.size()){
                mergedList.add(first.get(left));
                left++;
            }
        }
        if(right < second.size()){
            while(right < second.size()){
                mergedList.add(second.get(right));
                right++;
            }
        }
        
        return mergedList;
	}

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void heapSort(List<T> list, Comparator<T> comparator) {
        PriorityQueue<T> queue = new PriorityQueue();
        
        //add all of the items in the list to the queue
        for(T item: list){
            queue.offer(item);
        }
        
        list.clear();//clear the list to replace it with the sorted list
        
        //add items to the list in ascending order
        //they are already sorted in the priority queue
        while(queue.size() > 0){
            list.add(queue.poll());
        }
	}

	
	/**
	 * Returns the largest `k` elements in `list` in ascending order.
	 * 
	 * @param k
	 * @param list
	 * @param comparator
	 * @return 
	 * @return
	 */
	public List<T> topK(int k, List<T> list, Comparator<T> comparator) {
        PriorityQueue<T> heap = new PriorityQueue();
        List<T> largest = new ArrayList<T>();
        
        for(T item: list){
            if(heap.size() < k){//add the item because there is still space
                heap.offer(item);
            }else if(comparator.compare(item, heap.peek()) > 0){
                heap.poll();//remove the smallest number
                heap.offer(item);//add the new item
            }
        }
        
        //add the largest 'k' elements to the list
        for(int i = 0; i < k; ++i){
            largest.add(heap.poll());
        }
        
        return largest;
        
	}

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<Integer> list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
		
		Comparator<Integer> comparator = new Comparator<Integer>() {
			@Override
			public int compare(Integer n, Integer m) {
				return n.compareTo(m);
			}
		};
		
		ListSorter<Integer> sorter = new ListSorter<Integer>();
		sorter.insertionSort(list, comparator);
		System.out.println(list);

		list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
		sorter.mergeSortInPlace(list, comparator);
		System.out.println(list);

		list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
		sorter.heapSort(list, comparator);
		System.out.println(list);
	
		list = new ArrayList<Integer>(Arrays.asList(6, 3, 5, 8, 1, 4, 2, 7));
		List<Integer> queue = sorter.topK(4, list, comparator);
		System.out.println(queue);
	}
}
