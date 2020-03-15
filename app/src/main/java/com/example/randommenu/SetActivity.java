package com.example.randommenu;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class SetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        /* 데이터베이스가 비어있는지 MainActivity에서 받아오기 */
        Intent intent = getIntent();
        boolean check = intent.getBooleanExtra("key",true);

        ActionBar actionBar = getSupportActionBar();
        SetFragment1 fragment1 = new SetFragment1();
        SetFragment2 fragment2 = new SetFragment2();

        /* 데이터베이스가 비어있으면 fragment2를, 비어있지 않으면 fragment1을 containerF에 붙여줘요 */
        if (!check) {
            actionBar.setTitle("데이터 세팅");
            getSupportFragmentManager().beginTransaction().replace(R.id.containerF, fragment2).addToBackStack(null).commit();
        } else {
            actionBar.setTitle("데이터 추가");
            getSupportFragmentManager().beginTransaction().replace(R.id.containerF, fragment1).addToBackStack(null).commit();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        /* todo - BackPressed 문제 해결하기!! */
        FragmentManager fragmentManager = getSupportFragmentManager();
        int backStackCount = fragmentManager.getBackStackEntryCount();
        Log.d("SetActivity", "BackStackCount : "+backStackCount);
        if(1 < backStackCount) fragmentManager.popBackStack();
        else finish();
    }
}
