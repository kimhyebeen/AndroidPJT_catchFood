![language](https://img.shields.io/badge/language-Java-green)
![database](https://img.shields.io/badge/database-SQLite-d9fff8)
![version](https://img.shields.io/badge/ver-1.0.8-e4ff4d)

# Android_catchFood

Not sure what to eat this time? If so, use this application. The application selects the menu for you.

## Basic Information

기획 및 개발 기간 : 2020.03.13 ~ 2020.03.17

기술 스택 : 
* `Android Studio`
* (언어) `Java`
* (데이터베이스) `SQLite`

2020.03.17 구글 플레이 스토어 출시
https://play.google.com/store/apps/details?id=com.andpjt.catchfood

## Application Information

화면 구성 및 동작 :

<image src="./images/screen1.png" width=700 />
<image src="./images/screen2.png" width=700 />

Database table :

| Attribute | Type                                  |
| --------- | ------------------------------------- |
| _id       | integer **PRIMARY KEY autoincrement** |
| food      | text                                  |
| prefer    | integer                               |
