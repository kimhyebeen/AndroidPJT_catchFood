package com.example.randommenu;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

public class SetActivity extends AppCompatActivity {
    EditText editText;
    RatingBar ratingBar2;
    RecyclerView recyclerView1;

    DBHelper dbHelper = DBHelper.getDbHelper();
    SQLiteDatabase database = dbHelper.getWritableDatabase();
    ContentValues values;
    ListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("데이터 세팅");

        editText = findViewById(R.id.editText);
        ratingBar2 = findViewById(R.id.ratingBar2);
        recyclerView1 = findViewById(R.id.recyclerView1);

        /* recyclerView1 세팅 */
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getApplicationContext());
        recyclerView1.setLayoutManager(linearLayoutManager1);
        listAdapter = new ListAdapter();
        recyclerView1.setAdapter(listAdapter);

        if (database!=null) {
            Cursor cursor1 = dbHelper.selectAll(database);
            for (int i=0;i<cursor1.getCount();i++) {
                cursor1.moveToNext();
                listAdapter.addItem(cursor1.getString(1), cursor1.getInt(2));
            }
            cursor1.close();
            listAdapter.notifyDataSetChanged();
        }

        /* add 버튼 활성화 */
        Button addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* 버튼을 누르면 데이터베이스에 저장 */
                String food = editText.getText().toString();
                int prefer = (int) ratingBar2.getRating();

                String sql = "SELECT * FROM menu WHERE food=\'"+food+"\'";
                Cursor cursor = database.rawQuery(sql, null);
                if (cursor.getCount()>0) {
                    Toast.makeText(getApplicationContext(), "이미 존재합니다.", Toast.LENGTH_SHORT).show();
                } else {
                    values = new ContentValues();
                    values.put("food", food);
                    values.put("prefer", prefer);
                    dbHelper.insertToTable(database, values);

                    /* recyclerView1에 추가 */
                    editText.setText("");
                    ratingBar2.setNumStars(5);
                    ratingBar2.setRating(3);
                    listAdapter.addItem(food, prefer);
                    listAdapter.notifyDataSetChanged();
                }
            }
        });
    }
}
