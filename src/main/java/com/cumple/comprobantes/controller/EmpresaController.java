/*
 * Copyright (c) 2024 Luis Chumi.
 * Este software está licenciado bajo la Licencia Pública General de GNU versión 3.
 * Puedes encontrar una copia de la licencia en https://www.gnu.org/licenses/gpl-3.0.html.
 *
 * Para consultas o comentarios, puedes contactarme en luischumi.9@gmail.com
 */

package com.cumple.comprobantes.controller;

import com.cumple.comprobantes.model.entity.Empresa;
import com.cumple.comprobantes.services.EmpresaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/empresa")
public class EmpresaController {

    private final EmpresaService empresaService;
    private final static Logger console= LoggerFactory.getLogger(EmpresaController.class);

    @Autowired
    public EmpresaController(EmpresaService empresaService){
        this.empresaService=empresaService;
    }

    @GetMapping
    public ResponseEntity<List<Empresa>> listaEmpresas(){
        List<Empresa> empresas = empresaService.findByAll();
        return ResponseEntity.ok(empresas);
    }

    @PostMapping
    public ResponseEntity<Empresa> crearEmpresa(@Valid @RequestBody Empresa empresa){
        try{
            Empresa nuevaEmpresa= empresaService.save(empresa);
            console.info("Empresa Creada:{}",empresa.getNombre());
            return ResponseEntity.ok(nuevaEmpresa);
        }catch (Exception e){
            console.error("Error al crear el certificado: {}",e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Empresa> actualizarEmpresa(@PathVariable Long id, @Valid @RequestBody Empresa empresa){
       try{
           Empresa empresaEncontrada= empresaService.findById(id);
           if (empresaEncontrada ==null){
               return ResponseEntity.notFound().build();
           }
           empresaEncontrada.setRuc(empresa.getRuc());
           empresaEncontrada.setNombre(empresa.getNombre());
           empresaEncontrada.setEmp_ruta_xml(empresa.getEmp_ruta_xml());
           empresa.setRutaCertificado(empresa.getRutaCertificado());
           empresaEncontrada.setRutaXmlFirmado(empresa.getRutaXmlFirmado());
           empresaEncontrada.setRutaRespuesta(empresa.getRutaRespuesta());
           Empresa empresaActualizada = empresaService.save(empresaEncontrada);

           console.warn("Certificado actualizado: {}",empresaActualizada.getNombre());
           return ResponseEntity.ok(empresaActualizada);
       }catch (Exception e){
           console.error("Error al actualizar la empresa: {}",e.getMessage());
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
       }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> elmiminarEmpresa(@PathVariable Long id){
        empresaService.delete(id);
        console.warn("Empresa eliminada: {}",id);
        return ResponseEntity.noContent().build();
    }

}
