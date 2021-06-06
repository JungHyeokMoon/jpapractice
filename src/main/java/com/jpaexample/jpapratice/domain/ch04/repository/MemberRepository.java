package com.jpaexample.jpapratice.domain.ch04.repository;

import com.jpaexample.jpapratice.domain.ch04.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
}
