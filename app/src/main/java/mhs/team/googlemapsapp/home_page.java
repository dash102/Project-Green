package mhs.team.googlemapsapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class home_page extends Activity {

    public boolean isUser = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //getActionBar().setTitle("Recycle Home");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        final ImageButton pinIt = (ImageButton) findViewById(R.id.pinIt);
        final ImageButton recycleBin = (ImageButton) findViewById(R.id.recycleBin);
        final ImageButton settingsBot = (ImageButton) findViewById(R.id.settings);
        TextView newEmail = (TextView) findViewById(R.id.textView3);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user != null) {
            newEmail.setText(user.getDisplayName());
            isUser = true;
        }


        pinIt.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                            Intent intent = new Intent(home_page.this, Recycle.class);
                            startActivity(intent);
                    }
                }
        );

        recycleBin.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(home_page.this, recycle_info.class);
                        startActivity(intent);

                    }
                }
        );

        settingsBot.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        if(isUser == true) {
                            Intent intent = new Intent(home_page.this, profile_home.class);
                            startActivity(intent);
                        } else if(isUser == false) {
                            Intent intent = new Intent(home_page.this, register.class);
                            startActivity(intent);
                        }

                    }
                }
        );
    }

}
