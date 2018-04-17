package com.freemoney.apidemojeetendra;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.freemoney.apidemojeetendra.adapters.ListDataAdapter;
import com.freemoney.apidemojeetendra.model.Employeegetset;
import com.freemoney.apidemojeetendra.utility.Constant;
import com.freemoney.apidemojeetendra.utility.MySingleton;
import com.freemoney.apidemojeetendra.utility.Util;
import com.freemoney.apidemojeetendra.utility.Validation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements ListDataAdapter.DeleteupdateCommunicate {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    ListDataAdapter recyclerAdapter;
    ArrayList<Employeegetset> empList;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    String gender;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);

            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    public void checkgps() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();
        }
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        //Request location updates:
                    }

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }
                return;
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(MainActivity.this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        checkLocationPermission();
        checkgps();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.custom_dialog);
                dialog.setTitle("Title...");

                final EditText fullname = (EditText) dialog.findViewById(R.id.etname);
                final EditText txemail = (EditText) dialog.findViewById(R.id.etemail);
                final EditText txnumber = (EditText) dialog.findViewById(R.id.etNumber);
                final EditText txtaddress = (EditText) dialog.findViewById(R.id.etaddress);
                final EditText txtage = (EditText) dialog.findViewById(R.id.etage);
                Button Add = (Button) dialog.findViewById(R.id.save);
                gender = "Male";
                RadioGroup radioGroup = (RadioGroup) dialog.findViewById(R.id.rg);

                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        RadioButton rb = (RadioButton) group.findViewById(checkedId);
                        if (null != rb && checkedId > -1) {
                            gender = "" + rb.getText().toString();
                            //  Toast.makeText(MainActivity.this, rb.getText(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                Add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (!Validation.isValid(Validation.BLANK_CHECK, fullname.getText().toString())) {
                            Toast.makeText(getApplicationContext(), "Please enter name", Toast.LENGTH_LONG).show();
                        } else if (!Validation.isValid(Validation.BLANK_CHECK, txemail.getText().toString())) {
                            Toast.makeText(getApplicationContext(), "Please enter email", Toast.LENGTH_LONG).show();

                        } else if (!Validation.isValid(Validation.EMAIL, txemail.getText().toString())) {
                            Toast.makeText(getApplicationContext(), "Please enter valid email", Toast.LENGTH_LONG).show();

                        } else if (!Validation.isValid(Validation.BLANK_CHECK, txnumber.getText().toString())) {
                            Toast.makeText(getApplicationContext(), "Please enter Mobile Number", Toast.LENGTH_LONG).show();
                        } else if (!Validation.isValid(Validation.MOBILE, txnumber.getText().toString())) {
                            Toast.makeText(getApplicationContext(), "Please enter valid mobile number", Toast.LENGTH_LONG).show();

                        } else if (!Validation.isValid(Validation.BLANK_CHECK, txtaddress.getText().toString())) {
                            Toast.makeText(getApplicationContext(), "Please enter address", Toast.LENGTH_LONG).show();

                        } else if (!Validation.isValid(Validation.BLANK_CHECK, txtage.getText().toString())) {
                            Toast.makeText(getApplicationContext(), "Please enter age", Toast.LENGTH_LONG).show();

                        } else if (gender == null && gender.equals("")) {
                            Toast.makeText(getApplicationContext(), "Please select gender", Toast.LENGTH_LONG).show();

                        } else {
                            JSONObject jo = new JSONObject();
                            try {
                                double[] gps = Util.getGPS(MainActivity.this);
                                String latitude = "" + gps[0];

                                String longtitude = "" + gps[1];
                                jo.put("action", "Add_Employee");
                                jo.put("Name", fullname.getText().toString());
                                jo.put("Email_id", txemail.getText().toString());
                                jo.put("Mobile_no", txnumber.getText().toString());
                                jo.put("Address", txtaddress.getText().toString());
                                jo.put("Location", "" + latitude + "," + longtitude);
                                jo.put("Gender", gender);
                                jo.put("Age", txtage.getText().toString());
                                addemployee(jo.toString());
                                dialog.dismiss();


                            } catch (JSONException e) {

                            }

                        }

                    }
                });
                // if button is clicked, close the custom dialog

                dialog.show();

            }
        });
        showlistdata();
        // Request a string response from the provided URL.
    }

    public void addemployee(final String data) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.baseUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // your response

                        JSONObject mainobj = null;
                        try {
                            empList = new ArrayList<>();
                            mainobj = new JSONObject(response);
                            if (mainobj.getBoolean("success") == true) {
                                showlistdata();
                                Toast.makeText(getApplicationContext(), "Inserted successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "No data Found", Toast.LENGTH_LONG).show();

                            }
                        } catch (JSONException e) {

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // error
            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                String your_string_json = data;
                return your_string_json.getBytes();
            }
        };
        // Add the request to the RequestQueue.
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }

    public void showlistdata() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.baseUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject mainobj = null;
                        try {
                            empList = new ArrayList<>();
                            mainobj = new JSONObject(response);
                            if (mainobj.getBoolean("success") == true) {
                                //Log.d("sagareres", "" + mainobj.getString("success"));
                                JSONArray jsonArray = mainobj.getJSONArray("details");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject myjsonobject = jsonArray.getJSONObject(i);
                                    String employeeid = myjsonobject.getString("emp_id");
                                    String employeename = myjsonobject.getString("name");
                                    String employeeemailid = myjsonobject.getString("email_id");
                                    String employeemobileno = myjsonobject.getString("mobile_no");
                                    String employeeaddress = myjsonobject.getString("address");
                                    String employeelocation = myjsonobject.getString("location");
                                    String employeegender = myjsonobject.getString("gender");
                                    String employeeage = myjsonobject.getString("age");
                                    Employeegetset employeegetset = new Employeegetset(employeeid, employeename, employeeemailid, employeemobileno, employeeaddress, employeelocation, employeegender, employeeage);
                                    empList.add(employeegetset);

                                    Log.d("employeeids", "" + myjsonobject.getString("location"));
                                }
                                recyclerAdapter = new ListDataAdapter(MainActivity.this, empList);
                                recyclerView.setAdapter(recyclerAdapter);

                                //   Toast.makeText(getApplicationContext(), "" + response, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "No data Found", Toast.LENGTH_LONG).show();

                            }
                        } catch (JSONException e) {

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // error
            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                String your_string_json = "{\n" +
                        "\"action\":\"Emplist\"\n" +
                        "}";
                return your_string_json.getBytes();
            }
        };
        // Add the request to the RequestQueue.
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);

    }

    @Override
    public void ondelete(String s) {
        try {
            JSONObject jo = new JSONObject();
            jo.put("action", "Del_Emp");

            jo.put("Emp_id", s);
            deleteemp(jo.toString());

        } catch (JSONException e) {

        }
    }

    @Override
    public void onupdate(final String data) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.baseUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // your response

                        JSONObject mainobj = null;
                        try {
                            empList = new ArrayList<>();
                            mainobj = new JSONObject(response);
                            if (mainobj.getBoolean("success") == true) {
                                showlistdata();
                                Toast.makeText(getApplicationContext(), "Update successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "No data Found", Toast.LENGTH_LONG).show();

                            }
                        } catch (JSONException e) {

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // error
            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                String your_string_json = data;
                return your_string_json.getBytes();
            }
        };
        // Add the request to the RequestQueue.
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }

    public void deleteemp(final String empid) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.baseUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // your response

                        JSONObject mainobj = null;
                        try {
                            empList = new ArrayList<>();
                            mainobj = new JSONObject(response);
                            if (mainobj.getBoolean("success") == true) {
                                showlistdata();
                                Toast.makeText(getApplicationContext(), "Delete successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "No data Found", Toast.LENGTH_LONG).show();

                            }
                        } catch (JSONException e) {

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // error
            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                String your_string_json = empid;
                return your_string_json.getBytes();
            }
        };
        // Add the request to the RequestQueue.
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }
}
