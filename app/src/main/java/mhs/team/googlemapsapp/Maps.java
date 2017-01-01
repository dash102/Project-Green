package mhs.team.googlemapsapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Maps extends FragmentActivity {

    public static double latitude = 0.0;
    public static double longitude = 0.0;

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    Location location;
    Location myLocation;
    String updatedPosition;
    boolean updated = false;
    public static GoogleMap mMap; // Might be null if Google Play services APK is not available.
    Marker pin;

    public static Geocoder gcd;

    public String getCity(double lat, double lng) {
        gcd = new Geocoder(getApplicationContext(), Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = gcd.getFromLocation(lat, lng, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (addresses.size() > 0) {
            return addresses.get(0).getLocality();
        } else {
            Log.d("Weird...........", "....");
            return "N/A";
        }
    }

    public void showSimplePopUp() {

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
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    public void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        //if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        //}
    }

    public void setUpMap() {
        mMap.setMyLocationEnabled(true);
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        myLocation = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(myLocation.getLatitude(), myLocation.getLongitude()), 16.0f));

    }
}
