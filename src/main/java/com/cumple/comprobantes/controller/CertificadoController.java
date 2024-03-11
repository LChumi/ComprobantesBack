/*
 * Copyright (c) 2024 Luis Chumi.
 * Este software está licenciado bajo la Licencia Pública General de GNU versión 3.
 * Puedes encontrar una copia de la licencia en https://www.gnu.org/licenses/gpl-3.0.html.
 *
 * Para consultas o comentarios, puedes contactarme en luischumi.9@gmail.com
 */

package com.cumple.comprobantes.controller;

import com.cumple.comprobantes.model.entity.Certificado;
import com.cumple.comprobantes.services.CertificadoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/certificados")
public class CertificadoController {

    private final CertificadoService certificadoService;
    private static final Logger logger = LoggerFactory.getLogger(CertificadoController.class);

    @Autowired
    public CertificadoController(CertificadoService certificadoService) {
        this.certificadoService = certificadoService;
    }

    @GetMapping
    public ResponseEntity<List<Certificado>> listarCertificados() {
        List<Certificado> certificados = certificadoService.findByAll();
        return ResponseEntity.ok(certificados);
    }

    @PostMapping
    public ResponseEntity<Certificado> crearCertificado(@Valid @RequestBody Certificado certificado) {
        try {
            Certificado nuevoCertificado = certificadoService.save(certificado);
            logger.info("Certificado creado: {}", certificado.getNombre());
            return ResponseEntity.ok(nuevoCertificado);
        } catch (Exception e) {
            logger.error("Error al crear el certificado: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Certificado> actualizarCertificado(@PathVariable Long id, @Valid @RequestBody Certificado certificado) {
        try {
            Certificado certificadoEncontrado = certificadoService.findById(id);
            if (certificadoEncontrado == null) {
                return ResponseEntity.notFound().build();
            }
            certificadoEncontrado.setEmpresa(certificado.getEmpresa());
            certificadoEncontrado.setNombre(certificado.getNombre());
            certificadoEncontrado.setClave(certificado.getClave());
            certificadoEncontrado.setFechaFin(certificado.getFechaFin());
            certificadoEncontrado.setFechaInicio(certificado.getFechaInicio());
            Certificado certificadoActualizado = certificadoService.save(certificadoEncontrado);

            logger.info("Certificado actualizado: {}", certificado.getNombre());
            return ResponseEntity.ok(certificadoActualizado);
        } catch (Exception e) {
            logger.error("Error al actualizar el certificado: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCertificado(@PathVariable Long id) {
        certificadoService.delete(id);
        logger.warn("Certificado eliminado: {}", id);
        return ResponseEntity.noContent().build();
    }

}
