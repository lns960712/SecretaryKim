package com.android.SecretaryKim;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.SecretaryKim.DTO.UserDTO;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
    private Button Button_user_list;
    private Button Button_online;
    private Button Button_offline;
    private Button Button_email;
    private Button Button_finish_conference;
    private TextView TextView_isfinish;
    private UserDTO user;
    private ConferenceDTO conference;
    private Intent intent;
    private DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch);
        Button_user_list = findViewById(R.id.Button_user_list);
        Button_online = findViewById(R.id.Button_online);
        Button_offline = findViewById(R.id.Button_offline);
        Button_email = findViewById(R.id.Button_email);
        Button_finish_conference = findViewById(R.id.Button_finish_conference);
        TextView_isfinish = findViewById(R.id.TextView_isfinish);
        intent = getIntent();
        conference = (ConferenceDTO) intent.getSerializableExtra("conference");//intent값 넘겨받기
        user = (UserDTO) intent.getSerializableExtra("user");//intent값받아오기
        //회의 진행여부표시
        if(conference.isFinish()){
            TextView_isfinish.setText("종료된 회의");
        }else{
            TextView_isfinish.setText("진행중인 회의");
        }
        //사용자 초대 리스트로
        Button_user_list.setOnClickListener(v -> {
            Log.d("user", "UserList로 넘어감");
            Intent intent = new Intent(getApplicationContext(), UserListActivity.class);
            intent.putExtra("user", user); // 유저객체넘겨주기
            intent.putExtra("conference", conference);//컨퍼런스객체넘겨주기

            startActivity(intent);
        });
        //온라인 회의로(채팅기능연결)
        Button_online.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
            intent.putExtra("user", user); // 유저객체넘겨주기
            intent.putExtra("conference", conference);//컨퍼런스객체넘겨주기
            Log.d("user",user.getNickname());
            Log.d("user",user.getEmail());
//            intent.putExtra("email", this.intent.getExtras().getString("email")); // 이메일 넘겨주기
            startActivity(intent);
        });
        //오프라인 회의로(녹음기능연결)
        Button_offline.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), OfflineStartActivity.class);
            intent.putExtra("conference", conference);//컨퍼런스객체넘겨주기
            intent.putExtra("user", user); // 유저객체넘겨주기
            startActivity(intent);
        });
        //회의종료시키기
        Button_finish_conference.setOnClickListener(v -> {
            myRef = FirebaseDatabase.getInstance().getReference();
            conference.setFinish(true);
            myRef.child("conferences").child(conference.getConfId()).setValue(conference);
            Toast.makeText(getApplicationContext(), "회의가 종료되었습니다.", Toast.LENGTH_SHORT).show();

        });
        Button_email.setOnClickListener(new TextView.OnClickListener() {
            public void onClick(View view) {
                Intent email = new Intent(Intent.ACTION_SEND);
                email.setType("plain/text");
                String[] address = {"email@address.com"};
                email.putExtra(Intent.EXTRA_EMAIL, address);
                email.putExtra(Intent.EXTRA_SUBJECT, "test@test");
                email.putExtra(Intent.EXTRA_TEXT, "내용 미리보기 (미리적을 수 있음)");
                startActivity(email);
            }
        });

    }
}