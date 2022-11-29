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
