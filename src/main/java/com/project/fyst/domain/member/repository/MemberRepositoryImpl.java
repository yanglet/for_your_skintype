package com.project.fyst.domain.member.repository;

import com.project.fyst.domain.member.entity.Member;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.project.fyst.domain.entity.item.QItem.item;
import static com.project.fyst.domain.entity.likeditem.QLikedItem.likedItem;
import static com.project.fyst.domain.entity.member.QMember.member;

@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Member> findAllWithLikedItems() {
        return queryFactory.selectFrom(member).distinct()
                .join(member.likedItems, likedItem).fetchJoin()
                .join(likedItem.item, item).fetchJoin()
                .fetch();
    }

    @Override
    public Member findOneByEmailWithLikedItems(String email) {
        return queryFactory.selectFrom(member).distinct()
                .join(member.likedItems, likedItem).fetchJoin()
                .join(likedItem.item, item).fetchJoin()
                .where(member.email.eq(email))
                .fetchOne();
    }
}
