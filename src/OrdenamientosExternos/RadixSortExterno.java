
package OrdenamientosExternos;
import Herramientas.ManejadorDeArchivos;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Formatter;
/**
 * Esta clase está destinada al ordenamiento de los elementos, enteros, de 
 * un archivo de manera ascendente con el método homonimo de la clase.
 * @author Equipo 3
 */
public class RadixSortExterno {
    
    private final int TAMANIO_BLOQUE = 5;
    private final String[] nombresArchivosTemp = {"Cero.txt","Uno.txt","Dos.txt","Tres.txt","Cuatro.txt","Cinco.txt","Seis.txt","Siete.txt","Ocho.txt","Nueve.txt"};
    private ArrayList<ManejadorDeArchivos> manejadores;
    private ManejadorDeArchivos manejadorArchivo; 
    private ArrayList<ManejadorDeArchivos> manejadoresCopia;

    /**
     * Construye una instancia de la clase RadixSortExterno, inicializando
     * el atributo manejadorArchivo con filePath, creando diez archivos auxiliares
     * e inicializando manejadores con las direcciones de éstos.
     * @param filePath String de la dirección absoluta del archivo a ordenar
     * @throws java.io.FileNotFoundException
     */
    public RadixSortExterno(String filePath) throws FileNotFoundException{
        this.manejadores = new ArrayList<>();
        this.manejadoresCopia = new ArrayList<>();
        this.manejadorArchivo = new ManejadorDeArchivos(filePath);
        for (int i = 0 ; i < 10 ; i++){
            Formatter archivo = new Formatter("Radix/Archivo" + this.nombresArchivosTemp[i]);
            Formatter archivoCopia = new Formatter("Radix/ArchivoAuxiliar" + this.nombresArchivosTemp[i]);
            this.manejadores.add(new ManejadorDeArchivos("Radix/Archivo" + this.nombresArchivosTemp[i]));
            this.manejadoresCopia.add(new ManejadorDeArchivos("Radix/ArchivoAuxiliar" + this.nombresArchivosTemp[i]));    
        }
    }
    
    /**
     * Ordena los elementos del archivo en la dirección que establece el String filePath,
     * con el método de Radix Sort Externo.
     */
    public void ordenar(){
        ArrayList<Integer> datos;
        for (int i = 10 ; i<10000 ; i*=10){
            while((datos = this.manejadorArchivo.leerDatos(TAMANIO_BLOQUE)) != null){
                for(Integer x : datos){
                    int digito = x % i;
                    digito = 10*digito/i;
                    this.manejadores.get(digito).escribirDato(x.intValue());
                }
            }
            this.manejadorArchivo.resetLector();
            for(ManejadorDeArchivos x : this.manejadores ){
                x.resetLector();
            }
                
            this.manejadorArchivo.limpiarArchivo();
                
            for (int j = 0 ; j < 10 ;j++){
                ArrayList<Integer> buffer;
                while((buffer = this.manejadores.get(j).leerDatos(TAMANIO_BLOQUE)) != null){
                    this.manejadorArchivo.escribirDatos(buffer);
                    this.manejadoresCopia.get(j).escribirDatos(buffer);
                }
                this.manejadoresCopia.get(j).saltarLinea();
            }
            for(ManejadorDeArchivos x : this.manejadores ){
                x.limpiarArchivo();
            }
        }
        borrarArchivosTemp();    
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
     * Despliega la dirección de los archivos usados para ordenar los
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
    
