package com.android.secretarykim;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.google.android.gms.common.SignInButton;


public class LoginActivity extends AppCompatActivity {
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener((view) -> {
            Intent intent = new Intent(getApplicationContext(),OfflineStartActivity.class);
            startActivity(intent);

        });
        findViewById(R.id.sign_in_button).setOnClickListener((View.OnClickListener) this);

        SignInButton signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
    }
}
