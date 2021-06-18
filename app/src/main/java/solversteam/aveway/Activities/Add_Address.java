package solversteam.aveway.Activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import solversteam.aveway.Adapters.Address_adapter;
import solversteam.aveway.Connection.Connection;
import solversteam.aveway.Models.Address_model;
import solverteam.aveway.R;

/**
 * Created by mosta on 3/16/2017.
 */

public class  Add_Address extends AppCompatActivity {
    @BindView(R.id.include)
    Toolbar toolbar;
    private ListView list;
    FloatingActionButton fab;
    private String id_customer,firstcolor;
    int language;
    ArrayList<Address_model> adress;
    String frag="x";
    private SharedPreferences sharedPreferences;
    private Connection connection;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_address);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        sharedPreferences=getSharedPreferences("LanguageAndCountry", Context.MODE_PRIVATE);
        language=sharedPreferences.getInt("Lang",1);
        id_customer=sharedPreferences.getString("customer_id","53");
        firstcolor=sharedPreferences.getString("firstmenucolour","#000000");
        toolbar.setBackgroundColor(Color.parseColor(firstcolor));
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.addaddress);

        list=(ListView)findViewById(R.id.list);
        fab =(FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(getApplicationContext(),CheckOutActivity.class);
               // i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                i.putExtra("frag",getIntent().getExtras().getString("frag"));
                startActivity(i);
            }
        });

        adress=new ArrayList<>();


        try {
            connection = new Connection(this, "/GetAllCustomerAddresses/" + id_customer + "/" + language, "Get");
            Log.d("custid", id_customer + "");
            connection.reset();
            connection.Connect(new Connection.Result() {
                @Override
                public void data(String str) throws JSONException {
                    JSONObject jsonObject = new JSONObject(str);
                    JSONArray jsonArray = jsonObject.getJSONArray("1-addresses");
                    for (int Counter = 0; Counter < jsonArray.length(); Counter++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(Counter);
                        Address_model add =new Address_model((jsonObject1.getString("firstname")+" "+jsonObject1.getString("lastname"))
                                ,jsonObject1.getString("city")
                                ,jsonObject1.getString("address1")
                                ,jsonObject1.getString("phone_mobile")
                                ,jsonObject1.getString("id_address")
                                ,jsonObject1.getString("id_zone"));

                        adress.add(add);
                    }
                    list.setAdapter(new Address_adapter(Add_Address.this,adress,getIntent().getExtras().getString("frag")));

                }
            });
        }
        catch (Exception e){}



    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
        }
        return (super.onOptionsItemSelected(item));
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
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
