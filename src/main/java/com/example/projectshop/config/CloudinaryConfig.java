package com.example.projectshop.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {
    @Bean
    public Cloudinary cloudinary(){
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dxmuvfnsp",
                "api_key", "683565788767385",
                "api_secret", "5VYzj9l-mWrkgG1qaAwoyHFbGbU",
                "secure",true));
        return cloudinary;
    }
}
