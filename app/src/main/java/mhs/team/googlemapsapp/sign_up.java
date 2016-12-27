package mhs.team.googlemapsapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class sign_up extends Activity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        final EditText newName = (EditText) findViewById(R.id.newName);
        final EditText newEmail = (EditText) findViewById(R.id.newEmail);
        final EditText newPassword = (EditText) findViewById(R.id.newPassword);

        Button registerButton = (Button) findViewById(R.id.registerButton);

        TextView unknownError = (TextView) findViewById(R.id.unknownError);
        final TextView emailError = (TextView) findViewById(R.id.emailError);
        final TextView passwordError = (TextView) findViewById(R.id.passwordError);

        unknownError.setText("");
        emailError.setText("");
        passwordError.setText("");

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                emailError.setText("");
                passwordError.setText("");

                if(newEmail.getText().toString().indexOf("@") != -1 && newEmail.getText().toString().length() >= 6 && newPassword.getText().toString().length() >= 6) {
                    createAccount(newEmail.getText().toString(), newPassword.getText().toString(), newName.getText().toString());
                } else {
                    if(newPassword.getText().toString().length() < 6) {
                        passwordError.setText("Password must be more five characters");
                    }
                    if(newEmail.getText().toString().length() < 6) {
                        emailError.setText("Email adress must be longer than five characters");
                    }
                    if(newEmail.getText().toString().indexOf("@") == -1) {
                        emailError.setText("Must use a valid email address");
                    }
                }
            }
        });
    }

    /*private void handleTextError(String eText, String pText) {

    }*/
    private void createAccount(String email, String password, final String name) {

        mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("hmmmmmmmmm", "createUserWithEmail:onComplete:" + task.isSuccessful());
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if(task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Account created! You are now logged in.", Toast.LENGTH_LONG).show();
                        }
                        if (!task.isSuccessful()) {
                            //Toast.makeText(sign_up.this, R.string.auth_failed, Toast.LENGTH_SHORT).show();
                            Toast.makeText(getApplicationContext(), "Unknown error occurred. Try using a different email address.", Toast.LENGTH_LONG).show();
                        } else if (name.length() >= 1){
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(name)
                                    .build();

                            user.updateProfile(profileUpdates)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.d("hmmmmmmmmm", "User profile updated.");
                                            }
                                        }
                                    });
                        }

                    }
                });

    }
}
