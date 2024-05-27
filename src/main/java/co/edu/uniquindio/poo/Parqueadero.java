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
    /*
     * ========================================METODOS PARA MANEJAR EL MENU INTERACTIVO POR JOPTIONPANE===================================================
     */
    /*
     *  Metodo estatico para crear un parqueadero por JOptionPane
     */
    public static Parqueadero crearParqueadero(){
        String nombre = verificarEntradaTextoEstatico("Ingresa el nombre del parqueadero");
        int columnas = (int) (verificarEntradaNumericaEstatico("Ingrese el numero de columnas del parqueadero"));
        int filas = (int) (verificarEntradaNumericaEstatico("Ingrese el numero de filas del parqueadero"));

        return new Parqueadero(nombre, columnas, filas);
    }
    /*
     * Metodo para desplegar el menu para manejar el parqueadero
     */
    public void menuParqueadero(){
        int opc = 5;
        int opcion = (int) (verificarEntradaNumerica("=============MENU DEL PARQUEADERO \""+nombre+"\"=============\n 1 = agregar vehiculo"+"\n==============================================================\nTarifa para Carros: "+tarifaCarro+"\nTarifa para Motos Clasicas: "+tarifaMotoClasica+ "\nTarifa para Motos Hibridas: "+tarifaMotoHibrida+"\n=============================================================="));
        
        while (opcion > 0) {
            if(opcion == 1) {
                menuAgregarVehiculo();
                while (opc > 0) {
                    opc = (int) (verificarEntradaNumerica("=============MENU DEL PARQUEADERO \""+nombre+"\"============\n 1. agregar vehiculo\n 2. Retirar un vehiculo\n 3. Generar reportes\n 0.Salir"+"\n==============================================================\n" + //
                                                "Tarifa para Carros: "+tarifaCarro+"\nTarifa para Motos Clasicas: "+tarifaMotoClasica+ "\nTarifa para Motos Hibridas: "+tarifaMotoHibrida+"\n=============================================================="));
                    switch (opc) {
                        case 1:
                            menuAgregarVehiculo();
                            break;
                        case 2:
                            menuRetirarUnVehiculo();
                            break;
                        case 3:
                            menuGenerarReportes();
                            break;
                        /*
                         * Opcion 0: Salir del menu
                         */
                        case 0:
                            JOptionPane.showMessageDialog(null, "Muchas Gracias por utilizar nuestros servicios");
                            opcion = 0;
                            break;
    
                        default:
                            JOptionPane.showMessageDialog(null, "Inserte una opcion valida");
                            break;
                    }
                }
            }else{
                while (opcion != 1) {
                    JOptionPane.showMessageDialog(null, "Ingrese un valor valido");
                    opcion = Integer.parseInt(JOptionPane.showInputDialog("MENU PARQUEADERO\n" + " 1.agregar vehiculo"));
                }
            }
        }
    }
    /*
     * Metodo para desplegar el menu para agregar un vehiculo
     */
    public void menuAgregarVehiculo(){
        // Registro informacion del propietario del vehiculo
        Propietario propietario = new Propietario("Ejemplo", "Ejemplo", "Ejemplo", "Ejemplo");
        String[] opciones = {"lista de propietarios" , "Crear uno nuevo"};
        int opcionP = JOptionPane.showOptionDialog(null, "Seleccione que propietario desea agregar: ", "Menu agregar vehiculo", 0, 1, null, opciones, opciones[0]);
        if (opcionP == 0) {
            if (verificarListaPropietariosEstaLista() == true) {
                Map<String, Propietario> temporal = new HashMap<>();
                for(Propietario propietarioArreglo : propietarios){
                    if(propietarioArreglo != null){
                        temporal.put(propietarioArreglo.getNombre() + "-" + propietarioArreglo.getId(), propietarioArreglo);
                    }
                }
                String[] opcionesPropietarios = temporal.keySet().toArray(new String[0]);
                String propietarioSeleccionado = (String) JOptionPane.showInputDialog(null, "Seleccione el propietario de la lista: ", "Elegir", JOptionPane.QUESTION_MESSAGE,null,opcionesPropietarios,opcionesPropietarios[0]);
                propietario = temporal.get(propietarioSeleccionado);
            }else{
                JOptionPane.showMessageDialog(null, "Aun no se han agregardo propietarios al parqueadero","Error",JOptionPane.ERROR_MESSAGE);
                opcionP = 1;
            }
        }
        if (opcionP == 1) {
            String nombre = verificarEntradaTexto("Ingrese el nombre del propietario");
            String id = verificarEntradaTexto("Ingrese el numero de identificacion del propietario");
            String email = verificarEntradaTexto("Ingrese el correo del propietario");
            String telefono = verificarEntradaTexto("Ingrese el telefono del propietario");
            propietario = new Propietario(nombre, id, email, telefono);
        }
        
        
        // Registro informacion del vehiculo
        int posicion = (int) (verificarEntradaNumerica("Ingrese en que posicion desea ubicar el vehiculo: \n" + mostrarParqueaderoDisponibilidad()));

        while(verificarPosicionExiste(posicion) == false || verificarDisponibilidad(posicion) == false){
            JOptionPane.showMessageDialog(null, "Aqui no hay ningun vehiculo / Este puesto esta ocupado", "Error", JOptionPane.ERROR_MESSAGE);
            posicion = (int) (verificarEntradaNumerica("Ingrese en que posicion desea ubicar el vehiculo: \n" + mostrarParqueaderoDisponibilidad()));
        }

        String placa = verificarEntradaTexto("Ingrese la placa del vehiculo");
        String modelo = verificarEntradaTexto("Ingrese el modelo del vehiculo");
        int tipo = (int) (verificarEntradaNumerica("Ingrese el tipo de Vehiculo\n 1.Moto \n 2.Carro"));
        while (tipo > 0) {
            if (tipo == 1) {
                double velocidad = verificarEntradaNumerica("Ingrese la velocidad maxima de la moto");
                int tipoMoto = (int) (verificarEntradaNumerica("Ingrese el tipo de Moto\n 1.Clasica\n 2.Hibrida"));
                while (tipoMoto > 0) {
                    if (tipoMoto == 1) {
                        anadirVehiculo(new Moto(propietario,placa, modelo, velocidad, TipoMoto.CLASICA), posicion);
                        JOptionPane.showMessageDialog(null, "Moto Clasica agregada con exito");
                        tipo = 0;
                        tipoMoto = 0;
                    }else if (tipoMoto == 2) {
                        anadirVehiculo(new Moto(propietario,placa, modelo, velocidad, TipoMoto.HIBRIDA), posicion);
                        JOptionPane.showMessageDialog(null, "Moto Hibrida agregada con exito");
                        tipo = 0;
                        tipoMoto = 0;
                    }else{
                        JOptionPane.showMessageDialog(null, "Ingrese una opcion valida");
                        tipoMoto = (int) (verificarEntradaNumerica("Ingrese el tipo de Moto\n 1.Clasica\n 2.Hibrida"));
                    }
                }
                
        
            }else if (tipo == 2) {
                int numPuertas = (int) (verificarEntradaNumerica("Ingrese el numero de puertas del Carro"));
                anadirVehiculo(new Carro(propietario,placa, modelo, numPuertas), posicion);
                JOptionPane.showMessageDialog(null, "Carro agregado con exito");
                tipo = 0;

            }else{
                JOptionPane.showMessageDialog(null, "Ingrese una opcion valida");
                tipo = (int) (verificarEntradaNumerica("Ingrese el tipo de Vehiculo\n 1.Moto \n 2.Carro"));
            }
        }
    }
    /*
     * Metodo para desplegar el menu para acceder a la matriz del parqueadero y retirar un vehiculo
     */
    public void menuRetirarUnVehiculo(){
        int opc3 = 5;
        while (opc3 > 0) {
            opc3 = mostrarParqueaderoYSeleccionar("Retirar");
            if (opc3 != 0) {
                if (ubicarVehiculo(opc3) == null || verificarPosicionExiste(opc3) == false) {
                    JOptionPane.showMessageDialog(null, "Aqui no hay ningun vehiculo", "Error", JOptionPane.ERROR_MESSAGE);
                }else{
                    int respuesta = JOptionPane.showConfirmDialog(null, "¿Seguro que desea eliminar este Vehiculo?", "Retirar vehiculo", JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);
                    if (respuesta == 0) {
                        retirarVehiculo(opc3);
                        JOptionPane.showMessageDialog(null,"El vehiculo fue retirado con exito");
                    }
                }               
            }
        }
    }
    /*
     * Metodo para desplegar el menu para generar los reportes
     */
    public void menuGenerarReportes(){
        String[] opciones = {"Reporte General", "Reporte Diario", "Reporte Mensual", "Reporte Total Vehiculos", "Regresar"};
        int opc4 = 5;
        
        while (opc4 > 0) {
            int seleccion = JOptionPane.showOptionDialog(null, "Seleccione el reporte que desea generar: ", "Menu reportes", 0, 1, null, opciones, opciones[0]);
            if (seleccion == 0) {
                menuAccederALaListaDeVehiculos();
            }else if (seleccion == 1) {
                if (reportesDiarios.size() == 0) {
                JOptionPane.showMessageDialog(null, "Los reportes Diarios se generan al final del dia, espera a que se actualice el dia");
                }else{
                    mostrarReportesDiarios();
                }

            }else if (seleccion == 2) {

                if (reportesMensuales.size() == 0) {
                    JOptionPane.showMessageDialog(null, "Los reportes Mesuales se comienzan a generar al final del dia, espera a que se actualice el dia");
                }else{
                    mostrarReportesMensuales(fecha);
                }
            }else if (seleccion == 3) {
                generarReporteTotal();
            }else if (seleccion == 4) {
                opc4 = 0;
            }
        }
    }
    /*
     * Metodo para desplegar el menu para acceder a la matriz del parqueadero y inspeccionar vehiculos
     */
    public void menuAccederALaListaDeVehiculos(){
        int opc2 = 5;
        while (opc2 > 0) {
            opc2 = (int)(verificarEntradaNumerica("<html>Selecciona el vehiculo que deseas inspeccionar <br> "+ mostrarParqueadero()+ "<br>"+ "0. Salir</html>"));
            if (opc2 != 0) {
                if (verificarPosicionExiste(opc2) == true || ubicarVehiculo(opc2) != null) {
                    mostrarVehiculo(opc2);
                }else{
                    JOptionPane.showMessageDialog(null, "Aqui no hay ningun vehiculo", "Error", JOptionPane.ERROR_MESSAGE);
                }
                
            }
        }
    }
    /*
     * Metodo estatico para verificar que una entrada sea un texto
     */
    public static String verificarEntradaTextoEstatico(String informacion){
        String resultado =  "";
        boolean entradaValida = false;
        while (!entradaValida) {
            try {
                resultado = JOptionPane.showInputDialog(informacion);
                if (resultado == null) {
                    break;
                }else{
                    entradaValida = true;
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Entrada invalida", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return resultado;
    }
    /*
     * Metodo para verificar que una entrada sea un texto
     */
    public String verificarEntradaTexto(String informacion){
        String resultado =  "";
        boolean entradaValida = false;
        while (!entradaValida) {
            try {
                resultado = JOptionPane.showInputDialog(informacion);
                if (resultado == null) {
                    break;
                }else{
                    entradaValida = true;
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Entrada invalida", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return resultado;
    }
    /*
     * Metodo estatico para verificar que una entrada sea un numero
     */
    public static double verificarEntradaNumericaEstatico(String informacion){
        double resultado = 0;
        boolean entradaValida = false;
        while (!entradaValida) {
            try {
                String entrada = JOptionPane.showInputDialog(informacion);
                if (entrada == null) {
                    break;
                }else{
                    resultado = Integer.parseInt(entrada);
                    entradaValida = true;
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Entrada invalida", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return resultado;
    }
    /*
     * Metodo para verificar que una entrada sea un numero
     */
    public double verificarEntradaNumerica(String informacion){
        double resultado = 0;
        boolean entradaValida = false;
        while (!entradaValida) {
            try {
                String entrada = JOptionPane.showInputDialog(informacion);
                if (entrada == null) {
                    break;
                }else{
                    resultado = Integer.parseInt(entrada);
                    entradaValida = true;
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Entrada invalida", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return resultado;
    }
    /*
     * ==================================================== METODOS QUE SE USAN EN LOS TEST =======================================================
     */
    /*
     * Metodo para establecer las tarifas del parqueadero
     */
    public void establecerTarifas(double tarifaCarro, double tarifaMotoClasica, double tarifaMotoHibrida){
        assert tarifaCarro > 0;
        assert tarifaMotoClasica > 0;
        assert tarifaMotoHibrida > 0;
        setTarifaCarro(tarifaCarro);
        setTarifaMotoClasica(tarifaMotoClasica);
        setTarifaMotoHibrida(tarifaMotoHibrida);
    }
    /*
     * Metodo para preparar el parqueadero
     */
    public void prepararParqueadero(){
        generarArregloPropietarios();
        generarNumPuestos();
        generarPuestos();
    }
    /*
     * Metodo para modificar la fecha manualmente
     */
    public void setFecha(LocalDate fecha){
        this.fecha = fecha;
    }
}