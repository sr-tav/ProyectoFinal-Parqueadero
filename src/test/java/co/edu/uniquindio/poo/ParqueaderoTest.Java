package co.edu.uniquindio.poo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import javax.swing.JOptionPane;

import org.junit.jupiter.api.Test;

public class ParqueaderoTest {
    /*
     * Test para verificar la disponibilidad de un puesto
     */
    @Test
    public void verificarDisponibilidadTest(){
        Parqueadero par = new Parqueadero("Nombre", 2, 2);
        par.prepararParqueadero();
        //El parqueadero esta vacio, entonces la disponibilidad en todos es verdadera
        assertEquals(true, par.verificarDisponibilidad(1));
    }
    /*
     * Test para verificar la entrada de un vehiculo
     */
    @Test
    public void agregarVehiculoTest(){
        Parqueadero par = new Parqueadero("Nombre", 2, 2);
        par.prepararParqueadero();
        Carro carro = new Carro(new Propietario("Pepe", "122323", "Email.com", "3155676429"), "00000", "2020", 4);
        par.anadirVehiculo(carro, 2);
        //Usamos el metodo del parqueadero para ubicar un vehiculo dada una posicion y si ese vehiculo es el que agregamos
        assertEquals(carro, par.ubicarVehiculo(2));
    }
    /*
     * Test para buscar el propietario de un vehiculo
     */
    @Test
    public void ubicarPropietarioTest(){
        Parqueadero par = new Parqueadero("Nombre", 2, 2);
        par.prepararParqueadero();
        Propietario pro = new Propietario("Pepe", "122323", "Email.com", "3155676429");
        Carro carro = new Carro(pro, "00000", "2020", 4);
        par.anadirVehiculo(carro, 2);
        // El vehiculo tiene un propietario asociado, entonces si encontramos el vehiculo, tambien encontramos el propietario
        assertEquals(pro, par.ubicarVehiculo(2).getPropietario());

    }
    /*
     * Test para retirar un vehiculo de un puesto
     */
    @Test
    public void retirarVehiculoTest(){
        Parqueadero par = new Parqueadero("Nombre", 2, 2);
        Carro carro = new Carro(new Propietario("Pepe", "122323", "Email.com", "3155676429"), "00000", "2020", 4);

        par.prepararParqueadero();
        par.anadirVehiculo(carro, 2);
        // Retiramos el vehiculo que esta en la posicion 2 y verificamos la disponibilidad en ese puesto
        par.retirarVehiculo(2);
        assertTrue(par.verificarDisponibilidad(2));
        
    }
    /*
     * Test para verificar que no se pueda agregar un vehiculo en un puesto ocupado
     */
    @Test
    public void puestoOcuapdoTest(){
        Parqueadero par = new Parqueadero("Nombre", 2, 2);
        Carro carro = new Carro(new Propietario("Pepe", "122323", "Email.com", "3155676429"), "00000", "2020", 4);
        Moto moto = new Moto(new Propietario("Pepe", "122323", "Email.com", "3155676429"), "00000", "2021", 100, TipoMoto.CLASICA);

        par.prepararParqueadero();
        par.anadirVehiculo(carro, 2);
        par.anadirVehiculo(moto, 2);
        //El parqueadero no tira errores como para usar un assertThrows pero no deja agregar un vehiculo en un puesto ocupado, por eso solamente verificamos
        assertEquals(carro, par.ubicarVehiculo(2));
    }
    /*
     * Test para validar el cobro de un vehiculo
     */
    @Test
    public void validarCobro(){
        Parqueadero par = new Parqueadero("Nombre", 2, 2);
        Carro carro = new Carro(new Propietario("Pepe", "122323", "Email.com", "3155676429"), "00000", "2020", 4);
        par.establecerTarifas(300, 200, 100);
        par.prepararParqueadero();
        long tiempo = 5;
        assertEquals(1500, par.calcularCobro(tiempo, carro));

    }
    /*
     * Test para generar un reporte Diario
     */
    @Test
    public void generarReporteDiarioTest(){
        Parqueadero par = new Parqueadero("Nombre", 2, 2);
        Carro carro = new Carro(new Propietario("Pepe", "122323", "Email.com", "3155676429"), "00000", "2020", 4);

        par.prepararParqueadero();
        par.anadirVehiculo(carro, 2);
        par.setFecha(LocalDate.now());
        par.retirarVehiculo(2);
        par.generarReporteDiario(LocalDate.now());
        par.mostrarReportesDiarios();
    }
    /*
     * Test para generar reporte Mensual
     */
    @Test
    public void generarReporteMensualTest(){
        Parqueadero par = new Parqueadero("Nombre", 2, 2);
        Carro carro = new Carro(new Propietario("Pepe", "122323", "Email.com", "3155676429"), "00000", "2020", 4);
        par.establecerTarifas(300, 200, 100);
        par.prepararParqueadero();
        par.anadirVehiculo(carro, 2);
        par.setFecha(LocalDate.now());
        JOptionPane.showMessageDialog(null, "Espera un momento para calcular algo de tiempo");
        par.retirarVehiculo(2);
        par.generarReporteDiario(LocalDate.now());
        par.generarRecaudoDiario(LocalDate.now());
        par.generarReporteMensual(LocalDate.now());
        par.mostrarReportesMensuales(LocalDate.now());
    }
    /*
     * Test para generar un parqueadero con tamaño negativo
     */
    @Test
    public void parqueaderoTamañoNegativo(){
        assertThrows(Throwable.class, ()-> new Parqueadero(null, -2,-2));
    }
    /*
     * Test para intentar agregar un vehiculo fuera de los limites del parqueadero
     */
    @Test
    public void vehiculoFueraDelLimite(){
        Parqueadero par = new Parqueadero("Nombre", 2, 2);
        Carro carro = new Carro(new Propietario("Pepe", "122323", "Email.com", "3155676429"), "00000", "2020", 4);

        par.establecerTarifas(300, 200, 100);
        par.prepararParqueadero();
        //El parqueadero es de 2x2 entonces el puesto 5 no existe, el parqueadero no tira error pero no lo va a agregar por el metodo verificarPosicionExiste
        par.anadirVehiculo(carro, 5);
        assertFalse(par.verificarPosicionExiste(5));;
    }
    /*
     * Test para establecer tarifas negativas
     */
    @Test
    public void establecerTarifasNegativas(){
        Parqueadero par = new Parqueadero("Nombre", 2, 2);
        par.prepararParqueadero();
        assertThrows(Throwable.class, ()-> par.establecerTarifas(-300, -200, -100));
    }
}