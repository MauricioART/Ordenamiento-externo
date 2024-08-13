
package OrdenamientosExternos;
import Herramientas.*;
import java.io.FileNotFoundException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Formatter;

/**
 * Esta clase está destinada al ordenamiento de los elementos, enteros, de 
 * un archivo de manera ascendente con el método homonimo de la clase.
 * @author Equipo 3
 */
public class Polifase {
    
    private final int TAMANIO_BLOQUE = 5;
    private final String[] nombresArchivosTemp = {"Uno.txt","Dos.txt"};
    private ArrayList<ManejadorDeArchivos> manejadores;
    private ManejadorDeArchivos manejadorArchivo; 
    private ArrayList<ManejadorDeArchivos> manejadoresCopia;
    
    /**
     * Construye una instancia de la clase Polifase, inicializando
     * el atributo manejadorArchivo con filePath, creando dos archivos auxiliares
     * e inicializando manejadores con las direcciones de éstos.
     * @param filePath String de la dirección absoluta del archivo a ordenar
     * @throws java.io.FileNotFoundException
     */
    public Polifase(String filePath) throws FileNotFoundException{
        this.manejadores = new ArrayList<>();
        this.manejadoresCopia = new ArrayList<>();
        this.manejadorArchivo = new ManejadorDeArchivos(filePath);
        for (int i = 0 ; i < 2 ; i++){
            Formatter archivo = new Formatter("Polifase/Archivo" + this.nombresArchivosTemp[i]);
            Formatter archivoCopia = new Formatter("Polifase/ArchivoAuxiliar" + this.nombresArchivosTemp[i]);
            this.manejadores.add(new ManejadorDeArchivos("Polifase/Archivo" + this.nombresArchivosTemp[i]));
            this.manejadoresCopia.add(new ManejadorDeArchivos("Polifase/ArchivoAuxiliar" + this.nombresArchivosTemp[i]));
                
        }
    }
    /**
     * Ordena los elementos del archivo en la dirección que establece el String filePath,
     * con el método de Polifase.
     */
    public void ordenar(){
        
        ArrayList<Integer> datos;
        int par = 0;
        int par2 = 0;
        while((datos = this.manejadorArchivo.leerDatos(TAMANIO_BLOQUE)) != null){
            int[] datosInt = new int[datos.size()];
            int i = 0;
            for(Integer dato : datos){
                datosInt[i++] = dato;
            }
            MergeSort mergeS = new MergeSort();
            mergeS.sort(datosInt,0,TAMANIO_BLOQUE-1);
            this.manejadores.get((par++)%2).escribirBloques(mergeS.getArreglo());
            this.manejadoresCopia.get((par2++)%2).escribirBloques(mergeS.getArreglo());
        }
        int numeroBloques = 0;
        while(numeroBloques != 1){
            numeroBloques = 0;
            this.manejadorArchivo.limpiarArchivo();
            ArrayDeque<Integer> datosA,datosB;
            while((datosA = this.manejadores.get(0).leerBloques()) != null && (datosB = this.manejadores.get(1).leerBloques()) != null ){
                numeroBloques++;
                while(!datosA.isEmpty() || !datosB.isEmpty()){
                    if (!datosA.isEmpty() && !datosB.isEmpty())
                        if(datosA.peek() < datosB.peek())
                            this.manejadorArchivo.escribirDato(datosA.poll());
                        else
                            this.manejadorArchivo.escribirDato(datosB.poll());
                    else
                        if(datosA.isEmpty())
                            this.manejadorArchivo.escribirDato(datosB.poll());
                        else
                            this.manejadorArchivo.escribirDato(datosA.poll());
                }
                this.manejadorArchivo.escribirSeparadorBloque();
            }
            resetearLectores();
            limpiarArchivosTemp();
            ArrayList<Integer> bloque;
            par = 0;
            par2 = 0;
            while((bloque = this.manejadorArchivo.leerBloque()) != null){
                this.manejadores.get((par++)%2).escribirBloques(bloque);
                this.manejadoresCopia.get((par2++)%2).escribirBloques(bloque);
            }
        }
        borrarArchivosTemp();
    }
  
    /**
     * Regresa al lector de cada manejador de archivo a la posición 0 del
     * archivo.
     */
    public void resetearLectores(){
        this.manejadorArchivo.resetLector();
        this.manejadores.get(0).resetLector();
        this.manejadores.get(1).resetLector();
    }
    /**
     * Vacia los archivos temporales.
     */
    public void limpiarArchivosTemp(){
        this.manejadores.get(0).limpiarArchivo();
        this.manejadores.get(1).limpiarArchivo();
        this.manejadoresCopia.get(0).saltarLinea();
        this.manejadoresCopia.get(1).saltarLinea();
    }
    /**
     * Elimina los archivos temporales.
     */
    public void borrarArchivosTemp(){
        for(ManejadorDeArchivos x : this.manejadores){
            x.borrarArchivo();
        }
    }
    /**
     * Despliega la dirección ABSOLUTA de los archivos usados para ordenar los
     * datos del archivo.
     */
    public void mostrarDireccionArchivos(){
        System.out.println(">>>>>Archivo Ordenado ");
        System.out.println("Dirección archivo ordenado: " + this.manejadorArchivo.getPath());
        int i = 0;
        for (ManejadorDeArchivos x : this.manejadoresCopia)
            System.out.println("Archivo auxiliar " + (i++) + ": " + x.getPath());
    }
}
