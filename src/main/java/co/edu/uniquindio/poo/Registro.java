package co.edu.uniquindio.poo;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Registro {
    private LocalDate fechaEntrada;
    private LocalDate fechaSalida;
    private LocalDateTime horaEntrada;
    private LocalDateTime horaSalida;
    
    /*
     * Constructor de la clase Registro
     */
    public Registro(LocalDate fechaEntrada, LocalDateTime horaEntrada, LocalDateTime horaSalida, LocalDate fechaSalida){
        this.fechaSalida = fechaSalida;
        this.fechaEntrada = fechaEntrada;
        this.horaEntrada = horaEntrada;
        this.horaSalida = horaSalida;
    }
    /*
     * Metodo para calcular las horas de uso de vehiculo
     */
    public long calcularHorasUso(){
        long horas = 0;
        horas = java.time.Duration.between(horaEntrada,horaSalida).toSeconds();
        if (horaEntrada.getMinute() - horaSalida.getMinute() > 0) {
            horas++;
        }
        return horas;
    }
    /*
     * Metodo get para obtener la hora de entrada del registro
     */
    public LocalDateTime getHoraEntrada() {
        return horaEntrada;
    }
    /*
     * Metodo get para obtener la hora de salida del registro
     */
    public LocalDateTime getHoraSalida() {
        return horaSalida;
    }
    /*
     * Metodo get para obtener la fecha de entrada del registro
     */
    public LocalDate getFechaEntrada() {
        return fechaEntrada;
    }
    /*
     * Metodo set para modificar la hora de entrada del registro
     */
    public void setHoraEntrada(LocalDateTime horaEntrada) {
        this.horaEntrada = horaEntrada;
    }
    /*
     * Metodo set para modificar la hora de salida del registro
     */
    public void setHoraSalida(LocalDateTime horaSalida) {
        this.horaSalida = horaSalida;
    }
    /*
     * Metodo set para modificar la fecha de entrada del registro
     */
    public void setFechaEntrada(LocalDate fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }
    /*
     * Metodo get para obtener la fecha de salida del registro
     */
    public LocalDate getFechaSalida() {
        return fechaSalida;
    }
    /*
     * Metodo set para modificar la fecha de salida del registro
     */
    public void setFechaSalida(LocalDate fechaSalida) {
        this.fechaSalida = fechaSalida;
    }
    
}
