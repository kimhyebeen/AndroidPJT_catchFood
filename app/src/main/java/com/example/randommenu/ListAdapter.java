package com.example.randommenu;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ItemViewHolder> {
    private ArrayList<String> listFood = new ArrayList<>();
    private ArrayList<Integer> listRate = new ArrayList<>();
    Context context;
    View dialogView;
    AlertDialog.Builder builder;
    ContentValues values;
    DBHelper dbHelper = DBHelper.getDbHelper();
    SQLiteDatabase database = dbHelper.getWritableDatabase();

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_set1, parent, false);
        dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_edit, null, false);

        return new ItemViewHolder(view);
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView menuName;
        RatingBar ratingBar;
        String foodName;
        int rate;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            menuName = itemView.findViewById(R.id.menuName);
            ratingBar = itemView.findViewById(R.id.ratingBar);

            Button editButton = itemView.findViewById(R.id.editButton);
            editButton.setOnClickListener(new View.OnClickListener() {
                EditText editText2;
                RatingBar ratingBar3;

                @Override
                public void onClick(View v) {
                    /* 버튼을 눌렀을 때 dialog 띄우기 */
                    builder = new AlertDialog.Builder(context);
                    builder.setView(dialogView);

                    editText2 = dialogView.findViewById(R.id.editText2);
                    ratingBar3 = dialogView.findViewById(R.id.ratingBar3);
                    foodName = menuName.getText().toString();
                    rate = (int)ratingBar.getRating();
                    editText2.setText(foodName);
                    ratingBar3.setRating(rate);
                    ratingBar3.setNumStars(5);
                    final AlertDialog dialog = builder.create();

                    Button editButton2 = dialogView.findViewById(R.id.editButton2);
                    editButton2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            /* todo - 중복 food로 수정했을 때 처리 */
                            values = new ContentValues();
                            values.put("food", editText2.getText().toString());
                            values.put("prefer", (int) ratingBar3.getRating());
                            dbHelper.updateData(database, foodName, values);

                            int idx = listFood.indexOf(foodName);
                            Log.d("ListAdapter", ""+idx);
                            listFood.set(idx, editText2.getText().toString());
                            listRate.set(idx, (int)ratingBar3.getRating());
                            notifyItemChanged(idx);

                            dialog.dismiss();
//                            dialog.cancel();
                        }
                    });

                    Button deleteButton = dialogView.findViewById(R.id.deleteButton);
                    deleteButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String s = menuName.getText().toString();
                            int i = (int) ratingBar.getRating();
                            /* 데이터베이스에서 해당 아이템 삭제 */
                            dbHelper.deleteTable(database, s);

                            /* 다이아로그의 삭제 버튼을 눌렀을 때 데이터 삭제 */
                            int idx = listFood.indexOf(s);
                            Log.d("ListAdapter-delete", ""+idx);
                            listFood.remove(idx);
                            listRate.remove(idx);
                            notifyItemRemoved(idx);

//                            dialog.cancel();
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }
            });
        }

        public void onBind(String data, int ratingNum) {
            menuName.setText(data);
            ratingBar.setNumStars(5);
            ratingBar.setRating(ratingNum);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapter.ItemViewHolder holder, int position) {
        holder.onBind(listFood.get(position), listRate.get(position));
    }

    @Override
    public int getItemCount() {
        return listFood.size();
    }

    public void addItem(String data, int num) {
        // 외부에서 item을 추가시킬 함수입니다.
        listFood.add(data);
        listRate.add(num);
    }
}
