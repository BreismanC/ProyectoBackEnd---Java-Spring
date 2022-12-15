package com.dh.proyecto_BackEnd.service;

import com.dh.proyecto_BackEnd.dto.TurnoDTO;
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

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
class TurnoServiceTest {
    @Autowired
    private PacienteService pacienteService;
    @Autowired
    private OdontologoService odontologoService;
    @Autowired
    private TurnoService turnoService;

    @Test
    @Order(1)
    public void guardarTurnoTest(){
        Odontologo odontologoAGuardar = new Odontologo("a123", "Jorge", "Perez");
        Odontologo odontologoGuardado = odontologoService.guardarOdontologo(odontologoAGuardar);
        Paciente pacienteAGuardar= new Paciente("Rodolfo",
                "Baspineiro","5154", LocalDate.of(2022,11,28),
                "rodo@gmail.com",new Domicilio("Calle a",454,"Salta capital",
                "Salta"));
        Paciente pacienteGuardado=pacienteService.guardarPaciente(pacienteAGuardar);
        TurnoDTO turnoAGuardar = new TurnoDTO();
        turnoAGuardar.setPaciente_id(odontologoGuardado.getId());
        turnoAGuardar.setOdontologo_id(odontologoGuardado.getId());
        turnoAGuardar.setFecha(LocalDate.of(2022,12,9));
        TurnoDTO turnoGuardado = turnoService.guardarTurno(turnoAGuardar);
        assertNotNull(turnoGuardado);
    }

    @Test
    @Order(2)
    public void buscarTurnoPorIDTest() throws ResourceNotFoundException {
        Long idABuscar=1L;
        Optional<TurnoDTO> turnoBuscado=turnoService.buscarTurno(idABuscar);
        assertNotNull(turnoBuscado.get());
    }

    @Test
    @Order(3)
    public void buscarTurnosTest(){
        List<TurnoDTO> turnos=turnoService.listarTurnos();
        assertEquals(1,turnos.size());
    }

    @Test
    @Order(4)
    public void actualizarTurnoTest() throws ResourceNotFoundException {
        TurnoDTO turnoAActualizar= new TurnoDTO();
        Odontologo odontogoBuscado = odontologoService.buscarOdontologo(1L).get();
        Paciente pacienteBuscado = pacienteService.buscarPaciente(1L).get();
        turnoAActualizar.setFecha(LocalDate.of(2022,12,12));
        turnoAActualizar.setPaciente_id(pacienteBuscado.getId());
        turnoAActualizar.setOdontologo_id(odontogoBuscado.getId());
        turnoAActualizar.setId(1L);
        turnoService.actualizarTurno(turnoAActualizar);
        Optional<TurnoDTO> turnoActualizado=turnoService.buscarTurno(1L);
        assertEquals(LocalDate.of(2022,12,12),turnoActualizado.get().getFecha());
    }
    @Test
    @Order(5)
    public void eliminarTurnoTest() throws ResourceNotFoundException {
        Long idAEliminar=1L;
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, ()->{
            turnoService.eliminarTurno(idAEliminar);
            Optional<TurnoDTO> turnoEliminado=turnoService.buscarTurno(idAEliminar);
        });
        String mensajeEsperado = "No se puede encontrar al turno con id: " + idAEliminar;
        String mensajeActual = exception.getMessage();
        assertTrue(mensajeActual.contains(mensajeEsperado));
    }
}