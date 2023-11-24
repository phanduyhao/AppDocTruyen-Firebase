package com.example.doctruyenfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainDangNhap extends AppCompatActivity {
    private LinearLayout layoutSignUp;
    private EditText edtEmail, edtPass;
    private Button btnSignIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dang_nhap);

        initUi();
        initListener();
    }

    private void initListener() {
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSignIn();
            }
        });
        layoutSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainDangNhap.this, MainDangKy.class);
                startActivity(intent);
            }
        });
    }

    private void onClickSignIn() {
        String strEmail = edtEmail.getText().toString().trim();
        String strPass = edtPass.getText().toString().trim();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword(strEmail, strPass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = auth.getCurrentUser();
                            Intent intent = new Intent(MainDangNhap.this, MainActivity.class);
                            startActivity(intent);
                            finishAffinity();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(MainDangNhap.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void initUi(){
        layoutSignUp = findViewById(R.id.layout_sign_up);
        edtEmail = findViewById(R.id.taikhoan);
        edtPass = findViewById(R.id.matkhau);
        btnSignIn = findViewById(R.id.dangnhap);
    }
}