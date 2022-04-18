package com.project.fyst.domain.item.entity;

import com.project.fyst.domain.common.entity.BaseEntity;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Item extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private String price;
    private String priceSign;
    private String brand;
    private String imageLink;
    private String productLink;
    private String websiteLink;

    @Enumerated(EnumType.STRING)
    private ItemFeature itemFeature;
    @Enumerated(EnumType.STRING)
    private SkinType skinType;

    public Item(String name,
                String price,
                String priceSign,
                String brand,
                String imageLink,
                String productLink,
                String websiteLink,
                ItemFeature itemFeature,
                SkinType skinType) {
        this.name = name;
        this.price = price;
        this.priceSign = priceSign;
        this.brand = brand;
        this.imageLink = imageLink;
        this.productLink = productLink;
        this.websiteLink = websiteLink;
        this.itemFeature = itemFeature;
        this.skinType = skinType;
    }

    protected Item(){}

}
