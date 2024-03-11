/*
 * Copyright (c) 2024 Luis Chumi.
 * Este software está licenciado bajo la Licencia Pública General de GNU versión 3.
 * Puedes encontrar una copia de la licencia en https://www.gnu.org/licenses/gpl-3.0.html.
 *
 * Para consultas o comentarios, puedes contactarme en luischumi.9@gmail.com
 */

package com.cumple.comprobantes.controller;

import com.cumple.comprobantes.model.entity.UsuarioEmpresa;
import com.cumple.comprobantes.services.UsuarioEmpresaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/usuario-empresa")
public class UsuarioEmpresaController {

    private final UsuarioEmpresaService service;
    private static final Logger console = LoggerFactory.getLogger(UsuarioEmpresaController.class);

    @Autowired
    public UsuarioEmpresaController(UsuarioEmpresaService service){
        this.service=service;
    }

    @GetMapping
    public ResponseEntity<List<UsuarioEmpresa>> listar() {
        List<UsuarioEmpresa> listas = service.findByAll();
        return ResponseEntity.ok(listas);
    }

    @PostMapping
    public ResponseEntity<UsuarioEmpresa> crear(@Valid @RequestBody UsuarioEmpresa u){
        try {
            UsuarioEmpresa nuevo = service.save(u);
            console.info("Relacion creada", u.getEmpresa());
            return ResponseEntity.ok(nuevo);
        }catch(Exception e){
            console.error("Error al crear la relacion: {}",e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioEmpresa> actualizar(@PathVariable Long id,@Valid @RequestBody UsuarioEmpresa u){
        try {
            UsuarioEmpresa encontrado = service.findById(id);
            if (encontrado == null){
                return ResponseEntity.notFound().build();
            }
            encontrado.setEmpresa(u.getEmpresa());
            encontrado.setUsuario(u.getUsuario());
            UsuarioEmpresa actualizado=service.save(encontrado);

            console.info("Relacion actualizada",u.getEmpresa() , u.getUsuario());
            return ResponseEntity.ok(actualizado);
        }catch(Exception e){
            console.error("Error al actualizar la relacion",e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id){
        service .delete(id);
        console.warn("Relacion eliminada");
        return ResponseEntity.noContent().build();
    }

}
