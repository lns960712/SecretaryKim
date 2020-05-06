package com.android.secretarykim;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.SecretaryKim.OfflineStartActivity;

public class BranchActivity extends AppCompatActivity {

    private Button Button_online;
    private Button Button_offline;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch);
        Button_online = findViewById(R.id.Button_online);
        Button_offline = findViewById(R.id.Button_offline);
        Button_online.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(),ChatActivity.class);
            startActivity(intent);
        });
        Button_offline.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), OfflineStartActivity.class);
            startActivity(intent);
        });
    }
}
