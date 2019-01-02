package com.book.fourPDFView;

import com.lowagie.text.pdf.PdfWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.lowagie.text.Document;
import java.util.Map;

/**
 * Created by admin on 2019/1/2.
 */
public interface PdfExprotService {

    public void make(Map<String,Object> model, Document document, PdfWriter pdfWriter , HttpServletRequest request , HttpServletResponse response );

}
