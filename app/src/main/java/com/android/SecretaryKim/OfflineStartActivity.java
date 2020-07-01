package com.android.SecretaryKim;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.dinuscxj.progressbar.CircleProgressBar;

import java.util.ArrayList;

public class OfflineStartActivity extends AppCompatActivity {

    private TextView timeText;
    private CircleProgressBar timePBar;
    private boolean isRunning = false; // 회의 재개 중단 여부 판단
    private boolean isStarted = false; // 회의 시작 종료 여부 판단
    private Thread timeThread = null;

    Intent intent;
    SpeechRecognizer mRecognizer;
    ArrayList<String> matches;
    Button startButton;
    Button endButton;
    Button sttStartButton;
    TextView textView;
    final int PERMISSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_start);

        timeText = findViewById(R.id.timeText);
        timePBar = findViewById(R.id.timePBar);

        timePBar.setMax(1800000);
        timePBar.setProgress(0);
        timePBar.setProgressTextSize(0);

        if ( Build.VERSION.SDK_INT >= 23 ){
            // 퍼미션 체크
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET,
                    Manifest.permission.RECORD_AUDIO},PERMISSION);
        }

        startButton = (Button) findViewById(R.id.startButton);
        endButton = findViewById(R.id.endButton);
        sttStartButton = findViewById(R.id.sttStartButton);

        mRecognizer=SpeechRecognizer.createSpeechRecognizer(this);

        startButton.setOnClickListener(v -> {
                if ( isRunning == false && isStarted == false) { // 첫 시작시
                    isRunning = true;
                    isStarted = true;
                    matches = new ArrayList<>();
                    timeThread = new Thread(new timeThread());
                    timeThread.start();
                    startButton.setText("회의 중지");

                } else {
                    if(startButton.getText().equals("회의 시작") || startButton.getText().equals("회의 재개")) {
                        startButton.setText("회의 중지");
                    } else startButton.setText("회의 재개");
                    isRunning = !isRunning;
                }

        });
        endButton.setOnClickListener(v -> {
            if(isStarted) {
                isRunning = false;
                isStarted = false;

                String str = "";
                for(String s : matches) {
                    str += s + " ";
                }

                mRecognizer.destroy();
                timeThread.interrupt();

                Intent in = new Intent(getApplicationContext(), ResultActivity.class);

                in.putExtra("list", str);

                startActivity(in);
            }
        });

        sttStartButton.setOnClickListener(v -> {
            if(isStarted) {
                if(sttStartButton.getText().equals("음성 인식 시작") || sttStartButton.getText().equals("음성 인식 재개")) {
                    intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                    intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,getPackageName());
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"ko-KR");

                    mRecognizer.setRecognitionListener(listener);
                    mRecognizer.startListening(intent);
                    sttStartButton.setText("음성 인식 중지");
                } else if (sttStartButton.getText().equals("음성 인식 중지")) {
                    mRecognizer.stopListening();
                    sttStartButton.setText("음성 인식 재개");
                }
            }
        });
    }

    private RecognitionListener listener = new RecognitionListener() {
        @Override
        public void onReadyForSpeech(Bundle params) {}
        @Override
        public void onBeginningOfSpeech() {}
        @Override
        public void onRmsChanged(float rmsdB) {}
        @Override
        public void onBufferReceived(byte[] buffer) {}
        @Override
        public void onEndOfSpeech() {}
        @Override
        public void onError(int error) {
            String message;

            switch (error) {
                case SpeechRecognizer.ERROR_AUDIO:
                    message = "오디오 에러";
                    break;
                case SpeechRecognizer.ERROR_CLIENT:
                    message = "클라이언트 에러";
                    break;
                case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                    message = "퍼미션 없음";
                    break;
                case SpeechRecognizer.ERROR_NETWORK:
                    message = "네트워크 에러";
                    break;
                case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                    message = "네트웍 타임아웃";
                    break;
                case SpeechRecognizer.ERROR_NO_MATCH:
                    message = "찾을 수 없음";
                    break;
                case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                    message = "RECOGNIZER가 바쁨";
                    break;
                case SpeechRecognizer.ERROR_SERVER:
                    message = "서버가 이상함";
                    break;
                case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                    message = "말하는 시간초과";
                    break;
                default:
                    message = "알 수 없는 오류임";
                    break;
            }
            Toast.makeText(getApplicationContext(), "에러가 발생하였습니다. : " + message,Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResults(Bundle results) {
            // 말을 하면 ArrayList에 단어를 넣고 textView에 단어를 이어줍니다.
            for(String s : results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)) {
                matches.add(s);
            }
            intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,getPackageName());
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"ko-KR");
            mRecognizer.setRecognitionListener(listener);
            mRecognizer.startListening(intent);
        }

        @Override
        public void onPartialResults(Bundle partialResults) {}
        @Override
        public void onEvent(int eventType, Bundle params) {}
    };

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
