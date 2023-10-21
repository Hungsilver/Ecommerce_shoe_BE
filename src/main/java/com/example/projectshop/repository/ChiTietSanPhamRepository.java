package com.example.projectshop.repository;

import com.example.projectshop.domain.ChiTietSanPham;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChiTietSanPhamRepository extends JpaRepository<ChiTietSanPham, Integer> {
    @Query(value = "select * from chitietsanpham  order by giaban asc limit 1", nativeQuery = true)
    ChiTietSanPham getTop1ByPriceMin();

    @Query(value = "select * from chitietsanpham  order by giaban desc limit 1", nativeQuery = true)
    ChiTietSanPham getTop1ByPriceMax();

    @Query(value = "select c.id,c.soluong,c.giaban,c.ngaytao,c.trangthai,c.id_mausac,c.id_kichco,c.id_chatlieugiay,c.id_chatlieudegiay,c.id_sanpham from chitietsanpham c \n" +
            "inner join mausac m on m.id = c.id_mausac\n" +
            "inner join kichco k on k.id = c.id_kichco\n" +
            "inner join chatlieugiay cl on cl.id = c.id_chatlieugiay\n" +
            "inner join chatlieudegiay cld on cld.id = c.id_chatlieudegiay\n" +
            "inner join sanpham s on s.id = c.id_sanpham\n" +
            "where c.soluong like %:timKiem% \n" +
            "or m.ten like %:timKiem% \n" +
            "or cl.ten like %:timKiem% \n" +
            "or cld.ten like %:timKiem% \n" +
            "or s.ten like%:timKiem% \n" +
            "or c.soluong like %:timKiem%",
            nativeQuery = true)
    Page<ChiTietSanPham> timKiem(@Param("timKiem")String timKiem, Pageable pageable);
}
