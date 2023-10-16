package com.example.projectshop.repository;

import com.example.projectshop.domain.ChatLieuDeGiay;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatLieuDeGiayRepository extends JpaRepository<ChatLieuDeGiay, Integer> {
    @Query("select c from ChatLieuDeGiay c where c.ten like %:input%")
    Page<ChatLieuDeGiay> timKiem(@Param("input") String input, Pageable pageable);

    @Query(value = "select * from chatlieudegiay  limit 1",nativeQuery = true)
    ChatLieuDeGiay getTop1();

}
