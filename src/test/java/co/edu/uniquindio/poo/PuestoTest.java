package co.edu.uniquindio.poo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class PuestoTest {
    /*
     * Test para verificar la entrada de un vehiculo en su puesto
     */
    @Test
    public void crearPuestoTest(){
        Propietario pro = new Propietario("Pepe", "123456789", "Email.com", "3155676733");
        Carro car = new Carro(pro, "Placa", "Modelo", 4);
        Registro regi = new Registro(null, null, null, null);
        Puesto puesto = new Puesto(car, regi, 0);
        assertEquals(regi, puesto.getRegistro());
        assertEquals(car, puesto.getVehiculo());
        assertEquals(0, puesto.getCobro());
    }
    /*
     * Test para verificar la el registro de usos anteriores del puesto
     */
    @Test
    public void usosAnterioresDelPuestoTest(){
        Puesto puesto = new Puesto(null, null, 0);
        Propietario pro = new Propietario("Pepe", "123456789", "Email.com", "3155676733");
        Carro car = new Carro(pro, "Placa", "Modelo", 4);
        Carro car2 = new Carro(pro, "Placa2", "Modelo2", 2);
        puesto.agregarVehiculo(car);
        puesto.retirarVehiculo(LocalDate.now());
        puesto.agregarVehiculo(car2);
        assertEquals(car, puesto.buscarPuestoAnterior(car).getVehiculo());
    }
}
