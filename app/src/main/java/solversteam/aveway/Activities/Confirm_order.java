package solversteam.aveway.Activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import solversteam.aveway.Connection.Connection;
import solversteam.aveway.Connection.ConnectionDetector;
import solversteam.aveway.Models.CartCLass;
import solversteam.aveway.Models.ChartModel;
import solversteam.aveway.Models.TestModel;
import solversteam.aveway.utiltes.Fonts;
import solverteam.aveway.R;

/**
 * Created by mosta on 4/21/2017.
 */

public class Confirm_order extends AppCompatActivity {
    private TextView totalmoney_textview,order_date,order_to,shipping_prder,order_adress,shipping_date,delverdate,subtotal;
    private Spinner estmatedate,delver_time;
    private Button home,cancel;
    private String totalmoney,orderid,defult,delvery,firstcolor;
    @BindView(R.id.include)
    Toolbar toolbar;
    private int delay1,delay2;

    private Connection connection;
    private ConnectionDetector connectionDetector;
    private Intent intent;
    private ArrayList<ChartModel> chartModels;
    private SharedPreferences preferences;
    private String currencyname,date_delvery,times_delvery;
     private Dialog dialog;
    private ArrayList<CartCLass>cartList;
    private int country,language,currency,id_customer;
    private FirebaseAnalytics mFirebaseAnalytics;
    private ArrayAdapter customadapt,customadapt1;
    private  boolean key=false;
    String delverys="";
    ArrayList<String> dates,time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirm_order);
        ButterKnife.bind(this);
        Fonts fonts=new Fonts(this);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        try {
            dates = new ArrayList<>();
            time = new ArrayList<>();
            time = TestModel.getTime();
            dates = TestModel.getStartshippingat();
            //  Log.d("lmk",dates.get(0)+"\n"+time.get(0));
        }catch (Exception e){}
        preferences = getSharedPreferences("LanguageAndCountry", MODE_PRIVATE);
        cartList= TestModel.getCartList();
        preferences=getSharedPreferences("LanguageAndCountry", Context.MODE_PRIVATE);
        firstcolor=preferences.getString("firstmenucolour","#000000");
        toolbar.setBackgroundColor(Color.parseColor(firstcolor));
        country=preferences.getInt("Country",1);
        currency=Integer.parseInt(preferences.getString("currency","1"));
        language=preferences.getInt("Lang",1);
        id_customer=Integer.parseInt(preferences.getString("customer_id","53"));
        connectionDetector=new ConnectionDetector(this);
        currencyname= preferences.getString("currencyname","EGP");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView tx =(TextView)toolbar.findViewById(R.id.title);
        getSupportActionBar().setTitle(R.string.conf_order);

        delvery="";
        try {
            for (int i = 0; i < cartList.size(); i++) {
                delvery = delvery + cartList.get(i).getId_product() + "#" + cartList.get(i).getQuantity() +
                        "#" + cartList.get(i).getName() + "#" + cartList.get(i).getPrice();
                if (i != cartList.size() - 1)
                    delvery = delvery + "##";

            }
            Log.d("fmf", delvery + "");
        }catch (Exception e){
            Log.e("errorf",e.toString());
        }

       // totalmoney_textview,order_date,order_to,shipping_prder
        subtotal=(TextView)findViewById(R.id.price);
        totalmoney_textview=(TextView)findViewById(R.id.totalprice);
        shipping_date=(TextView)findViewById(R.id.shipping_date);
        delverdate=(TextView)findViewById(R.id.delverdate);
        order_date=(TextView)findViewById(R.id.order_date);
        order_to=(TextView)findViewById(R.id.order_name);
        shipping_prder=(TextView)findViewById(R.id.shipping);
        order_adress=(TextView) findViewById(R.id.order_adress);
        estmatedate=(Spinner) findViewById(R.id.estmatedate);
        delver_time=(Spinner) findViewById(R.id.time_delvery);
        home=(Button)findViewById(R.id.confirm_btn);
        cancel=(Button)findViewById(R.id.cancel_btn);
        home.setBackgroundColor(Color.parseColor(firstcolor));
        cancel.setBackgroundColor(Color.parseColor(firstcolor));
        fonts.setView(home);
        fonts.setView(cancel);
        Date d = new Date();
        final CharSequence date  = DateFormat.format("yyyy-MM-dd ", d.getTime());

       String str=getIntent().getExtras().getString("delay");
//        str = str.replaceAll("\\D+",",");
//        final String[] date_prder = str.split(",");

        try {
//            delay1=Integer.parseInt(date_prder[0]);
//            delay2=Integer.parseInt(date_prder[1]);
//              delverys=getdate(date.toString(),delay1 ,delay2);
//            delverdate.setText(delvery);
        }catch (Exception e){}
        try {


            Double total=Double.parseDouble(getIntent().getExtras().getString("shippingprice"))+Double.parseDouble(cartList.get(0).getTotalmoney());
            Log.d("total",total+"");


            order_date.setText(date.toString());
            order_to.setText(getIntent().getExtras().getString("name"));
            shipping_prder.setText(getIntent().getExtras().getString("shippingprice")+"\t"+currencyname);
            subtotal.setText(cartList.get(0).getTotalmoney()+"\t"+currencyname);
            shipping_date.setText(getIntent().getExtras().getString("delay"));
            totalmoney_textview.setText(total+"\t"+currencyname);
            order_adress.setText(getIntent().getExtras().getString("address"));

            customadapt = new ArrayAdapter(Confirm_order.this, android.R.layout.simple_list_item_1, dates);
            estmatedate.setAdapter(customadapt);
            customadapt1 = new ArrayAdapter(Confirm_order.this, android.R.layout.simple_list_item_1, time);
            delver_time.setAdapter(customadapt1);
            estmatedate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    Log.d("choose",dates.get(i));
                    try{
                        key =true;
                        date_delvery=dates.get(i);
  }catch (Exception e){}

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            try {


            delver_time.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    Log.d("choose",time.get(i));
                    try{
                        key =true;
                        times_delvery=time.get(i);
                    }catch (Exception e){}

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });}catch (Exception e){Log.d("re",e.toString());}

        }catch (Exception e){
            Log.d("error",e+"");
        }

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Confirm_order.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                overridePendingTransition(R.anim.no_thing, R.anim.left_to_right);
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(key){
                if(connectionDetector.isConnectingToInternet())
                {
                    try {
                        analtyic();
//
                        Double total=Double.parseDouble(getIntent().getExtras().getString("shippingprice"))+Double.parseDouble(cartList.get(0).getTotalmoney());
                        Log.d("total",total+"");

                        Log.d("carrier",getIntent().getExtras().getString("id_carrier") + "");
                        connection = new Connection(Confirm_order.this, "/AddOrder", "Post");
                        connection.reset();
                        connection.addParmmter("id_shop", country + "");
                        connection.addParmmter("id_lang", language + "");
                        connection.addParmmter("id_currency", currency + "");
                        connection.addParmmter("id_customer", id_customer + "");
                        connection.addParmmter("id_carrier", getIntent().getExtras().getString("id_carrier") + "");
                        connection.addParmmter("cart_product", delvery + "");
                        connection.addParmmter("id_address_delivery", getIntent().getExtras().getString("id_add") + "");//
                        connection.addParmmter("id_address_invoice",getIntent().getExtras().getString("id_add") + "");//
                        connection.addParmmter("total_shipping_tax_incl ", getIntent().getExtras().getString("shippingprice") + "");
                        connection.addParmmter("total_shipping_tax_excl ", getIntent().getExtras().getString("shippingprice") + "");
                        connection.addParmmter("total_paid",  total + "");
                        connection.addParmmter("total_paid_tax_incl",  cartList.get(0).getTotalmoney() + "");
                        connection.addParmmter("total_paid_tax_excl",  cartList.get(0).getTotalmoney() + "");
                        connection.addParmmter("startshippingat", date_delvery+ "");
                        connection.addParmmter("deliverytime",  times_delvery + "");



                        connection.Connect(new Connection.Result() {
                            @Override
                            public void data(String str) throws JSONException {
                                Log.d("checkresporder", str + "\n" + getIntent().getExtras().getString("id_carrier")
                                        + "\n" + getIntent().getExtras().getString("id_add"));
                                //  Toast.makeText(Confirm_order.this,str,Toast.LENGTH_LONG).show();
                                try {
                                    String order_id = str;
                                    JSONObject jsonObject = new JSONObject(str);
                                    JSONArray jsonArray = jsonObject.getJSONArray("id_order");
                                    String idaddress = jsonArray.getJSONObject(0).getString("id_order");
                                    if(TestModel.getfromcardmodel("card",Confirm_order.this)!=null)
                                    {
                                        chartModels=TestModel.getfromcardmodel("card",Confirm_order.this);
                                        chartModels.clear();
                                        TestModel.savetosharedcardmodel(chartModels,"card",Confirm_order.this);
                                        ArrayList<Integer> num=new ArrayList<Integer>();
                                        TestModel.setNums(num,Confirm_order.this);


                                    }
                                    else {
                                        chartModels=new ArrayList<>();
                                    }
                                    Intent intent = new Intent(Confirm_order.this, OrderMessage.class);
                                    intent.putExtra("order_id", idaddress);
                                    intent.putExtra("startshippingat", date_delvery+"");
                                    finish();
                                    startActivity(intent);
                                    overridePendingTransition(R.anim.no_thing, R.anim.slide_out_left);


                                } catch (Exception e) {
                                    e.printStackTrace();
                                    Log.d("eror",e+"");
                                    Toast.makeText(Confirm_order.this, "your order  is not completed, pleaze try again", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(Confirm_order.this, MainActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    overridePendingTransition(R.anim.no_thing, R.anim.left_to_right);
                                }
                            } });

                    }catch (Exception e)
                    {
                        e.printStackTrace();
//                            Toast.makeText(Confirm_order.this,"your order  is not completed, pleaze try again",Toast.LENGTH_LONG).show();
//                            Intent intent=new Intent(Confirm_order.this,MainActivity.class);
//                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                            startActivity(intent);
//                            overridePendingTransition(R.anim.no_thing, R.anim.left_to_right);
                    }
                }}
//                Intent intent = new Intent(Confirm_order.this , OrderPayment.class);
//                intent.putExtra("pos" , 1);
//                startActivity(intent);
//                overridePendingTransition(R.anim.right_to_left, R.anim.no_thing);
            }
        });


    }

    private String getdate(String dt,int delay1,int delay2){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        Calendar e = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(dt));
            e.setTime(sdf.parse(dt));
        } catch (ParseException ee) {
            ee.printStackTrace();
        }
        c.add(Calendar.DATE, delay1);  // number of days to add, can also use Calendar.DAY_OF_MONTH in place of Calendar.DATE
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        e.add(Calendar.DATE, delay2);  // number of days to add, can also use Calendar.DAY_OF_MONTH in place of Calendar.DATE
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        String output ="from \t"+ sdf1.format(c.getTime())+"\t to \t"+sdf2.format(e.getTime());
        return output;
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
    private void analtyic() {


        try{
            Bundle bundle = new Bundle();
            for(int i=0;i<cartList.size();i++) {
                bundle.putString("product_name", cartList.get(i).getName());
                bundle.putString("product_id",cartList.get(i).getId_product()+"");

                mFirebaseAnalytics.logEvent("most_order_items", bundle);
                mFirebaseAnalytics.setUserProperty("product_name", cartList.get(i).getName() + "");
                mFirebaseAnalytics.setUserProperty("product_id", cartList.get(i).getId_product() + "");
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
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
}

