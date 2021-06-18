package solversteam.aveway.Activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import solversteam.aveway.Adapters.ChartsAdapter;
import solversteam.aveway.Connection.Connection;
import solversteam.aveway.Models.ChartModel;
import solversteam.aveway.Models.TestModel;
import solversteam.aveway.utiltes.Fonts;
import solversteam.aveway.utiltes.ItemClickSupport;
import solverteam.aveway.R;

public class ChartActivity extends AppCompatActivity {
    @BindView(R.id.include)
    Toolbar toolbar;
    @BindView(R.id.activity_chart_recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.empty_chart)
    ImageView empty;
    ChartsAdapter chartsAdapter;
    private ArrayList<Integer>num;
    private ArrayList<ChartModel> models ;

    private TextView total_money;
    private Dialog dialog;

    ArrayList<String> adress;
    private Connection connection;
    private SharedPreferences sharedPreferences;
    private int country,currency,language;
    private String customer_id,logo,defult,firstcolor;
     private Fonts fonts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        ButterKnife.bind(this);
        fonts=new Fonts(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sharedPreferences=getSharedPreferences("LanguageAndCountry", Context.MODE_PRIVATE);
        firstcolor=sharedPreferences.getString("firstmenucolour","#000000");
        toolbar.setBackgroundColor(Color.parseColor(firstcolor));
        getSupportActionBar().setTitle("");
        TextView tx= (TextView)toolbar.findViewById(R.id.title);
        total_money=(TextView)findViewById(R.id.Chartactivity_total_textview);
        Button checkOut = (Button) findViewById(R.id.activity_chart_check_out_button);
        checkOut.setBackgroundColor(Color.parseColor(firstcolor));
        fonts.setView(checkOut);

        fonts.setView(tx);
        fonts.setView(total_money);
        tx.setText(sharedPreferences.getString("projectname","E_commerce"));
        toolbar.findViewById(R.id.title).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(ChartActivity.this, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP );
                startActivity(i);
                overridePendingTransition(R.anim.right_to_left, R.anim.no_thing);
                finish();

            }
        });

        //toolbar.findViewById(R.id.toolbar_edit).setVisibility(View.VISIBLE);
        models = new ArrayList<>();
        adress = new ArrayList<>();

        country=sharedPreferences.getInt("Country",1);
        currency=Integer.parseInt(sharedPreferences.getString("currency","1"));
        language=sharedPreferences.getInt("Lang",1);
        customer_id=sharedPreferences.getString("customer_id","53");
        logo=sharedPreferences.getString("logo","");
        defult=sharedPreferences.getString("defaultimage","");

        LinearLayoutManager llm = new LinearLayoutManager(this);


        if(TestModel.getfromcardmodel("card",this)==null)
        {
            initiatePopupWindow();
        }

        if(TestModel.getfromcardmodel("card",this)!=null)
        {
            if(TestModel.getfromcardmodel("card",this).size()<=0)
            {
                initiatePopupWindow();
            }

//            TextView price = (TextView) findViewById(R.id.activity_cart_total_price_text);



            num=new ArrayList<>();
            models=TestModel.getfromcardmodel("card",this);

                Log.d("checkmodelszie",models.size()+"");
            if(TestModel.getNums(this)==null|| TestModel.getNums(this).size()<=0)
            {  for(int i=0;i<models.size();i++)
                {
                    num.add(1);
                }
            TestModel.setNums(num,this);}
            else{
                if(TestModel.getNums(this).size()!=models.size())
                {
                    int x=TestModel.getNums(this).size();
                    num=TestModel.getNums(this);
                    for (int i=x;i<models.size();i++)
                    {
                        num.add(i,1);
                        TestModel.setNums(num,this);
                    }
                }
            }
//            Log.d("v_list_qnt",TestModel.getNums(this).get(0)+"");
            RelativeLayout relativeLayout=(RelativeLayout)findViewById(R.id.activity_cart) ;
            chartsAdapter = new ChartsAdapter(models, this,total_money,llm,recyclerView,TestModel.getNums(this),checkOut);
        }

        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(chartsAdapter);
        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
//                Toast.makeText(ChartActivity.this, "here we will go to details activity", Toast.LENGTH_SHORT).show();
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

    public boolean onTouchEvent(MotionEvent event)
    {

        if(event.getAction() == MotionEvent.ACTION_OUTSIDE){
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
    private void initiatePopupWindow() {

        String   firstcolor=sharedPreferences.getString("firstmenucolour","#7d2265");
        String secondcolor=sharedPreferences.getString("secondmenucolour","#000000");


        GradientDrawable gd = new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                new int[] {Color.parseColor(firstcolor),Color.parseColor(firstcolor)});
        gd.setCornerRadius(5);


        RelativeLayout item = (RelativeLayout)findViewById(R.id.popp);
        findViewById(R.id.contain).setVisibility(View.GONE);
        View child = getLayoutInflater().inflate(R.layout.popup, null);
        TextView tx =(TextView) child.findViewById(R.id.pop_text);
        TextView tx2 =(TextView) child.findViewById(R.id.pop_text2);
        fonts.setView(tx);
        fonts.setView(tx2);
        ImageView img =(ImageView) child.findViewById(R.id.imageView9);
        if (!logo.isEmpty())
        Picasso.with(this)
                .load(logo)
                .placeholder(R.drawable.defaultimage)
                .resize(250,180)
                .error(R.drawable.ave)
                .into(img);
        else
            Picasso.with(this)
                    .load(defult)
                    .placeholder(R.drawable.defaultimage)
                    .resize(250,180)
                    .error(R.drawable.ave)
                    .into(img);


        tx.setText(R.string.msg_cart);
        tx2.setText(R.string.msg_cart_wish_2);

        child.findViewById(R.id.pop).setPadding(10,10,10,10);
        Button btn = (Button) child.findViewById(R.id.pop);
        btn.setBackgroundDrawable(gd);
        fonts.setView(btn);
        child.findViewById(R.id.pop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(ChartActivity.this,MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                finish();
                startActivity(i);
                overridePendingTransition(R.anim.right_to_left, R.anim.no_thing);
            }
        });
        item.addView(child);

    }
}
