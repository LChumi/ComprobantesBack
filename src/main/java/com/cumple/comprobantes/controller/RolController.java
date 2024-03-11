/*
 * Copyright (c) 2024 Luis Chumi.
 * Este software está licenciado bajo la Licencia Pública General de GNU versión 3.
 * Puedes encontrar una copia de la licencia en https://www.gnu.org/licenses/gpl-3.0.html.
 *
 * Para consultas o comentarios, puedes contactarme en luischumi.9@gmail.com
 */

package com.cumple.comprobantes.controller;

import com.cumple.comprobantes.model.entity.Rol;
import com.cumple.comprobantes.services.RolService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/rol")
public class RolController {

    private final RolService  rolService;
    private static final Logger console = LoggerFactory.getLogger(RolController.class);

    @Autowired
    public RolController(RolService rolService){
        this.rolService=rolService;
    }

    @GetMapping
    public ResponseEntity<List<Rol>> listarRoles(){
        List<Rol> roles = rolService.findByAll();
        return ResponseEntity.ok(roles);
    }

    @PostMapping
    public ResponseEntity<Rol> crearRol(@Valid @RequestBody Rol rol){
        try{
            Rol nuevoRol = rolService.save(rol);
            console.info("Rol creado: {}",rol.getNombre());
            return ResponseEntity.ok(nuevoRol);
        }catch (Exception e){
            console.error("Error al crear el Rol",e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Rol> actualizarRol(@PathVariable Long id,@Valid @RequestBody Rol rol){
        try {
            Rol rolEncontrado=rolService.findById(id);
            if (rolEncontrado == null ){
                return  ResponseEntity.notFound().build();
            }
            rolEncontrado.setNombre(rol.getNombre());
            rolEncontrado.setId(rol.getId());
            Rol rolActualizado=rolService.save(rolEncontrado);

            console.info("Rol actualizado: {}", rol.getNombre());
            return ResponseEntity.ok(rolActualizado);
        }catch (Exception e){
            console.error("Error al actualizar el rool: {}",e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCErtificado(@PathVariable Long id){
        rolService.delete(id);
        console.warn("Rol Eliminado: {}",id);
        return ResponseEntity.noContent().build();
    }

}
