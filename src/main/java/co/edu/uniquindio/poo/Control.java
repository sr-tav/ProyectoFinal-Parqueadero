package co.edu.uniquindio.poo;

import java.util.Scanner;

import javax.swing.JOptionPane;

public class Control {



    public static void crearParqueadero(){
        Scanner scanner = new  Scanner(System.in);
        String opcion;
        System.out.println("MENU PARQUEADEROS");
        System.out.println("1. crear Parqueadero");
        System.out.println("2. acceder a parqueadero");
        opcion = scanner.nextLine();

        switch (opcion) {
            case "1":
                String nombre = JOptionPane.showInputDialog("Ingresa el nombre del ");
                System.out.println(nombre);
                break;
            case "2":


                break;
            default:
                break;
        }
        
    }
}
