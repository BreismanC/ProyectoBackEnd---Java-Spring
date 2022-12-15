package com.dh.proyecto_BackEnd.security;

import com.dh.proyecto_BackEnd.entity.Usuario;
import com.dh.proyecto_BackEnd.entity.UsuarioRole;
import com.dh.proyecto_BackEnd.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DatosInicialesUsuarios implements ApplicationRunner {
    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        BCryptPasswordEncoder cifrador = new BCryptPasswordEncoder();
        String passSinCifrar = "digital";
        String passCifrada = cifrador.encode(passSinCifrar);
        Usuario usuarioAInsertar = new Usuario("Breisman", "breisman","bchalaca@gmail.com", passCifrada, UsuarioRole.ROLE_USER);
        usuarioRepository.save(usuarioAInsertar);
        //Usuario tipo ADMIN
        String passCifrada2 = cifrador.encode("admin");
        usuarioAInsertar = new Usuario("Admin", "admin", "admin@gmail.com", passCifrada2, UsuarioRole.ROLE_ADMIN);
        usuarioRepository.save(usuarioAInsertar);
    }
}
