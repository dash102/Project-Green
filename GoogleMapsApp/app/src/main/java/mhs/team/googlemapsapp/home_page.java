package mhs.team.googlemapsapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.parse.Parse;


public class home_page extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getActionBar().setTitle("Recycle Home");

        // [Optional] Power your app with Local Datastore. For more info, go to
        // https://parse.com/docs/android/guide#local-datastore
        Parse.enableLocalDatastore(this);

        Parse.initialize(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        final ImageButton pinIt = (ImageButton) findViewById(R.id.pinIt);
        final ImageButton recycleBin = (ImageButton) findViewById(R.id.recycleBin);

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
    }

}
