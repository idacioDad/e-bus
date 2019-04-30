package com.example.xibomba;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import android.widget.Toast;
import  android.support.v4.app.FragmentActivity;


public class Login_form extends AppCompatActivity  {
    private GoogleSignInOptions gso;
    private GoogleSignInAccount account;
    private  FirebaseUser  user,currentUser;
    private FirebaseAuth mAuth;
    private String email, password;
    private EditText edt_logpass, edt_loguser;
    private Toast toast;
    private Button btn_login;
    private GoogleSignInClient mGoogleSignInClient;
    private static int RC_SIGN_IN=9854;
    private SignInButton signInButton;




   /* @Override
    protected void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        currentUser = mAuth.getCurrentUser();
        //updateUI(user);
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login_form );
        FirebaseApp.initializeApp( this );
        mAuth = FirebaseAuth.getInstance();

        btn_login=(Button)findViewById( R.id.btn_login );
        edt_loguser=(EditText) findViewById( R.id.edt_loguser);
        edt_logpass=(EditText) findViewById( R.id.edt_logpass );
        signInButton=(SignInButton)findViewById( R.id.sign_in_button );
        email =edt_loguser.getText().toString();
        password= edt_logpass.getText().toString();


        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient= GoogleSignIn.getClient( this,gso );

        signInButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        } );

        btn_login.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent( Login_form.this,MapaActivity.class );
                startActivity( i );
            }
        } );

    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {

            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent i=new Intent( Login_form.this,ProfileActivity.class );
                            startActivity( i );
                            finish();
                            Toast.makeText( getApplicationContext(),"Login feito",Toast.LENGTH_SHORT).show();

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText( getApplicationContext(),"Login negado.Credenciais nao encontradas",Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });

    }




  public void signIn() {

        mAuth.signInWithEmailAndPassword( email,password ).addOnCompleteListener(
                this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                             Log.d( "Login", "signInWithEmail:success" );
                            user = mAuth.getCurrentUser();
                            Intent i=new Intent( Login_form.this,ProfileActivity.class );
                            startActivity( i );
                            finish();
                            Toast.makeText( getApplicationContext(),"Login feito",Toast.LENGTH_SHORT).show();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("Login", "signInWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();

                        }
                    }
                }
        );
    }

    public void passar(View view) {
        Intent pass=new Intent( Login_form.this,MapaActivity.class );
        startActivity( pass );
    }

   /* public void createAccount(View view) {
        mAuth.createUserWithEmailAndPassword( email,password ).addOnCompleteListener(
                this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("Login", "createUserWithEmail:success");
                             user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("Login", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), getString( R.string.s1),Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                }
        );
    }*/

 /*   protected void updateUI(FirebaseUser user){

       user = mAuth.getCurrentUser();
        startActivity( new Intent( Login_form.this, ProfileActivity.class ) );
        finish();
        Toast.makeText( getApplicationContext(),"Login feito",Toast.LENGTH_SHORT).show();


    }*/
}



