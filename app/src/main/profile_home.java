package mhs.team.googlemapsapp;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class profile_home extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_home);

        final TextView userEmail = (TextView) findViewById(R.id.userEmail);
        TextView userOther = (TextView) findViewById(R.id.userOther);
        Button signOut = (Button) findViewById(R.id.signOut);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user != null) {
            userEmail.setText(user.getEmail());
            if (user.getDisplayName().length() >= 2) {
                userOther.setText("Welcome back " + user.getDisplayName());
            }
        }

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(getApplicationContext(), "You are now logged out.", Toast.LENGTH_LONG).show();
            }
        });
    }
}
