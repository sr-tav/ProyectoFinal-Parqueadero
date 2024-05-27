package co.edu.uniquindio.poo;

public class Propietario{
    
    private String nombre; 
    private String id;
    private String email;
    private String telefono;

    private double cobro;

    public Propietario(String nombre, String id, String email, String telefono){
        this.nombre = nombre;
        this.id = id;
        this.email = email;
        this.telefono = telefono;
        assert nombre != null && !nombre.isBlank();
        assert id != null && !id.isBlank();
        assert email != null && !email.isBlank();
        assert telefono != null && !telefono.isBlank();

    }
    /*
     * Metodo toString para mostrar toda la informacion en orden del propietario
     */
    public String toString(){
        return "----INFORMACION DEL PROPIETARIO----\n"+"Nombre: "+ nombre + "\n"+"Identificacion: "+ id + "\n"+"Email: "+ email + "\n"+"Telefono: "+ telefono + "\n"+ "-----------------------------------\n";
    }
    /*
     *   Verifica si el propietario es vacío
     */ 
    public boolean isEmpty() {
        return nombre == null || nombre.isEmpty() || id == null || id.isEmpty() || email == null || email.isEmpty() || telefono == null || telefono.isEmpty();
    }
    /*
     * Metodo para sumar los cobros al propietario
     */
    public void agregarCobro(double cobroNuevo){
        double cobroTotal = cobro + cobroNuevo;
        setCobro(cobroTotal);
    }
    /*
     *  Metodo get para obtener el nombre del propietario
     */
    public String getNombre() {
        return nombre;
    }
    /*
     *  Metodo get para obtener la identificacion del propietario
     */
    public String getId() {
        return id;
    }
    /*
     *  Metodo get para obtener el correo del propietario
     */
    public String getEmail() {
        return email;
    }
    /*
     *  Metodo get para obtener el telefono del propietario
     */
    public String getTelefono() {
        return telefono;
    }
    /*
     *  Metodo get para obtener el cobro que lleva el propietario
     */
    public double getCobro() {
        return cobro;
    }
    /*
     *  Metodo set para modificar el cobro del propietario
     */
    public void setCobro(double cobro) {
        this.cobro = cobro;
    }
    
}
