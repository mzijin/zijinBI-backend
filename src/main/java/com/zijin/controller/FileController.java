package com.zijin.controller;

import cn.hutool.core.io.FileUtil;
import com.zijin.common.BaseResponse;
import com.zijin.common.ErrorCode;
import com.zijin.common.ResultUtils;
import com.zijin.constant.FileConstant;
import com.zijin.exception.BusinessException;
import com.zijin.manager.CosManager;
import com.zijin.model.dto.file.UploadFileRequest;
import com.zijin.model.entity.User;
import com.zijin.model.enums.FileUploadBizEnum;
import com.zijin.service.FileService;
import com.zijin.service.UserService;
import java.io.File;
import java.util.Arrays;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件接口
 *
 *
 */

@RestController
@RequestMapping("/oss")
@CrossOrigin(origins = "http://localhost:8000", allowCredentials = "true")
public class FileController {


    @Resource
    private FileService ossService;

    /**
     * 上传头像
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public BaseResponse<String> uploadFile(@RequestPart("file") MultipartFile file){

        //获取上传的文件
        if (file.isEmpty()) {
            throw new BusinessException(ErrorCode.NOT_NULL, "上传文件为空");
        }
        //返回上传到oss的路径
        String url = ossService.uploadFileAvatar(file);
        //返回r对象
        return ResultUtils.success(url);
    }
    /**
     * 校验文件
     *
     * @param multipartFile
     * @param fileUploadBizEnum 业务类型
     */
    private void validFile(MultipartFile multipartFile, FileUploadBizEnum fileUploadBizEnum) {
        // 文件大小
        long fileSize = multipartFile.getSize();
        // 文件后缀
        String fileSuffix = FileUtil.getSuffix(multipartFile.getOriginalFilename());
        final long ONE_M = 1024 * 1024L;
        if (FileUploadBizEnum.USER_AVATAR.equals(fileUploadBizEnum)) {
            if (fileSize > ONE_M) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "文件大小不能超过 1M");
            }
            if (!Arrays.asList("jpeg", "jpg", "svg", "png", "webp").contains(fileSuffix)) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "文件类型错误");
            }
        }
    }
}
