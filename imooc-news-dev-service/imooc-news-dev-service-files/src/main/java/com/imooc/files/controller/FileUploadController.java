package com.imooc.files.controller;

import com.imooc.api.controller.files.FileUploadControllerApi;
import com.imooc.bo.AddNewAdminBO;
import com.imooc.enums.ResponseStatus;
import com.imooc.exception.MyCustomException;
import com.imooc.files.service.FileUploadService;
import com.imooc.result.JsonResult;
import com.imooc.utils.FileUtils;
import com.imooc.utils.ImageReviewUtils;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSFindIterable;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.mongodb.client.model.Filters;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 * @Author Dooby Kim
 * @Date 2022/1/1 10:55 下午
 * @Version 1.0
 */
@RestController
@Slf4j
public class FileUploadController implements FileUploadControllerApi {

    @Resource
    private FileUploadService fileUploadService;

    @Resource
    private ImageReviewUtils imageReviewUtils;

    // 目前"审核失败"这张图片的 URL 地址是写死的
    // private static final String FAIL_IMAGE_URL = "https://tva1.sinaimg.cn/large/008i3skNgy1gxzqinm2vpj30ku0msgn0.jpg";

    @Resource
    private GridFSBucket gridFSBucket;

    /**
     * 上传头像
     * <p>
     * 逻辑流程：
     * 1. 如果文件为空或文件名为空，则直接返回异常响应
     * 2. 否则：
     * ---- 1. 获取文件名称，判断文件格式是否符合规范（png,jpg,jpeg）,如果不符合规范则直接返回异常响应
     * ---- 否则：
     * -------- 1. 调用阿里云上传服务，上传文件成功后，获得文件访问的完整路径
     * ---------------- 1. 如果获得文件路径为空，则说明文件上传失败，返回异常响应
     * -------- 2. 调用阿里云内容安全服务，对图片进行审核
     * ---------------- 1. 如果图片审核通过，则将图片地址一同响应回前端
     * ---------------- 2. 如果图片审核不通过，则将审核失败的图片地址响应回前端
     * <p>
     * <p>
     * 2021-1-2：
     * 目前，内容安全服务并未开通，图片审核环节取消。
     *
     * @param userId
     * @param file
     * @return
     */
    @Override
    public JsonResult uploadFace(String userId, MultipartFile file) {
        if (file.isEmpty() || StringUtils.isBlank(file.getOriginalFilename())) {
            return new JsonResult(ResponseStatus.FILE_UPLOAD_EMPTY_ERROR);
        }
        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf('.') + 1);
        if (!suffix.equalsIgnoreCase("png")
                && !suffix.equalsIgnoreCase("jpg")
                && !suffix.equalsIgnoreCase("jpeg")) {
            return new JsonResult(ResponseStatus.FILE_FORMAT_ERROR);
        }

        String path = fileUploadService.upload(file, userId, suffix);

        if (StringUtils.isBlank(path)) {
            return new JsonResult(ResponseStatus.FILE_UPLOAD_FAIL_ERROR);
        }
        log.info("File Path : {}", path);

//        if (imageReviewUtils.reviewImage(path)) {
//            return new JsonResult(ResponseStatus.SUCCESS, path);
//        } else {
//            return new JsonResult(ResponseStatus.SUCCESS, FAIL_IMAGE_URL);
//        }
        return new JsonResult(ResponseStatus.SUCCESS, path);
    }

    /**
     * 上传多个文件
     * <p>
     * 逻辑流程：
     * 1. 声明一个 List，用于存放多个图片的路径，返回至前端
     * 2. 遍历 files 数组，对于没一个每一个 file 上传逻辑同 uploadFace
     *
     * @param userId
     * @param files
     * @return
     */
    @Override
    public JsonResult uploadSomeFiles(String userId, MultipartFile[] files) {

        List<String> imageUrlList = new ArrayList<>();
        if (files != null && files.length > 0) {
            for (MultipartFile file : files) {
                if (file.isEmpty() || StringUtils.isBlank(file.getOriginalFilename())) {
                    return new JsonResult(ResponseStatus.FILE_UPLOAD_EMPTY_ERROR);
                }
                String originalFilename = file.getOriginalFilename();
                String suffix = originalFilename.substring(originalFilename.lastIndexOf('.') + 1);
                if (!suffix.equalsIgnoreCase("png")
                        && !suffix.equalsIgnoreCase("jpg")
                        && !suffix.equalsIgnoreCase("jpeg")) {
                    continue;
                }

                String path = fileUploadService.upload(file, userId, suffix);

                if (StringUtils.isBlank(path)) {
                    continue;
                }
                log.info("File Path : {}", path);
                // FIXME: 放入到 imageURLList 前需要对图片进行审核
                imageUrlList.add(path);
            }
        }

        return new JsonResult(ResponseStatus.SUCCESS, imageUrlList);
    }

    /**
     * 上传人脸数据到 MongoDB 的 GridFS，实现人脸入库
     * 实现流程：
     * 1. 获取图片的 base64 字符串
     * 2. 将 base64 字符串转换为 byte 数组
     * 3. 将 byte 数组转换为输入流
     * 4. 上传至 GridFS 中，并获取文件在 GridFS 中的主键 ID
     * 5. 将主键 ID 返回给前端
     *
     * @param newAdminBO
     * @return
     */
    @Override
    public JsonResult uploadToGridFS(AddNewAdminBO newAdminBO) {
        // 获取图片的 base64 字符串
        String img64 = newAdminBO.getImg64();
        // 将 base64 字符串转换为 byte 数组
        byte[] bytes = Base64.getDecoder().decode(img64.trim());
        // 将 byte 数组转换为输入流
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        // 上传至 GridFS 中，并获取文件在 GridFS 中的主键 ID
        ObjectId fileId = gridFSBucket.uploadFromStream(newAdminBO.getAdminName() + ".png", byteArrayInputStream);

        return new JsonResult(ResponseStatus.SUCCESS, fileId.toString());
    }

    /**
     * 从 GridFS 中读取图片内容
     * 逻辑流程：
     * 1. 判断传入的 faceId 是否为空("" 或 null)，如果为空，则直接抛出自定义异常"文件不存在"
     * 2. 从 GridFS 中读取
     * 3. 将人脸图片输出到浏览器
     *
     * @param faceId
     * @return
     */
    @Override
    public void readInGridFS(HttpServletResponse response, String faceId) throws Exception {
        if (StringUtils.isBlank(faceId) || faceId.equalsIgnoreCase("null")) {
            MyCustomException.display(ResponseStatus.FILE_NOT_EXIST_ERROR);
        }

        File adminFace = readFileFromGridFSByFaceId(faceId);
        FileUtils.downloadFile(response, adminFace);
    }

    /**
     * 通过 faceId，从 GridFS 中获取图片（base64）数据
     * <p>
     * 逻辑流程：
     * 1. 获得 GridFS 中的人脸文件
     * 2. 将文件转换为 base64
     *
     * @param request
     * @param response
     * @param faceId
     * @return
     */
    @Override
    public JsonResult getFace64ByFaceId(HttpServletRequest request, HttpServletResponse response, String faceId) throws Exception {
        File file = readFileFromGridFSByFaceId(faceId);
        String base64 = FileUtils.fileToBase64(file);
        return new JsonResult(ResponseStatus.SUCCESS, base64);
    }

    private File readFileFromGridFSByFaceId(String faceId) throws FileNotFoundException {
        System.out.println(faceId);
        GridFSFindIterable gridFSFiles = gridFSBucket.find(Filters.eq("_id", new ObjectId(faceId)));
        GridFSFile gridFSFile = gridFSFiles.first();
        if (gridFSFile == null) {
            MyCustomException.display(ResponseStatus.FILE_NOT_EXIST_ERROR);
        }
        String filename = gridFSFile.getFilename();
        log.info("GridFS Filename : {}", filename);

        // 保存文件到服务器临时目录
        File fileTemp = new File("/Users/macbook/workspace/tmp");
        if (!fileTemp.exists()) {
            fileTemp.mkdirs();
        }
        File file = new File("/Users/macbook/workspace/tmp/" + faceId + "-" + filename);

        // 创建文件输出流
        OutputStream os = new FileOutputStream(file);
        // 下载到服务器
        gridFSBucket.downloadToStream(new ObjectId(faceId), os);

        return file;
    }
}
