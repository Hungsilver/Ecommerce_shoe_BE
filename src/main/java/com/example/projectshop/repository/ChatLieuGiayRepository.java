package com.example.projectshop.repository;

import com.example.projectshop.domain.ChatLieuGiay;
import com.example.projectshop.domain.SanPham;
import com.example.projectshop.domain.Xuatxu;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatLieuGiayRepository extends JpaRepository<ChatLieuGiay, Integer> {
    @Query(value = "select * from chatlieugiay  limit 1",nativeQuery = true)
    ChatLieuGiay getTop1();


//    @Query(value = "select clg from ChatLieuGiay clg where (clg.id =:id or :id is null) and(:names is null  or clg.ten in :names )")
//    List<ChatLieuGiay> test(@Param("id") Integer id,@Param("names") List<String> names);

    @Query(value = "select clg from ChatLieuGiay clg where (clg.id in :id or :id is null) and(:names is null  or clg.ten in :names )")
    List<ChatLieuGiay> test(@Param("id") Integer id,@Param("names") String[] names);

    @Query("select x from ChatLieuGiay x where x.ten like :ten")
    Page<ChatLieuGiay> findAllByTen(@Param("ten") String ten, Pageable pageable);


}
