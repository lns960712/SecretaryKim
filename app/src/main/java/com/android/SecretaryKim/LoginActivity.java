package com.android.SecretaryKim;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.data.model.User;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/*
    Splash 를 제외하고 가장 처음 화면이다
    앱 제목이 보이고 구글 계정으로 로그인 할 수 있다

 */

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    private FirebaseAuth auth;
    //private FirebaseUser user;
    private GoogleApiClient googleApiClient;
    private static final int RC_SIGN_IN = 9001;
    private SignInButton btn_google;
    private Button loginButton;
    private UserDTO user;
    private DatabaseReference mDatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,googleSignInOptions)
                .build();

        auth = FirebaseAuth.getInstance(); // 인증 객체 초기화
        loginButton = findViewById(R.id.loginButton);
        btn_google = findViewById(R.id.sign_in_button);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // 구글 로그인 버튼 클릭했을때
        loginButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), BranchActivity.class);
            startActivity(intent);
        });
        btn_google.setOnClickListener(view -> {
            Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
//            Intent intent = new Intent(getApplicationContext(),BranchActivity.class);
            Log.v("user", "클릭함");
//            startActivity(intent);
            startActivityForResult(intent, RC_SIGN_IN);
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) { // 구글 로그인 인증을 요청했을때 결과 값을 되돌려 받는 곳
        super.onActivityResult(requestCode, resultCode, data);
        Log.v("user", "onActivity");

        if(requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            if(result.isSuccess()) { // 인증 결과가 성공적이면
                GoogleSignInAccount account = result.getSignInAccount(); // account라는 데이터는 구글 로그인 정보를 담고있음
                Log.v("user", "인증성공");
                resultLogin(account); // 로그인 결과 값 출력 수행하라는 메소드
            }


        }
    }

    private void restoreData (FirebaseUser userData) { // Firebase에 데이터 유저 데이터 저장
        String email = userData.getEmail();
        String name = userData.getDisplayName();
        String uid = userData.getUid();
        System.out.println("account data is :");
        System.out.println(email);
        System.out.println(name);
        System.out.println(uid);
        user = new UserDTO();
        user.setUid(uid);
        user.setEmail(email);
        user.setNickname(name);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("users").child(uid).setValue(user);

    }

    private void resultLogin(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) { // 로그인에 실제 성공을 했는지
                        if (task.isSuccessful()) { // 로그인이 성공 했으면
                            Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), MainListActivity.class);
                            user = new UserDTO();
                            user.setEmail(account.getEmail());//intent값 넘겨받기
                            user.setNickname(account.getDisplayName());//intent값 넘겨받기
                            intent.putExtra("user", user); // 유저정보 넘겨주기
                            intent.putExtra("photoUrl", String.valueOf(account.getPhotoUrl())); // String.valueOf 특정 자료형을 String 형태로 변형
                            FirebaseUser userData = task.getResult().getUser();
                            restoreData(userData);
                            startActivity(intent);
                        }
                        else { // 로그인이 실패 했으면
                            Toast.makeText(LoginActivity.this, "Login Fail", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
