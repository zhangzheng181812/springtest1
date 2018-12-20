
package com.zz;

import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

@RestController
@RequestMapping({"/file"})
public class TestUploadController {

    @RequestMapping("/testUpload")
    public void testAsync(MultipartFile pic, HttpServletRequest request) throws IOException {
        File file = new File(request.getSession().getServletContext().getRealPath("/tmp"),pic.getOriginalFilename());
        if (!file.exists()) {
            file.getParentFile().mkdirs();
        }
        System.out.println(file.getAbsoluteFile());
        System.out.println(file.getAbsolutePath());
        System.out.println(file.getParent());
        System.out.println(file.getCanonicalPath());
        System.out.println(file.isAbsolute());
        System.out.println(file.getPath());
////        pic.transferTo(file);
//        System.out.println(pic);
//        URL url = ResourceUtils.getURL("classpath:static/123.txt");
//        System.out.println(url);
//        File url1 = ResourceUtils.getFile("classpath:static/123.txt");
//        System.out.println(url1);
        File targetFile = new File("/home/zihexin/servers/pics/merchantimg/", "123.txt");
        if (!targetFile.isDirectory()) {
            File f = new File("/home/zihexin/servers/pics/merchantimg/");
            f.mkdirs();
        } else {
            targetFile.mkdirs();
        }
        System.out.println(targetFile.getPath());
        System.out.println(targetFile.getName());
        System.out.println(targetFile.getPath()+System.getProperty("file.separator")+"123.txt");
        FileOutputStream out = new FileOutputStream(targetFile.getPath());
            out.write(pic.getBytes());
            out.flush();
            out.close();
    }
}

