package com.project.fyst.domain.entity.member;

import com.project.fyst.domain.entity.common.BaseEntity;
import com.project.fyst.domain.entity.likeditem.LikedItem;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Getter
@Setter
public class Member extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String name;
    private String gender;
    @Column(unique = true)
    private String email;
    private String password;
    private String phoneNumber;
    private String roles;

    @OneToMany(mappedBy = "member")
    private List<LikedItem> likedItems = new ArrayList<>();

    public Member(String name, String gender, String email, String password, String phoneNumber) {
        this.name = name;
        this.gender = gender;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    public Member(Long id, String name, String gender, String email, String password, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    public List<String> getRoleList(){
        if(this.roles.length() > 0){
            return Arrays.asList(this.roles.split(","));
        }

        return new ArrayList<>();
    }

    protected Member(){}

}