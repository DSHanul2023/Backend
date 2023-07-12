package com.example.hanul.repository;

import com.example.hanul.model.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, String> {
    MemberEntity findByEmail(String email);
    Boolean existsByEmail(String email);
    MemberEntity findByEmailAndPassword(String email, String password);
}
