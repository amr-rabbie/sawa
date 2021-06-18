package solversteam.aveway.Activities;

import android.app.Dialog;
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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import solversteam.aveway.Models.ChartModel;
import solversteam.aveway.Models.TestModel;
import solversteam.aveway.utiltes.Fonts;
import solverteam.aveway.R;

public class OrderMessage extends AppCompatActivity {
    private TextView startship_textview,order_view;
    private Button home;
    private String startship,orderid,firstcolor,defult;
    @BindView(R.id.include)
    Toolbar toolbar;

    private Intent intent;
    private ArrayList<ChartModel>chartModels;
    private SharedPreferences preferences;
    private String currencyname,logo;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_message);
        ButterKnife.bind(this);
        
        preferences = getSharedPreferences("LanguageAndCountry", MODE_PRIVATE);
        firstcolor=preferences.getString("firstmenucolour","#000000");
        toolbar.setBackgroundColor(Color.parseColor(firstcolor));
        currencyname= preferences.getString("currencyname","EGP");
        logo= preferences.getString("logo","");
        startship_textview=(TextView)findViewById(R.id.transport);
        order_view=(TextView)findViewById(R.id.OrderMessage_order_textview);
        home=(Button)findViewById(R.id.buttnhome);
        home.setBackgroundColor(Color.parseColor(firstcolor));
        new Fonts(this).setView(home);
        defult= preferences.getString("defaultimage","");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView tx =(TextView)toolbar.findViewById(R.id.title);
        tx.setText("");
        if(TestModel.getfromcardmodel("card",this)!=null)
        {
            chartModels=TestModel.getfromcardmodel("card",this);
            chartModels.clear();
            TestModel.savetosharedcardmodel(chartModels,"card",this);


        }
        else {
            chartModels=new ArrayList<>();
        }

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        try{
            startship=getIntent().getExtras().getString("startshippingat");
            orderid=getIntent().getExtras().getString("order_id");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        orderid = orderid.replaceAll("^\"|\"$", "");

        startship_textview.setText(getResources().getString(R.string.atleast)+"\t"+startship );
        order_view.setText(orderid );


    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        intent=new Intent(this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
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
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.layout_loading_item);
                dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(true);
                LinearLayout relativeLayout=(LinearLayout) dialog.findViewById(R.id.rel_loder);
                relativeLayout.setOnTouchListener(new View.OnTouchListener() {
                    public boolean onTouch(View view, MotionEvent event) {
                        switch(event.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                // The user just touched the screen

                                dialog.dismiss();
                                break;
                            case MotionEvent.ACTION_UP:
                                // The touch just ended

                                break;
                        }

                        return false;
                    }});
                return dialog;
            default:
                return null;
        }}
}
