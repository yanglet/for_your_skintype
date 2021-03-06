package com.project.fyst.domain.likeditem.repository;

import com.project.fyst.domain.entity.item.QItem;
import com.project.fyst.domain.entity.likeditem.QLikedItem;
import com.project.fyst.domain.item.entity.Item;
import com.project.fyst.domain.likeditem.entity.LikedItem;
import com.project.fyst.domain.member.entity.Member;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.project.fyst.domain.entity.item.QItem.item;
import static com.project.fyst.domain.entity.likeditem.QLikedItem.likedItem;
import static com.project.fyst.domain.entity.member.QMember.member;

@RequiredArgsConstructor
public class LikedItemRepositoryImpl implements LikedItemRepositoryCustom{

    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    @Transactional // org.springframework.dao.InvalidDataAccessApiUsageException 해결
    @Override
    public LikedItem save(Item item, Member member) {
        LikedItem likedItem = LikedItem.createLikedItem(item, member);
        em.persist(likedItem);

        return likedItem;
    }

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
    public List<LikedItem> findAllByItem(Item item) {
        return queryFactory
                .selectFrom(likedItem)
                .join(likedItem.member, member).fetchJoin()
                .join(likedItem.item, QItem.item).fetchJoin()
                .where(likedItem.item.eq(item))
                .fetch();
    }

    /**
     * DataIntegrityViolationException 해결
     * 연관된 엔티티를 페치조인으로 끌어와서 삭제
     */
    @Transactional // org.springframework.dao.InvalidDataAccessApiUsageException 해결
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
