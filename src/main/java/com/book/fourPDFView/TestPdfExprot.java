package com.book.fourPDFView;

import com.entity.Code;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.service.CodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.awt.*;
import java.util.List;

/**
 * Created by admin on 2019/1/2.
 */
@Controller
@RequestMapping("/pdf")
public class TestPdfExprot {
    @Autowired
    private CodeService codeService ;

    @RequestMapping
    public ModelAndView exportPdf(){
        List<Code> all = codeService.findAll();
        PdfView pdfView = new PdfView(exprotService());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setView(pdfView);
        modelAndView.addObject("codeList",all);
        return modelAndView;
    }

    @SuppressWarnings("ubchecked")
    private PdfExprotService exprotService(){
        return (model,document,writer,request,respose) -> {
            try{
                //A4
                document.setPageSize(PageSize.A4);
                //标题
                document.addTitle("test");
                //换行
                document.add(new Chunk("\n"));
                //表格
                PdfPTable table = new PdfPTable(2);
                //单元格
                PdfPCell cell = null;
                //字体
                Font f8 = new Font();
                f8.setColor(Color.blue);
                f8.setStyle(Font.BOLD);
                //标题
                cell = new PdfPCell(new Paragraph("id",f8));
                //股中对其
                cell.setHorizontalAlignment(1);
                //将单元格加入表格
                table.addCell(cell);
                cell = new PdfPCell(new Paragraph("code",f8));
                //居中对齐
                cell.setHorizontalAlignment(1);
                table.addCell(cell);

                //拼接数据
                List<Code> codeList = (List<Code>) model.get("codeList");
                for (Code code :codeList ) {
                    document.add(new Chunk("\n"));
                    cell = new PdfPCell(new Paragraph(code.getId()+""));
                    table.addCell(cell);
                    cell = new PdfPCell(new Paragraph(code.getCode()));
                    table.addCell(cell);
                }
                document.add(table);
                //在文档中加入表格
            }catch (DocumentException e ){
                e.printStackTrace();
            }
        };
    }
}
