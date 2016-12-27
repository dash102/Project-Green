package mhs.team.googlemapsapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class register extends Activity {

    private static final int RC_SIGN_IN = 1;
    ParseObject accounts = new ParseObject("accounts");
    String username;
    String password;
    String email;
    Boolean isUsername = false;
    Boolean isPassword = false;
    Boolean isEmail = false;
    GoogleApiClient mGoogleApiClient;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        Button toLogIn = (Button) findViewById(R.id.toSignIn);
        final TextView register = (TextView) findViewById(R.id.register);
        final EditText setPassword = (EditText) findViewById(R.id.setPassword); //password must be at least 6 characters long
        final EditText setEmail = (EditText) findViewById(R.id.setEmail);


        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("hmmmmmmmmm", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d("hmmmmmmmmm", "onAuthStateChanged:signed_out");
                }
            }
        };



        toLogIn.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        //Intent intent = new Intent(register.this, login.class);
                        //startActivity(intent);
                        signIn(setEmail.getText().toString(), setPassword.getText().toString());
                    }
                }
        );

        register.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        //createAccount(setEmail.getText().toString(), setPassword.getText().toString());
                        Intent intent = new Intent(register.this, sign_up.class);
                        startActivity(intent);
                    }
                }
        );

    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    public void createAccount(String email, String password) {

        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("hmmmmmmmmm", "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(register.this, R.string.auth_failed,
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }

    private void signIn(String email, String password) {

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("hmmmmmmmmm", "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w("hmmmmmmmmm", "signInWithEmail:failed", task.getException());
                            Toast.makeText(register.this, R.string.auth_failed,
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "You are now logged in.", Toast.LENGTH_LONG).show();
                        }

                    }
                });
    }

}
