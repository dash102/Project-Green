package mhs.team.googlemapsapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class paper_maps extends FragmentActivity {

    public static double latitude = 0.0;
    public static double longitude = 0.0;
    ParseObject spots = new ParseObject("spots");
    public static double latitudeArray[] = new double[1000];
    public static double longitudeArray[] = new double[1000];
    public static int x = 0;

    Location location;
    Location myLocation;

    public static GoogleMap mMap; // Might be null if Google Play services APK is not available.
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }
        return true;
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        getActionBar().setTitle("Paper Bins");
        getActionBar().setDisplayHomeAsUpEnabled(true);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.paper_maps);
        setUpMapIfNeeded();
        final Button paperMarker = (Button) findViewById(R.id.paperMarker);
        paperMarker.setText("Add a Marker");

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_MEDIUM);
        String provider = locationManager.getBestProvider(criteria, true);

        location = locationManager.getLastKnownLocation(provider);
        myLocation = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
        // Check location
        Log.e("Location: ", new LatLng(myLocation.getLatitude(), myLocation.getLongitude()).toString());

        for(int i = 0; i < longitudeArray.length; i++) {
            longitudeArray[i] = 20.0;
            latitudeArray[i] = 20.0;
        }

        // Get all of the paper data points from parse

        ParseQuery
                .getQuery("spots")
                .whereEqualTo("type", "paper")
                .findInBackground(new FindCallback<ParseObject>() {
                    public void done(List<ParseObject> spots, ParseException e) {
                        //Toast.makeText(getApplicationContext(), "asdfkljas;df", Toast.LENGTH_SHORT).show();
                        if (e == null) {
                            for (int i = 0; i < spots.size(); i++) {
                                longitudeArray[i] = spots.get(i).getDouble("longitude");
                                latitudeArray[i] = spots.get(i).getDouble("latitude");
                                //longitudeArray[i] = 1234.4321;
                                //latitudeArray[i] = 4321.1234;
                                //Toast.makeText(getApplicationContext(), String.valueOf(longitudeArray[i]), Toast.LENGTH_LONG).show();
                                x++;
                            }
                            makeMarkers();
                        } else {
                            Log.d("score", "Error: " + e.getMessage());
                            Toast.makeText(getApplicationContext(), "drumph donald ", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        paperMarker.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        String locationProviders = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
                        if (locationProviders == null || locationProviders.equals("")) {
                            showSimplePopUp();
                        }
                        else if (!(locationProviders == null || locationProviders.equals(""))){
                            if (paperMarker.getText().toString().equals("Add a Marker")) {
                                // Setting location for future use

                                paperMarker.setText("Save");

                                // Set marker
                                mMap.addMarker(new MarkerOptions()
                                        .position(new LatLng(myLocation.getLatitude(), myLocation.getLongitude()))
                                        .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher))
                                        .title("Paper recycling bin")
                                        .draggable(true));

                                latitude = myLocation.getLatitude();
                                longitude = myLocation .getLongitude();
                            } else {
                                // Send marker data to Parse
                                spots.put("type", "paper");
                                spots.put("longitude", longitude);
                                spots.put("latitude", latitude);
                                spots.put("username", "Bob123");
                                spots.saveInBackground();
                                Toast.makeText(getApplicationContext(), "Spot saved.", Toast.LENGTH_SHORT).show();
                                paperMarker.setText("Add a Marker");


                            }
                        }
                    }
                }
        );


    }


    public void makeMarkers() {
        for (int i = 0; i < longitudeArray.length; i++) {
            // Set markers
            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(latitudeArray[i], longitudeArray[i]))
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher))
                    .title("Paper recycling bin")
                    .draggable(false));
        }
        Toast.makeText(getApplicationContext(), String.valueOf(x), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }



    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */




    private void setUpMap() {
        mMap.setMyLocationEnabled(true);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(39.1917,-96.5917), 12.0f));

    }
}