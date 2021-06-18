package solversteam.aveway.Activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import solversteam.aveway.Connection.Connection;
import solversteam.aveway.Connection.ConnectionDetector;
import solversteam.aveway.Models.CartCLass;
import solversteam.aveway.Models.TestModel;
import solverteam.aveway.R;

public class PaymentOption extends AppCompatActivity {
    @BindView(R.id.include)
    Toolbar toolbar;
    @BindView(R.id.credit_card_payment)
    LinearLayout credit_card;
    @BindView(R.id.cash_payment)
    LinearLayout cash_payment;
    private Connection connection;
    private ConnectionDetector connectionDetector;
    private ArrayList<CartCLass>cartList;
    private SharedPreferences sharedPreferences;
    private int country,language,currency,id_customer;
    private String firstcolor;
    private Dialog dialog;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_option);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        sharedPreferences=getSharedPreferences("LanguageAndCountry", Context.MODE_PRIVATE);
        firstcolor=sharedPreferences.getString("firstmenucolour","#000000");
        toolbar.setBackgroundColor(Color.parseColor(firstcolor));
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.option);
        connectionDetector=new ConnectionDetector(this);
        cartList= TestModel.getCartList();

        country=sharedPreferences.getInt("Country",1);
        currency=Integer.parseInt(sharedPreferences.getString("currency","1"));
        language=sharedPreferences.getInt("Lang",1);
        id_customer=Integer.parseInt(sharedPreferences.getString("customer_id","53"));



//        credit_card.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(PaymentOption.this , OrderPayment.class);
//                intent.putExtra("pos" , 0);
//                startActivity(intent);
//                overridePendingTransition(R.anim.right_to_left, R.anim.no_thing);
//            }
//        });
        cash_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PaymentOption.this, Confirm_order.class);
                // finish();
                intent.putExtra("id_add",getIntent().getExtras().getString("id_add"));
                intent.putExtra("id_zone",getIntent().getExtras().getString("id_zone"));
                intent.putExtra("address",getIntent().getExtras().getString("address"));
                intent.putExtra("name",getIntent().getExtras().getString("name"));
                intent.putExtra("mob",getIntent().getExtras().getString("mob"));
                intent.putExtra("id_carrier",getIntent().getExtras().getString("id_carrier"));
                intent.putExtra("shippingprice",getIntent().getExtras().getString("shippingprice"));
                intent.putExtra("name",getIntent().getExtras().getString("name"));
                intent.putExtra("delay",getIntent().getExtras().getString("delay"));

                startActivity(intent);
                overridePendingTransition(R.anim.no_thing, R.anim.slide_out_left);

            }
        });

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

    @Override
    protected void onRestart() {
        super.onRestart();


//        getIntent().getExtras().getString("id_add");
//       getIntent().getExtras().getString("id_zone");
//        getIntent().getExtras().getString("address");
//         getIntent().getExtras().getString("name");
//        getIntent().getExtras().getString("mob");


    }
}
