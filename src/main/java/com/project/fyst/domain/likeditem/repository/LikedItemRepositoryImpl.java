package com.project.fyst.domain.likeditem.repository;

import com.project.fyst.domain.entity.item.QItem;
import com.project.fyst.domain.entity.likeditem.QLikedItem;
import com.project.fyst.domain.entity.member.QMember;
import com.project.fyst.domain.item.entity.Item;
import com.project.fyst.domain.likeditem.entity.LikedItem;
import com.project.fyst.domain.member.entity.Member;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.util.List;

import static com.project.fyst.domain.entity.item.QItem.*;
import static com.project.fyst.domain.entity.likeditem.QLikedItem.*;
import static com.project.fyst.domain.entity.member.QMember.*;

@RequiredArgsConstructor
public class LikedItemRepositoryImpl implements LikedItemRepositoryCustom{

    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    @Override
    public List<LikedItem> findAllByEmail(String email) {
        return queryFactory
                .selectFrom(likedItem)
                .join(likedItem.member, member).fetchJoin()
                .join(likedItem.item, item).fetchJoin()
                .where(member.email.eq(email))
                .fetch();
    }

    @Override
    public void delete(Long likedItemId) {
        LikedItem likedItem = queryFactory
                .selectFrom(QLikedItem.likedItem)
                .join(QLikedItem.likedItem.member, member).fetchJoin()
                .join(QLikedItem.likedItem.item, item).fetchJoin()
                .where(QLikedItem.likedItem.id.eq(likedItemId))
                .fetchOne();

        em.remove(likedItem);
    }
}
