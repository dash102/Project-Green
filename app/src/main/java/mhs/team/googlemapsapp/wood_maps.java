package mhs.team.googlemapsapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.provider.ContactsContract;
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
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class wood_maps extends Maps implements GoogleMap.OnMarkerDragListener {

    DatabaseReference ref = database.getReference("Wood");

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }
        return true;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.wood_maps);
        setUpMapIfNeeded();
        final Button woodMarker = (Button) findViewById(R.id.woodMarker);
        woodMarker.setText("Add a Marker");

        mMap.setOnMarkerDragListener(this);
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        String provider = locationManager.getBestProvider(criteria, true);

        location = locationManager.getLastKnownLocation(provider);
        myLocation = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
        // Check location
        Log.e("Location: ", new LatLng(myLocation.getLatitude(), myLocation.getLongitude()).toString());

        Query queryRef = ref.orderByChild("wood");

        // Get all of the location points from the firebase database
        queryRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChild) {
                System.out.println(dataSnapshot.getValue());

                Map<String, Object> value = (Map<String, Object>) dataSnapshot.getValue();

                String lon = String.valueOf(value.get("longitude"));
                String lat = String.valueOf(value.get("latitude"));
                makeMarkers(Double.parseDouble(lat), Double.parseDouble(lon));

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        woodMarker.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        String locationProviders = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
                        if (locationProviders == null || locationProviders.equals("")) {
                            showSimplePopUp();
                        }
                        else if (!(locationProviders == null || locationProviders.equals(""))){
                            if (woodMarker.getText().toString().equals("Add a Marker")) {
                                // Setting location for future use

                                woodMarker.setText("Save");

                                // Set marker
                                pin = mMap.addMarker(new MarkerOptions()
                                        .position(new LatLng(myLocation.getLatitude(), myLocation.getLongitude()))
                                        .icon(BitmapDescriptorFactory.fromResource(R.mipmap.pinit_resized))
                                        .title("Wood recycling bin")
                                        .draggable(true));
                                //Toast.makeText(getApplicationContext(), pin.getPosition() + "", Toast.LENGTH_LONG).show();

                                // Set the marker location to the current one, even if it will be dragged later
                                String[] latLongPin = (pin.getPosition()+"").split(",");
                                latitude = Double.parseDouble(latLongPin[0].replaceAll("[^\\d.]", ""));
                                longitude = Double.parseDouble(latLongPin[1].replaceAll("[^\\d.]", ""));

                            } else {
                                pin.setDraggable(false);
                                // If user dragged the pin to another location, update the location.
                                if (updated){
                                    String[] latLongPin = (updatedPosition).split(",");
                                    latitude = Double.parseDouble(latLongPin[0].replaceAll("[^\\d.]", ""));

                                    longitude = Double.parseDouble(latLongPin[1].replaceAll("[^\\d.]", ""));
                                }
                                // Send marker data to the firebase database
                                Map<String, Double> toPut = new HashMap<>();
                                toPut.put("longitude", longitude);
                                toPut.put("latitude", latitude);
                                ref.push().setValue(toPut);

                                Toast.makeText(getApplicationContext(), "Spot saved.", Toast.LENGTH_SHORT).show();
                                woodMarker.setText("Add a Marker");

                            }
                        }
                    }
                }
        );

    }


    public void makeMarkers(double latit, double longi) {
        // Set markers
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(latit, longi))
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.pinit_resized))
                .title("Wood recycling bin")
                .draggable(false));
        //Log.d("okay..........", latit + ", " + longi);
    }

    @Override
    public void onMarkerDragStart(Marker marker) {
        updated = true;
    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        Toast.makeText(getApplicationContext(), marker.getPosition() + "", Toast.LENGTH_LONG).show();
        updatedPosition = marker.getPosition() + "";
        updated = true;
    }


    @Override
    public void onMarkerDrag(Marker marker) {
        updated = true;
    }

}