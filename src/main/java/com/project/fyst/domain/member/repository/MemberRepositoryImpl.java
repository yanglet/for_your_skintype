package com.project.fyst.domain.member.repository;

import com.project.fyst.domain.entity.member.QMember;
import com.project.fyst.domain.likeditem.repository.LikedItemRepository;
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
public class MemberRepositoryImpl implements MemberRepositoryCustom{

    private final LikedItemRepository likedItemRepository;
    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    @Override
    public List<Member> findAllWithLikedItems() {
        return queryFactory.selectFrom(member).distinct()
                .join(member.likedItems, likedItem).fetchJoin()
                .join(likedItem.item, item).fetchJoin()
                .fetch();
    }

    @Override
    public Member findOneByEmailWithLikedItems(String email) {
        return queryFactory.selectFrom(member)
                .join(member.likedItems, likedItem).fetchJoin()
                .join(likedItem.item, item).fetchJoin()
                .where(member.email.eq(email))
                .fetchOne();
    }

    @Transactional // org.springframework.dao.InvalidDataAccessApiUsageException 해결
    @Override
    public void delete(Long memberId) {
        /**
         * DataIntegrityViolationException 해결
         * Member가 갖고있는 likeditems 을 다 지워야 삭제할 수 있음
         */
        Member member = queryFactory.selectFrom(QMember.member)
                .where(QMember.member.id.eq(memberId))
                .fetchOne();

        Member findMember = findOneByEmailWithLikedItems(member.getEmail());

        findMember.getLikedItems().stream().forEach(l -> likedItemRepository.delete(l));

        em.remove(findMember);
    }
}
