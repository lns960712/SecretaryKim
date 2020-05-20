package com.android.SecretaryKim;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.dinuscxj.progressbar.CircleProgressBar;

public class OfflineStartActivity extends AppCompatActivity {

    private TextView timeText;
    private CircleProgressBar timePBar;
    private boolean isRunning = false; // 회의 재개 중단 여부 판단
    private boolean isStarted = false; // 회의 시작 종료 여부 판단

    private Thread timeThread = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_start);

        timeText = findViewById(R.id.timeText);
        timePBar = findViewById(R.id.timePBar);

        timePBar.setMax(1800000);
        timePBar.setProgress(0);
        timePBar.setProgressTextSize(0);
        timeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( isRunning != true && isStarted != true) { // 첫 시작시
                    isRunning = true;
                    isStarted = true;

                    timeThread = new Thread(new timeThread());
                    timeThread.start();
                }

                if ( isStarted == true ) {
                    isRunning = !isRunning;
                }
            }
        });
        timeText.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(isStarted) {
                    isRunning = false;
                    isStarted = false;
                    timeThread.interrupt();
                }
                return false;
            }
        });

    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int mSec = msg.arg1 % 100;
            int sec = (msg.arg1 / 100) % 60;
            int min = (msg.arg1 / 100) / 60;
            int hour = (msg.arg1 / 100) / 360;
            //1000이 1초 1000*60 은 1분 1000*60*10은 10분 1000*60*60은 한시간

            @SuppressLint("DefaultLocale") String result = String.format("%02d:%02d:%02d:%02d", hour, min, sec, mSec);

            timePBar.setProgress(msg.arg1);
            timeText.setText(result);
        }
    };

    public class timeThread implements Runnable {
        @Override
        public void run() {
            int i = 0;

            while (true) {
                while (isRunning) { //일시정지를 누르면 멈춤
                    Message msg = new Message();
                    msg.arg1 = i++;
                    handler.sendMessage(msg);

                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        runOnUiThread(new Runnable(){
                            @Override
                            public void run() {
                                timeText.setText("");
                                timeText.setText("00:00:00:00");
                            }
                        });
                        return; // 인터럽트 받을 경우 return
                    }
                }
            }
        }
    }
}
