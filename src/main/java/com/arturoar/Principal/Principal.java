
package com.arturoar.Principal;
import com.arturoar.OrdenamientosExternos.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
/**
 * Esta clase contiene la interfaz de usuario para poder ordenar los datos enteros de un 
 * archivo con tres diferentes métodos de ordenamiento externo.
 * @author ArturoAR
 */
public class Principal {
    
   /**
    * 
    * @param args
    */
   public static void main(String[] args){
		
	Scanner sc = new Scanner(System.in);

	boolean continuar = true;
	do{
            try {
                System.out.println(" ◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆");
                System.out.println(" ◆◆◆◆◆◆◆◆" + "  Ordenamientos Externos  " + "◆◆◆◆◆◆◆◆");
                System.out.println("┃◆1.-Ordenamiento Polifase                ┃");
                System.out.println("┃◆2.-Mezcla Equilibrada                   ┃");
                System.out.println("┃◆3.-Radix Externo                        ┃");
                System.out.println("┃◆4.-Salir                                ┃");
                System.out.print("┃◆Opción: ");
                
                switch(sc.nextInt()){

                    case 1:
                        sc.nextLine();
                        System.out.print("┃Dirección del archivo a ordenar: ");
                        String path = sc.nextLine();
                        System.out.print("┃Tamaño de bloque: ");
                        int m = sc.nextInt();
                        Polifase pf = new Polifase(path, m);
                        pf.ordenar();
                        pf.mostrarDireccionArchivos();
                        break;
                    case 2:
                        sc.nextLine();
                        System.out.print("┃Dirección del archivo a ordenar: ");
                        String filePath = sc.nextLine();
                        System.out.println(filePath);
                        MezclaEquilibrada me = new MezclaEquilibrada(filePath);
                        me.ordenar();
                        me.mostrarDireccionArchivos();
                        break;
                    case 3:
                        sc.nextLine();
                        System.out.print("┃Dirección del archivo a ordenar: ");
                        RadixSortExterno rse = new RadixSortExterno(sc.nextLine());
                        System.out.print("┃Máximo número de digitos: ");
                        rse.ordenar(sc.nextInt());
                        rse.mostrarDireccionArchivos();
                        break;
                    case 4:
                        continuar = false;
                        break;
                    default:
                        System.out.println("\t◆◆◆◆OPCIÓN INVALIDA◆◆◆◆");
                        break;
                }
            } catch (FileNotFoundException ex) {
                System.out.println(">>>>>Archivo no encontrado");
                
            } catch (IOException e) {
                System.out.println(">>d>>>Archivo no encontrado");
            }

    }while(continuar);
    }
    
}
   
    