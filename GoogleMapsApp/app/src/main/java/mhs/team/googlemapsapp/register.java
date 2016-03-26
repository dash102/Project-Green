package mhs.team.googlemapsapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class register extends AppCompatActivity {

    ParseObject accounts = new ParseObject("accounts");
    String username;
    String password;
    String email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        Button toLogIn = (Button) findViewById(R.id.toSignIn);
        Button register = (Button) findViewById(R.id.register);
        final EditText setUsername = (EditText) findViewById(R.id.setUsername);
        final EditText setPassword = (EditText) findViewById(R.id.setPassword);
        final EditText setEmail = (EditText) findViewById(R.id.setEmail);

        register.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        if(setUsername.getText().toString().length() >= 3 && setPassword.getText().toString().length() >= 3 && setEmail.getText().toString().length() >= 3) {
                            ParseQuery
                                    .getQuery("accounts")
                                    .whereEqualTo("username", setUsername.getText().toString())
                                    .findInBackground(new FindCallback<ParseObject>() {
                                        public void done(List<ParseObject> spots, ParseException e) {
                                            if (e == null) {
                                                if(spots.size() == 0) {
                                                    Toast.makeText(getApplicationContext(), "U_RIGHT", Toast.LENGTH_SHORT).show();
                                                    username = setUsername.getText().toString();
                                                    accounts.add("username", username);
                                                    accounts.saveInBackground();
                                                } else {
                                                    Toast.makeText(getApplicationContext(), "U_WRONG", Toast.LENGTH_SHORT).show();
                                                }
                                            } else {
                                                Log.d("score", "Error: " + e.getMessage());
                                                Toast.makeText(getApplicationContext(), "drumph donald ", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                            ParseQuery
                                    .getQuery("accounts")
                                    .whereEqualTo("password", setPassword.getText().toString())
                                    .findInBackground(new FindCallback<ParseObject>() {
                                        public void done(List<ParseObject> spots, ParseException e) {
                                            if (e == null) {
                                                if (spots.size() == 0) {
                                                    Toast.makeText(getApplicationContext(), "P_RIGHT", Toast.LENGTH_SHORT).show();
                                                    accounts.add("password", setPassword.getText().toString());
                                                    password = setPassword.getText().toString();
                                                    accounts.saveInBackground();
                                                } else {
                                                    Toast.makeText(getApplicationContext(), "P_WRONG", Toast.LENGTH_SHORT).show();
                                                }
                                            } else {
                                                Log.d("score", "Error: " + e.getMessage());
                                                Toast.makeText(getApplicationContext(), "drumph donald ", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                            ParseQuery
                                    .getQuery("accounts")
                                    .whereEqualTo("email", setEmail.getText().toString())
                                    .findInBackground(new FindCallback<ParseObject>() {
                                        public void done(List<ParseObject> spots, ParseException e) {
                                            if (e == null) {
                                                if(spots.size() == 0) {
                                                    Toast.makeText(getApplicationContext(), "E_RIGHT", Toast.LENGTH_SHORT).show();
                                                    accounts.add("email", setEmail.getText().toString());
                                                    email = setEmail.getText().toString();
                                                    accounts.saveInBackground();
                                                } else {
                                                    Toast.makeText(getApplicationContext(), "E_WRONG", Toast.LENGTH_SHORT).show();
                                                }
                                            } else {
                                                Log.d("score", "Error: " + e.getMessage());
                                                Toast.makeText(getApplicationContext(), "drumph donald ", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        } else {
                            Toast.makeText(getApplicationContext(), "Pleas enter at least 3 characters in each field.", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );

    }

}
