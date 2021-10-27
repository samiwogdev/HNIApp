//LOGIN




package com.example.samiwogdev.trustfund;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    Button login;
    TextInputLayout email;
    TextInputLayout password;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    //cloud firebase
    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        firebaseAuth = FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                firebaseAuth.signInWithEmailAndPassword(email.getEditText().getText().toString(), password.getEditText().getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>(){

                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    firebaseUser = firebaseAuth.getCurrentUser();
                                    String email = firebaseUser.getEmail();
                                    //find login user by email
                                    Query checkUser = FirebaseDatabase.getInstance().getReference("trust_mobile_users")
                                    .orderByChild("email").equalTo(email);
                                    checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            if (snapshot.exists()){
                                                //fetch login user phone
                                                String phone = snapshot.child(email).child("phone").getValue(String.class);
                                                //display user phone number
                                                Toast.makeText(Login.this, phone,
                                                        Toast.LENGTH_LONG).show();
                                            }else{
                                                Toast.makeText(Login.this, "User does not Exist",
                                                        Toast.LENGTH_LONG).show();
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });





//                                    Toast.makeText(Login.this, email,
//                                            Toast.LENGTH_LONG).show();
//                                    Intent intent =  new Intent(Login.this, Otp.class);
//                                    startActivity(intent);
                                }else{
                                    Toast.makeText(Login.this, task.getException().getLocalizedMessage(),
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });
    }
}