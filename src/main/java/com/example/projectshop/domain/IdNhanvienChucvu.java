package com.example.projectshop.domain;


import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
@Embeddable
public class IdNhanvienChucvu implements Serializable {

    @ManyToOne
    @JoinColumn(name = "id_nhanvien")
    private NhanVien idnhanvien;

    @ManyToOne
    @JoinColumn(name = "id_chucvu")
    private ChucVu idchucvu;

}
