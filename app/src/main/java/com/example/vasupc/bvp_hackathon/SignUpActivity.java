package com.example.vasupc.bvp_hackathon;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.vasupc.bvp_hackathon.Model.Family;
import com.example.vasupc.bvp_hackathon.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;


public class SignUpActivity extends AppCompatActivity  {

    private static final int REQUEST_READ_CONTACTS = 0;

    private EditText mname, memail, mPassword, m_member1_name, m_member1_no, m_member2_name, m_member2_no;
    private ProgressBar mProgressView;
    private  Button signUP_button;

    String random = "ACEFGHJKLMNPQRUVWXYabcdefhijkprstuvwx" ;

    String key = "" ;

    DatabaseReference mDatabase;

    private RadioGroup radioSexGroup;
    private RadioButton radioSexButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mname = (EditText) findViewById(R.id.signup_name);
        memail = (EditText) findViewById(R.id.signup_email);
        mPassword = (EditText) findViewById(R.id.signup_password);
        m_member1_name = (EditText) findViewById(R.id.signup_member1_name);
        m_member1_no = (EditText) findViewById(R.id.signup_member1_no);
        m_member2_name = (EditText) findViewById(R.id.signup_member2_name);
        m_member2_no = (EditText) findViewById(R.id.signup_member2_no);
        mProgressView = (ProgressBar) findViewById(R.id.signup_progress);
        signUP_button = (Button) findViewById(R.id.signup_button);

        radioSexGroup=(RadioGroup)findViewById(R.id.radioGroup);

        for(int i = 0 ; i < 6 ; i++){
            key += random.charAt(new Random().nextInt(random.length()));
        }

        signUP_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                int selectedId=radioSexGroup.getCheckedRadioButtonId();
                radioSexButton=(RadioButton)findViewById(selectedId);

                Family family = new Family(m_member1_name.getText().toString(),m_member1_no.getText().toString(),m_member2_name.getText().toString(),m_member2_no.getText().toString());
                User user = new User(mname.getText().toString(),radioSexButton.getText().toString(),memail.getText().toString(),mPassword.getText().toString(),family);

                mDatabase.child("Users").child(key).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(SignUpActivity.this, "User Added", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });

            }
        });

    }



}

