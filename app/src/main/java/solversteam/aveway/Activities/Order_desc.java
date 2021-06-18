package solversteam.aveway.Activities;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import solversteam.aveway.Adapters.final_checkAdapter;
import solversteam.aveway.Connection.Connection;
import solversteam.aveway.Models.CartCLass;
import solversteam.aveway.Models.Order_model;
import solversteam.aveway.utiltes.ExpandableHeightGridView;
import solverteam.aveway.R;

/**
 * Created by mosta on 3/19/2017.
 */

public class Order_desc extends AppCompatActivity {


    @BindView(R.id.include)
    Toolbar toolbar;
    int language,currency,country;
    private String currencyname,id_customer,name_custome,statusilecustomer,firstcolor;
    ArrayList<Order_model> list;
    private SharedPreferences sharedPreferences;
    private Connection connection;
    private TextView name ,adress_text,status,id,totalprice,totalpriceWship,shipp_text,order_adress,orders_date,shipping_via,shipping_lo,excepected_delivery,tracking_no;
    private Button ship_btn,cancel_btn;
    ArrayList<CartCLass> cartList;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orderdesc);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        sharedPreferences = getSharedPreferences("LanguageAndCountry", Context.MODE_PRIVATE);
        firstcolor = sharedPreferences.getString("firstmenucolour", "#000000");
        toolbar.setBackgroundColor(Color.parseColor(firstcolor));
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.myorder);
        list = new ArrayList<>();
        cartList = new ArrayList<>();

        country = sharedPreferences.getInt("Country", 1);
        currency = Integer.parseInt(sharedPreferences.getString("currency", "1"));
        language = sharedPreferences.getInt("Lang", 1);
        currencyname = sharedPreferences.getString("currencyname", "EGB");
        id_customer = sharedPreferences.getString("customer_id", "53");


        name = (TextView) findViewById(R.id.order_name);
        status = (TextView) findViewById(R.id.status);
        order_adress = (TextView) findViewById(R.id.order_adress);
        totalpriceWship = (TextView) findViewById(R.id.total);
        totalprice = (TextView) findViewById(R.id.subtotal);
        shipp_text = (TextView) findViewById(R.id.shipping);
        id = (TextView) findViewById(R.id.orderid);
        orders_date = (TextView) findViewById(R.id.order_dates);

        name.setText(getIntent().getExtras().getString("name"));
        shipping_lo=(TextView) findViewById(R.id.shipping_lo);
        shipping_via=(TextView) findViewById(R.id.shipping_via);
      excepected_delivery=(TextView) findViewById(R.id.expected_delivery);
        tracking_no=(TextView) findViewById(R.id.tracking_number_value);
        status.setText(getIntent().getExtras().getString("status"));
        ((TextView) findViewById(R.id.type_order)).setText(getIntent().getExtras().getString("type"));
        id.setText(getIntent().getExtras().getString("orderid"));
        String[] date_prder = (getIntent().getExtras().getString("date")).split("T");
        orders_date.setText(date_prder[0]);
        Log.d("dated",date_prder[0]);

        Connection connection = new Connection(this, "/GetAllOrderDetails/" + country + "/" + id_customer +
                "/" + getIntent().getExtras().getString("orderid") + "/" + language, "Get");
        connection.reset();
        connection.Connect(new Connection.Result() {
            JSONObject jsonObject;
            JSONArray jsonArray;

            @Override
            public void data(String str) throws JSONException {
                jsonObject = new JSONObject(str);
                jsonArray = jsonObject.getJSONArray("1-orderhistory");

                cartList = new ArrayList<>();
                for (int Counter = jsonArray.length() - 1; Counter >= 0; Counter--) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(Counter);
                    CartCLass cartCLass = new CartCLass(country, language, currency, id_customer, 0,
                            jsonObject1.getInt("product_quantity"), "0",
                            jsonObject1.getString("product_price"),
                            jsonObject1.getString("product_name"),
                            jsonObject1.getString("url_image"));
                    cartList.add(cartCLass);
                    shipping_lo.setText( jsonObject1.getString("Shipping"));
                    shipping_via.setText( jsonObject1.getString("ShippingCompany"));
                   excepected_delivery.setText( jsonObject1.getString("ExpectedDelivery"));
                   tracking_no.setText( jsonObject1.getString("TrackingNumber"));
                    shipp_text.setText(jsonObject1.getString("ShippingFees") + " " + currencyname);
                }
                ExpandableHeightGridView list = (ExpandableHeightGridView) findViewById(R.id.list_view);
                list.setAdapter(new final_checkAdapter(Order_desc.this, currencyname, cartList));
                Double sub = 0.0;
                for (int i = 0; i < cartList.size(); i++) {
                    sub = sub+(Double.parseDouble(cartList.get(i).getPrice()) * cartList.get(i).getQuantity());
                }
                totalprice.setText(sub + " " + currencyname);
                Double ship = Double.parseDouble(getIntent().getExtras().getString("price")) - sub;

                totalpriceWship.setText(getIntent().getExtras().getString("price")+ " " + currencyname );

            }
        });


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
