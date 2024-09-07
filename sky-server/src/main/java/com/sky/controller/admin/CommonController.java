package com.sky.controller.admin;

import com.sky.constant.MessageConstant;
import com.sky.result.Result;
import com.sky.utils.S3Util;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.constant.ErrorConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/admin/common")
@Api("common Api")
public class CommonController {
    @Autowired
    S3Util s3Util;
    @PostMapping("/upload")
    @ApiOperation("upload file")
    public Result<String> upload(MultipartFile file) {
        log.info("Uploading file" + file );
        // get the original name of file
        String fileName = file.getOriginalFilename();

        // construct new name to avoid file overwrite
        String extension = fileName.substring(fileName.lastIndexOf("."));
        String objectName = UUID.randomUUID() + extension;

        // upload to Cloud

        try {
             String filePath = s3Util.upload(file.getBytes(), objectName);
             return Result.success(filePath);
        } catch (IOException e) {
            log.info("file upload failure" + e.getMessage());
        }
        return Result.success(MessageConstant.UPLOAD_FAILED);



    }
}
