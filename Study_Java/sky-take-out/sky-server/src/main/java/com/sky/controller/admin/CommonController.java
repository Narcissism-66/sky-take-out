package com.sky.controller.admin;

import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

/*
 * @Auther:fz
 * @Date:2025/7/27
 * @Description:
 */
@RestController
@RequestMapping("admin/common")
@Api(tags = "通用接口")
public class CommonController {

    @Value("${file.upload-dir}") // 从配置文件中读取上传目录路径
    private String uploadDir;

    @PostMapping("upload")
    @ApiOperation("文件上传")
    public Result<String> upload(MultipartFile file){
        try {
            // 检查文件是否为空
            if (file.isEmpty()) {
                return Result.error( "上传的文件为空");
            }

            // 创建上传目录（如果不存在）
            File directory = new File(uploadDir);
            if (!directory.exists()) {
                if (!directory.mkdirs()) { // 创建目录
                    return Result.error("无法创建上传目录");
                }
            }

            // 生成唯一的文件名
            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(uploadDir, fileName);

            // 将文件保存到服务器
            Files.copy(file.getInputStream(), filePath);

            // 返回成功响应，包含文件的相对路径
            String fileUrl ="http://localhost:8080/"+"Image/" + fileName; // 相对路径
            System.out.println(fileUrl);
            return Result.success(fileUrl);
        } catch (IOException e) {
            e.printStackTrace();
            return Result.error("文件上传失败：" + e.getMessage());
        }
    }
}
