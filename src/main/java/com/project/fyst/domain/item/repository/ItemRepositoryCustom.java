package com.project.fyst.domain.item.repository;

import com.project.fyst.domain.item.entity.Item;
import com.project.fyst.domain.item.entity.SkinType;

import java.util.List;

public interface ItemRepositoryCustom {
    List<Item> findDrItems();
    List<Item> findInItems();
    List<Item> findSiItems();
    List<Item> findBeItems();
    List<Item> findItemsBySkinType(SkinType skinType);
    List<Item> findItemsByBrand(String brand);
}
