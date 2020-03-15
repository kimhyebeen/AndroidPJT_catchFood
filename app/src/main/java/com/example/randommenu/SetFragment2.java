package com.example.randommenu;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SetFragment2 extends Fragment {
    LinearLayout menulist1;
    LinearLayout menulist2;
    Button addButton2;
    DBHelper dbHelper = DBHelper.getDbHelper();
    SQLiteDatabase database = dbHelper.getWritableDatabase();
    LinearLayout.LayoutParams params;
    ContentValues values;

    String[] foo = {"언니네 화덕피자", "비스트로 따봄", "카페 모리아", "더치킨", "미파닭", "미스터 피자", "213 버거", "뉴욕 버거", "머꼬가자", "송호성 돈까스", "브라운 돈까스", "알촌", "일미", "육앤샤", "덮밥집", "조박사 부대찌개", "이서식당", "찌개찌개", "취해", "마라미방"};
    boolean[] touch = new boolean[20];
    Button btn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_set2, container, false);
        menulist1 = rootView.findViewById(R.id.menuList1);
        menulist2 = rootView.findViewById(R.id.menuList2);

        params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.bottomMargin = 15;
        params.weight = 1;
        params.width = LinearLayout.LayoutParams.MATCH_PARENT;
        params.height = LinearLayout.LayoutParams.MATCH_PARENT;

        /* menulist에 동적 버튼 추가하기 */
        addButton();

        addButton2 = rootView.findViewById(R.id.addButton2);
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
                getActivity().onBackPressed();
            }
        });

        return rootView;
    }

    public void addButton() {
        Button.OnClickListener ocl = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!touch[v.getId()]) {
                    touch[v.getId()] = !touch[v.getId()];
                    v.setBackgroundResource(R.drawable.btndesign_add3ss);
                } else {
                    touch[v.getId()] = !touch[v.getId()];
                    v.setBackgroundResource(R.drawable.btndesign_add3);
                }
            }
        };

        for (int i=0;i<10;i++) {
            btn = new Button(getContext());
            btn.setText(foo[i]);
            btn.setLayoutParams(params);
            btn.setId(i);
            btn.setBackgroundResource(R.drawable.btndesign_add3);
            btn.setOnClickListener(ocl);
            menulist1.addView(btn);
        }
        for (int i=10;i<20;i++) {
            btn = new Button(getContext());
            btn.setText(foo[i]);
            btn.setLayoutParams(params);
            btn.setId(i);
            btn.setBackgroundResource(R.drawable.btndesign_add3);
            btn.setOnClickListener(ocl);
            menulist2.addView(btn);
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        Log.d("Set222222", "onResume()");
        addButton2.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("Set22222", "onPause()");
        addButton2.setVisibility(View.INVISIBLE);
    }
}
