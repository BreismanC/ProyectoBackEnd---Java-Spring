package com.dh.proyecto_BackEnd.controller;

import com.dh.proyecto_BackEnd.dto.TurnoDTO;
import com.dh.proyecto_BackEnd.exceptions.BadRequestException;
import com.dh.proyecto_BackEnd.exceptions.ResourceNotFoundException;
import com.dh.proyecto_BackEnd.service.OdontologoService;
import com.dh.proyecto_BackEnd.service.PacienteService;
import com.dh.proyecto_BackEnd.service.TurnoService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/turnos")
public class TurnoController {
    private static final Logger LOGGER = Logger.getLogger(TurnoController.class);
    private TurnoService turnoService;
    private PacienteService pacienteService;
    private OdontologoService odontologoService;
    @Autowired
    public TurnoController(TurnoService turnoService, PacienteService pacienteService, OdontologoService odontologoService) {
        this.turnoService = turnoService;
        this.pacienteService = pacienteService;
        this.odontologoService = odontologoService;
    }

    @PostMapping
    public ResponseEntity<TurnoDTO> guardar(@RequestBody TurnoDTO turnoDTO) throws BadRequestException, ResourceNotFoundException{
        LOGGER.info("Iniciando una operación de guardado de un turno para el paciente con id: " + turnoDTO.getPaciente_id() + " - asignado al odontologo con id: " + turnoDTO.getOdontologo_id());
        //Verificamos que el paciente y el odontologo existan antes de poder guardar
        if(odontologoService.buscarOdontologo(turnoDTO.getOdontologo_id()).isPresent() && pacienteService.buscarPaciente(turnoDTO.getPaciente_id()).isPresent()){
            LOGGER.info("Existo en la operacion de guardado del turno");
            return ResponseEntity.ok(turnoService.guardarTurno(turnoDTO));
        }else{
            LOGGER.info("Fallo en la operacion de guardado del turno");
            throw new BadRequestException("Error en la solicitud");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<TurnoDTO> buscar(@PathVariable Long id) throws ResourceNotFoundException {
        LOGGER.info("Iniciando una operación de busqueda del turno con id: "+ id);
        Optional<TurnoDTO> turnoDTOBuscado = turnoService.buscarTurno(id);
        return ResponseEntity.ok(turnoDTOBuscado.get());
    }

    @GetMapping
    public ResponseEntity<List<TurnoDTO>> listar() {
        LOGGER.info("Iniciando la operación de listar todos los turnos");
        return ResponseEntity.ok(turnoService.listarTurnos());
    }

    @PutMapping
    public ResponseEntity<String> actualizar(@RequestBody TurnoDTO turnoDTO) throws ResourceNotFoundException, BadRequestException{
        LOGGER.info("Iniciando una operacion de busqueda - actualizacion del turno con id:" + turnoDTO.getId());
        //Verificar que el turno exista
        Optional<TurnoDTO> turnoBuscado = turnoService.buscarTurno(turnoDTO.getId());
        //Verificamos que el paciente y el odontologo a actualizar existan
        if(odontologoService.buscarOdontologo(turnoDTO.getOdontologo_id()).isPresent() && pacienteService.buscarPaciente(turnoDTO.getPaciente_id()).isPresent()){
            turnoService.actualizarTurno(turnoDTO);
            LOGGER.info("Existo en la operacion de actualizacion del turno");
            return ResponseEntity.ok("Se actualizó el turnoDTO con id: " + turnoDTO.getId());
        }else{
            LOGGER.info("Fallo en la operacion de actualizacion de un turno");
            throw new BadRequestException("Error en la solicitud");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> borrar(@PathVariable Long id) throws ResourceNotFoundException{
        LOGGER.warn("Iniciando la operacion de busqueda - eliminacion del turnoDTO con id: " + id);
        turnoService.eliminarTurno(id);
        return ResponseEntity.ok("Se elimino el turnoDTO con id:" + id);
    }
}
