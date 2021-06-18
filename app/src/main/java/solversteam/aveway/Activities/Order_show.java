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
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import solversteam.aveway.Adapters.OrderAdapter;
import solversteam.aveway.Connection.Connection;
import solversteam.aveway.Models.Order_model;
import solversteam.aveway.utiltes.ExpandableHeightGridView;
import solverteam.aveway.R;

/**
 * Created by mosta on 3/19/2017.
 */

public class Order_show extends AppCompatActivity {

    @BindView(R.id.include)
    Toolbar toolbar;
   private int language,currency,country;
    private String currencyname,id_customer,name_custome,mobilecustomer,firstcolor;
    private ArrayList<Order_model> list;
    private SharedPreferences sharedPreferences;
    private Connection connection;
    Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_history);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        sharedPreferences=getSharedPreferences("LanguageAndCountry", Context.MODE_PRIVATE);
        firstcolor=sharedPreferences.getString("firstmenucolour","#000000");
        toolbar.setBackgroundColor(Color.parseColor(firstcolor));
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.myorder);
        list=new ArrayList<>();
        ScrollView scrollView = (ScrollView) findViewById(R.id.scroll);
        scrollView.setFocusableInTouchMode(true);
        scrollView.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);

        country=sharedPreferences.getInt("Country",1);
        currency=Integer.parseInt(sharedPreferences.getString("currency","1"));
        language=sharedPreferences.getInt("Lang",1);
        currencyname=sharedPreferences.getString("currencyname","EGB");
        id_customer=sharedPreferences.getString("customer_id","53");
        final String customername = sharedPreferences.getString("name", "");

        try {
            connection = new Connection(this, "/GetAllCustomerOrderHistory/" + country + "/"+ id_customer + "/" + language, "Get");
            Log.d("custid", id_customer + "");
       //     Toast.makeText(Order_show.this,id_customer,Toast.LENGTH_LONG).show();
            connection.reset();
            connection.Connect(new Connection.Result() {
                @Override
                public void data(String str) throws JSONException {
                    JSONObject jsonObject = new JSONObject(str);
                    JSONArray jsonArray = jsonObject.getJSONArray("1-orderhistory");
                    list=new ArrayList<>();
                    for (int Counter = jsonArray.length()-1; Counter >= 0; Counter--) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(Counter);
//                 //       Toast.makeText(Order_show.this,jsonObject1.getString("total_paid")+"\n"+jsonObject1.getString("order_payment_type")
//                                +"\n"+jsonObject1.getString("order_status")+"\n"+jsonObject1.getString("id_order"),Toast.LENGTH_LONG).show();
                        Order_model add =new Order_model(
                                jsonObject1.getString("firstname")+"\t"+jsonObject1.getString("lastname")
                                ,jsonObject1.getString("id_order_state")
                                ,jsonObject1.getString("date_add")
                                ,jsonObject1.getString("total_paid")
                                , ""
                                , ""
                                ,jsonObject1.getString("order_payment_type")
                                ,jsonObject1.getString("order_status")
                                ,jsonObject1.getString("id_order"));


                        list.add(add);
                    }
                    ExpandableHeightGridView listv = (ExpandableHeightGridView) findViewById(R.id.list_view);
                    listv.setAdapter(new OrderAdapter(Order_show.this,currencyname,list,customername,id_customer,language,country));

                }
            });
        }
        catch (Exception e){}




    }
    public boolean onTouchEvent(MotionEvent event)
    {

        if(event.getAction() == MotionEvent.ACTION_OUTSIDE){
            System.out.println("TOuch outside the dialog ******************** ");
            dialog.dismiss();
        }
        return false;
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
