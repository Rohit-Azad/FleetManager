package com.example.fleetmanager;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SOSActivity extends AppCompatActivity {

    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private static final String TRACKING_LOCATION_KEY = "tracking_location";
    private static final String TAG = SOSActivity.class.getSimpleName();

    // ui
    Button mGetQuoteLocation;
    TextView mLatitude;
    TextView mLongitude;
    TextView mTimestamp;
    TextView mAddress;

    String userName,id;

    private FusedLocationProviderClient mFusedLocationClient;
    private Location mLastLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sos);

        SharedPreferences sp = getSharedPreferences("driver_info",MODE_PRIVATE);
        id=sp.getString("d_id","1");

        userName = sp.getString("driver_name","");

        Log.d(TAG, "onCreate: "+userName);

        initializeUI();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_LOCATION_PERMISSION:
                // If the permission is granted, get the location,
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLocation();
                } else {
                    Toast.makeText(this,
                            R.string.location_permission_denied,
                            Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void initializeUI() {
        mLatitude = (TextView) findViewById(R.id.latitude_value);
        mLongitude = (TextView) findViewById(R.id.longitude_value);
        mTimestamp = (TextView) findViewById(R.id.timestamp_value);
        mAddress = (TextView) findViewById(R.id.address_value);

        // Button -> Get Location
        mGetQuoteLocation = (Button) findViewById(R.id.get_location);
        mGetQuoteLocation.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getLocation();
            }
        });
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]
                            {Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
        } else {
            Log.d(TAG, "getLocation: permissions granted");
        }

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    mLastLocation = location;

                    mLatitude.setText(Double.toString(location.getLatitude()));
                    mLongitude.setText(Double.toString(location.getLongitude()));

                    Timestamp timestamp = new Timestamp(location.getTime());
                    Date date = new Date(timestamp.getTime());
                    mTimestamp.setText(date.toString());

                    setAddress(location);
                } else {
                    Toast.makeText(SOSActivity.this,
                            R.string.location_permission_denied,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void open_map(View view)
    {

        Intent i = new Intent(SOSActivity.this , MapsActivity.class);

        startActivity(i);

    }


    private void sendSMS(String phoneNumber, String message)
    {

        SmsManager sms = SmsManager.getDefault();

        sms.sendTextMessage(phoneNumber, null, message, null , null);


    }

    public void open_addperson(View view) {
        Intent i = new Intent(SOSActivity.this , AddPersonActivity.class);

        startActivity(i);


    }

    public void sos(View view) {

        SharedPreferences sp = getSharedPreferences("persons" , MODE_PRIVATE);

        String name1 = sp.getString("name1" , "");
        String name2 = sp.getString("name2" , "");
        String name3 = sp.getString("name3" , "");


        String contact1 = sp.getString("contact1" , "");
        String contact2 = sp.getString("contact2" , "");
        String contact3 = sp.getString("contact3" , "");

        if(contact1.trim().length()==10)
        {
            sendSMS(contact1.trim() ,"Hey "+name1 +",\nSOS ALERT from "+
                                        userName.trim().toUpperCase()+" (ID: "+id+" )"+
                                        "\nLast Location: "+ mAddress.getText().toString().trim()+
//                                        "\nLatitude: "+mLatitude.getText().toString()trim()+
//                                        "\nLongitude: "+mLongitude.getText().toString()trim() +
                                       // "\nTime: "+mTimestamp.getText().toString().trim() +
                                            "\n<FLEET MANAGER ALERT SERVICE>");
            Toast.makeText(this , "Alert message sent" , Toast.LENGTH_SHORT).show();
        }

        if(contact2.trim().length()==10)
        {
            sendSMS(contact2.trim() ,"Hey "+name2 +",\nSOS ALERT from "+
                    userName.trim().toUpperCase()+" (ID: "+id+" )"+
                    "\nLast Location: "+ mAddress.getText().toString().trim()+
//                                        "\nLatitude: "+mLatitude.getText().toString()trim()+
//                                        "\nLongitude: "+mLongitude.getText().toString()trim() +
                    // "\nTime: "+mTimestamp.getText().toString().trim() +
                    "\n<FLEET MANAGER ALERT SERVICE>");
            Toast.makeText(this , "Alert message sent" , Toast.LENGTH_SHORT).show();
        }

        if(contact3.trim().length()==10)
        {
            sendSMS(contact3.trim() ,"Hey "+name3 +",\nSOS ALERT from "+
                    userName.trim().toUpperCase()+" (ID: "+id+" )"+
                    "\nLast Location: "+ mAddress.getText().toString().trim()+
//                                        "\nLatitude: "+mLatitude.getText().toString()trim()+
//                                        "\nLongitude: "+mLongitude.getText().toString()trim() +
                    // "\nTime: "+mTimestamp.getText().toString().trim() +
                    "\n<FLEET MANAGER ALERT SERVICE>");
            Toast.makeText(this , "Alert message sent" , Toast.LENGTH_SHORT).show();
        }

        /////

        JSONObject jobj = new JSONObject();

        SharedPreferences sp1 = getSharedPreferences("driver_info",MODE_PRIVATE);
        String admin_id =sp1.getString("admin_id2","");

        try {

            jobj.put("lat" , mLatitude.getText().toString());
            jobj.put("lng" , mLongitude.getText().toString());
            jobj.put("address",mAddress.getText().toString());
            jobj.put("timestamp",mTimestamp.getText().toString());
            jobj.put("d_id",sp1.getString("d_id",""));
            jobj.put("a_id",sp1.getString("admin_id2",""));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jobjreq = new JsonObjectRequest("http://"+Ipaddress.ip+"/fleet/SOSLog.php",jobj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    if(response.getString("key").equals("0"))
                    {
                       // Toast.makeText(SOSActivity.this ,"Error" , Toast.LENGTH_SHORT).show();

                    }
                    else if(response.getString("key").equals("1")) {
                        Toast.makeText(SOSActivity.this ,"SOS sent Successfully" , Toast.LENGTH_SHORT).show();

//                        Intent i = new Intent(SOSActivity.this, AdminAddInventoryActivity.class);
//
//                        startActivity(i);
//                        finish();
                    }

                    else {
                       // Toast.makeText(SOSActivity.this ,"Error" , Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                System.out.println(error);
            }
        });

        jobjreq.setRetryPolicy(new DefaultRetryPolicy(20000,3,2))  ;
        AppControllerActivity app=new AppControllerActivity(SOSActivity.this);
        app.addToRequestQueue(jobjreq);

        /////
    }

    private void setAddress(Location location) {
        Geocoder geocoder = new Geocoder(SOSActivity.this,
                Locale.getDefault());
        List<Address> addresses = null;
        String resultMessage = "";

        try {
            addresses = geocoder.getFromLocation(
                    location.getLatitude(),
                    location.getLongitude(),
                    // In this sample, get just a single address
                    1);
        } catch (IOException ioException) {
            // Catch network or other I/O problems
            resultMessage = SOSActivity.this
                    .getString(R.string.service_not_available);
            Log.e(TAG, resultMessage, ioException);
        }

        if (addresses == null || addresses.size() == 0) {
            if (resultMessage.isEmpty()) {
                resultMessage = SOSActivity.this
                        .getString(R.string.no_address_found);
                Log.e(TAG, resultMessage);
            }
        } else {
            Address address = addresses.get(0);
            StringBuilder out = new StringBuilder();
            // Fetch the address lines using getAddressLine,
            // join them, and send them to the thread
            for (int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
                out.append(address.getAddressLine(i));
            }

            resultMessage = out.toString();
        }
        mAddress.setText(resultMessage);
        Log.e(TAG, resultMessage);
    }

    @Override
    protected void onResume() {
        super.onResume();

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    mLastLocation = location;

                    mLatitude.setText(Double.toString(location.getLatitude()));
                    mLongitude.setText(Double.toString(location.getLongitude()));

                    Timestamp timestamp = new Timestamp(location.getTime());
                    Date date = new Date(timestamp.getTime());
                    mTimestamp.setText(date.toString());

                    setAddress(location);
                } else {
                    Toast.makeText(SOSActivity.this,
                            R.string.location_permission_denied,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
