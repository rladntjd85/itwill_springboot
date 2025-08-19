package com.itwillbs.db.members.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.itwillbs.db.members.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

	// 이메일로 사용자 조회
	Optional<Member> findByEmail(String email);

}
