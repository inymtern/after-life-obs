package com.after.life.obs.cos;

import com.alibaba.fastjson2.JSON;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

/**
 * @Author Lostceleste
 * @Version 1.0
 * @Date 2023-08-22 21:07
 */
@Component
public class CosUtils {

    @Resource
    private COSClient cosClient;

    @Value("${config.cos.bucket}")
    private String bucket;

    public String uploadWebp(File file) {
        String key = "img/" + System.currentTimeMillis() + ".webp";
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, key, file);
        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
        return JSON.toJSONString(putObjectResult);
    }

    public String uploadWebp(byte[] bytes) {
        String key = "img/" + System.currentTimeMillis() + ".webp";
        InputStream inputStream = new ByteArrayInputStream(bytes);
        ObjectMetadata objectMetadata = new ObjectMetadata();
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, key, inputStream, objectMetadata);
        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
        return "/" + key;
    }
}
