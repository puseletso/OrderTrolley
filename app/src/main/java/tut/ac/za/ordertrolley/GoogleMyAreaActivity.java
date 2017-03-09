package tut.ac.za.ordertrolley;

import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import tut.ac.za.ordertrolley.classes.Constants;
import tut.ac.za.ordertrolley.classes.Distance;
import tut.ac.za.ordertrolley.classes.Store;
import tut.ac.za.ordertrolley.classes.Store_Distance;

public class GoogleMyAreaActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Store store;
    private Geocoder mGeocoder;

    private List<Address> locationAddress;
    private double latitude;
    private double longitude;
    private double destLatitude;
    private double destLongitude;
    private List<Address> destAddresses;
    private TextView tvDistance;
    private Distance distance;
    private double travelDistance;
    private Store_Distance storeDistance;
    PlaceAutocompleteFragment autocompleteFragment;
    private String TAG ="GoogleMyAreaActivity";
    private String area;
    private LatLng destLat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_my_area);

        Intent intent = getIntent();

        store = (Store) intent.getSerializableExtra(Constants.STORE);
        mGeocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

        autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        tvDistance = (TextView) findViewById(R.id.tvDistance);



        try {
            locationAddress = mGeocoder.getFromLocationName(store.getAddress().getStreetName(),1);

            latitude = locationAddress.get(0).getLatitude();
            longitude = locationAddress.get(0).getLongitude();
            //Toast.makeText(StoreMapActivity.this,String.valueOf(locationAddress.get(0).getLongitude()+ "Lat"),Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
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

        Toast.makeText(GoogleMyAreaActivity.this,store.getAddress().getStreetName(),Toast.LENGTH_SHORT).show();




        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i(TAG, "Place: " + place.getName());

                destLat = place.getLatLng();
                area =place.getAddress().toString();

              //  order.setDeliveryAddress(place.getAddress().toString());

                mMap.addMarker(new MarkerOptions().position(place.getLatLng()).title(place.getName().toString()));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(place.getLatLng()));
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });

    }


    public void onSubmit(View view) {


        if(!"".equals(area)) {







                    Polyline line = mMap.addPolyline(new PolylineOptions().add(new LatLng(latitude, longitude), new LatLng(destLatitude, destLongitude))
                            .width(5)
                            .color(Color.RED));

                    distance = new Distance();

                    distance.setStartP(new LatLng(latitude, longitude));
                    distance.setEndP(destLat);

                    travelDistance = distance.CalculationByDistance();

                    tvDistance.setVisibility(View.VISIBLE);
                    tvDistance.setText("Distance: " + String.format("%.3f", travelDistance) + " KM");



        }else
        {

            Toast.makeText(GoogleMyAreaActivity.this,"Enter Your Delivery Address",Toast.LENGTH_SHORT).show();
        }

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my_area, menu);



        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        // User user  = fh.getUSer(mFirebaseUser.getUid());


        int id = item.getItemId();



        if(id == R.id.action_wallet)
        {

            if(!"".equals(area)) {
                storeDistance = new Store_Distance();
                storeDistance.setStore(store);
                storeDistance.setTotalDistance(travelDistance);
                storeDistance.setDeliveryArea(area);

                Intent intent = new Intent(GoogleMyAreaActivity.this, CostActivity.class);
                intent.putExtra(Constants.STORE_DISTANCE, storeDistance);
                startActivity(intent);
            }else
            {
                Toast.makeText(GoogleMyAreaActivity.this,"Enter Area Field",Toast.LENGTH_SHORT).show();
            }
        }






        return super.onOptionsItemSelected(item);
    }
}
