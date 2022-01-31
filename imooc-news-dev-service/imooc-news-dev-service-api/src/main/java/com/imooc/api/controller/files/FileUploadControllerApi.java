package com.imooc.api.controller.files;

import com.imooc.bo.AddNewAdminBO;
import com.imooc.result.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author Dooby Kim
 * @Date 2022/1/1 10:38 下午
 * @Version 1.0
 */
@Api(value = "FileUploadController", tags = {"用于文件上传"})
@RequestMapping("fs")
public interface FileUploadControllerApi {

    @ApiOperation(value = "上传用户头像", notes = "上传用户头像", httpMethod = "POST")
    @PostMapping("/uploadFace")
    JsonResult uploadFace(@RequestParam String userId, MultipartFile file);

    /**
     * 文件上传到 MongoDB 的 GridFS 中
     *
     * @param newAdminBO
     * @return
     */
    @PostMapping("/uploadToGridFS")
    JsonResult uploadToGridFS(@RequestBody AddNewAdminBO newAdminBO);
}
