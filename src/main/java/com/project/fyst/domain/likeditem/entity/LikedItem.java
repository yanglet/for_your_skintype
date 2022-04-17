package com.project.fyst.domain.likeditem.entity;

import com.project.fyst.domain.member.entity.Member;
import com.project.fyst.domain.common.entity.BaseEntity;
import com.project.fyst.domain.item.entity.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class LikedItem extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "likeditem_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    //생성 메서드
    public static LikedItem of(Item item, Member member){
        LikedItem likedItem = new LikedItem();
        likedItem.setItem(item);
        likedItem.setMember(member);
        member.getLikedItems().add(likedItem);

        return likedItem;
    }
}
