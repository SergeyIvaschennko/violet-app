package com.example.web.repository;

import com.example.web.models.MetaUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MetaUserRepository extends JpaRepository<MetaUser, Long> {

    @Query("SELECT mu.username, mu.picUrl FROM MetaUser mu")
    List<Object[]> findAllMetaUserInfo();

    MetaUser findMetaUserByUsername(String username);

//    @Query("SELECT mu FROM MetaUser mu WHERE mu.user.id = :userId")
//    MetaUser findMetaUserByUserId(@Param("userId") Long userId);

}

