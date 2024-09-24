
package com.arturoar.tools;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * This class contains all the methods used for working with files. 
 * It includes various methods for reading and writing. 
 * It mainly works with the FileInputStream/FileOutputStream classes, 
 * allowing for reading and writing "raw bytes".
 * @author ArturoAR
 */

public class FileHandler implements AutoCloseable{

    private final static int BLOCK_SEPARATOR = 39;
    private final static int DATA_SEPARATOR = 44;
    private File file;
    private FileInputStream reader;
    private FileOutputStream writer;


    /**
    * Constructs an instance of the FileHandler class using the provided File object.
    *
    * @param file The File object representing the file to be accessed.
    * @throws java.io.FileNotFoundException If the specified file is not found.
    */
    public FileHandler(File file) throws FileNotFoundException {
        this.file = file;
        this.reader = new FileInputStream(this.file);
        this.writer = new FileOutputStream(this.file,true);
    }


    /**
     * Constructs an instance of the FileHandler class using the given filePath.
     * It creates the necessary directories and the file if it does not already exist.
     *
     * @param filePath The path of the file to be accessed.
     * @throws java.io.FileNotFoundException If the specified file is not found.
     * @throws java.io.IOException If an I/O error occurs while creating directories or the file.
     */
    public FileHandler(String filePath) throws FileNotFoundException, IOException{

        this.file = new File(filePath);

        // Create directories if they don't exist
        if (this.file.getParentFile().mkdirs()) {
            System.out.println("Directories created.");
        }

        // Create the file
        if (this.file.createNewFile()) {
            System.out.println("File created.");
        } else {
            System.out.println("File already exists.");
        }

        this.reader = new FileInputStream(this.file);
        this.writer = new FileOutputStream(this.file,true);
    }

   /**
     * Returns the absolute path of the file associated with this FileHandler instance.
     *
     * @return The absolute path of the file as a String.
     */
    public String getPath(){
        return this.file.getAbsolutePath();
    }
        
    /**
     * Resets the reader's position to the beginning of the file.
     * If an error occurs while resetting, a message is printed to the console.
     */
    public void resetReader(){
        try {
            this.reader.getChannel().position(0);
        } 
        catch (IOException ex) {
            System.out.println("*****File not found*****");
        }
    }

    /**
     * Reads raw bytes from the current position of the reader until the DATA_SEPARATOR character,
     * interpreting them as an integer.
     * 
     * @return The interpreted integer value.
     */
    public int readSingleData(){
        try {
            int x,i = 1, number = 0;
            Stack<Integer> stack = new Stack<>();
            while((x = this.reader.read()) != DATA_SEPARATOR && x != -1){
                stack.push(Character.getNumericValue(x));
            }
            if (stack.empty())
                return -1;
            else{
                while(!stack.empty()){
                    number = number + i * stack.pop();
                    i *= 10;
                }
                return number;
            }

        } catch (IOException ex) {
           return -1;
        }
    }
    /**
     * Reads the specified number of comma-separated blocks, defined by m,
     * interpreting them as integers and returning them in an ArrayList<Integer>.
     *
     * @param m The number of data blocks to read.
     * @return An ArrayList containing the interpreted integers.
     */
    public ArrayList<Integer> readData(int m){
	    try{
            boolean hasMoreData;
            int n = 0;
            int x,i,number;
            ArrayList<Integer> nData = new ArrayList<>();
            Stack<Integer> stack = new Stack<>();
            do{
                hasMoreData = false;
		        while((x = this.reader.read()) != DATA_SEPARATOR && x != -1){
			            stack.push(Character.getNumericValue(x));
                        hasMoreData = true;
		        }
		        i = 1;
		        number = 0;
		        while(!stack.empty()){
                    number = number + i * stack.pop();
                    i *= 10;
		        }
                if (hasMoreData)
                    nData.add(number);
                n++;
		    }while( n < m && x != -1);

            if (nData.isEmpty())
                return null;
            else
                return nData;

        } catch(Exception e){
            System.out.println("***File not found***");
            return null;
	    }
    }
    /**
     * Reads raw bytes from the current position until the BLOCK_SEPARATOR character,
     * interpreting them as integers. Returns an ArrayList containing the interpreted integers.
     *
     * @return An ArrayList with the decoded integers.
     */
    public ArrayList<Integer> readBlock(){
	    try{
            int x, i, number;
            ArrayList<Integer> nData = new ArrayList<>();
            Stack<Integer> stack = new Stack<>();
            do{
		        while((x = this.reader.read()) != DATA_SEPARATOR && x != -1 && x != BLOCK_SEPARATOR){
                    if (x != -1)
			            stack.push(Character.getNumericValue(x));
		        }
		        i = 1;
		        number = 0;
		        while(!stack.empty()){
                    number = number + i * stack.pop();
                    i *= 10;
		        }
                if (x != -1 && x != BLOCK_SEPARATOR)
                    nData.add(number);
		    }while( x != BLOCK_SEPARATOR && x != -1);
            if (nData.isEmpty())
                return null;
            else
                return nData;

        } catch(IOException e){
            System.out.println("***File not found***");
            return null;
	    }
    }
    
    /**
     * Reads raw bytes from the current position until the BLOCK_SEPARATOR character,
     * interpreting them as integers. Each integer is constructed from the bytes
     * read between DATA_SEPARATOR and BLOCK_SEPARATOR, and returned in an ArrayDeque.
     *
     * @return An ArrayDeque containing the decoded integers, or null if an I/O error occurs.
     */
     public ArrayDeque<Integer> readBlocks(){
	    try{
            int x,i,number;
            ArrayDeque<Integer> nData = new ArrayDeque<>();
            Stack<Integer> stack = new Stack<>();
            do{
		        while((x = this.reader.read()) != DATA_SEPARATOR && x != -1 && x != BLOCK_SEPARATOR){
                        stack.push(Character.getNumericValue(x));
		        }
		        i = 1;
		        number = 0;
		        while(!stack.empty()){
                    number = number + i * stack.pop();
                    i *= 10;
		        }
                if (x != -1 && x != BLOCK_SEPARATOR)
                    nData.add(number);
		    }while( x != BLOCK_SEPARATOR && x != -1);
            return nData;
        }catch(IOException e){
            System.out.println("***File not found***");
            return null;
    	}
    }

    /**
     * Closes the reader, releasing any resources associated with it.
     * If an error occurs during closing, it logs the exception using a logger.
     */
    public void closeReader(){
        try {
            this.reader.close();
        } 
        catch (IOException ex) {
                Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
    * Writes the integer data to the file, appending it to the end.
    *
    * @param number The integer to be written to the file.
    */
    public void writeSingleData(int number){
	try{
            String numberStr = String.valueOf(number);
            for (char x : numberStr.toCharArray()){
                this.writer.write(Integer.parseInt(String.valueOf(x))+48);
            }
            this.writer.write(DATA_SEPARATOR);
            removeLineBreaks();
	}
	catch(IOException e){
            System.out.println("***File not found***");
	}
    }
    
    /**
     * Writes the integers contained in the input list to the file, 
     * appending them to the end and separating them with commas.
     *
     * @param data The list of integers to be written to the file.
     */
    public void writeData(ArrayList<Integer> data){
	try{
            int size = data.size();
            String datoStr = "";
            for (int i = 0 ; i < size ;i++){
		datoStr = data.get(i).toString();
		char[] datoChar = datoStr.toCharArray();
		for (char x : datoChar){
                    this.writer.write(Integer.parseInt(String.valueOf(x))+48);
		}
                //if (!datos.isEmpty())
                this.writer.write(DATA_SEPARATOR);
            }
            removeLineBreaks();
        }
	catch(IOException e){
            System.out.println("***File not found***");
	}
    }
    /**
     * Writes the integers contained in the input list to the file, 
     * appending them to the end and separating them with commas,
     * followed by the BLOCK_SEPARATOR character at the end.
     *
     * @param data The list of integers to be written to the file.
     */
    public void writeBlocks(ArrayList<Integer> data){
	try{
            String datoStr = "";
            for (int i = 0 ; i < data.size() ;i++){
		        datoStr = data.get(i).toString();

		        for (char x : datoStr.toCharArray()){
                    this.writer.write(Integer.parseInt(String.valueOf(x))+48);
                }
                if (!data.isEmpty())
                    this.writer.write(DATA_SEPARATOR);
            }
            this.writer.write(BLOCK_SEPARATOR);
            removeLineBreaks();
	}
	catch(IOException e){
            System.out.println("***File not found***");
	}
    }
    
    /**
     * Adds a newline character to the file denoted by this abstract pathname.
     */
    public void addLineBreak(){
        try {
            this.writer.write(11);
        } catch (IOException ex) {
            //Logger.getLogger(ManejadorDeArchivos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
    /**
     * Writes the BLOCK_SEPARATOR character to the file and removes any line breaks
     * following its addition.
     */
    public void writeBlockSeparator(){
        try{
            this.writer.write(BLOCK_SEPARATOR);
            removeLineBreaks();
	}
	catch(IOException e){
            System.out.println("***File not found***");
	}
    }

    /**
     * Cleans the file of any line breaks caused by the write() method.
     *
     * @return true if and only if the file is successfully cleaned.
     */
    public boolean removeLineBreaks(){
        try{
            BufferedReader br = new BufferedReader(new FileReader(this.file));
            ArrayList<String> blocks = new ArrayList<>();
            String buffer;
            while((buffer = br.readLine()) != null){
		        blocks.add(buffer);
            }
            br.close();
            buffer = "";
            for (String x : blocks){
                buffer += x;
            }
            BufferedWriter bw = new BufferedWriter(new FileWriter(this.file));
            bw.write(buffer);
            bw.close();
            return true;
        }
        catch(IOException e){
            System.out.println("***File not found***");
            return false;
        }	
    }
     
    /**
     * Empties the file denoted by this abstract pathname.
     *
     * @return true if and only if the file is successfully cleared.
     */
    public boolean cleanFile(){
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter(this.file));
            bw.write("");
            bw.close();
            return true;
        }
        catch(IOException e){
            System.out.println("***File not found***");
            return false;
        } 
    }
   
    /**
     * Deletes the file or directory denoted by this abstract pathname.
     *
     * @return true if and only if the file or directory was successfully deleted.
     */
    public boolean eraseFile(){
        return this.file.delete();
    }

    /**
     * Closes the reader and writer, releasing any resources associated with them.
     * If an error occurs during closing, it logs the exception using a logger.
     */
    @Override
    public void close() {
        try {
            if (reader != null) {
                reader.close();
            }
            if (writer != null) {
                writer.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
	
}
