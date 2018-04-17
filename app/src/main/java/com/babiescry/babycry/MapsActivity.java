package com.babiescry.babycry;

import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Handler handler = new Handler(new HandlerCallback());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    class HandlerCallback implements Handler.Callback {
        @Override
        public boolean handleMessage(Message msg) {
            Bundle recievedData = msg.getData();
            String stationString = recievedData.getString("stationJsonString");
            try {
                JSONObject station = new JSONObject(stationString);;
                JSONObject position = station.getJSONObject("position");
                LatLng stationLatLng = new LatLng(position.getDouble("lat"), position.getDouble("lng"));

                String name = station.getString("name");
                final String availableBikeStands = station.getString("available_bike_stands");
                final String availableBikes = station.getString("available_bikes");

                String message = "Available Bike Stands: " + availableBikeStands + ", " +
                        "Available Bikes: " + availableBikes;
                mMap.addMarker(new MarkerOptions().position(stationLatLng).title(name).snippet(message));
                if(recievedData.getBoolean("moveCamera2ThisPosition", false))
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(stationLatLng, 14));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return false;
        }
    }

    @Override
    public void onDestroy(){
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
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
        setMarkers();
    }

    private void setMarkers(){
        final String stationJSON = getIntent().getStringExtra("stationJsonString");
        new Thread(new Runnable() {
            public void run() {
                final JSONArray jsonArray;
                try {
                    jsonArray = new JSONArray(stationJSON);
                    for(int i = 0; i<jsonArray.length(); i++){
                        JSONObject station = jsonArray.getJSONObject(i);
                        Message msg = new Message();
                        Bundle data = new Bundle();
                        data.putString("stationJsonString",  station.toString());
                        if(i == 0)
                            data.putBoolean("moveCamera2ThisPosition",  true);
                        msg.setData(data);
                        handler.sendMessage(msg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
