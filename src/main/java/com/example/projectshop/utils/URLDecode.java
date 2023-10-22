package com.example.projectshop.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class URLDecode {

    public static String getDecode(String input){
        // mã hóa dấu , thành %2C
        try {
            String decodedParam = URLDecoder.decode(input, "UTF-8");
            return decodedParam;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
