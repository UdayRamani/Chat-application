package com.example.familychat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetpasswordActivity extends AppCompatActivity {

    EditText send_Email;
    Button btn_reset;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpassword);


        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Reset Password");
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        send_Email=findViewById(R.id.send_email);
        btn_reset=findViewById(R.id.btn_reset);

        firebaseAuth=firebaseAuth.getInstance();

        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email=send_Email.getText().toString();

                if(email.equals(""))
                {
                    Toast.makeText(ResetpasswordActivity.this,"All fileds are required",Toast.LENGTH_SHORT);
                }
                else
                {
                    firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(ResetpasswordActivity.this, "please check your Email", Toast.LENGTH_SHORT);
                                startActivity(new Intent(ResetpasswordActivity.this, LoginActivity.class));
                            }
                            else
                            {
                                String error=task.getException().getMessage();
                                Toast.makeText(ResetpasswordActivity.this,error,Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });















    }
}
