package co.edu.uniquindio.poo;

public class Vehiculo {
    private String placa;
    private String modelo;
    private Propietario propietario;

    /*
     * Constructor
     */
    private Vehiculo(String placa, String modelo){
        this.placa = placa;
        this.modelo = modelo;
        assert placa != null && !placa.isBlank();
        assert modelo != null && !modelo.isBlank();
    }
    
    /*
     * Constructor
     */
    public void setPlaca(String placa) {
        this.placa = placa;
    }

    /*
     * Constructor
     */
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    /*
     * Constructor
     */
    public void setPropietario(Propietario propietario) {
        this.propietario = propietario;
    }

    /*
     * Constructor
     */
    public String getPlaca() {
        return placa;
    }
    /*
     * Constructor
     */

    public String getModelo() {
        return modelo;
    }
    /*
     * Constructor
     */

    public Propietario getPropietario() {
        return propietario;
    }
    


}
