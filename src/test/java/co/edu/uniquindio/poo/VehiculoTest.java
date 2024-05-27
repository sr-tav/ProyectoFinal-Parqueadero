package co.edu.uniquindio.poo;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class VehiculoTest {

    /*
     * Prueba para el constructor de la clase vehículo
     */
    @Test
    public void VerificarConstructor() {
        Propietario propietario = new Propietario("Vanesa", "1234", "leidyvanesa@gmail.com", "235678");
        Vehiculo vehiculo = new Vehiculo(propietario, "ABC123", "ModeloX");

        assertEquals("ABC123", vehiculo.getPlaca());
        assertEquals("ModeloX", vehiculo.getModelo());
        assertEquals(propietario, vehiculo.getPropietario());
    }
/*
 * prueba para testear los métodos getters y setters
 */
    @Test
    public void verificarGetYSet() {
        Propietario propietario = new Propietario("Vanesa", "1234", "leidyvanesa@gmail.com", "235678");
        Vehiculo vehiculo = new Vehiculo(propietario, "ABC123", "ModeloX");

        vehiculo.setPlaca("XYZ789");
        vehiculo.setModelo("ModeloY");
        Propietario nuevoPropietario = new Propietario("Vanesa", "1234", "leidyvanesa@gmail.com", "235678");
        vehiculo.setPropietario(nuevoPropietario);

        assertEquals("XYZ789", vehiculo.getPlaca());
        assertEquals("ModeloY", vehiculo.getModelo());
        assertEquals(nuevoPropietario, vehiculo.getPropietario());
    }
    /*
     *prueba para testear el método To String
     */

    @Test
    public void testToString() {
        Propietario propietario = new Propietario("Vanesa", "1234", "leidyvanesa@gmail.com", "235678");
        Vehiculo vehiculo = new Vehiculo(propietario, "ABC123", "ModeloX");

        String expected = propietario.toString() + 
                          "------INFORMACION DEL VEHICULO-----\n" +
                          "Placa: ABC123\n" + 
                          "Modelo: ModeloX\n";

        assertEquals(expected, vehiculo.toString());
    }

}
