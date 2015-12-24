package mhs.team.googlemapsapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class IconPage extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_icon_page);

        Button plasticButton = (Button) findViewById(R.id.plasticButton);
        Button paperButton = (Button) findViewById(R.id.paperButton);
        Button metalButton = (Button) findViewById(R.id.metalButton);

        plasticButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IconPage.this, plastic_maps.class);
                startActivity(intent);
            }
        });

        paperButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IconPage.this, paper_maps.class);
                startActivity(intent);
            }
        });

        metalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IconPage.this, metal_maps.class);
                startActivity(intent);
            }
        });

    }
}