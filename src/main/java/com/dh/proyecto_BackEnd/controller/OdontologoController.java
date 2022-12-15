package com.dh.proyecto_BackEnd.controller;

import com.dh.proyecto_BackEnd.entity.Odontologo;
import com.dh.proyecto_BackEnd.exceptions.ResourceNotFoundException;
import com.dh.proyecto_BackEnd.service.OdontologoService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/odontologos")
public class OdontologoController {
    private static final Logger LOGGER = Logger.getLogger(OdontologoController.class);
    private OdontologoService odontologoService;

    @Autowired
    public OdontologoController(OdontologoService odontologoService) {
        this.odontologoService = odontologoService;
    }

    @PostMapping
    public ResponseEntity<Odontologo> guardar(@RequestBody Odontologo odontologo){
        LOGGER.info("Iniciando una operación de guardado de odontologo con nombre: " + odontologo.getNombre());
        return ResponseEntity.ok(odontologoService.guardarOdontologo(odontologo));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Odontologo> buscar(@PathVariable Long id) throws ResourceNotFoundException {
        LOGGER.info("Iniciando una operación de busqueda de odontologo con id: "+ id);
        Optional<Odontologo> odontologoBuscado = odontologoService.buscarOdontologo(id);
        return ResponseEntity.ok(odontologoBuscado.get());
    }

    @GetMapping
    public ResponseEntity<List<Odontologo>> listar() {
        LOGGER.info("Iniciando la operación de listar todos los odontologos");
                    return ResponseEntity.ok(odontologoService.listarOdontologos());
    }

    @PutMapping
    public ResponseEntity<String> actualizar(@RequestBody Odontologo odontologo) throws ResourceNotFoundException{
        LOGGER.info("Iniciando una operacion de busqueda - actualizacion de un odontologo con id:" + odontologo.getId());
        odontologoService.actualizarOdontologo(odontologo);
        return ResponseEntity.ok("Se actualizó el odontologo con id: " + odontologo.getId());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> borrar(@PathVariable Long id) throws ResourceNotFoundException{
        LOGGER.warn("Iniciando la operacion de busqueda - eliminacion del odontologo con id: " + id);
        odontologoService.eliminarOdontologo(id);
        return ResponseEntity.ok("Se elimino el odontologo con id:" + id);
    }

}
/*
guardad
buscaruno
listar
actualizar
eliminar
 */