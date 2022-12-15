package com.dh.proyecto_BackEnd.service;
import com.dh.proyecto_BackEnd.entity.Odontologo;
import com.dh.proyecto_BackEnd.exceptions.ResourceNotFoundException;
import com.dh.proyecto_BackEnd.repository.OdontologoRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OdontologoService {
    private static final Logger LOGGER = Logger.getLogger(OdontologoService.class);
    private OdontologoRepository odontologoRepository;

    @Autowired
    public OdontologoService(OdontologoRepository odontologoRepository) {
        this.odontologoRepository = odontologoRepository;
    }

    public Odontologo guardarOdontologo(Odontologo odontologo){
        return odontologoRepository.save(odontologo);
    }

    public Optional<Odontologo> buscarOdontologo(Long id) throws ResourceNotFoundException{
        Optional<Odontologo> odontologoBuscado = odontologoRepository.findById(id);
        if(odontologoBuscado.isPresent()){
            LOGGER.info("Exito en la operación de busqueda del odontologo con id: " + id);
            return odontologoBuscado;
        }
        else{
            LOGGER.error("Fallo en la operacion de busqueda del odontologo con id: " + id);
            throw new ResourceNotFoundException("No se puede encontrar al odontologo con id: " + id + " ya que no se encuentra en la BD");
        }
    }

    public List<Odontologo> listarOdontologos(){
        return odontologoRepository.findAll();
    }

    public void actualizarOdontologo(Odontologo odontologo) throws ResourceNotFoundException{
        Optional<Odontologo> odontologoBuscado = buscarOdontologo(odontologo.getId());
        odontologoRepository.save(odontologo);
        LOGGER.info("Exito en la operación de actualizacion del odontologo con id: " + odontologo.getId());
    }

    public void eliminarOdontologo(Long id) throws ResourceNotFoundException{
        Optional<Odontologo> odontologoBuscado = buscarOdontologo(id);
        LOGGER.info("Exito en la operacion de eliminacion del odontologo con id: " + id);
        odontologoRepository.deleteById(id);
    }
}
