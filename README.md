# coupangeats_server_mason_bella

##  MASON
## 2022-11-26

- EC2 서버 구축
- ERD 설계 진행 중
- 초기 프로젝트 생성

#### ⚠️ 발생한 이슈 및 해결

1. My sql을 설치하고 새로운 유저 생성 후 외부 접속 권한을 주었지만 외부 접속이 되지 않았음

⚒️ 해결 방법  

- Mysql 설정 파일 경로로 이동 cd /var/www/dev/coupangeats_server_mason_bella/etc/mysql/mysql.conf.d
- 설정 파일 열기 sudo vim mysqld.cnf
- bind-address 부분을 0.0.0.0으로 설정을 해서 외부 접속을 허용해준다.

2. Certbot nginx plugin 설치 할 때 에러 발생

⚒️ 해결 방법

- 처음에 Certbot nginx plugin 설치 할 때  명령어를 1. sudo apt install python-certbot-nginx  2. sudo apt-add-repository -r ppa:certbot/certbot 순으로 입력했는데 1번 명령어를 입력했을 때 에러가 발생했고 이후 2번 명령어 역시 에러가 발생하였다.
- 스텍 오버플로우를 참조하여 에러를 해결하였는데 방법은 우분투 20.04의 경우 위의 1번 명령어를 sudo apt-get install certbot python3-certbot-nginx로 사용해야한다. 즉 python이 아니라 python3로 입력해야 함

[https://stackoverflow.com/questions/60249177/e-the-repository-http-ppa-launchpad-net-certbot-certbot-ubuntu-focal-release](https://stackoverflow.com/questions/60249177/e-the-repository-http-ppa-launchpad-net-certbot-certbot-ubuntu-focal-release)

3 . 인증서 발급 과정 중 에러로 인해 실패

⚒️ 해결 방법

- [https://growingsaja.tistory.com/487](https://growingsaja.tistory.com/487) 인바운드규칙에서 80 포트를 열어주어야 한다.


### 2022-11-27

- ERD 설계 완료
- API 리스트업
- ssl 적용
- dev/prod 서버 구축   

⚠️ 발생한 이슈 및 해결

1. git push를 로컬에서 진행하고 cli에서 git pull 명령어를 입력했을 때 로컬에서 push 했던 수정 내용이 적용 되지 않았다. 

⚒️ 해결 방법

git remote -v 명령어를 입력했을 때 연결된 원격 저장소가 없다는 것을 확인하고 git remote add origin으로 원격 저장소와 연결하였고  git branch checkout main을 통해 main으로 branch를 후 

git branch —set-upstream-to=origin/main main 명령어 실행 후 git pull을 실행하니 해결되었다.

(정확한 해결방법인지는 모르겠으나 일단 push한 내용이 pull을 했을 경우 적용이 된다.)

### 2022-11-28
- ERD 수정
- RDS 구축 완료
- 레스토랑 API 구현 중

### 2022-11-29
- 레스토랑 API 
     - 가게 생성, 가게 수정, 가게 삭제, 가게 전체 검색, 가게 index 검색, 이름으로 검색, 카테고리 검색 구현 
     - 구현한 부분 API 작성 중
     - 생성, 수정, 삭제 jwt 적용

### 2022-11-30
 - 레스토랑 API 수정 
     -  이미지 테이블 변경에 맞춰 레스토랑 API 수정
     - jwt 적용 해지
 - 레스토랑 API 명세서 작성
 - 주소 API 개발 중
 
 
### 2022-12-01
- 주소 API 개발 보류 메뉴 API 개발
     - 메뉴 API 생성,삭제,수정 개발 (테스트 x, 명세서 작성 x) 
     
### 2022-12-02
- 메뉴 API 개발, 테스트 완료 (명세서 x)
- 메뉴 옵션 API 개발 완료 (테스트, 명세서 x)

### 2022-12-03
- 메뉴 API 완성 (테스트 o, 명세서 o)
- 메뉴 옵션 API 완성 (테스트 o, 명세서 o)
- 레스토랑 API 중 메뉴 명으로 레스토랑 검색 추가
- 회의 진행(클라이언트 요청 사항과 서버 요청 사항 종합 & 변경 사항, 추가 사항 등 종합)

### 2022-12-04
- 주소,가게,유저 테이블 수정
- 주소, 가게 테이블 수정으로 인해 코드 수정
- 주소 API 개발 완료 (테스트, 명세서 x)

### 2022-12-05
- 주소 API 개발 및 테스트
- 레스토랑 API 개발 및 테스트
- 명세서 

### 2022-12-06
- 메뉴, 레스토랑 API 수정
- 이미지 url 생성, 더미데이터 생성
- API 명세서 수정

### 2022-12-07
- API validation 수정 및 추가
- 게시판 API 개발 및 테스트
- RESTAURANT 조회 페이징 기능 추가
- RESTAURANT 조회 시 결과 값에 평점 추가
- API 테스트
---

## BELLA

### 🗓️ 2022-11-26
- EC2 서버 구축
- ERD 설계 진행 중
- 초기 프로젝트 생성

### 🗓️ 2022-11-27
- ERD 설계 완료
- API 리스트업
- RDS 데이터베이스 구축
- dev/prod 서버 구축

     🚨 **jar 실행 시 port 충돌 에러**   
     Web server failed to start. Port 9000 was already in use.

     🌟 **실행중인 jar pid kill**
     ```bash
     ps -ef | grep jar
     # 실행중인 jar pid
     kill 20947
     ```

### 🗓️ 2022-11-28
- ERD 수정
- USER API 개발 및 테스트 완료
- USER API 명세서 작성

### 🗓️ 2022-11-29
- USER API 프로필 이미지 반영해서 개발 및 테스트
- DATABASE 이미지 테이블 수정
> 1. RESTAURANT USER MENU REVIEW 테이블의 IMAGE 컬럼 삭제
> 2. IMAGE 테이블에 컬럼 3개 추가
> - TARGET_ID INT NOT NULL COMMENT '대상 고유 번호'
> - TARGET_CODE VARCHAR(2) NOT NULL COMMENT 'RS(가게) / MN(메뉴) / US(유저) / RV(리뷰)'
> - MODIFICATION_DATE TIMESTAMP NULL COMMENT '수정일'
- USER API 명세서 수정
- SSL 적용

### 🗓️ 2022-11-30
- MENU, ORDER 테이블 수정
- REVIEW API 개발 시작
- USER API 주소 데이터 받아오게끔 수정

### 🗓️ 2022-12-01
- REVIEW API 개발
- USER API 수정

### 🗓️ 2022-12-02
- REVIEW API 개발 및 테스트 완료
- REVIEW API 명세서 작성
- ORDER API 개발 시작

### 🗓️ 2022-12-03
- 프론트와의 협업 미팅 진행으로 일부 API 및 데이터 수정
- REVIEW API 수정
- WISH API 개발 완료
- USER API 수정

### 🗓️ 2022-12-04
- 유저 주소 테이블 조인 삭제
- 리뷰 조회 api 가게명 추가

### 🗓️ 2022-12-05
- ORDER API 개발
- WISH API 명세서 작성

### 🗓️ 2022-12-06
- USER API 수정 : 회원정보 수정 시 비밀번호 암호화 처리
- ORDER API 수정 : 주문 VALIDATION 체크 추가
- WISH API 수정 : 이미지 테이블 JOIN
- API 명세서 작성 완료 및 수정사항 반영해서 개발서버 재가동

### 🗓️ 2022-12-07
- ORDER API 수정 : 인덱스로 주문 조회 API 추가
- REVIEW API 수정 : 리뷰가 0개일 경우 평균 별점 3.0으로 초기화
- API 27개 개발 테스트 완료 및 서버 반영
