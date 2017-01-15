package com.example.joginderpal.roombuddy;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    Toolbar bt;

    double lat1;
    double lon1;
    private GoogleMap mMap;
    Marker marker;
    GoogleApiClient mGoogleApiClient;
    LatLng ll1;
    String location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        bt= (Toolbar) findViewById(R.id.bt);
        bt.findViewById(R.id.bottomregister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LatLng ll2=marker.getPosition();

                Intent intent=new Intent(MapsActivity.this,MainActivity.class);
               intent.putExtra("latitude",ll2.latitude);
                intent.putExtra("longitude",ll2.longitude);
                startActivity(intent);

            }
        });
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

        if (mMap != null) {

            mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
                @Override
                public void onMarkerDragStart(Marker marker) {

                }

                @Override
                public void onMarkerDrag(Marker marker) {

                }

                @Override
                public void onMarkerDragEnd(Marker marker) {

                    Geocoder gc = new Geocoder(MapsActivity.this);
                    LatLng ll = marker.getPosition();
                    try {
                        List<Address> list = gc.getFromLocation(ll.latitude, ll.longitude, 1);
                        android.location.Address ad = list.get(0);
                       marker.setTitle(ad.getLocality());
                        marker.showInfoWindow();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            });


            mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                @Override
                public View getInfoWindow(Marker marker) {
                    return null;
                }

                @Override
                public View getInfoContents(Marker marker) {
                    ImageView img;

                    View v = getLayoutInflater().inflate(R.layout.info_window, null);
                    TextView tx = (TextView) v.findViewById(R.id.tx);
                    TextView tx1 = (TextView) v.findViewById(R.id.tx1);
                    TextView tx2 = (TextView) v.findViewById(R.id.tx2);
                    img = (ImageView) v.findViewById(R.id.imageinfo);
                     ll1 = marker.getPosition();
                    location=marker.getTitle();
                    tx.setText(marker.getTitle());
                    tx1.setText("latitude :" + ll1.latitude);
                    tx2.setText("longitude :" + ll1.longitude);
                 //   if (pass.equals("restaurant")) {
                 //       String icon = getIntent().getExtras().getString("icon");
                 //       Picasso.with(getApplicationContext()).load(icon).into(img);
                //    }


                    return v;
                }
            });

            lat1=getIntent().getExtras().getDouble("lat");
             lon1=getIntent().getExtras().getDouble("lon");
            gotoLocation(lat1,lon1);
            setMarker(lat1,lon1);

           //  Add a marker in Sydney and move the camera
          //  LatLng sydney = new LatLng(-34, 151);
          //  mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
          //  mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        }
    }
    public void gotoLocation(double lat, double lon) {
        LatLng ll = new LatLng(lat, lon);
        CameraUpdate update = CameraUpdateFactory.newLatLng(ll);
        mMap.moveCamera(update);
    }

    public void gotoLocation(double lat, double lon, float zoom) {
        LatLng ll = new LatLng(lat, lon);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, zoom);
        mMap.moveCamera(update);
    }

    public void setMarker(String locality, double lat, double lon) {

        if (marker != null) {
            marker.remove();
        }

        MarkerOptions markerOptions = new MarkerOptions().title(locality).position(new LatLng(lat, lon)).draggable(true).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN));
        marker = mMap.addMarker(markerOptions);

    }


    public void setMarker(double lat, double lon) {

        if (marker != null) {
            marker.remove();
        }

        MarkerOptions markerOptions = new MarkerOptions().position(new LatLng(lat, lon)).draggable(true).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN));
        marker = mMap.addMarker(markerOptions);

    }

    public void setMarker1(double lat, double lon) {

        if (marker != null) {
            marker.remove();
        }

        MarkerOptions markerOptions = new MarkerOptions().position(new LatLng(lat, lon)).draggable(true).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        marker = mMap.addMarker(markerOptions);

    }


}
