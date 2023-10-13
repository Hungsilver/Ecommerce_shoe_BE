package com.example.projectshop.repository;

import com.example.projectshop.domain.ChatLieuGiay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatLieuGiayRepository extends JpaRepository<ChatLieuGiay, Integer> {
}
