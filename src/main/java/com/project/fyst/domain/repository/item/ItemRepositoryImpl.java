package com.project.fyst.domain.repository.item;

import com.project.fyst.domain.entity.item.Item;
import com.project.fyst.domain.entity.item.QItem;
import com.project.fyst.domain.entity.item.SkinType;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.project.fyst.domain.entity.item.QItem.*;

@RequiredArgsConstructor
public class ItemRepositoryImpl implements ItemRepositoryCustom{

    private final JPAQueryFactory queryFactory;

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
}
