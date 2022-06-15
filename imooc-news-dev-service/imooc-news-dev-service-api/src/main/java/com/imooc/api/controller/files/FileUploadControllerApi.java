package com.imooc.api.controller.files;

import com.imooc.bo.AddNewAdminBO;
import com.imooc.result.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;

/**
 * @Author Dooby Kim
 * @Date 2022/1/1 10:38 下午
 * @Version 1.0
 */
@Api(value = "FileUploadController", tags = {"用于文件上传"})
@RequestMapping("fs")
public interface FileUploadControllerApi {

    /**
     * 上传单个文件（图片）
     * @param userId
     * @param file
     * @return
     */
    // @ApiOperation(value = "上传用户头像", notes = "上传用户头像", httpMethod = "POST")
    @PostMapping("/uploadFace")
    JsonResult uploadFace(@RequestParam String userId, MultipartFile file);

    /**
     * 上传多个文件
     * @param userId
     * @param file
     * @return
     */
    @PostMapping("/uploadSomeFiles")
    JsonResult uploadSomeFiles(@RequestParam String userId, MultipartFile[] files);

    /**
     * 文件上传到 MongoDB 的 GridFS 中
     *
     * @param newAdminBO
     * @return
     */
    @PostMapping("/uploadToGridFS")
    JsonResult uploadToGridFS(@RequestBody AddNewAdminBO newAdminBO);

    /**
     * 从 GridFS 中读取图片内容
     *
     * @param faceId
     * @return
     * @throws Exception
     */
    @GetMapping("/readInGridFS")
    void readInGridFS(HttpServletResponse response, String faceId) throws Exception;

    /**
     * 通过 faceId，从 GridFS 中获取图片（base64）数据
     *
     * @param request
     * @param response
     * @param faceId
     * @return
     */
    @GetMapping("/getFace64ByFaceId")
    JsonResult getFace64ByFaceId(HttpServletRequest request, HttpServletResponse response, String faceId) throws Exception;
}
