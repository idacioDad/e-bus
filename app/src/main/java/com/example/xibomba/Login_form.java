package com.example.xibomba;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import android.widget.Toast;
import  android.support.v4.app.FragmentActivity;


public class Login_form extends AppCompatActivity  {
    private GoogleSignInOptions gso;
    private GoogleSignInAccount account;
    private  FirebaseUser  user,currentUser;
    private FirebaseAuth mAuth;
    private String email, password;
    private EditText  edt_logpass, edt_loguser;
    private Toast toast;




    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        currentUser = mAuth.getCurrentUser();
        updateUI(user);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login_form );
        mAuth = FirebaseAuth.getInstance();

        edt_loguser=(EditText) findViewById( R.id.edt_loguser);
        edt_logpass=(EditText) findViewById( R.id.edt_loguser );
        email =edt_loguser.getText().toString();
        password= edt_logpass.getText().toString();

    }



    public void signIn(View view) {
        mAuth.signInWithEmailAndPassword( email,password ).addOnCompleteListener(
                this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithEmail:success");
                             user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                }
        );
    }

    public void createAccount(View view) {
        mAuth.createUserWithEmailAndPassword( email,password ).addOnCompleteListener(
                this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "createUserWithEmail:success");
                             user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), getString( R.string.s1),Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                }
        );
    }

    protected void updateUI(FirebaseUser user){
        startActivity( new Intent( this, MainActivity.class ) );

    }
}



