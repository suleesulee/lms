package com.sulee.lms.member.repository;

import com.sulee.lms.member.entity.AccessInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccessInfoRepository extends JpaRepository<AccessInfo, String> {
}
