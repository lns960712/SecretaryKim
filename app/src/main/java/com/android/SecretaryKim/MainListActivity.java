package com.android.SecretaryKim;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/*
    본인의 정보를 보여주고 참가하고 있는 회의 목록을 볼 수 있는 액티비티
    LoginActivity 에서 넘어온다
    회의 생성 버튼을 누르면 ConferenceInfoActivity 로 넘어간다
    참가하고 있는 회의 정보에 대해 Firebase 에서 정보를 받아 올 수 있어야 한다
    사용자를 초대하는 기능이 필요하다
    프로필 사진, 참가하고 있는 회의 리스트,
    회의 생성 버튼이 필요하다
 */

public class MainListActivity extends AppCompatActivity {
    private Intent intent;
    private Button makeButton;
    private ImageView imageView;
    private DatabaseReference mDatabase;
    private FirebaseAuth mauth;
    private String ConfId = null; // 회의 ID

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list);

        mauth=FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        makeButton = findViewById(R.id.makeConference);
        imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.usericon);

        intent = getIntent();
        makeButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), BranchActivity.class);
            intent.putExtra("nickname", this.intent.getExtras().getString("nickname")); // 닉네임 넘겨주기
            intent.putExtra("email", this.intent.getExtras().getString("email")); // 이메일 넘겨주기
            restoreData();
            startActivity(intent);
        });

    }

    protected void restoreData () {
        // 현재 시간을 나타내는 timestamp를 생성
        Long tsLong = System.currentTimeMillis()/1000;
        String timestamp = tsLong.toString();
        System.out.println("this time is :" + timestamp);

        // 파이어베이스에 데이터 저장
        FirebaseUser user=mauth.getCurrentUser();
        ConfId = user.getUid() + "_" + timestamp; // 회의 ID
        mDatabase.child("conferences").child(ConfId).setValue("hello world");
        // 유저 정보에 참여하고 있는 회의 저장 setValue가 아닌 add인지 확인 필요
        // DB상에서 리스트로 보일 필요 있음
        mDatabase.child("users").child(user.getUid()).child("conference").setValue(ConfId);

    }

}
