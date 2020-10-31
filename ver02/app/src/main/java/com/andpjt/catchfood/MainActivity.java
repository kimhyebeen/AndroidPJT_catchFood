package com.andpjt.catchfood;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class MainActivity extends AppCompatActivity {
    private AdView mAdView;

    ImageView menuButton;
    TextView menuTextView;
    TextView preMenuText;
    TextView helpText;

    Timer timer;
    TimerTask timerTask;
    SQLiteDatabase database;
    DBHelper dbHelper = DBHelper.getDbHelper();

    ArrayList<String> menu;
    Handler handler = new Handler();
    View dialogView;
    AlertDialog dialog;
    AlertDialog.Builder builder;

    int cycle = 0;
    int n=0;
    double random;
    boolean btncheck=true;

    @Override
    protected void onResume() {
        super.onResume();
        /* random menu에 보여줄 데이터 가져오기 */
        menuTextView.setText(R.string.hint2);
        preMenuText.setText("");
        cycle=0;
        init();
        Log.d("MainAcitivity", "btncheck = "+btncheck);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (!btncheck) {
            timerTask.cancel();
            timer.cancel();
            timer.purge();
            btncheck = true;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /* 애드몹 광고 */
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        /* 인터넷 연결 확인 */
        int internet = getInternet(getApplicationContext());
        if (internet==0) Toast.makeText(getApplicationContext(), "인터넷을 연결해주세요", Toast.LENGTH_SHORT).show();

        menuButton = findViewById(R.id.menuButton);
        menuTextView = findViewById(R.id.menuTextView);
        preMenuText = findViewById(R.id.preMenuText);
        helpText = findViewById(R.id.helpText);
        Button datasetButton = findViewById(R.id.datasetButton);

        /* 데이터베이스 세팅 */
        if (database == null) {
            Log.d("MainActivity", "데이터베이스 오픈");
            if (dbHelper == null) {
                dbHelper = new DBHelper(getApplicationContext(), "foodlist", null, 1);
                dbHelper.setDbHelper(dbHelper);
            }
            database = dbHelper.getWritableDatabase();
//            dbHelper.dropTable(database);
            dbHelper.createTable(database);
        }

        /* menu random 버튼 활성화 */
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isdb()) Toast.makeText(getApplicationContext(), "데이터를 세팅해주세요.", Toast.LENGTH_SHORT).show();
                else {
                    if (!btncheck) {
                        timerTask.cancel();
                        timer.cancel();
                        timer.purge();
                        btncheck = true;
                        /* 이전에 나왔던 menu 보여주기 (최대 3개) */
                        String premenu = menuTextView.getText().toString();
                        if (cycle>2) {
                            preMenuText.setText(premenu);
                            cycle=0;
                            /* menu버튼을 3번 누를때마다 donot리스트에서 랜덤으로 message 보여주기 */
                            showMessage();
                        }
                        else preMenuText.setText(premenu+"\n"+preMenuText.getText());
                        cycle++;
                    }
                    else {
                        timerStart();
                        btncheck = false;
                    }
                }
            }
        });
        /* data setting 버튼 활성화 */
        datasetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isdb()) { // db에 아무 내용 없으면,,
                    Intent intent = new Intent(getApplicationContext(), InitActivity.class);
                    intent.putExtra("key", false);
                    startActivity(intent);
                } else { // db에 내용이 있을 경우,,
                    Intent intent = new Intent(getApplicationContext(), SetActivity.class);
                    intent.putExtra("key", true);
                    startActivity(intent);
                }
            }
        });

        dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_help, null, false);
        builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        Button outButton = dialogView.findViewById(R.id.outButton);
        dialog = builder.create();
        outButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        /* 도움말 활성화 */
        helpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });
    }

    public void init() {
        if (database != null) {
            menu = new ArrayList<>();
            Cursor cursor1 = dbHelper.selectAll(database);
            Log.d("MainActivity", ""+cursor1.getCount());
            if (cursor1.getCount()!=0) {
                for (int i=0;i<cursor1.getCount();i++) {
                    cursor1.moveToNext();
                    for (int j=0;j<cursor1.getInt(2);j++) menu.add(cursor1.getString(1));
                }
                /* 기존 string데이터 말고 가져온 데이터 random menu 보여주기 */
                if (btncheck)
                    timerStart();
            }
        }
    }

    public void timerStart() {
        if (isdb()) {
            btncheck = false;
            timerTask = new TimerTask() {
                @Override
                public void run() {
                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            random = Math.random() * menu.size();
                            menuTextView.setText(menu.get((int) random));
                        }
                    };
                    handler.post(runnable);
                    n++;
                }
            };
            timer = new Timer();
            timer.schedule(timerTask, 0, 80);
        } else {
            menuTextView.setText(R.string.hint2);
            btncheck = true;
        }
    }

    public boolean isdb() {
        Cursor cursordb = dbHelper.selectAll(database);
        int cnt = cursordb.getCount();
        if (cnt==0) return false;
        else return true;
    }

    public void showMessage() {
        String[] donot = {"답정너!!!", "그만 눌러,,.,,,", "그만 누를 때 됐잖아.,,....", "그만.., 그만.......", "하, 이제 진짜 마지막이다???",
                "진짜 이제 그만 누르면 안돼?", "그냥 추천해준거 먹지..??", "언제까지 누를거야?", "이러다가 밥 시간 다 지날듯ㅎㅎ", "ㅋㅋ아니ㅋㅋ 밥 안먹을거야??",
                "ㅎ...그냥 아무거나 먹어..", "야야ㅑ 밥시간 길어? 그냥 먹지??", "그래그래 계에에에속 눌러라..", "도대체 무슨 답을 기다리는거야?", "진짜 답정너..."};
        int rdm = (int) (Math.random() * donot.length);
        Toast.makeText(getApplicationContext(), donot[rdm], Toast.LENGTH_SHORT).show();
    }

    public static int getInternet(Context context)
    {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                return 1;
            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                return 2;
            }
        }
        return 0;
    }
}
