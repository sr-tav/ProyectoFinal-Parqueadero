package co.edu.uniquindio.poo;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.LinkedList;

public class Puesto{

    private Vehiculo vehiculo;
    private Registro registro;
    private double cobro;
    private Collection<Puesto> usosAnteriores;
    /*
     * Constructor de la clase puesto
     */
    public Puesto(Vehiculo vehiculo, Registro registro, double cobro){
        this.vehiculo = vehiculo;
        this.registro = registro;
        assert cobro >=0;
        usosAnteriores = new LinkedList<>();
    }
    /*
     * Metodo para agregar un nuevo uso del puesto
     */
    public void agregarNuevoUsoPuesto(Puesto puesto){
        usosAnteriores.add(puesto);
    }
    /*
     *Metodo para agregar un nuevo vehiculo al puesto 
     */
    public void agregarVehiculo(Vehiculo vehiculo1){
        if (this.vehiculo == null) {
            // Crear un nuevo registro para el nuevo vehiculo
            Registro nuevoRegistro = new Registro(null, null, null, null);
            nuevoRegistro.setFechaEntrada(LocalDate.now());
            nuevoRegistro.setHoraEntrada(LocalDateTime.now());

            // Actualizar la informacion con el nuevo vehiculo y su registro
            this.vehiculo = vehiculo1;
            this.registro = nuevoRegistro;
            this.cobro = 0;
            agregarNuevoUsoPuesto(new Puesto(vehiculo1, nuevoRegistro, 0));
        }else if (this.vehiculo != null) {
            
        }
        
    }
    /*
     * Metodo para buscar un puesto en la informacion anterior
     */
    public Puesto buscarPuestoAnterior(Vehiculo vehiculo){
        Puesto puestoEjemplo = new Puesto(null, null, 0);
        for (Puesto puesto : usosAnteriores) {
            if (puesto != null && puesto.getVehiculo() != null && puesto.getRegistro() != null && puesto.getVehiculo() == vehiculo) {
                puestoEjemplo = puesto;
            }
        }
        return puestoEjemplo;
    }
    /*
     * Metodo para retirar un vehiculo del parqueadero
     */
    public long retirarVehiculo(LocalDate fecha){
        long horas = 0;
        if (this.vehiculo != null) {
            //Actualizar la hora y la fecha de salida del registro actual
            this.registro.setFechaSalida(fecha);
            this.registro.setHoraSalida(LocalDateTime.now());

            //Buscar el uso anterior y actualizarlo
            Puesto puestoAnterior = buscarPuestoAnterior(this.vehiculo);
            if (puestoAnterior != null) {
                puestoAnterior.getRegistro().setFechaSalida(fecha);
                puestoAnterior.getRegistro().setHoraSalida(LocalDateTime.now());
                horas = puestoAnterior.getRegistro().calcularHorasUso();
            }
        }
        //Limpiar puesto
        this.vehiculo = null;
        this.registro = null;
        this.cobro = 0;
        return horas;
    }
    /*
     * Metodo para modificar el cobro despues de retirado el vehiculo del puesto
     */
    public void modificarCobroPuestoDespues(Vehiculo vehiculo, double cobro){
        buscarPuestoAnterior(vehiculo).setCobro(cobro);   
    
    }
    /*
     * Metodo para anadir un vehiculo al puesto
     */
    public void setVehiculo(Vehiculo vehiculo){
        this.vehiculo = vehiculo;
    }
    /*
     * Metodo get para obtener el vehiculo asociado al puesto
     */
    public Vehiculo getVehiculo() {
        return vehiculo;
    }
    /*
     *  Metodo get para obtener el registro asociado al puesto
     */
    public Registro getRegistro(){
        return registro;
    }
    /*
     * Metodo get para obtener la lista de usos anteriores de este del puesto
     */
    public Collection<Puesto> getUsosAnteriores() {
        return usosAnteriores;
    }
    /*
     * Metodo get para obtener el cobro asociado al puesto
     */
    public double getCobro() {
        return cobro;
    }
    /*
     *  Metodo set para modificar el cobro asociado al puesto
     */
    public void setCobro(double cobro) {
        this.cobro = cobro;
    }
}
