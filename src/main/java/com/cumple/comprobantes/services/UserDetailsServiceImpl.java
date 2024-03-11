/*
 * Copyright (c) 2024 Luis Chumi.
 * Este software está licenciado bajo la Licencia Pública General de GNU versión 3.
 * Puedes encontrar una copia de la licencia en https://www.gnu.org/licenses/gpl-3.0.html.
 *
 * Para consultas o comentarios, puedes contactarme en luischumi.9@gmail.com
 */

package com.cumple.comprobantes.services;

import com.cumple.comprobantes.model.entity.Usuario;
import com.cumple.comprobantes.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private  UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByNombre(username);

        if (usuario == null ){
            throw  new UsernameNotFoundException(username);
        }
        System.out.println(User.withUsername(username).password(usuario.getClave()).authorities("ROLE_"+usuario.getRol().getNombre()).build());
        return User
                .withUsername(username)
                .password(usuario.getClave())
                .authorities("ROLE_"+usuario.getRol().getNombre())
                .build();
    }
}
