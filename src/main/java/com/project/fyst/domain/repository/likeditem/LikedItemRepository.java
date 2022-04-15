package com.project.fyst.domain.repository.likeditem;

import com.project.fyst.domain.entity.likeditem.LikedItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikedItemRepository extends JpaRepository<LikedItem, Long> {
}
