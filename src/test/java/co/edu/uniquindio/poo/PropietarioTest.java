package co.edu.uniquindio.poo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class PropietarioTest {
    /*
     * Test para verificar la creacion de un propietario
     */
    @Test
    public void datosCompletosTest(){
        Propietario pro = new Propietario("Pepe", "123456789", "Email.com", "3155676733");
        assertEquals("Pepe", pro.getNombre());
        assertEquals("123456789", pro.getId());
        assertEquals("Email.com", pro.getEmail());
        assertEquals("3155676733", pro.getTelefono());
    }
    /*
     * Test para verificar las restricciones del propietario
     */
    @Test
    public void datosNulos(){
        assertThrows(Throwable.class,()-> new Propietario(null, null, null, null));
    }
    @Test
    public void datosVacios(){
        assertThrows(Throwable.class,()-> new Propietario("", "", "", ""));
    }
}
