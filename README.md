# SEB_41_MAIN_PROJECT

부트캠프를 통해 배웠던 기술 스택과 Pre-Project에서 경험한 내용을 토대로 실제 서비스를 기획 단계부터 배포까지 개발합니다.

# 과외차이

(대충 과외차이 로고 사진)

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

<img src="https://img.shields.io/badge/react-61DAFB?style=for-the-badge&logo=react&logoColor=black">
<img src="https://img.shields.io/badge/redux-3578e5.svg?style=for-the-badge&logo=data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIGZpbGw9Im5vbmUiIHdpZHRoPSIyMzY4IiB2aWV3Qm94PSIzMCAxMSAyNy41IDc4Ij48Y2lyY2xlIGN4PSI0My41IiBjeT0iMTguNSIgZmlsbD0iI2ZmZiIgcj0iNy41Ii8+PGNpcmNsZSBjeD0iNDMuNSIgY3k9IjgxLjUiIGZpbGw9IiNmZmYiIHI9IjcuNSIvPjxnIHN0cm9rZT0iI2ZmZiIgc3Ryb2tlLXdpZHRoPSIzIj48cGF0aCBkPSJNNDMuOTk5IDI1QzQyLjUgMzcgNTcuNSAzNCA1Ny41IDQyLjVjMCA1LTUuODc4IDYuMzY1LTEzLjUwMSA3QzM3Ljk5OSA1MCAzMCA1MCAzMCA1OHMxNiA1LjUgMTMuOTk5IDE3TTM0LjEzMiAzMy4zNTNjMCAxNS4yODkgMjMuMTUgMTguMjg5IDIzLjE1IDMyLjYyIi8+PC9nPjwvc3ZnPg==&logoColor=white">
<img src="https://img.shields.io/badge/postcss-DD3A0A.svg?style=for-the-badge&logo=PostCSS&logoColor=white">
<img src="https://img.shields.io/badge/html5-E34F26?style=for-the-badge&logo=html5&logoColor=white">
<img src="https://img.shields.io/badge/css-1572B6?style=for-the-badge&logo=css3&logoColor=white">
<img src="https://img.shields.io/badge/javascript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black"><br>
<img src="https://img.shields.io/badge/figma-F24E1E?style=for-the-badge&logo=Figma&logoColor=white">
<img src="https://img.shields.io/badge/eslint-4B32C3?style=for-the-badge&logo=ESLint&logoColor=white">
<img src="https://img.shields.io/badge/stylelint-263238?style=for-the-badge&logo=stylelint&logoColor=white">
<img src="https://img.shields.io/badge/prettier-F7B93E?style=for-the-badge&logo=Prettier&logoColor=black">
<img src="https://img.shields.io/badge/craco-1ab190?style=for-the-badge&logo=data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZlcnNpb249IjEuMCIgd2lkdGg9IjUxMi4wMDAwMDBwdCIgaGVpZ2h0PSI1MTIuMDAwMDAwcHQiIHZpZXdCb3g9IjAgMCA1MTIuMDAwMDAwIDUxMi4wMDAwMDAiIHByZXNlcnZlQXNwZWN0UmF0aW89InhNaWRZTWlkIG1lZXQiPg0KDQo8ZyB0cmFuc2Zvcm09InRyYW5zbGF0ZSgwLjAwMDAwMCw1MTIuMDAwMDAwKSBzY2FsZSgwLjEwMDAwMCwtMC4xMDAwMDApIiBmaWxsPSIjMDAwMDAwIiBzdHJva2U9Im5vbmUiPg0KPHBhdGggZD0iTTIyMjUgNDc5NCBjLTY3NyAtMTIxIC0xMTk4IC00NTkgLTE1NjYgLTEwMTcgLTU0IC04MiAtMTk5IC0zNjMgLTE5OSAtMzg2IDAgLTYgLTEzIC00MyAtMjkgLTgzIC0zMSAtNzkgLTgyIC0yODEgLTEwNiAtNDIyIC0yMSAtMTIzIC0yMSAtNTI5IDAgLTY1MiAyNCAtMTQxIDc1IC0zNDMgMTA2IC00MjIgMTYgLTQwIDI5IC03NyAyOSAtODMgMCAtMjMgMTQ1IC0zMDQgMTk5IC0zODYgMzMxIC01MDEgODE2IC04NDYgMTM3NiAtOTc3IDIxMiAtNDkgMjc0IC01NiA1MjUgLTU2IDI1MSAwIDMxMyA3IDUyNSA1NiA1NjAgMTMxIDEwNDUgNDc2IDEzNzYgOTc3IDU0IDgyIDE5OSAzNjMgMTk5IDM4NiAwIDYgMTMgNDMgMjkgODMgMzEgNzkgODIgMjgxIDEwNiA0MjIgMjEgMTIzIDIxIDUyOSAwIDY1MiAtMjQgMTQxIC03NSAzNDMgLTEwNiA0MjIgLTE2IDQwIC0yOSA3NyAtMjkgODMgMCAyMSAtMTQyIDMwMCAtMTkyIDM3NiAtMjg2IDQ0MCAtNjg3IDc1NiAtMTE3MyA5MjYgLTEwOCAzOCAtMjY3IDc4IC00MDkgMTAyIC0xMTggMjAgLTU0NSAyMCAtNjYxIC0xeiBtNjQ1IC03MjMgbDAgLTI0MCA4MyAtMjUgYzQ1IC0xNCAxMjkgLTQ4IDE4NiAtNzYgbDEwNSAtNTEgMTU4IDE1OCAxNTggMTU4IDIxNyAtMjE4IDIxOCAtMjE3IC0xNTkgLTE1OSAtMTU5IC0xNTkgNDcgLTkwIGMyNSAtNTAgNTkgLTEzMCA3NSAtMTc5IGwyOSAtODggMjQxIC0zIDI0MSAtMiAwIC0zMTAgMCAtMzEwIC0yMzggMCAtMjM3IDAgLTMzIC0xMDIgYy0xOCAtNTcgLTU0IC0xNDMgLTgxIC0xOTMgbC00OCAtOTAgMTYxIC0xNjMgMTYxIC0xNjIgLTIxOCAtMjE3IC0yMTcgLTIxOCAtMTYyIDE2MiAtMTYzIDE2MiAtOTUgLTQ4IGMtNTIgLTI2IC0xMzcgLTYxIC0xODcgLTc2IGwtOTMgLTI5IDAgLTIzOCAwIC0yMzggLTMxMCAwIC0zMTAgMCAtMiAyNDEgLTMgMjQxIC05NSAzMiBjLTUyIDE3IC0xMzEgNTEgLTE3NSA3NCBsLTgwIDQyIC0xNjIgLTE2MiAtMTYzIC0xNjMgLTIxNyAyMTggLTIxOCAyMTcgMTYxIDE2MiAxNjAgMTYzIC00NyA5MCBjLTI3IDUwIC02MyAxMzYgLTgxIDE5MyBsLTMzIDEwMiAtMjM3IDAgLTIzOCAwIDAgMzEwIDAgMzEwIDI0MSAyIDI0MSAzIDMyIDk1IGMxNyA1MiA1MyAxMzYgNzkgMTg1IGw0NyA5MCAtMTYzIDE2MyAtMTYyIDE2MiAyMTggMjE3IDIxNyAyMTggMTY0IC0xNjQgMTY1IC0xNjUgMTA4IDUxIGM1OSAyOCAxNDEgNjEgMTgxIDczIGw3MiAyMiAwIDIzOSAwIDIzOSAzMTAgMCAzMTAgMCAwIC0yMzl6Ii8+DQo8cGF0aCBkPSJNMjM4NCAzMzg1IGMtMjcyIC01OSAtNTA0IC0yNTcgLTYwOCAtNTE5IC00MCAtOTkgLTU2IC0xODcgLTU2IC0zMDYgMCAtMTIyIDE2IC0yMDggNTkgLTMxNSA4NCAtMjEwIDI1NiAtMzgyIDQ2NiAtNDY2IDEyMyAtNDkgMjAxIC02MiAzNTAgLTU2IDExMyAzIDE0MyA4IDIyNSAzNiAyMzkgODAgNDI5IDI1OCA1MjEgNDg2IDQzIDEwNyA1OSAxOTMgNTkgMzE1IDAgMTIyIC0xNiAyMDggLTU5IDMxNSAtMTAzIDI1NyAtMzM2IDQ1MyAtNjA5IDUxMSAtODMgMTcgLTI2NyAxNyAtMzQ4IC0xeiBtNTAgLTYyNCBjMTA5IC0xMDkgMTk3IC0yMDMgMTk1IC0yMDkgLTIgLTUgLTkzIC05NiAtMjAyIC0yMDMgbC0xOTggLTE5MiAtNzIgNzEgLTcyIDcyIDEzMCAxMzAgMTMwIDEzMCAtMTMwIDEzMCAtMTMwIDEzMCA3MCA3MCBjMzggMzggNzIgNzAgNzUgNzAgMyAwIDk1IC04OSAyMDQgLTE5OXogbTQ2NyA3IGMxMDkgLTEwNiAxOTkgLTIwMCAxOTkgLTIwOCAwIC04IC05MCAtMTAyIC0yMDEgLTIwOSBsLTIwMSAtMTk0IC03MSA3MSAtNzIgNzIgMTMwIDEzMCAxMzAgMTMwIC0xMzAgMTMwIC0xMzAgMTMwIDcwIDcwIGMzOCAzOCA3MSA3MCA3MyA3MCAyIDAgOTMgLTg3IDIwMyAtMTkyeiIvPg0KPC9nPg0KPC9zdmc+&logoColor=white">

### Backend

<img src="https://img.shields.io/badge/Spring Boot-6DB33F?style=for-the-badge&logo=Spring Boot&logoColor=white">
<img src="https://img.shields.io/badge/Spring DATA JPA-6DB33F?style=for-the-badge&logo=Spring&logoColor=white"> 
<img src="https://img.shields.io/badge/Spring Security-6DB33F?style=for-the-badge&logo=Spring Security&logoColor=white">
<img src="https://img.shields.io/badge/Mapstruct-e74f29?style=for-the-badge&logo=data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZpZXdCb3g9IjAgMCAzOTkuMTUgMzMwLjExIj48ZyBpZD0i66CI7J207Ja0XzIiIGZpbGw9IiNmZmZmZmYiPjxnIGlkPSLroIjsnbTslrRfMS0yIiBkYXRhLW5hbWU9IuugiOydtOyWtCAxIj48cGF0aCBkPSJNLjExLDI4OS4wNWM0LjgxLS4wOCw5LjYzLS4yMSwxNC40NC0uMjEsMjcuMDUsMCw1NC4xLjEyLDgxLjQ2LTIuNTMtNiw0LjI3LTEyLDguNTYtMTgsMTIuOEM2Ni45NCwzMDcsNjYuOTQsMzA3LDc4LDMxNC44OHE3LjkxLDUuNjUsMTUuNzcsMTEuMzhjMS41MSwxLjEsMy4yOSwyLjI3LjMsMy43OC0zMCwwLTYwLjEsMC05MC4xNS4wNy0yLjg4LDAtNC0uMzQtMy45NC0zLjcxQy4yNiwzMTQsLjExLDMwMS41LjExLDI4OS4wNVoiLz48cGF0aCBkPSJNMzk5LjA2LDQyLjA4Yy0zMS4xOC4zNy02Mi40Mi0xLjU4LTk0LjQ0LDMuMTksMi4yMS0zLjE3LDQuNjMtNC4zMyw2LjczLTUuODMsNi4yMi00LjQ1LDEyLjQzLTguOTMsMTguNzYtMTMuMjMsMi45My0yLDMuNTYtMy4zMS4xNi01LjYzLTcuMjgtNS0xNC4zMy0xMC4yOS0yMS40My0xNS41MS0xLjMxLTEtMy0xLjU4LTMuNjctMy4yOEMzMDYsLjE1LDMwOCwxLDMwOS4wNy4wOWMyOC44MywwLDU3LjY2LjA1LDg2LjQ5LS4wOSwzLDAsMy42My42LDMuNTksMy41OUMzOTguOTQsMTYuNDEsMzk5LjA2LDI5LjI1LDM5OS4wNiw0Mi4wOFoiLz48cGF0aCBkPSJNMjY5LjMxLDIxNi4zNWMuMjIsNi4wNy0xLjI4LDEyLjcyLTMuMDcsMTkuMzMtLjc0LDIuNzUtMS44NywzLjY1LTQuNSwxLjYyLTMxLjEyLTI0LTY0LjY1LTQ0LjcxLTk1Ljg2LTY4LjYycS0xMS44OC05LjEyLTIzLjM2LTE4Ljc3Yy03LTUuODctMTAuMzctMTMuODItMTEuNC0yMi42OC0xLjI5LTExLjA5LjMzLTIxLjkyLDMuODgtMzIuNDkuODgtMi42MiwxLjg2LTQuMjUsNC44My0xLjgxLDMzLjYyLDI3LjY0LDcwLjMzLDUxLjA4LDEwNC44MSw3Ny41NCw3LDUuMzQsMTQuMzIsMTAuMzUsMTguNTcsMTguNTVTMjY5LjQsMjA2LjE4LDI2OS4zMSwyMTYuMzVaIi8+PHBhdGggZD0iTTMwNS4xMywyNC4xMWMtLjE0LDEuNjktMS4zLDIuMi0yLjIsMi44Ny04LjQ2LDYuMjEtMTYuOTQsMTIuMzgtMjUuMzgsMTguNjFhMTcuODksMTcuODksMCwwLDEtNi44NiwyLjgyYy0zMy4wNSw3LjYtNjIsMjIuOTMtODYuNTMsNDYuMzktMi40OSwyLjM4LTQuMzIsMy03LjE5LjczLTguNDgtNi43Mi0xNy4wOS0xMy4yOC0yNS43NC0xOS43OS0yLjctMi0yLjU1LTQuMDgtMS4yMi02Ljg5LDYuNzYtMTQuMjgsMTguNDYtMjMuOTEsMzAuOTMtMzIuNjEsMjctMTguODYsNTcuNDctMjksODkuNzktMzQuMThhOC44Niw4Ljg2LDAsMCwxLDcuMSwxLjQ5YzguNCw2LjI4LDE2Ljg3LDEyLjQ3LDI1LjMsMTguNzJBMjEuOCwyMS44LDAsMCwxLDMwNS4xMywyNC4xMVoiLz48cGF0aCBkPSJNMTI4LjEzLDMyOGMtMiwuMTUtMy41My0uODUtNS0yLTcuOTEtNS44Mi0xNS43Ny0xMS43Mi0yMy43NC0xNy40Ni0zLjA2LTIuMi0zLjU2LTMuOTItLjE3LTYuMzQsOC4zOS02LDE2LjYxLTEyLjI0LDI1LTE4LjI4YTEyLjQ1LDEyLjQ1LDAsMCwxLDQuNTEtMmMzMy40Mi03LjQ4LDYyLjc4LTIyLjcsODcuNjUtNDYuMjgsMy40Ni0zLjI4LDUuNy0zLjE0LDkuMTgtLjMsNy42LDYuMiwxNS40NiwxMi4xLDIzLjM2LDE3LjkzLDMuNSwyLjU4LDMuNzQsNSwxLjcxLDguODMtOC4xNiwxNS4yNy0yMS4yMSwyNS40OS0zNS4yNCwzNC42Mi0yNiwxNi45Mi01NC44MSwyNi4xOS04NS4yNSwzMC45NUMxMjkuNDUsMzI3Ljg3LDEyOC43OSwzMjgsMTI4LjEzLDMyOFoiLz48L2c+PC9nPjwvc3ZnPg==&logoColor=white">
<img src="https://img.shields.io/badge/JUnit5-25A162?style=for-the-badge&logo=JUnit5&logoColor=white">
<img src="https://img.shields.io/badge/Mockito-6c9d1a?style=for-the-badge&logo=로고&logoColor=white">
<img src="https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=Gradle&logoColor=white">
<img src="https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHhtbG5zOnhsaW5rPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5L3hsaW5rIiB4bWxuczpza2V0Y2g9Imh0dHA6Ly93d3cuYm9oZW1pYW5jb2RpbmcuY29tL3NrZXRjaC9ucyIgd2lkdGg9IjIwMHB4IiBoZWlnaHQ9IjIwMHB4IiB2aWV3Qm94PSIwIDAgMjAwIDIwMCIgdmVyc2lvbj0iMS4xIj4NCiAgICA8IS0tIEdlbmVyYXRvcjogU2tldGNoIDMuMy4yICgxMjA0MykgLSBodHRwOi8vd3d3LmJvaGVtaWFuY29kaW5nLmNvbS9za2V0Y2ggLS0+DQogICAgPHRpdGxlPkdyb3VwPC90aXRsZT4NCiAgICA8ZGVzYz5DcmVhdGVkIHdpdGggU2tldGNoLjwvZGVzYz4NCiAgICA8ZGVmcy8+DQogICAgPGcgaWQ9IlBhZ2UtMSIgc3Ryb2tlPSJub25lIiBzdHJva2Utd2lkdGg9IjEiIGZpbGw9Im5vbmUiIGZpbGwtcnVsZT0iZXZlbm9kZCIgc2tldGNoOnR5cGU9Ik1TUGFnZSI+DQogICAgICAgIDxnIGlkPSJqd3RfbG9nbyIgc2tldGNoOnR5cGU9Ik1TTGF5ZXJHcm91cCIgdHJhbnNmb3JtPSJ0cmFuc2xhdGUoLTI1MC4wMDAwMDAsIDAuMDAwMDAwKSI+DQogICAgICAgICAgICA8ZyBpZD0iR3JvdXAiIHNrZXRjaDp0eXBlPSJNU1NoYXBlR3JvdXAiPg0KICAgICAgICAgICAgICAgIDxnIHRyYW5zZm9ybT0idHJhbnNsYXRlKDI1MC4wMDAwMDAsIDAuMDAwMDAwKSI+DQogICAgICAgICAgICAgICAgICAgIA0KICAgICAgICAgICAgICAgICAgICA8ZyB0cmFuc2Zvcm09InRyYW5zbGF0ZSg1MC4wMDAwMDAsIDUwLjAwMDAwMCkiIGlkPSJTaGFwZSI+DQogICAgICAgICAgICAgICAgICAgICAgICA8cGF0aCBkPSJNNTcuNSwyNi45IEw1Ny41LDIuODQyMTcwOTRlLTE0IEw0Mi41LDIuODQyMTcwOTRlLTE0IEw0Mi41LDI2LjkgTDUwLDM3LjIgTDU3LjUsMjYuOSBaIiBmaWxsPSIjRkZGRkZGIi8+DQogICAgICAgICAgICAgICAgICAgICAgICA8cGF0aCBkPSJNNDIuNSw3My4xIEw0Mi41LDEwMCBMNTcuNSwxMDAgTDU3LjUsNzMuMSBMNTAsNjIuOCBMNDIuNSw3My4xIFoiIGZpbGw9IiNGRkZGRkYiLz4NCiAgICAgICAgICAgICAgICAgICAgICAgIDxwYXRoIGQ9Ik01Ny41LDczLjEgTDczLjMsOTQuOSBMODUuNSw4NiBMNjkuNiw2NC4zIEw1Ny41LDYwLjMgTDU3LjUsNzMuMSBaIiBmaWxsPSIjMDBGMkU2Ii8+DQogICAgICAgICAgICAgICAgICAgICAgICA8cGF0aCBkPSJNNDIuNSwyNi45IEwyNi43LDUuMSBMMTQuNSwxNCBMMzAuNCwzNS43IEw0Mi41LDM5LjcgTDQyLjUsMjYuOSBaIiBmaWxsPSIjMDBGMkU2Ii8+DQogICAgICAgICAgICAgICAgICAgICAgICA8cGF0aCBkPSJNMzAuNCwzNS43IEw0LjgsMjcuNCBMMC4xLDQxLjcgTDI1LjcsNTAgTDM3LjksNDYuMSBMMzAuNCwzNS43IFoiIGZpbGw9IiMwMEI5RjEiLz4NCiAgICAgICAgICAgICAgICAgICAgICAgIDxwYXRoIGQ9Ik02Mi4xLDUzLjkgTDY5LjYsNjQuMyBMOTUuMiw3Mi42IEw5OS45LDU4LjMgTDc0LjMsNTAgTDYyLjEsNTMuOSBaIiBmaWxsPSIjMDBCOUYxIi8+DQogICAgICAgICAgICAgICAgICAgICAgICA8cGF0aCBkPSJNNzQuMyw1MCBMOTkuOSw0MS43IEw5NS4yLDI3LjQgTDY5LjYsMzUuNyBMNjIuMSw0Ni4xIEw3NC4zLDUwIFoiIGZpbGw9IiNENjNBRkYiLz4NCiAgICAgICAgICAgICAgICAgICAgICAgIDxwYXRoIGQ9Ik0yNS43LDUwIEwwLjEsNTguMyBMNC44LDcyLjYgTDMwLjQsNjQuMyBMMzcuOSw1My45IEwyNS43LDUwIFoiIGZpbGw9IiNENjNBRkYiLz4NCiAgICAgICAgICAgICAgICAgICAgICAgIDxwYXRoIGQ9Ik0zMC40LDY0LjMgTDE0LjUsODYgTDI2LjcsOTQuOSBMNDIuNSw3My4xIEw0Mi41LDYwLjMgTDMwLjQsNjQuMyBaIiBmaWxsPSIjRkIwMTVCIi8+DQogICAgICAgICAgICAgICAgICAgICAgICA8cGF0aCBkPSJNNjkuNiwzNS43IEw4NS41LDE0IEw3My4zLDUuMSBMNTcuNSwyNi45IEw1Ny41LDM5LjcgTDY5LjYsMzUuNyBaIiBmaWxsPSIjRkIwMTVCIi8+DQogICAgICAgICAgICAgICAgICAgIDwvZz4NCiAgICAgICAgICAgICAgICA8L2c+DQogICAgICAgICAgICA8L2c+DQogICAgICAgIDwvZz4NCiAgICA8L2c+DQo8L3N2Zz4=&logoColor=white">
<img src="https://img.shields.io/badge/OAuth2-eea738?style=for-the-badge&logo=로고&logoColor=white">
<img src="https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=MySQL&logoColor=white">
<img src="https://img.shields.io/badge/Redis-DC382D?style=for-the-badge&logo=Redis&logoColor=white">
<img src="https://img.shields.io/badge/H2 DataBase-09476b?style=for-the-badge&logo=data:image/svg+xml;base64,PHN2ZyB4bWxuczppbmtzY2FwZT0iaHR0cDovL3d3dy5pbmtzY2FwZS5vcmcvbmFtZXNwYWNlcy9pbmtzY2FwZSIgeG1sbnM6c29kaXBvZGk9Imh0dHA6Ly9zb2RpcG9kaS5zb3VyY2Vmb3JnZS5uZXQvRFREL3NvZGlwb2RpLTAuZHRkIiB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHhtbG5zOnN2Zz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHhtbG5zOnJkZj0iaHR0cDovL3d3dy53My5vcmcvMTk5OS8wMi8yMi1yZGYtc3ludGF4LW5zIyIgeG1sbnM6Y2M9Imh0dHA6Ly9jcmVhdGl2ZWNvbW1vbnMub3JnL25zIyIgeG1sbnM6ZGM9Imh0dHA6Ly9wdXJsLm9yZy9kYy9lbGVtZW50cy8xLjEvIiB3aWR0aD0iNjEuODE1MjU0bW0iIGhlaWdodD0iNjEuODE1MzYxbW0iIHZpZXdCb3g9IjAgMCA2MS44MTUyNTQgNjEuODE1MzYxIiB2ZXJzaW9uPSIxLjEiIGlkPSJzdmc4NTUyIiBpbmtzY2FwZTp2ZXJzaW9uPSIxLjEuMiAoMGEwMGNmNTMzOSwgMjAyMi0wMi0wNCkiIHNvZGlwb2RpOmRvY25hbWU9ImgyLWxvZ28uc3ZnIj4NCiAgPGRlZnMgaWQ9ImRlZnM4NTQ2Ii8+DQogIDxzb2RpcG9kaTpuYW1lZHZpZXcgaWQ9ImJhc2UiIHBhZ2Vjb2xvcj0iI2ZmZmZmZiIgYm9yZGVyY29sb3I9IiM2NjY2NjYiIGJvcmRlcm9wYWNpdHk9IjEuMCIgaW5rc2NhcGU6cGFnZW9wYWNpdHk9IjAuMCIgaW5rc2NhcGU6cGFnZXNoYWRvdz0iMiIgaW5rc2NhcGU6em9vbT0iMC45ODk5NDk0OSIgaW5rc2NhcGU6Y3g9Ii0xNTYuMDY4NTciIGlua3NjYXBlOmN5PSIyNDQuOTYxOTkiIGlua3NjYXBlOmRvY3VtZW50LXVuaXRzPSJtbSIgaW5rc2NhcGU6Y3VycmVudC1sYXllcj0ibGF5ZXIxIiBzaG93Z3JpZD0iZmFsc2UiIGlua3NjYXBlOndpbmRvdy13aWR0aD0iMjQ2MCIgaW5rc2NhcGU6d2luZG93LWhlaWdodD0iMTU2MyIgaW5rc2NhcGU6d2luZG93LXg9IjAiIGlua3NjYXBlOndpbmRvdy15PSIwIiBpbmtzY2FwZTp3aW5kb3ctbWF4aW1pemVkPSIxIiBpbmtzY2FwZTpwYWdlY2hlY2tlcmJvYXJkPSIxIiBmaXQtbWFyZ2luLXRvcD0iMCIgZml0LW1hcmdpbi1sZWZ0PSIwIiBmaXQtbWFyZ2luLXJpZ2h0PSIwIiBmaXQtbWFyZ2luLWJvdHRvbT0iMCIvPg0KICA8bWV0YWRhdGEgaWQ9Im1ldGFkYXRhODU0OSI+DQogICAgPHJkZjpSREY+DQogICAgICA8Y2M6V29yayByZGY6YWJvdXQ9IiI+DQogICAgICAgIDxkYzpmb3JtYXQ+aW1hZ2Uvc3ZnK3htbDwvZGM6Zm9ybWF0Pg0KICAgICAgICA8ZGM6dHlwZSByZGY6cmVzb3VyY2U9Imh0dHA6Ly9wdXJsLm9yZy9kYy9kY21pdHlwZS9TdGlsbEltYWdlIi8+DQogICAgICA8L2NjOldvcms+DQogICAgPC9yZGY6UkRGPg0KICA8L21ldGFkYXRhPg0KICA8ZyBpbmtzY2FwZTpsYWJlbD0iTGF5ZXIgMSIgaW5rc2NhcGU6Z3JvdXBtb2RlPSJsYXllciIgaWQ9ImxheWVyMSIgdHJhbnNmb3JtPSJ0cmFuc2xhdGUoLTc0LjY2MzgyNywtMTA4LjEzNjA1KSI+DQogICAgPHBhdGggZD0ibSAxMTguODg4NDgsMTQyLjMxNTU1IGMgMC4zNjQ3NiwtMC4wMzg5IDAuNzI0NjMsLTAuMDU4NCAxLjA3OTY1LC0wLjA1ODQgMS43ODc2MSwwIDMuMTU3MzMsMC40MDMyOCA0LjEwOTI0LDEuMjA5OTMgMC45NTE5MiwwLjgwNjU2IDEuNDI3OTMsMS45NTEwOSAxLjQyNzkzLDMuNDMzMTQgMCwxLjIzNjAxIC0wLjM3MDY0LDIuNDg1NTQgLTEuMTExOCwzLjc0OTU0IC0wLjc0MTIzLDEuMjY0ODggLTEuOTkxMDYsMi43NDcxOSAtMy43NDk2MSw0LjQ0Nzg0IC0xLjE3NzE5LDEuMTQ3NTggLTIuNzQzMTQsMi41MTM1MyAtNC42OTc4NSw0LjA5Nzc2IC0xLjk1NDcsMS41ODQyNiAtMy45MDU3OSwzLjA4MTAxIC01Ljg1MzI3LDQuNDkxMTUgdiA1LjM2MjY2IGggMjUuNDg0MTIgdiAtNi4yMTI1NCBoIC0xNC41ODQyMiBjIDAuNTY2ODYsLTAuNDA2ODcgMS41MTg3NiwtMS4xMjMyMiAyLjg1NTgxLC0yLjE0NzIyIDEuMzM3MDgsLTEuMDI0ODkgMi42NjY5MSwtMi4xNDgxMyAzLjk4OTQ0LC0zLjM2ODgxIDIuMTA3MjcsLTEuOTYxMzggMy42NzMyLC0zLjg2NTkyIDQuNjk3ODMsLTUuNzEwOTEgMS4wMjQ2MywtMS44NDU4OSAxLjUzNjg5LC0zLjc5Mzc1IDEuNTM2ODksLTUuODQyNzMgMCwtMy4xMjQ2NiAtMS4wNjgyLC01LjU0MDg0IC0zLjIwNDYsLTcuMjQ4NTIgLTIuMTM2MzEsLTEuNzA3NTkgLTUuMjM5MjQsLTIuNTYxNDMgLTkuMzA4NTEsLTIuNTYxNDMgLTAuODkxMTEsMCAtMS43ODE0LDAuMDQzOCAtMi42NzEwNSwwLjEzMTU0IHYgLTEyLjQwMTA4IGggLTguNTM0MjUgdiAxMi4xMTIyOCBIIDk3Ljc1Mjg3MyB2IC0xMi4xMTIyOCBoIC04LjUzMzg4OSB2IDMzLjA5MTg4IGggOC41MzM4ODkgdiAtMTQuNTc4OTggaCAxMi42MDEzNTcgdiAxNC41Nzg5NiBoIDAuMDk2NyBjIDAuNzk5ODgsLTAuNjE5OCAxLjYwMDMyLC0xLjI1NDA1IDIuNDAxMzgsLTEuOTAzNjIgMS45NTQ3MSwtMS41ODMzNiAzLjUyMDczLC0yLjk1MDE5IDQuNjk3OTIsLTQuMDk3NzkgMC40ODQ3NiwtMC40NjkxNCAwLjkzMDksLTAuOTIxMTQgMS4zMzgzMiwtMS4zNTY5MSB6IG0gLTEzLjI4Njk0LDI1LjgzMTQ2IGMgLTE2LjA2MDQ3MiwwIC0yOS4xMzMzMTcsLTEzLjA3Mjg1IC0yOS4xMzMzMTcsLTI5LjEzMzUyIDAsLTE2LjA0ODU0IDEzLjA2MDIxNSwtMjkuMDczMDQgMjkuMTMzMzE3LC0yOS4wNzMwNCAxNi4wNjA2NSwwIDI5LjA3MzE0LDEzLjAxMjQxIDI5LjA3MzE0LDI5LjA3MzA0IHYgMC45MDIyMiBoIDEuODA0NCB2IC0wLjkwMjIyIGMgMCwtMTcuMDU3MiAtMTMuODIwMzIsLTMwLjg3NzQ0IC0zMC44Nzc1NCwtMzAuODc3NDQgLTE3LjA2ODIyNywwIC0zMC45Mzc3MTMsMTMuODMxMjUgLTMwLjkzNzcxMywzMC44Nzc0NCAwLDE3LjA1NzYgMTMuODgwMzEzLDMwLjkzNzkyIDMwLjkzNzcxMywzMC45Mzc5MiBoIDAuOTAyMTkgdiAtMS44MDQ0IHoiIGlkPSJwYXRoNjM5NCIgaW5rc2NhcGU6Y29ubmVjdG9yLWN1cnZhdHVyZT0iMCIgc3R5bGU9ImZpbGw6I2ZmZmZmZjtmaWxsLW9wYWNpdHk6MTtzdHJva2Utd2lkdGg6MC45MDIxOTgiLz4NCiAgPC9nPg0KPC9zdmc+&logoColor=white">
<img src="https://img.shields.io/badge/QueryDSL-008ad0?style=for-the-badge&logo=data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZlcnNpb249IjEuMCIgd2lkdGg9IjIwMC4wMDAwMDBwdCIgaGVpZ2h0PSIyMDAuMDAwMDAwcHQiIHZpZXdCb3g9IjAgMCAyMDAuMDAwMDAwIDIwMC4wMDAwMDAiIHByZXNlcnZlQXNwZWN0UmF0aW89InhNaWRZTWlkIG1lZXQiPg0KPG1ldGFkYXRhPg0KQ3JlYXRlZCBieSBwb3RyYWNlIDEuMTYsIHdyaXR0ZW4gYnkgUGV0ZXIgU2VsaW5nZXIgMjAwMS0yMDE5DQo8L21ldGFkYXRhPg0KPGcgdHJhbnNmb3JtPSJ0cmFuc2xhdGUoMC4wMDAwMDAsMjAwLjAwMDAwMCkgc2NhbGUoMC4xMDAwMDAsLTAuMTAwMDAwKSIgZmlsbD0iI2ZmZmZmZiIgc3Ryb2tlPSJub25lIj4NCjxwYXRoIGQ9Ik02NjAgMTg0OCBjLTUyIC0zNiAtNzMgLTcxIC03OCAtMTMyIC0xMSAtMTM4IDEyNiAtMjI4IDI0OSAtMTYzIDExNiA2MSAxMjEgMjE5IDEwIDI5NCAtNDcgMzIgLTEzNSAzMiAtMTgxIDF6Ii8+DQo8cGF0aCBkPSJNMTA2NiAxODM5IGMtOTkgLTg2IC00MSAtMjQzIDg5IC0yNDEgODUgMSAxMzkgNTUgMTM3IDEzOCAtMSA4NyAtNTEgMTM0IC0xNDEgMTM0IC00MiAwIC01NiAtNSAtODUgLTMxeiIvPg0KPHBhdGggZD0iTTE0MTQgMTY5MSBjLTU4IC0yNSAtNzQgLTExOSAtMjggLTE2NSAyMCAtMjAgMzcgLTI2IDY4IC0yNiA1MSAwIDgxIDIwIDk2IDY0IDEyIDM4IC0yIDg5IC0zMyAxMTQgLTIwIDE3IC03NyAyNCAtMTAzIDEzeiIvPg0KPHBhdGggZD0iTTMwNyAxNTk2IGMtMTMzIC00OSAtMTY0IC0yMDQgLTYyIC0zMDMgMTQ4IC0xNDIgMzc0IDI5IDI4NCAyMTUgLTM5IDc5IC0xMzggMTE5IC0yMjIgODh6Ii8+DQo8cGF0aCBkPSJNMTU5NSAxNDUwIGMtMjggLTMxIC0yNCAtNzkgOSAtMTA3IDMzIC0yOCA1NiAtMjkgOTAgLTIgMzUgMjcgMzcgOTAgNCAxMTMgLTMyIDIyIC04MSAyMCAtMTAzIC00eiIvPg0KPHBhdGggZD0iTTEzNCAxMTAxIGMtNTcgLTM0IC04NyAtODUgLTg3IC0xNDQgMCAtMTYyIDE3NCAtMjM2IDI5MSAtMTI0IDc5IDc2IDYwIDIxMCAtMzYgMjY1IC01MSAyOCAtMTI1IDI5IC0xNjggM3oiLz4NCjxwYXRoIGQ9Ik0xMTIxIDg3MiBjLTQwIC0yMSAtNzEgLTczIC03MSAtMTE3IDAgLTQ0IDMxIC05NiA3MSAtMTE3IDM5IC0yMSA2NyAtMjIgMTE5IC03IDQ4IDE0IDg2IDUgMTAyIC0yNCA4IC0xMyAxMyAtNTkgMTMgLTExNyAwIC01MiA2IC0xMTQgMTMgLTEzNyAyMCAtNjggODIgLTEzNyAxNTQgLTE3MiA4MyAtNDAgMTM5IC00NyAyMTcgLTI3IDEzOSAzNSAyMjYgMTM3IDIzOCAyNzUgMjMgMjc2IC0yNjEgNDM1IC01MDQgMjgxIC04MyAtNTMgLTEzNCAtMzUgLTE1MyA1MyAtMjEgMTAzIC0xMTcgMTU1IC0xOTkgMTA5eiIvPg0KPHBhdGggZD0iTTI5MiA2NDIgYy00OSAtMjcgLTcyIC02OCAtNzIgLTEzMCAwIC00NSA0IC01NyAzNCAtODkgMzAgLTMzIDM5IC0zNyA5MyAtNDEgNTMgLTQgNjIgLTEgOTEgMjMgNDYgMzggNjUgODAgNTkgMTI2IC0xMyAxMDAgLTExOCAxNTcgLTIwNSAxMTF6Ii8+DQo8cGF0aCBkPSJNNTg4IDM1NCBjLTM1IC0xOCAtNDggLTQzIC00OCAtODkgMCAtNDcgMTMgLTcxIDUwIC05MCA2MSAtMzEgMTI0IC05IDE1MCA1NCAxNyA0MiAtMiA5NyAtNDQgMTIyIC0zNyAyMiAtNzEgMjQgLTEwOCAzeiIvPg0KPHBhdGggZD0iTTg3NSAyMzUgYy0zNiAtMzUgLTMzIC03NiA3IC0xMTAgNTIgLTQ0IDExOCAtMTMgMTE4IDU2IDAgNzAgLTc3IDEwMyAtMTI1IDU0eiIvPg0KPC9nPg0KPC9zdmc+&logoColor=white">
<img src="https://img.shields.io/badge/Spring Rest Docs-6DB33F?style=for-the-badge&logo=로고&logoColor=white">

### Server Infra

<img src="https://img.shields.io/badge/Amazon EC2-FF9900?style=for-the-badge&logo=Amazon EC2&logoColor=white">
<img src="https://img.shields.io/badge/Amazon S3-569A31?style=for-the-badge&logo=Amazon S3&logoColor=white">
<img src="https://img.shields.io/badge/Amazon CloudFront-FF9900?style=for-the-badge&logo=data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSIyMDc4IiBoZWlnaHQ9IjI1MDAiIHZpZXdCb3g9IjAgMCAyNTYgMzA4IiBwcmVzZXJ2ZUFzcGVjdFJhdGlvPSJ4TWlkWU1pZCI+PHBhdGggZD0iTTE2Ni4wMSAxMjcuNDY1bDU5LjIwMi02LjMxNCAzMC42NzYgNi4zNjkuMTExLjA2OC01Ni41NTggMy4zNzQtMzMuNDg0LTMuNDQyLjA1My0uMDU1eiIgZmlsbD0iIzVFMUYxOCIvPjxwYXRoIGQ9Ik0xNjUuOTU4IDEyNy41MjFsNTkuMjA0LTQuNjI1LjQwNy0uNTkzLjAwMi03My4wNDQtLjQwNy0uODM1LTU5LjIwNiAxNS4zOTd2NjMuNyIgZmlsbD0iIzhDMzEyMyIvPjxwYXRoIGQ9Ik0yNTYgMTI3LjU5bC0zMC44MzgtNC42OTQuMDAyLTc0LjQ3MiAzMC44MzUgMTUuNDI4LjAwMSA2My43MzgiIGZpbGw9IiNFMDUyNDMiLz48cGF0aCBkPSJNMTY1Ljk1OCAxODAuMTE1bC44NjUuNjA2IDU4LjM0OSAzLjk1OSAyOS43NzItMy45NTkgMS4wNTUtLjU3OS01Ni41NTctMy40NzUtMzMuNDg0IDMuNDQ4IiBmaWxsPSIjRjJCMEE5Ii8+PHBhdGggZD0iTTE2NS45NTggMTgwLjExNWw1OS4yMTQgNC40MDUuMTgzLjI0Ni0uMDQ1IDczLjk0Mi0uMTQ2LjM4Ni01OS4yMDYtMTUuMjc1di02My43MDQiIGZpbGw9IiM4QzMxMjMiLz48cGF0aCBkPSJNMjU1Ljk5OSAxODAuMTQybC0zMC44MjcgNC4zNzgtLjAwOCA3NC41NzQgMzAuODM1LTE1LjMwOXYtNjMuNjQzIiBmaWxsPSIjRTA1MjQzIi8+PGc+PHBhdGggZD0iTTg5LjExOSAxMjcuNDA4bC01OC41My01LjAxNEwuMTk1IDEyNy40NGwtLjE5My4xNTEgNTYuNTU3IDMuMzc0IDMyLjg3NC0zLjM3Ni0uMzE0LS4xOHoiIGZpbGw9IiM1RTFGMTgiLz48cGF0aCBkPSJNLjAwMiAxMjcuNTlsMzAuNTU3LTQuNDc0Ljg5OS0uNjM3di03My4ybC0uODk5LS44NTVMLjAwMiA2My44NTV2NjMuNzM1IiBmaWxsPSIjOEMzMTIzIi8+PHBhdGggZD0iTTg5LjQzMyAxMjcuNTg4bC01OC44NzQtNC40NzJWNDguNDI0bDU4Ljg3NyAxNS4zOTctLjAwMyA2My43NjciIGZpbGw9IiNFMDUyNDMiLz48L2c+PGc+PHBhdGggZD0iTTg5LjQzMyAxODAuMTQybC0xLjM1MS45ODUtNTcuNTIzIDQuMzEzLTI5LjU4My00LjMxMy0uOTc2LS45ODUgNTYuNTU5LTMuNDc1IDMyLjg3NCAzLjQ3NSIgZmlsbD0iI0YyQjBBOSIvPjxwYXRoIGQ9Ik0wIDE4MC4xNDJsMzAuNTU4IDQuMzQuNzc3Ljk1Mi4wODMgNzIuMzMzLS44NTkgMS4zMjdMLjAwMiAyNDMuNzg1IDAgMTgwLjE0MiIgZmlsbD0iIzhDMzEyMyIvPjxwYXRoIGQ9Ik04OS40MzMgMTgwLjE0MmwtNTguODc1IDQuMzQuMDAxIDc0LjYxMiA1OC44NzQtMTUuMjc1di02My42NzciIGZpbGw9IiNFMDUyNDMiLz48L2c+PGc+PHBhdGggZD0iTTE4OS4xMDQgMTg5LjY2bC02MS4xMDMtNi4wMjYtNjEuNzEyIDYuMDI3Ljg3LjczNSA2MC41NDEgOS42MTcgNjAuNTMzLTkuNjE3Ljg3MS0uNzM2IiBmaWxsPSIjRjJCMEE5Ii8+PHBhdGggZD0iTTY2LjI4OSAxODkuNjYxbDYxLjQxMSA4Ljk5OC42MjMuODMzLjA3OSAxMDYuOTU5LS43MDIgMS4xODYtNjEuNDExLTMwLjcwNnYtODcuMjciIGZpbGw9IiM4QzMxMjMiLz48cGF0aCBkPSJNMTg5LjEwNCAxODkuNjZsLTYxLjQwNCA4Ljk5OXYxMDguOTc4bDYxLjQwNS0zMC43MDYtLjAwMS04Ny4yNzEiIGZpbGw9IiNFMDUyNDMiLz48L2c+PGc+PHBhdGggZD0iTTEyOC4wMDEgMTIzLjkzM2wtNjEuNzEyLTUuOTU4LjE1OS0uMDkxIDYxLjI0OS05LjUwMiA2MS4yMjIgOS41MjQuMTg2LjA2OS02MS4xMDQgNS45NTh6IiBmaWxsPSIjNUUxRjE4Ii8+PHBhdGggZD0iTTY2LjI4OSAxMTcuOTc1bDYxLjQxMS04Ljk0OS4yOTQtLjI2OEwxMjcuODU4LjE2NSAxMjcuNyAwIDY2LjI4OSAzMC43MDl2ODcuMjY2IiBmaWxsPSIjOEMzMTIzIi8+PHBhdGggZD0iTTE4OS4xMDUgMTE3Ljk3NWwtNjEuNDA1LTguOTQ5VjBsNjEuNDA1IDMwLjcwOXY4Ny4yNjYiIGZpbGw9IiNFMDUyNDMiLz48L2c+PC9zdmc+&logoColor=white">
<img src="https://img.shields.io/badge/ACM-191A1B?style=for-the-badge&logo=data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHhtbG5zOnhsaW5rPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5L3hsaW5rIiB2aWV3Qm94PSIwIDAgODUgODUiIGZpbGw9IiNmZmYiIGZpbGwtcnVsZT0iZXZlbm9kZCIgc3Ryb2tlPSIjMDAwIiBzdHJva2UtbGluZWNhcD0icm91bmQiIHN0cm9rZS1saW5lam9pbj0icm91bmQiPjx1c2UgeGxpbms6aHJlZj0iI0EiIHg9IjIuNSIgeT0iMi41Ii8+PHN5bWJvbCBpZD0iQSIgb3ZlcmZsb3c9InZpc2libGUiPjxnIHN0cm9rZT0ibm9uZSI+PHBhdGggZD0iTTgwIDQ3LjAwNWwtNDAuMDEyIDQuOTYyVjI3Ljk5TDgwIDMyLjk1MnYxNC4wNTN6IiBmaWxsPSIjNzU5YzNlIi8+PHBhdGggZD0iTTAgNDcuMDA1bDM5Ljk4OCA0Ljk2MlYyNy45OUwwIDMyLjk1MnYxNC4wNTN6IiBmaWxsPSIjNGI2MTJjIi8+PHBhdGggZD0iTTEwIDBoNjB2MTVIMTB6IiBmaWxsPSIjNjQ4MzM5Ii8+PHBhdGggZD0iTTY1IDIwbC00OC45NzYuMjEyTDEwIDE1aDYwbC01IDV6IiBmaWxsPSIjM2M0OTI5Ii8+PHBhdGggZD0iTTEwIDY1aDYwdjE1SDEweiIgZmlsbD0iIzY0ODMzOSIvPjxwYXRoIGQ9Ik02NSA2MEgxNWwtNSA1aDYwbC01LTV6IiBmaWxsPSIjYjdjYTlkIi8+PC9nPjwvc3ltYm9sPjwvc3ZnPg==&logoColor=white">
<img src="https://img.shields.io/badge/ElastiCache-181717?style=for-the-badge&logo=data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHhtbG5zOnhsaW5rPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5L3hsaW5rIiB3aWR0aD0iMjU2cHgiIGhlaWdodD0iMzA4cHgiIHZpZXdCb3g9IjAgMCAyNTYgMzA4IiB2ZXJzaW9uPSIxLjEiIHByZXNlcnZlQXNwZWN0UmF0aW89InhNaWRZTWlkIj4NCgk8Zz4NCgkJPHBvbHlnb24gZmlsbD0iIzk5QkNFMyIgcG9pbnRzPSIwLjgyMTcgMjA3LjYwNDIgMTI3Ljk5OTcgMzA3LjIwMDIgMjU1LjE3ODcgMjA3LjYwNDIgMTI3Ljk5NTcgMTkxLjc5MjIiLz4NCgkJPHBvbHlnb24gZmlsbD0iIzE5NDg2RiIgcG9pbnRzPSIxMjcuOTk5OCAwLjAwMDMgLTAuMDAwMiA5OS41OTEzIDEyNy4xNzQ4IDExNS40MDQzIDI1NS45OTk4IDk5LjU5NjMiLz4NCgkJPHBvbHlnb24gZmlsbD0iIzFGNUI5OSIgcG9pbnRzPSIxMjcuOTk5OCAwLjAwMDMgLTAuMDAwMiA2My41OTEzIC0wLjAwMDIgOTkuNTk2MyAxMjcuOTk5OCA2MS40NDEzIi8+DQoJCTxwb2x5Z29uIGZpbGw9IiMxRjVCOTkiIHBvaW50cz0iMCAyNDMuNjA4NyAxMjggMzA3LjE5OTcgMTI4IDI0NS43NTc3IDAgMjA3LjYwODciLz4NCgkJPHBvbHlnb24gZmlsbD0iIzFGNUI5OSIgcG9pbnRzPSIxMjggMjAwLjA1MzcgNzIuNTMzIDE5MS45Mjg3IDcyLjUzMyAxMTUuODkxNyAxMjggMTA3Ljg5MTcgMTI5LjMwOSAxMTAuNDIzNyAxMjkuMTcxIDE5Ny41MjI3Ii8+DQoJCTxwb2x5Z29uIGZpbGw9IiM1Mjk0Q0YiIHBvaW50cz0iMTI3Ljk5OTggMC4wMDAzIDEyNy45OTk4IDYxLjQ0MTMgMjU1Ljk5OTggOTkuNTk2MyAyNTUuOTk5OCA2My41OTEzIi8+DQoJCTxwb2x5Z29uIGZpbGw9IiM1Mjk0Q0YiIHBvaW50cz0iMTI3Ljk5OTggMjQ1Ljc1NzkgMTI3Ljk5OTggMzA3LjE5OTkgMjU1Ljk5OTggMjQzLjYwODkgMjU1Ljk5OTggMjA3LjYwODkiLz4NCgkJPHBvbHlnb24gZmlsbD0iIzUyOTRDRiIgcG9pbnRzPSIxMjggMjAwLjA1MzcgMTgzLjQ2NyAxOTEuOTI4NyAxODMuNDY3IDExNS44OTE3IDEyOCAxMDcuODkxNyIvPg0KCTwvZz4NCjwvc3ZnPg==&logoColor=white">
<img src="https://img.shields.io/badge/Amazon RDS-527FFF?style=for-the-badge&logo=Amazon RDS&logoColor=white">
<img src="https://img.shields.io/badge/Nginx-009639?style=for-the-badge&logo=Nginx&logoColor=white">
<img src="https://img.shields.io/badge/Certbot-e11b22?style=for-the-badge&logo=&logoColor=white">
<img src="https://img.shields.io/badge/GitHub Actions-2088FF?style=for-the-badge&logo=GitHub Actions&logoColor=white">
<img src="https://img.shields.io/badge/Github Secret-ef5033?style=for-the-badge&logo=data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZpZXdCb3g9IjAgMCA0MzEuOTMgNDMxLjc4Ij48ZGVmcz48c3R5bGU+LmNscy0xe2ZpbGw6I2ZmZmZmZjt9PC9zdHlsZT48L2RlZnM+PGcgaWQ9IuugiOydtOyWtF8yIiBkYXRhLW5hbWU9IuugiOydtOyWtCAyIj48ZyBpZD0i66CI7J207Ja0XzEtMiIgZGF0YS1uYW1lPSLroIjsnbTslrQgMSI+PHBhdGggY2xhc3M9ImNscy0xIiBkPSJNMTIzLDBjNiwxLjQ4LDEyLjE3LDIuNzMsMTguMTUsNC40OGExMDkuMTgsMTA5LjE4LDAsMCwxLDQ3LDI4LjI1YzkuNDMsOS40MSwxOC45MywxOC43NiwyOC4yMSwyOC4zMiwyLjMzLDIuNCwzLjU3LDIuMjUsNS44NywwLDEyLjY0LTEyLjU0LDI2LjI1LTEyLjQsMzguODMuMTdMNDIyLjksMjIzYzEyLDEyLDEyLDI2LjMuMTQsMzguMlEzNDIuMSwzNDIuMDksMjYxLjE4LDQyM2MtNCw0LTguMiw3LjMyLTE0LDcuOS0zLDEuMTMtNi4yMS0uNDMtOS4xNy44OWgtMWMtNy41OC0uNTUtMTIuODctNC44Mi0xOC4wNy0xMFExMzguNDcsMzQxLDU3LjgsMjYwLjU4QzQ3LDI0OS44Myw0Ni41NiwyMzQuOTQsNTYuNTUsMjIzLjQyYzMuMS0zLjU3LDMuMTItNC44NC0uNDItOC40NC04Ljg5LTktMTgtMTcuNzgtMjYuOC0yNi45MS0xMS43OS0xMi4yOC0yMC44NS0yNi4yOS0yNi00Mi42OEMxLjEsMTM4LjM3LDEuNDcsMTMxLDAsMTIzLjk0Vjk1YzEuNDItMTAuMTksMi0yMC40Nyw2LjQ4LTMwLjFDMjAuNTMsMzQuMzksNDMsMTMuMzcsNzUsMi45MSw4MS44MS43LDg5LDEuMjQsOTYsMFpNMjkxLjU4LDIyOC45MmMyLjY4LDIuNiwzLjA2LDQuNzYsMi4xNiw4LjE2LTMuMjQsMTIuMjIsMi40MiwyNS4xOCwxMy40OSwzMS42NWEyOC45MSwyOC45MSwwLDAsMCw0MC4wOC0zOC41N2MtNi0xMS4yNC0xOC44MS0xNy40NS0zMS4xNC0xNC42Ni0zLjI4Ljc0LTUuMi0uMDgtNy40LTIuMzJxLTE3LjM2LTE3LjYyLTM1LTM1Yy0yLjYyLTIuNTYtMy40OS00Ljc4LTIuNzgtOC41NmEyOC41MiwyOC41MiwwLDAsMC0zMy42OS0zMy4yM2MtMy4yOS42OC01LjE3LS4wOS03LjM4LTIuMzFRMTk0LDk4LDE1Ny44Niw2MmMtMTEuNDktMTEuNS0yNS4zMy0xOC4zNS00MS40Ny0xOS44OEM4Ni45MSwzOS4zMiw2MC4yOSw1NC44LDQ3Ljg4LDgxLjVjLTExLjksMjUuNi02LjI3LDU1Ljc1LDE0LjI5LDc2LjQzLDkuMjgsOS4zMywxOC43NSwxOC40OCwyNy44NCwyOCwyLjkyLDMuMDcsNC40MSwyLjg4LDcuMywwLDI0LTI0LjI2LDQ4LjI3LTQ4LjMyLDcyLjM0LTcyLjU0LDIuMzUtMi4zNywzLjcxLTIuOCw2LjI4LS4xOHExOC41MiwxOC45MywzNy40NSwzNy40NmMyLjA5LDIsMi4zNCwzLjc4LDEuNTcsNi41YTI4LjA4LDI4LjA4LDAsMCwwLDEzLjMsMzIuNTFjMy4wNywxLjcyLDMuNjIsMy42NSwzLjYxLDYuNzJxLS4xMiw0Ny40NiwwLDk0LjkzYzAsMy4xNi0uNzUsNS0zLjcxLDYuNjZhMjguMjksMjguMjksMCwwLDAtMTMuNTQsMzEuNTksMjguODUsMjguODUsMCwwLDAsNTUuNTQsMi4yMywyOC4yMSwyOC4yMSwwLDAsMC0xMC45NS0zMi41Niw3LjU0LDcuNTQsMCwwLDEtMy44MS03LjI1Yy4xLTMxLC4wNi02MiwuMDctOTIuOTMsMC0xLjQ3LjEyLTIuOTQuMjUtNS45QzI2OC4yNSwyMDUuNjgsMjc5Ljc2LDIxNy40NiwyOTEuNTgsMjI4LjkyWiIvPjwvZz48L2c+PC9zdmc+&logoColor=white">

### Team Tools

<img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white">
<img src="https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white">
<img src="https://img.shields.io/badge/notion-000000?style=for-the-badge&logo=notion&logoColor=white">
<img src="https://img.shields.io/badge/discord-5865F2?style=for-the-badge&logo=discord&logoColor=white">

## 프로젝트 소개

### 회원 인증

(사진)
서비스 자체 회원은 물론 OAuth를 이용한 구글/카카오 계정을 통해 회원가입 및 로그인을 할 수 있습니다.

### 매칭 서비스

(사진)
원하는 상대에 대한 정보를 입력해 등록하고 다른 사람들이 등록한 프로필을 확인할 수 있습니다. 필터나 검색을 이용해 원하는 상대를 찾을 수 있습니다.

여러 과외나 학생을 관리하기 위한 다중 프로필 기능을 지원하며, 서비스 내에서 매칭을 할 수 있도록 메세지 기능을 제공합니다.

### 과외 관리 서비스

(사진)
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
|                                                        (김민경 이미지)                                                        |                                                       이수영 이미지                                                        |                                                       신승구 이미지                                                        |                                                       강호수 이미지                                                        |                                                    유영민 이미지                                                     |                                                       김다은 이미지                                                        |
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
