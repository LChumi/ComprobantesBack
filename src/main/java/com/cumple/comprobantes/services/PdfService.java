/*
 * Copyright (c) 2024 Luis Chumi.
 * Este software está licenciado bajo la Licencia Pública General de GNU versión 3.
 * Puedes encontrar una copia de la licencia en https://www.gnu.org/licenses/gpl-3.0.html.
 *
 * Para consultas o comentarios, puedes contactarme en luischumi.9@gmail.com
 */

package com.cumple.comprobantes.services;

import com.cumple.comprobantes.model.entity.Comprobante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.w3c.dom.Document;
import org.w3c.tidy.Tidy;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;

@Service
public class PdfService {

    private static final String PDF_RESOURCES = "/pdf-resources/";

    private final SpringTemplateEngine springTemplateEngine;
    private final ComprobanteService comprobanteService;

    @Autowired
    PdfService(SpringTemplateEngine springTemplateEngine, ComprobanteService comprobanteService){
        this.springTemplateEngine=springTemplateEngine;
        this.comprobanteService= comprobanteService;
    }

    public File generarteFilePdf(Long id) throws Exception{
        System.out.println("llega" + id);
        Context context = getContextFacturaPdf(id);
        String html = loadAndFillTemplate(context);
        String xhtml = convertToXhml(html);
        return renderFacturaPdf(xhtml);
    }

    private String convertToXhml(String html) throws UnsupportedEncodingException {
        System.out.println("llega 2{}"+html);
        Tidy tidy = new Tidy();
        tidy.setXHTML(true);
        tidy.setIndentContent(true);
        tidy.setPrintBodyOnly(true);
        tidy.setInputEncoding("UTF-8");
        tidy.setOutputEncoding("UTF-8");
        tidy.setSmartIndent(true);
        tidy.setShowWarnings(false);
        tidy.setQuiet(true);
        tidy.setTidyMark(false);

        Document htmlDom = tidy.parseDOM(new ByteArrayInputStream(html.getBytes()),null);

        OutputStream out = new ByteArrayOutputStream();
        tidy.pprint(htmlDom,out);
        return out.toString();
    }

    private File renderFacturaPdf(String html) throws Exception{
        System.out.println("llega 3 "+ html);
        File file = File.createTempFile("factura", ".pdf");
        OutputStream outputStream = new FileOutputStream(file);
        ITextRenderer renderer = new ITextRenderer(20f * 4f / 3f, 20);
        renderer.setDocumentFromString(html, new ClassPathResource(PDF_RESOURCES).getURL().toExternalForm());
        renderer.layout();
        renderer.createPDF(outputStream);
        outputStream.close();
        file.deleteOnExit();
        return file;
    }

    private Context getContextFacturaPdf(Long id){
        System.out.println("llega 4 "+id);
        Comprobante comprobante = this.comprobanteService.findById(id);
        String factura=comprobante.getCaracter();
        Context context = new Context();
        context.setVariable("factura",factura);
        return context;
    }

    private String loadAndFillTemplate(Context context){
        System.out.println("llega 5 " + context.toString());
        return springTemplateEngine.process("facturaPDF",context); //-> esto hace referencia a la plantilla de templates en el directorio resources
    }
}
