package mhs.team.googlemapsapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.parse.Parse;
import com.parse.ParseObject;

public class Recycle extends Activity {
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }
        return true;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        getActionBar().setTitle("Choose a Material");
        getActionBar().setDisplayHomeAsUpEnabled(true);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_icon_page);


        Button plasticButton = (Button) findViewById(R.id.plasticButton);
        Button paperButton = (Button) findViewById(R.id.paperButton);
        Button metalButton = (Button) findViewById(R.id.metalButton);

        plasticButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Recycle.this, plastic_maps.class);
                startActivity(intent);
            }
        });

        paperButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Recycle.this, paper_maps.class);
                startActivity(intent);
            }
        });

        metalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Recycle.this, metal_maps.class);
                startActivity(intent);
            }
        });

    }
}