package com.android.secretarykim;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.google.android.gms.common.SignInButton;


public class LoginActivity extends AppCompatActivity {
    private Button loginButton;
    private SignInButton sign_in_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(v -> {
//            Intent intent = new Intent(getApplicationContext(),OfflineStartActivity.class);
//            startActivity(intent);
            Intent intent = new Intent(getApplicationContext(),BranchActivity.class);
            startActivity(intent);
        });
//        sign_in_button = findViewById(R.id.sign_in_button);
//        sign_in_button.setOnClickListener((v -> {
//
//        });

        SignInButton signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
    }
}
