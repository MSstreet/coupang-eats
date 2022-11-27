# coupangeats_server_mason_bella

## 2022-11-26
#### [BELLA]
- EC2 서버 구축
- ERD 설계 진행 중
- 초기 프로젝트 생성

#### [MASON]
- EC2 서버 구축
- ERD 설계 진행 중
- 초기 프로젝트 생성

#####  1.1 오류 

Certbot nginx plugin 설치 할 때 에러

sudo apt install python-certbot-nginx ->  sudo apt-add-repository -r ppa:certbot/certbot 에러

#####  1.2 오류 해결

[https://stackoverflow.com/questions/60249177/e-the-repository-http-ppa-launchpad-net-certbot-certbot-ubuntu-focal-release]

우분투 20.04의 경우 sudo apt-get install certbot python3-certbot-nginx 명령어 사용

#####  2.1 오류 

인증서 발급 과정 중 에러로 인해 실패

#####  2.2 해결 

 [https://growingsaja.tistory.com/487](https://growingsaja.tistory.com/487) 인바운드규칙에서 80 포트를 열어주어야함!


## 2022-11-27
#### [BELLA]
- ERD 설계 완료
- API 리스트업
- RDS 데이터베이스 구축
- dev/prod 서버 구축   


🚨 **[jar 실행 시 port 충돌 에러]**

Web server failed to start. Port 9000 was already in use.

🌟 **실행중인 jar pid kill**

```bash
ps -ef | grep jar
# 실행중인 jar pid

kill 20947
```

#### [MASON]
- ERD 설계 완료
- API 리스트업
- ssl 적용
- dev/prod 서버 구축   

#####  1.1 오류
git pull이 적용 안되는 오류

#####  1.2 오류 해결
git remot add origin
git branch 현재 branch 안나옴
main branch로 이동 = git checkout main 





