/*
 * Copyright (c) 2024 Luis Chumi.
 * Este software está licenciado bajo la Licencia Pública General de GNU versión 3.
 * Puedes encontrar una copia de la licencia en https://www.gnu.org/licenses/gpl-3.0.html.
 *
 * Para consultas o comentarios, puedes contactarme en luischumi.9@gmail.com
 */

package com.cumple.comprobantes.controller;

import com.cumple.comprobantes.model.entity.Usuario;
import com.cumple.comprobantes.services.UsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private static final Logger console = LoggerFactory.getLogger(UsuarioController.class);

    @Autowired
    public UsuarioController(UsuarioService usuarioService){
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listaUsuarios() {
        List<Usuario> usuarios = usuarioService.findByAll();
        return ResponseEntity.ok(usuarios);
    }

    @PostMapping
    public ResponseEntity<Usuario> crearUsuario(@Valid @RequestBody Usuario usuario) {
        try{
            Usuario nuevoUsuario = usuarioService.save(usuario);
            console.info("Usuario creado: {}", usuario.getNombre());
            return ResponseEntity.ok(nuevoUsuario);
        }catch(Exception e){
            console.error("Error al crear el usuario: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable Long id,@Valid @RequestBody Usuario usuario){
        try {
            Usuario usuarioEncontrado = usuarioService.findById(id);
            if (usuarioEncontrado == null){
                return ResponseEntity.notFound().build();
            }
            usuarioEncontrado.setNombre(usuario.getNombre());
            usuarioEncontrado.setClave(usuario.getClave());
            usuarioEncontrado.setId(usuario.getId());
            Usuario usuarioActualizado=usuarioService.save(usuarioEncontrado);

            console.info("Usuario actualizado: {}",usuario.getNombre());
            return ResponseEntity.ok(usuarioActualizado);
        }catch (Exception e){
            console.error("Error al actualizar Usuario: {}",e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        usuarioService.delete(id);
        console.warn("Usuario eliminado: {}",id);
        return ResponseEntity.noContent().build();
    }

}
