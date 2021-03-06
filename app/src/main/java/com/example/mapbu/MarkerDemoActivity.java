package com.example.mapbu;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class MarkerDemoActivity extends FragmentActivity implements
        GoogleMap.OnMarkerClickListener,
        OnMapReadyCallback {
    private FusedLocationProviderClient fusedLocationClient;

    private static final LatLng PERTH = new LatLng(-31.952854, 115.857342);
    private static final LatLng SYDNEY = new LatLng(-33.87365, 151.20689);
    private static final LatLng BRISBANE = new LatLng(-27.47093, 153.0235);


    private Marker mPerth;
    private Marker mSydney;
    private Marker mBrisbane;
    private Marker selectedMarker= null;
    private Marker mCurrent;
    private LocationRequest loc;

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
    SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        //gps = new GPSTracker(HomeActivity.this);
        //Location currentLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);

    }


    protected void createLocationRequest() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    /** Called when the map is ready. */
    @Override
    public void onMapReady(GoogleMap map) {
        loc = new LocationRequest();
        mMap = map;
        /*fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            LatLng myLatLng = new LatLng(location.getLatitude(),
                                    location.getLongitude());
                            mCurrent = mMap.addMarker(new MarkerOptions()
                                    .position(myLatLng)
                                    .title("Current Location"));
                            mBrisbane.setTag(0);

                            // Logic to handle location object
                        }
                    }
                });*/
        /*private void startLocationUpdates() {
            fusedLocationClient.requestLocationUpdates(locationRequest,
                    locationCallback,
                    Looper.getMainLooper());
        }*/

        // Add some markers to the map, and add a data object to each marker.
        mPerth = mMap.addMarker(new MarkerOptions()
                .position(PERTH)
                .title("Perth"));
        mPerth.setTag(0);

        mSydney = mMap.addMarker(new MarkerOptions()
                .position(SYDNEY)
                .title("Sydney"));
        mSydney.setTag(0);

        mBrisbane = mMap.addMarker(new MarkerOptions()
                .position(BRISBANE)
                .title("Brisbane"));
        mBrisbane.setTag(0);

        // Set a listener for marker click.
        mMap.setOnMarkerClickListener(this);
    }

    /** Called when the user clicks a marker. */
    @Override
    public boolean onMarkerClick(final Marker marker) {

        // Retrieve the data from the marker.
        Integer clickCount = (Integer) marker.getTag();

        // Check if a click count was set, then display the click count.
        if (clickCount != null) {
            clickCount = clickCount + 1;
            marker.setTag(clickCount);
            if(clickCount==1){
           //     Toast.makeText(this,
               //         marker.getTitle() +
                //                " has been clicked " + clickCount + " times.",
                //        Toast.LENGTH_SHORT).show();
                Toast.makeText(this,"You have clicked "+marker.getTitle()+". Click this marker again to set your stop.",Toast.LENGTH_SHORT).show();
            }
            else{
                if(selectedMarker==null ||!selectedMarker.equals(marker)){
                    Toast.makeText(this, marker.getTitle()+" has been set as your stop.",Toast.LENGTH_SHORT).show();
                    selectedMarker=marker;
                }
                else{
                    Toast.makeText(this, marker.getTitle()+" has been deselected as your stop. Select another stop or quit",Toast.LENGTH_SHORT).show();
                }
            }


           // Toast.makeText(this,
            //        marker.getTitle() +
             //               " has been clicked " + clickCount + " times.",
             //       Toast.LENGTH_SHORT).show();
        }

        // Return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).
        return false;
    }

}
