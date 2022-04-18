package com.project.fyst.domain.item.repository;

import com.project.fyst.domain.entity.item.QItem;
import com.project.fyst.domain.item.entity.Item;
import com.project.fyst.domain.item.entity.SkinType;
import com.project.fyst.domain.likeditem.repository.LikedItemRepository;
import com.project.fyst.global.jwt.dto.AccessToken;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.project.fyst.domain.entity.item.QItem.item;

@RequiredArgsConstructor
public class ItemRepositoryImpl implements ItemRepositoryCustom{

    private final JPAQueryFactory queryFactory;
    private final LikedItemRepository likedItemRepository;
    private final EntityManager em;

    @Override
    public List<Item> findDrItems() {
        return queryFactory.selectFrom(item)
                .where(item.brand.eq("drjart"))
                .fetch();
    }

    @Override
    public List<Item> findInItems() {
        return queryFactory.selectFrom(item)
                .where(item.brand.eq("innisfree"))
                .fetch();
    }

    @Override
    public List<Item> findSiItems() {
        return queryFactory.selectFrom(item)
                .where(item.brand.eq("sidmool"))
                .fetch();
    }

    @Override
    public List<Item> findBeItems() {
        return queryFactory.selectFrom(item)
                .where(item.brand.eq("beplain"))
                .fetch();
    }

    @Override
    public List<Item> findItemsBySkinType(SkinType skinType) {
        return queryFactory.selectFrom(item)
                .where(item.skinType.eq(skinType))
                .fetch();
    }

    @Override
    public List<Item> findItemsByBrand(String brand) {
        return queryFactory.selectFrom(item)
                .where(item.brand.eq(brand))
                .fetch();
    }

    @Transactional
    @Override
    public void delete(Long itemId, AccessToken accessToken) {
        /**
         * Item을 참조하고 있는 LikedItem을 모두 지워야
         * Item을 지울 수 있음
         */
        Item item = queryFactory.selectFrom(QItem.item)
                .where(QItem.item.id.eq(itemId))
                .fetchOne();
        likedItemRepository.findAllByItem(item)
                .stream()
                .forEach(l -> likedItemRepository.delete(l.getId()));

        em.remove(item);
    }
}
