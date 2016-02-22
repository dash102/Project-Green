package mhs.team.googlemapsapp;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class paper_maps extends FragmentActivity {

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
        getActionBar().setTitle("Paper Bins");
        getActionBar().setDisplayHomeAsUpEnabled(true);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.paper_maps);
        setUpMapIfNeeded();
        final Button paperMarker = (Button) findViewById(R.id.paperMarker);

        paperMarker.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        // Setting location for future use
                        LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
                        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                        Criteria criteria = new Criteria();
                        criteria.setAccuracy(Criteria.ACCURACY_LOW);
                        String provider = locationManager.getBestProvider(criteria, true);

                        Location location = locationManager.getLastKnownLocation(provider);
                        Location myLocation = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
                        // Check location
                        Log.e("Location: ", new LatLng(myLocation.getLatitude(), myLocation.getLongitude()).toString());
                        // Set marker
                        mMap.addMarker(new MarkerOptions()
                                .position(new LatLng(myLocation.getLatitude(), myLocation.getLongitude()))
                                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher))
                                .title("Paper recycling bin")
                                .draggable(true));

                    }
                }
        );
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

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

    private void setUpMap() {
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(39.1917,-96.5917), 12.0f));

    }
}
