# NBE8-10-1-Team05
1차 프로젝트 - 카페백엔

## 기능
### 고객
- 이메일로 접속 시 브라우저 LocalStorage에 저장
- 주문서 생성
- 나의 주문 내역 조회
- 주문서 수정
- 주문 취소

### 관리자
- 일별 배송 내역을 합산하여 조회

## 시연 영상
https://drive.google.com/file/d/1NVhuyxe1CtFj-YXvb4H8DH27rZB0IuOM/view?usp=sharing

## ERD
<img width="2689" height="1962" alt="erd" src="https://github.com/user-attachments/assets/00595fe5-92aa-4fc1-931c-bb5c71f25d73" />

## API 명세서
[Notion API 명세서 바로가기](https://www.notion.so/API-2ca15a0120548183afd0c8446c170c33#2cb15a01205480e992a8caff71c9ea3e)

## 실행 방법

1. 백엔드
  - intelliJ 등에서 Backend 폴더에 있는 스프링부트 서버를 localhost:8080 번에 띄우기
  - http://localhost:8080/h2-console/ 에서 접속가능하거나 http://localhost:8080/api/v1/order/detail/1 에서 JSON이 잘 나오면 성공.

2. 프론트엔드
  - a. 배포 사이트: https://frontend-qz3b00dv2-minji-kims-projects-d96c6185.vercel.app/
  - b. 로컬 실행
    - frontend 폴더 바로 아래에 `.env` 파일 추가
    - 파일내용
      ```
      NEXT_PUBLIC_API_BASE_URL=http://localhost:8080
      ```
    - frontend 폴더에서 npm run dev 로 next.js 실행
    - (만일 실행이 안될 시)
      ```
      npm cache clean --force
      rm -rf node_modules
      ```
    - package-lock.json 삭제
      ```
      npm install
      npm run dev
      ```
    - [localhost:3000](http://localhost:3000/) 으로 접속해 아무 이메일이나 입력해 접속. (`john@gmail.com` 으로 접속시 샘플 주문 정보가 있음.)
    - [localhost:3000/admin](http://localhost:3000/admin) <- 관리자 페이지
   
## 팀원 소개
|이름|역할|담당 업무|
|--|--|--|
|[양지니](https://github.com/JinyYa)|팀장|프로젝트 총괄, 초기 세팅, 코드 리뷰|
|[김민지](https://github.com/kminji127)|팀원|프론트엔드 개발, api 연동|
|[노정원](https://github.com/NohGar)|팀원|주문 CRUD 개발, 발표|
|[문준서](https://github.com/MOONJUNSEO)|팀원|상품 CRUD 개발, 테스트 코드 작성|

