package com.project.fyst.domain.repository.item;

import com.project.fyst.domain.entity.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long>, ItemRepositoryCustom {
    @Query("select i from Item i where i.id=:id")
    Optional<Item> findOne(@Param("id") Long id);
}
