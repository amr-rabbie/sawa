package solversteam.aveway.Activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import solversteam.aveway.Adapters.WishListAdapter;
import solversteam.aveway.Models.ChartModel;
import solversteam.aveway.Models.TestModel;
import solversteam.aveway.Models.WishListModel;
import solverteam.aveway.R;

public class WishListActivity extends AppCompatActivity {
    @BindView(R.id.activity_wishList_recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.include)
    Toolbar toolbar;
    private WishListAdapter adapter;
    private GridLayoutManager gridLayoutManager;
    private ArrayList<WishListModel> models;
    private ArrayList<ChartModel> chartModels;
    private TextView notifytext;
    private String defult;
    private Dialog dialog;
    private SharedPreferences sharedPreferences;
    private String firstcolor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish_list);
        ButterKnife.bind(this);
         sharedPreferences=getSharedPreferences("LanguageAndCountry", Context.MODE_PRIVATE);
         firstcolor=sharedPreferences.getString("firstmenucolour","#7d2265");
        toolbar.setBackgroundColor(Color.parseColor(firstcolor));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SharedPreferences preferences = getSharedPreferences("LanguageAndCountry", MODE_PRIVATE);
        getSupportActionBar().setTitle("");
        TextView tx= (TextView)toolbar.findViewById(R.id.title);
        tx.setText(preferences.getString("projectname","E_commerce"));
        toolbar.findViewById(R.id.title).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(WishListActivity.this, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP );
                startActivity(i);
                overridePendingTransition(R.anim.right_to_left, R.anim.no_thing);
                finish();

            }
        });
        toolbar.findViewById(R.id.toolbar_shopping).setVisibility(View.VISIBLE);
        toolbar.findViewById(R.id.toolbar_search).setVisibility(View.VISIBLE);
        toolbar.findViewById(R.id.toolbar_shopping).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WishListActivity.this, ChartActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.right_to_left, R.anim.no_thing);
            }
        });

        notifytext=(TextView)toolbar.findViewById(R.id.textOne);
        notifytext.setVisibility(View.INVISIBLE);
        if(TestModel.getfromcardmodel("card",this)!=null)
        {
            chartModels=TestModel.getfromcardmodel("card",this);
            notifytext.setVisibility(View.VISIBLE);
            if(chartModels.size()>0)
            {notifytext.setText(TestModel.getfromshared("card",WishListActivity.this).size()+"");}
            else {
                notifytext.setVisibility(View.INVISIBLE);

            }

        }
        else {
            chartModels=new ArrayList<>();
            notifytext.setVisibility(View.INVISIBLE);

        }
        toolbar.findViewById(R.id.toolbar_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WishListActivity.this, SearchActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.right_to_left, R.anim.no_thing);
            }
        });
        models=new ArrayList<>();
        models = TestModel.getfromshared("wish",this);
//        Log.d("checkitem",models.size()+"");

        if(models==null){initiatePopupWindow();}
        if (models!=null)
        {
            if(models.size()<=0){initiatePopupWindow();}
           // Log.d("checkitem0",models.size()+"");
            findViewById(R.id.empty_wishlist).setVisibility(View.GONE);
        adapter = new WishListAdapter(models, this,notifytext,chartModels);
        recyclerView.setAdapter(adapter);
        gridLayoutManager = new GridLayoutManager(WishListActivity.this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);}
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
    protected void onRestart() {
        super.onRestart();
        if(TestModel.getfromcardmodel("card",this)!=null)
        {
            chartModels=TestModel.getfromcardmodel("card",this);
            if(chartModels.size()>0)
            {notifytext.setVisibility(View.VISIBLE);
                notifytext.setText(TestModel.getfromshared("card",WishListActivity.this).size()+"");}
            else {
                notifytext.setVisibility(View.INVISIBLE);
            }

        }
        else {
            notifytext.setVisibility(View.INVISIBLE);

        }
    }

    private void initiatePopupWindow() {
        GradientDrawable gd = new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                new int[] {Color.parseColor(firstcolor),Color.parseColor(firstcolor)});
        gd.setCornerRadius(5);


       String logo=sharedPreferences.getString("logo","");
         defult=sharedPreferences.getString("defaultimage","");
        RelativeLayout item = (RelativeLayout)findViewById(R.id.popp);
        findViewById(R.id.contain).setVisibility(View.GONE);
        View child = getLayoutInflater().inflate(R.layout.popup, null);ImageView img =(ImageView) child.findViewById(R.id.imageView9);
        if (!logo.isEmpty())
            Picasso.with(this)
                    .load(logo)
                    .resize(250,180)
                    .placeholder(R.drawable.defaultimage)
                    .error(R.drawable.ave)
                    .into(img);
        else
            Picasso.with(this)
                    .load(defult)
                    .resize(250,180)
                    .placeholder(R.drawable.defaultimage)
                    .error(R.drawable.ave)
                    .into(img);

        TextView tx =(TextView) child.findViewById(R.id.pop_text);
        TextView tx2 =(TextView) child.findViewById(R.id.pop_text2);
        tx.setText(R.string.msg_wish);
        tx2.setText(R.string.msg_cart_wish_2);
        child.findViewById(R.id.pop).setPadding(10,10,10,10);
        child.findViewById(R.id.pop).setBackgroundDrawable(gd);
        child.findViewById(R.id.pop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(WishListActivity.this,MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                finish();
                startActivity(i);
                overridePendingTransition(R.anim.right_to_left, R.anim.no_thing);
            }
        });
        item.addView(child);

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
