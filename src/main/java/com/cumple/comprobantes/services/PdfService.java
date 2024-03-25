/*
 * Copyright (c) 2024 Luis Chumi.
 * Este software está licenciado bajo la Licencia Pública General de GNU versión 3.
 * Puedes encontrar una copia de la licencia en https://www.gnu.org/licenses/gpl-3.0.html.
 *
 * Para consultas o comentarios, puedes contactarme en luischumi.9@gmail.com
 */

package com.cumple.comprobantes.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.naming.Context;
import java.io.File;

@Service
public class PdfService {

    private static final String PDF_RESOURCES = "/pdf_resources/";

    private final SpringTemplateEngine springTemplateEngine;
    //servicio de donde esta la factura private FacturaService facService

    @Autowired
    PdfService(SpringTemplateEngine springTemplateEngine){
        this.springTemplateEngine=springTemplateEngine;
    }

    public File generarteFilePdf(){
        Context context = getContextFacturaPdf();
        String html = loadAndFillTemplate(context);
        String xhtml = convertToXhml(html);
        return renderFacturaPdf(xhtml);
    }
}
