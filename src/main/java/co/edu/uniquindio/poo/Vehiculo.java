package co.edu.uniquindio.poo;

public class Vehiculo {
    private String placa;
    private String modelo;
    private Propietario propietario;
    /*
     * Constructor de la clase vehiculo
     */
    public Vehiculo( Propietario propietario, String placa, String modelo){
        this.propietario = propietario;
        this.placa = placa;
        this.modelo = modelo;
    }
    /*
     * Metodo toString para obtener toda la informacion del Vehiculo
     */
    @Override
    public String toString(){
        return propietario.toString() + "------INFORMACION DEL VEHICULO-----\n"+"Placa: "+ placa + "\n"+"Modelo: "+ modelo + "\n";
    }
    /*
     *  Metodo set para modificar la placa del vehiculo
     */
    public void setPlaca(String placa) {
        this.placa = placa;
    }
    /*
     * Metodo set para modificar el modelo del vehiculo
     */
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
    /*
     * Metodo set para modificar el propietario del vehiculo
     */
    public void setPropietario(Propietario propietario) {
        this.propietario = propietario;
    }
    /*
     * Metodo get para obtener la placa del vehiculo
     */
    public String getPlaca() {
        return placa;
    }
    /*
     * Metodo get para obtener el modelo del vehiculo
     */
    public String getModelo() {
        return modelo;
    }
    /*
     * Metodo get para obtener el propietario del vehiculo
     */
    public Propietario getPropietario() {
        return propietario;
    }
}
