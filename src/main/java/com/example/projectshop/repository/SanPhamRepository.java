package com.example.projectshop.repository;

import com.example.projectshop.domain.ChiTietSanPham;
import com.example.projectshop.domain.SanPham;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface SanPhamRepository extends JpaRepository<SanPham, Integer> {

    @Query(value = "SELECT s FROM SanPham s\n" +
            "         INNER JOIN ThuongHieu t ON t.id = s.thuongHieu.id\n" +
            "         INNER JOIN Xuatxu x ON x.id = s.xuatXu.id\n" +
            "         INNER JOIN DanhMuc d ON d.id = s.danhMuc.id\n" +
            "WHERE (t.id IN :thuongHieu OR :thuongHieu IS NULL)\n" +
            "  AND (x.id IN :xuatXu OR :xuatXu IS NULL)\n" +
            "  AND (d.id IN :danhMuc OR :danhMuc IS NULL)\n" +
            "  AND (LOWER(s.ma) LIKE LOWER(CONCAT('%', :maSanPham, '%')) OR :maSanPham IS NULL)\n" +
            "  AND (s.trangThai = :trangThai OR :trangThai IS NULL)\n")
    Page<SanPham> getAll(
            @Param("thuongHieu") List<Integer> thuongHieu,
            @Param("xuatXu") List<Integer> xuatXu,
            @Param("danhMuc") List<Integer> danhMuc,
            @Param("maSanPham") String maSanPham,
            @Param("trangThai") Integer trangThai,
            Pageable pageable
    );

    @Query(value = "SELECT DISTINCT s FROM SanPham s\n" +
            "         INNER JOIN ChiTietSanPham c ON c.sanPham.id = s.id\n" +
            "         INNER JOIN ThuongHieu t ON t.id = s.thuongHieu.id\n" +
            "         INNER JOIN Xuatxu x ON x.id = s.xuatXu.id\n" +
            "         INNER JOIN MauSac m ON m.id = c.mauSac.id\n" +
            "         INNER JOIN KichCo k ON k.id = c.kichCo.id\n" +
            "         INNER JOIN ChatLieuGiay clg ON clg.id = c.chatLieuGiay.id\n" +
            "         INNER JOIN ChatLieuDeGiay cldg ON cldg.id = c.chatLieuDeGiay.id\n" +
            "WHERE c.giaBan BETWEEN :priceMin AND :priceMax\n" +
            "  AND (t.id IN :thuongHieu OR :thuongHieu IS NULL)\n" +
            "  AND (x.id IN :xuatXu OR :xuatXu IS NULL)\n" +
            "  AND (m.id IN :mauSac OR :mauSac IS NULL)\n" +
            "  AND (k.id IN :kichCo OR :kichCo IS NULL)\n" +
            "  AND (clg.id IN :chatLieuGiay OR :chatLieuGiay IS NULL)\n" +
            "  AND (cldg.id IN :chatLieuDeGiay OR :chatLieuDeGiay IS NULL)\n" +
            "  AND (LOWER(s.ten) LIKE LOWER(CONCAT('%', :tenSanPham, '%')) OR :tenSanPham IS NULL)\n" +
            "  AND (s.trangThai = :trangThai OR :trangThai IS NULL)\n")
    Page<SanPham> getAllByParam(
            @Param("priceMin") BigDecimal priceMin,
            @Param("priceMax") BigDecimal priceMax,
            @Param("thuongHieu") List<Integer> thuongHieu,
            @Param("xuatXu") List<Integer> xuatXu,
            @Param("mauSac") List<Integer> mauSac,
            @Param("kichCo") List<Integer> kichCo,
            @Param("chatLieuGiay") List<Integer> chatLieuGiay,
            @Param("chatLieuDeGiay") List<Integer> chatLieuDeGiay,
            @Param("tenSanPham") String tenSanPham,
            @Param("trangThai") Integer trangThai,
            Pageable pageable
    );


    @Query(value = "select * from sanpham order by id desc limit 1", nativeQuery = true)
    SanPham getTop1ByIdMax();

    @Query(value = "select s from SanPham s where s.ten = :name")
    Optional<SanPham> findByName(@Param("name") String name);


}
