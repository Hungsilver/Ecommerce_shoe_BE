package com.example.projectshop.dto.anhsanpham;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class AnhSanPhamRequest {
    private Integer id;
    private MultipartFile[] ten;

}
