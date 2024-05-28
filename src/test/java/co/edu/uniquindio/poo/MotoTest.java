package co.edu.uniquindio.poo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class MotoTest {
    /*
     * Test para verificar los datos completos de un vehiculo tipo moto
     */
    @Test
    public void datosCompletosTest(){
        Propietario pro = new Propietario("Pepe", "122323", "Email.com", "3155676429");
        Moto moto = new Moto(pro, "Placa", "Modelo", 300, TipoMoto.CLASICA);
        assertEquals(pro, moto.getPropietario());
        assertEquals("Placa", moto.getPlaca());
        assertEquals("Modelo", moto.getModelo());
        assertEquals(300, moto.getVelocidadMaxima());
        assertEquals(TipoMoto.CLASICA, moto.getTipo());
    }
}
