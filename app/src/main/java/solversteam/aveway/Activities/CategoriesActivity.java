package solversteam.aveway.Activities;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import solversteam.aveway.Adapters.CategoriesAdapter;
import solversteam.aveway.Connection.Connection;
import solversteam.aveway.Models.ChartModel;
import solversteam.aveway.Models.ItemForView;
import solversteam.aveway.Models.TestModel;
import solversteam.aveway.Models.WishListModel;
import solversteam.aveway.utiltes.Fonts;
import solversteam.aveway.utiltes.ItemClickSupport;
import solversteam.aveway.utiltes.OnLoadMoreListener;
import solverteam.aveway.R;

public class CategoriesActivity extends AppCompatActivity {
    private Dialog dialog;

    @BindView(R.id.activity_categories_recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.include)
    Toolbar toolbar;
    CategoriesAdapter adapter;

    private JSONObject jsonObject;
    private JSONArray jsonArray;
    private Connection connection;
    GridLayoutManager llm;
    FrameLayout layout;
    private ArrayList<WishListModel>models;
    ArrayList<Object> itemForViews = new ArrayList<>();
    ArrayList<ItemForView> itemForViews2 = new ArrayList<>();
    ArrayList<Integer> Ids = new ArrayList<>();
    private boolean flag = true, flag1 = true;
    private int size,conversion_rate,index = 0,i = 0;
    private String url, Key,brand_name,brand_id,cat_name,cat_id,log_name,firstcolor;
    private ArrayList<String> image_urls;
    private TextView notifytext;
    private ArrayList<ChartModel> chartModels;
    private  Bundle bundle1;

    String Gsize="";
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        Fonts fonts=new Fonts(this);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        ButterKnife.bind(this);
        image_urls = new ArrayList<>();
        setSupportActionBar(toolbar);
        SharedPreferences preferences;
        preferences = getApplicationContext().getSharedPreferences("LanguageAndCountry", MODE_PRIVATE);
        firstcolor=preferences.getString("firstmenucolour","#000000");
        toolbar.setBackgroundColor(Color.parseColor(firstcolor));
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.findViewById(R.id.toolbar_shopping).setVisibility(View.VISIBLE);
        toolbar.findViewById(R.id.toolbar_search).setVisibility(View.VISIBLE);
        toolbar.findViewById(R.id.toolbar_shopping).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoriesActivity.this, ChartActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.right_to_left, R.anim.no_thing);
            }
        });
        getSupportActionBar().setTitle("");
        TextView tx= (TextView)toolbar.findViewById(R.id.title);
        notifytext=(TextView)toolbar.findViewById(R.id.textOne);
        fonts.setView(tx);
        fonts.setView(notifytext);
        tx.setText(preferences.getString("projectname","E_commerce"));
        toolbar.findViewById(R.id.title).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(CategoriesActivity.this, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP );
                startActivity(i);
                overridePendingTransition(R.anim.right_to_left, R.anim.no_thing);
                finish();

            }
        });

        notifytext.setVisibility(View.INVISIBLE);
        if(TestModel.getfromcardmodel("card",this)!=null)
        {
            chartModels=TestModel.getfromcardmodel("card",this);
            if(chartModels.size()>0)
            {notifytext.setVisibility(View.VISIBLE);
                notifytext.setText(TestModel.getfromshared("card",CategoriesActivity.this).size()+"");}
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
                Intent intent = new Intent(CategoriesActivity.this, SearchActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.right_to_left, R.anim.no_thing);
            }
        });
        llm = new GridLayoutManager(CategoriesActivity.this, 1);


        final int language = preferences.getInt("Lang", 1) ;
        final int country = preferences.getInt("Country", 1) ;
        conversion_rate=preferences.getInt("currencyconversion",1);

//        boolean brands = false , cat = false , allcat = false;
        String type="";
        try {
            type = getIntent().getExtras().getString("type");

        }catch (Exception e){
            e.printStackTrace();
        }

        Bundle bundle = new Bundle();
        boolean bool = bundle.getBoolean("allCategory");
        if (type.equals("brands")) {
            brand_name=TestModel.getBrands_name().get(getIntent().getExtras().getInt("position"));
            brand_id=TestModel.getBrandsIds().get(getIntent().getExtras().getInt("position"))+"";
            start_analytics("brands","brand_name",brand_name,"brand_id",brand_id);

            Log.d("checkdatabrands","check");
            url = "/GetAllManufacturerProduct/" + country + "/" + language + "/" + brand_id+"/"+index+"/10" ;
            Key = "1-GategoryProduct";
            if(TestModel.getBrandsIds()!=null)             size = TestModel.getBrandsIds().size();             else                 size=0;
        } else if (type.equals("cat1")) {
            cat_name=TestModel.getCatName().get(getIntent().getExtras().getInt("position"));
            cat_id=TestModel.getCatIds().get(getIntent().getExtras().getInt("position"))+"";
            start_analytics("category","category_name",cat_name,"category_id",cat_id);
            Log.d("checkidandnameincat1",cat_name+"\n"+cat_id);

            url = "/GetAllGategoryProduct/" + country + "/" + language + "/" + TestModel.getCatIds().get(getIntent().getExtras().getInt("position")) + "/" + index+"/10";
            Key = "1-GategoryProduct";
            if(TestModel.getBrandsIds()!=null)
                size = TestModel.getBrandsIds().size();
            else
                size=0;
        }



        else if (type.equals("cat1_menu")) {
            cat_name=getIntent().getExtras().getString("name");
            cat_id=getIntent().getExtras().getString("id");
            start_analytics("category","category_name",cat_name,"category_id",cat_id);
            Log.d("checkidandnameincat1",cat_name+"\n"+cat_id);

            url = "/GetAllGategoryProduct/" + country + "/" + language + "/" + cat_id + "/" + index+"/10";
            Key = "1-GategoryProduct";
            if(TestModel.getBrandsIds()!=null)
                size = TestModel.getBrandsIds().size();
            else
                size=0;
        }








        else if (type.equals("cat2")) {
            cat_name=TestModel.getCatIds2_1_name().get(getIntent().getExtras().getInt("position"));
            cat_id=TestModel.getCatIds2_1().get(getIntent().getExtras().getInt("position"))+"";
            start_analytics("category","category_name",cat_name,"category_id",cat_id);
            Log.d("checkidandnameincat2",cat_name+"\n"+cat_id);

            url = "/GetAllGategoryProduct/" + country + "/" + language + "/" + TestModel.getCatIds2_1().get(getIntent().getExtras().getInt("position")) + "/" + index+"/10";
            Key = "1-GategoryProduct";
            if(TestModel.getBrandsIds()!=null)             size = TestModel.getBrandsIds().size();
            else
                size=0;
        }
        else if (type.equals("cat3")) {

            cat_name=TestModel.getCat3_name();
            cat_id=TestModel.getCat3()+"";
            start_analytics("category","category_name",cat_name,"category_id",cat_id);
            Log.d("checkidandnameincat3",cat_name+"\n"+cat_id);

            url = "/GetAllGategoryProduct/" + country + "/" + language + "/" + TestModel.getCat3() + "/" + index+"/10";
            Key = "1-GategoryProduct";
            if(TestModel.getBrandsIds()!=null)             size = TestModel.getBrandsIds().size();             else                 size=0;
        }

    else if (type.equals("apads")) {
            //cat_name=TestModel.getCat3_name();
      String  cat_id=TestModel.getApads().get(getIntent().getExtras().getInt("position"))+"";
       // start_analytics("category","category_name",cat_name,"category_id",cat_id);
       // Log.d("checkidandnameincat3",cat_name+"\n"+cat_id);

        url = "/GetAllGategoryProduct/" + country + "/" + language + "/" + cat_id + "/" + index+"/10";
        Key = "1-GategoryProduct";
        if(TestModel.getBrandsIds()!=null)             size = TestModel.getBrandsIds().size();             else                 size=0;
    }

        else if(type.equals("product")){
            cat_name=TestModel.getCatName().get(getIntent().getExtras().getInt("position"));
            cat_id=TestModel.getCatIds().get(i)+"";
            start_analytics("category","category_name","","category_id",cat_id);

            url = "/GetAllGategoryProduct/" + country + "/" + language + "/" + TestModel.getCatIds().get(i) + "/" + index+"/10";
            Key = "1-GategoryProduct";
            if(TestModel.getBrandsIds()!=null)             size = TestModel.getBrandsIds().size();             else                 size=0;
        } else if(type.equals("allproducts")){

            Bundle extras = getIntent().getExtras();
            url = extras.getString("url")+"/"+index+"/10";
            Key = extras.getString("Key");
        }

        loadMore(url, Key);
        findViewById(R.id.sort_by).setVisibility(View.GONE);
        layout = (FrameLayout) findViewById(R.id.dailog_fragment);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout.setVisibility(View.INVISIBLE);
                TestModel.setFlag(false);
            }
        });
        adapter = new CategoriesAdapter(itemForViews, CategoriesActivity.this, null, recyclerView, layout,llm,Ids,image_urls,itemForViews2,notifytext,Gsize);
        final String finalType = type;
        adapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("checkctatus",index+"");
//                        Toast.makeText(CategoriesActivity.this,"Pages",Toast.LENGTH_LONG).show();
                        index= ++index;
                        if (index < 5) {
                            if (getIntent().getExtras().getString("type").equals("brands")) {
                                url = "/GetAllManufacturerProduct/" + country + "/" + language + "/" +  TestModel.getBrandsIds().get(getIntent().getExtras().getInt("position"))+"/" + index+"/10";
                                Key = "1-GategoryProduct";
                                if(TestModel.getBrandsIds()!=null)             size = TestModel.getBrandsIds().size();             else                 size=0;
                            } else if (getIntent().getExtras().getString("type").equals("cat1")) {
                                url = "/GetAllGategoryProduct/" + country + "/" + language + "/" + TestModel.getCatIds().get(getIntent().getExtras().getInt("position")) + "/" + index+"/10";
                                Key = "1-GategoryProduct";
                                if(TestModel.getBrandsIds()!=null)             size = TestModel.getBrandsIds().size();             else                 size=0;
                            }
                            else if (getIntent().getExtras().getString("type").equals("cat2")) {
                                url = "/GetAllGategoryProduct/" + country + "/" + language + "/" + TestModel.getCatIds2_1().get(getIntent().getExtras().getInt("position")) + "/" + index+"/10";
                                Key = "1-GategoryProduct";
                                if(TestModel.getBrandsIds()!=null)             size = TestModel.getBrandsIds().size();             else                 size=0;
                            }
                            else if (getIntent().getExtras().getString("type").equals("cat3")) {
                                url = "/GetAllGategoryProduct/" + country + "/" + language + "/" + TestModel.getCat3()+ "/" + index+"/10";
                                Key = "1-GategoryProduct";
                                if(TestModel.getBrandsIds()!=null)             size = TestModel.getBrandsIds().size();             else                 size=0;
                            }else if(getIntent().getExtras().getString("type").equals("allproducts")){
                                url = getIntent().getExtras().getString("url")+"/"+index+"/10";
                                Key =getIntent().getExtras().getString("Key");
                                if(TestModel.getBrandsIds()!=null)             size = TestModel.getBrandsIds().size();             else                 size=0;
                            }
                            loadMore(url, Key);// a method which requests remote data
                        } else {//result size 0 means there is no more data available at server
                            adapter.setMoreDataAvailable(false);
                            //telling adapter to stop calling load more as no more server data available
                            Toast.makeText(CategoriesActivity.this, "No More Data Available", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(llm);
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
        }}

    public void loadMore(final String URL, final String Key) {
        connection = new Connection(this, URL, "Get");
        connection.reset();
        connection.Connect(new Connection.Result() {
            @Override
            public void data(String str) throws JSONException {
                jsonObject = new JSONObject(str);
                try{
                JSONArray allsize=jsonObject.getJSONArray("1-FOUND_ROWS");

                Gsize =allsize.getJSONObject(0).getString("FOUND_ROWS");
                TestModel.setSize_array(Gsize);}
                catch (Exception e)
                {
                    e.printStackTrace();
                    Gsize="";
                }

                jsonArray = jsonObject.getJSONArray(Key);


                Log.d("checksize",jsonArray.length()+"");
                if (flag) {
                    itemForViews.add(jsonArray.length() +"-"+Gsize+" Results");
                    flag = false;
                }
                for (int Counter = 0; Counter < jsonArray.length(); Counter++) {
                    try {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(Counter);
                        itemForViews.add(new ItemForView(jsonObject1.getString("productlink"),
                                jsonObject1.getString("category_name"), "See All",
                                jsonObject1.getString("url_image"),
                                jsonObject1.getString("Name"),
                                jsonObject1.getInt("Price tax excl.") * conversion_rate,
                                0, 0, R.layout.item_layout, URL, Key,jsonObject1.getString("categoryid")));
                        itemForViews2.add(new ItemForView(jsonObject1.getString("productlink"),
                                jsonObject1.getString("category_name"), "",
                                jsonObject1.getString("url_image"),
                                jsonObject1.getString("Name"),
                                jsonObject1.getInt("Price tax excl.") * conversion_rate,
                                0, 0, R.layout.item_layout, URL, Key,jsonObject1.getString("categoryid")));
                        Ids.add(jsonObject1.getInt("ID"));
                        image_urls.add(jsonObject1.getString("url_image"));

                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
                ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        if (position!=0){
                            Intent intent = new Intent(CategoriesActivity.this, ItemSelectedShowActivity.class);
                            intent.putExtra("ID", Ids);
                            intent.putExtra("position", position - 1);
                            Bundle bundle = new Bundle();
                            bundle.putParcelableArrayList("myImages", itemForViews2);
                            intent.putExtra("img_urls", image_urls);
                            intent.putExtras(bundle);
                            startActivity(intent);
                            overridePendingTransition(R.anim.right_to_left, R.anim.no_thing);}
                    }
                });
                if (jsonArray.length() > 0)
                    adapter.notifyDataChanged();
                if(jsonArray.length()<10)
                {
                    adapter.setOnLoadMoreListener(null);
                }

            }
        });
    }

    @Override
    public void onBackPressed() {
//        Toast.makeText(this, "Flag is " + TestModel.isFlag(), Toast.LENGTH_SHORT).show();
        if (TestModel.isFlag()) {
            layout.setVisibility(View.INVISIBLE);
            TestModel.setFlag(false);
        } else {
            super.onBackPressed();
            overridePendingTransition(R.anim.no_thing, R.anim.left_to_right);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if(TestModel.getfromcardmodel("card",this)!=null)
        {
            chartModels=TestModel.getfromcardmodel("card",this);
            if(chartModels.size()>0)
            {notifytext.setVisibility(View.VISIBLE);
                notifytext.setText(TestModel.getfromshared("card",CategoriesActivity.this).size()+"");}
            else{
                notifytext.setVisibility(View.INVISIBLE);
            }

        }
        else {
            notifytext.setVisibility(View.INVISIBLE);
        }

        ArrayList<String> newList = new ArrayList<String>(Ids.size());
        for (Integer myInt : Ids) {
            newList.add(String.valueOf(myInt));
        }

        models=new ArrayList<>();
        models= TestModel.getfromshared("wish",this);
        if(models!=null) {
            for (int position = 0; position < models.size(); position++) {
//                Log.d("checks7", TestModel.checkidinmain(TestModel.getAllids(), models.get(position).getItem_id()) + "");
                if (TestModel.checkidinmain(newList, models.get(position).getItem_id())) {
                    Log.d("checkidb2a", models.get(position).getItem_id() + "");
                    try {
                        ImageView view = (ImageView) recyclerView.findViewWithTag(models.get(position).getItem_id() + "");
                        view.setImageResource(R.drawable.ic_favorite_black_24dp);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            for(int position=0;position<Ids.size();position++)
            {
                if (!TestModel.checkitem(models,newList.get(position).toString())) {
                    try {
                        ImageView view = (ImageView) recyclerView.findViewWithTag(Ids.get(position).toString()+ "");
                        view.setImageResource(R.mipmap.ic_wishlist_outline_disable);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    private void start_analytics(String type,String name_key,String name_value,String id_key,String id_value)
    {   if(type.equals("brands"))
    {
        log_name="most_brand";
    }
        else{
        log_name="most_category";
    }
        try{
            bundle1 = new Bundle();
            bundle1.putString(name_key,name_value);
            bundle1.putString(id_key, id_value);

            mFirebaseAnalytics.logEvent(log_name, bundle1);
            mFirebaseAnalytics.setUserProperty(name_key,name_value+"");
            mFirebaseAnalytics.setUserProperty(name_value,id_value+"");


        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
