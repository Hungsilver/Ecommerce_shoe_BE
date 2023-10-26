package com.example.projectshop.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "anhsanpham")
public class AnhSanPham {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "ten")
    private String ten;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "id_chitietsanpham")
    private ChiTietSanPham chiTietSanPham;

}
