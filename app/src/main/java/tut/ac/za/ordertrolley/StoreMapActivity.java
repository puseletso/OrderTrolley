package tut.ac.za.ordertrolley;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import tut.ac.za.ordertrolley.classes.Constants;
import tut.ac.za.ordertrolley.classes.Item;
import tut.ac.za.ordertrolley.classes.Store;

public class StoreMapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Store store;
    private Geocoder mGeocoder;
    private List<Address> locationAddress;
    private double latitude;
    private double longitude;
    private String key;
    private List<Item> items;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_map);

        getSupportActionBar().setTitle("Click Marker");
        items = new ArrayList<Item>();
        Intent intent = getIntent();

        store = (Store) intent.getSerializableExtra(Constants.STORE);
        key = intent.getStringExtra(Constants.KEY);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.




        mGeocoder = new Geocoder(getApplicationContext(), Locale.getDefault());


        try {
            locationAddress = mGeocoder.getFromLocationName(store.getAddress().getStreetName(),1);

            latitude = locationAddress.get(0).getLatitude();
            longitude = locationAddress.get(0).getLongitude();
            //Toast.makeText(StoreMapActivity.this,String.valueOf(locationAddress.get(0).getLongitude()+ "Lat"),Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera


        LatLng sydney = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(sydney).title(store.getName() +"("+store.getAddress().getStreetName()+")"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Intent intent = new Intent(StoreMapActivity.this,StoreCatalogueActivity.class);
                intent.putExtra(Constants.STORE,store);
                intent.putExtra(Constants.KEY,key);

                startActivity(intent);

                return false;
            }
        });

    }
}
