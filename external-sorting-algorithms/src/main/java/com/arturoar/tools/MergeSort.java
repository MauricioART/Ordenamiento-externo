
package com.arturoar.tools;
import java.util.ArrayList;
/**
 * Esta clase ordena un arreglo de enteros con el método Merge Sort 
 * @author Edgar Tista García
 */
public class MergeSort {
    
   private int[] arreglo; 
   
   /**
    * Regresa el arreglo ordenado en forma de ArrayList<>.
    * @return ArrayList con los elementos ordenados
    */
   public ArrayList<Integer> getArreglo(){
       ArrayList<Integer> datos = new ArrayList<>();
       for (int x : this.arreglo){
           datos.add(x);
       }
       return datos;
   }
   /**
    * Imprime en pantalla el arreglo de entrada.
    * @param arr Arreglo de enteros a imprimir
    */
    void printArray(int[] arr)
    { 
        int n = arr.length;
        for (int j : arr) System.out.print(j + " ");
        System.out.println(); 
    } 
    
    /**
     * Une los "subarreglos" de forma ordenada.  
     * @param arr Arreglo de enteros de entrada
     * @param l indice del primer elemento de arr
     * @param m indice del elemento medio de arr
     * @param r indice del ultimo elemento de arr
     */
    void merge(int[] arr, int l, int m, int r)
    { 
        int n1 = m - l + 1; 
        int n2 = r - m; 
  
        int[] L= new int [n1];
        int[] R = new int [n2];

        for (int i=0; i<n1; ++i)
            L[i] = arr[l + i];
        for (int j=0; j<n2; ++j) 
            R[j] = arr[m + 1+ j]; 
  
        int i = 0, j = 0; 
  
        int k = l; 
        while (i < n1 && j < n2) 
        { 
            if (L[i] <= R[j]) 
            { 
                arr[k] = L[i]; 
                i++; 
            } 
            else
            { 
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
     * Ordena con la ayuda de merge el arreglo de entrada.(Método MergeSort)
     * @param arr Arreglo de enteros de entrada
     * @param l indice del primer elemento de arr
     * @param r indice del ultimo elemento de arr 
     */
    public void sort(int[] arr,int l, int r) {
        
        if (l < r) { 
            int m = (l+r)/2; 
  
            sort(arr,l, m); 
            sort(arr,m+1, r); 
  
            merge(arr, l, m, r); 
        } 
        this.arreglo = arr;
    }
}