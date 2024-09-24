
package com.arturoar.externalsortingalgorithms;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import com.arturoar.tools.*;

/**
 * Esta clase está destinada al ordenamiento de los elementos, enteros, de 
 * un archivo de manera ascendente con el método homonimo de la clase.
 * @author ArturoAR
 */
public class PolyphaseMergeSort {
    
    private final int BLOCK_SIZE;
    private final String[] tempFilesName = {"One.txt","Two.txt"};
    private ArrayList<FileHandler> fileHandlers;
    private FileHandler fileHandler; 
    private ArrayList<FileHandler> copyFilesHandlers;
    
    /**
     * Initializes the PolyphaseMergeSort with the given file and block size.
     * Prepares auxiliary files for the sorting process using the Polyphase Merge Sort algorithm.
     * 
     * @param file The file to be sorted.
     * @param m The block size used during sorting.
     * @throws FileNotFoundException If the specified file cannot be found.
     * @throws IOException If an I/O error occurs during file handling.
     */
    public PolyphaseMergeSort(File file, int m) throws FileNotFoundException, IOException {
        this.BLOCK_SIZE = m;
        this.fileHandlers = new ArrayList<>();
        this.copyFilesHandlers = new ArrayList<>();
        this.fileHandler = new FileHandler(file);
        for (int i = 0 ; i < 2 ; i++){
            this.fileHandlers.add(new FileHandler("Polyphase/File" + this.tempFilesName[i]));
            this.copyFilesHandlers.add(new FileHandler("Polyphase/AuxiliarFile" + this.tempFilesName[i]));
                
        }
    }

    /**
     * Sorts the elements of the file located at the specified file path using the Polyphase Merge Sort algorithm. 
     **/
    public void sort(){
        
        ArrayList<Integer> data;
        int even = 0, even2 = 0;  //Manejo de intercalación de los archivos
        while((data = this.fileHandler.readData(BLOCK_SIZE)) != null){
            int[] dataInt = new int[data.size()];
            int i = 0;
            for(Integer integer : data){
                dataInt[i++] = integer;
            }
            MergeSort mergeS = new MergeSort();
            mergeS.sort(dataInt,0,BLOCK_SIZE-1);
            this.fileHandlers.get((even++)%2).writeBlocks(mergeS.getArray());
            this.copyFilesHandlers.get((even2++)%2).writeBlocks(mergeS.getArray());
        }
        int numberOfBlocks = 0;
        while(numberOfBlocks != 1){
            numberOfBlocks = 0;
            this.fileHandler.cleanFile();
            ArrayDeque<Integer> AData = this.fileHandlers.get(0).readBlocks(), BData = this.fileHandlers.get(1).readBlocks();

            while (!AData.isEmpty() || !BData.isEmpty()) {
                numberOfBlocks++;
                while (!AData.isEmpty() || !BData.isEmpty()) {
                    if (!AData.isEmpty() && !BData.isEmpty())
                        if (AData.peek() < BData.peek())
                            this.fileHandler.writeSingleData(AData.poll());
                        else
                            this.fileHandler.writeSingleData(BData.poll());
                    else if (AData.isEmpty())
                        this.fileHandler.writeSingleData(BData.poll());
                    else
                        this.fileHandler.writeSingleData(AData.poll());
                }
                AData = this.fileHandlers.get(0).readBlocks();
                BData = this.fileHandlers.get(1).readBlocks();
                if (!(AData.isEmpty() && BData.isEmpty()))
                    this.fileHandler.writeBlockSeparator();
            }

            resetReaders();
            //TODO: Escribir en los archivos auxiliares antes de limpiarlos y tambien el del manejador
            cleanTempFiles();
            ArrayList<Integer> block;
            even = 0;
            even2 = 0;
            while ((block = this.fileHandler.readBlock()) != null) {
                this.fileHandlers.get((even++) % 2).writeBlocks(block);
                this.copyFilesHandlers.get((even2++) % 2).writeBlocks(block);
            }
        }
        eraseTempFiles();
    }
  
    /**
     * Resets the reader of each file handler to the beginning of the file.
    */
    public void resetReaders(){
        this.fileHandler.resetReader();
        this.fileHandlers.get(0).resetReader();
        this.fileHandlers.get(1).resetReader();
    }
    /**
     * Clears the temporal files.
     */
    public void cleanTempFiles(){
        this.fileHandlers.get(0).cleanFile();
        this.fileHandlers.get(1).cleanFile();
        this.copyFilesHandlers.get(0).addLineBreak();
        this.copyFilesHandlers.get(1).addLineBreak();
    }
    /**
     * Erase the temporary files.
     */
    public void eraseTempFiles(){
        for(FileHandler x : this.fileHandlers){
            x.cleanFile();
        }
    }
    
    
    /**
     * Displays the paths of the files used to sort the data from the file.
     */
    public void showFilesPath(){
        System.out.println(">>>>>File sorted ");
        System.out.println("Sorted file path: " + this.fileHandler.getPath());
        int i = 0;
        for (FileHandler x : this.copyFilesHandlers)
            System.out.println("Auxiliar file " + (i++) + ": " + x.getPath());
    }
}
