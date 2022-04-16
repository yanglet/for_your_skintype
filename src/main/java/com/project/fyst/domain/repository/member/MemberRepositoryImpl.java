package com.project.fyst.domain.repository.member;

import com.project.fyst.domain.entity.member.Member;
import com.project.fyst.domain.entity.member.QMember;
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
}
