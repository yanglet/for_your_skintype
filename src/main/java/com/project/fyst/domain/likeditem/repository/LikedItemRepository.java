package com.project.fyst.domain.likeditem.repository;

import com.project.fyst.domain.likeditem.entity.LikedItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikedItemRepository extends JpaRepository<LikedItem, Long> {
}
