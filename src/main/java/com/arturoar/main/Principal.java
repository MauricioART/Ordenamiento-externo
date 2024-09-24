package com.arturoar.main;

import com.arturoar.externalsortingalgorithms.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.io.File;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/*
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.FileChooser;
import javafx.stage.Stage;*/
/**
 * Esta clase contiene la interfaz de usuario para poder ordenar los datos enteros de un 
 * archivo con tres diferentes métodos de ordenamiento externo.
 * @author ArturoAR
 */
public class Principal  {

    private static File selectedFile;
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
                        System.out.print("Abriendo FileChooser...");
                        launchFileChooser(); // Abrir FileChooser
                        // Aquí puedes hacer algo con el archivo seleccionado
                        if (selectedFile != null) {
                            System.out.println("Archivo seleccionado: " + selectedFile.getAbsolutePath());
                            // Por ejemplo: Polifase pf = new Polifase(selectedFile.getAbsolutePath(), 10);
                        } else {
                            System.out.println("No se seleccionó ningún archivo.");
                        }
                        System.out.print("┃Tamaño de bloque: ");
                        int m = sc.nextInt();
                        Polifase pf = new Polifase(selectedFile, m);
                        pf.ordenar();
                        pf.mostrarDireccionArchivos();
                        break;
                    case 2:
                        sc.nextLine();
                        System.out.print("Abriendo FileChooser...");
                        launchFileChooser(); // Abrir FileChooser
                        // Aquí puedes hacer algo con el archivo seleccionado
                        if (selectedFile != null) {
                            System.out.println("Archivo seleccionado: " + selectedFile.getAbsolutePath());
                            // Por ejemplo: Polifase pf = new Polifase(selectedFile.getAbsolutePath(), 10);
                        } else {
                            System.out.println("No se seleccionó ningún archivo.");
                        }
                        MezclaEquilibrada me = new MezclaEquilibrada(selectedFile);
                        me.ordenar();
                        me.mostrarDireccionArchivos();
                        break;
                    case 3:
                        sc.nextLine();
                        System.out.print("Abriendo FileChooser...");
                        launchFileChooser(); // Abrir FileChooser
                        // Aquí puedes hacer algo con el archivo seleccionado
                        if (selectedFile != null) {
                            System.out.println("Archivo seleccionado: " + selectedFile.getAbsolutePath());
                            // Por ejemplo: Polifase pf = new Polifase(selectedFile.getAbsolutePath(), 10);
                        } else {
                            System.out.println("No se seleccionó ningún archivo.");
                        }
                        RadixSortExterno rse = new RadixSortExterno(selectedFile);

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

    // Método para lanzar JavaFX y abrir el FileChooser
    private static void launchFileChooser() {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
        fileChooser.setFileFilter(filter);
    
        // Crear el JDialog, sin necesidad de un JFrame
        JDialog dialog = new JDialog((JDialog) null, "Seleccionar Archivo", true); // Modal y sin ventana padre
        dialog.setAlwaysOnTop(true);  // Asegurar que esté siempre al frente
        dialog.setLocationRelativeTo(null);  // Centrar en la pantalla
    
        // Añadir un listener para manejar la selección del archivo directamente en el JFileChooser
        fileChooser.addActionListener(e -> {
            if (JFileChooser.APPROVE_SELECTION.equals(e.getActionCommand())) {
                // Obtener el archivo seleccionado
                selectedFile = fileChooser.getSelectedFile();
                System.out.println("Selected file: " + selectedFile.getAbsolutePath());
                dialog.dispose(); // Cerrar el diálogo cuando se selecciona el archivo
            } else if (JFileChooser.CANCEL_SELECTION.equals(e.getActionCommand())) {
                System.out.println("Selección cancelada");
                dialog.dispose(); // Cerrar el diálogo si se cancela la operación
            }
        });
    
        // Añadir el JFileChooser al JDialog
        dialog.add(fileChooser);
        dialog.pack();
        dialog.setVisible(true);  // Mostrar el diálogo
    }

    /*Platform.startup(() -> {
            try {
                Stage stage = new Stage();
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Selecciona el archivo a ordenar");
                selectedFile = fileChooser.showOpenDialog(stage);
            } catch (Exception e){
                System.out.println(e.getMessage());
            }
            finally {
                Platform.exit(); // Cerrar la plataforma de JavaFX una vez que se ha seleccionado el archivo
            }
        });*/

/*
    @Override
    public void start(Stage primaryStage) {
        // No se utiliza el primaryStage en este caso.
    }*/
    
}
   
    