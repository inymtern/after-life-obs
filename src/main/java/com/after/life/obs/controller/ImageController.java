package com.after.life.obs.controller;

import com.after.life.obs.cos.CosUtils;
import com.after.life.obs.except.CommonException;
import com.tinify.Source;
import com.tinify.Tinify;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @Author Lostceleste
 * @Version 1.0
 * @Date 2023-08-22 20:28
 */
@RestController
@RequestMapping("/images")
public class ImageController {

    @Value("${config.upload.path}")
    private String uploadPath;

    @Value("${config.cos.address}")
    private String cosAddress;

    @Resource
    private CosUtils cosUtils;


    @PostMapping("/upload")
    public ResponseEntity<String> upload(MultipartFile file) {
        if (!isImage(file)) throw new CommonException("仅支持jpg/png/jpeg/webp文件!");
        byte[] bytes = smallImages4Bytes(file);
        try {
            String s = cosUtils.uploadWebp(bytes);
            return ResponseEntity.ok(cosAddress + s);
        } catch (Exception e) {
            throw new CommonException("上传图片失败！");
        }

    }


    /**
     *
     * @param fileName 文件名
     * @return newName
     */
    public String smallImage4FileName(String fileName) {
        Source source = null;
        try {
            source = Tinify.fromFile(uploadPath + File.separator + fileName);
            String newName = System.currentTimeMillis() + ".webp";
            source.toFile(newName);
            return newName;
        } catch (IOException e) {
            throw new CommonException("处理文件出错!");
        }
    }


    public byte[] smallImages4Bytes(MultipartFile file) {
        if(file.isEmpty()) throw new CommonException("文件为空!");
        byte[] sourceData;
        try {
            sourceData = file.getBytes();
            return Tinify.fromBuffer(sourceData).toBuffer();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    public boolean isImage(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        if (fileName != null) {
            String lowerCaseFileName = fileName.toLowerCase();
            if (lowerCaseFileName.endsWith(".png") || lowerCaseFileName.endsWith(".jpg") || lowerCaseFileName.endsWith(".jpeg") || lowerCaseFileName.endsWith(".webp") ) {
                return true;
            }
        }
        return false;
    }

}
