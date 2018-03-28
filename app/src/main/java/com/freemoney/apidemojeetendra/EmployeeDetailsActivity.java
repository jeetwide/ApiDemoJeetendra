package com.freemoney.apidemojeetendra;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EmployeeDetailsActivity extends AppCompatActivity implements OnMapReadyCallback {
    @BindView(R.id.tvname)
TextView tvname;
    @BindView(R.id.tvemail)
TextView tvemail;
    @BindView(R.id.tvcontact)
    TextView tvcontact;
    @BindView(R.id.tvaddress)
    TextView tvaddress;

    private GoogleMap mMap;

    Bundle b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_details);
ButterKnife.bind(this);
        b=getIntent().getExtras();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        tvname.setText(b.getString("Name"));
        tvemail.setText(b.getString("Email"));
        tvaddress.setText(b.getString("Address"));
        tvcontact.setText(b.getString("Phone"));


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        String location=b.getString("Location");
        List<String> latlong = Arrays.asList(location.split(","));
        LatLng CurrentPoint = new LatLng(Double.parseDouble(latlong.get(0)),Double.parseDouble(latlong.get(1)));
        mMap.addMarker(new
                MarkerOptions().position(CurrentPoint).title("Current Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(CurrentPoint));

    }
}
