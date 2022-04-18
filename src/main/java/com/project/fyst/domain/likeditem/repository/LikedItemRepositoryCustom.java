package com.project.fyst.domain.likeditem.repository;

import com.project.fyst.domain.item.entity.Item;
import com.project.fyst.domain.likeditem.entity.LikedItem;
import com.project.fyst.domain.member.entity.Member;

import java.util.List;

public interface LikedItemRepositoryCustom {
    LikedItem save(Item item, Member Member);
    List<LikedItem> findAllByEmail(String email);
    void delete(Long likedItemId);
}
