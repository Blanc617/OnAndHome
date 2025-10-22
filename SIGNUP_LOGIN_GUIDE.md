# 온앤홈 프로젝트 - 로그인 & 회원가입 수정 사항

## 📋 수정 내용 요약

### 1. UserController 작성 ✅
**파일**: `src/main/java/com/onandhome/user/UserController.java`

회원가입, 로그인, 사용자 조회, 정보 수정, 비밀번호 변경, 삭제 등의 REST API 엔드포인트 구현

**주요 API 엔드포인트:**
- `POST /api/user/register` - 회원가입
- `POST /api/user/login` - 로그인
- `GET /api/user/{id}` - 사용자 조회 (ID로)
- `GET /api/user/username/{userId}` - 사용자 조회 (userId로)
- `PUT /api/user/{id}` - 사용자 정보 수정
- `PUT /api/user/{id}/password` - 비밀번호 변경
- `DELETE /api/user/{id}` - 사용자 삭제

---

### 2. 로그인 페이지 수정 ✅
**파일**: `src/main/resources/templates/admin/login.html`

- 원본 형식 유지
- 회원가입 링크 추가
- 에러/로그아웃 메시지 스타일 개선

**로그인 가능 계정:**
- ID: `admin`
- Password: `admin123`

---

### 3. 회원가입 페이지 생성 ✅
**파일**: `src/main/resources/templates/admin/signup.html`

완전한 회원가입 페이지 구현:
- 아이디, 비밀번호, 이메일, 이름, 휴대폰, 성별, 생년월일, 주소 입력
- 클라이언트 유효성 검사
- 비밀번호 일치 확인
- 실시간 피드백

---

### 4. LoginController 수정 ✅
**파일**: `src/main/java/com/onandhome/web/LoginController.java`

- 회원가입 페이지 라우트 추가
- `/admin/signup` GET 메소드 추가

---

### 5. API 테스트 페이지 생성 ✅
**파일**: `src/main/resources/static/api-test.html`

아름다운 UI로 API를 테스트할 수 있는 페이지
- 회원가입 테스트
- 로그인 테스트
- 사용자 조회 테스트

**접근 URL**: `http://localhost:8080/api-test.html`

---

## 🔐 기존 코드 정리

### UserService (기존 - 수정 없음)
이미 회원가입, 로그인, 사용자 관리 등의 비즈니스 로직이 잘 구현되어 있음

**주요 메서드:**
- `register(UserDTO)` - 회원가입
- `login(userId, password)` - 로그인
- `updateUser(UserDTO)` - 정보 수정
- `changePassword()` - 비밀번호 변경

### User Entity (기존 - 수정 없음)
- JPA Entity로 잘 정의됨
- 자동 타임스탐프 관리

### UserRepository (기존 - 수정 없음)
- 필요한 쿼리 메서드 모두 구현됨

### DataInitializer (기존 - 수정 없음)
- 애플리케이션 시작 시 자동으로 관리자 계정 생성
- `admin / admin123` 계정 자동 생성

---

## 🚀 사용 방법

### 1. 애플리케이션 시작
```bash
gradle bootRun
```

### 2. 로그인 페이지 접속
```
http://localhost:8080/admin/login
```

### 3. 테스트 계정으로 로그인
- ID: `admin`
- Password: `admin123`

### 4. 회원가입하기
로그인 페이지에서 "회원가입" 링크 클릭

### 5. API 테스트하기
```
http://localhost:8080/api-test.html
```

---

## 📡 API 요청/응답 예시

### 회원가입 요청
```json
POST /api/user/register

{
  "userId": "user123",
  "password": "password123",
  "email": "user@example.com",
  "username": "홍길동",
  "phone": "010-1234-5678",
  "gender": "M",
  "birthDate": "1990-01-01",
  "address": "서울시 강남구"
}
```

### 회원가입 성공 응답
```json
{
  "success": true,
  "message": "회원가입 성공",
  "data": {
    "id": 3,
    "userId": "user123",
    "email": "user@example.com",
    "username": "홍길동",
    "phone": "010-1234-5678",
    "gender": "M",
    "birthDate": "1990-01-01",
    "address": "서울시 강남구",
    "role": "USER",
    "active": true,
    "createdAt": "2024-10-20T14:30:00",
    "updatedAt": "2024-10-20T14:30:00"
  }
}
```

### 로그인 요청
```json
POST /api/user/login

{
  "userId": "admin",
  "password": "admin123"
}
```

### 로그인 성공 응답
```json
{
  "success": true,
  "message": "로그인 성공",
  "data": {
    "id": 1,
    "userId": "admin",
    "email": "admin@onandhome.com",
    "username": "관리자",
    "phone": "010-1234-5678",
    "gender": "M",
    "birthDate": "1990-01-01",
    "address": "서울시 강남구",
    "role": "ADMIN",
    "active": true,
    "createdAt": "2024-10-20T10:00:00",
    "updatedAt": "2024-10-20T10:00:00"
  }
}
```

---

## ✅ 확인 사항

- [x] UserController REST API 완성
- [x] 회원가입 페이지 작성
- [x] 로그인 페이지에 회원가입 링크 추가
- [x] LoginController에 회원가입 라우트 추가
- [x] API 테스트 페이지 작성
- [x] admin/admin123으로 로그인 가능
- [x] 회원가입 기능 정상 작동

---

## 🔧 데이터베이스 설정

DB는 `application.properties`에 이미 설정되어 있습니다:
- URL: `jdbc:mysql://localhost:3306/onandhome`
- Username: `admin`
- Password: `1111`

JPA는 `ddl-auto=update`로 설정되어 있으므로 테이블이 자동 생성됩니다.

---

## 📝 디버깅 팁

**만약 로그인이 안 된다면:**

1. MySQL 데이터베이스 확인
   ```sql
   SELECT * FROM users;
   ```

2. 로그 확인
   - `/admin/check-login` 접속해서 로그인 상태 확인

3. 콘솔 로그 확인
   - `log.info()` 메시지로 진행 상황 추적

---

## 📞 지원되는 기능

✅ 회원가입 (회원가입 페이지)
✅ 로그인 (로그인 페이지)
✅ 사용자 조회 (API)
✅ 사용자 정보 수정 (API)
✅ 비밀번호 변경 (API)
✅ 사용자 삭제 (API)
✅ 로그아웃 (로그인 페이지)

---

## 🎯 다음 단계 (선택사항)

1. JWT 토큰 인증 추가
2. 이메일 중복 검사
3. 아이디 중복 검사
4. 비밀번호 재설정
5. 프로필 이미지 업로드
6. 2단계 인증

---

**작성일**: 2024-10-20
**최종 수정**: 2024-10-20
