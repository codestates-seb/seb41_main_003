# SEB_41_MAIN_PROJECT

부트캠프를 통해 배웠던 기술 스택과 Pre-Project에서 경험한 내용을 토대로 실제 서비스를 기획 단계부터 배포까지 개발합니다.

# 과외차이

![readme](https://user-images.githubusercontent.com/77656241/215430685-4fb5754e-3410-4d39-8300-436029e08bd5.png)

> 매칭으로 끝내지 않는 비대면 통합 과외 관리 서비스, 과외차이

- 비대면 특화 과외 매칭 및 관리 서비스
- 서비스 내에서 매칭된 과외는 과외 현황을 확인할 수 있는 관리 기능 제공

## 프로젝트 기간

2023.01.03 ~

## 배포 링크

**[👩‍🏫과외차이](https://tutordiff.site)**

- 테스트 계정
  - 튜터
    - ID: tutor@tutor.com
    - PW: 1111111t
    - 2nd PW: 1234
  - 튜티
    - ID: tutuee@tutee.com
    - PW: 1111111t
    - 2nd PW: 1234

**[📝API REST Docs](https://api-tutordiff.site)**

## 기술 스택

### FrontEnd

<img src="https://img.shields.io/badge/react-61DAFB?style=for-the-badge&logo=react&logoColor=black"> <img src="https://img.shields.io/badge/redux-3578e5.svg?style=for-the-badge&logo=data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIGZpbGw9Im5vbmUiIHdpZHRoPSIyMzY4IiB2aWV3Qm94PSIzMCAxMSAyNy41IDc4Ij48Y2lyY2xlIGN4PSI0My41IiBjeT0iMTguNSIgZmlsbD0iI2ZmZiIgcj0iNy41Ii8+PGNpcmNsZSBjeD0iNDMuNSIgY3k9IjgxLjUiIGZpbGw9IiNmZmYiIHI9IjcuNSIvPjxnIHN0cm9rZT0iI2ZmZiIgc3Ryb2tlLXdpZHRoPSIzIj48cGF0aCBkPSJNNDMuOTk5IDI1QzQyLjUgMzcgNTcuNSAzNCA1Ny41IDQyLjVjMCA1LTUuODc4IDYuMzY1LTEzLjUwMSA3QzM3Ljk5OSA1MCAzMCA1MCAzMCA1OHMxNiA1LjUgMTMuOTk5IDE3TTM0LjEzMiAzMy4zNTNjMCAxNS4yODkgMjMuMTUgMTguMjg5IDIzLjE1IDMyLjYyIi8+PC9nPjwvc3ZnPg==&logoColor=white"> <img src="https://img.shields.io/badge/postcss-DD3A0A.svg?style=for-the-badge&logo=PostCSS&logoColor=white"> <img src="https://img.shields.io/badge/html5-E34F26?style=for-the-badge&logo=html5&logoColor=white"> <img src="https://img.shields.io/badge/css-1572B6?style=for-the-badge&logo=css3&logoColor=white"> <img src="https://img.shields.io/badge/javascript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black"> <img src="https://img.shields.io/badge/figma-F24E1E?style=for-the-badge&logo=Figma&logoColor=white">
<img src="https://img.shields.io/badge/eslint-4B32C3?style=for-the-badge&logo=ESLint&logoColor=white"> <img src="https://img.shields.io/badge/stylelint-263238?style=for-the-badge&logo=stylelint&logoColor=white"> <img src="https://img.shields.io/badge/prettier-F7B93E?style=for-the-badge&logo=Prettier&logoColor=black">

### Backend

<img src="https://img.shields.io/badge/Spring Boot-6DB33F?style=for-the-badge&logo=Spring Boot&logoColor=white"> <img src="https://img.shields.io/badge/Spring DATA JPA-6DB33F?style=for-the-badge&logo=Spring&logoColor=white"> <img src="https://img.shields.io/badge/Spring Security-6DB33F?style=for-the-badge&logo=Spring Security&logoColor=white"> <img src="https://img.shields.io/badge/Mapstruct-e74f29?style=for-the-badge&logo=&logoColor=white"> <img src="https://img.shields.io/badge/JUnit5-25A162?style=for-the-badge&logo=JUnit5&logoColor=white"> <img src="https://img.shields.io/badge/Mockito-6c9d1a?style=for-the-badge&logo=&logoColor=white"> <img src="https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=Gradle&logoColor=white"> <img src="https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZpZXdCb3g9IjAgMCAxMDEgMTAxIj48ZyBmaWxsPSJub25lIiBmaWxsLXJ1bGU9ImV2ZW5vZGQiPjxwYXRoIGZpbGw9IiNGRkYiIGQ9Ik01Ny44IDI3LjIgNTcuNy4zaC0xNWwuMSAyNi45IDcuNSAxMC4zIDcuNS0xMC4zWk00Mi44IDczLjN2MjdoMTV2LTI3TDUwLjMgNjNsLTcuNSAxMC4zWiIvPjxwYXRoIGZpbGw9IiMwMEYyRTYiIGQ9Im01Ny44IDczLjMgMTUuOCAyMS44IDEyLjEtOC44LTE1LjgtMjEuOC0xMi4xLTMuOXYxMi43Wk00Mi44IDI3LjIgMjYuOSA1LjRsLTEyLjEgOC44TDMwLjYgMzZsMTIuMiAzLjlWMjcuMloiLz48cGF0aCBmaWxsPSIjMDBCOUYxIiBkPSJNMzAuNiAzNiA1IDI3LjcuNCA0MS45IDI2IDUwLjNsMTIuMS00TDMwLjYgMzZaTTYyLjQgNTQuMmw3LjUgMTAuMyAyNS42IDguMyA0LjYtMTQuMi0yNS42LTguMy0xMi4xIDMuOVoiLz48cGF0aCBmaWxsPSIjRDYzQUZGIiBkPSJtNzQuNSA1MC4zIDI1LjYtOC40LTQuNi0xNC4yTDY5LjkgMzZsLTcuNSAxMC4zIDEyLjEgNFpNMjYgNTAuMy40IDU4LjYgNSA3Mi44bDI1LjYtOC4zIDcuNS0xMC4zTDI2IDUwLjNaIi8+PHBhdGggZmlsbD0iI0ZCMDE1QiIgZD0iTTMwLjYgNjQuNSAxNC44IDg2LjNsMTIuMSA4LjggMTUuOS0yMS44VjYwLjZsLTEyLjIgMy45Wk02OS45IDM2bDE1LjgtMjEuOC0xMi4xLTguOC0xNS44IDIxLjh2MTIuN0w2OS45IDM2WiIvPjwvZz48L3N2Zz4=&logoColor=white"> <img src="https://img.shields.io/badge/OAuth2-eea738?style=for-the-badge&logo=&logoColor=white"> <img src="https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=MySQL&logoColor=white"> <img src="https://img.shields.io/badge/Redis-DC382D?style=for-the-badge&logo=Redis&logoColor=white"> <img src="https://img.shields.io/badge/H2 DataBase-09476b?style=for-the-badge&logo=data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZpZXdCb3g9IjAgMCA2MS44MTUgNjEuODE1Ij48cGF0aCBkPSJNMTE4Ljg4OCAxNDIuMzE2Yy4zNjUtLjA0LjcyNS0uMDU5IDEuMDgtLjA1OSAxLjc4OCAwIDMuMTU3LjQwMyA0LjExIDEuMjEuOTUxLjgwNyAxLjQyNyAxLjk1MSAxLjQyNyAzLjQzMyAwIDEuMjM2LS4zNyAyLjQ4Ni0xLjExMSAzLjc1LS43NDIgMS4yNjUtMS45OTIgMi43NDctMy43NSA0LjQ0OC0xLjE3NyAxLjE0Ny0yLjc0MyAyLjUxMy00LjY5OCA0LjA5N2ExMzUuNjY2IDEzNS42NjYgMCAwIDEtNS44NTMgNC40OTJ2NS4zNjJoMjUuNDg0di02LjIxMmgtMTQuNTg0YTEzMy45NiAxMzMuOTYgMCAwIDAgMi44NTUtMi4xNDggNTcuMTggNTcuMTggMCAwIDAgMy45OS0zLjM2OGMyLjEwNy0xLjk2MiAzLjY3My0zLjg2NiA0LjY5OC01LjcxMSAxLjAyNC0xLjg0NiAxLjUzNy0zLjc5NCAxLjUzNy01Ljg0MyAwLTMuMTI1LTEuMDY5LTUuNTQtMy4yMDUtNy4yNDktMi4xMzYtMS43MDctNS4yNC0yLjU2MS05LjMwOC0yLjU2MS0uODkyIDAtMS43ODIuMDQ0LTIuNjcyLjEzMnYtMTIuNDAyaC04LjUzNFYxMzUuOEg5Ny43NTN2LTEyLjExM2gtOC41MzR2MzMuMDkyaDguNTM0VjE0Mi4yaDEyLjYwMXYxNC41OGguMDk3Yy44LS42MiAxLjYtMS4yNTUgMi40MDEtMS45MDQgMS45NTUtMS41ODQgMy41MjEtMi45NSA0LjY5OC00LjA5OC40ODUtLjQ3LjkzMS0uOTIxIDEuMzM5LTEuMzU3em0tMTMuMjg2IDI1LjgzMWMtMTYuMDYgMC0yOS4xMzQtMTMuMDczLTI5LjEzNC0yOS4xMzQgMC0xNi4wNDggMTMuMDYtMjkuMDczIDI5LjEzNC0yOS4wNzMgMTYuMDYgMCAyOS4wNzMgMTMuMDEzIDI5LjA3MyAyOS4wNzN2LjkwM2gxLjgwNHYtLjkwM2MwLTE3LjA1Ny0xMy44Mi0zMC44NzctMzAuODc3LTMwLjg3Ny0xNy4wNjkgMC0zMC45MzggMTMuODMxLTMwLjkzOCAzMC44NzcgMCAxNy4wNTggMTMuODggMzAuOTM4IDMwLjkzOCAzMC45MzhoLjkwMnYtMS44MDR6IiBzdHlsZT0iZmlsbDojZmZmO2ZpbGwtb3BhY2l0eToxO3N0cm9rZS13aWR0aDouOTAyMTk4IiB0cmFuc2Zvcm09InRyYW5zbGF0ZSgtNzQuNjY0IC0xMDguMTM2KSIvPjwvc3ZnPg==&logoColor=white"> <img src="https://img.shields.io/badge/QueryDSL-008ad0?style=for-the-badge&logo=data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZlcnNpb249IjEuMCIgd2lkdGg9IjIwMC4wMDAwMDBwdCIgaGVpZ2h0PSIyMDAuMDAwMDAwcHQiIHZpZXdCb3g9IjAgMCAyMDAuMDAwMDAwIDIwMC4wMDAwMDAiIHByZXNlcnZlQXNwZWN0UmF0aW89InhNaWRZTWlkIG1lZXQiPg0KPG1ldGFkYXRhPg0KQ3JlYXRlZCBieSBwb3RyYWNlIDEuMTYsIHdyaXR0ZW4gYnkgUGV0ZXIgU2VsaW5nZXIgMjAwMS0yMDE5DQo8L21ldGFkYXRhPg0KPGcgdHJhbnNmb3JtPSJ0cmFuc2xhdGUoMC4wMDAwMDAsMjAwLjAwMDAwMCkgc2NhbGUoMC4xMDAwMDAsLTAuMTAwMDAwKSIgZmlsbD0iI2ZmZmZmZiIgc3Ryb2tlPSJub25lIj4NCjxwYXRoIGQ9Ik02NjAgMTg0OCBjLTUyIC0zNiAtNzMgLTcxIC03OCAtMTMyIC0xMSAtMTM4IDEyNiAtMjI4IDI0OSAtMTYzIDExNiA2MSAxMjEgMjE5IDEwIDI5NCAtNDcgMzIgLTEzNSAzMiAtMTgxIDF6Ii8+DQo8cGF0aCBkPSJNMTA2NiAxODM5IGMtOTkgLTg2IC00MSAtMjQzIDg5IC0yNDEgODUgMSAxMzkgNTUgMTM3IDEzOCAtMSA4NyAtNTEgMTM0IC0xNDEgMTM0IC00MiAwIC01NiAtNSAtODUgLTMxeiIvPg0KPHBhdGggZD0iTTE0MTQgMTY5MSBjLTU4IC0yNSAtNzQgLTExOSAtMjggLTE2NSAyMCAtMjAgMzcgLTI2IDY4IC0yNiA1MSAwIDgxIDIwIDk2IDY0IDEyIDM4IC0yIDg5IC0zMyAxMTQgLTIwIDE3IC03NyAyNCAtMTAzIDEzeiIvPg0KPHBhdGggZD0iTTMwNyAxNTk2IGMtMTMzIC00OSAtMTY0IC0yMDQgLTYyIC0zMDMgMTQ4IC0xNDIgMzc0IDI5IDI4NCAyMTUgLTM5IDc5IC0xMzggMTE5IC0yMjIgODh6Ii8+DQo8cGF0aCBkPSJNMTU5NSAxNDUwIGMtMjggLTMxIC0yNCAtNzkgOSAtMTA3IDMzIC0yOCA1NiAtMjkgOTAgLTIgMzUgMjcgMzcgOTAgNCAxMTMgLTMyIDIyIC04MSAyMCAtMTAzIC00eiIvPg0KPHBhdGggZD0iTTEzNCAxMTAxIGMtNTcgLTM0IC04NyAtODUgLTg3IC0xNDQgMCAtMTYyIDE3NCAtMjM2IDI5MSAtMTI0IDc5IDc2IDYwIDIxMCAtMzYgMjY1IC01MSAyOCAtMTI1IDI5IC0xNjggM3oiLz4NCjxwYXRoIGQ9Ik0xMTIxIDg3MiBjLTQwIC0yMSAtNzEgLTczIC03MSAtMTE3IDAgLTQ0IDMxIC05NiA3MSAtMTE3IDM5IC0yMSA2NyAtMjIgMTE5IC03IDQ4IDE0IDg2IDUgMTAyIC0yNCA4IC0xMyAxMyAtNTkgMTMgLTExNyAwIC01MiA2IC0xMTQgMTMgLTEzNyAyMCAtNjggODIgLTEzNyAxNTQgLTE3MiA4MyAtNDAgMTM5IC00NyAyMTcgLTI3IDEzOSAzNSAyMjYgMTM3IDIzOCAyNzUgMjMgMjc2IC0yNjEgNDM1IC01MDQgMjgxIC04MyAtNTMgLTEzNCAtMzUgLTE1MyA1MyAtMjEgMTAzIC0xMTcgMTU1IC0xOTkgMTA5eiIvPg0KPHBhdGggZD0iTTI5MiA2NDIgYy00OSAtMjcgLTcyIC02OCAtNzIgLTEzMCAwIC00NSA0IC01NyAzNCAtODkgMzAgLTMzIDM5IC0zNyA5MyAtNDEgNTMgLTQgNjIgLTEgOTEgMjMgNDYgMzggNjUgODAgNTkgMTI2IC0xMyAxMDAgLTExOCAxNTcgLTIwNSAxMTF6Ii8+DQo8cGF0aCBkPSJNNTg4IDM1NCBjLTM1IC0xOCAtNDggLTQzIC00OCAtODkgMCAtNDcgMTMgLTcxIDUwIC05MCA2MSAtMzEgMTI0IC05IDE1MCA1NCAxNyA0MiAtMiA5NyAtNDQgMTIyIC0zNyAyMiAtNzEgMjQgLTEwOCAzeiIvPg0KPHBhdGggZD0iTTg3NSAyMzUgYy0zNiAtMzUgLTMzIC03NiA3IC0xMTAgNTIgLTQ0IDExOCAtMTMgMTE4IDU2IDAgNzAgLTc3IDEwMyAtMTI1IDU0eiIvPg0KPC9nPg0KPC9zdmc+&logoColor=white"> <img src="https://img.shields.io/badge/Spring Rest Docs-6DB33F?style=for-the-badge&logo=&logoColor=white">

### Server Infra

<img src="https://img.shields.io/badge/Amazon EC2-FF9900?style=for-the-badge&logo=Amazon EC2&logoColor=white"> <img src="https://img.shields.io/badge/Amazon S3-569A31?style=for-the-badge&logo=Amazon S3&logoColor=white"> <img src="https://img.shields.io/badge/Amazon CloudFront-FF9900?style=for-the-badge&logo=data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSIyMDc4IiBoZWlnaHQ9IjI1MDAiIHZpZXdCb3g9IjAgMCAyNTYgMzA4IiBwcmVzZXJ2ZUFzcGVjdFJhdGlvPSJ4TWlkWU1pZCI+PHBhdGggZD0iTTE2Ni4wMSAxMjcuNDY1bDU5LjIwMi02LjMxNCAzMC42NzYgNi4zNjkuMTExLjA2OC01Ni41NTggMy4zNzQtMzMuNDg0LTMuNDQyLjA1My0uMDU1eiIgZmlsbD0iIzVFMUYxOCIvPjxwYXRoIGQ9Ik0xNjUuOTU4IDEyNy41MjFsNTkuMjA0LTQuNjI1LjQwNy0uNTkzLjAwMi03My4wNDQtLjQwNy0uODM1LTU5LjIwNiAxNS4zOTd2NjMuNyIgZmlsbD0iIzhDMzEyMyIvPjxwYXRoIGQ9Ik0yNTYgMTI3LjU5bC0zMC44MzgtNC42OTQuMDAyLTc0LjQ3MiAzMC44MzUgMTUuNDI4LjAwMSA2My43MzgiIGZpbGw9IiNFMDUyNDMiLz48cGF0aCBkPSJNMTY1Ljk1OCAxODAuMTE1bC44NjUuNjA2IDU4LjM0OSAzLjk1OSAyOS43NzItMy45NTkgMS4wNTUtLjU3OS01Ni41NTctMy40NzUtMzMuNDg0IDMuNDQ4IiBmaWxsPSIjRjJCMEE5Ii8+PHBhdGggZD0iTTE2NS45NTggMTgwLjExNWw1OS4yMTQgNC40MDUuMTgzLjI0Ni0uMDQ1IDczLjk0Mi0uMTQ2LjM4Ni01OS4yMDYtMTUuMjc1di02My43MDQiIGZpbGw9IiM4QzMxMjMiLz48cGF0aCBkPSJNMjU1Ljk5OSAxODAuMTQybC0zMC44MjcgNC4zNzgtLjAwOCA3NC41NzQgMzAuODM1LTE1LjMwOXYtNjMuNjQzIiBmaWxsPSIjRTA1MjQzIi8+PGc+PHBhdGggZD0iTTg5LjExOSAxMjcuNDA4bC01OC41My01LjAxNEwuMTk1IDEyNy40NGwtLjE5My4xNTEgNTYuNTU3IDMuMzc0IDMyLjg3NC0zLjM3Ni0uMzE0LS4xOHoiIGZpbGw9IiM1RTFGMTgiLz48cGF0aCBkPSJNLjAwMiAxMjcuNTlsMzAuNTU3LTQuNDc0Ljg5OS0uNjM3di03My4ybC0uODk5LS44NTVMLjAwMiA2My44NTV2NjMuNzM1IiBmaWxsPSIjOEMzMTIzIi8+PHBhdGggZD0iTTg5LjQzMyAxMjcuNTg4bC01OC44NzQtNC40NzJWNDguNDI0bDU4Ljg3NyAxNS4zOTctLjAwMyA2My43NjciIGZpbGw9IiNFMDUyNDMiLz48L2c+PGc+PHBhdGggZD0iTTg5LjQzMyAxODAuMTQybC0xLjM1MS45ODUtNTcuNTIzIDQuMzEzLTI5LjU4My00LjMxMy0uOTc2LS45ODUgNTYuNTU5LTMuNDc1IDMyLjg3NCAzLjQ3NSIgZmlsbD0iI0YyQjBBOSIvPjxwYXRoIGQ9Ik0wIDE4MC4xNDJsMzAuNTU4IDQuMzQuNzc3Ljk1Mi4wODMgNzIuMzMzLS44NTkgMS4zMjdMLjAwMiAyNDMuNzg1IDAgMTgwLjE0MiIgZmlsbD0iIzhDMzEyMyIvPjxwYXRoIGQ9Ik04OS40MzMgMTgwLjE0MmwtNTguODc1IDQuMzQuMDAxIDc0LjYxMiA1OC44NzQtMTUuMjc1di02My42NzciIGZpbGw9IiNFMDUyNDMiLz48L2c+PGc+PHBhdGggZD0iTTE4OS4xMDQgMTg5LjY2bC02MS4xMDMtNi4wMjYtNjEuNzEyIDYuMDI3Ljg3LjczNSA2MC41NDEgOS42MTcgNjAuNTMzLTkuNjE3Ljg3MS0uNzM2IiBmaWxsPSIjRjJCMEE5Ii8+PHBhdGggZD0iTTY2LjI4OSAxODkuNjYxbDYxLjQxMSA4Ljk5OC42MjMuODMzLjA3OSAxMDYuOTU5LS43MDIgMS4xODYtNjEuNDExLTMwLjcwNnYtODcuMjciIGZpbGw9IiM4QzMxMjMiLz48cGF0aCBkPSJNMTg5LjEwNCAxODkuNjZsLTYxLjQwNCA4Ljk5OXYxMDguOTc4bDYxLjQwNS0zMC43MDYtLjAwMS04Ny4yNzEiIGZpbGw9IiNFMDUyNDMiLz48L2c+PGc+PHBhdGggZD0iTTEyOC4wMDEgMTIzLjkzM2wtNjEuNzEyLTUuOTU4LjE1OS0uMDkxIDYxLjI0OS05LjUwMiA2MS4yMjIgOS41MjQuMTg2LjA2OS02MS4xMDQgNS45NTh6IiBmaWxsPSIjNUUxRjE4Ii8+PHBhdGggZD0iTTY2LjI4OSAxMTcuOTc1bDYxLjQxMS04Ljk0OS4yOTQtLjI2OEwxMjcuODU4LjE2NSAxMjcuNyAwIDY2LjI4OSAzMC43MDl2ODcuMjY2IiBmaWxsPSIjOEMzMTIzIi8+PHBhdGggZD0iTTE4OS4xMDUgMTE3Ljk3NWwtNjEuNDA1LTguOTQ5VjBsNjEuNDA1IDMwLjcwOXY4Ny4yNjYiIGZpbGw9IiNFMDUyNDMiLz48L2c+PC9zdmc+&logoColor=white"> <img src="https://img.shields.io/badge/ACM-aee85c?style=for-the-badge&logo=data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHhtbG5zOnhsaW5rPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5L3hsaW5rIiB2aWV3Qm94PSIwIDAgODUgODUiIGZpbGw9IiNmZmYiIGZpbGwtcnVsZT0iZXZlbm9kZCIgc3Ryb2tlPSIjMDAwIiBzdHJva2UtbGluZWNhcD0icm91bmQiIHN0cm9rZS1saW5lam9pbj0icm91bmQiPjx1c2UgeGxpbms6aHJlZj0iI0EiIHg9IjIuNSIgeT0iMi41Ii8+PHN5bWJvbCBpZD0iQSIgb3ZlcmZsb3c9InZpc2libGUiPjxnIHN0cm9rZT0ibm9uZSI+PHBhdGggZD0iTTgwIDQ3LjAwNWwtNDAuMDEyIDQuOTYyVjI3Ljk5TDgwIDMyLjk1MnYxNC4wNTN6IiBmaWxsPSIjNzU5YzNlIi8+PHBhdGggZD0iTTAgNDcuMDA1bDM5Ljk4OCA0Ljk2MlYyNy45OUwwIDMyLjk1MnYxNC4wNTN6IiBmaWxsPSIjNGI2MTJjIi8+PHBhdGggZD0iTTEwIDBoNjB2MTVIMTB6IiBmaWxsPSIjNjQ4MzM5Ii8+PHBhdGggZD0iTTY1IDIwbC00OC45NzYuMjEyTDEwIDE1aDYwbC01IDV6IiBmaWxsPSIjM2M0OTI5Ii8+PHBhdGggZD0iTTEwIDY1aDYwdjE1SDEweiIgZmlsbD0iIzY0ODMzOSIvPjxwYXRoIGQ9Ik02NSA2MEgxNWwtNSA1aDYwbC01LTV6IiBmaWxsPSIjYjdjYTlkIi8+PC9nPjwvc3ltYm9sPjwvc3ZnPg==&logoColor=white"> <img src="https://img.shields.io/badge/ElastiCache-181717?style=for-the-badge&logo=data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHhtbG5zOnhsaW5rPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5L3hsaW5rIiB3aWR0aD0iMjU2cHgiIGhlaWdodD0iMzA4cHgiIHZpZXdCb3g9IjAgMCAyNTYgMzA4IiB2ZXJzaW9uPSIxLjEiIHByZXNlcnZlQXNwZWN0UmF0aW89InhNaWRZTWlkIj4NCgk8Zz4NCgkJPHBvbHlnb24gZmlsbD0iIzk5QkNFMyIgcG9pbnRzPSIwLjgyMTcgMjA3LjYwNDIgMTI3Ljk5OTcgMzA3LjIwMDIgMjU1LjE3ODcgMjA3LjYwNDIgMTI3Ljk5NTcgMTkxLjc5MjIiLz4NCgkJPHBvbHlnb24gZmlsbD0iIzE5NDg2RiIgcG9pbnRzPSIxMjcuOTk5OCAwLjAwMDMgLTAuMDAwMiA5OS41OTEzIDEyNy4xNzQ4IDExNS40MDQzIDI1NS45OTk4IDk5LjU5NjMiLz4NCgkJPHBvbHlnb24gZmlsbD0iIzFGNUI5OSIgcG9pbnRzPSIxMjcuOTk5OCAwLjAwMDMgLTAuMDAwMiA2My41OTEzIC0wLjAwMDIgOTkuNTk2MyAxMjcuOTk5OCA2MS40NDEzIi8+DQoJCTxwb2x5Z29uIGZpbGw9IiMxRjVCOTkiIHBvaW50cz0iMCAyNDMuNjA4NyAxMjggMzA3LjE5OTcgMTI4IDI0NS43NTc3IDAgMjA3LjYwODciLz4NCgkJPHBvbHlnb24gZmlsbD0iIzFGNUI5OSIgcG9pbnRzPSIxMjggMjAwLjA1MzcgNzIuNTMzIDE5MS45Mjg3IDcyLjUzMyAxMTUuODkxNyAxMjggMTA3Ljg5MTcgMTI5LjMwOSAxMTAuNDIzNyAxMjkuMTcxIDE5Ny41MjI3Ii8+DQoJCTxwb2x5Z29uIGZpbGw9IiM1Mjk0Q0YiIHBvaW50cz0iMTI3Ljk5OTggMC4wMDAzIDEyNy45OTk4IDYxLjQ0MTMgMjU1Ljk5OTggOTkuNTk2MyAyNTUuOTk5OCA2My41OTEzIi8+DQoJCTxwb2x5Z29uIGZpbGw9IiM1Mjk0Q0YiIHBvaW50cz0iMTI3Ljk5OTggMjQ1Ljc1NzkgMTI3Ljk5OTggMzA3LjE5OTkgMjU1Ljk5OTggMjQzLjYwODkgMjU1Ljk5OTggMjA3LjYwODkiLz4NCgkJPHBvbHlnb24gZmlsbD0iIzUyOTRDRiIgcG9pbnRzPSIxMjggMjAwLjA1MzcgMTgzLjQ2NyAxOTEuOTI4NyAxODMuNDY3IDExNS44OTE3IDEyOCAxMDcuODkxNyIvPg0KCTwvZz4NCjwvc3ZnPg==&logoColor=white"> <img src="https://img.shields.io/badge/Amazon RDS-527FFF?style=for-the-badge&logo=Amazon RDS&logoColor=white"> <img src="https://img.shields.io/badge/Nginx-009639?style=for-the-badge&logo=Nginx&logoColor=white">
<img src="https://img.shields.io/badge/Certbot-e11b22?style=for-the-badge&logo=&logoColor=white"> <img src="https://img.shields.io/badge/GitHub Actions-2088FF?style=for-the-badge&logo=GitHub Actions&logoColor=white"> <img src="https://img.shields.io/badge/Github Secret-ef5033?style=for-the-badge&logo=data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZpZXdCb3g9IjAgMCA0MzEuOTMgNDMxLjc4Ij48ZyBkYXRhLW5hbWU9IuugiOydtOyWtCAyIj48cGF0aCBkPSJNMTIzIDBjNiAxLjQ4IDEyLjE3IDIuNzMgMTguMTUgNC40OGExMDkuMTggMTA5LjE4IDAgMCAxIDQ3IDI4LjI1YzkuNDMgOS40MSAxOC45MyAxOC43NiAyOC4yMSAyOC4zMiAyLjMzIDIuNCAzLjU3IDIuMjUgNS44NyAwIDEyLjY0LTEyLjU0IDI2LjI1LTEyLjQgMzguODMuMTdMNDIyLjkgMjIzYzEyIDEyIDEyIDI2LjMuMTQgMzguMlEzNDIuMSAzNDIuMDkgMjYxLjE4IDQyM2MtNCA0LTguMiA3LjMyLTE0IDcuOS0zIDEuMTMtNi4yMS0uNDMtOS4xNy44OWgtMWMtNy41OC0uNTUtMTIuODctNC44Mi0xOC4wNy0xMFExMzguNDcgMzQxIDU3LjggMjYwLjU4Yy0xMC44LTEwLjc1LTExLjI0LTI1LjY0LTEuMjUtMzcuMTYgMy4xLTMuNTcgMy4xMi00Ljg0LS40Mi04LjQ0LTguODktOS0xOC0xNy43OC0yNi44LTI2LjkxLTExLjc5LTEyLjI4LTIwLjg1LTI2LjI5LTI2LTQyLjY4QzEuMSAxMzguMzcgMS40NyAxMzEgMCAxMjMuOTRWOTVjMS40Mi0xMC4xOSAyLTIwLjQ3IDYuNDgtMzAuMUMyMC41MyAzNC4zOSA0MyAxMy4zNyA3NSAyLjkxIDgxLjgxLjcgODkgMS4yNCA5NiAwWm0xNjguNTggMjI4LjkyYzIuNjggMi42IDMuMDYgNC43NiAyLjE2IDguMTYtMy4yNCAxMi4yMiAyLjQyIDI1LjE4IDEzLjQ5IDMxLjY1YTI4LjkxIDI4LjkxIDAgMCAwIDQwLjA4LTM4LjU3Yy02LTExLjI0LTE4LjgxLTE3LjQ1LTMxLjE0LTE0LjY2LTMuMjguNzQtNS4yLS4wOC03LjQtMi4zMnEtMTcuMzYtMTcuNjItMzUtMzVjLTIuNjItMi41Ni0zLjQ5LTQuNzgtMi43OC04LjU2YTI4LjUyIDI4LjUyIDAgMCAwLTMzLjY5LTMzLjIzYy0zLjI5LjY4LTUuMTctLjA5LTcuMzgtMi4zMVExOTQgOTggMTU3Ljg2IDYyYy0xMS40OS0xMS41LTI1LjMzLTE4LjM1LTQxLjQ3LTE5Ljg4LTI5LjQ4LTIuOC01Ni4xIDEyLjY4LTY4LjUxIDM5LjM4LTExLjkgMjUuNi02LjI3IDU1Ljc1IDE0LjI5IDc2LjQzIDkuMjggOS4zMyAxOC43NSAxOC40OCAyNy44NCAyOCAyLjkyIDMuMDcgNC40MSAyLjg4IDcuMyAwIDI0LTI0LjI2IDQ4LjI3LTQ4LjMyIDcyLjM0LTcyLjU0IDIuMzUtMi4zNyAzLjcxLTIuOCA2LjI4LS4xOHExOC41MiAxOC45MyAzNy40NSAzNy40NmMyLjA5IDIgMi4zNCAzLjc4IDEuNTcgNi41YTI4LjA4IDI4LjA4IDAgMCAwIDEzLjMgMzIuNTFjMy4wNyAxLjcyIDMuNjIgMy42NSAzLjYxIDYuNzJxLS4xMiA0Ny40NiAwIDk0LjkzYzAgMy4xNi0uNzUgNS0zLjcxIDYuNjZhMjguMjkgMjguMjkgMCAwIDAtMTMuNTQgMzEuNTkgMjguODUgMjguODUgMCAwIDAgNTUuNTQgMi4yMyAyOC4yMSAyOC4yMSAwIDAgMC0xMC45NS0zMi41NiA3LjU0IDcuNTQgMCAwIDEtMy44MS03LjI1Yy4xLTMxIC4wNi02MiAuMDctOTIuOTMgMC0xLjQ3LjEyLTIuOTQuMjUtNS45IDEyLjU0IDEyLjUxIDI0LjA1IDI0LjI5IDM1Ljg3IDM1Ljc1WiIgZGF0YS1uYW1lPSLroIjsnbTslrQgMSIgc3R5bGU9ImZpbGw6I2ZmZiIvPjwvZz48L3N2Zz4=&logoColor=white">

### Team Tools

<img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white"> <img src="https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white"> <img src="https://img.shields.io/badge/notion-000000?style=for-the-badge&logo=notion&logoColor=white"> <img src="https://img.shields.io/badge/discord-5865F2?style=for-the-badge&logo=discord&logoColor=white">

## 프로젝트 소개

### 회원 인증

https://user-images.githubusercontent.com/77656241/215424569-1b7234b1-9617-4080-8d0b-77bf1299d389.mp4

서비스 자체 회원은 물론 OAuth를 이용한 구글/카카오 계정을 통해 회원가입 및 로그인을 할 수 있습니다.

### 매칭 서비스

https://user-images.githubusercontent.com/77656241/215424636-3813c414-d4c2-4c85-9955-9ac68626eb03.mp4

원하는 상대에 대한 정보를 입력해 등록하고 다른 사람들이 등록한 프로필을 확인할 수 있습니다. 필터나 검색을 이용해 원하는 상대를 찾을 수 있습니다.

여러 과외나 학생을 관리하기 위한 다중 프로필 기능을 지원하며, 서비스 내에서 매칭을 할 수 있도록 메세지 기능을 제공합니다.

### 과외 관리 서비스

https://user-images.githubusercontent.com/77656241/215424663-8bc388f5-a06c-48c4-a491-d2c9a932f482.mp4

서비스 내에서 매칭이 성사되면 자동으로 과외 관리 페이지를 생성합니다. 과외 관리 페이지에서는 튜터가 작성한 과외 일지를 통해 현재 진행 중인 과외의 현황을 확인 할 수 있습니다.

## 프로젝트 목표

프론트엔드와 백엔드 간 협업을 통하여 실무에 준한 협업과 개발 역량 향상

- 회원 가입, JWT 토큰 기반 인증 로그인
- OAuth 2.0 기반 소셜 (구글/카카오) 가입 및 로그인
- 회원 정보 수정 및 탈퇴
- 매칭을 위한 프로필 추가, 조회, 수정, 삭제
- 매칭을 위한 다른 프로필 조회 및 검색, 필터
- 사용자 간 Message 송/수신
- 과외 관리를 위한 과외 추가, 조회, 수정, 삭제
- 과외 현황 관리를 위한 일지 추가, 조회, 수정, 삭제

### 프론트엔드

- React, Router를 활용한 SPA 어플리케이션 개발
- 전역 상태의 효율적 관리를 위한 Recoil 사용
- Components Driven Development
- 통일성 있는 UI/UX를 위한 디자인 시스템 적용
- CSS 작성의 통일 및 모듈화를 위한 PostCSS 사용
- S3 Bucket 정적 페이지 배포
- ACM, CloudFront를 통한 HTTPS 연결 지원

### 백엔드

- REST API 백엔드 서버 구축

## 프로젝트 문서

[프로젝트 관리 Notion](https://codestates.notion.site/81a539d40b8043218fcc04a720ae87b5)  
[프로젝트 관리 Sheet (사용자 요구사항 정의서, 테이블 명세서, ERD)](https://docs.google.com/spreadsheets/d/1xXDMro8_Y60AMyBLFTEZ6FTt2ztlR0SviWoF0sieI28/edit?usp=sharing)  
[화면 정의서]()  
[Prototype](https://www.figma.com/file/tjklQ53VM1vS3iB3uPRYAA/seb_41_main_003_protoType?node-id=0%3A1&t=T5PQYwtVPd18BUXh-1)  
[User Flow](https://www.figma.com/file/uasFDiY5Q8HiEODFHsXt7s/seb_41_main_003_tutorDiff?node-id=0%3A1&t=uQ6BA3T37VQWrRjD-1)

## 프로젝트 팀 소개: 3조 쓰앵님👨‍🏫

|                                                  김민경<br>FE / Team Leader                                                   |                                                 이수영<br>BE / Part Leader                                                 |                                                        신승구<br>FE                                                        |                                                        강호수<br>BE                                                        |                                                     유영민<br>FE                                                     |                                                        김다은<br>BE                                                        |
| :---------------------------------------------------------------------------------------------------------------------------: | :------------------------------------------------------------------------------------------------------------------------: | :------------------------------------------------------------------------------------------------------------------------: | :------------------------------------------------------------------------------------------------------------------------: | :------------------------------------------------------------------------------------------------------------------: | :------------------------------------------------------------------------------------------------------------------------: |
| ![KakaoTalk_Photo_2023-01-30-16-26-52-5](https://user-images.githubusercontent.com/77656241/215424840-cb460528-c243-45e5-b3f2-dfaf00feeb64.png) | ![KakaoTalk_Photo_2023-01-30-16-26-52-2](https://user-images.githubusercontent.com/77656241/215424866-7bb44a9f-ac4b-4d80-81e9-d2550f86d292.png) | ![KakaoTalk_Photo_2023-01-30-16-26-52-3](https://user-images.githubusercontent.com/77656241/215424880-9d404d7b-f06d-4fdd-ba9e-8eeab159b3c8.png) | ![KakaoTalk_Photo_2023-01-30-16-26-52-1](https://user-images.githubusercontent.com/77656241/215424893-d02f6cd1-84db-4fa7-a85b-cdb4cd800d4f.png) | ![KakaoTalk_Photo_2023-01-30-16-26-52-4](https://user-images.githubusercontent.com/77656241/215424902-11d67428-941f-44cd-ab84-36d83730e63f.png) | ![KakaoTalk_Photo_2023-01-30-16-26-52-6](https://user-images.githubusercontent.com/77656241/215424915-043d3e72-5605-41c5-84af-4925d72732d2.png) |
| [@ansmeer008](https://github.com/ansmeer008) [🐌](https://github.com/codestates-seb/seb41_main_003/commits?author=ansmeer008) | [@sussa3007](https://github.com/sussa3007) [🐌](https://github.com/codestates-seb/seb41_main_003/commits?author=sussa3007) | [@ninefloor](https://github.com/ninefloor) [🐌](https://github.com/codestates-seb/seb41_main_003/commits?author=ninefloor) | [@hosoo3513](https://github.com/hosoo3513) [🐌](https://github.com/codestates-seb/seb41_main_003/commits?author=hosoo3513) | [@ymymmz9](https://github.com/ymymmz9) [🐌](https://github.com/codestates-seb/seb41_main_003/commits?author=ymymmz9) | [@DaeunKim9](https://github.com/DaeunKim9) [🐌](https://github.com/codestates-seb/seb41_main_003/commits?author=DaeunKim9) |

### 팀 규칙

1. 매일 오전 9시 간단한 인사와 이슈 공유를 위한 스탠딩 회의
   - 지각은 금물, 전화 받아도 화내지 않기
2. 상시 상주 시간 : 오후 1시 ~ 오후 6시
3. 조퇴/불참은 사전에 팀 단위 공유
4. 모르는 부분은 물어보고, 설명 잘 하기
5. 회의 때는 캠 필수!
6. 상주/저녁 시간은 모각코 시간

### GitHub Rules

- ISSUE
  1. 개발 계획, 버그, 문제 등을 빠르게 적용하여 템플릿 양식에 맞춰 작성.
  2. 작성자 정보를 기입.
  3. 백엔드/ 프론트 와 feat/bug 등 필요한 라벨을 추가하여 작성.
  4. Assignees에 담당 팀원을 설정.
  5. 적절한 Projects와 마일스톤을 설정.
- COMMIT
  1. 하나의 수정사항 당 하나의 커밋을 생성하기.
  2. 가능한 세부적으로 커밋 하기.
  3. 이미 지나 버렸다면, 그냥 커밋하기.
  4. 가능하면 Title에 커밋 내용 작성하기
  5. 커밋 컨벤션
     ```
     feat : 새로운 기능 구현
     fix : 버그 수정
     refactor : 리팩토링
     docs : 문서 수정
     design : css 등 UI 디자인/ 포멧팅, 세미콜론등 로직의 직접적인 변동 없는 수정
     rename : 파일 또는 디렉토리 명 수정
     remove : 단순 파일 삭제
     test : 테스트 코드 작성 및 수정
     chore : 기타 변경 사항
     ```
- PULL REQUEST
  1. 하나의 기능 구현 단위로 PR 하기
  2. PR의 Projects는 설정 하지 않기
  3. 필요한 라벨추가 하기
  4. 커밋 네이밍과 같은 규칙으로 작성하기
  5. PR 시 2명 이상의 리뷰 후 Merge하기
- BRANCH
  - Coz' Git Flow - `main`, `dev`, `feat`, `test`, `refactor`
  - 현재 과업을 간략히 나타낼 수 있을 만큼만 네이밍.
  - ex - `fe/feat_todo` , `be/feat_todo`
