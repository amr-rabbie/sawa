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
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import solversteam.aveway.Adapters.final_checkAdapter;
import solversteam.aveway.Models.CartCLass;
import solversteam.aveway.Models.TestModel;
import solversteam.aveway.utiltes.ExpandableHeightGridView;
import solversteam.aveway.utiltes.Fonts;
import solverteam.aveway.R;

/**
 * Created by mosta on 3/16/2017.
 */

public class Final_check extends AppCompatActivity {
    @BindView(R.id.include)
    Toolbar toolbar;
    int language,currency,country;
    String currencyname,id_customer,name_custome,mobilecustomer,firstcolor;
     ArrayList<CartCLass> cartList;
    String address ;
    private TextView adress_text,name,mob,change,totalprice,totalpriceWship;
    Button ship_btn;
    private SharedPreferences sharedPreferences;
    Bundle savedIns;
    String addressid;
    private String id_zone;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        savedIns=savedInstanceState;
        setContentView(R.layout.final_check);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        sharedPreferences=getSharedPreferences("LanguageAndCountry", Context.MODE_PRIVATE);
        firstcolor=sharedPreferences.getString("firstmenucolour","#000000");
        toolbar.setBackgroundColor(Color.parseColor(firstcolor));
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.check);
        ScrollView scrollView = (ScrollView) findViewById(R.id.scroll);
        scrollView.setFocusableInTouchMode(true);
        scrollView.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);


        country=sharedPreferences.getInt("Country",1);
        currency=Integer.parseInt(sharedPreferences.getString("currency","1"));
        language=sharedPreferences.getInt("Lang",1);
        currencyname=sharedPreferences.getString("currencyname","EGB");
        id_customer=sharedPreferences.getString("customer_id","53");
        addressid=getIntent().getExtras().getString("id_add");
//        name_custome=sharedPreferences.getString("name","name");
//        mobilecustomer=sharedPreferences.getString("mobile","");
try {
    address=getIntent().getExtras().getString("address");
    name_custome=getIntent().getExtras().getString("name");
    mobilecustomer=getIntent().getExtras().getString("mob");
    id_zone=getIntent().getExtras().getString("id_zone");
}
catch (Exception e){

    address="no data";
}


        cartList= TestModel.getCartList();
        adress_text=(TextView)findViewById(R.id.address_saved_text);
        name=(TextView)findViewById(R.id.name);
        mob=(TextView)findViewById(R.id.mobile);
        change=(TextView)findViewById(R.id.change_add);
        totalprice=(TextView)findViewById(R.id.total_text);
        totalpriceWship=(TextView)findViewById(R.id.total_with_ship);

        adress_text.setText(address);
        name.setText(name_custome);
        mob.setText(mobilecustomer);
        ship_btn =(Button)findViewById(R.id.ship_btn);
        new Fonts(this).setView(ship_btn);
        ship_btn.setBackgroundColor(Color.parseColor(firstcolor));
        ship_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Final_check.this, Shipingcity.class);
               // finish();
                intent.putExtra("id_add",getIntent().getExtras().getString("id_add"));
                intent.putExtra("id_zone",getIntent().getExtras().getString("id_zone"));
                intent.putExtra("address",getIntent().getExtras().getString("address"));
                intent.putExtra("name",getIntent().getExtras().getString("name"));
                intent.putExtra("mob",getIntent().getExtras().getString("mob"));
                intent.putExtra("name",getIntent().getExtras().getString("name"));

                startActivity(intent);
                overridePendingTransition(R.anim.no_thing, R.anim.slide_out_left);

            }
        });
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(getApplicationContext(), Add_Address.class);
                i.putExtra("frag","x");
                startActivity(i);
            }

        });


        ExpandableHeightGridView list = (ExpandableHeightGridView) findViewById(R.id.list_view);
        list.setAdapter(new final_checkAdapter(this,currencyname,TestModel.getCartList()));


        Log.d("test2",TestModel.getCartList().get(0).getTotalmoney()+"");

         totalprice.setText(TestModel.getCartList().get(0).getTotalmoney()+"\t \t"+currencyname);
     totalpriceWship.setText(cartList.get(0).getTotalmoney()+"\t \t"+currencyname);




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
        Intent intent = new Intent(this, ChartActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP );
        finish();
        startActivity(intent);
        overridePendingTransition(R.anim.no_thing, R.anim.left_to_right);
    }

    @Override
    protected void onRestart() {
        super.onRestart();

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
