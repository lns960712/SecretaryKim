package com.android.SecretaryKim;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

/*
    회의에 관한 정보를 보여주는 액티비티
    MainListActivity 에서 회의 리스트를 터치하면 들어온다
    온라인 회의를 통해 ChatActivity 로 넘어간다
    오프라인 회의를 통해 OfflineStartActivity 로 넘어간다
    회의에 대한 진행사항을 Firebase 에서 받아 올 수 있어야 한다
    회의 제목,
    본인 프로필 사진, 이름,
    참가자 명단,
    회의 진행 사항에 대한 정보,
    온라인 회의, 오프라인 회의 버튼 필요
 */

public class BranchActivity extends AppCompatActivity {

    private static final String TAG = "BranchActivity";
    private Button Button_online;
    private Button Button_offline;
    private UserDTO user;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch);
        Button_online = findViewById(R.id.Button_online);
        Button_offline = findViewById(R.id.Button_offline);
        intent = getIntent();
        user = (UserDTO) intent.getSerializableExtra("user");//intent값 넘겨받기
        Log.d(TAG, "onCreate: "+ intent.getExtras().getString("nickname"));
        Log.d(TAG, "onCreate: "+ intent.getExtras().getString("email"));

        Button_online.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
            intent.putExtra("user", user); // 닉네임 넘겨주기
//            intent.putExtra("email", this.intent.getExtras().getString("email")); // 이메일 넘겨주기
            startActivity(intent);
        });
        Button_offline.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), OfflineStartActivity.class);
            intent.putExtra("nickname", this.intent.getExtras().getString("nickname")); // 닉네임 넘겨주기
            intent.putExtra("email", this.intent.getExtras().getString("email")); // 이메일 넘겨주기
            startActivity(intent);
        });
    }
}