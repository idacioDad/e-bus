package com.example.xibomba;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity{

    private FirebaseAuth auth;
    private FirebaseUser user;
    private TextView profileTxt;
    private Button btn_sign_out;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_profile );

        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        profileTxt=(TextView)findViewById( R.id.txt);
        btn_sign_out=(Button)findViewById( R.id.btn_sign_out );

        profileTxt.setText( user.getEmail() );

        btn_sign_out.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               auth.signOut();
            }
        } );
    }

    public void signOut(View v) {
        auth.signOut();
        finish();
        Intent i = new Intent( ProfileActivity.this, MainActivity.class );
        startActivity( i );
    }
}
