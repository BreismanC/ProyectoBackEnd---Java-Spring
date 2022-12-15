package com.dh.proyecto_BackEnd.controller;

import com.dh.proyecto_BackEnd.entity.Paciente;
import com.dh.proyecto_BackEnd.exceptions.ResourceNotFoundException;
import com.dh.proyecto_BackEnd.service.PacienteService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    private static final Logger LOGGER = Logger.getLogger(PacienteController.class);
    private PacienteService pacienteService;
    @Autowired
    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @PostMapping
    public ResponseEntity<Paciente> guardar(@RequestBody Paciente paciente){
        LOGGER.info("Iniciando una operación de guardado de paciente con nombre: " + paciente.getNombre());
        return ResponseEntity.ok(pacienteService.guardarPaciente(paciente));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Paciente> buscar(@PathVariable Long id) throws ResourceNotFoundException {
        LOGGER.info("Iniciando una operación de busqueda de paciente con id: "+ id);
        Optional<Paciente> pacienteBuscado = pacienteService.buscarPaciente(id);
        return ResponseEntity.ok(pacienteBuscado.get());
    }

    @GetMapping("/correo/{email}")
    public ResponseEntity<Paciente> buscarPorString(@PathVariable String email) throws ResourceNotFoundException {
        LOGGER.info("Iniciando una operación de busqueda de paciente con id: "+ email);
        Optional<Paciente> pacienteBuscado = pacienteService.buscarPacientePorString(email);
        return ResponseEntity.ok(pacienteBuscado.get());
    }

    @GetMapping
    public ResponseEntity<List<Paciente>> listar() {
        LOGGER.info("Iniciando la operación de listar todos los pacientes");
        return ResponseEntity.ok(pacienteService.listarPacientes());
    }

    @PutMapping
    public ResponseEntity<String> actualizar(@RequestBody Paciente paciente) throws ResourceNotFoundException{
        LOGGER.info("Iniciando una operacion de busqueda - actualizacion de un paciente con id:" + paciente.getId());
        pacienteService.actualizarPaciente(paciente);
        return ResponseEntity.ok("Se actualizó el paciente con id: " + paciente.getId());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> borrar(@PathVariable Long id) throws ResourceNotFoundException{
        LOGGER.warn("Iniciando la operacion de busqueda - eliminacion del paciente con id: " + id);
        pacienteService.eliminarPaciente(id);
        return ResponseEntity.ok("Se elimino el paciente con id:" + id);
    }

}
