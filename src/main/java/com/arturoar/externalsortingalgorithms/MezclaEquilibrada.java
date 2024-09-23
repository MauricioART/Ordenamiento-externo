
package com.arturoar.externalsortingalgorithms;

import com.arturoar.tools.ManejadorDeArchivos;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.io.File;

/**
 * Esta clase está destinada al ordenamiento de los elementos, enteros, de 
 * un archivo de manera ascendente con el método homonimo de la clase. 
 * @author ArturoAR
 */
public class MezclaEquilibrada {
    
    
    private final int TAMANIO_BLOQUE = 5;
    private final String[] nombresArchivosTemp = {"Uno.txt","Dos.txt"};
    private ArrayList<ManejadorDeArchivos> manejadores;
    private ManejadorDeArchivos manejadorArchivo; 
    private ArrayList<ManejadorDeArchivos> manejadoresCopia;
    
    /**
     * Construye una instancia de la clase MezclaEquilibrada, inicializando
     * el atributo manejadorArchivo con filePath, creando dos archivos auxiliares
     * e inicializando manejadores con las direcciones de éstos.
     * @param file String de la dirección absoluta del archivo a ordenar
     * @throws FileNotFoundException 
     */
    public MezclaEquilibrada(File file) throws FileNotFoundException, IOException {
        this.manejadores = new ArrayList<>();
        this.manejadoresCopia = new ArrayList<>();
        this.manejadorArchivo = new ManejadorDeArchivos(file);
        for (int i = 0 ; i < 2 ; i++){
            this.manejadores.add(new ManejadorDeArchivos("MezclaEquilibrada/Archivo" + this.nombresArchivosTemp[i]));
            this.manejadoresCopia.add(new ManejadorDeArchivos("MezclaEquilibrada/ArchivoAuxiliar" + this.nombresArchivosTemp[i]));        
        }
        for (ManejadorDeArchivos manejador : this.manejadoresCopia){
            manejador.limpiarArchivo();
        }
    }
    /**
     * Ordena los elementos del archivo en la dirección que establece el
     * String filePath, con el método de Mezcla equilibrada.
     */
    public void ordenar() {
        crearBloquesOrdenados();

        int par = 0, par2 = 0, numeroBloques = 0;

        while (numeroBloques != 1) {
            numeroBloques = 0;
            this.manejadorArchivo.limpiarArchivo();
            ArrayDeque<Integer> datosA = this.manejadores.get(0).leerBloques(), datosB = this.manejadores.get(1).leerBloques();

            while (!datosA.isEmpty() || !datosB.isEmpty()) {
                    numeroBloques++;
                    while (!datosA.isEmpty() || !datosB.isEmpty()) {
                        if (!datosA.isEmpty() && !datosB.isEmpty())
                            if (datosA.peek() < datosB.peek())
                                this.manejadorArchivo.escribirDato(datosA.poll());
                            else
                                this.manejadorArchivo.escribirDato(datosB.poll());
                        else if (datosA.isEmpty())
                            this.manejadorArchivo.escribirDato(datosB.poll());
                        else
                            this.manejadorArchivo.escribirDato(datosA.poll());
                    }
                    datosA = this.manejadores.get(0).leerBloques();
                    datosB = this.manejadores.get(1).leerBloques();
                    if (!(datosA.isEmpty() && datosB.isEmpty()))
                        this.manejadorArchivo.escribirSeparadorBloque();
                }

                resetearLectores();
                //TODO: Escribir en los archivos auxiliares antes de limpiarlos y tambien el del manejador
                limpiarArchivosTemp();
                ArrayList<Integer> bloque;
                par = 0;
                par2 = 0;
                while ((bloque = this.manejadorArchivo.leerBloque()) != null) {
                    this.manejadores.get((par++) % 2).escribirBloques(bloque);
                    this.manejadoresCopia.get((par2++) % 2).escribirBloques(bloque);
                }
        }
            borrarArchivosTemp();
        }

    
    /**
     * Lee los datos del archivo a ordenar creando los bloques ordenados y 
     * guardandolos en los archivos auxiliares.
     */
    public void crearBloquesOrdenados(){
        int dato = 0, par = 0;
        boolean esMayor;
        ArrayDeque<Integer> pila = new ArrayDeque<>();
        ArrayList<Integer> bloque = new ArrayList<>();
        do{
            esMayor = true;
            int i = 1;
            while(esMayor){
                dato = this.manejadorArchivo.leerDato();
                if(pila.isEmpty())
                    pila.add(dato);
                else
                    if(dato > pila.getLast())
                        pila.add(dato);
                    else
                        esMayor = false;
            }
            while(!pila.isEmpty()){
                bloque.add(pila.poll());
            }
            this.manejadores.get((par++)%2).escribirBloques(bloque);
            bloque.clear();
            pila.add(dato);
            
        }while(dato != -1);
    }
    /** 7,20,55,86,4,32,100,23,80,56,28,2,1,99,64,52,52,13,72,30
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
            x.limpiarArchivo();
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