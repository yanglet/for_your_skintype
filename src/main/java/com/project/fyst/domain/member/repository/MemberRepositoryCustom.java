package com.project.fyst.domain.member.repository;

import com.project.fyst.domain.member.entity.Member;

import java.util.List;

public interface MemberRepositoryCustom {
    List<Member> findAllWithLikedItems();
    Member findOneByEmailWithLikedItems(String email);
}

