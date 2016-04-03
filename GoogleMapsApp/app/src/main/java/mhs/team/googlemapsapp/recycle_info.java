package mhs.team.googlemapsapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;

public class recycle_info extends Activity {
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }
        return true;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        //getActionBar().setTitle("Recycling Tips");
        //getActionBar().setDisplayHomeAsUpEnabled(true);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycle_info);
    }
}
