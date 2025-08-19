package com.itwillbs.db.commons.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.itwillbs.db.commons.entity.CommonCode;

@Repository
public interface CommonCodeRepository extends JpaRepository<CommonCode, Long> {
	
	Optional<CommonCode> findByCodeGroupAndCommonCode(String codeGroup, String commonCode);

}
