
package com.arturoar.Herramientas;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

//import javafx.stage.FileChooser;

/**
 * Esta clase contiene todos los métodos usados para trabajar con los archivos, cuenta
 * con varios métodos de escritura y lectura. Mayormente trabaja con las clases  
 * FileInputStream/FileOutputStream por lo que se leen y escriben "raw bytes".
 * @author ArturoAR
 */

public class ManejadorDeArchivos implements AutoCloseable{

    private final static int SEPARADOR_BLOQUES = 39;
    private final static int SEPARADOR_DATOS = 44;
    private File archivo;
    private FileInputStream lector;
    private FileOutputStream escritor;
    
    /**
     * Construye una instancia de la clase File con la direccion filePath
     * y con ella otras instancias de las clases FileInputStream/FileOutputStream
     * @param filePath 
     * @throws java.io.FileNotFoundException 
     */
    public ManejadorDeArchivos(String filePath) throws FileNotFoundException, IOException{

        this.archivo = new File(filePath);
        if (!this.archivo.exists()) {
            this.archivo.createNewFile();
        }

        this.lector = new FileInputStream(this.archivo);
        this.escritor = new FileOutputStream(this.archivo,true);
    }
/*
    public ManejadorDeArchivos(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt")
        );
        this.archivo = fileChooser.showOpenDialog();
        this.lector = new FileInputStream(this.archivo);
        this.escritor = new FileOutputStream(this.archivo,true);
    }*/

    /**
     * Regresa la dirección absoluta del archivo con el que se trabaja. 
     * @return 
     */
    public String getPath(){
        return this.archivo.getAbsolutePath();
    }
        
    /**
     * Regresa al atributo de la clase FileInputStream a la posición inicial del archivo.
     */
    public void resetLector(){
        try {
            this.lector.getChannel().position(0);
        } 
        catch (IOException ex) {
            System.out.println("No se puede");
        }
    }
    /**
     * Lee los raw bytes desde la posición actual del lector hasta el carácter SEPARADOR_DATOS
     * interpretandolos como un entero.
     * @return el entero interpretado
     */
    public int leerDato(){
        try {
            int n = 0;
            int x,i = 1, dato = 0;
            Stack<Integer> pila = new Stack<>();
            while((x = this.lector.read()) != SEPARADOR_DATOS && x != -1){
                pila.push(Character.getNumericValue(x));
            }
            if (pila.empty())
                return -1;
            else{
                while(!pila.empty()){
                    dato = dato + i * pila.pop();
                    i *= 10;
                }
                return dato;
            }

        } catch (IOException ex) {
           return -1;
        }
    }
    /**
     * Lee el número de bloques separados por comas establecidos por m, interpretandolos
     * como enteros y regresandolos en un ArrayList<Integer>.
     * @param m el número de datos a leer
     * @return los enteros interpretados contenidos en un ArrayList<>
     */
    public ArrayList<Integer> leerDatos(int m){
	    try{
            boolean hasMoreData;
            int n = 0;
            int x,i,dato;
            ArrayList<Integer> nDatos = new ArrayList<>();
            Stack<Integer> pila = new Stack<>();
            do{
                hasMoreData = false;
		        while((x = this.lector.read()) != SEPARADOR_DATOS && x != -1){
			            pila.push(Character.getNumericValue(x));
                        hasMoreData = true;
		        }
		        i = 1;
		        dato = 0;
		        while(!pila.empty()){
                    dato = dato + i * pila.pop();
                    i *= 10;
		        }
                if (hasMoreData)
                    nDatos.add(dato);
                n++;
		    }while( n < m && x != -1);

            if (nDatos.isEmpty())
                return null;
            else
                return nDatos;

        } catch(Exception e){
            System.out.println("***Archivo no encontrado***");
            return null;
	    }
    }
    /**
     * Lee los raw bytes desde la posición actual hasta el carácter SEPARADOR_BLOQUES,
     * interpretandolos como enteros. Regresa un ArrayList con los enteros interpretados.
     * @return un ArrayList con los enteros decodificados
     */
    public ArrayList<Integer> leerBloque(){
	    try{
            int x, i, dato;
            ArrayList<Integer> nDatos = new ArrayList<>();
            Stack<Integer> pila = new Stack<>();
            do{
		        while((x = this.lector.read()) != SEPARADOR_DATOS && x != -1 && x != SEPARADOR_BLOQUES){
                    if (x != -1)
			            pila.push(Character.getNumericValue(x));
		        }
		        i = 1;
		        dato = 0;
		        while(!pila.empty()){
                    dato = dato + i * pila.pop();
                    i *= 10;
		        }
                if (x != -1 && x != SEPARADOR_BLOQUES)
                    nDatos.add(dato);
		    }while( x != SEPARADOR_BLOQUES && x != -1);
            if (nDatos.isEmpty())
                return null;
            else
                return nDatos;

        } catch(IOException e){
            System.out.println("***Archivo no encontrado***");
            return null;
	    }
    }
    /**
     * 
     */
     public ArrayDeque<Integer> leerBloques(){
	    try{
            int x,i,dato;
            ArrayDeque<Integer> nDatos = new ArrayDeque<>();
            Stack<Integer> pila = new Stack<>();
            do{
		        while((x = this.lector.read()) != SEPARADOR_DATOS && x != -1 && x != SEPARADOR_BLOQUES){
                        pila.push(Character.getNumericValue(x));
		        }
		        i = 1;
		        dato = 0;
		        while(!pila.empty()){
                    dato = dato + i * pila.pop();
                    i *= 10;
		        }
                if (x != -1 && x != SEPARADOR_BLOQUES)
                    nDatos.add(dato);
		    }while( x != SEPARADOR_BLOQUES && x != -1);
            return nDatos;
        }catch(IOException e){
            System.out.println("***Archivo no encontrado***");
            return null;
    	}
    }

    /**
     * 
     */
    public void closeLector(){
        try {
            this.lector.close();
        } 
        catch (IOException ex) {
                Logger.getLogger(ManejadorDeArchivos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * Escribe el entero dato en el archivo, agregandolo al final de éste.
     * @param dato entero a escribir en el archivo
     */
    public void escribirDato(int dato){
	try{
            String datoStr = String.valueOf(dato);
            char[] datoChar = datoStr.toCharArray();
            for (char x : datoChar){
                this.escritor.write(Integer.parseInt(String.valueOf(x))+48);
            }
            this.escritor.write(SEPARADOR_DATOS);
            depurarArchivo();
	}
	catch(IOException e){
            System.out.println("***Archivo no encontrado***");
	}
    }
    
    /**
     * Escribe los enteros contenidos en la lista de entrada en el archivo, agregandolos al final 
     * de éste y separandolos por comas.
     * @param datos Lista de enteros a escribir
     */
    public void escribirDatos(ArrayList<Integer> datos){
	try{
            int size = datos.size();
            String datoStr = "";
            for (int i = 0 ; i < size ;i++){
		datoStr = datos.get(i).toString();
		char[] datoChar = datoStr.toCharArray();
		for (char x : datoChar){
                    this.escritor.write(Integer.parseInt(String.valueOf(x))+48);
		}
                //if (!datos.isEmpty())
                this.escritor.write(SEPARADOR_DATOS);
            }
            depurarArchivo();
        }
	catch(IOException e){
            System.out.println("***Archivo no encontrado***");
	}
    }
    /**
     * Escribe los enteros contenidos en la lista de entrada en el archivo, agregandolos al final 
     * de éste y separandolos por comas, agregando al último el carácter SEPARADOR_BLOQUES.
     * @param datos 
     */
    public void escribirBloques(ArrayList<Integer> datos){
	try{
            String datoStr = "";
            for (int i = 0 ; i < datos.size() ;i++){
		        datoStr = datos.get(i).toString();

		        for (char x : datoStr.toCharArray()){
                    this.escritor.write(Integer.parseInt(String.valueOf(x))+48);
                }
                if (!datos.isEmpty())
                    this.escritor.write(SEPARADOR_DATOS);
            }
            this.escritor.write(SEPARADOR_BLOQUES);
            depurarArchivo();
	}
	catch(IOException e){
            System.out.println("***Archivo no encontrado***");
	}
    }
    
    /**
     * "Agrega" un salto de linea en el archivo denotado por este abstract pathname.
     */
    public void saltarLinea(){
        try {
            this.escritor.write(11);
            /*BufferedReader br = new BufferedReader(new FileReader(this.archivo));
            ArrayList<String> bloques = new ArrayList<>();
            String buffer;
            while((buffer = br.readLine()) != null){
                StringTokenizer sTkr = new StringTokenizer(buffer,"&");
                while(sTkr.hasMoreTokens()){
                    bloques.add(sTkr.nextToken());
                }
            }
            br.close();
            BufferedWriter bw = new BufferedWriter(new FileWriter(this.archivo));
            for (String x : bloques){
                bw.write(x);
                bw.newLine();
            }
            bw.close();*/
        } catch (IOException ex) {
            //Logger.getLogger(ManejadorDeArchivos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * Escribe en el archivo el carácter SEPARADOR_BLOQUES denotado por este
     * abstract pathname.
     */
    public void escribirSeparadorBloque(){
        try{
            this.escritor.write(SEPARADOR_BLOQUES);
            depurarArchivo();
	}
	catch(IOException e){
            System.out.println("***Archivo no encontrado***");
	}
    }

    /**
     * Limpia el archivo de cualquier salto de linea provocado por el método write()
     * @return true si y solo si se logra depurar el archivo
     */
    public boolean depurarArchivo(){
        try{
            BufferedReader br = new BufferedReader(new FileReader(this.archivo));
            ArrayList<String> bloques = new ArrayList<>();
            String buffer;
            while((buffer = br.readLine()) != null){
		        bloques.add(buffer);
            }
            br.close();
            buffer = "";
            for (String x : bloques){
                buffer += x;
            }
            BufferedWriter bw = new BufferedWriter(new FileWriter(this.archivo));
            bw.write(buffer);
            bw.close();
            return true;
        }
        catch(IOException e){
            System.out.println("***Archivo no encontrado***");
            return false;
        }	
    }
     
    /**
     * Vacía el archivo denotado por este abstract pathname.
     * @return true si y solo si se logra limpiar el archivo
     */
    public boolean limpiarArchivo(){
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter(this.archivo));
            bw.write("");
            bw.close();
            return true;
        }
        catch(IOException e){
            System.out.println("***Archivo no encontrado***");
            return false;
        } 
    }
    /**
     * Elimina el archivo o la carpeta denotada por este abstact pathname.
     */
    public boolean borrarArchivo(){
        return this.archivo.delete();
    }

    @Override
    public void close() {
        try {
            if (lector != null) {
                lector.close();
            }
            if (escritor != null) {
                escritor.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(ManejadorDeArchivos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
	
}
