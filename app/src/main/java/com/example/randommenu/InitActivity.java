package com.example.randommenu;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class InitActivity extends AppCompatActivity {
    LinearLayout menuList1;
    LinearLayout menuList2;
    DBHelper dbHelper = DBHelper.getDbHelper();
    SQLiteDatabase database = dbHelper.getWritableDatabase();
    LinearLayout.LayoutParams params;
    ContentValues values;

    String[] foo = {"언니네 화덕피자", "비스트로 따봄", "카페 모리아", "더치킨", "미파닭", "미스터 피자", "213 버거", "뉴욕 버거", "머꼬가자", "송호성 돈까스", "브라운 돈까스", "알촌", "일미", "육앤샤", "덮밥집", "조박사 부대찌개", "이서식당", "찌개찌개", "취해", "마라미방"};
    boolean[] touch = new boolean[20];
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("초기 데이터 세팅");

        menuList1 = findViewById(R.id.menuList1);
        menuList2 = findViewById(R.id.menuList2);

        params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.bottomMargin = 15;
        params.weight = 1;
        params.width = LinearLayout.LayoutParams.MATCH_PARENT;
        params.height = LinearLayout.LayoutParams.MATCH_PARENT;

        /* menulist에 동적 버튼 추가하기 */
        Button.OnClickListener ocl = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!touch[v.getId()]) {
                    touch[v.getId()] = !touch[v.getId()];
                    v.setBackgroundResource(R.drawable.btn_click);
                } else {
                    touch[v.getId()] = !touch[v.getId()];
                    v.setBackgroundResource(R.drawable.btn_unclick);
                }
            }
        };

        for (int i=0;i<20;i++) {
            btn = new Button(getApplicationContext());
            btn.setText(foo[i]);
            btn.setLayoutParams(params);
            btn.setId(i);
            btn.setBackgroundResource(R.drawable.btn_unclick);
            btn.setOnClickListener(ocl);
            if (i<10) menuList1.addView(btn);
            else menuList2.addView(btn);
        }

        /* add 버튼 활성화 */
        Button addButton2 = findViewById(R.id.addButton2);
        addButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* add버튼을 누르면 데이터베이스에 저장 */
                for (int i=0;i<20;i++) {
                    if (touch[i]) {
                        values = new ContentValues();
                        values.put("food", foo[i]);
                        values.put("prefer", 1);
                        dbHelper.insertToTable(database, values);
                    }
                }
                onBackPressed();
            }
        });

    }
}
