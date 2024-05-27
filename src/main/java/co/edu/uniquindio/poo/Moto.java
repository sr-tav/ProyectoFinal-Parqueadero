package co.edu.uniquindio.poo;

public class Moto extends Vehiculo {
    private double velocidadMaxima;
    private TipoMoto tipo;

    /*
     * Constructor de la clase moto
     */
    public Moto(Propietario propietario, String placa, String modelo, double velocidadMaxima, TipoMoto tipo){
        super(propietario,placa,modelo);
        this.velocidadMaxima = velocidadMaxima;
        this.tipo = tipo;
        assert velocidadMaxima>0;
    }
    /*
     * Metodo toString para obtener toda la informacion de la moto
     */
    @Override
    public String toString(){
        return super.toString() + "Tipo de Vehiculo: Moto\n" + "Tipo de moto: " + tipo + "\n" + "Velocidad Maxima : "+ velocidadMaxima + "\n";
    }
    /*
     * Metodo get para obtener la velocidad maxima de la moto
     */
    public double getVelocidadMaxima() {
        return velocidadMaxima;
    }
    /*
     * Metodo get para obtener el tipo de moto
     */
    public TipoMoto getTipo() {
        return tipo;
    }
    /*
     * Metodo set para modificar la velocidad maxima de la moto
     */
    public void setVelocidadMaxima(double velocidadMaxima) {
        this.velocidadMaxima = velocidadMaxima;
    }
    /*
     * metodo set para modificar el tipo de moto
     */
    public void setTipo(TipoMoto tipo) {
        this.tipo = tipo;
    }
    
    
}
