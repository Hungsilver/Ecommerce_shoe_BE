package com.example.projectshop.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CloudinaryService {
    @Autowired
    private Cloudinary cloudinary;

    public List<Map> uploadListFile(MultipartFile[] input) {
        List<Map> list = new ArrayList<>();
        try {
            for(MultipartFile file : input){
                Map map = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
                list.add(map);
            }
        } catch (Exception e) {
            return null;
        }
        return list;
    }

    public Map uploadFile(MultipartFile file) {
        try{
            return cloudinary.uploader().upload(file.getBytes(),ObjectUtils.emptyMap());
        }catch (IOException e){
            return null;
        }

    }
}
