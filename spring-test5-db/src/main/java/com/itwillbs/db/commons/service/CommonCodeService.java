package com.itwillbs.db.commons.service;

import org.springframework.stereotype.Service;

import com.itwillbs.db.commons.entity.CommonCode;
import com.itwillbs.db.commons.enums.MemberRoleType;
import com.itwillbs.db.commons.repository.CommonCodeRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CommonCodeService {
	private final CommonCodeRepository commonCodeRepository;

	public CommonCodeService(CommonCodeRepository commonCodeRepository) {
		this.commonCodeRepository = commonCodeRepository;
	}
	// ====================================================================
	// 사용자 권한 조회
	public CommonCode getMemberRole(MemberRoleType user) {
		return null;
	}
	
	public CommonCode getMemberRole(String commonCode) {
		// CommonCodeRepository - findByCodeGroupAndCommonCode()
		// => 첫번째 파라미터인 코드그룹은 "MEMBER_ROLE" 값으로 고정
		return commonCodeRepository.findByCodeGroupAndCommonCode("MEMBER_ROLE", commonCode)
				.orElseThrow(() -> new EntityNotFoundException("해당 권한 코드가 존재하지 않습니다!"));
	}
	
	

}













