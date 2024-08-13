
package Principal;
import OrdenamientosExternos.*;
import java.io.FileNotFoundException;
import java.util.Scanner;
/**
 * Esta clase contiene la interfaz de usuario para poder ordenar los datos enteros de un 
 * archivo con tres diferentes métodos de ordenamiento externo.
 * @author Equipo 3
 */
public class Principal {
    
   /**
    * 
    * @param args 
    */
   public static void main(String[] args){
		
	Scanner sc = new Scanner(System.in);
	Scanner sc2 = new Scanner(System.in);
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
                        System.out.print("┃Dirección del archivo a ordenar: ");
                        Polifase pf = new Polifase(sc2.nextLine());
                        pf.ordenar();
                        pf.mostrarDireccionArchivos();
                        break;
                    case 2:
                        System.out.print("┃Dirección del archivo a ordenar: ");
                        MezclaEquilibrada me = new MezclaEquilibrada(sc2.nextLine());
                        me.ordenar();
                        me.mostrarDireccionArchivos();
                        break;
                    case 3:
                        System.out.print("┃Dirección del archivo a ordenar: ");
                        RadixSortExterno rse = new RadixSortExterno(sc2.nextLine());
                        rse.ordenar();
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
                
            }
			
	}while(continuar);
    }  
    
}
   
    