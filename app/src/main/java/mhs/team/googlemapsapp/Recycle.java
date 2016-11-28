package mhs.team.googlemapsapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

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
                if (isLocationServiceEnabled()) {
                    Intent intent = new Intent(Recycle.this, paper_maps.class);
                    startActivity(intent);
                    //Toast.makeText(getApplicationContext(), "Location enabled", Toast.LENGTH_LONG).show();
                } else {
                    showSimplePopUp();
                }
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

    public boolean isLocationServiceEnabled() {
        LocationManager locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);;


        boolean GpsStatus = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return GpsStatus;
    }

    private void showSimplePopUp() {

        AlertDialog.Builder helpBuilder = new AlertDialog.Builder(this);
        helpBuilder.setTitle("Location Services Disabled");
        helpBuilder.setMessage("Please go Settings to enable Location Services. \nNote: You may have to wait a few seconds for your location to be found.");
        helpBuilder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog
                    }
                });

        // Remember, create doesn't show the dialog
        AlertDialog helpDialog = helpBuilder.create();
        helpDialog.show();
    }
}