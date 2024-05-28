package co.edu.uniquindio.poo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CarroTest {
    /*
     * Test para verificar la creacion de un carro adecuada
     */
    @Test
    public void carroDatosCompletosTest(){
        Propietario pro = new Propietario("Pepe", "122323", "Email.com", "3155676429");
        Carro carro = new Carro(pro, "00000", "2020", 4);
        assertEquals(pro, carro.getPropietario());
        assertEquals("00000", carro.getPlaca());
        assertEquals("2020", carro.getModelo());
        assertEquals(4, carro.getNumPuertas());
    }
}
