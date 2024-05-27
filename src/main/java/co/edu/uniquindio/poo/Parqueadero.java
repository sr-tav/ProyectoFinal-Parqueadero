package co.edu.uniquindio.poo;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JOptionPane;

public class Parqueadero {
    private String nombre;
    private int columnas;
    private int filas;

    private Puesto[][] puestos;
    private int[][] numPuestos;

    private double tarifaCarro;
    private double tarifaMotoClasica;
    private double tarifaMotoHibrida;
    private Collection<Vehiculo> vehiculosParqueados;
    private Collection<String> reportesDiarios;
    private LocalDate fecha;
    private Timer tiempo;
    private Collection<String> informacionRecaudosDiarios;
    private LinkedList<String> reportesMensuales;
    private Collection<Double> reportesRecaudosDiarios;
    private Propietario[] propietarios;
    
    /*
     * Constructor de la clase Parqueadero
     */
    public Parqueadero(String nombre, int columnas, int filas){
        vehiculosParqueados = new LinkedList<>();
        reportesDiarios = new LinkedList<>();
        informacionRecaudosDiarios = new LinkedList<>();
        reportesRecaudosDiarios = new LinkedList<>();
        reportesMensuales = new LinkedList<>();
        this.columnas = columnas;
        this.filas = filas;
        this.nombre = nombre;
        assert columnas >0;
        assert filas >0;
        assert nombre != null && !nombre.isBlank();
    }
    /*
     * Metodo para actualizar la fecha del parqueadero
     */
    public void actualizarFecha(LocalDate fecha){
        this.fecha = fecha;

        tiempo = new Timer();
        TimerTask actualizarDia = new TimerTask() {

            @Override
            public void run() {
                final LocalDate fechaActualizada = Parqueadero.this.fecha;
                generarReporteDiario(fechaActualizada);
                generarRecaudoDiario(fechaActualizada);
                generarReporteMensual(fechaActualizada);
                Parqueadero.this.fecha = fechaActualizada.plusDays(1);
                System.out.println("Dia actualizado :" + Parqueadero.this.fecha);
                
            }
            
        };

        tiempo.scheduleAtFixedRate(actualizarDia, 30000,30000);
    }
    /*
     * Metodo para verificar si una posicion existe en el parqueadero
     */
    public boolean verificarPosicionExiste(int posicion){
        boolean existe = false;

        for(int i = 0; i<numPuestos.length; i++){
            for(int j = 0; j<numPuestos[i].length; j++){
                if (numPuestos[i][j] == posicion) {
                    existe = true;
                }
            }
        }
        return existe;
    }
    
    /*
     * Metodo para mostrar toda la informacion de un vehiculo dado un puesto
     */
    public void mostrarVehiculo(int numeroPuesto){
        Vehiculo vehiculo = ubicarVehiculo(numeroPuesto);
        if (vehiculo == null) {
            JOptionPane.showMessageDialog(null, "Aqui no hay ningun vehiculo", "Error", JOptionPane.ERROR_MESSAGE);
        }else{
            Registro registro = ubicarRegistro(numeroPuesto);
            JOptionPane.showMessageDialog(null, vehiculo.toString() + "Fecha de entrada: " + registro.getFechaEntrada() + "\n" + "Hora de Entrada: " + registro.getHoraEntrada().getHour()+ ":" +registro.getHoraEntrada().getMinute()+ ":" +registro.getHoraEntrada().getSecond() + "\n" + "-----------------------------------\n");
        }   
    }
    /*
     * Metodo para verificar si un vehiculo se puede agregar en un determinado puesto
     * return disponible
     */
    public boolean verificarDisponibilidad(int numero){
        boolean disponible = false;
        for(int i = 0; i<puestos.length; i++){
            for(int j =0; j<puestos[i].length;j++){
                if (numPuestos[i][j] == numero && puestos[i][j].getVehiculo() == null) {
                    disponible = true;
                }
            }
        }
        return disponible;
    }
    /*
     * Metodo para mostrar la matriz y seleccionar un vehiculo de ella
     */
    public int mostrarParqueaderoYSeleccionar(String accion){
        String resultado = "";
        int seleccion = 0;

        for(int i = 0; i<puestos.length;i++){
            for(int j=0; j<puestos[i].length;j++){
                if (puestos[i][j].getVehiculo() != null) {
                    resultado += numPuestos[i][j] + " = "+ puestos[i][j].getVehiculo().getPlaca()+ "&nbsp;&nbsp;" + "  ";
                }else{
                    resultado += numPuestos[i][j]  + " = " + "<font color = 'red'> Vacio </font>" + "&nbsp;&nbsp;" + "  ";
                }
            }
            resultado += "<br>";  
        }
        

        seleccion = (int) (verificarEntradaNumerica("<html>" + "Seleccione el numero del vehiculo que desea "+ accion + "<br>" + resultado + "<br>" + "0 =  Salir" + "</html>"));
        return seleccion;
    }
    /*
     * Metodo para mostrar la matriz pero su disponibilidad
     */
    public String mostrarParqueaderoDisponibilidad(){
        String resultado = "<html>";

            for(int i = 0; i<puestos.length;i++){
                for(int j=0; j<puestos[i].length;j++){
                    if (puestos[i][j].getVehiculo() != null) {
                        resultado += numPuestos[i][j]  + " = " + "<font color = 'red'> Ocupado </font>" + "&nbsp;&nbsp;" + "  ";
                    }else{
                        resultado += numPuestos[i][j]  + " = " + "<font color = 'green'> Disponible </font>" + "&nbsp;&nbsp;" + "  ";
                    }
                }
                resultado += "<br>";
            }

            resultado +="</html>";

        return resultado;
    }
    /*
     * Metodo para mostrar la matriz del parqueadero
     */
    public String mostrarParqueadero(){
        String resultado = "";

            for(int i = 0; i<puestos.length;i++){
                for(int j=0; j<puestos[i].length;j++){
                    if (puestos[i][j].getVehiculo() != null) {
                        resultado += numPuestos[i][j] + " = "+ puestos[i][j].getVehiculo().getPlaca()+ "&nbsp;&nbsp;" + "  ";
                    }else{
                        resultado += numPuestos[i][j]  + " = "+"<font color = 'red'> Vacio </font>" + "&nbsp;&nbsp;" + "  ";
                    }
                }
                resultado += "<br>";
            }

        return resultado;
    }
    /*
     * Metodo para generar el arreglo de propietarios
     */
    public void generarArregloPropietarios(){
        Propietario[] propietarios = new Propietario[filas*columnas];
        this.propietarios = propietarios;
    }
    /*
     * Metodo para generar el numero de los puestos
     */
    public void generarNumPuestos(){
        int sum = 0;
        int[][] numPuestos = new int[filas][columnas];

        for(int i = 0; i<filas;i++){
            for(int j=0; j<columnas;j++){
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
        Puesto[][] puestos = new Puesto[filas][columnas];

        for(int i = 0; i<filas; i++){
            for(int j = 0; j<columnas;j++){
                puestos[i][j] = new Puesto(null, null,0);
            }
        }
        this.puestos = puestos;
    }
    /*
     * Metodo para reitrar un vehiculo del parqueadero
     */
    public void retirarVehiculo(int numeroPuesto){
        long horas = 0;
        for(int i = 0; i<puestos.length; i++){
            for(int j =0; j<puestos[i].length;j++){
                if (numPuestos[i][j] == numeroPuesto) {
                    Vehiculo vehiculo = puestos[i][j].getVehiculo();
                    horas = puestos[i][j].retirarVehiculo(fecha);
                    double cobro = calcularCobro(horas, vehiculo);
                    puestos[i][j].modificarCobroPuestoDespues(vehiculo,cobro);
                    vehiculo.getPropietario().agregarCobro(cobro);
                }
            }
        }
    }
    /*
     * Metodo para calcular el cobro un vehiculo
     */
    public double calcularCobro(long horas, Vehiculo vehiculo){
        double cobro = 0;
        if (vehiculo instanceof Carro ) {
            cobro = horas * tarifaCarro;
        }else if(vehiculo instanceof Moto){
            Moto moto = (Moto) vehiculo;
            if (moto.getTipo() == TipoMoto.CLASICA) {
                cobro = horas*tarifaMotoClasica;
            }else{
                cobro = horas*tarifaMotoHibrida;
            }
        }
        return cobro;
    }
    /*
     * Metodo para anadir un vehiculo y que el puesto no este ocupado
     */
    public void anadirVehiculo(Vehiculo vehiculo, int numeroPuesto){
        if(verificarDisponibilidad(numeroPuesto) == true){
            
            for(int i = 0; i<puestos.length; i++){
                for(int j =0; j<puestos[i].length;j++){
                    if (numPuestos[i][j] == numeroPuesto) {
                        puestos[i][j].agregarVehiculo(vehiculo);
                        vehiculosParqueados.add(vehiculo);
                        agregarPropietario(vehiculo.getPropietario());
                    }
                }
            }
        }
    }
    /*
     * Metodo para buscar un registro con una posicion dada
     */
    public Registro ubicarRegistro(int numeroPuesto){
        Registro registro = new Registro(null, null, null,null);
        
        for(int i = 0; i<puestos.length; i++){
            for(int j =0; j<puestos[i].length;j++){

                if (numPuestos[i][j] == numeroPuesto) {
                    registro = puestos[i][j].getRegistro();
                }  
            }
        }

        return registro;
    }
    /*
     * Metodo para buscar un vehiculo con una posicion dada
     */
    public Vehiculo ubicarVehiculo(int num){
        Vehiculo vehiculo = new Vehiculo(null,null, null);

        for(int i = 0; i<puestos.length; i++){
            for(int j =0; j<puestos[i].length;j++){

                if (numPuestos[i][j] == num) {
                    vehiculo = puestos[i][j].getVehiculo();
                }  
            }
        }
        return vehiculo;
    }
    /*
     * Metodo para generar el reporte de todos los parqueos
     */
    public void generarReporteTotal(){
        String info = "";
        for(int i =0; i<puestos.length;i++){
            for(int j = 0; j<puestos[0].length;j++){
                for (Vehiculo vehiculo: vehiculosParqueados) {
                    Puesto puesto = puestos[i][j].buscarPuestoAnterior(vehiculo);
                    if (puesto != null && puesto.getRegistro() != null) {
                        if (puesto.getRegistro().getHoraSalida() == null) {
                            info += vehiculo.toString() + "Fecha de entrada: " + puesto.getRegistro().getFechaEntrada() + "\n" + "Hora de Entrada: " + puesto.getRegistro().getHoraEntrada().getHour()+":"+puesto.getRegistro().getHoraEntrada().getMinute()+":"+puesto.getRegistro().getHoraEntrada().getSecond()+ "\n" + "Hora de Salida: Aun no ha salido del parqueadero" + "\n"  + "-----------------------------------------------------------\n";
                        }else {
                            info += vehiculo.toString() + "Fecha de entrada: " + puesto.getRegistro().getFechaEntrada() + "\n" + "Hora de Entrada: " + puesto.getRegistro().getHoraEntrada().getHour()+":"+puesto.getRegistro().getHoraEntrada().getMinute()+":"+puesto.getRegistro().getHoraEntrada().getSecond()+ "\n" + "Fecha de Salida: " + puesto.getRegistro().getFechaSalida() + "\n" + "Hora de Salida: " + puesto.getRegistro().getHoraSalida().getHour() + ":" + puesto.getRegistro().getHoraSalida().getMinute()+":"+puesto.getRegistro().getHoraSalida().getSecond()+"\n" + "Cobro: " +puesto.getCobro() + "\n" + "-----------------------------------------------------------\n";
                        }
                    }
                    
                    
                } 
            }
        }
        System.out.println(info);
    }
    /*
     * Metodo para generar el reporte mensual
     */
    public void generarRecaudoDiario(LocalDate fecha){
        double gananciaTotalDia =0;
        String info ="------ RECAUDAO DEL DIA = "+ fecha + " -------\n";
        if (informacionRecaudosDiarios.size() == 30) {
            informacionRecaudosDiarios.clear();
        }
        for(int i =0; i<filas;i++){
            for(int j = 0; j<columnas;j++){
                for (Vehiculo vehiculo : vehiculosParqueados) {
                    Puesto puesto = puestos[i][j].buscarPuestoAnterior(vehiculo);

                    if (puesto != null && puesto.getVehiculo() != null && puesto.getRegistro() != null) {

                        if (puesto.getRegistro().getHoraSalida() != null && puesto.getRegistro().getFechaSalida().getDayOfWeek() == fecha.getDayOfWeek()) {
                            gananciaTotalDia += puesto.getCobro();
                        }   
                    }
                }
            }
        }

        info += "$" + gananciaTotalDia;
        informacionRecaudosDiarios.add(info);
        reportesRecaudosDiarios.add(gananciaTotalDia);
    }
    /*
     * Metodo para generar los reportes mensuales
     */
    public void generarReporteMensual(LocalDate time){

        if (informacionRecaudosDiarios.size() > 0) {
            double recaudoTotal = 0;
            String info = "";
            for (String recaudo : informacionRecaudosDiarios) {
                info +=  recaudo + "\n" ;
            }
            for (Double recaudo : reportesRecaudosDiarios) {
                recaudoTotal += recaudo;
            } 
            info += "RECAUDO TOTAL DEL MES = " + recaudoTotal;

            reportesMensuales.addFirst(info);
        }
    }
    /*
     * Metodo para mostrar los reportes mensuales
     */
    public void mostrarReportesMensuales(LocalDate time){
        String info = "----------------  REPORTE DEL MES " + "\"" + time.getMonth() + "\"" + "   -----------------\n";
        for (String reporte : reportesMensuales) {
            info += reporte;
            break;
        }
        JOptionPane.showMessageDialog(null, info);
    }
    /*
     * Metodo para verificar si un vehiculo es un carro
     */
    public boolean verificarCarro(Vehiculo vehiculo){
        boolean esCarro = false;
        if (vehiculo instanceof Carro) {
            esCarro = true;
        }
        return esCarro;
    }
    /*
     * Metodo para generar el reporte Diario
     */
    public void generarReporteDiario(LocalDate fecha){
        String info = "----------------REPORTE DEL DIA " + fecha + "-----------------\n";
        int gananciaTotalDia = 0;

        for(int i =0; i<filas;i++){
            for(int j = 0; j<columnas;j++){
                for (Vehiculo vehiculo : vehiculosParqueados) {

                    Puesto puesto = puestos[i][j].buscarPuestoAnterior(vehiculo);

                    if (puesto.getRegistro() != null) {
                        if (puesto.getRegistro().getHoraSalida() != null && puesto.getRegistro().getFechaSalida().getDayOfWeek() == fecha.getDayOfWeek()) {
                            if (verificarCarro(vehiculo) == true) {
                                info += "Tipo de vehiculo = Carro\n" + "Cobro del vehiculo = " + puesto.getCobro() + "\n";
                                gananciaTotalDia += puesto.getCobro();
                            }else if (verificarCarro(vehiculo) == false) {
                                Moto moto = (Moto) vehiculo;
                                if (moto.getTipo() == TipoMoto.CLASICA) {
                                    info += "Tipo de vehiculo = Moto Clasica\n" + "Cobro del vehiculo = " + puesto.getCobro() + "\n";
                                    gananciaTotalDia += puesto.getCobro();
                                }else if(moto.getTipo() == TipoMoto.HIBRIDA ){
                                    info += "Tipo de vehiculo = Moto Hibrida\n" + "Cobro del vehiculo = " + puesto.getCobro() + "\n";
                                    gananciaTotalDia += puesto.getCobro();
                                }
                            }
                        }   
                    }
                }
            }
        }
        info += "GANANCIA DEL DIA = " + gananciaTotalDia;
        reportesDiarios.add(info);
    }
    /*
     * Metodo para mostrar el reporte diario
     */
    public void mostrarReportesDiarios(){

        for (String reporte : reportesDiarios) {
            JOptionPane.showMessageDialog(null, reporte);
        }
    }
    /*
     * Metodo para guardar un propietario
     */
    public void agregarPropietario(Propietario propietario){
        if (verificarPropietarioExiste(propietario) == false) {
            for(int i = 0; i<propietarios.length;i++){
                if (propietarios[i] == null) {
                    propietarios[i] = propietario;
                    break;
                }
            }
        }
    }
    /*
     * Metodo para verificar si un propietario ya existe en la lista del parqueadero
     */
    public boolean verificarPropietarioExiste(Propietario propietario){
        boolean existe = false;
        if (propietarios[0] == null) {
            existe = false;
        }else{
            for(int i = 0; i<propietarios.length;i++){
                if (propietarios[i] != null) {
                    if (propietarios[i].getId() == propietario.getId()) {
                        existe = true;
                    }
                }
            }
        }
        return existe;
    }
    /*
     * Metodo para verificar que la lista de propietarios esta lista
     */
    public boolean verificarListaPropietariosEstaLista(){
        boolean lista = false;
        if (propietarios[0] != null) {
            lista = true;
        }
        return lista;
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
    public double getTarifaCarro() {
        return tarifaCarro;
    }
    public double getTarifaMotoClasica() {
        return tarifaMotoClasica;
    }
    public double getTarifaMotoHibrida() {
        return tarifaMotoHibrida;
    }
    public Puesto[][] getPuestos() {
        return puestos;
    }
    public int[][] getNumPuestos() {
        return numPuestos;
    }
    public Collection<Vehiculo> getVehiculosParqueados() {
        return vehiculosParqueados;
    }
    public void setTarifaCarro(double tarifaCarro) {
        this.tarifaCarro = tarifaCarro;
    }
    public void setTarifaMotoClasica(double tarifaMotoClasica) {
        this.tarifaMotoClasica = tarifaMotoClasica;
    }
    public void setTarifaMotoHibrida(double tarifaMotoHibrida) {
        this.tarifaMotoHibrida = tarifaMotoHibrida;
    }
    public void setPuestos(Puesto[][] puestos) {
        this.puestos = puestos;
    }
    public void setNumPuestos(int[][] numPuestos) {
        this.numPuestos = numPuestos;
    }
    public void setVehiculosParqueados(Collection<Vehiculo> vehiculosParqueados) {
        this.vehiculosParqueados = vehiculosParqueados;
    }
    
    public Propietario[] getPropietarios() {
        return propietarios;
    }
    
    public LocalDate getFecha() {
        return fecha;
    }
}