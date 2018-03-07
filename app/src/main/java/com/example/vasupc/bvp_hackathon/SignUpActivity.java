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


public class SignUpActivity extends AppCompatActivity  {

    private static final int REQUEST_READ_CONTACTS = 0;

    private EditText mname, memail, mPassword, m_member1_name, m_member1_no, m_member2_name, m_member2_no;
    private ProgressBar mProgressView;
    private  Button signUP_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mname = (EditText) findViewById(R.id.signup_name);
        memail = (EditText) findViewById(R.id.signup_email);
        mPassword = (EditText) findViewById(R.id.signup_password);
        m_member1_name = (EditText) findViewById(R.id.signup_member1_name);
        m_member1_no = (EditText) findViewById(R.id.signup_member1_no);
        m_member2_name = (EditText) findViewById(R.id.signup_member2_name);
        m_member2_no = (EditText) findViewById(R.id.signup_member2_no);
        mProgressView = (ProgressBar) findViewById(R.id.signup_progress);
        signUP_button = (Button) findViewById(R.id.signup_button);
        signUP_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

    }



}

