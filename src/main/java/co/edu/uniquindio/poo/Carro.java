package co.edu.uniquindio.poo;

public class Carro extends Vehiculo{
    private int numPuertas;
    
    /*
     * Constructor de la clase Carro
     */
    public Carro(Propietario propietario, String placa, String modelo,int numPuertas){
        super(propietario,placa,modelo);
        this.numPuertas = numPuertas;
        assert numPuertas > 0 && numPuertas <= 5;
    }
    /*
     * Metodo toString para obtener toda la informacion del Carro
     */
    @Override
    public String toString(){
        return super.toString() + "Tipo de Vehiculo: Carro\n" + "Numero de puertas: " + numPuertas + "\n";
    }
    /*
     * Metodo get para obtener el numero de puertas del vehiculo
     */
    public int getNumPuertas() {
        return numPuertas;
    }
    /*
     *  Metodo set para modificar el numero de puertas de un vehiculo
     */
    public void setNumPuertas(int numPuertas) {
        this.numPuertas = numPuertas;
    }
    
}