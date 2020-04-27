package com.android.secretarykim;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView crecyclerView;
    private RecyclerView.Adapter cAdapter;
    private RecyclerView.LayoutManager clayoutManager;
    private List<ChatDTO> chatDataset;
    private String nick = "nick1";

    private EditText EditText_chat;
    private Button Button_send;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Button_send = findViewById(R.id.Button_send);
        EditText_chat = findViewById(R.id.EditText_chat);
        Button_send.setOnClickListener(v -> {
            String message = EditText_chat.getText().toString();
            if(message!=null) {
                ChatDTO chat = new ChatDTO();
                chat.setNickname(nick);
                chat.setMessage(message);
                myRef.push().setValue(chat);
                EditText_chat.setText("");
            }
        });
        //recyclerView
        crecyclerView = (RecyclerView) findViewById(R.id.chat_recycler_view);
        crecyclerView.setHasFixedSize(true);
        clayoutManager = new LinearLayoutManager(this);
        crecyclerView.setLayoutManager(clayoutManager);
        // specify an adapter (see also next example)
        //어댑터 설정
        chatDataset = new ArrayList<>();
        cAdapter = new ChatAdapter(chatDataset, ChatActivity.this, nick);
        crecyclerView.setAdapter(cAdapter);
        //DB연결
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference();


        //DB에서 데이터 가져오기
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d("CHAT_LOG", dataSnapshot.getValue().toString());
                ChatDTO chat = dataSnapshot.getValue(ChatDTO.class);
                ((ChatAdapter)cAdapter).addChat(chat);
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }
            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
