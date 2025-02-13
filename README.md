# scheduleManageApp
<챕터 목표>
1. Spring의 핵심 컨셉에 대해 이해하고 설명할 수 있다.
2. BeanValidation을 적용할 수 있다.
3. 인증/인가의 차이점을 설명할 수 있다.
4. Cookie/Session을 적용하여 사용자 인증을 할 수 있다.
5. 영속성 컨텍스트에 대해 설명할 수 있다.
6. JPA를 이용하여 데이터 중심이 아닌 객체 지향적인 개발을 할 수 있다.

<hr>
# API 명세

### 1) 회원가입 API
- URL: /api/users/signup
- Method: POST
- Request Body
```
{
    "username": "user1",
    "email": "user1@example.com",
    "password": "password123"
}
```

- Response
```
{
    "id": 1,
    "username": "user1",
    "email": "user1@example.com",
    "createdAt": "2025-02-11T12:00:00",
    "updatedAt": "2025-02-11T12:00:00"
}
```
### 2) 로그인 API
- URL: /api/users/login
- Method: POST
- Request Body
```
{
    "email": "user1@example.com",
    "password": "password123"
}
```
- Response
```
{
    "message": "로그인 성공"
}
```

### 3) 일정 생성 API
- URL: /api/schedules
- Method: POST
- Request Body
```
{
    "title": "회의 일정",
    "contents": "오후 3시 팀 미팅"
}
```
Response
```
{
    "id": 1,
    "title": "회의 일정",
    "contents": "오후 3시 팀 미팅",
    "user": { "id": 1, "username": "user1" },
    "createdAt": "2025-02-11T12:10:00",
    "updatedAt": "2025-02-11T12:10:00"
}
```
### 4) 댓글 생성 API
- URL: /api/comments
- Method: POST
- Request Body
```
{
    "content": "좋은 일정이네요!",
    "scheduleId": 1
}
```
Response
```
{
    "id": 1,
    "content": "좋은 일정이네요!",
    "user": { "id": 1, "username": "user1" },
    "schedule": { "id": 1, "title": "회의 일정" },
    "createdAt": "2024-02-11T12:15:00"
}
```

<hr>

# 트러블 슈팅 (깃헙)
작업을 새벽에 마무리 했으나, pr이 안 돼서, 

### 1. 원격(main)과 로컬(feat)의 관계가 완전히 끊어진 상태.
- 원격(origin/main)을 가져오지 않은 상태에서 feat을 작업했기 때문이라고  판단했고,<br>
`git checkout -b main origin/main`을 하기 전에 이미 main 브랜치가 있어서 오류가 발생했습니다.<br>
그 상태에서 git reset --hard main을 실행하면서, feat 브랜치의 작업이 초기화 되었습니다.<br>

🚨 git reset --hard main이 위험했던 이유<br>
`git reset --hard main`<br>
이 명령어는 feat 브랜치를 main의 상태로 강제 덮어씌우는 명령입니다.<br>
하지만, main 브랜치에는 아무 코드도 없었음.<br>
결과적으로 feat 브랜치의 코드가 완전히 날아가 버리게 된 것입니다! 😱<br>

### 2. git merge --allow-unrelated-histories 사용했지만, 필요 없었음
`git merge --allow-unrelated-histories`<br>
이 옵션은 서로 다른 커밋 히스토리를 가진 두 브랜치를 병합할 때 사용하는 것임에도 불구하고,<br>
이미 fetch & rebase를 하려던 상황에서는 필요 없었음.<br>
이걸 실행해도 변경된 게 없어서 "이미 업데이트 상태입니다."가 나왔던 것입니다.<br>


### 3. git push origin feat --force로 강제 덮어씌운 게 문제
결국 돌고 돌아 해당 명령어를 통해 '원격 브랜치를 강제로 덮어씌우는' 행위를 했습니다.<br>
즉,` reset --hard main`으로 날려버린 상태를 그대로 github에 업로드한 것이다.<br>
그래서, github에도 코드가 전부 사라졋던 것.<br>

### 정리) 즉, 왜 PR이 안 됐을까?
feat이 main과 연결되지 않은 상태가 되었기 때문<br>
PR을 만들려면 feat 브랜치가 main을 기반으로 만들어져야 함.<br>
하지만 git reset --hard main으로 feat과 main의 연결을 끊어버려서 비교할 수 없게 된 것.<br>

<hr>

그렇다면, 앞으로 어떻게 해야 할 것인가? (개선점 및 해결책)<br>
이제 같은 실수를 반복하지 않도록 안전한 방법을 정리해보겠습니다.

### 앞으로 안전하게 PR을 생성하는 법
🔥 `git reset --hard`는 웬만하면 사용하지 말기!<br>
🌟 항상 fetch 후 rebase를 사용해서 브랜치 정리하기!

```
//GitHub 원격 브랜치 가져오기
git fetch origin

// 최신 main 브랜치를 가져오기
git checkout main
git pull origin main

//최신 main을 기반으로 feat 브랜치 정리하기
git checkout feat
git rebase main  # 여기서 충돌 나면 해결 후 git rebase --continue

//안전하게 원격에 업로드
git push origin feat --force
```

### 결론
1. git reset --hard는 정말 필요할 때만 사용하는 것이 좋다.
→ reset --hard main을 해버려서 feat 브랜치 작업이 다 날아갔던 것.

2. 항상 git fetch origin & git rebase main을 먼저 하기!
→ feat이 main과 연결된 상태로 유지돼야 PR을 만들 수 있음.

3. GitHub에 push할 때는 --force를 주의해서 사용!
→ 실수로 날린 코드가 있다면 git reflog로 복구할 수도 있음.
