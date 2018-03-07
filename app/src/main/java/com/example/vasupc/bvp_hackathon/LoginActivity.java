package com.example.vasupc.bvp_hackathon;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vasupc.bvp_hackathon.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    EditText etEmail,etPassword;
    Button btnLogin;

    FirebaseDatabase database;
    DatabaseReference ref;

    SharedPreferences sharedPreferencesMyInfo;  
    SharedPreferences.Editor editorMyInfo ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        database = FirebaseDatabase.getInstance();
        ref = database.getReference("Users");

        sharedPreferencesMyInfo = this.getSharedPreferences("MyInfo" , MODE_PRIVATE);
        editorMyInfo = sharedPreferencesMyInfo.edit();

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check();
            }
        });

    }

    public void check(){
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                boolean found = false ;

                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String email = (String) ds.child("email").getValue();
                    String password = (String) ds.child("password").getValue();

                    if (email.equalsIgnoreCase(etEmail.getText().toString()) && password.equals(etPassword.getText().toString())){
                        Toast.makeText(LoginActivity.this, "Verified", Toast.LENGTH_SHORT).show();
                        found = true ;
                        getUserDetails(ds.getKey());
                        break;
                    }
                }

                if(!found){
                    Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
                Toast.makeText(LoginActivity.this, "The read failed: " + databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getUserDetails(String userid){
        FirebaseDatabase getDatabase = FirebaseDatabase.getInstance();
        DatabaseReference getRef = getDatabase.getReference("Users").child(userid);

        getRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String email = (String) ds.child("name").getValue();
                    String name = (String) ds.child("email").getValue();
                    String member1name = (String) ds.child("Family").child("member1name").getValue();
                    String member1number = (String) ds.child("Family").child("member1number").getValue();
                    String member2name = (String) ds.child("Family").child("member2name").getValue();
                    String member2number = (String) ds.child("Family").child("member2number").getValue();

                    editorMyInfo.putString("Email" , email);
                    editorMyInfo.putString("Name" , name);
                    editorMyInfo.putString("Member1Name" , member1name);
                    editorMyInfo.putString("Member1Number" , member1number);
                    editorMyInfo.putString("Member2Name" , member2name);
                    editorMyInfo.putString("Member2Number" , member2number);
                    editorMyInfo.commit();

                    Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(LoginActivity.this , HomeActivity.class));
                    finish();

                    break;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
