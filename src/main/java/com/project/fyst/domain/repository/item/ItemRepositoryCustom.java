package com.project.fyst.domain.repository.item;

import com.project.fyst.domain.entity.item.Item;
import com.project.fyst.domain.entity.item.SkinType;

import java.util.List;

public interface ItemRepositoryCustom {
    List<Item> findDrItems();
    List<Item> findInItems();
    List<Item> findSiItems();
    List<Item> findBeItems();
    List<Item> findItemsBySkinType(SkinType skinType);
    List<Item> findItemsByBrand(String brand);
}
