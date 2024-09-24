
package com.arturoar.tools;

import java.util.ArrayList;

/**
 * This class sorts an array of integers using the Merge Sort method.
 * 
 * @author GeeksForGeeks
 */
public class MergeSort {
    
   private int[] array; 
   
   /**
    * Returns the sorted array as an ArrayList<>.
    * 
    * @return ArrayList containing the sorted elements.
    */
   public ArrayList<Integer> getArray() {
       ArrayList<Integer> datos = new ArrayList<>();
       for (int x : this.array) {
           datos.add(x);
       }
       return datos;
   }

   /**
    * Prints the input array to the console.
    * 
    * @param arr Array of integers to print.
    */
   void printArray(int[] arr) { 
       int n = arr.length;
       for (int j : arr) System.out.print(j + " ");
       System.out.println(); 
   } 
   
   /**
    * Merges the "subarrays" in a sorted manner.
    * 
    * @param arr Input array of integers.
    * @param l Index of the first element of arr.
    * @param m Index of the middle element of arr.
    * @param r Index of the last element of arr.
    */
   void merge(int[] arr, int l, int m, int r) { 
       int n1 = m - l + 1; 
       int n2 = r - m; 

       int[] L = new int[n1];
       int[] R = new int[n2];

       for (int i = 0; i < n1; ++i)
           L[i] = arr[l + i];
       for (int j = 0; j < n2; ++j) 
           R[j] = arr[m + 1 + j]; 

       int i = 0, j = 0; 
       int k = l; 
       
       while (i < n1 && j < n2) { 
           if (L[i] <= R[j]) { 
               arr[k] = L[i]; 
               i++; 
           } else { 
               arr[k] = R[j]; 
               j++; 
           } 
           k++; 
       } 

       while (i < n1) { 
           arr[k] = L[i]; 
           i++; 
           k++; 
       } 

       while (j < n2) { 
           arr[k] = R[j]; 
           j++; 
           k++; 
       } 
   } 

   /**
    * Sorts the input array with the help of merge (Merge Sort method).
    * 
    * @param arr Input array of integers.
    * @param l Index of the first element of arr.
    * @param r Index of the last element of arr.
    */
   public void sort(int[] arr, int l, int r) {
       if (l < r) { 
           int m = (l + r) / 2; 
           sort(arr, l, m); 
           sort(arr, m + 1, r); 
           merge(arr, l, m, r); 
       } 
       this.array = arr;
   }
}
