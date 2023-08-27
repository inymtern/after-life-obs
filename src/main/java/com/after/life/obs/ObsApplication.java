package com.after.life.obs;

import com.tinify.Tinify;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class ObsApplication {

    private String tinKey;

    @Value("${config.tinyPng.key}")
    public void setTinKey(String tinKey) {
        this.tinKey = tinKey;
    }

    public static void main(String[] args) {
        SpringApplication.run(ObsApplication.class, args);
    }

    @PostConstruct
    public void init() {
        Tinify.setKey(tinKey);
    }
}