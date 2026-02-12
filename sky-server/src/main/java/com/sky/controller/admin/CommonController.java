package com.sky.controller.admin;

import com.aliyun.oss.model.MultipartUpload;
import com.sky.constant.MessageConstant;
import com.sky.result.Result;
import com.sky.utils.AliOssUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/admin/common")
@Api(tags = "interface for common use")
@Slf4j
public class CommonController {

    @Autowired
    private AliOssUtil aliOssUtil;

    /**
     * file upload
     * @param file
     * @return
     */
    public Result<String> upload(MultipartFile file) {
        log.info("file upload: {}", file);

        // TODO: file upload
        try {
            // get original name of file
            String originalFilename = file.getOriginalFilename();

            // cut the extension of file
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));

            // construct the name of file
            String objectName = UUID.randomUUID().toString() + extension;

            // path of file
            String filePath = aliOssUtil.upload(file.getBytes(), objectName);
            return Result.success(filePath);

        } catch (IOException e) {
            log.error("file upload failed: {}", e);
        }

        return Result.error(MessageConstant.UPLOAD_FAILED);
    }
}
