package com.android.SecretaryKim;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


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
