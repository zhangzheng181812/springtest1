package com.book.fourPDFView;

import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by admin on 2019/1/2.
 */
public class PdfView extends AbstractPdfView {

    private PdfExprotService pdfExprotService ;

    public PdfView(PdfExprotService pdfExprotService) {
        this.pdfExprotService = pdfExprotService;
    }

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {
        pdfExprotService.make(model,document,writer,request,response);
    }
}
