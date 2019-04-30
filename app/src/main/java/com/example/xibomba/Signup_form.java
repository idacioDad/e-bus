package com.example.xibomba;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

public class Signup_form extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText edt_signpass, edt_confirm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_signup_form );
        getSupportActionBar().setTitle( "Registar" );


    }
}
