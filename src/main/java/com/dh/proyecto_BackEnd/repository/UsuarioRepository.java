package com.dh.proyecto_BackEnd.repository;

import com.dh.proyecto_BackEnd.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String correo);
}
