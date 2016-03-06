package mhs.team.googlemapsapp;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
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
import com.parse.ParseObject;

public class plastic_maps extends FragmentActivity {

    public static double latitude = 0.0;
    public static double longitude = 0.0;
    ParseObject spots = new ParseObject("spots");

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }
        return true;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        getActionBar().setTitle("Plastic Bins");
        getActionBar().setDisplayHomeAsUpEnabled(true);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.plastic_maps);
        setUpMapIfNeeded();
        final Button plasticMarker = (Button) findViewById(R.id.plasticMarker);
        plasticMarker.setText("ADD A MARKER");

        plasticMarker.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        if (plasticMarker.getText().toString() == "ADD A MARKER") {
                            // Setting location for future use
                            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                            Criteria criteria = new Criteria();
                            criteria.setAccuracy(Criteria.ACCURACY_LOW);
                            String provider = locationManager.getBestProvider(criteria, true);

                            Location location = locationManager.getLastKnownLocation(provider);
                            Location myLocation = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
                            // Check location
                            Log.e("Location: ", new LatLng(myLocation.getLatitude(), myLocation.getLongitude()).toString());

                            plasticMarker.setText("SAVE");

                            // Set marker
                            mMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(myLocation.getLatitude(), myLocation.getLongitude()))
                                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher))
                                    .title("Plastic recycling bin")
                                    .draggable(true));

                            latitude = myLocation.getLatitude();
                            longitude = myLocation.getLongitude();
                        } else {
                            spots.put("type", "plastic");
                            spots.put("longitude", longitude);
                            spots.put("latitude", latitude);
                            spots.put("username", "Bob123");
                            spots.saveInBackground();
                            Toast.makeText(getApplicationContext(), "Spot saved.", Toast.LENGTH_SHORT).show();
                            plasticMarker.setText("ADD A MARKER");
                        }
                    }
                }
        );
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
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(39.1917, -96.5917), 12.0f));

    }
}