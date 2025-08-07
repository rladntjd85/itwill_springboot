package com.itwillbs.db.members.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.itwillbs.db.members.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

}
