package com.project.fyst.domain.likeditem.dto;

import com.project.fyst.domain.item.dto.ItemDto;
import com.project.fyst.domain.likeditem.entity.LikedItem;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LikedItemDto {
    private Long id;
    private ItemDto item;

    public LikedItemDto(LikedItem likedItem) {
        this.id = likedItem.getId();
        this.item = new ItemDto(likedItem.getItem());
    }
}
