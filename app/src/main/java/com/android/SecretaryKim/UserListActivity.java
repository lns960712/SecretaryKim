package com.android.SecretaryKim;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.SecretaryKim.DTO.UserDTO;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class UserListActivity extends AppCompatActivity {
    private UserDTO user;
    private ConferenceDTO conference;
    private CheckBox CheckBox_invite;
    private Button Button_invite;
    private Intent intent;

    private DatabaseReference mUsersDatabase; // 유저 목록 가져오는 Reference
    private DatabaseReference mconfDatabase; // 회의 목록 가져오는 Reference
    private RecyclerView userrecyclerView;
    private RecyclerView.Adapter userAdapter;
    private RecyclerView.LayoutManager userlayoutManager;
    private List<UserDTO> userDataset;
    private List<String> joinedUserId;


    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_userlist);
        Button_invite = findViewById(R.id.Button_invite);
        CheckBox_invite = findViewById(R.id.CheckBox_invite);
        mUsersDatabase = FirebaseDatabase.getInstance().getReference("users");
        mconfDatabase = FirebaseDatabase.getInstance().getReference("conferences");
        intent = getIntent();
        user = (UserDTO) intent.getSerializableExtra("user");//intent값 넘겨받기
        conference = (ConferenceDTO) intent.getSerializableExtra("conference");//intent값받아오기
        Button_invite.setOnClickListener(v ->{
//            mconfDatabase.child(conference.getConfId()).child("joinedUserId").setValue();
            mconfDatabase.child(conference.getConfId()).child("joinedUserId").setValue(joinedUserId);
            Intent intent = new Intent(getApplicationContext(), BranchActivity.class);
            startActivity(intent);
        });

        //userList recyclerView를 위한 어댑터 설정
        userrecyclerView = findViewById(R.id.users_recycler_view);
        userrecyclerView.setHasFixedSize(true);
        userlayoutManager = new LinearLayoutManager(this);
        userrecyclerView.setLayoutManager(userlayoutManager);

        joinedUserId = new ArrayList<>();
        userDataset = new ArrayList<>();
        userAdapter = new UserListAdapter(userDataset, UserListActivity.this, user.getUid(), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Object object = v.getTag();
                if(object!=null){//null체크를 해줘야 좋다.
                    int position = (int) object;
                    UserDTO users = ((UserListAdapter)userAdapter).getUserDTO(position);//선언이 RecyclerView.Adapter로 되어 있어서 형변환이 필요함
                    joinedUserId.add(users.getUid());
                    for (int i = 0; i<joinedUserId.size(); i ++) {
                        Log.d("User List :",joinedUserId.get(i));
                    }
//                    CheckBox_invite.setChecked(true);
                }
            }
        });
        userrecyclerView.setAdapter(userAdapter);
        //userList 설정 끝


        // DB에서 유저 데이터 가져오기
        mUsersDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d("USERLIST_LOG : ", dataSnapshot.getValue().toString());
                UserDTO users = dataSnapshot.getValue(UserDTO.class);
                ((UserListAdapter)userAdapter).addUsers(users);
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
