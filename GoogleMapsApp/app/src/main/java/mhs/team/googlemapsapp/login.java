package mhs.team.googlemapsapp;

import android.app.Activity;
import android.content.Intent;
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
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class login extends Activity {

    public String theUsername;
    public String thePassword;
    public String theEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        final EditText putUsername = (EditText) findViewById(R.id.putUsername);
        final EditText putPassword = (EditText) findViewById(R.id.putPassword);
        Button signIn = (Button) findViewById(R.id.signIn);

        final Intent intent = new Intent(login.this, home_page.class);
        signIn.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        if(putUsername.getText().toString().length() >= 3 && putPassword.getText().toString().length() >= 3) {
                            ParseQuery
                                    .getQuery("accounts")
                                    .whereEqualTo("username", putUsername.getText().toString())
                                    .getFirstInBackground(new GetCallback<ParseObject>() {
                                        @Override
                                        public void done(ParseObject object, ParseException e) {
                                            if (e == null) {
                                                String[] arr = new String[1];
                                                arr[0] = String.valueOf(object.get("password"));
                                                //Toast.makeText(getApplicationContext(),arr[0], Toast.LENGTH_SHORT).show();
                                                //Toast.makeText(getApplicationContext(),  putPassword.getText().toString(), Toast.LENGTH_SHORT).show();
                                                if(arr[0].equals("[" + putPassword.getText().toString() + "]") ) {
                                                    theUsername = putUsername.getText().toString();
                                                    thePassword = putPassword.getText().toString();
                                                    theEmail = arr[0];
                                                    Toast.makeText(getApplicationContext(), "logged in", Toast.LENGTH_SHORT).show();
                                                    backToHome();
                                                    //startActivity(intent);
                                                } else {
                                                    Toast.makeText(getApplicationContext(), "here0", Toast.LENGTH_SHORT).show();
                                                }
                                                } else {
                                                Toast.makeText(getApplicationContext(), "here", Toast.LENGTH_SHORT).show();
                                                Log.d("score", "Error: " + e.getMessage());
                                            }
                                        }
                                    });
                        }
                    }
                }
        );
    }

    public void backToHome() {
        Intent intent1 = new Intent(login.this, register.class);
        startActivity(intent1);
    }

}
