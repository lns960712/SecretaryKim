package com.android.SecretaryKim;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateConferenceActivity extends AppCompatActivity {

    private static final String TAG = "ChatActivity";
    private UserDTO user ;
    private Intent intent;
    private EditText EditText_conference;
    private DatabaseReference mDatabase;
    private Button Button_create;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_conference);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        Button_create = findViewById(R.id.Button_create);
        EditText_conference = findViewById(R.id.EditText_conference);
        intent = getIntent();
        user = (UserDTO) intent.getSerializableExtra("user");
        Button_create.setOnClickListener(v -> {
            String title = EditText_conference.getText().toString();
                if (title.length() >= 1) {
                    ConferenceDTO conference = new ConferenceDTO();
                    Long tsLong = System.currentTimeMillis() / 1000;// 현재 시간을 나타내는 timestamp를 생성
                    conference.setTimestamp(tsLong.toString());
                    System.out.println("this time is :" + tsLong.toString());
                    conference.setUserId(user.getUid());
                    conference.setConfId(user.getUid() + "_" + conference.getTimestamp());// 회의 ID
                    conference.setTitle(title);
                    mDatabase.child("conferences").child(conference.getConfId()).setValue(conference);
                    Log.d("user", conference.getConfId());
                    Log.d("user", user.getUid());
                    // 유저 정보에 참여하고 있는 회의 저장 setValue가 아닌 add인지 확인 필요
                    // DB상에서 리스트로 보일 필요 있음
                    mDatabase.child("users").child(user.getUid()).child("conference").setValue(conference.getConfId());
                    Intent intent = new Intent(getApplicationContext(), BranchActivity.class);
                    intent.putExtra("user", user); // 유저객체넘겨주기
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "회의제목을 입력해주세요", Toast.LENGTH_SHORT).show();
                }

        });
    }
}