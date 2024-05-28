package co.edu.uniquindio.poo;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.print.attribute.standard.RequestingUserName;

import org.junit.jupiter.api.Test;

public class RegistroTest {
    /*
     * Test para verificar las horas del registro
     */
    @Test
    public void verificarHorasTest(){
        Registro regis = new Registro(LocalDate.of(2024, 05, 27), LocalDateTime.of(2024, 05, 27, 19, 1, 1), LocalDateTime.of(2024, 05, 28, 18, 1, 1), LocalDate.of(2024, 06, 27));
        assertEquals(LocalDate.of(2024, 05, 27), regis.getFechaEntrada());
        assertEquals(LocalDateTime.of(2024, 05, 27, 19, 1, 1), regis.getHoraEntrada());
        assertEquals(LocalDateTime.of(2024, 05, 28, 18, 1, 1), regis.getHoraSalida());
        assertEquals(LocalDate.of(2024, 06, 27), regis.getFechaSalida());
    }
    /*
     * Test para verficar el calculo de horas de uso
     */
    @Test
    public void verificarTiempoUso(){
        Registro regis = new Registro(LocalDate.of(2024, 05, 27), LocalDateTime.of(2024, 05, 27, 19, 1, 1), LocalDateTime.of(2024, 05, 27, 19, 1, 6), LocalDate.of(2024, 06, 27));
        assertEquals(5, regis.calcularHorasUso());
    }
}
