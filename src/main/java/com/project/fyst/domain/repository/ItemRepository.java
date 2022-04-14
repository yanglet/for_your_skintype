package com.project.fyst.domain.repository;

import com.project.fyst.domain.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Optional<Item> findOne(Long id);
}
