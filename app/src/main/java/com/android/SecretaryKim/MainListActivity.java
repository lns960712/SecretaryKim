package com.android.SecretaryKim;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.SecretaryKim.DTO.ConferenceDTO;
import com.android.SecretaryKim.DTO.UserDTO;
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
    private ImageView imageView;
    private DatabaseReference mConfDatabase;
    private RecyclerView conrecyclerView;
    private RecyclerView.Adapter conAdapter;
    private RecyclerView.LayoutManager conlayoutManager;
    private List<ConferenceDTO> conferenceDataset;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list);
        //DB연결
        mConfDatabase = FirebaseDatabase.getInstance().getReference("conferences");

        makeButton = findViewById(R.id.makeConference);
        imageView = findViewById(R.id.imageView);
//        imageView.setImageResource(R.drawable.usericon);
        intent = getIntent();
        user = (UserDTO) intent.getSerializableExtra("user");//intent값 넘겨받기
        makeButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), CreateConferenceActivity.class);
            intent.putExtra("user", user); // 유저객체넘겨주기
            startActivity(intent);
        });

        //Conference recyclerView
        conrecyclerView = findViewById(R.id.conference_recycler_view);
        conrecyclerView.setHasFixedSize(true);
        conlayoutManager = new LinearLayoutManager(this);
        conrecyclerView.setLayoutManager(conlayoutManager);

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


        // DB에서 회의방 데이터 가져오기
        mConfDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d("CONFERENCE_LOG : ", dataSnapshot.getKey());
                ConferenceDTO conference = dataSnapshot.getValue(ConferenceDTO.class);
//                ConferenceDTO conference = dataSnapshot.child("conferences").getValue(ConferenceDTO.class);
                if(conference.getUserId().equals(user.getUid())) {
                    ((ConferenceAdapter) conAdapter).addConference(conference);
                }
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
}