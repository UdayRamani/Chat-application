package com.example.familychat;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;
public class RegisterActivity extends AppCompatActivity {

    EditText username,email,password;
    Button btn_register;
    private FirebaseAuth auth;
    private ProgressDialog progressDialog;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Register");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        btn_register = findViewById(R.id.btn_register);
        auth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        //attaching listener to button
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }
        private void registerUser(){
            //getting email and password from edit texts

            final String username1 = username.getText().toString().trim();
            String email1 = email.getText().toString().trim();
            String password1  = password.getText().toString().trim();
            //checking if email and passwords are empty
            if(TextUtils.isEmpty(username1)||TextUtils.isEmpty(email1)||TextUtils.isEmpty(password1))
            {
                Toast.makeText(RegisterActivity.this,"All field are requierd",Toast.LENGTH_SHORT).show();

            }
            else if(password1.length()<6)
            {
                Toast.makeText(RegisterActivity.this,"password must be 6 characters",Toast.LENGTH_SHORT).show();

            }
            else {
                //if the email and password are not empty
                //displaying a progress dialog
                //creating a new user

                progressDialog.setMessage("Registering Please Wait...");
                progressDialog.show();
                auth.createUserWithEmailAndPassword(email1, password1)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                //checking if success
                                if (task.isSuccessful()) {
                                    FirebaseUser firebaseUser=auth.getCurrentUser();
                                    assert firebaseUser != null;
                                    String userid=firebaseUser.getUid();

                                    reference= FirebaseDatabase.getInstance().getReference("Users").child(userid);
                                    HashMap<String,String> hashMap=new HashMap<>();
                                    hashMap.put("id",userid);
                                    hashMap.put("username",username1);
                                    hashMap.put("imageURL","default");
                                    hashMap.put("status","offline");
                                    hashMap.put("search",username1.toLowerCase());


                                    //go to another activity if successfully and message successfully
                                    reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful())
                                            {
                                                Intent intent=new Intent(RegisterActivity.this,MainActivity.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                startActivity(intent);
                                                Toast.makeText(RegisterActivity.this, "Successfully registered", Toast.LENGTH_LONG).show();
                                                finish();
                                            }
                                        }
                                    });
                                    }
                                    else
                                    {
                                    //display some message here
                                    Toast.makeText(RegisterActivity.this, "Registration Error", Toast.LENGTH_LONG).show();
                                    }
                                progressDialog.dismiss();
                            }
                        });
            }
        }
    }
