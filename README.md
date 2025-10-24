# On&Home (Backend: Spring Boot, Java 21)

이 저장소는 **On&Home** 서버 프로젝트입니다. 프론트 화면 설명은 생략하고, 백엔드 중심으로 정리합니다.

## 바로 보기
- 문서(준비중): `docs/README.md`
- 다이어그램(준비중): `diagrams/` 폴더
- 상태 체크(예): `/actuator/health`  
- API 문서(예): `/swagger-ui.html`

## 팀 체크리스트 (UAT용 요약)
- [ ] 로그인/회원가입 핵심 시나리오 통과 스크린샷
- [ ] API 문서 접속 가능(스웨거)
- [ ] 헬스체크/메트릭 노출 확인
- [ ] 릴리스 노트(CHANGELOG.md) 업데이트

## 프로젝트 개요 (Mermaid 테스트)
```mermaid
flowchart LR
  User-->Web[On&Home 서버(Thymeleaf)]
  Web-->API[Spring Boot API]
  API-->DB[(RDBMS)]
