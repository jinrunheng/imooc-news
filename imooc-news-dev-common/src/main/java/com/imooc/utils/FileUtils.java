package com.imooc.utils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * 文件工具类
 *
 * @Author Dooby Kim
 * @Date 2022/2/1 7:02 下午
 * @Version 1.0
 */
public class FileUtils {

    /**
     * 文件流下载，在浏览器中展示
     */
    public static void downloadFile(HttpServletResponse response, File file) {
        String filePath = file.getPath();
        System.out.println("filePath = " + filePath);
        // 对encode过的filePath处理
        if (filePath.contains("%")) {
            try {
                filePath = URLDecoder.decode(filePath, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        ServletOutputStream out = null;
        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
            String[] dir = filePath.split("/");
            String fileName = dir[dir.length - 1];
            String[] array = fileName.split("[.]");
            String fileType = array[array.length - 1].toLowerCase();
            // 设置文件ContentType类型
            if ("jpg,jepg,gif,png".contains(fileType)) {    // 判断图片类型
                response.setContentType("image/" + fileType);
            } else if ("pdf".contains(fileType)) {          // 判断pdf类型
                response.setContentType("application/pdf");
            } else {                                        // 设置multipart
                response.setContentType("multipart/form-data");
            }
            out = response.getOutputStream();
            // 读取文件流
            int len = 0;
            byte[] buffer = new byte[1024 * 10];
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 将文件转换为 base64 字符串
     *
     * @param file
     * @return
     */
    public static String fileToBase64(File file) throws UnsupportedEncodingException {
        InputStream in = null;
        byte[] fileData = null;
        // 读取文件字节数组
        try {
            in = new FileInputStream(file);
            fileData = new byte[in.available()];
            in.read(fileData);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 对字节数组 base64 编码并且返回
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] encode = encoder.encode(fileData);
        return new String(encode, StandardCharsets.UTF_8);
    }
}
