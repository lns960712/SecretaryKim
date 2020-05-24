package com.android.SecretaryKim;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MainListActivity extends AppCompatActivity {
    private Intent intent;
    private Button makeButton;
    private ImageView imageView;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);

        makeButton = findViewById(R.id.makeConference);
        imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.usericon);

        intent = getIntent();
        makeButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), OfflineStartActivity.class);
            intent.putExtra("nickname", this.intent.getExtras().getString("nickname")); // 닉네임 넘겨주기
            intent.putExtra("email", this.intent.getExtras().getString("email")); // 이메일 넘겨주기
            startActivity(intent);
        });

    }

}
