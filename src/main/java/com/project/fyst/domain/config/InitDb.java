package com.project.fyst.domain.config;

import com.google.gson.Gson;
import com.project.fyst.domain.entity.Item;
import com.project.fyst.domain.entity.LikedItem;
import com.project.fyst.domain.entity.Member;
import com.project.fyst.domain.repository.ItemRepository;
import com.project.fyst.domain.repository.LikedItemRepository;
import com.project.fyst.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() throws IOException, ParseException {
        initService.dbInit();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final ItemRepository itemRepository;
        private final MemberRepository memberRepository;
        private final LikedItemRepository likedItemRepository;

        public void dbInit() throws IOException, ParseException {
            ClassPathResource resource = new ClassPathResource("data/itemdata.json");
            Path path = Paths.get(resource.getURI());
            System.out.println("path = " + path);
            JSONArray jsonList = (JSONArray) new JSONParser().parse(new FileReader(path.toString()));

            for (Object o : jsonList) { //상품정보 초기화
                itemRepository.save(new Gson().fromJson(o.toString(), Item.class));
            }

            Member member1 = new Member("박유저",
                    "woman",
                    "user1@gmail.com",
                    "user1_password",
                    "01012345678");
            Member member2 = new Member("양글렛",
                    "man",
                    "yanglet@gmail.com",
                    "yanglet_pw",
                    "01023124123");


            LikedItem likedItem1 = LikedItem.createLikedItem(itemRepository.findOne(3L).get(), member1);
            LikedItem likedItem2 = LikedItem.createLikedItem(itemRepository.findOne(5L).get(), member1);
            LikedItem likedItem3 = LikedItem.createLikedItem(itemRepository.findOne(8L).get(), member2);
            LikedItem likedItem4 = LikedItem.createLikedItem(itemRepository.findOne(14L).get(), member2);

            likedItemRepository.save(likedItem1);
            likedItemRepository.save(likedItem2);
            likedItemRepository.save(likedItem3);
            likedItemRepository.save(likedItem4);
            memberRepository.save(member1);
            memberRepository.save(member2);
        }
    }
}
