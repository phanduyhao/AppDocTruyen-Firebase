package com.example.doctruyenfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainDangKy extends AppCompatActivity {

    private LinearLayout layoutSignIn;
    private EditText edtEmail, edtPass;
    private Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dang_ky);

        initUi();
        initListener();
        layoutSignin();
        viewDangNhap();
    }
    
    private void initUi(){
        edtEmail = findViewById(R.id.dktaikhoan);
        edtPass = findViewById(R.id.dkmatkhau);
        btnSignUp = findViewById(R.id.dangky);

    }
    private void initListener() {
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSignUp();
            }
        });
    }

    private void onClickSignUp() {
        String strEmail = edtEmail.getText().toString().trim();
        String strPass = edtPass.getText().toString().trim();

        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(strEmail, strPass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                           Intent intent = new Intent(MainDangKy.this, MainActivity.class);
                           startActivity(intent);
                           finishAffinity();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(MainDangKy.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void viewDangNhap() {
        layoutSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainDangKy.this, MainDangNhap.class);
                startActivity(intent);
            }
        });
    }

    private void layoutSignin(){
        layoutSignIn = findViewById(R.id.layout_sign_in);
    }
}