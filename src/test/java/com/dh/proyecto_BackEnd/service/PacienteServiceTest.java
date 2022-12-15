package com.dh.proyecto_BackEnd.service;

import com.dh.proyecto_BackEnd.entity.Domicilio;
import com.dh.proyecto_BackEnd.entity.Paciente;
import com.dh.proyecto_BackEnd.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
class PacienteServiceTest {
    @Autowired
    private PacienteService pacienteService;

    @Test
    @Order(1)
    public void guardarPacienteTest(){
        Paciente pacienteAGuardar= new Paciente("Rodolfo",
                "Baspineiro","5154", LocalDate.of(2022,11,28),
                "rodo@gmail.com",new Domicilio("Calle a",454,"Salta capital",
                "Salta"));
        Paciente pacienteGuardado=pacienteService.guardarPaciente(pacienteAGuardar);
        assertEquals(1L,pacienteGuardado.getId());
    }
    @Test
    @Order(2)
    public void buscarPacientePorIDTest() throws ResourceNotFoundException {
        Long idABuscar=1L;
        Optional<Paciente> pacienteBuscado=pacienteService.buscarPaciente(idABuscar);
        assertNotNull(pacienteBuscado.get());
    }
    @Test
    @Order(3)
    public void buscarPacientesTest(){
        List<Paciente> pacientes=pacienteService.listarPacientes();
        assertEquals(1,pacientes.size());
    }
    @Test
    @Order(4)
    public void actualizarPacienteTest() throws ResourceNotFoundException {
        Paciente pacienteAActualizar= new Paciente(1L,"Ezequiel",
                "Baspineiro","5154", LocalDate.of(2022,11,28),
                "rodo@gmail.com",new Domicilio(1L,"Calle a",454,"Salta capital",
                "Salta"));
        pacienteService.actualizarPaciente(pacienteAActualizar);
        Optional<Paciente> pacienteActualizado=pacienteService.buscarPaciente(1L);
        assertEquals("Ezequiel",pacienteActualizado.get().getNombre());
    }
    @Test
    @Order(5)
    public void eliminarPacienteTest(){
        Long idAEliminar=1L;
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, ()->{
            pacienteService.eliminarPaciente(idAEliminar);
            Optional<Paciente> pacienteEliminado=pacienteService.buscarPaciente(idAEliminar);
        });
        String mensajeEsperado = "No se puede encontrar al paciente con id: " + idAEliminar;
        String mensajeActual = exception.getMessage();
        assertTrue(mensajeActual.contains(mensajeEsperado));
    }
}