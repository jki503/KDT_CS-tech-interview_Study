package com.prgrms.test.domain.repository;

import com.prgrms.test.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
