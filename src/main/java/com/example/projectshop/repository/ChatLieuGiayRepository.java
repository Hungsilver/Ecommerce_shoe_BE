package com.example.projectshop.repository;

import com.example.projectshop.domain.ChatLieuDeGiay;
import com.example.projectshop.domain.ChatLieuGiay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatLieuGiayRepository extends JpaRepository<ChatLieuGiay, Integer> {
    @Query(value = "select * from chatlieugiay  limit 1",nativeQuery = true)
    ChatLieuGiay getTop1();
}
