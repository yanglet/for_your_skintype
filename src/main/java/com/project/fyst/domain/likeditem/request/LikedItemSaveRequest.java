package com.project.fyst.domain.likeditem.request;

import com.project.fyst.domain.item.entity.Item;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LikedItemSaveRequest {
    private Item item;
}
