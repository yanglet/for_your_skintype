# for_your_skintype
ForYourSkinType 백엔드 API 서버

https://github.com/yanglet/Fyst_backend 를 리뉴얼 하였음.

## 서비스 소개
간단한 테스트를 통해 피부타입에 맞는 스킨 케어 제품을 찾아주는 웹 서비스

## 구현
- Spring Data JPA를 통한 DB 접근 및 관리
- 주 저장소로 MySQL 사용
- QueryDSL을 통해 메소드화 된 방식으로 쿼리를 구성해 오류를 컴파일 시점에 처리
- NoSQL인 Redis를 이용해 캐시
- Swagger를 통한 API 문서관리
- Spring Security 와 jwt 방식을 이용한 사용자 인증 및 권한처리
- Advice를 이용한 예외처리
- 도메인형 디렉토리 구조를 선택
- Issues를 통해 프로젝트 진행 상황을 관리 ( https://github.com/yanglet/for_your_skintype/issues?q=is%3Aissue+is%3Aclosed )

## 데모 영상
<p>
  <img src="https://user-images.githubusercontent.com/96788792/164140645-ff03e5f1-ed9f-4418-86ea-e9611c426db9.gif">
</p>
