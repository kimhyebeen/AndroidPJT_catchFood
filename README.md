# AndroidPJT_catchFood

Not sure what to eat this time? If so, use this application. The application selects the menu for you.

## ERROR

2020.03.16

SetFragment1에서 RecyclerView 안에 있는 item의 edit버튼을 눌러 dialog를 띄울 경우, 두 번 이상 띄우게 되면

“The specified child already has a parent. You must call removeView() on the child's parent first.” 라는 오류가 발생한다.

dialog가 SetActivity.java의 Context를 사용하기 때문인거 같은데, 어떻게 Activity의 위가 아닌 fragment 위에서 dialog를 실행해야할 지 모르겠다.

## Basic Information

기획 및 개발 기간 : 2020.03.13 ~ ing 

기술 스택 : Android Studio, Java, Database, SQLite

## Application Information

Database table :

| Attribute | Type                                  |
| --------- | ------------------------------------- |
| _id       | integer **PRIMARY KEY autoincrement** |
| food      | text                                  |
| prefer    | integer                               |
