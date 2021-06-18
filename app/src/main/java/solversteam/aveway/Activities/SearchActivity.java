package solversteam.aveway.Activities;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.ButterKnife;
import solversteam.aveway.Adapters.CategoriesAdapter;
import solversteam.aveway.Adapters.SearchListAdapter;
import solversteam.aveway.Connection.Connection;
import solversteam.aveway.Connection.ConnectionDetector;
import solversteam.aveway.Models.ItemForView;
import solversteam.aveway.Models.TestModel;
import solversteam.aveway.Models.WishListModel;
import solversteam.aveway.utiltes.Fonts;
import solverteam.aveway.R;

/**
 * Created by ahmed ezz on 26/02/2017.
 */

public class SearchActivity extends AppCompatActivity  {
    private SharedPreferences preferences;
    private int language,country,index=0,conversion_rate, start=0,end=0;
    private Boolean flag_loading;
    private ListView search_list;
    private EditText search_edittetx;
    private CategoriesAdapter categoriesAdapter;
    private ArrayList<WishListModel>models;
    private  Dialog dialog;

    private Connection connection;
    private ConnectionDetector connectionDetector;
    private String url,key="1-Product",search_key;
    private ArrayList<ItemForView> itemForViewsnew,itemForViews2 ;
    private ArrayList<Integer> Ids,Idsnew ;
    private JSONObject jsonObject;
    private JSONArray jsonArray;
    private boolean flag = true;
    private ArrayList<String> image_urls,image_urlsnew;
    private SearchListAdapter adapter;
    private GridLayoutManager layoutmanager;
    private TextView result_view;
    ImageView search;
    private String Gsize,firstcolor;
    private FirebaseAnalytics mFirebaseAnalytics;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);

        connectionDetector=new ConnectionDetector(this);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        preferences = getSharedPreferences("LanguageAndCountry", MODE_PRIVATE);
        language = preferences.getInt("Lang", 1) ;
        country = preferences.getInt("Country", 1) ;
        conversion_rate=preferences.getInt("currencyconversion",1);
        image_urls = new ArrayList<>();
        itemForViewsnew=new ArrayList<>();
        Ids=new ArrayList<>();
        ButterKnife.bind(this);
        toolbar=(Toolbar) findViewById(R.id.include);
        firstcolor=preferences.getString("firstmenucolour","#000000");
        toolbar.setBackgroundColor(Color.parseColor(firstcolor));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.search);
        search_list=(ListView)findViewById(R.id.activity_searchable_serachdata_recyclerview);
        search_edittetx=(EditText) findViewById(R.id.activity_searchable_toolbar).findViewById(R.id.serachable_toolbar_edittext);

        new Fonts(this).setView(search_edittetx);
        try{
            String search=getIntent().getExtras().getString("Search_name");
            search_edittetx.setText(search);
            search();

        }catch (Exception e){}

        search=(ImageView) findViewById(R.id.search_btn_search);
        search.setBackgroundColor(Color.parseColor(firstcolor));
        image_urlsnew=new ArrayList<>();
        Idsnew=new ArrayList<>();
        result_view=(TextView)findViewById(R.id.activity_search_result_textview);


       search.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

             search();

            }
        });
        search_edittetx.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    search();
                    return true;
                }
                return false;
            }
        });



        search_edittetx.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!search_edittetx.getText().toString().equals(""))
                    search();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        search_list.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {

//load more news from bottom of list
                if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount != 0) {
                    if (flag_loading == false) {
                        flag_loading = true;
                        index++;
                        if(connectionDetector.isConnectingToInternet())
                        {
                        url = "/GetAllGategoryProductBySearch/" + country + "/" + language + "/" + search_key+"/0" + "/" + index + "/10";
                        connection = new Connection(SearchActivity.this, url, "Get");
                        connection.reset();
                        connection.Connect(new Connection.Result() {
                            @Override
                            public void data(String str) throws JSONException {
                                Log.i("wezza", str);

                                getData(str, url,index,key);
                            }
                        });}
                    }
                }
            }
        });


}
    public void getData(String json,String Url,int pagenumber,String Key)
    {   image_urls = new ArrayList<>();
        itemForViews2=new ArrayList<>();
        Ids=new ArrayList<>();


        try{
            jsonObject = new JSONObject(json);
           // Log.d("wezzaha",Key);
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

            if(jsonArray.length()>0){
                for (int Counter = 0; Counter < jsonArray.length(); Counter++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(Counter);
                        itemForViews2.add(new ItemForView(jsonObject1.getString("productlink"),
                                "", "See All",
                                jsonObject1.getString("url_image"),
                                jsonObject1.getString("Name"),
                                jsonObject1.getInt("Price tax excl.") * conversion_rate,
                                0, 0, R.layout.item_layout, Url, Key));
                        Ids.add(jsonObject1.getInt("ID"));
                        image_urls.add(jsonObject1.getString("url_image"));
                }
             //   Log.d("checkdata1",itemForViews2.size()+"");


                if(pagenumber==0)
                {
                    end=0;
                }
                else {
                    end=pagenumber+20;
                }
                for( start=0;start<itemForViews2.size();start++)
                {
                    itemForViewsnew.add(itemForViews2.get(start));
                    image_urlsnew.add(image_urls.get(start));
                    Idsnew.add(Ids.get(start));


                }


                int index = search_list.getFirstVisiblePosition();
                View v = search_list.getChildAt(0);
                int top = (v == null) ? 0 : v.getTop();
                //Log.d("checkdata2",itemForViewsnew.size()+"");

                adapter =new SearchListAdapter(this,search_list,itemForViewsnew,Idsnew,image_urlsnew);
                search_list.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                if(itemForViewsnew.size()>0)
                {result_view.setVisibility(View.VISIBLE);
                result_view.setText(itemForViewsnew.size()+"-"+Gsize+"\t"+getResources().getString(R.string.results));}
                else {
                    result_view.setVisibility(View.INVISIBLE);
                }
               if(itemForViewsnew==null)
                   result_view.setVisibility(View.INVISIBLE);

// notify dataset changed or re-assign adapter here

// restore the position of listview
                search_list.setSelectionFromTop(index, top);
                flag_loading=false;



            }
            else {
                if(pagenumber==0)
                {
                    Toast.makeText(this,"لا يوجد مزيد من نتائج البحث  ",Toast.LENGTH_LONG).show();
                    result_view.setVisibility(View.INVISIBLE);

                }
                else {
                    Toast.makeText(this,"لا يوجد المزيد من نتائج البحث ",Toast.LENGTH_LONG).show();}
                    flag_loading=true;


            }

            adapter.notifyDataSetChanged();
        }

        catch (Exception e)
        {
            e.printStackTrace();
            Toast.makeText(this,"لا يوجد  المزيد من نتئائج البحث ",Toast.LENGTH_LONG).show();
            result_view.setVisibility(View.INVISIBLE);

            flag_loading=true;

        }

    }













    @Override
    protected void onRestart() {
        super.onRestart();
/* Specify the size of the list up front to prevent resizing. */
        ArrayList<String> newList = new ArrayList<String>(Idsnew.size());
        for (Integer myInt : Idsnew) {
            newList.add(String.valueOf(myInt));
        }

        models=new ArrayList<>();
        models= TestModel.getfromshared("wish",this);
        if(models!=null) {
            for (int position = 0; position < models.size(); position++) {
//                Log.d("checks7", TestModel.checkidinmain(TestModel.getAllids(), models.get(position).getItem_id()) + "");
                if (TestModel.checkidinmain(newList, models.get(position).getItem_id())) {
               //     Log.d("checkidb2a", models.get(position).getItem_id() + "");
                    try {
                        ImageView view = (ImageView) search_list.findViewWithTag(models.get(position).getItem_id() + "");
                        view.setImageResource(R.drawable.ic_favorite_black_24dp);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            for(int position=0;position<Idsnew.size();position++)
            {
                if (!TestModel.checkitem(models,newList.get(position).toString())) {
                    try {
                        ImageView view = (ImageView) search_list.findViewWithTag(Idsnew.get(position).toString()+ "");
                        view.setImageResource(R.mipmap.ic_wishlist_outline_disable);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
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
    private void search()
    {

        search_key = search_edittetx.getText().toString();
        if(itemForViews2!=null)
        {
            itemForViews2.clear();
            itemForViewsnew.clear();
            Idsnew.clear();
            image_urlsnew.clear();
            image_urls.clear();
            Ids.clear();
            index=0;

        }

        if(search_key.length()==1||search_key.length()>1)
        // if(!String.valueOf(search_key).equals(" "))
        {



            if(String.valueOf(search_key.charAt(0)).equals(" "))
            {
                search_key=search_key.replace(String.valueOf(search_key.charAt(0)),"");
            }
            if(search_key.length()>0)
                if(String.valueOf(search_key.charAt(search_key.length()-1)).equals(" "))
                {
                    search_key=search_key.replace(String.valueOf(search_key.charAt(search_key.length()-1)),"");

                }

            try{
                Bundle bundle = new Bundle();
                bundle.putString("search_key", search_key);
                mFirebaseAnalytics.logEvent("most_search", bundle);
                mFirebaseAnalytics.setUserProperty("news_search",search_key+"");


            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            if(connectionDetector.isConnectingToInternet())
            {
            url = "/GetAllGategoryProductBySearch/" + country + "/" + language + "/" + search_key +"/0"+ "/" + index + "/10";

            // for keyboard down

            connection = new Connection(SearchActivity.this,url, "Get");
            connection.reset();
            connection.Connect(new Connection.Result() {
                @Override
                public void data(String str) throws JSONException {

                    getData(str, url, index,key);

                }
            });}}
        else {
            try{
                if(adapter!=null){
            result_view.setVisibility(View.INVISIBLE);
            adapter.notifyDataSetChanged();
            adapter.notifyDataSetInvalidated();
                }
            }catch (Exception e){Log.d("exception","kfkf");}
        }
    }
}
