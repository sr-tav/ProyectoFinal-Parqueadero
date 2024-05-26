package co.edu.uniquindio.poo;

import java.util.Collection;

public class Parqueadero {

    private String nombre;
    private int columnas;
    private int filas;
    private Vehiculo[][] puestos;
    private int[][] numPuestos;
    //mensaje para david
    /*
     * Constructor de la clase Parqueadero
     */
    public Parqueadero(String nombre, int columnas, int filas){
        this.columnas = columnas;
        this.filas = filas;
        this.nombre = nombre;
        assert columnas >0;
        assert filas >0;
        assert nombre != null && !nombre.isBlank();
    }
    /*
     * Metodo para verificar si un vehiculo se puede agregar en un determinado puesto
     * return disponible
     */
    public boolean verificarDisponibilidad(int numero){
        boolean disponible = false;
        for(int i = 0; i<puestos.length; i++){
            for(int j =0; j<puestos.length;j++){
                if (numPuestos[i][j] == numero && puestos[i][j] == null) {
                    disponible = true;
                }
            }
        }
        return disponible;
    }
    /*
     * Metodo para mostrar la matriz del parqueadero
     */
    public void mostrarParqueadero(){

        for(int i = 0; i<puestos.length;i++){
            for(int j=0; j<puestos.length;j++){
                System.out.print(puestos[i][j] +" = "+ numPuestos[i][j]+ "\t");
            }
            System.out.println();
        }
    }
    /*
     * Metodo para generar el numero de los puestos
     */
    public void generarNumPuestos(){
        int[][] numPuestos = new int[filas][columnas];
        int sum = 0;

        for(int i = 0; i<numPuestos.length;i++){
            for(int j=0; j<numPuestos.length;j++){
                if (numPuestos[i][j] == 0) {
                    numPuestos[i][j] = sum+1;
                    sum = numPuestos[i][j];
                }
            }
        }
        this.numPuestos = numPuestos;
    }
    /*
     * metodo para generar la matriz con las filas y columnas ingresadas a la hora de crear el parqueadero
     */
    public void generarPuestos(){
        Vehiculo[][] puestos = new Vehiculo[filas][columnas];
        this.puestos = puestos;
    }
    /*
     * Metodo para anadir un vehiculo y que el puesto no este ocupado
     */
    public void anadirVehiculo(Vehiculo vehiculo, int numeroPuesto){
        assert verificarDisponibilidad(numeroPuesto) == true;
        for(int i = 0; i<puestos.length; i++){
            for(int j =0; j<puestos.length;j++){
                if (numPuestos[i][j] == numeroPuesto) {
                    puestos[i][j] = vehiculo;
                }
            }
        }
    }
    /*
     * metodo para obtener el nombre del parqueadero
     * return nombre
     */
    public String getNombre() {
        return nombre;
    }
    /*
     * metodo para obtener el numero de columnas del parqueadero
     * return columnas
     */
    public int getColumnas() {
        return columnas;
    }
    /*
     * metodo para obtener el numero de filas del parqueadero
     * retorna filas
     */
    public int getFilas() {
        return filas;
    }
    /*
     * metodo para modificar el nombre del parqueadero
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    /*
     * metodo para modificar el numero de columnas del parqueadero
     */
    public void setColumnas(int columnas) {
        this.columnas = columnas;
    }
    /*
     * metodo para modificar el numero de filas del parqueadero
     */
    public void setFilas(int filas) {
        this.filas = filas;
    }
    
}
