package com.project.fyst.domain.repository.item;

import com.project.fyst.domain.entity.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Optional<Item> findOne(Long id);
}
