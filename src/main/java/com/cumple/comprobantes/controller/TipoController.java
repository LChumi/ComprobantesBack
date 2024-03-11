/*
 * Copyright (c) 2024 Luis Chumi.
 * Este software está licenciado bajo la Licencia Pública General de GNU versión 3.
 * Puedes encontrar una copia de la licencia en https://www.gnu.org/licenses/gpl-3.0.html.
 *
 * Para consultas o comentarios, puedes contactarme en luischumi.9@gmail.com
 */

package com.cumple.comprobantes.controller;

import com.cumple.comprobantes.model.entity.Tipo;
import com.cumple.comprobantes.services.TipoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/tipo")
public class TipoController {

    private final TipoService tipoService;
    private static final Logger console = LoggerFactory.getLogger(TipoController.class);

    @Autowired
    public TipoController (TipoService tipoService){
        this.tipoService= tipoService;
    }

    @GetMapping
    public ResponseEntity<List<Tipo>> listarTipos(){
        List<Tipo> tipos = tipoService.findByAll();
        return ResponseEntity.ok(tipos);
    }

    @PostMapping
    public ResponseEntity<Tipo> crearTipo(@Valid @RequestBody Tipo tipo){
        try {
            Tipo nuevoTipo = tipoService.save(tipo);
            console.info("Tipo creado: {}", tipo.getNombre());
            return ResponseEntity.ok(nuevoTipo);
        }catch(Exception e){
            console.error("Error al crear el tipo:_ {}",e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tipo> actualizarTipo(@PathVariable Long id, @Valid @RequestBody Tipo tipo){
        try {
            Tipo tipoEncontrado = tipoService.findById(id);
            if (tipoEncontrado == null ){
                return ResponseEntity.notFound().build();
            }
            tipoEncontrado.setNombre(tipo.getNombre());
            Tipo tipoActualizado = tipoService.save(tipoEncontrado);

            console.info("Tipo actualizado: {}",tipo.getNombre());
            return ResponseEntity.ok(tipoActualizado);
        }catch (Exception e){
            console.info("Error al actializar el tipo: {}",e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTipo(@PathVariable Long id){
        tipoService.delete(id);
        console.warn("Tipo eliminado: {}",id);
        return ResponseEntity.noContent().build();
    }
}
