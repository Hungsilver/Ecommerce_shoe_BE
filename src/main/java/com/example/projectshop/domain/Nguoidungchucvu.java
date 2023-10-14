package com.example.projectshop.domain;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "nguoidungchucvu")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Nguoidungchucvu {
    @Id
    @Column(name = "id_NguoiDung")
    private Integer idNguoiDung;

    @Column(name = "id_ChucVu")
    private Integer idChucVu;


}
