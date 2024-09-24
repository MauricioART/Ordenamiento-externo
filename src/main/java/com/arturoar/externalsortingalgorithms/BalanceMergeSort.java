
package com.arturoar.externalsortingalgorithms;

import com.arturoar.tools.FileHandler;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.io.File;

/**
 * This class is intended for sorting the integer elements of a 
 * file in ascending order using the Balanced Merge Sort algorithm.
 * 
 * @author ArturoAR
 */
public class BalanceMergeSort {
    
    private final String[] tempFilesNames = {"Uno.txt","Dos.txt"};
    private ArrayList<FileHandler> fileHandlers;
    private FileHandler fileHandler; 
    private ArrayList<FileHandler> copyFilesHandlers;
    
    /**
     * Constructs an instance of the BalanceMergeSort class, initializing file handlers 
     * for sorting using the Balanced Merge Sort algorithm.
     * 
     * @param file the file to be sorted
     * @throws FileNotFoundException if the specified file does not exist
     * @throws IOException if an I/O error occurs while creating the file handlers
     * 
     * @author ArturoAR
     */
    public BalanceMergeSort(File file) throws FileNotFoundException, IOException {
        this.fileHandlers = new ArrayList<>();
        this.copyFilesHandlers = new ArrayList<>();
        this.fileHandler = new FileHandler(file);
        for (int i = 0 ; i < 2 ; i++){
            this.fileHandlers.add(new FileHandler("BalanceMergeSort/File" + this.tempFilesNames[i]));
            this.copyFilesHandlers.add(new FileHandler("BalanceMergeSort/AuxiliarFile" + this.tempFilesNames[i]));        
        }
        for (FileHandler handler : this.copyFilesHandlers){
            handler.cleanFile();
        }
    }

    /**
     * Sorts the elements of the file in the direction specified by the
     * String filePath, using the Balanced Merge Sort algorithm.
     */
    public void sort() {
        createSortedBlocks();

        int even = 0, even2 = 0, numberOfBlocks = 0;

        while (numberOfBlocks != 1) {
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
     * Reads data from the file to be sorted, creating ordered blocks 
     * and storing them in auxiliary files.
     */
    public void createSortedBlocks(){
        int integer = 0, even = 0;
        boolean isGreater;
        ArrayDeque<Integer> stack = new ArrayDeque<>();
        ArrayList<Integer> block = new ArrayList<>();
        do{
            isGreater = true;
            while(isGreater){
                integer = this.fileHandler.readSingleData();
                if(stack.isEmpty())
                    stack.add(integer);
                else
                    if(integer > stack.getLast())
                        stack.add(integer);
                    else
                        isGreater = false;
            }
            while(!stack.isEmpty()){
                block.add(stack.poll());
            }
            this.fileHandlers.get((even++)%2).writeBlocks(block);
            block.clear();
            stack.add(integer);
            
        }while(integer != -1);
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
     * Clears the temporary files.
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
    public void showFilePaths(){
        System.out.println(">>>>>File sorted ");
        System.out.println("Sorted file path: " + this.fileHandler.getPath());
        int i = 0;
        for (FileHandler x : this.copyFilesHandlers)
            System.out.println("Auxiliar file " + (i++) + ": " + x.getPath());
    }
}