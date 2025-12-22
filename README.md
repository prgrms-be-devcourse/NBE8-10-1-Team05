# NBE8-10-1-Team05-temp
team05  repository

## 실행하기 위해 해야하는 일

-intelliJ 등에서 Backend 폴더에 있는 스프링부트 서버를 localhost:8080 번에 띄우기
  -http://localhost:8080/h2-console/ 에서 접속가능하거나 http://localhost:8080/api/v1/order/detail/1 에서 JSON이 잘 나오면 성공.
  
- .env 파일 추가
- 경로: frontend 폴더 바로 아래
  파일내용
  ```
  NEXT_PUBLIC_API_BASE_URL=http://localhost:8080
  ```
- fronted 폴더에서 npm run dev 로 next.js 실행
- (만일 실행이 안될 시)
  ```
  npm cache clean --force
  rm -rf node_modules
  ```
- package-lock.json 삭제
  ```
  npm install
  npm run dev
- [localhost:3000](http://localhost:3000/) 으로 접속해 아무 이메일이나 입력해 접속. (john@gmail.com 으로 접속시 샘플 주문 정보가 있음.)
- [localhost:3000/admin](http://localhost:3000/admin) <- 관리자 페이지
