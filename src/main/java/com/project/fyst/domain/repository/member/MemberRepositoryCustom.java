package com.project.fyst.domain.repository.member;

import com.project.fyst.domain.entity.member.Member;

import java.util.List;

public interface MemberRepositoryCustom {
    List<Member> findAllWithLikedItems();
}
