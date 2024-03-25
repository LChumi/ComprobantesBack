/*
 * Copyright (c) 2024 Luis Chumi.
 * Este software está licenciado bajo la Licencia Pública General de GNU versión 3.
 * Puedes encontrar una copia de la licencia en https://www.gnu.org/licenses/gpl-3.0.html.
 *
 * Para consultas o comentarios, puedes contactarme en luischumi.9@gmail.com
 */

package com.cumple.comprobantes.controller;

import com.cumple.comprobantes.model.entity.Comprobante;
import com.cumple.comprobantes.services.ComprobanteService;
import com.cumple.comprobantes.services.PdfService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/comprobantes")
@Slf4j
public class ComprobanteController {

    private final ComprobanteService comprobanteService;
    private final PdfService pdfService;


    @Autowired
    public ComprobanteController(ComprobanteService comprobanteService, PdfService pdfService){
        this.comprobanteService=comprobanteService;
        this.pdfService=pdfService;
    }

    @GetMapping
    public ResponseEntity<List<Comprobante>> listarComprobantes(){
        List<Comprobante> comprobantes= comprobanteService.findByAll();
        return ResponseEntity.ok(comprobantes);
    }

    @PostMapping
    public ResponseEntity<Comprobante> crearComprobante(@Valid @RequestBody Comprobante comprobante){
        try{
            Comprobante nuevoComprobante = comprobanteService.save(comprobante);
            log.info("Comprobante creado: {}",comprobante.getNroComprobante());
            return ResponseEntity.ok(nuevoComprobante);
        }catch (Exception e){
            log.error("Error al crear el comprobante",e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Comprobante> actualizarComprobante(@PathVariable Long id , @Valid @RequestBody Comprobante comprobante){
        try{
            Comprobante comprobanteEncontrado= comprobanteService.findById(id);
            if (comprobanteEncontrado == null){
                return ResponseEntity.notFound().build();
            }
            comprobanteEncontrado.setNroComprobante(comprobante.getNroComprobante());
            comprobanteEncontrado.setClaveAcceso(comprobante.getClaveAcceso());
            comprobanteEncontrado.setFechaComprobante(comprobante.getFechaComprobante());
            comprobanteEncontrado.setFechaAutorizacion(comprobante.getFechaAutorizacion());
            comprobanteEncontrado.setCliente(comprobante.getCliente());
            comprobanteEncontrado.setTotal(comprobante.getTotal());
            comprobanteEncontrado.setEmail(comprobante.getEmail());
            comprobanteEncontrado.setAlmacen(comprobante.getAlmacen());
            comprobanteEncontrado.setPVenta(comprobante.getPVenta());
            comprobanteEncontrado.setNumero(comprobante.getNumero());

            Comprobante comprobanteActualizado=comprobanteService.save(comprobanteEncontrado);
            log.info("Comporbante actualizado: {}",comprobanteActualizado.getNroComprobante());
            return ResponseEntity.ok(comprobanteActualizado);
        }catch (Exception e){
            log.error("Error al actualizar el certificado; {}",e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarComprobante(@PathVariable Long id){
        comprobanteService.delete(id);
        log.warn("Comprobante eliminado: {}", id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/pdf/{id}")
    public void descargarPdf(HttpServletResponse response,@PathVariable Long id){
        try{
            Path file = Paths.get(pdfService.generarteFilePdf(id).getAbsolutePath());
            if (Files.exists(file)){
                response.setContentType("application/pdf");
                response.addHeader("Content-Disposition","attachment; filename"+file.getFileName());
                Files.copy(file,response.getOutputStream());
                response.getOutputStream().flush();
            }
        } catch (Exception e) {
            log.error("Error al descargar Pdf: {}",e.getMessage());
        }
    }

}
