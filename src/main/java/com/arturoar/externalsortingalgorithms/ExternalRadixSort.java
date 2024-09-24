
package com.arturoar.externalsortingalgorithms;
import com.arturoar.tools.FileHandler;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.io.File;
/**
 * This class is intended for sorting the integer elements of a 
 * file in ascending order using the External Radix Sort algorithm.
 * 
 * @author ArturoAR
 */
public class ExternalRadixSort {
    
    private final int BLOCK_SIZE = 5;
    private final String[] tempFilesName = {"Zero.txt","One.txt","Two.txt","Three.txt","Four.txt","Five.txt","Six.txt","Seven.txt","Eight.txt","Nine.txt"};
    private ArrayList<FileHandler> fileHandlers;
    private FileHandler fileHandler; 
    private ArrayList<FileHandler> copyFilesHandlers;

    /**
     * Constructs an instance of the ExternalRadixSort class, initializing file handlers 
     * for the specified file and creating temporary and auxiliary file handlers.
     * 
     * @param file the file to be sorted using the external radix sort algorithm
     * @throws FileNotFoundException if the specified file does not exist
     * @throws IOException if an I/O error occurs
     */
    public ExternalRadixSort(File file) throws FileNotFoundException, IOException {
        this.fileHandlers = new ArrayList<>();
        this.copyFilesHandlers = new ArrayList<>();
        this.fileHandler = new FileHandler(file);
        for (int i = 0 ; i < 10 ; i++){
            this.fileHandlers.add(new FileHandler("Radix/File" + this.tempFilesName[i]));
            this.copyFilesHandlers.add(new FileHandler("Radix/AuxiliarFile" + this.tempFilesName[i]));    
        }
    }
    
    /**
     * Sorts the elements of the file located at the specified filePath
     * using the External Radix Sort algorithm.
     */
    public void sort(int maxDigits){
        int upperLimit = (int) Math.pow(10, maxDigits + 1);
        ArrayList<Integer> data;
        for (int i = 10 ; i< upperLimit ; i*=10){
            while((data = this.fileHandler.readData(BLOCK_SIZE)) != null){
                for(Integer x : data){
                    int digito = x % i;
                    digito = 10*digito/i;
                    this.fileHandlers.get(digito).writeSingleData(x);
                }
            }
            this.fileHandler.resetReader();
            for(FileHandler x : this.fileHandlers ){
                x.resetReader();
            }
                
            this.fileHandler.cleanFile();
                
            for (int j = 0 ; j < 10 ;j++){
                ArrayList<Integer> buffer;
                while((buffer = this.fileHandlers.get(j).readData(BLOCK_SIZE)) != null){
                    this.fileHandler.writeData(buffer);
                    this.copyFilesHandlers.get(j).writeData(buffer);
                }
                this.copyFilesHandlers.get(j).addLineBreak();
            }
            for(FileHandler x : this.fileHandlers ){
                x.cleanFile();
            }
        }
        eraseTempFiles();    
    }
    /**
     * Erase temporal files.
     */
    public void eraseTempFiles(){
        for(FileHandler x : this.fileHandlers){
            x.eraseFile();
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
    
