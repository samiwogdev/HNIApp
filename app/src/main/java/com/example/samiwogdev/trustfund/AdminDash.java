package com.example.samiwogdev.trustfund;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminDash extends AppCompatActivity {
    TextInputLayout email, password, account, phone;
    Button signup;
    DatabaseReference reference;
    TrustUsers users;

    String[] items = {"Select User Role","Admin", "Customer"};
    AutoCompleteTextView autoCompleteTxt;
    ArrayAdapter<String> adapterItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dash);

        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        password = findViewById(R.id.password);
        account = findViewById(R.id.account);
        signup = findViewById(R.id.signup);
        users = new TrustUsers();

        autoCompleteTxt = findViewById(R.id.auto_complete_txt);
        adapterItems = new ArrayAdapter<String>(this, R.layout.list_item, items);
        autoCompleteTxt.setAdapter(adapterItems);
        autoCompleteTxt.setOnItemClickListener( new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
            }
        });

        reference = FirebaseDatabase.getInstance().getReference().child("trust_mobile_users");
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if (saveNewUser()){
                email.getEditText().getText().clear();
                phone.getEditText().getText().clear();
                password.getEditText().getText().clear();
                account.getEditText().getText().clear();
                Toast.makeText(AdminDash.this, "Successful", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(AdminDash.this, "Something went wrong, try again!", Toast.LENGTH_LONG).show();
            }
            }
        });
    }

    private boolean saveNewUser(){
        String _email = email.getEditText().getText().toString().trim();
        String _phone = phone.getEditText().getText().toString().trim();
        String _password = password.getEditText().getText().toString().trim();
        String _account = account.getEditText().getText().toString().trim();
        String _role = autoCompleteTxt.getText().toString().trim();
        if(_email.isEmpty()){
            email.setError("Email is required");
            email.requestFocus();
            return false;
        }
        if(_phone.isEmpty()){
            phone.setError("Phone is required");
            phone.requestFocus();
            return false;
        }if(_password.isEmpty()){
            password.setError("Password is required");
            password.requestFocus();
            return false;
        }if(_account.isEmpty()){
            account.setError("Account is required");
            account.requestFocus();
            return false;
        }
//        if(_role.isEmpty()){
//            role.setError("Role is required");
//            role.requestFocus();
//            return false;
//        }
//        if(_phone.charAt(0) == 0){
//            _phone = _phone.substring(1);
//        }
        users.setEmail(_email);
        users.setPhone(_phone);
        users.setPassword(_password);
        users.setAccount(_account);
        users.setRole(_role);
        reference.child(_account).setValue(users);
        return true;
    }
}