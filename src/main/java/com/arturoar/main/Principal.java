package com.arturoar.main;

import com.arturoar.externalsortingalgorithms.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.io.File;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;


/**
 * This class provides the user interface to sort integer data from a file using 
 * three different external sorting algorithms.
 * @author ArturoAR
 */

public class Principal  {

    private static File selectedFile;

    /**
     * Main method to handle user input and sorting operations.
     * @param args command-line arguments
     */
    public static void main(String[] args) {

        try (Scanner scanner = new Scanner(System.in)) {
            boolean continueLoop = true;
            do {
                try {
                    System.out.println(" ◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆");
                    System.out.println(" ◆◆◆◆◆◆◆◆" + " External Sorting Algorithms  " + "◆◆◆◆◆◆◆◆");
                    System.out.println("┃◆1.-Polyphase Merge Sort                 ┃");
                    System.out.println("┃◆2.-Balance Merge Sort                   ┃");
                    System.out.println("┃◆3.-External Radix Sort                  ┃");
                    System.out.println("┃◆4.-Exit program                         ┃");
                    System.out.print("┃◆Option: ");

                    switch (scanner.nextInt()) {

                        case 1:
                            scanner.nextLine();
                            System.out.print("Opening FileChooser...");
                            launchFileChooser(); // Open FileChooser
                            // Here you can do something with the selected file
                            if (selectedFile != null) {
                                System.out.println("Selected file: " + selectedFile.getAbsolutePath());
                                // Example: Polyphase pf = new Polyphase(selectedFile.getAbsolutePath(), 10);
                            } else {
                                System.out.println("No file was selected.");
                            }
                            System.out.print("┃Polyphase block size: ");
                            int m = scanner.nextInt();
                            PolyphaseMergeSort pf = new PolyphaseMergeSort(selectedFile, m);
                            pf.sort();
                            pf.showFilesPath();
                            break;
                        case 2:
                            scanner.nextLine();
                            System.out.print("Opening FileChooser...");
                            launchFileChooser(); // Open FileChooser
                            // Here you can do something with the selected file
                            if (selectedFile != null) {
                                System.out.println("Selected file: " + selectedFile.getAbsolutePath());
                                // Example: BalanceMergeSort me = new BalanceMergeSort(selectedFile);
                            } else {
                                System.out.println("No file was selected.");
                            }
                            BalanceMergeSort me = new BalanceMergeSort(selectedFile);
                            me.sort();
                            me.showFilePaths();
                            break;
                        case 3:
                            scanner.nextLine();
                            System.out.print("Opening FileChooser...");
                            launchFileChooser(); // Open FileChooser
                            // Here you can do something with the selected file
                            if (selectedFile != null) {
                                System.out.println("Selected file: " + selectedFile.getAbsolutePath());
                            } else {
                                System.out.println("No file was selected.");
                            }
                            ExternalRadixSort rse = new ExternalRadixSort(selectedFile);
                            System.out.print("┃Max number of digits: ");
                            rse.sort(scanner.nextInt());
                            rse.showFilesPath();
                            break;
                        case 4:
                            continueLoop = false;
                            break;
                        default:
                            System.out.println("\t◆◆◆◆INVALID OPTION◆◆◆◆");
                            break;
                    }
                } catch (FileNotFoundException ex) {
                    System.out.println(">>>>>File not found");
                } catch (IOException e) {
                    System.out.println(">>>>>File error");
                }
            } while (continueLoop);
        }
    }

    // Method to launch JavaFX and open the FileChooser
    private static void launchFileChooser() {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
        fileChooser.setFileFilter(filter);

        // Create JDialog without a JFrame
        JDialog dialog = new JDialog((JDialog) null, "Select File", true); // Modal and no parent window
        dialog.setAlwaysOnTop(true);  // Ensure it's always on top
        dialog.setLocationRelativeTo(null);  // Center on the screen

        // Add listener to handle file selection directly in JFileChooser
        fileChooser.addActionListener(e -> {
            if (JFileChooser.APPROVE_SELECTION.equals(e.getActionCommand())) {
                // Get the selected file
                selectedFile = fileChooser.getSelectedFile();
                dialog.dispose(); // Close the dialog when the file is selected
            } else if (JFileChooser.CANCEL_SELECTION.equals(e.getActionCommand())) {
                System.out.println("Selection cancelled");
                dialog.dispose(); // Close the dialog if the operation is canceled
            }
        });

        // Add JFileChooser to JDialog
        dialog.add(fileChooser);
        dialog.pack();
        dialog.setVisible(true);  // Show the dialog
    }
    
}
   
    