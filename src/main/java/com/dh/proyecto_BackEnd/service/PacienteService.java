package com.dh.proyecto_BackEnd.service;

import com.dh.proyecto_BackEnd.entity.Paciente;
import com.dh.proyecto_BackEnd.exceptions.ResourceNotFoundException;
import com.dh.proyecto_BackEnd.repository.PacienteRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {
    private static final Logger LOGGER = Logger.getLogger(PacienteService.class);
    private PacienteRepository pacienteRepository;
    @Autowired
    public PacienteService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    public Paciente guardarPaciente(Paciente paciente){
        return pacienteRepository.save(paciente);
    }

    public Optional<Paciente> buscarPaciente(Long id) throws ResourceNotFoundException{
        Optional<Paciente> pacienteBuscado = pacienteRepository.findById(id);
        if(pacienteBuscado.isPresent()){
            LOGGER.info("Exito en la operación de busqueda del paciente con id: " + id);
            return pacienteBuscado;
        }
        else{
            LOGGER.error("Fallo en la operacion de busqueda del paciente con id: " + id);
            throw new ResourceNotFoundException("No se puede encontrar al paciente con id: " + id + " ya que no se encuentra en la BD");
        }
    }

    public List<Paciente> listarPacientes(){
        return pacienteRepository.findAll();
    }

    public void actualizarPaciente(Paciente paciente) throws ResourceNotFoundException{
        Optional<Paciente> pacienteBuscado = buscarPaciente(paciente.getId());
        pacienteRepository.save(paciente);
        LOGGER.info("Exito en la operación de actualizacion del paciente con id: " + paciente.getId());
    }

    public void eliminarPaciente(Long id) throws ResourceNotFoundException{
        Optional<Paciente> pacienteBuscado = buscarPaciente(id);
        pacienteRepository.deleteById(id);
        LOGGER.info("Exito en la operacion de eliminacion del paciente con id: " + id);
    }

    public Optional<Paciente> buscarPacientePorString(String email) throws ResourceNotFoundException{
        Optional<Paciente> pacienteBuscado = pacienteRepository.findByEmail(email);
        if(pacienteBuscado.isPresent()){
            LOGGER.info("Exito en la operación de busqueda del paciente con email: " + email);
            return pacienteBuscado;
        }
        else{
            LOGGER.error("Fallo en la operacion de busqueda del paciente con email: " + email);
            throw new ResourceNotFoundException("No se puede encontrar al paciente con email: " + email + " ya que no se encuentra en la BD");
        }
    }

}
