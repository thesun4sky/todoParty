# todoParty

이 프로젝트는 Spring Framework 기반의 프로젝트로 TODO LIST 기능을 구현하고있는 프로젝트 입니다.

## 디렉토리 구조
```bash
├── src                         # 소스 코드가 위치하는 디렉토리
│   ├── main                    # 메인 소스 코드가 위치하는 디렉토리
│   │   ├── java                # 자바 소스 코드가 위치하는 디렉토리
│   │   │   ├── comm.thesun4sky.todoparty  # 기본 패키지 이름
│   │   │   │   ├── configuration # 스프링 설정 클래스들이 위치하는 디렉토리
│   │   │   │   ├── user        # 유저 관련 클래스(Controller, Service, Repository 등..)가 위치하는 디렉토리
│   │   │   │   ├── todo        # 할일(TODO) 관련 클래스(Controller, Service, Repository 등..)가 위치하는 디렉토리
│   │   │   │   ├── comment     # 댓글 관련 클래스(Controller, Service, Repository 등..)가 위치하는 디렉토리
│   │   │   │   ├── jwt         # JWT(Json Web Token) 관련 클래스들이 위치하는 디렉토리
│   │   │   │   └── SpringBlogApplication.java  # 스프링 애플리케이션의 진입점인 메인 클래스
│   │   └── resources           # 리소스 파일들이 위치하는 디렉토리
│   └── test                    # 테스트 소스 코드가 위치하는 디렉토리
├── build.gradle               # Gradle 빌드 스크립트 파일
├── settings.gradle            # Gradle 설정 파일
└── README.md                  # 프로젝트에 대한 설명이 담긴 마크다운 파일

``` 

## ERD 
https://drawsql.app/teams/teasun/diagrams/springblog-2
![image](https://github.com/thesun4sky/todoParty/assets/17782941/ed4a9aaa-6dd9-4734-8249-bf8d04148589)


## API 명세
https://documenter.getpostman.com/view/530494/2s9Ye8fuaj
![image](https://github.com/thesun4sky/todoParty/assets/17782941/b082a74f-fd32-4c31-a65d-c19631673fdf)
![image](https://github.com/thesun4sky/todoParty/assets/17782941/a714dc5d-794d-4f08-b4fb-c1790a02c07a)
![image](https://github.com/thesun4sky/todoParty/assets/17782941/6e68c15a-9880-4508-8375-96ef0f0eb312)
![image](https://github.com/thesun4sky/todoParty/assets/17782941/14ee1306-9bfd-4da7-8995-5534c067d32c)


## Spring Security 인증
<img width="1000" alt="image (10)" src="https://github.com/thesun4sky/spring-blog/assets/17782941/5e134760-8ac2-499e-8aea-f0d411bd0bd0">
