![language](https://img.shields.io/badge/language-Java-green)
![database](https://img.shields.io/badge/database-SQLite-d9fff8)

# Android_catchFood

Not sure what to eat this time? If so, use this application. The application selects the menu for you.

## Basic Information

2020.03.17 구글 플레이 스토어 출시
https://play.google.com/store/apps/details?id=com.andpjt.catchfood

## History
1. 기획 및 개발 (2020.03.13 ~ 2020.03.17)
2. 리팩토링 (2020.10.31)
  * 전체 UI 디자인 및 색상 변경
3. 리팩토링 (2020.11.11~)
  * Java파일을 Kotlin파일로 언어 변경
  * 데이터바인딩 적용
  * deprecated된 메서드 수정

## Application Information

![version](https://img.shields.io/badge/ver-2.0.2-e4ff4d)

<image src="./images/ver02-screen.png" width=900 />

Database table :

| Attribute | Type                                  |
| --------- | ------------------------------------- |
| _id       | integer **PRIMARY KEY autoincrement** |
| food      | text                                  |
| prefer    | integer                               |

## pre-version

![version](https://img.shields.io/badge/ver-1.0.8-e4ff4d)

기획 및 개발 기간 : 2020.03.13 ~ 2020.03.17

기술 스택 : 
* `Android Studio`
* (언어) `Java`
* (데이터베이스) `SQLite`

<image src="./images/ver01-screen1.png" width=700 />
