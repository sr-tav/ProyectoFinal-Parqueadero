package co.edu.uniquindio.poo;
import java.time.LocalDate;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class Control{
    public static Scanner scan = new  Scanner(System.in);
    private static LocalDate ahora;

    public static void crearParqueadero(){
        String[] opciones = {"Crear Parqueadero"};
        System.out.println("\u000C");
        int opcion = JOptionPane.showOptionDialog(null, "BIENVENIDO", "Menu", 0, 1, null, opciones, opciones[0]);
        switch (opcion) {
            case 0:
                Parqueadero parqueadero = Parqueadero.crearParqueadero();
                prepararParqueadero(parqueadero);
                JOptionPane.showMessageDialog(null, "Parqueadero se creo con exito");
                parqueadero.menuParqueadero();
                break;
            case -1:
                JOptionPane.showMessageDialog(null, "Gracias por utilizar nuestros servicios");
                break;
            default:
                JOptionPane.showMessageDialog(null, "Inserte una opcion valida");
                break;
        }
    }
    /*
     * Metodo para establecer las tarifas del parqueadero
     */
    public static void establecerTarifas(Parqueadero parqueadero){
        double tarifaCarro = Parqueadero.verificarEntradaNumericaEstatico(("Ingrese la tarifa por hora para los carros"));
        double tarifaMotoClasica = Parqueadero.verificarEntradaNumericaEstatico("Ingrese la tarifa por hora para las motos clasicas");
        double tarifaMotoHibrida = Parqueadero.verificarEntradaNumericaEstatico("Ingrese la tarifa para las motos hibridas");
        parqueadero.setTarifaCarro(tarifaCarro);
        parqueadero.setTarifaMotoClasica(tarifaMotoClasica);
        parqueadero.setTarifaMotoHibrida(tarifaMotoHibrida);
        
    }
    /*
     * Metodo para preparar el parqueadero
     */
    public static void prepararParqueadero(Parqueadero parqueadero){
        ahora = LocalDate.now();
        establecerTarifas(parqueadero);
        parqueadero.generarArregloPropietarios();
        parqueadero.generarNumPuestos();
        parqueadero.generarPuestos();
        parqueadero.actualizarFecha(ahora);
    }
}