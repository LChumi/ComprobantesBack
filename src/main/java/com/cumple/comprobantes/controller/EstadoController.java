/*
 * Copyright (c) 2024 Luis Chumi.
 * Este software está licenciado bajo la Licencia Pública General de GNU versión 3.
 * Puedes encontrar una copia de la licencia en https://www.gnu.org/licenses/gpl-3.0.html.
 *
 * Para consultas o comentarios, puedes contactarme en luischumi.9@gmail.com
 */

package com.cumple.comprobantes.controller;

import com.cumple.comprobantes.model.entity.Estado;
import com.cumple.comprobantes.services.EstadoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/estado")
public class EstadoController {

    private final EstadoService estadoService;

    private static final Logger console = LoggerFactory.getLogger(EstadoController.class);

    @Autowired
    public EstadoController(EstadoService estadoService){
        this.estadoService=estadoService;
    }

    @GetMapping
    public ResponseEntity<List<Estado>> listarEstados(){
        List<Estado> estados = estadoService.findByAll();
        return ResponseEntity.ok(estados);
    }

    @PostMapping
    public ResponseEntity<Estado> crearEstado(@Valid @RequestBody Estado estado){
        try {
            Estado nuevoEstado = estadoService.save(estado);
            console.info("Estado creado: {}",estado.getNombre());
            return ResponseEntity.ok(nuevoEstado);
        } catch (Exception e){
            console.error("Error al crear el estado: {}",e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Estado> actualizarEstado(@PathVariable Long id,@Valid @RequestBody Estado estado){
        try {
            Estado estadoEncontrado = estadoService.findById(id);
            if (estadoEncontrado == null){
                return ResponseEntity.notFound().build();
            }
            estadoEncontrado.setNombre(estado.getNombre());
            Estado estadoActualizado= estadoService.save(estadoEncontrado);

            console.info("Estado actualizado: {}",estado.getNombre());
            return ResponseEntity.ok(estadoActualizado);
        }catch (Exception e){
            console.error("Error al actualizar eñ certificado: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEstado(@PathVariable Long id){
        estadoService.delete(id);
        console.warn("Comprobante eliminado: {}",id);
        return ResponseEntity.noContent().build();
    }

}
