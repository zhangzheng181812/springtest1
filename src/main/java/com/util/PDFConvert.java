package com.util;

import org.jodconverter.OfficeDocumentConverter;
import org.jodconverter.office.DefaultOfficeManagerBuilder;
import org.jodconverter.office.OfficeException;
import org.jodconverter.office.OfficeManager;

import java.io.File;
import java.util.regex.Pattern;

public class PDFConvert {
    private static String officeHomeDir = "C:\\Program Files\\LibreOffice";

    public static void main(String[] args) {
        try {
            System.out.println(doDocToFdpLibre("C:\\Users\\Administrator\\Desktop\\机器密码.txt","C:\\Users\\Administrator\\Desktop\\4.pdf"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     *
     *@name 文档转换为pdf工具类
     *@description 相关说明 支持：xls，xlsx，ppt，pptx，txt，其中doc，docx转换与原文件有较大差异,libreOffice 默认安装路径
     *Linux：/opt/libreoffice6.0
     *Windows：C:/Program Files (x86)/LibreOffice
     *Mac：/Application/openOfficeSoft
     *@time 创建时间:2018年9月17日下午1:49:18
     *@param sourceFile 需要转换的原文件
     *@param tarPdfFile 转换后的目标pdf文件
     *@return
     *@author myflea@163.com
     *@history 修订历史（历次修订内容、修订人、修订时间等）
     */
    public static String doDocToFdpLibre(String sourceFile, String tarPdfFile) throws Exception {

        File inputFile = new File(sourceFile);

        String libreOfficePath = getOfficeHome();

        DefaultOfficeManagerBuilder builder = new DefaultOfficeManagerBuilder();
        builder.setOfficeHome(new File(libreOfficePath));
        // 端口号
        builder.setPortNumber(8100);
        builder.setTaskExecutionTimeout(1000 * 60 * 5L);
        // 设置任务执行超时为5分钟
        builder.setTaskQueueTimeout(1000 * 60 * 60 * 24L);
        // 设置任务队列超时为24小时

        OfficeManager officeManager = builder.build();
        startService(officeManager);
        OfficeDocumentConverter converter = new OfficeDocumentConverter(officeManager);
        File outputFile = new File(tarPdfFile);
        converter.convert(inputFile, outputFile);
        stopService(officeManager);
        String pdfPath = outputFile.getPath();
        return pdfPath;
    }

    private static String getOfficeHome() {

        if (null != officeHomeDir) {
            return officeHomeDir;
        } else {
            String osName = System.getProperty("os.name");
            if (Pattern.matches("Windows.*", osName)) {
                officeHomeDir = "C:/Program Files (x86)/LibreOffice";
                return officeHomeDir;
            } else if (Pattern.matches("Linux.*", osName)) {
                officeHomeDir = "/opt/libreoffice6.0";
                return officeHomeDir;
            } else if (Pattern.matches("Mac.*", osName)) {
                officeHomeDir = "/Application/openOfficeSoft";
                return officeHomeDir;
            }
            return null;
        }

    }

    private static void stopService(OfficeManager officeManager) throws OfficeException {
        if (null != officeManager) {
            officeManager.stop();
        }
    }

    private static void startService(OfficeManager officeManager) {

        try {
            // 准备启动服务
            officeManager.start(); // 启动服务
        } catch (Exception ce) {
        }
    }

    /**
     *
     *@name 设置libreOffice安装目录
     *@description 相关说明：如果libreOffice安装目录为默认目录，则不需要设置，否则需要设置
     *@time 创建时间:2018年9月17日下午1:52:36
     *@param officeHome
     *@author 作者
     *@history 修订历史（历次修订内容、修订人、修订时间等）
     */
    public static void setOfficeHome(String officeHome) {
        officeHomeDir = officeHome;
    }
}
