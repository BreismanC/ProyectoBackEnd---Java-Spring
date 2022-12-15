package com.dh.proyecto_BackEnd.service;

import com.dh.proyecto_BackEnd.dto.TurnoDTO;
import com.dh.proyecto_BackEnd.entity.Odontologo;
import com.dh.proyecto_BackEnd.entity.Paciente;
import com.dh.proyecto_BackEnd.entity.Turno;
import com.dh.proyecto_BackEnd.exceptions.ResourceNotFoundException;
import com.dh.proyecto_BackEnd.repository.TurnoRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TurnoService {
    private static final Logger LOGGER = Logger.getLogger(TurnoService.class);
    private TurnoRepository turnoRepository;
    @Autowired
    public TurnoService(TurnoRepository turnoRepository) {
        this.turnoRepository = turnoRepository;
    }

    public TurnoDTO guardarTurno(TurnoDTO turnoDTO){
        Turno turnoGuardado = turnoRepository.save(turnoDTOATurno(turnoDTO));
        return turnoATurnoDTO(turnoGuardado);
    }

    public Optional<TurnoDTO> buscarTurno(Long id) throws ResourceNotFoundException {
        Optional<Turno> turnoBuscado = turnoRepository.findById(id);
        if(turnoBuscado.isPresent()){
            LOGGER.info("Exito en la operación de busqueda del turno con id: " + id);
            return Optional.of(turnoATurnoDTO(turnoBuscado.get()));
        }
        else{
            LOGGER.error("Fallo en la operacion de busqueda del turno con id: " + id);
            throw new ResourceNotFoundException("No se puede encontrar al turno con id: " + id + " ya que no se encuentra en la BD");
        }
    }

    public List<TurnoDTO> listarTurnos(){
        List<Turno> turnosBuscados=turnoRepository.findAll();
        List<TurnoDTO> respuesta = new ArrayList<>();
        //Recorremos la lista para ir convirtiendo cada elemento a un turnoDTO
        for (Turno turno : turnosBuscados) {
            respuesta.add(turnoATurnoDTO(turno));
        }
        return respuesta;
    }

    public void actualizarTurno(TurnoDTO turnoDTO) throws ResourceNotFoundException{
        Optional<TurnoDTO> turnoBuscado = buscarTurno(turnoDTO.getId());
        turnoRepository.save(turnoDTOATurno(turnoDTO));
        LOGGER.info("Exito en la operación de actualizacion del turno con id: " + turnoDTO.getId());
    }

    public void eliminarTurno(Long id) throws ResourceNotFoundException{
        Optional<TurnoDTO> turnoBuscado = buscarTurno(id);
        turnoRepository.deleteById(id);
        LOGGER.info("Exito en la operacion de eliminacion del turno con id: " + id);
    }

    //Convertir un turnoDTO a turno
    private Turno turnoDTOATurno(TurnoDTO turnoDTO){
        Turno respuesta = new Turno();
        Odontologo odontologo = new Odontologo();
        Paciente paciente = new Paciente();
        //Cargar la informacion del turnoDTO al turno
        respuesta.setId(turnoDTO.getId());
        odontologo.setId(turnoDTO.getOdontologo_id());
        paciente.setId(turnoDTO.getPaciente_id());
        respuesta.setOdontologo(odontologo);
        respuesta.setPaciente(paciente);
        respuesta.setFecha(turnoDTO.getFecha());
        return respuesta;
    }

    //Convertir un turno a turnoDTO
    private TurnoDTO turnoATurnoDTO(Turno turno){
        TurnoDTO respuesta = new TurnoDTO();
        respuesta.setId(turno.getId());
        respuesta.setPaciente_id(turno.getPaciente().getId());
        respuesta.setOdontologo_id(turno.getOdontologo().getId());
        respuesta.setFecha(turno.getFecha());
        return respuesta;
    }
}
