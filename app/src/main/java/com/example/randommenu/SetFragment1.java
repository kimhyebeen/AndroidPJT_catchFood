package com.example.randommenu;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SetFragment1 extends Fragment {
    EditText editText;
    RatingBar ratingBar2;
    RecyclerView recyclerView1;
    ListAdapter listAdapter;
    Button addButton;
    DBHelper dbHelper = DBHelper.getDbHelper();
    SQLiteDatabase database = dbHelper.getWritableDatabase();
    ContentValues values;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_set1, container, false);
        editText = rootView.findViewById(R.id.editText);
        ratingBar2 = rootView.findViewById(R.id.ratingBar2);
        recyclerView1 = rootView.findViewById(R.id.recyclerView1);

        /* recyclerView1 세팅 */
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getContext());
        recyclerView1.setLayoutManager(linearLayoutManager1);
        listAdapter = new ListAdapter();
        recyclerView1.setAdapter(listAdapter);
        setting();


        addButton = rootView.findViewById(R.id.addButton);
        addButton.setVisibility(View.VISIBLE);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* 버튼을 누르면 데이터베이스에 저장 */
                String food = editText.getText().toString();
                int prefer = (int) ratingBar2.getRating();

                String sql = "SELECT * FROM menu WHERE food=\'"+food+"\'";
                Cursor cursor = database.rawQuery(sql, null);
                if (cursor.getCount()>0) {
                    Toast.makeText(getContext(), "이미 존재합니다.", Toast.LENGTH_SHORT).show();
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
        return rootView;
    }

    public void setting() {
        /* 데이터베이스 세팅 */
        if (database!=null) {
            Cursor cursor1 = dbHelper.selectAll(database);
            for (int i=0;i<cursor1.getCount();i++) {
                cursor1.moveToNext();
                listAdapter.addItem(cursor1.getString(1), cursor1.getInt(2));
            }
            cursor1.close();
            listAdapter.notifyDataSetChanged();
        }
    }



    @Override
    public void onResume() {
        super.onResume();
        Log.d("Set111111", "onResume()");
        addButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("Set111111", "onPause()");
        addButton.setVisibility(View.INVISIBLE);
    }
}
