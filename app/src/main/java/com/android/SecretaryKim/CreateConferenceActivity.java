package com.android.SecretaryKim;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.SecretaryKim.DTO.ConferenceDTO;
import com.android.SecretaryKim.DTO.UserDTO;
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
                    conference.addJoinedUserNickname(user.getNickname());
                    conference.setUserId(user.getUid());
                    conference.setConfId(user.getUid() + "_" + conference.getTimestamp());// 회의 ID
                    conference.setTitle(title);
                    conference.setFinish(false);

                    mDatabase.child("conferences").child(conference.getConfId()).setValue(conference);
                    Log.d("user", conference.getConfId());
                    Log.d("user", user.getUid());

                    mDatabase.child("users").child(user.getUid()).child("conference").push().setValue(conference.getConfId());
                    Intent intent = new Intent(getApplicationContext(), MainListActivity.class);
                    intent.putExtra("conference", conference);//컨퍼런스객체넘겨주기
                    intent.putExtra("user", user);//컨퍼런스객체넘겨주기
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "회의제목을 입력해주세요", Toast.LENGTH_SHORT).show();
                }

        });
    }
}