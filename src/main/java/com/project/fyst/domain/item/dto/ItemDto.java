package com.project.fyst.domain.item.dto;

import com.project.fyst.domain.item.entity.Item;
import com.project.fyst.domain.item.entity.ItemFeature;
import com.project.fyst.domain.item.entity.SkinType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ItemDto {

    private Long id;
    private String name;
    private String price;
    private String priceSign;
    private String brand;
    private String imageLink;
    private String productLink;
    private String websiteLink;
    private ItemFeature itemFeature;
    private SkinType skinType;

    public ItemDto(Item item) {
        this.id = item.getId();
        this.name = item.getName();
        this.price = item.getPrice();
        this.priceSign = item.getPriceSign();
        this.brand = item.getBrand();
        this.imageLink = item.getImageLink();
        this.productLink = item.getProductLink();
        this.websiteLink = item.getWebsiteLink();
        this.itemFeature = item.getItemFeature();
        this.skinType = item.getSkinType();
    }

}
