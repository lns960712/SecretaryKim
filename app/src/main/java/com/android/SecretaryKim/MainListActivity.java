package com.android.SecretaryKim;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

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
    private UserDTO user;
    private Button makeButton;
    private Button enterButton;
    private ImageView imageView;
    private DatabaseReference mDatabase;
    private RecyclerView conrecyclerView;
    private RecyclerView.Adapter conAdapter;
    private RecyclerView.LayoutManager conlayoutManager;
    private List<ConferenceDTO> conferenceDataset;
    private DatabaseReference myRef;
    private FirebaseAuth mauth; // 지우지 말것

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list);

        mauth = FirebaseAuth.getInstance();
        //DB연결
        mDatabase = FirebaseDatabase.getInstance().getReference("conferences");

        makeButton = findViewById(R.id.makeConference);
        imageView = findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.usericon);
        intent = getIntent();
        user = (UserDTO) intent.getSerializableExtra("user");//intent값 넘겨받기
        makeButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), CreateConferenceActivity.class);
            intent.putExtra("user", user); // 유저객체넘겨주기
//            restoreData();
            startActivity(intent);
        });
        enterButton.setOnClickListener(v -> { // email로 confID 받아서 참가해야함
            void show()
            {
                final EditText edittext = new EditText(this);

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("AlertDialog Title");
                builder.setMessage("AlertDialog Content");
                builder.setView(edittext);
                builder.setPositiveButton("입력",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getApplicationContext(),edittext.getText().toString() ,Toast.LENGTH_LONG).show();
                            }
                        });
                builder.setNegativeButton("취소",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                builder.show();
            };

        });
        //recyclerView
        conrecyclerView = findViewById(R.id.conference_recycler_view);
        conrecyclerView.setHasFixedSize(true);
        conlayoutManager = new LinearLayoutManager(this);
        conrecyclerView.setLayoutManager(conlayoutManager);
        // specify an adapter (see also next example)
        //어댑터 설정
        conferenceDataset = new ArrayList<>();
        conAdapter = new ConferenceAdapter(conferenceDataset, MainListActivity.this, user.getUid(), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Object object = v.getTag();
                if(object!=null){//null체크를 해줘야 좋다.
                    int position = (int) object;
                    ConferenceDTO conference = ((ConferenceAdapter)conAdapter).getConference(position);//선언이 RecyclerView.Adapter로 되어 있어서 형변환이 필요함
                    Intent intent = new Intent(getApplicationContext(),BranchActivity.class);
                    intent.putExtra("user", user); // 유저객체넘겨주기
                    intent.putExtra("conference",conference);
                    startActivity(intent);
                }
            }
        });
        conrecyclerView.setAdapter(conAdapter);
        //DB연결
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        myRef = database.getReference();

        //DB에서 데이터 가져오기
        // 이 부분에 .child() 추가 해서 경로 바꿀 수 있음
        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d("CONFERENCE_LOG : ", dataSnapshot.getKey());
                ConferenceDTO conference = dataSnapshot.getValue(ConferenceDTO.class);
//                ConferenceDTO conference = dataSnapshot.child("conferences").getValue(ConferenceDTO.class);
                ((ConferenceAdapter)conAdapter).addConference(conference);
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) { }
            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });

    }

//    protected void restoreData() {
//        // 파이어베이스에 데이터 저장
//
//        conference = new ConferenceDTO();
//        Long tsLong = System.currentTimeMillis() / 1000;// 현재 시간을 나타내는 timestamp를 생성
//        conference.setTimestamp(tsLong.toString());
//        System.out.println("this time is :" + tsLong.toString());
//        conference.setUserId(user.getUid());
//        conference.setConfId(user.getUid() + "_" + conference.getTimestamp());// 회의 ID
//        mDatabase.child("conferences").child(conference.getConfId()).setValue(conference);
//        Log.d("user", conference.getConfId());
//        Log.d("user", user.getUid());
//        // 유저 정보에 참여하고 있는 회의 저장 setValue가 아닌 add인지 확인 필요
//        // DB상에서 리스트로 보일 필요 있음
//        mDatabase.child("users").child(user.getUid()).child("conference").setValue(conference.getConfId());
//
//    }
}