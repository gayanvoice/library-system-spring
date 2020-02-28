package com.library.web.repository;

import com.library.web.model.Author;
import com.library.web.model.Member;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends CrudRepository<Member, Long> {

    Member findByMemberId(long memberId);

    Optional<Member> findByUsername(String username);
}