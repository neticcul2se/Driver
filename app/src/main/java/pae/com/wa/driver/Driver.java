package pae.com.wa.driver;

import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class Driver extends FragmentActivity implements LocationListener {
    GoogleMap googleMap;
    Marker mMarker;
    Circle gCircle;
    Button plus,minus;
    TextView free,has;
    public  static int full =15;
    public  static int user=0;
    double mylat,mylng;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);
        if (android.os.Build.VERSION.SDK_INT > 9) {

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

            StrictMode.setThreadPolicy(policy);

        }
        if (!isGooglePlayServicesAvailable())
        {
            finish();
        }
        has =(TextView)findViewById(R.id.has);
        free = (TextView)findViewById(R.id.free);
        plus = (Button)findViewById(R.id.plus);
        minus =(Button)findViewById(R.id.minus);
        free.setText("ว่าง : " + String.valueOf(full));
        has.setText(" ผู้โดยสาร : " + String.valueOf(user));
        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.googleMap);
        googleMap = supportMapFragment.getMap();
        googleMap.setMyLocationEnabled(true);

        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String bestProvider = locationManager.getBestProvider(criteria, true);
        Location location = locationManager.getLastKnownLocation(bestProvider);
        if (location != null) {
            onLocationChanged(location);
        }

        locationManager.requestLocationUpdates(bestProvider, 5000, 0, this);


        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user<15&&full>0) {
                    user = user + 1;
                    full = full - 1;
                    has.setText(" ผู้โดยสาร : " + user);
                    free.setText(" ที่ว่าง : " + full);
                }
            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

           if(user>0&&full<15) {
               user = user - 1;
               full = full + 1;
               has.setText(" ผู้โดยสาร : " + user);
               free.setText(" ที่ว่าง : " + full);
           }

            }
        });


    }
    public  void makerMy(double latitude,double longitude) { //ปักหมุดแล้วซูมกล้องตำแหน่งปัจจุบัน
        LatLng latLng = new LatLng(latitude, longitude);
        if (mMarker != null)
        {
            mMarker.remove();
            gCircle.remove();
        }
        mMarker =  googleMap.addMarker(new MarkerOptions()
                .position(latLng));
        gCircle =  googleMap.addCircle(new CircleOptions()
                .center(latLng)
                .radius(700)
                .strokeWidth(5)
                .strokeColor(Color.argb(100, 11, 195, 255))
                .fillColor(Color.argb(10, 11, 195, 255)));


       // googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLng)
                .tilt(90)
                .bearing(0)
                .zoom(15)
                .build();
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));




    }


    private boolean isGooglePlayServicesAvailable() {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (ConnectionResult.SUCCESS == status) {
            return true;
        } else {
            GooglePlayServicesUtil.getErrorDialog(status, this, 0).show();
            return false;
        }
    }


    @Override
    public void onLocationChanged(Location location) {
        double latitude= location.getLatitude();
        double longitude = location.getLongitude();
        mylat=latitude;
        mylng=longitude;
        makerMy(latitude, longitude);
        sendLocation sent = new sendLocation();

     sent.send(1,2,3, latitude, longitude);

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
