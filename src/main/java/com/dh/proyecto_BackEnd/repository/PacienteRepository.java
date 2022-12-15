package com.dh.proyecto_BackEnd.repository;

import com.dh.proyecto_BackEnd.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PacienteRepository extends JpaRepository<Paciente,Long> {
    Optional<Paciente> findByEmail(String email);
}
