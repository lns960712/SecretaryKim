package com.android.SecretaryKim;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.SecretaryKim.DTO.ChatDTO;
import com.android.SecretaryKim.DTO.ConferenceDTO;
import com.android.SecretaryKim.DTO.UserDTO;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/* 채팅을 기능을 하는 클래스*/

public class ChatActivity extends AppCompatActivity {

    private static final String TAG = "ChatActivity";
    private RecyclerView crecyclerView;
    private RecyclerView.Adapter cAdapter;
    private RecyclerView.LayoutManager clayoutManager;
    private List<ChatDTO> chatDataset;
    private UserDTO user ;
    private ConferenceDTO conference;
    private Intent intent;
    private CheckBox CheckBox_ishighlight;
    private EditText EditText_chat;
    private Button Button_send;
    private DatabaseReference chatRef;
    private FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Button_send = findViewById(R.id.Button_send);
        EditText_chat = findViewById(R.id.EditText_chat);
        CheckBox_ishighlight = findViewById(R.id.CheckBox_ishighlight);

        //DB연결
        database = FirebaseDatabase.getInstance();
        intent = getIntent();

        conference = (ConferenceDTO) intent.getSerializableExtra("conference");//intent값받아오기
        chatRef = database.getReference().child("conferences").child(conference.getConfId()).child("chat");
        user = (UserDTO) intent.getSerializableExtra("user");//intent값받아오기
        Log.d("conference",conference.getTitle());
        Log.d("conferenceID",conference.getConfId());
        Button_send.setOnClickListener(v -> {
            String message = EditText_chat.getText().toString();
            if(message!=null) {
                ChatDTO chat = new ChatDTO();
                if(CheckBox_ishighlight.isChecked()){
                    chat.setHighlight(true);
                }else chat.setHighlight(false);
                chat.setNickname(user.getNickname());
                chat.setUser(user);
                chat.setMessage(message);

                chatRef.push().setValue(chat);
//                myRef.push().setValue(chat);//DB에 값 넣기
                EditText_chat.setText("");
                CheckBox_ishighlight.setChecked(false);
            }
        });

        //recyclerView
        crecyclerView = (RecyclerView) findViewById(R.id.chat_recycler_view);
        crecyclerView.setHasFixedSize(true);
        clayoutManager = new LinearLayoutManager(this);
        crecyclerView.setLayoutManager(clayoutManager);

        //어댑터 설정
        chatDataset = new ArrayList<>();
        cAdapter = new ChatAdapter(chatDataset, ChatActivity.this, user.getNickname());
        crecyclerView.setAdapter(cAdapter);


        //DB에서 데이터 가져오기
        chatRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                Log.d("CHAT_LOG : ", dataSnapshot.child("conferences").child(conference.getConfId()).getValue().toString());
                // 이 부분에 .child() 추가 해서 경로 바꿀 수 있음
//                ChatDTO chat = dataSnapshot.child("conferences").child(conference.getConfId()).getValue(ChatDTO.class);
//                ChatDTO chat = dataSnapshot.child(conference.getConfId()).child("chat").getValue(ChatDTO.class);
                ChatDTO chat = dataSnapshot.getValue(ChatDTO.class);
                ((ChatAdapter)cAdapter).addChat(chat);
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
