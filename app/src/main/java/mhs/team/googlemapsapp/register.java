package mhs.team.googlemapsapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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

public class register extends Activity {

    ParseObject accounts = new ParseObject("accounts");
    String username;
    String password;
    String email;
    Boolean isUsername = false;
    Boolean isPassword = false;
    Boolean isEmail = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        Button toLogIn = (Button) findViewById(R.id.toSignIn);
        final Button register = (Button) findViewById(R.id.register);
        final EditText setUsername = (EditText) findViewById(R.id.setUsername);
        final EditText setPassword = (EditText) findViewById(R.id.setPassword);
        final EditText setEmail = (EditText) findViewById(R.id.setEmail);

        toLogIn.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(register.this, login.class);
                        startActivity(intent);

                    }
                }
        );

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
                                                    isUsername = true;
                                                } else {
                                                    isUsername = false;
                                                }
                                            } else {
                                                Log.d("score", "Error: " + e.getMessage());
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
                                                    isPassword = true;
                                                } else {
                                                    isPassword = false;
                                                }
                                            } else {
                                                Log.d("score", "Error: " + e.getMessage());
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
                                                    isEmail = true;
                                                } else {
                                                    isEmail = false;
                                                }
                                            } else {
                                                Log.d("score", "Error: " + e.getMessage());
                                            }
                                        }
                                    });
                            Toast.makeText(getApplicationContext(), "Loading...", Toast.LENGTH_SHORT).show();

                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if(isUsername && isPassword && isEmail) {
                                        username = setUsername.getText().toString();
                                        password = setPassword.getText().toString();
                                        email = setEmail.getText().toString();
                                        accounts.add("username", username);
                                        accounts.add("password", password);
                                        accounts.add("email", email);
                                        accounts.saveInBackground();
                                        register.getBackground().setAlpha(50);
                                        register.setEnabled(false);
                                        Toast.makeText(getApplicationContext(), "Your account has been made, enjoy!", Toast.LENGTH_LONG).show();
                                    } else {
                                        if(!isUsername) {
                                            Toast.makeText(getApplicationContext(), "Username already taken", Toast.LENGTH_SHORT).show();
                                        }
                                        if(!isPassword) {
                                            Toast.makeText(getApplicationContext(), "Password already taken", Toast.LENGTH_SHORT).show();
                                        }
                                        if(!isEmail) {
                                            Toast.makeText(getApplicationContext(), "Email has already been used", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            }, 2000);

                        } else {
                            Toast.makeText(getApplicationContext(), "Pleas enter at least 3 characters in each field.", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );

    }

}
