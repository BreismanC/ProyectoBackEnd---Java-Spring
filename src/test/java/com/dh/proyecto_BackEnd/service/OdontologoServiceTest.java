package com.dh.proyecto_BackEnd.service;

import com.dh.proyecto_BackEnd.entity.Domicilio;
import com.dh.proyecto_BackEnd.entity.Odontologo;
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
class OdontologoServiceTest {
    @Autowired
    private OdontologoService odontologoService;

    @Test
    @Order(1)
    public void guardarOdontologoTest(){
        Odontologo odontologoAGuardar = new Odontologo("a123", "Jorge", "Perez");
        Odontologo odontologoGuardado = odontologoService.guardarOdontologo(odontologoAGuardar);
        assertNotNull(odontologoGuardado);
    }

    @Test
    @Order(2)
    public void buscarOdontologoPorIdTest() throws ResourceNotFoundException {
        Long idABuscar=1L;
        Optional<Odontologo> odontologoBuscado = odontologoService.buscarOdontologo(idABuscar);
        assertNotNull(odontologoBuscado.get());
    }

    @Test
    @Order(3)
    public void listarOdontologosTest(){
        List<Odontologo> odontologos = odontologoService.listarOdontologos();
        assertEquals(1, odontologos.size());
    }

    @Test
    @Order(4)
    public void actualizarOdontologoTest() throws ResourceNotFoundException {
        Odontologo odontologoAActualizar= new Odontologo(1L, "xyz", "Juan", "Rodriguez");
        odontologoService.actualizarOdontologo(odontologoAActualizar);
        Optional<Odontologo> odontologoActualizado=odontologoService.buscarOdontologo(1L);
        assertEquals("Juan",odontologoActualizado.get().getNombre());
    }

    @Test
    @Order(5)
    public void eliminarOdontologoTest(){
        Long idAEliminar=1L;
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, ()->{
            odontologoService.eliminarOdontologo(idAEliminar);
            Optional<Odontologo> odontologoEliminado=odontologoService.buscarOdontologo(idAEliminar);
        });
        String mensajeEsperado = "No se puede encontrar al odontologo con id: " + idAEliminar;
        String mensajeActual = exception.getMessage();
        assertTrue(mensajeActual.contains(mensajeEsperado));
    }
}