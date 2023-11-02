package com.example.projectshop.repository;

import com.example.projectshop.domain.ChatLieuDeGiay;
import com.example.projectshop.domain.KichCo;
import com.example.projectshop.domain.Xuatxu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface KichCoRepository extends JpaRepository<KichCo, Integer> {
    @Query(value = "select * from kichco  limit 1",nativeQuery = true)
    ChatLieuDeGiay getTop1();

    @Query("select x from KichCo x where x.size like :size")
    Page<KichCo> findAllBySize(@Param("size") String size, Pageable pageable);
}
