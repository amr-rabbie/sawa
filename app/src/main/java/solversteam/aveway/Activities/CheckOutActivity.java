package solversteam.aveway.Activities;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.satsuware.usefulviews.LabelledSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import solversteam.aveway.Connection.Connection;
import solversteam.aveway.Connection.ConnectionDetector;
import solversteam.aveway.Models.CustomCountries;
import solversteam.aveway.utiltes.CustomMapView;
import solversteam.aveway.utiltes.Fonts;
import solversteam.aveway.utiltes.GetScreenSize;
import solverteam.aveway.R;

public class CheckOutActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener
        , GoogleMap.OnCameraMoveStartedListener,
        GoogleMap.OnCameraMoveListener,
        GoogleMap.OnCameraMoveCanceledListener,
        GoogleMap.OnCameraIdleListener{
    @BindView(R.id.include)
    Toolbar toolbar;
    @BindView(R.id.First_Name)
    EditText fristName;
    @BindView(R.id.Last_Name)
    EditText Last_Name;

    @BindView(R.id.btn_save_address)
    Button btn_save_address;
    private LabelledSpinner countrySpinner, citySpinner, locationSpinner;
    private EditText mobile_edittext;
    private AutoCompleteTextView address_editext;
    private Connection connection;
    private ConnectionDetector connectionDetector;
    private ArrayList<CustomCountries> countriesList, citiesList;
    private ArrayAdapter customadapt;
    private SharedPreferences sharedPreferences;
    private ArrayList<String> countriesnames, citiesnames, adress;
    private int country, currency, language, height;
    private String customer_id, namefirst, namelast, mob,firstcolor;
    private Dialog dialog;
    private int where_i_come;
    private CustomMapView mapView;
    private GoogleMap map;
    private ScrollView main_scroll;
    private GetScreenSize getScreenSize;
    private Marker marker;
    private MarkerOptions markerOptions;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private Location mLastLocation;
    private GoogleMap googleMap;
    private Circle circle;
    private Geocoder geocoder;
    private Double latitude,longitude;
    private LatLng latLng;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);
        Fonts fonts=new Fonts(this);
        getScreenSize = new GetScreenSize(this);
        getScreenSize.getImageSize();
        height = getScreenSize.getHeight();
        main_scroll = (ScrollView) findViewById(R.id.scrollview);
        LocationManager manager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(this, "GPS is disabled!", Toast.LENGTH_LONG).show();
            startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
        }
        else
            Toast.makeText(this, "GPS is enabled!", Toast.LENGTH_LONG).show();
        if(checkLocationPermission())
        {
            if(checkLocationPermission())
            {

            }
        }

        set_map(savedInstanceState);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.addnewaddress);
        countriesList = new ArrayList<>();
        countriesnames = new ArrayList<>();
        citiesList = new ArrayList<>();
        citiesnames = new ArrayList<>();
        adress = new ArrayList<>();
        sharedPreferences = getSharedPreferences("LanguageAndCountry", Context.MODE_PRIVATE);
        firstcolor=sharedPreferences.getString("firstmenucolour","#000000");
        toolbar.setBackgroundColor(Color.parseColor(firstcolor));
        btn_save_address.setBackgroundColor(Color.parseColor(firstcolor));
        country = sharedPreferences.getInt("Country", 1);
        currency = Integer.parseInt(sharedPreferences.getString("currency", "1"));
        language = sharedPreferences.getInt("Lang", 1);
        customer_id = sharedPreferences.getString("customer_id", "53");
        namefirst = sharedPreferences.getString("name", "");
        namelast = sharedPreferences.getString("lastname", "");
        mob = sharedPreferences.getString("mobile", "");
        countrySpinner = (LabelledSpinner) findViewById(R.id.country_spinner);
        citySpinner = (LabelledSpinner) findViewById(R.id.city_spinner);
        locationSpinner = (LabelledSpinner) findViewById(R.id.location);
        address_editext = (AutoCompleteTextView) findViewById(R.id.address);
        mobile_edittext = (EditText) findViewById(R.id.MobileNumber);
        fonts.setView(address_editext);
        fonts.setView(mobile_edittext);
        fonts.setView(fristName);
        fonts.setView(Last_Name);
        fonts.setView(btn_save_address);
        try {
            where_i_come = getIntent().getExtras().getInt("where");
            Log.d("checkwhere", where_i_come + "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        connectionDetector = new ConnectionDetector(this);
        if (connectionDetector.isConnectingToInternet()) {
            try {

                fristName.setText(namefirst);
                Last_Name.setText(namelast);
                mobile_edittext.setText(mob);
                Log.d("mob_o", mob + "mm");

                connection = new Connection(this, "/GetAllCustomerAddresses/" + customer_id + "/" + language, "Get");
                connection.reset();
                connection.Connect(new Connection.Result() {
                    @Override
                    public void data(String str) throws JSONException {
                        JSONObject jsonObject = new JSONObject(str);
                        JSONArray jsonArray = jsonObject.getJSONArray("1-addresses");
                        for (int Counter = 0; Counter < jsonArray.length(); Counter++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(Counter);
                            adress.add(jsonObject1.getString("address1"));
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                                (CheckOutActivity.this, android.R.layout.select_dialog_item, adress);
                        //Getting the instance of AutoCompleteTextView
                        address_editext.setThreshold(1);//will start working from first character
                        address_editext.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
                        address_editext.setTextColor(Color.BLACK);
                    }
                });

                connection = new Connection(this, "/GetAllCustomerCountries/" + country + "/" + language, "Get");
                connection.reset();
                connection.Connect(new Connection.Result() {
                    @Override
                    public void data(String str) throws JSONException {
                        JSONObject jsonObject = new JSONObject(str);
                        JSONArray jsonArray = jsonObject.getJSONArray("1-CustomerCountries");
                        for (int position = 0; position < jsonArray.length(); position++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(position);
                            String country_name = jsonObject1.getString("name");
                            String id_country = jsonObject1.getString("id_country");
                            CustomCountries customCountries = new CustomCountries(country_name, id_country, 0);
                            countriesList.add(customCountries);
                            countriesnames.add(country_name);
                        }
                        customadapt = new ArrayAdapter(CheckOutActivity.this, android.R.layout.simple_spinner_dropdown_item, countriesnames);
                        countrySpinner.setCustomAdapter(customadapt);
                        countrySpinner.setOnItemChosenListener(new LabelledSpinner.OnItemChosenListener() {
                            @Override
                            public void onItemChosen(View labelledSpinner, AdapterView<?> adapterView, View itemView, int position, long id) {
                                String country_name = countriesList.get(position).getCountry_name();
                                String id_country = countriesList.get(position).getId_country();
//                            id_country="21";
                                connection = new Connection(CheckOutActivity.this, "/GetAllCustomerCountryStates/" + country + "/" + id_country + "/" + language, "Get");
                                connection.reset();
                                final String finalId_country = id_country;
                                connection.Connect(new Connection.Result() {
                                    @Override
                                    public void data(String str) throws JSONException {
                                        JSONObject jsonObject = new JSONObject(str);
                                        citiesList=new ArrayList<>();
                                        citiesnames=new ArrayList<>();
                                        JSONArray jsonArray = jsonObject.getJSONArray("1-countries");
                                        for (int position = 0; position < jsonArray.length(); position++) {
                                            JSONObject jsonObject1 = jsonArray.getJSONObject(position);
                                            String country_name = jsonObject1.getString("name");
                                            String id_state = jsonObject1.getString("id_state");
                                            int idzone = jsonObject1.getInt("id_zone");
                                            CustomCountries customCountries = new CustomCountries(country_name, id_state, idzone);
                                            citiesList.add(customCountries);
                                            citiesnames.add(country_name);
                                        }
                                        customadapt = new ArrayAdapter(CheckOutActivity.this, android.R.layout.simple_spinner_dropdown_item, citiesnames);
                                        citySpinner.setCustomAdapter(customadapt);
                                        citySpinner.setOnItemChosenListener(new LabelledSpinner.OnItemChosenListener() {
                                            @Override
                                            public void onItemChosen(View labelledSpinner, AdapterView<?> adapterView, View itemView, int position, final long id) {
                                                final String city_name = citiesList.get(position).getCountry_name();
                                                final String id_state = citiesList.get(position).getId_country();
                                                final int zone_name = citiesList.get(position).getIdzone();
                                                btn_save_address.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {

                                                        final String mobile = mobile_edittext.getText().toString();
                                                        final String address = address_editext.getText().toString();
                                                        final String firstname = fristName.getText().toString();
                                                        final String lastname = Last_Name.getText().toString();
                                                        String locationtype = locationSpinner.getSpinner().getSelectedItem().toString();
                                                        if (connectionDetector.isConnectingToInternet()) {
                                                            if (validate()) {
                                                                connection = new Connection(CheckOutActivity.this, "/AddAddress", "Post");
                                                                connection.reset();
                                                                connection.addParmmter("id_country", finalId_country + "");
                                                                connection.addParmmter("id_state", id_state);
                                                                connection.addParmmter("id_customer", customer_id);
                                                                connection.addParmmter("alias", locationtype);
                                                                connection.addParmmter("lastname", lastname);
                                                                connection.addParmmter("firstname", firstname);
                                                                connection.addParmmter("address1", address);
                                                                connection.addParmmter("city", city_name);
                                                                connection.addParmmter("phone_mobile", mobile);
                                                                connection.Connect(new Connection.Result() {
                                                                    @Override
                                                                    public void data(String str) throws JSONException {
                                                                        try {


                                                                            JSONObject jsonObject = new JSONObject(str);
                                                                            JSONArray jsonArray = jsonObject.getJSONArray("id_address");
                                                                            String idaddress = jsonArray.getJSONObject(0).getString("id_address");
                                                                            Log.d("checkaddress", str);

                                                                            // Toast.makeText(CheckOutActivity.this,str,Toast.LENGTH_LONG).show();
                                                                            String frag = getIntent().getExtras().getString("frag", "x");
                                                                            if (frag.equals("frag")) {
                                                                                Intent intent = new Intent(CheckOutActivity.this, Add_Address.class);
                                                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                                                intent.putExtra("frag", frag);
                                                                                finish();
                                                                                startActivity(intent);
                                                                                overridePendingTransition(R.anim.no_thing, R.anim.slide_out_left);
                                                                            } else {
                                                                                Intent intent = new Intent(CheckOutActivity.this, Final_check.class);
                                                                                intent.putExtra("address", address + "\t ," + city_name);
                                                                                intent.putExtra("name", firstname + "\t" + lastname);
                                                                                intent.putExtra("mob", mobile);
                                                                                intent.putExtra("id_zone", zone_name + "");
                                                                                intent.putExtra("id_add", idaddress);// ta3del

                                                                                finish();
                                                                                startActivity(intent);
                                                                                overridePendingTransition(R.anim.no_thing, R.anim.slide_out_left);
                                                                            }

                                                                        } catch (Exception e) {
                                                                            String frag = getIntent().getExtras().getString("frag", "x");
                                                                            Toast.makeText(CheckOutActivity.this, str, Toast.LENGTH_LONG).show();
                                                                            Intent intent = new Intent(CheckOutActivity.this, Add_Address.class);
                                                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                                            intent.putExtra("frag", frag);
                                                                            finish();
                                                                            startActivity(intent);
                                                                            overridePendingTransition(R.anim.no_thing, R.anim.slide_out_left);
                                                                        }
                                                                    }
                                                                });


                                                            }
                                                        }


                                                    }

                                                });
                                            }

                                            @Override
                                            public void onNothingChosen(View labelledSpinner, AdapterView<?> adapterView) {

                                            }
                                        });
                                    }
                                });
                            }

                            @Override
                            public void onNothingChosen(View labelledSpinner, AdapterView<?> adapterView) {

                            }
                        });


                    }
                });
            } catch (Exception e) {
            }

        }


//        _loginLink.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Finish the registration screen and return to the Login activity
//                finish();
//            }
//        });
    }

    public boolean signup() {
        Log.d("TAG", "Signup");

        if (!validate()) {
            //onSignupFailed();
            return false;
        }

        btn_save_address.setEnabled(false);

//        final ProgressDialog progressDialog = new ProgressDialog(CheckOutActivity.this,
//                R.style.AppTheme_Dark_Dialog);
//        progressDialog.setIndeterminate(true);
//        progressDialog.setMessage("Creating Account...");
//        progressDialog.show();


        // TODO: Implement your own signup logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        onSignupSuccess();
                        // onSignupFailed();
                        //progressDialog.dismiss();
                    }
                }, 3000);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.no_thing, R.anim.left_to_right);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
        }
        return (super.onOptionsItemSelected(item));
    }

    public boolean validate() {
        boolean valid = true;

        String name = fristName.getText().toString();
        String Last_Name1 = Last_Name.getText().toString();
//        String street1 = street.getText().toString();
//
        if (name.isEmpty() || name.length() <= 2 || name.length() > 15) {
            fristName.setError("between 2 and 15 alphanumeric characters");
            valid = false;
        } else {
            fristName.setError(null);
        }

        if (Last_Name1.isEmpty() || Last_Name1.length() <= 2 || Last_Name1.length() > 15) {
            Last_Name.setError("between 2 and 15 alphanumeric characters");
            valid = false;
        } else {
            Last_Name.setError(null);
        }

//
        if (address_editext.getText().toString().isEmpty() || address_editext.length() < 4 || address_editext.getText().toString().length() > 70) {
            address_editext.setError("at least 4 alphanumeric characters");
            valid = false;
        } else {
            address_editext.setError(null);
        }

        if (mobile_edittext.getText().toString().isEmpty() || mobile_edittext.getText().toString().length() < 5 || mobile_edittext.getText().toString().length() > 15) {
            mobile_edittext.setError("between 5 and 15 alphanumeric characters");
            valid = false;
        } else {
            mobile_edittext.setError(null);
        }
        return valid;
    }

    public void onSignupSuccess() {
        btn_save_address.setEnabled(true);
        Intent intent = new Intent(CheckOutActivity.this, PaymentOption.class);
        finish();
        startActivity(intent);
        overridePendingTransition(R.anim.no_thing, R.anim.slide_out_left);

    }

    //    public void onSignupFailed() {
//        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
//        btn_save_address.setEnabled(true);
//    }
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
            System.out.println("TOuch outside the dialog ******************** ");
            dialog.dismiss();
        }
        return false;
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case 1:
                dialog = new Dialog(this, R.style.CircularProgress);
                dialog = new Dialog(this, R.style.CircularProgress);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.layout_loading_item);
                dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);

                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(true);

                LinearLayout relativeLayout;
                relativeLayout = (LinearLayout) dialog.findViewById(R.id.rel_loder);
                relativeLayout.setOnTouchListener(new View.OnTouchListener() {
                    public boolean onTouch(View view, MotionEvent event) {
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                // The user just touched the screen
                                //   Toast.makeText(SplashActivity.this,"ended", Toast.LENGTH_SHORT).show();

                                dialog.dismiss();
                                break;
                            case MotionEvent.ACTION_UP:
                                // The touch just ended
                                // Toast.makeText(SplashActivity.this,"ended", Toast.LENGTH_SHORT).show();

                                break;
                        }

                        return false;
                    }
                });


                return dialog;
            default:
                return null;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    private void set_map(Bundle savedInstanceState) {

        mapView = (CustomMapView) findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);
        // Check if we were successful in obtaining the map.


        ViewGroup.LayoutParams layoutParams = mapView.getLayoutParams();
        layoutParams.height = height / 2;
        mapView.setLayoutParams(layoutParams);


        // Gets to GoogleMap from the MapView and does initialization stuff
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(final GoogleMap map) {
                googleMap = map;
                googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                //buildGoogleApiClient();
                // googleMap.setMyLocationEnabled(true);

                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    Log.d("checkfen", "hna3");

                    if (ContextCompat.checkSelfPermission(CheckOutActivity.this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        buildGoogleApiClient();
                        Log.d("checkfen", "hna1");
                    }
                }else {
                    Log.d("checkfen", "hna2");

                    buildGoogleApiClient();
                }


                if (ActivityCompat.checkSelfPermission(CheckOutActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(CheckOutActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                  //  Toast.makeText(CheckOutActivity.this, "hna", Toast.LENGTH_SHORT).show();
                    //     map.setMyLocationEnabled(true);


                    // Check if we were successful in obtaining the map.


//                    LatLng sydney = new LatLng(30.011587, 31.329695);
//                      markerOptions=new MarkerOptions().position(sydney)
//                            .title("Marker in Sydney").draggable(true);
//                      marker=map.addMarker(markerOptions);
//                    map.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
//
//                        @Override
//                        public void onMarkerDragStart(Marker marker) {
//                            Toast.makeText(CheckOutActivity.this,"ha2",Toast.LENGTH_SHORT).show();
//
//                        }
//
//                        @Override
//                        public void onMarkerDragEnd(Marker marker) {
//                            Log.d("latitude : ",marker.getPosition().latitude+"");
//                            marker.setSnippet(marker.getPosition().latitude+"");
//                            map.animateCamera(CameraUpdateFactory.newLatLng(marker.getPosition()));
//                            Toast.makeText(CheckOutActivity.this,"ha",Toast.LENGTH_SHORT).show();
//
//                        }
//
//                        @Override
//                        public void onMarkerDrag(Marker marker) {
//                            Toast.makeText(CheckOutActivity.this,"ha1",Toast.LENGTH_SHORT).show();
//
//                        }
//
//                    });
//
//                    map.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
//                        @Override
//                        public void onMyLocationChange(Location location) {
//                          Toast.makeText(CheckOutActivity.this,"done",Toast.LENGTH_SHORT).show();
//
//                            double latitude = location.getLatitude();
//
//                            // Getting longitude of the current location
//                            double longitude =location.getLongitude();
//
//                            // Creating a LatLng object for the current location
//                            LatLng latLng = new LatLng(latitude, longitude); //your_text_view.settext(latitude+","+longtitudde)
//
//                            // Showing the current location in Google Map
//                            map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//                            map.addMarker(new MarkerOptions().position(latLng).title("Marker in"+location));
//
//                            // Zoom in the Google Map
//                            map.animateCamera(CameraUpdateFactory.zoomTo(20));
//
//                        }
//                    });
                    //   map.moveCamera(CameraUpdateFactory.newLatLng(sydney));


                    //invalidate the map in order to show changes
                    mapView.invalidate();
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                //     map.setMyLocationEnabled(true);

                googleMap.setTrafficEnabled(true);
                googleMap.setIndoorEnabled(true);
                googleMap.setBuildingsEnabled(true);
                googleMap.getUiSettings().setZoomControlsEnabled(true);
                MapsInitializer.initialize(CheckOutActivity.this);

//        // Updates the location and zoom of the MapView
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(43.1, -87.9), 10);
                map.animateCamera(cameraUpdate);
            }
        });
//        map.getUiSettings().setMyLocationButtonEnabled(false);
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        map.setMyLocationEnabled(true);
//
//        // Needs to call MapsInitializer before doing any CameraUpdateFactory calls
//        MapsInitializer.initialize(this);
//
////        // Updates the location and zoom of the MapView
//        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(43.1, -87.9), 10);
//        map.animateCamera(cameraUpdate);
    }

    protected synchronized void buildGoogleApiClient() {
       // Toast.makeText(this, "build", Toast.LENGTH_SHORT).show();
        Log.d("checkconn", "build");
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
     //   Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();
        Log.d("checkconn", "connected");
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
       // Toast.makeText(this, "failed", Toast.LENGTH_SHORT).show();
        Log.d("checkconn", "failed");
    }

    @Override
    public void onLocationChanged(Location location) {

        Log.d("loaction", "connected");
//        googleMap.setOnCameraIdleListener(CheckOutActivity.this);
//        googleMap.setOnCameraMoveStartedListener(CheckOutActivity.this);
//        googleMap.setOnCameraMoveListener(CheckOutActivity.this);
//        googleMap.setOnCameraMoveCanceledListener(CheckOutActivity.this);


        mLastLocation = location;
        mLastLocation.getAccuracy();
        Log.d("loaction", mLastLocation+"");
        if (marker != null) {
            marker.remove();
        }
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(final LatLng latLng) {
             //   Toast.makeText(CheckOutActivity.this, "ha3", Toast.LENGTH_SHORT).show();
                if (marker == null) {
                    marker = googleMap.addMarker(new MarkerOptions().position(latLng));
                } else {
                    Log.d("checklat", latLng.toString());
                    marker.setPosition(latLng);
                    circle.setCenter(latLng);



                    try {
                        get_data(latLng.latitude,latLng.longitude);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
        //Place current location marker
//        Handler main=new Handler();
//        main.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        },1000);
        latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.draggable(true);
        marker = googleMap.addMarker(markerOptions);
        circle=googleMap.addCircle( new CircleOptions().center(latLng)
                .radius(500)
                .strokeWidth(2)
                .strokeColor(Color.BLUE)
                .fillColor(Color.parseColor("#500084d3")));
//        Handler main=new Handler();
//        main.post(new Runnable() {
//            @Override
//            public void run() {
//                googleMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
//                    @Override
//                    public void onCameraMove() {
//                        if (marker == null) {
//                                    marker = googleMap.addMarker(new MarkerOptions().position(latLng));
//                                } else {
//                                    Log.d("checklat", latLng.toString());
//
//
//                                    marker.setPosition(latLng);
//                                    circle.setCenter(latLng);
//
//                                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//                                    googleMap.animateCamera(CameraUpdateFactory.zoomTo(11));
//
//                                    try {
//                                        get_data(latLng.latitude,latLng.longitude);
//                                    } catch (IOException e) {
//                                        e.printStackTrace();
//                                    }
//
//                                }
//
//                            }
//                });
//
//            }
//        });


        //move map camera
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(11));
        try {
            get_data(latLng.latitude,latLng.longitude);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
//        googleMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
//
//            @Override
//            public void onMarkerDrag(Marker marker) {
//            Toast.makeText(CheckOutActivity.this,"done1",Toast.LENGTH_SHORT).show();
//            }
//            @Override
//            public void onMarkerDragEnd(Marker marker) {
//                LatLng newLocation = marker.getPosition();
////                mLocation.setLatitude(newLocation.latitude);
////                mLocation.setLongitude(newLocation.longitude);
////                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLocation.getLatitude(), mLocation.getLongitude()), 15.0f));
//                Toast.makeText(CheckOutActivity.this,"done3",Toast.LENGTH_SHORT).show();
//
//            }
//            @Override
//            public void onMarkerDragStart(Marker marker) {
//                Toast.makeText(CheckOutActivity.this,"done2",Toast.LENGTH_SHORT).show();
//
//            }
//
//        });


    }
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public boolean checkLocationPermission(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
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
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // Permission was granted.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        //  googleMap.setMyLocationEnabled(true);
                    }

                } else {

                    // Permission denied, Disable the functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other permissions this app might request.
            //You can add here other case statements according to your requirement.
        }
    }
    private void get_data(Double latitude, Double longitude) throws IOException {
        try {
            List<Address> addresses;
            geocoder = new Geocoder(this, Locale.getDefault());

            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            Log.d("checkfinaldata", addresses + "");
            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getAddressLine(3);
            String state = addresses.get(0).getAddressLine(1) + "\t" + addresses.get(0).getAddressLine(2);
            String country = addresses.get(0).getCountryName();
            String knownName = addresses.get(0).getFeatureName();
            Log.d("checkfinaldata", address + "\n" + city + "\n" + state + "\n" + country + "\n" + knownName);

            address_editext.setText(address + "\t" + state + "\t" );
        }catch (Exception e){}
    }

    @Override
    public void onCameraIdle() {
//        Toast.makeText(this, "The camera has stopped moving.",
//                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCameraMoveCanceled() {
//        Toast.makeText(this, "Camera movement canceled.",
//                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCameraMove() {
        Log.d("checkcamera","user still moving map");

    }

    @Override
    public void onCameraMoveStarted(int reason) {
        if (reason == GoogleMap.OnCameraMoveStartedListener.REASON_GESTURE) {
            Toast.makeText(this, "The user gestured on the map.",
                    Toast.LENGTH_SHORT).show();
        } else if (reason == GoogleMap.OnCameraMoveStartedListener
                .REASON_API_ANIMATION) {
            Toast.makeText(this, "The user tapped something on the map.",
                    Toast.LENGTH_SHORT).show();
        } else if (reason == GoogleMap.OnCameraMoveStartedListener
                .REASON_DEVELOPER_ANIMATION) {
            Toast.makeText(this, "The app moved the camera.",
                    Toast.LENGTH_SHORT).show();
        }

    }


}

