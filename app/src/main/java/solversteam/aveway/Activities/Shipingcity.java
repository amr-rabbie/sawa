package solversteam.aveway.Activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

import com.satsuware.usefulviews.LabelledSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import solversteam.aveway.Adapters.Shippingadapter;
import solversteam.aveway.Connection.Connection;
import solversteam.aveway.Connection.ConnectionDetector;
import solversteam.aveway.Models.CustomCountries;
import solversteam.aveway.utiltes.ExpandableHeightGridView;
import solverteam.aveway.R;

/**
 * Created by mosta on 3/18/2017.
 */

public class Shipingcity extends AppCompatActivity {
    @BindView(R.id.include)
    Toolbar toolbar;
    private LabelledSpinner shipSpinner;
    private Connection connection;
    private ConnectionDetector connectionDetector;
    private ArrayList<CustomCountries> countriesList,citiesList;
    private ArrayAdapter customadapt;
    private SharedPreferences sharedPreferences;
    private ArrayList<String>countriesnames,citiesnames,price;
    private int country,currency,language;
    private String customer_id,namefirst,namelast,mob,firstcolor;
    private Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shiping_option);
        ButterKnife.bind(this);
        sharedPreferences = getSharedPreferences("LanguageAndCountry", Context.MODE_PRIVATE);
        firstcolor=sharedPreferences.getString("firstmenucolour","#000000");
        toolbar.setBackgroundColor(Color.parseColor(firstcolor));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.addship);
        price = new ArrayList<>();
        countriesnames = new ArrayList<>();
        countriesList = new ArrayList<>();
        citiesList = new ArrayList<>();
        country = sharedPreferences.getInt("Country", 1);
        final String currencyname = sharedPreferences.getString("currencyname", "EGB");
        language = sharedPreferences.getInt("Lang", 1);
        customer_id = sharedPreferences.getString("customer_id", "53");
        namefirst = sharedPreferences.getString("name", "");
        namelast = sharedPreferences.getString("lastname", "");
        mob = sharedPreferences.getString("mobile", "");
//        shipSpinner=(LabelledSpinner)findViewById(R.id.shipping_spinner) ;
//
//
//        connection=new Connection(this,"/GetAllCustomerCountryZones/"+country+"/"+language,"Get");
//        connection.reset();
//        connection.Connect(new Connection.Result() {
//            @Override
//            public void data(String str) throws JSONException {
//                JSONObject jsonObject = new JSONObject(str);
//                Log.d("str",str);
//                JSONArray jsonArray = jsonObject.getJSONArray("1-countries");
//                for (int position = 0; position < jsonArray.length(); position++) {
//                    JSONObject jsonObject1 = jsonArray.getJSONObject(position);
//                    String country_name = jsonObject1.getString("name");
//                    String id_country = jsonObject1.getString("id_zone");
//                    String prices = jsonObject1.getString("price");
//                 //   CustomCountries customCountries = new CustomCountries(country_name, id_country);
//                //  countriesList.add(customCountries);
//                    countriesnames.add(country_name);
//                    price.add(prices);
//                }
//                customadapt = new ArrayAdapter(Shipingcity.this, android.R.layout.simple_spinner_dropdown_item, countriesnames);
//                shipSpinner.setCustomAdapter(customadapt);
//                shipSpinner.setOnItemChosenListener(new LabelledSpinner.OnItemChosenListener() {
//                    @Override
//                    public void onItemChosen(View labelledSpinner, AdapterView<?> adapterView, View itemView, int position, long id) {
//
//                        final String id_country=countriesList.get(position).getId_country();
//                        final String pricee=price.get(position) ;
//                        Log.d("hi",id_country+"\n"+pricee);
//                        connection=new Connection(Shipingcity.this,"/GetAllCountryZoneCarriers/"+country+"/"+id_country+"/"+language,"Get");
//                        connection.reset();
//                        connection.Connect(new Connection.Result() {
//                            @Override
//                            public void data(String str) throws JSONException {
//                                JSONObject jsonObject = new JSONObject(str);
//                                Log.d("str",str);
//                                citiesList=new ArrayList<>();
//                                citiesnames=new ArrayList<>();
//                                JSONArray jsonArray = jsonObject.getJSONArray("1-CountryZoneCarriers");
//                                for (int position = 0; position < jsonArray.length(); position++) {
//                                    JSONObject jsonObject1 = jsonArray.getJSONObject(position);
//                                    String country_name = jsonObject1.getString("name");
//                                    String id_country = jsonObject1.getString("id_carrier");
//                                     CustomCountries customCountries = new CustomCountries(country_name, id_country);
//                                    citiesList.add(position,customCountries);
//                                    citiesnames.add(country_name);
//                                 }
//                                Log.d("hty",id_country+"\n"+pricee+"\n"+citiesList.size() +"\n"+countriesList.get(0).getId_country());
//
//                                ExpandableHeightGridView mygrid2 = (ExpandableHeightGridView) findViewById(R.id.list_view);
//                                mygrid2.setAdapter(new Shippingadapter(Shipingcity.this,citiesList,pricee,currencyname));
//
//                            }});
//
//                    }
//
//                    @Override
//                    public void onNothingChosen(View labelledSpinner, AdapterView<?> adapterView) {
//
//                    }
//                });
//
//            }});
//
//    }


        String id_zone=getIntent().getExtras().getString("id_zone");

        connection=new Connection(Shipingcity.this,"/GetAllCountryZoneCarriers/"+country+"/"+id_zone+"/"+language,"Get");
        connection.reset();
        connection.Connect(new Connection.Result() {
            @Override
            public void data(String str) throws JSONException {
                JSONObject jsonObject = new JSONObject(str);
                Log.d("str",str);
                citiesList=new ArrayList<>();
                citiesnames=new ArrayList<>();
                JSONArray jsonArray = jsonObject.getJSONArray("1-CountryZoneCarriers");
                for (int position = 0; position < jsonArray.length(); position++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(position);
                    String country_name = jsonObject1.getString("name");
                    String id_country = jsonObject1.getString("id_carrier");
                    String imag = jsonObject1.getString("imageurl");
                    String delay = jsonObject1.getString("delay");
                    int price = jsonObject1.getInt("price");
                    CustomCountries customCountries = new CustomCountries(country_name, id_country,price+"",imag,delay);
                    citiesList.add(position,customCountries);
                    citiesnames.add(country_name);
                }
                //  Log.d("hty",id_country+"\n"+pricee+"\n"+citiesList.size() +"\n"+countriesList.get(0).getId_country());

                ExpandableHeightGridView mygrid2 = (ExpandableHeightGridView) findViewById(R.id.list_view);
                mygrid2.setAdapter(new Shippingadapter(Shipingcity.this,citiesList,50+"",currencyname));

            }});

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, Final_check.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP );
        intent.putExtra("id_add",getIntent().getExtras().getString("id_add"));
        intent.putExtra("id_zone",getIntent().getExtras().getString("id_zone"));
        intent.putExtra("address",getIntent().getExtras().getString("address"));
        intent.putExtra("name",getIntent().getExtras().getString("name"));
        intent.putExtra("mob",getIntent().getExtras().getString("mob"));
         finish();
        startActivity(intent);
        overridePendingTransition(R.anim.no_thing, R.anim.left_to_right);
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
                relativeLayout=(LinearLayout) dialog.findViewById(R.id.rel_loder);
                relativeLayout.setOnTouchListener(new View.OnTouchListener() {
                    public boolean onTouch(View view, MotionEvent event) {
                        switch(event.getAction()) {
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


}
