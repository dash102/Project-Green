package mhs.team.googlemapsapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

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
        //getActionBar().setTitle("Choose a Material");
        //getActionBar().setDisplayHomeAsUpEnabled(true);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_icon_page);


        ImageButton plasticButton = (ImageButton) findViewById(R.id.plasticButton);
        ImageButton paperButton = (ImageButton) findViewById(R.id.paperButton);
        ImageButton metalButton = (ImageButton) findViewById(R.id.metalButton);
        ImageButton glassButton = (ImageButton) findViewById(R.id.glassButton);
        ImageButton woodButton = (ImageButton) findViewById(R.id.woodButton);
        ImageButton cardboardButton = (ImageButton) findViewById(R.id.cardboardButton);

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

        cardboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Recycle.this, cardboard_maps.class);
                startActivity(intent);
            }
        });

        glassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Recycle.this, glass_maps.class);
                startActivity(intent);
            }
        });

        woodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Recycle.this, wood_maps.class);
                startActivity(intent);
            }
        });

    }
}